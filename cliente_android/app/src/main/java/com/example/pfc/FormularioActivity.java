package com.example.pfc;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pfc.api.DjangoApi;
import com.example.pfc.api.RetrofitClient;
import com.example.pfc.api.SesionIdeal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioActivity extends AppCompatActivity {

    private EditText etAlias, etFecha, etTamano, etPeriodo, etMarea, etVientoDir, etVientoEst;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etAlias = findViewById(R.id.etAlias);
        etFecha = findViewById(R.id.etFecha);
        etTamano = findViewById(R.id.etTamano);
        etPeriodo = findViewById(R.id.etPeriodo);
        etMarea = findViewById(R.id.etMarea);
        etVientoDir = findViewById(R.id.etVientoDir);
        etVientoEst = findViewById(R.id.etVientoEst);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> guardarSesion());
    }

    private void guardarSesion() {
        SesionIdeal nuevaSesion = new SesionIdeal(
                etAlias.getText().toString(),
                etFecha.getText().toString(),
                etTamano.getText().toString(),
                etPeriodo.getText().toString(),
                etMarea.getText().toString(),
                etVientoDir.getText().toString(),
                etVientoEst.getText().toString(),
                1
        );

        DjangoApi api = RetrofitClient.getApi();
        Call<SesionIdeal> call = api.crearSesion(nuevaSesion);

        call.enqueue(new Callback<SesionIdeal>() {
            @Override
            public void onResponse(Call<SesionIdeal> call, Response<SesionIdeal> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FormularioActivity.this, "¡Sesión guardada!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(FormularioActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    Log.e("API_POST", "Error código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SesionIdeal> call, Throwable t) {
                Toast.makeText(FormularioActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}