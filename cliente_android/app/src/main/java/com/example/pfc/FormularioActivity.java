package com.example.pfc;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Spinner spinnerZona;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etAlias = findViewById(R.id.etAlias);
        etFecha = findViewById(R.id.etFecha);
        spinnerZona = findViewById(R.id.spinnerZona);
        etTamano = findViewById(R.id.etTamano);
        etPeriodo = findViewById(R.id.etPeriodo);
        etMarea = findViewById(R.id.etMarea);
        etVientoDir = findViewById(R.id.etVientoDir);
        etVientoEst = findViewById(R.id.etVientoEst);
        btnGuardar = findViewById(R.id.btnGuardar);

        // 1. Configuramos las opciones del menú desplegable
        String[] zonas = {"A Coruña / Norte", "Nemiña / Costa expuesta", "Carnota", "Rías Baixas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zonas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZona.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> guardarSesion());
    }

    private void guardarSesion() {
        // 2. Leemos la zona que el usuario tiene seleccionada en el menú
        String zonaSeleccionada = spinnerZona.getSelectedItem().toString();

        // 3. La añadimos al crear la sesión
        SesionIdeal nuevaSesion = new SesionIdeal(
                etAlias.getText().toString(),
                etFecha.getText().toString(),
                zonaSeleccionada,
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