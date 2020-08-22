package minsa.formulario.Adapters.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.DataSet.SeveriDataSet;
import minsa.formulario.R;

public class SeveridadDialogRecyclerViewAdapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.SeveridadDialogRecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<SeveriDataSet> lista;

    public SeveridadDialogRecyclerViewAdapter(Context context, List<SeveriDataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_severidad_dialog, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvNombre) TextView tvNombre;
        @BindView(R.id.tvDescripcion) TextView tvDescripcion;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bind(Context context, SeveriDataSet item) {
            tvNombre.setText(item.getDescripcion());
            tvDescripcion.setText(item.getDetalle());
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.SeveridadDialogRecyclerViewAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.SeveridadDialogRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}