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
import minsa.formulario.DataSet.DepartDataSet;
import minsa.formulario.R;

public class DepartamentoDialogRecyclerViewAdapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.DepartamentoDialogRecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<DepartDataSet> lista;

    public DepartamentoDialogRecyclerViewAdapter(Context context, List<DepartDataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_tip_doc_dialog, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvNombre) TextView tvNombre;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bind(Context context, DepartDataSet item) {
            tvNombre.setText(item.getDepartNombre());
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.DepartamentoDialogRecyclerViewAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.DepartamentoDialogRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}