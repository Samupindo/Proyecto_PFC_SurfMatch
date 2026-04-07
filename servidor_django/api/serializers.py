from rest_framework import serializers
from .models import Surfista, SesionIdeal, Match


class SurfistaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Surfista
        fields = '__all__' # Convierte todos los campos del modelo

class SesionIdealSerializer(serializers.ModelSerializer):
    class Meta:
        model = SesionIdeal
        fields = '__all__'

class MatchSerializer(serializers.ModelSerializer):
    alias_sesion = serializers.ReadOnlyField(source='sesion_ideal.alias')
    # Añadimos esta línea para formatear la fecha manualmente y evitar el error de renderizado
    fecha_hora_match = serializers.DateTimeField(format="%Y-%m-%dT%H:%M:%S")

    class Meta:
        model = Match
        fields = '__all__'