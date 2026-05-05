from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import SesionIdealViewSet, MatchViewSet

router = DefaultRouter()
router.register(r'sesiones', SesionIdealViewSet)
router.register(r'matches', MatchViewSet)

urlpatterns = [
    path('', include(router.urls)),
]