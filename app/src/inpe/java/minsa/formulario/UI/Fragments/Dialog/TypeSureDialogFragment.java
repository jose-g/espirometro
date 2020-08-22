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
import minsa.formulario.Adapters.recyclerview.TypeSureDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.TipSegDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.TipSegDbHelper;
import minsa.formulario.R;

public class TypeSureDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<TipSegDataSet> original = new ArrayList<>();
    private List<TipSegDataSet> lista = new ArrayList<>();

    private TypeSureDialogRecyclerViewAdapter adapter;

    public TypeSafeDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipSeg;

    public interface TypeSafeDialogListener {
        void setTypeSafe(TipSegDataSet dataSet);
    }

    public static TypeSureDialogFragment newInstance() {
        TypeSureDialogFragment f = new TypeSureDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_type_sure_dialog, null);

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

                for(TipSegDataSet bean : original) {
                    if(bean.getTip_seg().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorTipSeg = mPrinciDbHelper.getAllPrinci(TipSegDbHelper.TipSegTableC.TipSegTableN);
        while(vCursorTipSeg.moveToNext()) {
            TipSegDataSet vDstDepart = new TipSegDataSet();
            vDstDepart.setId_tip_seg(vCursorTipSeg.getInt(vCursorTipSeg.getColumnIndex(TipSegDbHelper.TipSegTableC.TipSegIdenti)));
            vDstDepart.setTip_seg(vCursorTipSeg.getString(vCursorTipSeg.getColumnIndex(TipSegDbHelper.TipSegTableC.TipSegDescri)));
            lista.add(vDstDepart);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new TypeSureDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TypeSureDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setTypeSafe(lista.get(position));
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