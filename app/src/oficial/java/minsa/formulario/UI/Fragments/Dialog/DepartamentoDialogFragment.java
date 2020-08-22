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
import minsa.formulario.Adapters.recyclerview.DepartamentoDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.DepartDataSet;
import minsa.formulario.DbHelper.DepartDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class DepartamentoDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<DepartDataSet> original = new ArrayList<>();
    private List<DepartDataSet> lista = new ArrayList<>();

    private DepartamentoDialogRecyclerViewAdapter adapter;

    public DepartamentoDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface DepartamentoDialogListener {
        void setDepartamento(DepartDataSet dataSet);
    }

    public static DepartamentoDialogFragment newInstance() {
        DepartamentoDialogFragment f = new DepartamentoDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_departamento_dialog, null);

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

                for(DepartDataSet bean : original) {
                    if(bean.getDepartNombre().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorTipDoc = mPrinciDbHelper.getAllPrinci(DepartDbHelper.DepartTableC.DepartTableN);
        while(vCursorTipDoc.moveToNext()) {
            DepartDataSet vDstDepart = new DepartDataSet();
            vDstDepart.setDepartCodigo(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(DepartDbHelper.DepartTableC.DepartCodigo)));
            vDstDepart.setDepartNombre(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(DepartDbHelper.DepartTableC.DepartDescri)));
            lista.add(vDstDepart);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new DepartamentoDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DepartamentoDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setDepartamento(lista.get(position));
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