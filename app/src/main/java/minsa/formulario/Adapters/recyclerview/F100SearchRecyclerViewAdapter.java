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
import minsa.formulario.DataSet.F100DataSet;
import minsa.formulario.R;
import minsa.formulario.Util.Utils;

public class F100SearchRecyclerViewAdapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.F100SearchRecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<F100DataSet> lista;

    public F100SearchRecyclerViewAdapter(Context context, List<F100DataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_item_f100_search, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvNombre) TextView tvNombre;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.ivEdit) ImageView ivEdit;
        @BindView(R.id.ivSend) ImageView ivSend;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }

        public void bind(Context context, F100DataSet item) {
            tvNombre.setText(item.getPaciente());
            tvDate.setText("Fecha de ejecución: " + Utils.changeDateFormat(item.getFecha(), "yyyy-MM-dd", "dd/MM/yyyy"));

            if(item.getId_f100() > 0) {
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
        minsa.formulario.Adapters.recyclerview.F100SearchRecyclerViewAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.F100SearchRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}