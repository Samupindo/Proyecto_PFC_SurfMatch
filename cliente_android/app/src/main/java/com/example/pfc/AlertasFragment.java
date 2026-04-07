package com.example.pfc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pfc.api.DjangoApi;
import com.example.pfc.api.RetrofitClient;
import com.example.pfc.api.Match;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertasFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alertas, container, false);
        recyclerView = view.findViewById(R.id.rvAlertas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarAlertas();
    }

    private void cargarAlertas() {
        DjangoApi api = RetrofitClient.getApi();
        api.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Aquí necesitarás crear un AlertasAdapter similar al SesionesAdapter
                    // Por ahora, si quieres probar, crea el AlertasAdapter basándote en el otro
                    recyclerView.setAdapter(new AlertasAdapter(response.body()));
                }
            }
            @Override public void onFailure(Call<List<Match>> call, Throwable t) {}
        });
    }
}