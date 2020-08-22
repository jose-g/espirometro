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
import minsa.formulario.Adapters.recyclerview.DistritoDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.DistriDataSet;
import minsa.formulario.DbHelper.DistriDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class DistritoDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<DistriDataSet> original = new ArrayList<>();
    private List<DistriDataSet> lista = new ArrayList<>();

    private DistritoDialogRecyclerViewAdapter adapter;

    public DistritoDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface DistritoDialogListener {
        void setDistrito(DistriDataSet dataSet);
    }

    public static DistritoDialogFragment newInstance(String departamento, String provincia) {
        DistritoDialogFragment f = new DistritoDialogFragment();
        Bundle args = new Bundle();
        args.putString("departamento", departamento);
        args.putString("provincia", provincia);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_distrito_dialog, null);

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

                for(DistriDataSet bean : original) {
                    if(bean.getDistriNombre().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorTipDoc = mPrinciDbHelper.getAllPrinciTwo(DistriDbHelper.DistriTableC.DistriTableN, DistriDbHelper.DistriTableC.DepartCodigo, getArguments().getString("departamento"), DistriDbHelper.DistriTableC.ProvinCodigo, getArguments().getString("provincia"));
        while(vCursorTipDoc.moveToNext()){
            DistriDataSet vDstDistri = new DistriDataSet();
            vDstDistri.setDistriCodigo(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(DistriDbHelper.DistriTableC.DistriCodigo)));
            vDstDistri.setDistriNombre(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(DistriDbHelper.DistriTableC.DistriDescri)));
            lista.add(vDstDistri);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new DistritoDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DistritoDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setDistrito(lista.get(position));
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