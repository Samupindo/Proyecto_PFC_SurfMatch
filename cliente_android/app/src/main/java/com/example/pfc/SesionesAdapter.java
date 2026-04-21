package com.example.pfc;

import android.content.Intent;
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

    public SesionesAdapter(List<SesionIdeal> listaSesiones) {
        this.listaSesiones = listaSesiones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sesion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SesionIdeal sesion = listaSesiones.get(position);

        holder.tvAlias.setText(sesion.getAlias());

        String detalles = sesion.getTamano() + "m | " +
                sesion.getPeriodo() + "s | " +
                sesion.getDireccion_viento();
        holder.tvDetalles.setText(detalles);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetalleSesionActivity.class);
            intent.putExtra("id", sesion.getId());
            intent.putExtra("alias", sesion.getAlias());
            intent.putExtra("fecha", sesion.getFecha_referencia());
            intent.putExtra("zona", sesion.getZona_referencia());
            intent.putExtra("tamano", sesion.getTamano());
            intent.putExtra("periodo", sesion.getPeriodo());
            intent.putExtra("marea", sesion.getMarea());
            intent.putExtra("viento_dir", sesion.getDireccion_viento());
            intent.putExtra("viento_est", sesion.getEstado_viento());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaSesiones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAlias, tvDetalles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlias = itemView.findViewById(R.id.tvAlias);
            tvDetalles = itemView.findViewById(R.id.tvDetalles);
        }
    }
}