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
import minsa.formulario.Adapters.recyclerview.SeveridadDialogRecyclerViewAdapter;
import minsa.formulario.DataSet.SeveriDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class SeveridadDialogFragment extends DialogFragment {

    @BindView(R.id.svSearch) SearchView svSearch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<SeveriDataSet> original = new ArrayList<>();
    private List<SeveriDataSet> lista = new ArrayList<>();

    private SeveridadDialogRecyclerViewAdapter adapter;

    public SeveridadDialogListener delegate;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorTipDoc;

    public interface SeveridadDialogListener {
        void setSeveridad(SeveriDataSet dataSet);
    }

    public static SeveridadDialogFragment newInstance() {
        SeveridadDialogFragment f = new SeveridadDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_severidad_dialog, null);

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

                for(SeveriDataSet bean : original) {
                    if(bean.getDescripcion().toLowerCase().contains(newText.toLowerCase())) {
                        lista.add(bean);
                    }
                }

                adapter.notifyDataSetChanged();
                return false;
            }
        });

//        vCursorTipDoc = mPrinciDbHelper.getAllPrinci(SeveriDbHelper.SeveriTableC.SeveriTableN);
//        while(vCursorTipDoc.moveToNext()) {
//            SeveriDataSet vDstDepart = new SeveriDataSet();
//            vDstDepart.setId(vCursorTipDoc.getInt(vCursorTipDoc.getColumnIndex(SeveriDbHelper.SeveriTableC.SeveriIdenti)));
//            vDstDepart.setDescripcion(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(SeveriDbHelper.SeveriTableC.SeveriDescri)));
//
//            if(vCursorTipDoc.getInt(vCursorTipDoc.getColumnIndex(SeveriDbHelper.SeveriTableC.SeveriIdenti)) == 1) {
//                vDstDepart.setDetalle("Tratamiento domiciliario, Tos, malestar general, dolor de garganta, fiebre, congestión nasal");
//            } else if(vCursorTipDoc.getInt(vCursorTipDoc.getColumnIndex(SeveriDbHelper.SeveriTableC.SeveriIdenti)) == 2) {
//                vDstDepart.setDetalle("Paciente hospitalizada. Disnea o dificultad respiratoria, FR > 22 respiraciones/minuto, Alteración de conciencia (desorientación, confusión), Hipertensión arterial o shock, Signos clínicos o radiológicos de neumonía, Recuento de infocitos < 100 celuulas/uL");
//            } else if(vCursorTipDoc.getInt(vCursorTipDoc.getColumnIndex(SeveriDbHelper.SeveriTableC.SeveriIdenti)) == 3) {
//                vDstDepart.setDetalle("Paciente hospitalizado en unidades críticos: FR > 22 respiraciones/minuto o PaCO2 < 32mmHg, Alteración de conciencia, Pasitolica < 100mmHg o RAM < 65mmHg,PaO2 < 60mmHg o PaFi < 300, Signos cñinicos de fatiga muscular: aleteo nasal, uso de músculos accesorios, desbalance tócaro-abdominal, Lactato sérico> 2mosm/l");
//            }
//
//            lista.add(vDstDepart);
//        }

        lista.add(new SeveriDataSet(4, "ASINTOMÁTICO", "No presenta síntomas"));
        lista.add(new SeveriDataSet(1, "LEVE", "Tratamiento domiciliario, Tos, malestar general, dolor de garganta, fiebre, congestión nasal"));
        lista.add(new SeveriDataSet(2, "MODERADO", "Paciente hospitalizada. Disnea o dificultad respiratoria, FR > 22 respiraciones/minuto, Alteración de conciencia (desorientación, confusión), Hipertensión arterial o shock, Signos clínicos o radiológicos de neumonía, Recuento de infocitos < 100 celuulas/uL"));
        lista.add(new SeveriDataSet(3, "SEVERO", "Paciente hospitalizado en unidades críticos: FR > 22 respiraciones/minuto o PaCO2 < 32mmHg, Alteración de conciencia, Pasitolica < 100mmHg o RAM < 65mmHg,PaO2 < 60mmHg o PaFi < 300, Signos cñinicos de fatiga muscular: aleteo nasal, uso de músculos accesorios, desbalance tócaro-abdominal, Lactato sérico> 2mosm/l"));

        for(int i=0; i < lista.size(); i++) {
            original.add(lista.get(i));
        }

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayout.getOrientation()));

        adapter = new SeveridadDialogRecyclerViewAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SeveridadDialogRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(null != delegate) {
                    delegate.setSeveridad(lista.get(position));
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