package minsa.formulario.UI.Fragments.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.Adapters.recyclerview.ConglomeradoDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.ConglomeradoDataSet;
import minsa.formulario.DbHelper.ConglomeradoDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class ConglomeradoDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<ConglomeradoDataSet> original = new ArrayList<>();
    private List<ConglomeradoDataSet> lista = new ArrayList<>();

    private ConglomeradoDialogRecyclerViewAdapter adapter;

    public ConglomeradoDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface ConglomeradoDialogListener {
        void setConglomerado(ConglomeradoDataSet dataSet);
    }

    public static ConglomeradoDialogFragment newInstance(String id_tipo_conglomerado, String ubigeo) {
        ConglomeradoDialogFragment f = new ConglomeradoDialogFragment();
        Bundle args = new Bundle();
        args.putString("id_tipo_conglomerado", id_tipo_conglomerado);
        args.putString("ubigeo", ubigeo);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_conglomerado_dialog, null);

        ButterKnife.bind(this, view);

        mPrinciDbHelper = new PrinciDbHelper(getContext());

        svSearch.requestFocus();

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lista.clear();

                for(ConglomeradoDataSet bean : original) {
                    if(bean.getNombre().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorTipDoc = mPrinciDbHelper.getAllPrinciTwo(
                ConglomeradoDbHelper.TableC.TableN,
                ConglomeradoDbHelper.TableC.id_tipo_conglomerado,
                getArguments().getString("id_tipo_conglomerado", ""),
                ConglomeradoDbHelper.TableC.ubigeo,
                getArguments().getString("ubigeo", "")
        );
        while(vCursorTipDoc.moveToNext()) {
            ConglomeradoDataSet vDstDepart = new ConglomeradoDataSet();
            vDstDepart.setId(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(ConglomeradoDbHelper.TableC.id)));
            vDstDepart.setNombre(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(ConglomeradoDbHelper.TableC.nombre)));
            lista.add(vDstDepart);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new ConglomeradoDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ConglomeradoDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setConglomerado(lista.get(position));
                }
                dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme)
                .setNegativeButton("Cerrar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setView(view)
                .setCancelable(false)
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}