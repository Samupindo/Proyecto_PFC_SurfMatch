package com.example.pfc.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DjangoApi {

    // GET: Obtener la lista de todas las sesiones guardadas
    @GET("sesiones/")
    Call<List<SesionIdeal>> getSesiones();

    // POST: Enviar una nueva sesión al servidor para guardarla
    @POST("sesiones/")
    Call<SesionIdeal> crearSesion(@Body SesionIdeal sesion);

    // PUT: Actualizar una sesión existente buscando por su ID
    @PUT("sesiones/{id}/")
    Call<SesionIdeal> actualizarSesion(@Path("id") int id, @Body SesionIdeal sesion);

    // DELETE: Borrar una sesión específica por su ID
    @DELETE("sesiones/{id}/")
    Call<Void> borrarSesion(@Path("id") int id);

    @GET("matches/")
    Call<List<Match>> getMatches();
    // Este método actualiza una sesión existente basándose en su ID

    @DELETE("sesiones/{id}/")
    Call<Void> eliminarSesion(@Path("id") int id);

}