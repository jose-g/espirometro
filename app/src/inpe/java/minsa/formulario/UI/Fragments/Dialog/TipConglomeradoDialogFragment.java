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
import minsa.formulario.Adapters.recyclerview.TipConglomeradoDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.TipConglomeradoDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.TipConglomeradoDbHelper;
import minsa.formulario.R;

public class TipConglomeradoDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<TipConglomeradoDataSet> original = new ArrayList<>();
    private List<TipConglomeradoDataSet> lista = new ArrayList<>();

    private TipConglomeradoDialogRecyclerViewAdapter adapter;

    public TipConglomeradoDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface TipConglomeradoDialogListener {
        void setTipConglomerado(TipConglomeradoDataSet dataSet);
    }

    public static TipConglomeradoDialogFragment newInstance(int origin, String ubigeo) {
        TipConglomeradoDialogFragment f = new TipConglomeradoDialogFragment();
        Bundle args = new Bundle();
        args.putInt("origin", origin);
        args.putString("ubigeo", ubigeo);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_tip_conglomerado_dialog, null);

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

                for(TipConglomeradoDataSet bean : original) {
                    if(bean.getDescripcion().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        if(getArguments().getInt("origin", 0) == 0) {
            vCursorTipDoc = mPrinciDbHelper.getAllPrinci(TipConglomeradoDbHelper.TableC.TableN);
            while(vCursorTipDoc.moveToNext()) {
                TipConglomeradoDataSet vDstDepart = new TipConglomeradoDataSet();
                vDstDepart.setId(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipConglomeradoDbHelper.TableC.id)));
                vDstDepart.setDescripcion(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipConglomeradoDbHelper.TableC.descripcion)));
                vDstDepart.setHabitacional_concurrencia(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipConglomeradoDbHelper.TableC.habitacional_concurrencia)));
                lista.add(vDstDepart);
            }
        } else {
            vCursorTipDoc = mPrinciDbHelper.getCustom(getArguments().getString("ubigeo", ""));

            while(vCursorTipDoc.moveToNext()) {
                TipConglomeradoDataSet vDstDepart = new TipConglomeradoDataSet();
                vDstDepart.setId(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipConglomeradoDbHelper.TableC.id)));
                vDstDepart.setDescripcion(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipConglomeradoDbHelper.TableC.descripcion)));
                vDstDepart.setHabitacional_concurrencia(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipConglomeradoDbHelper.TableC.habitacional_concurrencia)));
                lista.add(vDstDepart);
            }
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new TipConglomeradoDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TipConglomeradoDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setTipConglomerado(lista.get(position));
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