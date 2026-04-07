package com.example.pfc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pfc.api.DjangoApi;
import com.example.pfc.api.RetrofitClient;
import com.example.pfc.api.SesionIdeal;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesionesFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sesiones, container, false);
        recyclerView = view.findViewById(R.id.rvSesiones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarDatos();
    }

    private void cargarDatos() {
        DjangoApi api = RetrofitClient.getApi();
        api.getSesiones().enqueue(new Callback<List<SesionIdeal>>() {
            @Override
            public void onResponse(Call<List<SesionIdeal>> call, Response<List<SesionIdeal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setAdapter(new SesionesAdapter(response.body()));
                }
            }
            @Override public void onFailure(Call<List<SesionIdeal>> call, Throwable t) {}
        });
    }
}