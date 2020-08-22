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
import minsa.formulario.Adapters.recyclerview.TipDocDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.TipDocDbHelper;
import minsa.formulario.R;

public class TipDocDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<TipDocDataSet> original = new ArrayList<>();
    private List<TipDocDataSet> lista = new ArrayList<>();

    private TipDocDialogRecyclerViewAdapter adapter;

    public TipDocDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface TipDocDialogListener {
        void setTipDoc(TipDocDataSet dataSet);
    }

    public static TipDocDialogFragment newInstance(int origen) {
        TipDocDialogFragment f = new TipDocDialogFragment();
        Bundle args = new Bundle();
        args.putInt("origen", origen);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_tip_doc_dialog, null);

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

                for(TipDocDataSet bean : original) {
                    if(bean.getTipDocDescri().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorTipDoc = mPrinciDbHelper.getAllPrinci(TipDocDbHelper.TipDocTableC.TipDocTableN);
        while(vCursorTipDoc.moveToNext()) {
            if(getArguments().getInt("origen", 0) == 1) {
                if(Integer.parseInt(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocIdenti))) > 4) {
                    continue;
                }
            }

            TipDocDataSet vDstDepart = new TipDocDataSet();
            vDstDepart.setTipDocIdenti(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocIdenti)));
            vDstDepart.setTipDocDescri(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocDescri)));
            lista.add(vDstDepart);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new TipDocDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TipDocDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setTipDoc(lista.get(position));
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