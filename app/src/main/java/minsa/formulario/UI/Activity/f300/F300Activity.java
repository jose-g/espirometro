package minsa.formulario.UI.Activity.f300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import minsa.formulario.Adapters.recyclerview.F300RecyclerViewAdapter;
import minsa.formulario.DataSet.F300DataSet;
import minsa.formulario.DbHelper.F300DbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class F300Activity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.tvPaciente) TextView tvPaciente;
    @BindView(R.id.tvTipoDocumento) TextView tvTipoDocumento;
    @BindView(R.id.tvDocumento) TextView tvDocumento;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private List<F300DataSet> f300DataSets = new ArrayList<>();
    private F300RecyclerViewAdapter adapter;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorF300;

    private String paciente = "";
    private String id_tipo_documento = "";
    private String tipodocumento = "";
    private String documento = "";
    private int id_dato_paciente = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f300);

        ButterKnife.bind(this);

        paciente = getIntent().getStringExtra("paciente");
        id_tipo_documento = getIntent().getStringExtra("id_tipo_documento");
        tipodocumento = getIntent().getStringExtra("tipodocumento");
        documento = getIntent().getStringExtra("documento");
        id_dato_paciente = getIntent().getIntExtra("id_dato_paciente", 0);

        tvPaciente.setText("Paciente: " + paciente);
        tvTipoDocumento.setText("Tipo de documento: " + tipodocumento);
        tvDocumento.setText("Número de documento: " + documento);

        toolbar.setTitle("F300 - Seguimiento Clínico");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPrinciDbHelper = new PrinciDbHelper(getApplicationContext());

        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayout);

        adapter = new F300RecyclerViewAdapter(getApplicationContext(), f300DataSets);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new F300RecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(f300DataSets.get(position).getId_f300() > 0) {
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), NewF300Activity.class);
                intent.putExtra("id", f300DataSets.get(position).get_id());
                intent.putExtra("id_tipo_documento", id_tipo_documento);
                intent.putExtra("tipodocumento", tipodocumento);
                intent.putExtra("documento", documento);
                intent.putExtra("paciente", paciente);
                intent.putExtra("id_dato_paciente", id_dato_paciente);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        f300DataSets.clear();

        vCursorF300 = mPrinciDbHelper.getAllTwoValues(F300DbHelper.TableC.TableN, F300DbHelper.TableC.id_tip_doc, F300DbHelper.TableC.num_doc, id_tipo_documento, documento);
        if(vCursorF300.getCount() != 0) {
            while(vCursorF300.moveToNext()) {
                F300DataSet f300DataSet = new F300DataSet();
                f300DataSet.set_id(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC._id)));
                f300DataSet.setFecha(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.fecha)));
                f300DataSet.setPresion_arterial(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.presion_arterial)));
                f300DataSet.setPresion_arterial_2(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.presion_arterial_2)));
                f300DataSet.setFrecuencia_cardiaca(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.frecuencia_cardiaca)));
                f300DataSet.setFrecuencia_respiratoria(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.frecuencia_respiratoria)));
                f300DataSet.setEvolucion(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.evolucion)));
                f300DataSet.setId_f300(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_f300)));
                f300DataSets.add(f300DataSet);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @OnClick(R.id.efab)
    public void onClicEFAB(View view) {
        Intent intent = new Intent(getApplicationContext(), NewF300Activity.class);
        intent.putExtra("id_tipo_documento", id_tipo_documento);
        intent.putExtra("tipodocumento", tipodocumento);
        intent.putExtra("documento", documento);
        intent.putExtra("paciente", paciente);
        intent.putExtra("id_dato_paciente", id_dato_paciente);
        startActivity(intent);
    }

}