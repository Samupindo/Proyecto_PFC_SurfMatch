from rest_framework import viewsets
from .models import Surfista, SesionIdeal, Match # Añadimos Match
from .serializers import SurfistaSerializer, SesionIdealSerializer, MatchSerializer # Añadimos MatchSerializer

class SurfistaViewSet(viewsets.ModelViewSet):
    queryset = Surfista.objects.all()
    serializer_class = SurfistaSerializer

class SesionIdealViewSet(viewsets.ModelViewSet):
    queryset = SesionIdeal.objects.all()
    serializer_class = SesionIdealSerializer

class MatchViewSet(viewsets.ModelViewSet):
    # Traemos todos los matches, ordenados por fecha (el más reciente primero)
    queryset = Match.objects.all().order_by('-fecha_hora_match')
    serializer_class = MatchSerializer