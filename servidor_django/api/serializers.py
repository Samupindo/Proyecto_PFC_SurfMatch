from rest_framework import serializers
from .models import Surfista, SesionIdeal

class SurfistaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Surfista
        fields = '__all__' # Convierte todos los campos del modelo

class SesionIdealSerializer(serializers.ModelSerializer):
    class Meta:
        model = SesionIdeal
        fields = '__all__'