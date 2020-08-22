package minsa.formulario.UI.Fragments.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
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
import minsa.formulario.Adapters.recyclerview.ProcedenciaDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.ProcedDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.ProcedDbHelper;
import minsa.formulario.R;

public class ProcedenciaDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<ProcedDataSet> original = new ArrayList<>();
    private List<ProcedDataSet> lista = new ArrayList<>();

    private ProcedenciaDialogRecyclerViewAdapter adapter;

    public ProcedenciaDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface ProcedenciaDialogListener {
        void setProcedencia(ProcedDataSet dataSet);
    }

    public static ProcedenciaDialogFragment newInstance() {
        ProcedenciaDialogFragment f = new ProcedenciaDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_procedencia_dialog, null);

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

                for(ProcedDataSet bean : original) {
                    if(bean.getProcedDescri().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        if(mPrinciDbHelper.getCount(ProcedDbHelper.ProcedTableC.ProcedTableN) == 6) {
            String[] vArrProced = getResources().getStringArray(R.array.formul_opcn01_pren03);

            mPrinciDbHelper.deleteAll(ProcedDbHelper.ProcedTableC.ProcedTableN);
            for (int i = 0; i < vArrProced.length; i++) {
                String sArrProced[] = vArrProced[i].split("-");
                ContentValues ProcedValues = new ContentValues();
                ProcedValues.put(ProcedDbHelper.ProcedTableC.ProcedIdenti, sArrProced[0]);
                ProcedValues.put(ProcedDbHelper.ProcedTableC.ProcedDescri, sArrProced[1]);
                mPrinciDbHelper.savePrinci(ProcedDbHelper.ProcedTableC.ProcedTableN, ProcedValues);
            }
        }

        vCursorTipDoc = mPrinciDbHelper.getAllPrinci(ProcedDbHelper.ProcedTableC.ProcedTableN);
        while(vCursorTipDoc.moveToNext()) {
            ProcedDataSet vDstDepart = new ProcedDataSet();
            vDstDepart.setProcedIdenti(vCursorTipDoc.getInt(vCursorTipDoc.getColumnIndex(ProcedDbHelper.ProcedTableC.ProcedIdenti)));
            vDstDepart.setProcedDescri(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(ProcedDbHelper.ProcedTableC.ProcedDescri)));
            lista.add(vDstDepart);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new ProcedenciaDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProcedenciaDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setProcedencia(lista.get(position));
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