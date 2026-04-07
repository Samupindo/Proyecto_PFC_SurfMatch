from django.core.management.base import BaseCommand
import requests
from api.models import SesionIdeal, Match
from datetime import datetime
from django.utils.timezone import make_aware


class Command(BaseCommand):
    help = 'Radar Real: Busca el mejor momento del viernes'

    ZONAS = {
        "A Coruña": {"lat": 43.36, "lon": -8.41, "offshore": [135, 225]},
        "Nemiña": {"lat": 43.01, "lon": -9.26, "offshore": [45, 160]},
        "Costa da Morte": {"lat": 43.01, "lon": -9.26, "offshore": [45, 160]},
        "Carnota": {"lat": 42.82, "lon": -9.10, "off_min": 315, "off_max": 160},
        "Rías Baixas": {"lat": 42.23, "lon": -8.85, "offshore": [45, 135]},
    }

    def handle(self, *args, **kwargs):
        Match.objects.all().delete()  # Limpiamos para no duplicar
        sesiones = SesionIdeal.objects.all()

        for sesion in sesiones:
            config = self.ZONAS.get(sesion.zona_referencia)
            if not config: continue

            url = f"https://marine-api.open-meteo.com/v1/marine?latitude={config['lat']}&longitude={config['lon']}&hourly=wave_height,wave_period,wind_speed_10m,wind_direction_10m&timezone=Europe%2FMadrid"

            try:
                data = requests.get(url).json()
                t_list = data['hourly']['time']
                h_list = data['hourly']['wave_height']
                p_list = data['hourly']['wave_period']

                tam_id = float(sesion.tamano)

                for i in range(len(t_list)):
                    if "2026-04-10" in t_list[i]:
                        dt = datetime.fromisoformat(t_list[i])
                        if 9 <= dt.hour <= 18:

                            h_real = h_list[i]
                            if (tam_id - 0.8 <= h_real <= tam_id + 0.8):
                                Match.objects.create(
                                    sesion_ideal=sesion,
                                    fecha_hora_match=make_aware(dt),
                                    altura_real=h_real,
                                    periodo_real=p_list[i],
                                    viento_real=0,  # Simplificado para el test
                                    leido_en_zona=sesion.zona_referencia
                                )
                                self.stdout.write(
                                    self.style.SUCCESS(f"🎯 VIERNES CAZADO para {sesion.alias} a las {dt.hour}:00"))
                                break

            except Exception as e:
                self.stdout.write(f"Error: {e}")