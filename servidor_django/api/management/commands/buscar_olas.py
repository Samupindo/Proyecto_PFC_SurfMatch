from django.core.management.base import BaseCommand
import requests
from api.models import SesionIdeal, Match
from datetime import datetime
from django.utils.timezone import make_aware


class Command(BaseCommand):
    help = 'Radar Pro Diario: Filtros de Periodo Mínimo y Altura Asimétrica'

    ZONAS = {
        "A Coruña": {"lat": 43.36, "lon": -8.41, "offshore": [135, 225]},
        "A Coruña / Norte": {"lat": 43.36, "lon": -8.41, "offshore": [135, 225]},
        "Nemiña": {"lat": 43.01, "lon": -9.26, "offshore": [45, 160]},
        "Costa da Morte": {"lat": 43.01, "lon": -9.26, "offshore": [45, 160]},
        "Carnota": {"lat": 42.82, "lon": -9.10, "off_min": 315, "off_max": 160},
        "Carnota / Fisterra Sur": {"lat": 42.82, "lon": -9.10, "off_min": 315, "off_max": 160},
        "Rías Baixas": {"lat": 42.23, "lon": -8.85, "offshore": [45, 135]},
    }

    def handle(self, *args, **kwargs):
        Match.objects.all().delete()
        sesiones = SesionIdeal.objects.all()
        self.stdout.write(self.style.HTTP_INFO("🕵️ Ajustando filtros: Anti-mar de viento y tolerancia de tamaño..."))

        for sesion in sesiones:
            config = self.ZONAS.get(sesion.zona_referencia)
            if not config: continue

            url = f"https://marine-api.open-meteo.com/v1/marine?latitude={config['lat']}&longitude={config['lon']}&hourly=wave_height,wave_period,wind_speed_10m,wind_direction_10m&timezone=Europe%2FMadrid"

            try:
                data = requests.get(url).json()
                t_list = data['hourly']['time']
                h_list = data['hourly']['wave_height']
                p_list = data['hourly']['wave_period']
                v_list = data['hourly']['wind_speed_10m']
                d_list = data['hourly']['wind_direction_10m']

                tam_id = float(sesion.tamano)
                periodo_id = int(sesion.periodo)  # Extraemos el periodo ideal de la BD
                hoy = datetime.now().date()

                mejores_por_dia = {}

                for i in range(len(t_list)):
                    dt = datetime.fromisoformat(t_list[i])
                    fecha_str = dt.strftime('%Y-%m-%d')

                    if dt.date() >= hoy and 8 <= dt.hour <= 20:
                        h_mar_abierto = h_list[i]

                        if h_mar_abierto is not None:
                            factor_exposicion = 1.0
                            if "pindo" in sesion.alias.lower():
                                factor_exposicion = 0.35

                            h_real = h_mar_abierto * factor_exposicion
                            p_real = p_list[i] if p_list[i] is not None else 0

                            # --- LA SOLUCIÓN ESTÁ AQUÍ ---

                            # 1. Altura Asimétrica: Toleramos poco por abajo (-0.3m), pero mucho por arriba (+1.0m)
                            limite_inf_altura = tam_id - 0.3
                            limite_sup_altura = tam_id + 1.0

                            # 2. Periodo Mínimo: Evitamos el mar revuelto de viento (Día 27)
                            # Exigimos siempre un mínimo de 8s, o tu ideal menos 4s (lo que sea mayor)
                            periodo_minimo = max(periodo_id - 4, 8)

                            if (limite_inf_altura <= h_real <= limite_sup_altura) and (p_real >= periodo_minimo):
                                score = 0
                                score += (p_real * 2)

                                v_vel = v_list[i] if v_list[i] is not None else 0
                                v_dir = d_list[i] if d_list[i] is not None else 0
                                es_offshore = False

                                if "Carnota" in sesion.zona_referencia:
                                    if v_dir >= 315 or v_dir <= 160: es_offshore = True
                                else:
                                    r = config.get('offshore', [0, 0])
                                    if len(r) == 2 and r[0] <= v_dir <= r[1]: es_offshore = True

                                if es_offshore and v_vel < 25:
                                    score += 35
                                elif v_vel < 12:
                                    score += 15
                                else:
                                    score -= v_vel

                                if 10 <= dt.hour <= 13:
                                    score += 20

                                if score > 0:
                                    if fecha_str not in mejores_por_dia or score > mejores_por_dia[fecha_str]['score']:
                                        mejores_por_dia[fecha_str] = {
                                            'idx': i,
                                            'score': score,
                                            'altura_calculada': h_real
                                        }

                for fecha_str, datos_dia in mejores_por_dia.items():
                    idx = datos_dia['idx']
                    mejor_puntuacion = datos_dia['score']
                    dt_ganador = datetime.fromisoformat(t_list[idx])

                    viento_seguro = v_list[idx] if v_list[idx] is not None else 0.0
                    periodo_seguro = p_list[idx] if p_list[idx] is not None else 0

                    Match.objects.create(
                        sesion_ideal=sesion,
                        fecha_hora_match=make_aware(dt_ganador),
                        altura_real=datos_dia['altura_calculada'],
                        periodo_real=periodo_seguro,
                        viento_real=viento_seguro,
                        leido_en_zona=sesion.zona_referencia
                    )
                    # Ahora imprimimos también el periodo para que veas cómo filtra
                    self.stdout.write(self.style.SUCCESS(
                        f"📅 {dt_ganador.strftime('%d/%m %H:00')} | {sesion.alias} | Olas: {datos_dia['altura_calculada']:.2f}m | Per: {periodo_seguro}s | Score: {mejor_puntuacion:.1f}"))

            except Exception as e:
                self.stdout.write(self.style.ERROR(f"Error procesando {sesion.alias}: {e}"))