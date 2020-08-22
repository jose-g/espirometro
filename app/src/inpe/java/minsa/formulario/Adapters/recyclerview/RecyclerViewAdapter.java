package minsa.formulario.Adapters.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import minsa.formulario.DataSet.DatoPacienteDataSet;
import minsa.formulario.DataSet.RecyInterDataSet;
import minsa.formulario.R;
import minsa.formulario.UI.Activity.SpinnerDialog;
import minsa.formulario.UI.Fragments.InboxFragment;
import minsa.formulario.Util.Utils;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;

    private Context context;
    private List<RecyInterDataSet> lista;
    private List<RecyInterDataSet> itemsCopy;

    SpinnerDialog mSpinnerDialog;

    public RecyclerViewAdapter(Context context, List<RecyInterDataSet> lista) {
        this.context = context;
        this.lista = lista;
        //this.itemsCopy.addAll(lista);
        itemsCopy = new ArrayList<>();
        //this.itemsCopy = lista;
        itemsCopy.addAll(lista);
        Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath4:" + "Valor-->:" + lista.size());
        Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath4:" + "Valor-->:" + itemsCopy.size());
        Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->lista:" + "Valor-->:" + lista.toString());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(context, lista.get(i));
    }

    public void filter(String text) {


        this.lista.clear();
        Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath:" + "Valor-->:" + lista.size());
        Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath:" + "Valor-->:" + itemsCopy.size());
        if(text.isEmpty()){

            this.lista.addAll(itemsCopy);

            Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath:2" + "Valor-->:" + lista.size());
        } else{
            Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath3:" + "Valor-->:" + lista.size());
            Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath3:" + "Valor-->:" + itemsCopy.size());
            text = text.toLowerCase();
            for(RecyInterDataSet item: itemsCopy){
                if(item.getApellidos().toLowerCase().contains(text) || item.getNombres().toLowerCase().contains(text)){
                    lista.add(item);
                }
            }
        }

        Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath5:" + "Valor-->:" + lista.size());
        Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath55:" + "Valor-->:" + itemsCopy.size());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvResTitulo) TextView C_apellido;
        @BindView(R.id.tvResNombres) TextView C_nombres;
        @BindView(R.id.tvResCodigo) TextView C_dni;
        @BindView(R.id.tvResArchivo) TextView tipo_dni;
        @BindView(R.id.imgResEstado) ImageView imgResEstado;
        @BindView(R.id.img_row_delete) ImageView img_row_delete;
        @BindView(R.id.img_row_add) ImageView img_row_add;
        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);
            ButterKnife.bind(this.img_row_add, v);

            v.setOnClickListener(this);
            img_row_add.setOnClickListener(this);

            this.img_row_add.setTag(this);
            itemView.setTag(this);

        }



        public void bind(Context context, RecyInterDataSet item) {
            C_apellido.setText(item.getApellidos()); //tipo
            C_nombres.setText(item.getNombres()); //nombre interno
            C_dni.setText(item.getDoc()); //codigo
            tipo_dni.setText(item.getTipo_doc()); //archivo

            if (item.getEstado().equals("1")) {
                imgResEstado.setImageResource(R.drawable.pendingfile2);
                img_row_delete.setImageResource(R.drawable.read1);
                C_apellido.setTypeface(null, Typeface.BOLD);      // for Normal Text
                //C_nombres.setTypeface(null, Typeface.BOLD);        // for Bold only.
            } else if (item.getEstado().equals("2")) {
                imgResEstado.setImageResource(R.drawable.aclararfile);
                img_row_delete.setImageResource(R.drawable.dobletick);
                C_apellido.setTypeface(null, Typeface.NORMAL);      // for Normal Text
                //C_nombres.setTypeface(null, Typeface.BOLD);        // for Bold only.
            }
            else if (item.getEstado().equals("3")) {
                imgResEstado.setImageResource(R.drawable.confirmfile);
                img_row_delete.setImageResource(R.drawable.dobletick);
                C_apellido.setTypeface(null, Typeface.NORMAL);      // for Normal Text
            }
            else if (item.getEstado().equals("4")) {
                imgResEstado.setImageResource(R.drawable.pendingfile2);
                img_row_delete.setImageResource(R.drawable.read1);
                C_apellido.setTypeface(null, Typeface.BOLD);
            }
            else if (item.getEstado().equals("5")) {
                imgResEstado.setImageResource(R.drawable.returnfile);
                img_row_delete.setImageResource(R.drawable.dobletick);
                C_apellido.setTypeface(null, Typeface.NORMAL);      // for Normal Text
            }
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);

        }



    }

    public void setOnLongClickListener(ClickListener clickListener) {
        RecyclerViewAdapter.clickListener = clickListener;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}