from rest_framework import serializers
from .models import  SesionIdeal, Match


class SurfistaSerializer(serializers.ModelSerializer):
    class Meta:
        fields = '__all__'

class SesionIdealSerializer(serializers.ModelSerializer):
    class Meta:
        model = SesionIdeal
        fields = '__all__'

class MatchSerializer(serializers.ModelSerializer):
    alias_sesion = serializers.ReadOnlyField(source='sesion_ideal.alias')
    fecha_hora_match = serializers.DateTimeField(format="%Y-%m-%dT%H:%M:%S")

    class Meta:
        model = Match
        fields = '__all__'