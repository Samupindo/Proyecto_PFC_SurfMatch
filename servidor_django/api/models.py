from django.db import models


class Surfista(models.Model):
    firebase_uid = models.CharField(max_length=100, unique=True)
    nombre_usuario = models.CharField(max_length=50)

    def __str__(self):
        return self.nombre_usuario


class SesionIdeal(models.Model):
    surfista = models.ForeignKey(Surfista, on_delete=models.CASCADE)
    alias = models.CharField(max_length=100)
    fecha_referencia = models.DateField()

    # Añadimos la zona de referencia
    zona_referencia = models.CharField(max_length=50, default="Costa da Morte")

    tamano = models.DecimalField(max_digits=4, decimal_places=2)
    periodo = models.IntegerField()
    marea = models.CharField(max_length=50)
    direccion_viento = models.CharField(max_length=50)
    estado_viento = models.CharField(max_length=50)

    def __str__(self):
        return f"{self.alias} ({self.zona_referencia} | {self.tamano}m @ {self.periodo}s)"

class Match(models.Model):
    sesion_ideal = models.ForeignKey(SesionIdeal, on_delete=models.CASCADE)
    fecha_hora_match = models.DateTimeField()
    altura_real = models.DecimalField(max_digits=4, decimal_places=2)
    periodo_real = models.IntegerField()
    viento_real = models.DecimalField(max_digits=4, decimal_places=2)
    leido_en_zona = models.CharField(max_length=100)
    creado_el = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return f"Match: {self.sesion_ideal.alias} el {self.fecha_hora_match}"