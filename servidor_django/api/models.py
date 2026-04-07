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

    # Aquí están los nuevos campos
    tamano = models.DecimalField(max_digits=4, decimal_places=2)  # Ej: 2.10
    periodo = models.IntegerField()  # Ej: 13

    marea = models.CharField(max_length=50)
    direccion_viento = models.CharField(max_length=50)
    estado_viento = models.CharField(max_length=50)

    def __str__(self):
        return f"{self.alias} ({self.tamano}m @ {self.periodo}s)"