package com.example.pfc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfc.api.SesionIdeal;

import java.util.List;

public class SesionesAdapter extends RecyclerView.Adapter<SesionesAdapter.ViewHolder> {

    private List<SesionIdeal> listaSesiones;

    // Constructor: recibe la lista de datos cuando creamos el adaptador
    public SesionesAdapter(List<SesionIdeal> listaSesiones) {
        this.listaSesiones = listaSesiones;
    }

    // 1. Infla el diseño de la tarjetita XML que creamos antes
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sesion, parent, false);
        return new ViewHolder(view);
    }

    // 2. Rellena cada tarjetita con los datos de la sesión correspondiente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SesionIdeal sesion = listaSesiones.get(position);

        // Ponemos el título
        holder.tvAlias.setText(sesion.getAlias());

        // Construimos la línea de detalles (ej: "2.0m | 13s | NW")
        String detalles = sesion.getTamano() + "m | " +
                sesion.getPeriodo() + "s | " +
                sesion.getDireccion_viento();
        holder.tvDetalles.setText(detalles);
    }

    // 3. Le dice al RecyclerView cuántas tarjetas tiene que dibujar en total
    @Override
    public int getItemCount() {
        return listaSesiones.size();
    }

    // Clase interna que enlaza los elementos visuales de una tarjeta
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAlias, tvDetalles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlias = itemView.findViewById(R.id.tvAlias);
            tvDetalles = itemView.findViewById(R.id.tvDetalles);
        }
    }
}