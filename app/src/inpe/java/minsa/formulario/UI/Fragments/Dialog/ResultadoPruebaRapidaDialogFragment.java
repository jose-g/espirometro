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
import minsa.formulario.Adapters.recyclerview.ResultadoPruebaRapidaDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.TipResDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.TipResDbHelper;
import minsa.formulario.R;

public class ResultadoPruebaRapidaDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<TipResDataSet> original = new ArrayList<>();
    private List<TipResDataSet> lista = new ArrayList<>();

    private ResultadoPruebaRapidaDialogRecyclerViewAdapter adapter;

    public ResultadoPruebaRapidaDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface ResultadoPruebaRapidaDialogListener {
        void setResultadoPrueba(TipResDataSet dataSet, int origin);
    }

    public static ResultadoPruebaRapidaDialogFragment newInstance(int origin) {
        ResultadoPruebaRapidaDialogFragment f = new ResultadoPruebaRapidaDialogFragment();
        Bundle args = new Bundle();
        args.putInt("origin", origin);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_resultado_prueba_dialog, null);

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

                for(TipResDataSet bean : original) {
                    if(bean.getTipResDescri().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorTipDoc = mPrinciDbHelper.getAllPrinci(TipResDbHelper.TipResTableC.TipResTableN);
        while(vCursorTipDoc.moveToNext()){
            TipResDataSet vDstSegRes = new TipResDataSet();
            vDstSegRes.setTipResIdenti(vCursorTipDoc.getInt(vCursorTipDoc.getColumnIndex(TipResDbHelper.TipResTableC.TipResIdenti)));
            vDstSegRes.setTipResDescri((vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipResDbHelper.TipResTableC.TipResDescri))).toUpperCase());
            lista.add(vDstSegRes);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new ResultadoPruebaRapidaDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ResultadoPruebaRapidaDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setResultadoPrueba(lista.get(position), getArguments().getInt("origin"));
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