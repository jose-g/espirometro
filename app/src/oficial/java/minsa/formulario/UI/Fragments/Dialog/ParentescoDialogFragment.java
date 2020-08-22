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
import minsa.formulario.Adapters.recyclerview.ParentescoDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.ParentescoDataSet;
import minsa.formulario.DbHelper.ParentescoDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class ParentescoDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<ParentescoDataSet> original = new ArrayList<>();
    private List<ParentescoDataSet> lista = new ArrayList<>();

    private ParentescoDialogRecyclerViewAdapter adapter;

    public ParentescoDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorParentesco;

    public interface ParentescoDialogListener {
        void setParentesco(ParentescoDataSet dataSet);
    }

    public static ParentescoDialogFragment newInstance() {
        ParentescoDialogFragment f = new ParentescoDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_parentesco_dialog, null);

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

                for(ParentescoDataSet bean : original) {
                    if(bean.getParentesco().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

        vCursorParentesco = mPrinciDbHelper.getAllPrinci(ParentescoDbHelper.ParentescoTableC.ParentescoTableN);
        while(vCursorParentesco.moveToNext()) {
            ParentescoDataSet vDstDepart = new ParentescoDataSet();
            vDstDepart.setId(vCursorParentesco.getInt(vCursorParentesco.getColumnIndex(ParentescoDbHelper.ParentescoTableC.ParentescoIdenti)));
            vDstDepart.setParentesco(vCursorParentesco.getString(vCursorParentesco.getColumnIndex(ParentescoDbHelper.ParentescoTableC.ParentescoDescri)));
            lista.add(vDstDepart);
        }

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new ParentescoDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ParentescoDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setParentesco(lista.get(position));
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