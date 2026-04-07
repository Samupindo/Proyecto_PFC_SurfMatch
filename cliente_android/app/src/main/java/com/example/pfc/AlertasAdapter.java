package com.example.pfc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfc.api.Match;

import java.util.List;

public class AlertasAdapter extends RecyclerView.Adapter<AlertasAdapter.ViewHolder> {

    private List<Match> listaMatches;

    public AlertasAdapter(List<Match> listaMatches) {
        this.listaMatches = listaMatches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match m = listaMatches.get(position);

        holder.tvTitulo.setText("¡HAY BAÑO!: " + m.getAlias_sesion());
        holder.tvFecha.setText("Día previsto: " + m.getFecha_hora_match().replace("T", " "));
        holder.tvDetalles.setText("Zona: " + m.getLeido_en_zona() + " | Olas: " + m.getAltura_real() + "m | Periodo: " + m.getPeriodo_real() + "s");
    }

    @Override
    public int getItemCount() {
        return listaMatches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha, tvDetalles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTituloMatch);
            tvFecha = itemView.findViewById(R.id.tvFechaMatch);
            tvDetalles = itemView.findViewById(R.id.tvDetallesMatch);
        }
    }
}