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
import minsa.formulario.DataSet.CodigoPaisDataSet;
import minsa.formulario.DataSet.RecyInterDataSet;
import minsa.formulario.R;

public class ListaInternoRecyclerViewAdapter extends RecyclerView.Adapter<ListaInternoRecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<RecyInterDataSet> lista;

    public ListaInternoRecyclerViewAdapter(Context context, List<RecyInterDataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_interno_dialog, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.C_apellido) TextView C_apellido;
        @BindView(R.id.C_nombres) TextView C_nombres;
        @BindView(R.id.C_dni) TextView C_dni;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bind(Context context, RecyInterDataSet item) {
            C_apellido.setText(item.getApellidos());
            C_nombres.setText(item.getNombres());
            C_dni.setText(item.getDoc());
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        ListaInternoRecyclerViewAdapter.clickListener = clickListener;
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        ListaInternoRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}