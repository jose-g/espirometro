package minsa.formulario.Adapters.recyclerview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.DataSet.ViajeF200DataSet;
import minsa.formulario.R;

public class ViajeRecyclerViewAdapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.ViajeRecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<ViajeF200DataSet> lista;

    public ViajeListener delegate;

    public interface ViajeListener {
        void setCiudad(int position, String ciudad);
    }

    public ViajeRecyclerViewAdapter(Context context, List<ViajeF200DataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_item_viaje, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.rlContentCountry) RelativeLayout rlContentCountry;
        @BindView(R.id.tvTitleCountry) TextView tvTitleCountry;
        @BindView(R.id.tvCountry) TextView tvCountry;
        @BindView(R.id.tietCiudad) TextInputEditText tietCiudad;
        @BindView(R.id.ivTrash) ImageView ivTrash;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            ivTrash.setOnClickListener(this);
            rlContentCountry.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        public void bind(Context context, ViajeF200DataSet item) {
            int i = (getAdapterPosition() + 1);

            if(getAdapterPosition() == 0) {
                ivTrash.setVisibility(View.GONE);
            } else {
                ivTrash.setVisibility(View.VISIBLE);
            }

            if(i == 1) {
                tvTitle.setText("Primer viaje");
            } else if(i == 2) {
                tvTitle.setText("Segundo viaje");
            } else if(i == 3) {
                tvTitle.setText("Tercer viaje");
            }

            tvTitleCountry.setText("Pais " + (i == 1 ? "(*)" : ""));

            if(item.getPais().isEmpty()) {
                tvCountry.setText("Seleccionar");
            } else {
                tvCountry.setText(item.getPais());
                tvCountry.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            }

            tietCiudad.setText(item.getCiudad());

            tietCiudad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    delegate.setCiudad(getAdapterPosition(), s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.ViajeRecyclerViewAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.ViajeRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}