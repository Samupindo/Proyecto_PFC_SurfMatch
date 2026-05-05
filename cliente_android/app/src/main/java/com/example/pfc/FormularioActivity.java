package com.example.pfc;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pfc.api.DjangoApi;
import com.example.pfc.api.RetrofitClient;
import com.example.pfc.api.SesionIdeal;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioActivity extends AppCompatActivity {

    private EditText etAlias, etFecha, etTamano, etPeriodo, etMarea, etVientoDir, etVientoEst;
    private Spinner spinnerZona;
    private Button btnGuardar, btnCancelar;
    String miUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
        btnCancelar = findViewById(R.id.btnCancelar); // Nuevo botón

        String[] zonas = {"A Coruña / Norte", "Nemiña / Costa expuesta", "Carnota", "Rías Baixas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, zonas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZona.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> guardarSesion());

        btnCancelar.setOnClickListener(v -> intentarSalir());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                intentarSalir();
            }
        });
    }

    private void intentarSalir() {
        if (hayCambiosSinGuardar()) {
            mostrarDialogoConfirmacion();
        } else {
            finish();
        }
    }

    private boolean hayCambiosSinGuardar() {
        return !etAlias.getText().toString().trim().isEmpty() ||
                !etFecha.getText().toString().trim().isEmpty() ||
                !etTamano.getText().toString().trim().isEmpty() ||
                !etPeriodo.getText().toString().trim().isEmpty();
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Descartar cambios")
                .setMessage("¿Estás seguro de que quieres salir? Se perderán los datos introducidos.")
                .setPositiveButton("Descartar", (dialog, which) -> finish()) // Sale de la Activity
                .setNegativeButton("Seguir editando", null) // Cierra el diálogo y se queda
                .show();
    }

    private void guardarSesion() {
        String zonaSeleccionada = spinnerZona.getSelectedItem().toString();

        SesionIdeal nuevaSesion = new SesionIdeal(
                etAlias.getText().toString(),
                etFecha.getText().toString(),
                zonaSeleccionada,
                etTamano.getText().toString(),
                etPeriodo.getText().toString(),
                etMarea.getText().toString(),
                etVientoDir.getText().toString(),
                etVientoEst.getText().toString(),
                miUid
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