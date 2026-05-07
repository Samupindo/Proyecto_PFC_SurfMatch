# SurfMatch 🌊 
### Sistema Inteligente de Alertas de Surf

**SurfMatch** es una solución integral diseñada para surfistas que buscan optimizar sus sesiones en el agua. A diferencia del resto de aplicaciones con unicamente la predicción, SurfMatch permite personalizar las condiciones ideales (altura de ola, periodo y dirección de viento) para playas específicas.

El sistema utiliza un motor de procesamiento en el servidor que analiza datos en tiempo real de la API de **Open-Meteo**, calcula una puntuación de calidad (*score*) y notifica al usuario a través de una aplicación **Android** nativa cuando se detecta un "match" perfecto.

---

## 🛠️ Requisitos del Sistema

Para ejecutar este proyecto, es necesario disponer de las siguientes herramientas:

* **Python 3.12+**: Motor de ejecución del servidor.
* **Android Studio**: Versión Jellyfish o superior para el desarrollo/emulación del cliente.
* **Conectividad**: Acceso a internet para el consumo de APIs externas y Firebase.

---

## 🚀 Guía de Instalación y Arranque

El proyecto se entrega en una estructura de carpetas dividida en `servidor_django` y `cliente_android`.

### 1. Servidor (Backend Django)

1.  **Acceso**: Abre una terminal en la carpeta `servidor_django`.
2.  **Entorno Virtual**: Crea y activa un entorno para aislar las dependencias:
    * `python -m venv venv`
    * Windows: `.\venv\Scripts\activate`
    * macOS/Linux: `source venv/bin/activate`
3.  **Dependencias**: Instala los paquetes necesarios:
    ```bash
    pip install -r requirements.txt
    ```
4.  **Base de Datos**: Genera la estructura de tablas local:
    ```bash
    python manage.py migrate
    ```
5.  **Ejecución**: Inicia el servidor de desarrollo:
    ```bash
    python manage.py runserver
    ```
6.  **Sincronización de Olas**: Para activar el algoritmo de búsqueda de alertas por primera vez, ejecuta:
    ```bash
    python manage.py buscar_olas
    ```

### 2. Cliente (Frontend Android)

1.  **Importación**: Abre **Android Studio** y selecciona `Open`. Elige la carpeta `cliente_android`.
2.  **Sincronización**: Deja que **Gradle** descargue las librerías necesarias.
3.  **Configuración de Red**: El cliente está configurado para conectar con el servidor local mediante la IP `10.0.2.2` (puente estándar del emulador de Android al localhost).
4.  **Despliegue**: Conecta un dispositivo o inicia un emulador y pulsa el botón `Run`.

---

## 🏗️ Stack Tecnológico

* **Servidor**: Django Framework 5.x, Python, SQLite.
* **App Móvil**: Android Nativo (Java), Retrofit 2 (API Rest), Firebase Auth (Seguridad).
* **Datos Externos**: Marine API de Open-Meteo.

---

> **Nota de entrega**: Este repositorio contiene todos los archivos fuente, modelos de datos y la lógica de negocio asimétrica detallada en la memoria del proyecto.
