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
import minsa.formulario.Adapters.recyclerview.CondicionEgresoDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.CondicionEgresoDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class CondicionEgresoDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<CondicionEgresoDataSet> original = new ArrayList<>();
    private List<CondicionEgresoDataSet> lista = new ArrayList<>();

    private CondicionEgresoDialogRecyclerViewAdapter adapter;

    public CondicionEgresoDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface CondicionEgresoDialogListener {
        void setCondicionEgreso(CondicionEgresoDataSet dataSet);
    }

    public static CondicionEgresoDialogFragment newInstance() {
        CondicionEgresoDialogFragment f = new CondicionEgresoDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_condicion_egreso_dialog, null);

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

                for(CondicionEgresoDataSet bean : original) {
                    if(bean.getDescripcion().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        lista.add(new CondicionEgresoDataSet(1, "Recuperado"));
        lista.add(new CondicionEgresoDataSet(2, "Traslado al hospital"));
        lista.add(new CondicionEgresoDataSet(3, "Traslado al hospital para uci"));
        lista.add(new CondicionEgresoDataSet(4, "Fallecido"));

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new CondicionEgresoDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CondicionEgresoDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setCondicionEgreso(lista.get(position));
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