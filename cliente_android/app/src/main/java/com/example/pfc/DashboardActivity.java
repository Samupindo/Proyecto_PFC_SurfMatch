package com.example.pfc;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pfc.api.DjangoApi;
import com.example.pfc.api.RetrofitClient;
import com.example.pfc.api.SesionIdeal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "PruebaAPI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        obtenerSesionesDelServidor();
    }

    private void obtenerSesionesDelServidor() {
        DjangoApi api = RetrofitClient.getApi();
        Call<List<SesionIdeal>> call = api.getSesiones();

        call.enqueue(new Callback<List<SesionIdeal>>() {
            @Override
            public void onResponse(Call<List<SesionIdeal>> call, Response<List<SesionIdeal>> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Error del servidor HTTP: " + response.code());
                    return;
                }

                List<SesionIdeal> sesiones = response.body();
                if (sesiones != null && !sesiones.isEmpty()) {
                    for (SesionIdeal sesion : sesiones) {
                        Log.d(TAG, "¡ÉXITO! Recibido: " + sesion.getAlias() +
                                " | Tamaño: " + sesion.getTamano() + "m | Período: " +
                                sesion.getPeriodo() + "s");
                    }
                } else {
                    Log.d(TAG, "Conectado, pero no hay sesiones guardadas.");
                }
            }

            @Override
            public void onFailure(Call<List<SesionIdeal>> call, Throwable t) {
                Log.e(TAG, "Fallo al conectar con Django: " + t.getMessage());
            }
        });
    }
}