package com.example.pfc;

import android.content.Intent;
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

public class DetalleSesionActivity extends AppCompatActivity {

    private EditText etAlias, etFecha, etTamano, etPeriodo, etMarea, etVientoDir, etVientoEst;
    private Spinner spinnerZona;
    private Button btnGuardar, btnCancelar;
    private int sesionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_sesion);

        etAlias = findViewById(R.id.etAlias);
        etFecha = findViewById(R.id.etFecha);
        spinnerZona = findViewById(R.id.spinnerZona);
        etTamano = findViewById(R.id.etTamano);
        etPeriodo = findViewById(R.id.etPeriodo);
        etMarea = findViewById(R.id.etMarea);
        etVientoDir = findViewById(R.id.etVientoDir);
        etVientoEst = findViewById(R.id.etVientoEst);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnGuardar.setText("Actualizar Cambios");

        String[] zonas = {"A Coruña / Norte", "Nemiña / Costa expuesta", "Carnota", "Rías Baixas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zonas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZona.setAdapter(adapter);

        // 3. Recibir los datos de la tarjeta que hemos tocado
        Intent intent = getIntent();
        sesionId = intent.getIntExtra("id", -1);
        etAlias.setText(intent.getStringExtra("alias"));
        etFecha.setText(intent.getStringExtra("fecha"));
        etTamano.setText(intent.getStringExtra("tamano"));
        etPeriodo.setText(String.valueOf(intent.getIntExtra("periodo", 0)));
        etMarea.setText(intent.getStringExtra("marea"));
        etVientoDir.setText(intent.getStringExtra("viento_dir"));
        etVientoEst.setText(intent.getStringExtra("viento_est"));

        String zonaGuardada = intent.getStringExtra("zona");
        for (int i = 0; i < zonas.length; i++) {
            if (zonas[i].equals(zonaGuardada)) {
                spinnerZona.setSelection(i);
                break;
            }
        }

        btnCancelar.setOnClickListener(v -> finish());
        btnGuardar.setOnClickListener(v -> actualizarSesion());
    }

    private void actualizarSesion() {
        String zonaSeleccionada = spinnerZona.getSelectedItem().toString();

        SesionIdeal sesionModificada = new SesionIdeal(
                etAlias.getText().toString(),
                etFecha.getText().toString(),
                zonaSeleccionada,
                etTamano.getText().toString(),
                etPeriodo.getText().toString(),
                etMarea.getText().toString(),
                etVientoDir.getText().toString(),
                etVientoEst.getText().toString(),
                1 // Aquí deberías poner el ID de usuario real de Firebase en el futuro
        );

        DjangoApi api = RetrofitClient.getApi();
        Call<SesionIdeal> call = api.actualizarSesion(sesionId, sesionModificada);

        call.enqueue(new Callback<SesionIdeal>() {
            @Override
            public void onResponse(Call<SesionIdeal> call, Response<SesionIdeal> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DetalleSesionActivity.this, "¡Sesión actualizada!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetalleSesionActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    Log.e("API_PUT", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SesionIdeal> call, Throwable t) {
                Toast.makeText(DetalleSesionActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}