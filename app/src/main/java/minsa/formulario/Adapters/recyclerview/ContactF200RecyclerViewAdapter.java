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
import minsa.formulario.DataSet.ContactF200DataSet;
import minsa.formulario.R;

public class ContactF200RecyclerViewAdapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.ContactF200RecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<ContactF200DataSet> lista;

    public ContactF200RecyclerViewAdapter(Context context, List<ContactF200DataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_item_contact_f200, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvYear) TextView tvYear;
        @BindView(R.id.tvTitleRiesgo) TextView tvTitleRiesgo;
        @BindView(R.id.tvRiesgo) TextView tvRiesgo;
        @BindView(R.id.tvParentesco) TextView tvParentesco;
        @BindView(R.id.ivTrash) ImageView ivTrash;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            ivTrash.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        public void bind(Context context, ContactF200DataSet item) {
            tvName.setText(item.getName() + " " + item.getPaternal() + " " + item.getMaternal());

            if(item.getDate_birth().isEmpty()) {
                tvYear.setVisibility(View.GONE);
            } else {
                tvYear.setVisibility(View.VISIBLE);
            }

            tvYear.setText("Edad: " + item.getEdad());

            String riesgo = "";
            for(int i=0; i < item.getRiskFactors().size(); i++) {
                if(i == item.getRiskFactors().size()-1) {
                    riesgo += item.getRiskFactors().get(i).getRisk_factor() + "\n";
                } else {
                    riesgo += item.getRiskFactors().get(i).getRisk_factor() + "\n";
                }
            }

            tvRiesgo.setText(riesgo);
            tvParentesco.setText("Parentesco: " + item.getRelationship());
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.ContactF200RecyclerViewAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.ContactF200RecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}