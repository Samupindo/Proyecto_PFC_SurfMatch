from django.db import models


class Surfista(models.Model):
    # Guardaremos el ID que nos devuelva Firebase para enlazar las cuentas
    firebase_uid = models.CharField(max_length=128, unique=True)
    nombre_usuario = models.CharField(max_length=100)

    def __str__(self):
        return self.nombre_usuario


class SesionIdeal(models.Model):
    # Relación 1:N -> Un surfista puede tener varias sesiones ideales guardadas
    surfista = models.ForeignKey(Surfista, on_delete=models.CASCADE, related_name='sesiones')

    # Datos para identificar la sesión
    alias = models.CharField(max_length=100)  # Ej: "Baño épico de invierno"
    fecha_referencia = models.DateField(null=True, blank=True)  # Ej: 2026-02-07

    # Parámetros a monitorizar con Open-Meteo
    swell_minimo = models.DecimalField(max_digits=4, decimal_places=2)  # Ej: 4.00
    swell_maximo = models.DecimalField(max_digits=4, decimal_places=2)  # Ej: 5.00
    marea = models.CharField(max_length=50)  # Ej: "Rising"
    direccion_viento = models.CharField(max_length=50)  # Ej: "North"
    estado_viento = models.CharField(max_length=50, blank=True)  # Ej: "Glassy"

    def __str__(self):
        return f"{self.alias} ({self.swell_minimo}m - {self.swell_maximo}m)"