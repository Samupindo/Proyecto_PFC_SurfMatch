from rest_framework import viewsets
from .models import SesionIdeal, Match
from .serializers import SesionIdealSerializer, MatchSerializer

class SesionIdealViewSet(viewsets.ModelViewSet):
    serializer_class = SesionIdealSerializer
    queryset = SesionIdeal.objects.all()

    def get_queryset(self):
        uid_firebase = self.request.query_params.get('surfista', None)
        if uid_firebase is not None:
            return SesionIdeal.objects.filter(surfista=uid_firebase)
        return SesionIdeal.objects.all()

class MatchViewSet(viewsets.ModelViewSet):
    queryset = Match.objects.all().order_by('fecha_hora_match')
    serializer_class = MatchSerializer