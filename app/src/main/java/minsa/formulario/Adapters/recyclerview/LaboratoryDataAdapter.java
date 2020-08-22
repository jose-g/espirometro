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
import minsa.formulario.DataSet.LaboratoryDataDataSet;
import minsa.formulario.R;
import minsa.formulario.Util.Utils;

public class LaboratoryDataAdapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.LaboratoryDataAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<LaboratoryDataDataSet> lista;

    public LaboratoryDataAdapter(Context context, List<LaboratoryDataDataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_item_laboratory_data, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTypeSample) TextView tvTypeSample;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvHour) TextView tvHour;
        @BindView(R.id.ivTrash) ImageView ivTrash;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            ivTrash.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        public void bind(Context context, LaboratoryDataDataSet item) {
            tvTypeSample.setText("Tipo de muestra: " + item.getType_sample());
            tvDate.setText("Fecha: " + Utils.changeDateFormat(item.getDate(), "yyyy-MM-dd", "dd/MM/yyyy"));
            tvHour.setText("Hora: " + item.getHour());
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.LaboratoryDataAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.LaboratoryDataAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}