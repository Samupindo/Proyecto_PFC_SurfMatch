from rest_framework import viewsets
from .models import Surfista, SesionIdeal
from .serializers import SurfistaSerializer, SesionIdealSerializer

class SurfistaViewSet(viewsets.ModelViewSet):
    queryset = Surfista.objects.all()
    serializer_class = SurfistaSerializer

class SesionIdealViewSet(viewsets.ModelViewSet):
    queryset = SesionIdeal.objects.all()
    serializer_class = SesionIdealSerializer