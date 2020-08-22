package minsa.formulario.UI.Fragments.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.Adapters.recyclerview.CodigoPaisDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.CodigoPaisDataSet;
import minsa.formulario.R;

public class CodigoPaisDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<CodigoPaisDataSet> original = new ArrayList<>();
    private List<CodigoPaisDataSet> lista = new ArrayList<>();

    private CodigoPaisDialogRecyclerViewAdapter adapter;

    public CodigoPaisDialogListener delegate;

    public interface CodigoPaisDialogListener {
        void setCodigoPais(CodigoPaisDataSet dataSet, int origin);
    }

    public static CodigoPaisDialogFragment newInstance(int origin) {
        CodigoPaisDialogFragment f = new CodigoPaisDialogFragment();
        Bundle args = new Bundle();
        args.putInt("origin", origin);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_codigo_pais_dialog, null);

        ButterKnife.bind(this, view);

        svSearch.requestFocus();

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lista.clear();

                for(CodigoPaisDataSet bean : original) {
                    if(bean.getSufijo().toLowerCase().contains(newText.toLowerCase()) || bean.getCod_telefono().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        try {
            Gson gsonProvincia = new Gson();
            BufferedReader brProvincia = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("json/codigo_pais.json")));
            CodigoPaisDataSet[] codigoPaisDataSets = gsonProvincia.fromJson(brProvincia, CodigoPaisDataSet[].class);

            for(int i=0; i < codigoPaisDataSets.length; i++) {
                CodigoPaisDataSet bean = new CodigoPaisDataSet(codigoPaisDataSets[i].getCod_telefono(), codigoPaisDataSets[i].getSufijo());
                lista.add(bean);
                original.add(bean);
            }

        } catch (Exception ex) {

        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new CodigoPaisDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CodigoPaisDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setCodigoPais(lista.get(position), getArguments().getInt("origin"));
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