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
import minsa.formulario.DataSet.TestInformationF200DataSet;
import minsa.formulario.R;

public class TestInformationF200Adapter extends RecyclerView.Adapter<minsa.formulario.Adapters.recyclerview.TestInformationF200Adapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<TestInformationF200DataSet> lista;

    public TestInformationF200Adapter(Context context, List<TestInformationF200DataSet> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_item_test_information_f200, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTypeTest) TextView tvTypeTest;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvResult) TextView tvResult;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }

        public void bind(Context context, TestInformationF200DataSet item) {
            tvTypeTest.setText("Tipo de Prueba: " + item.getType_test());
            tvDate.setText("Fecha: " + item.getDate());
            tvResult.setText("Resultado: " + item.getResult());
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    public void setOnLongClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.TestInformationF200Adapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        minsa.formulario.Adapters.recyclerview.TestInformationF200Adapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}