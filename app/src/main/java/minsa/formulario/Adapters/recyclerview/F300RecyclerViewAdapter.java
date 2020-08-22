package minsa.formulario.Adapters.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.DataSet.F300DataSet;
import minsa.formulario.R;
import minsa.formulario.Util.Utils;

public class F300RecyclerViewAdapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.F300RecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<F300DataSet> lista;

    public F300RecyclerViewAdapter(Context context, List<F300DataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_item_f300, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvEvolucion) TextView tvEvolucion;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvPresionArterial) TextView tvPresionArterial;
        @BindView(R.id.tvFrecuenciaCardiaca) TextView tvFrecuenciaCardiaca;
        @BindView(R.id.tvFrecuenciaRespiratoria) TextView tvFrecuenciaRespiratoria;
        @BindView(R.id.ivEdit) ImageView ivEdit;
        @BindView(R.id.ivSend) ImageView ivSend;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }

        public void bind(Context context, F300DataSet item) {
            tvEvolucion.setText("Evolución: " + item.getEvolucion());
            tvDate.setText("Fecha: " + Utils.changeDateFormat(item.getFecha(), "yyyy-MM-dd", "dd/MM/yyyy"));
            tvPresionArterial.setText("Presión Arterial: " + item.getPresion_arterial() + " / " + item.getPresion_arterial_2());
            tvFrecuenciaCardiaca.setText("Frecuencia Cardiaca: " + item.getFrecuencia_cardiaca());
            tvFrecuenciaRespiratoria.setText("Frecuencia Respiratoria: " + item.getFrecuencia_respiratoria());

            if(item.getId_f300() > 0) {
                ivEdit.setVisibility(View.GONE);
                ivSend.setVisibility(View.VISIBLE);
            } else {
                ivEdit.setVisibility(View.VISIBLE);
                ivSend.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.F300RecyclerViewAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.F300RecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}