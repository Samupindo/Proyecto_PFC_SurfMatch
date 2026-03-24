from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import SurfistaViewSet, SesionIdealViewSet

# El router crea automáticamente las URLs para los ViewSets
router = DefaultRouter()
router.register(r'surfistas', SurfistaViewSet)
router.register(r'sesiones', SesionIdealViewSet)

urlpatterns = [
    path('', include(router.urls)),
]