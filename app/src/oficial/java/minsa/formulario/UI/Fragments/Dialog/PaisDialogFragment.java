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
import minsa.formulario.Adapters.recyclerview.PaisDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.PaisDataSet;
import minsa.formulario.DbHelper.PaisDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class PaisDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<PaisDataSet> original = new ArrayList<>();
    private List<PaisDataSet> lista = new ArrayList<>();

    private PaisDialogRecyclerViewAdapter adapter;

    public PaisDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorPais;

    public interface PaisDialogListener {
        void setPais(PaisDataSet dataSet, int position);
    }

    public static PaisDialogFragment newInstance(int position) {
        PaisDialogFragment f = new PaisDialogFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_pais_dialog, null);

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

                for(PaisDataSet bean : original) {
                    if(bean.getPais().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorPais = mPrinciDbHelper.getAllPrinci(PaisDbHelper.PaisTableC.PaisTableN);
        while(vCursorPais.moveToNext()) {
            PaisDataSet vDstDepart = new PaisDataSet();
            vDstDepart.setId_pais(vCursorPais.getInt(vCursorPais.getColumnIndex(PaisDbHelper.PaisTableC.PaisIdenti)));
            vDstDepart.setPais(vCursorPais.getString(vCursorPais.getColumnIndex(PaisDbHelper.PaisTableC.PaisDescri)));
            lista.add(vDstDepart);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new PaisDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PaisDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setPais(lista.get(position), getArguments().getInt("position", 0));
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