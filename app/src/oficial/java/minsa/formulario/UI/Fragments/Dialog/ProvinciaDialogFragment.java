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
import minsa.formulario.Adapters.recyclerview.ProvinciaDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.ProvinDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.ProvinDbHelper;
import minsa.formulario.R;

public class ProvinciaDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<ProvinDataSet> original = new ArrayList<>();
    private List<ProvinDataSet> lista = new ArrayList<>();

    private ProvinciaDialogRecyclerViewAdapter adapter;

    public ProvinciaDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface ProvinciaDialogListener {
        void setProvincia(ProvinDataSet dataSet);
    }

    public static ProvinciaDialogFragment newInstance(String departamento) {
        ProvinciaDialogFragment f = new ProvinciaDialogFragment();
        Bundle args = new Bundle();
        args.putString("departamento", departamento);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_provincia_dialog, null);

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

                for(ProvinDataSet bean : original) {
                    if(bean.getProvinNombre().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorTipDoc = mPrinciDbHelper.getAllPrinciOne(ProvinDbHelper.ProvinTableC.ProvinTableN, ProvinDbHelper.ProvinTableC.DepartCodigo, getArguments().getString("departamento", ""));
        while(vCursorTipDoc.moveToNext()){
            ProvinDataSet vDstProvin = new ProvinDataSet();
            vDstProvin.setProvinCodigo(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(ProvinDbHelper.ProvinTableC.ProvinCodigo)));
            vDstProvin.setProvinNombre(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(ProvinDbHelper.ProvinTableC.ProvinDescri)));
            lista.add(vDstProvin);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new ProvinciaDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProvinciaDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setProvincia(lista.get(position));
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