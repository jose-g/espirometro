package minsa.formulario.UI.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import minsa.formulario.Adapters.recyclerview.DatoPacienteSyncRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.F100SyncRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.F200SyncRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.F300SyncRecyclerViewAdapter;
import minsa.formulario.AppController;
import minsa.formulario.DataSet.DatoPacienteDataSet;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DataSet.F100DataSet;
import minsa.formulario.DataSet.F200DataSet;
import minsa.formulario.DataSet.F300DataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.DatoPacienteDbHelper;
import minsa.formulario.DbHelper.DatoPacienteSintomaDbHelper;
import minsa.formulario.DbHelper.F100DbHelper;
import minsa.formulario.DbHelper.F100RiesgoDbHelper;
import minsa.formulario.DbHelper.F200ContactoDbHelper;
import minsa.formulario.DbHelper.F200ContactoRiesgoDbHelper;
import minsa.formulario.DbHelper.F200DbHelper;
import minsa.formulario.DbHelper.F200LaboratorioDbHelper;
import minsa.formulario.DbHelper.F200RiesgoDbHelper;
import minsa.formulario.DbHelper.F200SintomaDbHelper;
import minsa.formulario.DbHelper.F200ViajeDbHelper;
import minsa.formulario.DbHelper.F300AlarmaDbHelper;
import minsa.formulario.DbHelper.F300DbHelper;
import minsa.formulario.DbHelper.F300SintomaDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.Utils;

public class HomeFragment extends Fragment {

    @BindView(R.id.rvDatoPaciente) RecyclerView rvDatoPaciente;
    @BindView(R.id.rv100) RecyclerView rv100;
    @BindView(R.id.rv200) RecyclerView rv200;
    @BindView(R.id.rv300) RecyclerView rv300;
    @BindView(R.id.efab) ExtendedFloatingActionButton efab;

    private DirWebDataSet vDstDirWeb;
    private String vTokens = "";

    private List<DatoPacienteDataSet> datoPacienteDataSets = new ArrayList<>();
    private DatoPacienteSyncRecyclerViewAdapter datoPacienteSyncRecyclerViewAdapter;

    private List<F100DataSet> f100DataSets = new ArrayList<>();
    private F100SyncRecyclerViewAdapter f100SyncRecyclerViewAdapter;

    private List<F200DataSet> f200DataSets = new ArrayList<>();
    private F200SyncRecyclerViewAdapter f200SyncRecyclerViewAdapter;

    private List<F300DataSet> f300DataSets = new ArrayList<>();
    private F300SyncRecyclerViewAdapter adapter;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorDataPatient;
    private Cursor vCursorF100;
    private Cursor vCursorF200;
    private Cursor vCursorF300;

    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);

        vDstDirWeb = DirWebDataSet.getInstance();

        mPrinciDbHelper = new PrinciDbHelper(getContext());

        handleSSLHandshake();

        getActivity().setTitle("Enviar fichas");

        ////////////////////////////////////////////
        /// DATOS PACIENTES
        ////////////////////////////////////////////
        rvDatoPaciente.setLayoutManager(new LinearLayoutManager(getContext()));
        datoPacienteSyncRecyclerViewAdapter = new DatoPacienteSyncRecyclerViewAdapter(getContext(), datoPacienteDataSets);
        rvDatoPaciente.setAdapter(datoPacienteSyncRecyclerViewAdapter);

        datoPacienteSyncRecyclerViewAdapter.setOnItemClickListener(new DatoPacienteSyncRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });

        ////////////////////////////////////////////
        /// F100
        ////////////////////////////////////////////
        rv100.setLayoutManager(new LinearLayoutManager(getContext()));

        f100SyncRecyclerViewAdapter = new F100SyncRecyclerViewAdapter(getContext(), f100DataSets);
        rv100.setAdapter(f100SyncRecyclerViewAdapter);

        f100SyncRecyclerViewAdapter.setOnItemClickListener(new F100SyncRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });

        ////////////////////////////////////////////
        /// F200
        ////////////////////////////////////////////
        LinearLayoutManager ll200 = new LinearLayoutManager(getContext());
        rv200.setLayoutManager(ll200);

        f200SyncRecyclerViewAdapter = new F200SyncRecyclerViewAdapter(getContext(), f200DataSets);
        rv200.setAdapter(f200SyncRecyclerViewAdapter);

        f200SyncRecyclerViewAdapter.setOnItemClickListener(new F200SyncRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });

        ////////////////////////////////////////////
        /// F300
        ////////////////////////////////////////////
        rv300.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new F300SyncRecyclerViewAdapter(getContext(), f300DataSets);
        rv300.setAdapter(adapter);

        adapter.setOnItemClickListener(new F300SyncRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });

        getDataPatient();
        getF100();
        getF200();
        getData300();

        return root;
    }

    @OnClick(R.id.efab)
    public void onClicEFAB(View view) {
        efab.setEnabled(false);
        send();
    }

    private void send() {
        if(vTokens.isEmpty()) {
            MSerGetTokens();
        } else {
            efab.setEnabled(false);
            if(datoPacienteDataSets.size() == 0) {
                if(f100DataSets.size() > 0) {
                    progressDialog = Utils.showProgressDialog(getActivity(), "Enviado Fichas 100 (1 de " + f100DataSets.size() + ")");
                    progressDialog.show();

                    MSerSet100(0);
                } else if (f200DataSets.size() > 0) {
                    progressDialog = Utils.showProgressDialog(getActivity(), "Enviado Fichas 200 (1 de " + f200DataSets.size() + ")");
                    progressDialog.show();

                    MSerSet200(0);
                } else if (f300DataSets.size() > 0) {
                    progressDialog = Utils.showProgressDialog(getActivity(), "Enviado Fichas 300 (1 de " + f300DataSets.size() + ")");
                    progressDialog.show();

                    MSerSet300(0);
                }
            } else {
                progressDialog = Utils.showProgressDialog(getActivity(), "Enviado datos de los pacientes (1 de " + datoPacienteDataSets.size() + ")");
                progressDialog.show();

                MSerSetDatoPaciente(0);
            }
        }
    }

    private void getDataPatient() {
        datoPacienteDataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorDataPatient = mPrinciDbHelper.getAllTwoValues(DatoPacienteDbHelper.TableC.TableN, DatoPacienteDbHelper.TableC.id_usuario, DatoPacienteDbHelper.TableC.id_dato_paciente, usuariDataSet.getUsuariIdenti(), "0");
        if(vCursorDataPatient.getCount() != 0) {
            while(vCursorDataPatient.moveToNext()) {
                DatoPacienteDataSet datoPacienteDataSet = new DatoPacienteDataSet();
                datoPacienteDataSet.set_id(vCursorDataPatient.getInt(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC._id)));
                datoPacienteDataSet.setFecha(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.fecha)));
                datoPacienteDataSet.setId_tipo_doc(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_tipo_doc)));
                datoPacienteDataSet.setTipo_doc(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.tipo_doc)));
                datoPacienteDataSet.setNumero_doc(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.numero_doc)));
                datoPacienteDataSet.setNombres(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.nombres)));
                datoPacienteDataSet.setPaterno(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.paterno)));
                datoPacienteDataSet.setMaterno(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.materno)));
                datoPacienteDataSet.setFec_nac(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.fec_nac)));
                datoPacienteDataSet.setId_sexo(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_sexo)));
                datoPacienteDataSet.setSexo(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.sexo)));
                datoPacienteDataSet.setCelular(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.celular)));
                datoPacienteDataSet.setCelular_contacto(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.celular_contacto)));
                datoPacienteDataSet.setCorreo(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.correo)));
                datoPacienteDataSet.setId_tipo_residencia(vCursorDataPatient.getInt(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_tipo_residencia)));
                datoPacienteDataSet.setDireccion(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.direccion)));
                datoPacienteDataSet.setId_departamento(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_departamento)));
                datoPacienteDataSet.setId_provincia(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_provincia)));
                datoPacienteDataSet.setId_distrito(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_distrito)));
                datoPacienteDataSet.setLatitud(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.latitud)));
                datoPacienteDataSet.setLongitud(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.longitud)));
                datoPacienteDataSet.setEs_pers_salud(vCursorDataPatient.getInt(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.es_pers_salud)));
                datoPacienteDataSet.setId_profesion(vCursorDataPatient.getInt(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_profesion)));
                datoPacienteDataSet.setTiene_sintoma(vCursorDataPatient.getInt(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.tiene_sintoma)));
                datoPacienteDataSet.setFecha_sintoma(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.fecha_sintoma)));
                datoPacienteDataSet.setOtro_sintoma(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.otro_sintoma)));
                datoPacienteDataSet.setId_usuario(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_usuario)));
                datoPacienteDataSet.setCodigo_pais_celular(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.codigo_pais_celular)));
                datoPacienteDataSet.setCodigo_pais_telefono(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.codigo_pais_telefono)));
                datoPacienteDataSets.add(datoPacienteDataSet);
            }
        }
        datoPacienteSyncRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getF100() {
        f100DataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorF100 = mPrinciDbHelper.getAllTwoValues(F100DbHelper.TableC.TableN, F100DbHelper.TableC.id_usuario, F100DbHelper.TableC.id_f100, usuariDataSet.getUsuariIdenti(), "0");
        if(vCursorF100.getCount() != 0) {
            while(vCursorF100.moveToNext()) {
                F100DataSet f100DataSet = new F100DataSet();
                f100DataSet.set_id(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC._id)));
                f100DataSet.setFecha(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.fecha)));
                f100DataSet.setHora(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.hora)));
                f100DataSet.setMarca_prueba(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.marca_prueba)));
                f100DataSet.setLote_prueba(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.lote_prueba)));
                f100DataSet.setIndustria_prueba(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.industria_prueba)));
                f100DataSet.setFecha_prueba(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.fecha_prueba)));
                f100DataSet.setId_procedencia(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_procedencia)));
                f100DataSet.setProcedencia(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.procedencia)));
                f100DataSet.setId_resultado_prueba_rapida(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_resultado_prueba_rapida)));
                f100DataSet.setId_resultado_prueba_rapida_2(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_resultado_prueba_rapida_2)));
                f100DataSet.setId_severidad(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_severidad)));
                f100DataSet.setId_trimestre(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_trimestre)));
                f100DataSet.setTrimestre(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.trimestre)));
                f100DataSet.setOtro_riesgo(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.otro_riesgo)));
                f100DataSet.setPcr(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.pcr)));
                f100DataSet.setObservacion(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.observacion)));
                f100DataSet.setId_tip_doc(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_tip_doc)));
                f100DataSet.setTipo_doc(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.tipo_doc)));
                f100DataSet.setNum_doc(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.num_doc)));
                f100DataSet.setPaciente(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.paciente)));
                f100DataSet.setId_usuario(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_usuario)));
                f100DataSet.setId_f100(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_f100)));
                f100DataSet.setId_dato_paciente(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_dato_paciente)));
                f100DataSet.setId_conglomerado(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_conglomerado)));
                f100DataSet.setConglomerado(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.conglomerado)));
                f100DataSet.setId_tipo_conglomerado(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_tipo_conglomerado)));
                f100DataSet.setTipo_conglomerado(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.tipo_conglomerado)));
                f100DataSets.add(f100DataSet);
            }
        }
        f100SyncRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getF200() {
        f200DataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorF200 = mPrinciDbHelper.getAllTwoValues(F200DbHelper.TableC.TableN, F200DbHelper.TableC.id_usuario, F200DbHelper.TableC.id_f200, usuariDataSet.getUsuariIdenti(), "0");
        if(vCursorF200.getCount() != 0) {
            while(vCursorF200.moveToNext()) {
                F200DataSet f200DataSet = new F200DataSet();
                f200DataSet.set_id(vCursorF200.getInt(vCursorF200.getColumnIndex(F200DbHelper.TableC._id)));
                f200DataSet.setId_tip_seg(vCursorF200.getInt(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_tip_seg)));
                f200DataSet.setTip_seg(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.tip_seg)));
                f200DataSet.setOtro_seguro(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.otro_seguro)));
                f200DataSet.setFec_notif(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.fec_notif)));
                f200DataSet.setProfesion(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.profesion)));
                f200DataSet.setHospitalizado(vCursorF200.getInt(vCursorF200.getColumnIndex(F200DbHelper.TableC.hospitalizado)));
                f200DataSet.setFec_hospi(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.fec_hospi)));
                f200DataSet.setTemperatura(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.temperatura)));
                f200DataSet.setOtro_sintoma(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.otro_sintoma)));
                f200DataSet.setOtro_riesgo(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.otro_riesgo)));
                f200DataSet.setHa_viajado(vCursorF200.getInt(vCursorF200.getColumnIndex(F200DbHelper.TableC.ha_viajado)));
                f200DataSet.setId_cont_caso_conf(vCursorF200.getInt(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_cont_caso_conf)));
                f200DataSet.setContacto_caso_confirmado(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.contacto_caso_confirmado)));
                f200DataSet.setId_tip_doc_inve(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_tip_doc_inve)));
                f200DataSet.setTip_doc_inve(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.tip_doc_inve)));
                f200DataSet.setDoc_inve(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.doc_inve)));
                f200DataSet.setNomb_inve(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.nomb_inve)));
                f200DataSet.setPaterno_inve(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.paterno_inve)));
                f200DataSet.setMaterno_inve(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.materno_inve)));
                f200DataSet.setObservacion(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.observacion)));
                f200DataSet.setId_tip_doc(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_tip_doc)));
                f200DataSet.setTipo_doc(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.tipo_doc)));
                f200DataSet.setNum_doc(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.num_doc)));
                f200DataSet.setPaciente(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.paciente)));
                f200DataSet.setId_usuario(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_usuario)));
                f200DataSet.setId_f200(vCursorF200.getInt(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_f200)));
                f200DataSet.setId_dato_paciente(vCursorF200.getInt(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_dato_paciente)));
                f200DataSet.setId_conglomerado(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_conglomerado)));
                f200DataSet.setConglomerado(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.conglomerado)));
                f200DataSet.setId_tipo_conglomerado(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.id_tipo_conglomerado)));
                f200DataSet.setTipo_conglomerado(vCursorF200.getString(vCursorF200.getColumnIndex(F200DbHelper.TableC.tipo_conglomerado)));
                f200DataSets.add(f200DataSet);
            }
        }
        f200SyncRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getData300() {
        f300DataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorF300 = mPrinciDbHelper.getAllTwoValues(F300DbHelper.TableC.TableN, F300DbHelper.TableC.id_usuario, F300DbHelper.TableC.id_f300, usuariDataSet.getUsuariIdenti(), "0");
        if(vCursorF300.getCount() != 0) {
            while(vCursorF300.moveToNext()) {
                F300DataSet f300DataSet = new F300DataSet();
                f300DataSet.set_id(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC._id)));
                f300DataSet.setFecha(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.fecha)));
                f300DataSet.setId_tipo_monitoreo(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_tipo_monitoreo)));
                f300DataSet.setTipo_monitoreo(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.tipo_monitoreo)));
                f300DataSet.setRealizar_medicion(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.realizar_medicion)));
                f300DataSet.setPresion_arterial(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.presion_arterial)));
                f300DataSet.setPresion_arterial_2(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.presion_arterial_2)));
                f300DataSet.setPresion_arterial_media(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.presion_arterial_media)));
                f300DataSet.setFrecuencia_cardiaca(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.frecuencia_cardiaca)));
                f300DataSet.setFrecuencia_respiratoria(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.frecuencia_respiratoria)));
                f300DataSet.setTemperatura(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.temperatura)));
                f300DataSet.setOtro_sintoma(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.otro_sintoma)));
                f300DataSet.setOtro_alarma(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.otro_alarma)));
                f300DataSet.setId_evolucion(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_evolucion)));
                f300DataSet.setEvolucion(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.evolucion)));
                f300DataSet.setEgreso_clinico(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.egreso_clinico)));
                f300DataSet.setId_condicion_egreso(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_condicion_egreso)));
                f300DataSet.setCondicion_egreso(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.condicion_egreso)));
                f300DataSet.setObservacion(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.observacion)));
                f300DataSet.setId_tip_doc(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_tip_doc)));
                f300DataSet.setTipo_doc(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.tipo_doc)));
                f300DataSet.setNum_doc(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.num_doc)));
                f300DataSet.setPaciente(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.paciente)));
                f300DataSet.setId_usuario(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_usuario)));
                f300DataSet.setId_f300(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_f300)));
                f300DataSet.setId_dato_paciente(vCursorF300.getInt(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_dato_paciente)));
                f300DataSet.setId_conglomerado(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_conglomerado)));
                f300DataSet.setConglomerado(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.conglomerado)));
                f300DataSet.setId_tipo_conglomerado(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.id_tipo_conglomerado)));
                f300DataSet.setTipo_conglomerado(vCursorF300.getString(vCursorF300.getColumnIndex(F300DbHelper.TableC.tipo_conglomerado)));
                f300DataSets.add(f300DataSet);
            }
        }
        adapter.notifyDataSetChanged();

        if(datoPacienteDataSets.size() == 0 && f100DataSets.size() == 0 && f200DataSets.size() == 0 && f300DataSets.size() == 0) {
            efab.setVisibility(View.GONE);
        }
    }

    private void MSerSetDatoPaciente(int position) {
        if(position >= datoPacienteDataSets.size()) {
            MSerSet100(0);
            return;
        }

        if(datoPacienteDataSets.get(position).getId_dato_paciente() > 0) {
            MSerSetDatoPaciente(position+1);
            return;
        }

        progressDialog.setMessage("Enviado datos de los pacientes (" + (position+1) + " de " + datoPacienteDataSets.size() + ")");

        if(datoPacienteDataSets.get(position).getId_tipo_doc().equals("6")) {
            MSerRegistrarPaciente(position);
        } else {
            MSerSospechoso(position);
        }
    }

    private void MSerSospechoso(int position) {
        String id_tip_doc = datoPacienteDataSets.get(position).getId_tipo_doc();
        String num_doc = datoPacienteDataSets.get(position).getNumero_doc();

        Log.e("XXX Consultar sospech:", id_tip_doc + " - " + num_doc);

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_SOSPECHOSO + "/" + id_tip_doc + "/" + num_doc, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String codigo = response.getString("codigo");
                    if(codigo.equals("1")) {
                        MSerActualizarPaciente(position);
                    } else if(codigo.equals("0")) {
                        MSerRegistrarPaciente(position);
                    } else {
                        MSerSetDatoPaciente(position+1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    MSerSetDatoPaciente(position+1);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                MSerSetDatoPaciente(position+1);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                headers.put("Content-Type", vDstDirWeb.getDirWebConTypL());
                return headers;
            }

        };

        jsonObject.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObject);
    }

    private void MSerActualizarPaciente(int position) {
        JSONObject prueba = new JSONObject();
        try {
            prueba.put("fec_prueba", "");
            prueba.put("hor_prueba", "");
            prueba.put("id_procedencia", "2");
            prueba.put("id_tipo_resultado1", "");
            prueba.put("foto_prueba1", "");
            prueba.put("nro_visita", "");
            prueba.put("id_tipo_resultado2", "");
            prueba.put("foto_prueba2", "");
            prueba.put("id_clasificacion", "");
            prueba.put("pcd", "");
            prueba.put("observacion", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray sintomas_array = new JSONArray();
        Cursor vCursorSintoma = mPrinciDbHelper.getAllPrinciOne(DatoPacienteSintomaDbHelper.TableC.TableN, DatoPacienteSintomaDbHelper.TableC._id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));
        if(null != vCursorSintoma) {
            while(vCursorSintoma.moveToNext()) {
                int id_sintoma = vCursorSintoma.getInt(vCursorSintoma.getColumnIndex(DatoPacienteSintomaDbHelper.TableC.id_sintoma));
                JSONObject objectSintoma = new JSONObject();
                try {
                    objectSintoma.put("sintoma", String.valueOf(id_sintoma));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sintomas_array.put(objectSintoma);
            }
        }

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        JSONObject paciente = new JSONObject();
        try {
            paciente.put("codigo_pais_telefono", datoPacienteDataSets.get(position).getCodigo_pais_telefono());
            paciente.put("telefono", datoPacienteDataSets.get(position).getCelular_contacto());
            paciente.put("codigo_pais_celular", datoPacienteDataSets.get(position).getCodigo_pais_celular());
            paciente.put("celular", datoPacienteDataSets.get(position).getCelular());
            paciente.put("personal_salud", datoPacienteDataSets.get(position).getEs_pers_salud() == 1 ? "SI" : "NO");
            paciente.put("id_profesion", datoPacienteDataSets.get(position).getId_profesion() > 0 ? String.valueOf(datoPacienteDataSets.get(position).getId_profesion()) : "");
            paciente.put("ubigeo", datoPacienteDataSets.get(position).getId_departamento() + datoPacienteDataSets.get(position).getId_provincia() + datoPacienteDataSets.get(position).getId_distrito());
            paciente.put("direccion", datoPacienteDataSets.get(position).getDireccion());
            paciente.put("tipo_vivienda", datoPacienteDataSets.get(position).getId_tipo_residencia());
            paciente.put("latitud", datoPacienteDataSets.get(position).getLatitud());
            paciente.put("longitud", datoPacienteDataSets.get(position).getLongitud());
            paciente.put("fec_inicio_sintomas", datoPacienteDataSets.get(position).getFecha_sintoma().replace("-", ""));
            paciente.put("sintomas_array", sintomas_array);
            paciente.put("otro_sintoma", datoPacienteDataSets.get(position).getOtro_sintoma());
            paciente.put("id_usuario", usuariDataSet.getUsuariIdenti());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject();
        try {
            object.put("tip_documento", datoPacienteDataSets.get(position).getId_tipo_doc());
            object.put("num_documento", datoPacienteDataSets.get(position).getNumero_doc());
            object.put("paciente", paciente);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("XXX Actualizar Datos: ", object.toString());

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, vDstDirWeb.getDirWebApiUrl() + Constants.URL_ACTUALIZAR_PACIENTE, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String cod_respuesta = response.getString("codigo");

                    switch (cod_respuesta) {
                        case "0000":
                        case "6002":

                            datoPacienteDataSets.get(position).setId_dato_paciente(1);
                            datoPacienteSyncRecyclerViewAdapter.notifyDataSetChanged();

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(DatoPacienteDbHelper.TableC.id_dato_paciente, 1);
                            mPrinciDbHelper.updatePrinci(DatoPacienteDbHelper.TableC.TableN, contentValues, DatoPacienteDbHelper.TableC._id, String.valueOf(datoPacienteDataSets.get(position).get_id()));

                            MSerSetDatoPaciente(position + 1);

                            break;
                        case "6000":
                            MSerRegistrarPaciente(position+1);
                            break;
                        default:
                            MSerSetDatoPaciente(position+1);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    MSerSetDatoPaciente(position+1);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                MSerSetDatoPaciente(position+1);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                headers.put("Content-Type", vDstDirWeb.getDirWebConTypL());
                return headers;
            }

        };

        jsonOblect.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonOblect);
    }

    private void MSerRegistrarPaciente(int position) {
        JSONObject paciente = new JSONObject();
        try {
            paciente.put("tip_documento", datoPacienteDataSets.get(position).getId_tipo_doc());
            paciente.put("num_documento", datoPacienteDataSets.get(position).getNumero_doc());
            paciente.put("nombre", datoPacienteDataSets.get(position).getNombres());
            paciente.put("ape_paterno", datoPacienteDataSets.get(position).getPaterno());
            paciente.put("ape_materno", datoPacienteDataSets.get(position).getMaterno());
            paciente.put("sexo", datoPacienteDataSets.get(position).getId_sexo());
            paciente.put("fec_nacimiento", datoPacienteDataSets.get(position).getFec_nac().replace("-", ""));
            paciente.put("personal_salud", datoPacienteDataSets.get(position).getEs_pers_salud() == 1 ? "SI" : "NO");
            paciente.put("codigo_pais_celular", datoPacienteDataSets.get(position).getCodigo_pais_celular());
            paciente.put("celular", datoPacienteDataSets.get(position).getCelular());
            paciente.put("codigo_pais_telefono", datoPacienteDataSets.get(position).getCodigo_pais_telefono());
            paciente.put("telefono", datoPacienteDataSets.get(position).getCelular_contacto());
            paciente.put("correo", datoPacienteDataSets.get(position).getCorreo());
            paciente.put("prioridad", "2");
            paciente.put("id_profesion", datoPacienteDataSets.get(position).getId_profesion() > 0 ? String.valueOf(datoPacienteDataSets.get(position).getId_profesion()) : "");
            paciente.put("id_estado", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject prueba = new JSONObject();
        try {
            prueba.put("fec_prueba", "");
            prueba.put("hor_prueba", "");
            prueba.put("id_procedencia", "2");
            prueba.put("id_tipo_resultado1", "");
            prueba.put("foto_prueba1", "");
            prueba.put("nro_visita", "");
            prueba.put("id_tipo_resultado2", "");
            prueba.put("foto_prueba2", "");
            prueba.put("id_clasificacion", "");
            prueba.put("pcd", "");
            prueba.put("observacion", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject investigacion = new JSONObject();
        try {
            investigacion.put("tipo_vivienda", datoPacienteDataSets.get(position).getId_tipo_residencia());
            investigacion.put("direccion", datoPacienteDataSets.get(position).getDireccion());
            investigacion.put("ubigeo", datoPacienteDataSets.get(position).getId_departamento() + datoPacienteDataSets.get(position).getId_provincia() + datoPacienteDataSets.get(position).getId_distrito());
            investigacion.put("fec_inicio_sintomas", datoPacienteDataSets.get(position).getFecha_sintoma().replace("-", ""));
            investigacion.put("latitud", datoPacienteDataSets.get(position).getLatitud());
            investigacion.put("longitud", datoPacienteDataSets.get(position).getLongitud());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray sintomas_array = new JSONArray();
        Cursor vCursorSintoma = mPrinciDbHelper.getAllPrinciOne(DatoPacienteSintomaDbHelper.TableC.TableN, DatoPacienteSintomaDbHelper.TableC._id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));
        if(null != vCursorSintoma) {
            while(vCursorSintoma.moveToNext()) {
                int id_sintoma = vCursorSintoma.getInt(vCursorSintoma.getColumnIndex(DatoPacienteSintomaDbHelper.TableC.id_sintoma));
                JSONObject objectSintoma = new JSONObject();
                try {
                    objectSintoma.put("sintoma", String.valueOf(id_sintoma));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sintomas_array.put(objectSintoma);
            }
        }

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        JSONObject object = new JSONObject();
        try {
            object.put("paciente", paciente);
            object.put("prueba", prueba);
            object.put("investigacion", investigacion);
            object.put("sintomas_array", sintomas_array);
            object.put("otro_sintoma", datoPacienteDataSets.get(position).getOtro_sintoma());
            object.put("id_usuario", usuariDataSet.getUsuariIdenti());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("XXX Registrar Datos: ", object.toString());

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, vDstDirWeb.getDirWebApiUrl() + Constants.URL_REGISTRO_PACIENTE, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String cod_respuesta = response.getString("cod_respuesta");
                    String num_documento_generado = response.getString("num_documento_generado") == null ? "" : response.getString("num_documento_generado");
                    if(cod_respuesta.equals("0000")) {

                        datoPacienteDataSets.get(position).setId_dato_paciente(1);
                        datoPacienteSyncRecyclerViewAdapter.notifyDataSetChanged();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatoPacienteDbHelper.TableC.id_dato_paciente, 1);

                        if(datoPacienteDataSets.get(position).getId_tipo_doc().equals("6")) {
                            contentValues.put(DatoPacienteDbHelper.TableC.numero_doc, num_documento_generado);
                        }

                        mPrinciDbHelper.updatePrinci(DatoPacienteDbHelper.TableC.TableN, contentValues, DatoPacienteDbHelper.TableC._id, String.valueOf(datoPacienteDataSets.get(position).get_id()));

                        if(datoPacienteDataSets.get(position).getId_tipo_doc().equals("6")) {

                            Cursor vCursor100 = mPrinciDbHelper.getAllPrinciOne(F100DbHelper.TableC.TableN, F100DbHelper.TableC.id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));
                            if(vCursor100.moveToFirst()) {
                                ContentValues cv100 = new ContentValues();
                                cv100.put(F100DbHelper.TableC.num_doc, num_documento_generado);
                                mPrinciDbHelper.updatePrinci(F100DbHelper.TableC.TableN, cv100, F100DbHelper.TableC.id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));

                                for(int i=0; i < f100DataSets.size(); i++) {
                                    if(f100DataSets.get(i).getId_dato_paciente() == datoPacienteDataSets.get(position).get_id()) {
                                        f100DataSets.get(i).setNum_doc(num_documento_generado);
                                    }
                                }
                            }

                            Cursor vCursor200 = mPrinciDbHelper.getAllPrinciOne(F200DbHelper.TableC.TableN, F200DbHelper.TableC.id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));
                            if(vCursor200.moveToFirst()) {
                                ContentValues cv200 = new ContentValues();
                                cv200.put(F200DbHelper.TableC.num_doc, num_documento_generado);
                                mPrinciDbHelper.updatePrinci(F200DbHelper.TableC.TableN, cv200, F200DbHelper.TableC.id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));

                                for(int i=0; i < f200DataSets.size(); i++) {
                                    if(f200DataSets.get(i).getId_dato_paciente() == datoPacienteDataSets.get(position).get_id()) {
                                        f200DataSets.get(i).setNum_doc(num_documento_generado);
                                    }
                                }
                            }

                            Cursor vCursor300 = mPrinciDbHelper.getAllPrinciOne(F300DbHelper.TableC.TableN, F300DbHelper.TableC.id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));
                            if(vCursor300.moveToFirst()) {
                                ContentValues cv300 = new ContentValues();
                                cv300.put(F300DbHelper.TableC.num_doc, num_documento_generado);
                                mPrinciDbHelper.updatePrinci(F300DbHelper.TableC.TableN, cv300, F300DbHelper.TableC.id_dato_paciente, String.valueOf(datoPacienteDataSets.get(position).get_id()));

                                for(int i=0; i < f300DataSets.size(); i++) {
                                    if(f300DataSets.get(i).getId_dato_paciente() == datoPacienteDataSets.get(position).get_id()) {
                                        f300DataSets.get(i).setNum_doc(num_documento_generado);
                                    }
                                }
                            }

                        }

                        MSerSetDatoPaciente(position+1);
                    } else if(cod_respuesta.equals("6001")) {

                        datoPacienteDataSets.get(position).setId_dato_paciente(1);
                        datoPacienteSyncRecyclerViewAdapter.notifyDataSetChanged();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatoPacienteDbHelper.TableC.id_dato_paciente, 1);

                        mPrinciDbHelper.updatePrinci(DatoPacienteDbHelper.TableC.TableN, contentValues, DatoPacienteDbHelper.TableC._id, String.valueOf(datoPacienteDataSets.get(position).get_id()));

                        MSerSetDatoPaciente(position+1);
                    } else {
                        MSerSetDatoPaciente(position+1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    MSerSetDatoPaciente(position+1);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                MSerSetDatoPaciente(position+1);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                headers.put("Content-Type", vDstDirWeb.getDirWebConTypL());
                return headers;
            }

        };

        jsonOblect.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonOblect);
    }

    private void MSerSet100(int p1) {
        if(p1 >= f100DataSets.size()) {
            MSerSet200(0);
            return;
        }

        if(f100DataSets.get(p1).getId_f100() > 0) {
            MSerSet100(p1+1);
            return;
        }

        JSONObject paciente = new JSONObject();
        try {
            paciente.put("num_documento", f100DataSets.get(p1).getNum_doc());
            paciente.put("tip_documento", f100DataSets.get(p1).getId_tip_doc());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject prueba = new JSONObject();
        try {
            prueba.put("fec_prueba", f100DataSets.get(p1).getFecha().replace("-", ""));
            prueba.put("foto_prueba1", "");
            prueba.put("foto_prueba2", "");
            prueba.put("hor_prueba", f100DataSets.get(p1).getHora().replace(":", ""));

            int clasificacion = f100DataSets.get(p1).getId_severidad();
            int resultado_prueba_rapida_2 = f100DataSets.get(p1).getId_resultado_prueba_rapida_2();
            int pcd = f100DataSets.get(p1).getPcr();

            prueba.put("id_clasificacion", clasificacion == 0 ? "" : String.valueOf(clasificacion));
            prueba.put("id_procedencia", f100DataSets.get(p1).getId_procedencia());
            prueba.put("id_tipo_resultado1", f100DataSets.get(p1).getId_resultado_prueba_rapida());
            prueba.put("id_tipo_resultado2", resultado_prueba_rapida_2 == 0 ? "" : String.valueOf(resultado_prueba_rapida_2));
            prueba.put("nro_visita", "1");
            prueba.put("observacion", f100DataSets.get(p1).getObservacion());
            prueba.put("pcd", pcd == 0 ? "" : String.valueOf(pcd));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray riesgos = new JSONArray();
        Cursor vCursorF100Riesgo = mPrinciDbHelper.getAllPrinciOne(F100RiesgoDbHelper.TableC.TableN, F100RiesgoDbHelper.TableC._id_f100, String.valueOf(f100DataSets.get(p1).get_id()));
        if(null != vCursorF100Riesgo) {
            while(vCursorF100Riesgo.moveToNext()) {
                int id_riesgo = vCursorF100Riesgo.getInt(vCursorF100Riesgo.getColumnIndex(F100RiesgoDbHelper.TableC.id_riesgo));
                JSONObject objectSintoma = new JSONObject();
                try {
                    objectSintoma.put("riesgo", String.valueOf(id_riesgo));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                riesgos.put(objectSintoma);
            }
        }

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        JSONObject object = new JSONObject();
        try {
            object.put("fecha_vencimiento", f100DataSets.get(p1).getFecha_prueba().replace("-", ""));
            object.put("id_lugaratencion", "1");
            object.put("id_usuario", usuariDataSet.getUsuariNroDoc());
            object.put("industria", f100DataSets.get(p1).getIndustria_prueba());
            object.put("lote", f100DataSets.get(p1).getLote_prueba());
            object.put("marca", f100DataSets.get(p1).getMarca_prueba());
            object.put("otro_riesgo", f100DataSets.get(p1).getOtro_riesgo());
            object.put("paciente", paciente);
            object.put("prueba", prueba);
            object.put("riesgos_array", riesgos);
            object.put("trimestre_embarazo", f100DataSets.get(p1).getId_trimestre() > 0 ? String.valueOf(f100DataSets.get(p1).getId_trimestre()) : "");
            object.put("es_conglomerado", f100DataSets.get(p1).getId_tipo_conglomerado());
            object.put("id_conglomerado", f100DataSets.get(p1).getId_conglomerado());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("XXX F100: ", object.toString());

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, vDstDirWeb.getDirWebApiUrl() + Constants.URL_REGISTRO_F100, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String cod_respuesta = response.getString("cod_respuesta");
                    if(cod_respuesta.equals("0000")) {

                        f100DataSets.get(p1).setId_f100(1);
                        f100SyncRecyclerViewAdapter.notifyDataSetChanged();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(F100DbHelper.TableC.id_f100, 1);

                        mPrinciDbHelper.updatePrinci(F100DbHelper.TableC.TableN, contentValues, F100DbHelper.TableC._id, String.valueOf(f100DataSets.get(p1).get_id()));

                        MSerSet100(p1+1);
                    } else {
                        MSerSet100(p1+1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    MSerSet100(p1+1);
                }

                progressDialog.setMessage("Enviado Fichas 100 (" + (p1+1) + " de " + f100DataSets.size() + ")");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();

                if((f100DataSets.size()-1) == p1) {
                    Utils.hideProgressDialog(progressDialog);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                headers.put("Content-Type", vDstDirWeb.getDirWebConTypL());
                return headers;
            }

        };

        jsonOblect.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonOblect);

    }

    private void MSerSet200(int p2) {
        if(p2 >= f200DataSets.size()) {
            MSerSet300(0);
            return;
        }

        if(f200DataSets.get(p2).getId_f200() > 0) {
            MSerSet200(p2+1);
            return;
        }

        JSONObject paciente = new JSONObject();
        try {
            paciente.put("num_documento", f200DataSets.get(p2).getNum_doc());
            paciente.put("tip_documento", f200DataSets.get(p2).getId_tip_doc());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject investigacion = new JSONObject();
        try {
            investigacion.put("id_tipo_seguro", f200DataSets.get(p2).getId_tip_seg());
            investigacion.put("otro_seguro", f200DataSets.get(p2).getOtro_seguro());
            investigacion.put("fecha_notificacion", f200DataSets.get(p2).getFec_notif().replace("-", ""));
            investigacion.put("ocupacion", f200DataSets.get(p2).getProfesion());
            investigacion.put("hospitalizado", f200DataSets.get(p2).getHospitalizado());
            investigacion.put("fecha_hospitalizacion", f200DataSets.get(p2).getFec_hospi().replace("-", ""));
            investigacion.put("temperatura", f200DataSets.get(p2).getTemperatura());
            investigacion.put("contacto_covid", f200DataSets.get(p2).getId_cont_caso_conf());
            investigacion.put("viajo", f200DataSets.get(p2).getHa_viajado());
            investigacion.put("observacion", f200DataSets.get(p2).getObservacion());
            investigacion.put("trimestre_embarazo", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject investigador = new JSONObject();
        try {
            investigador.put("apellido_materno_investigador", f200DataSets.get(p2).getMaterno_inve());
            investigador.put("apellido_paterno_investigador", f200DataSets.get(p2).getPaterno_inve());
            investigador.put("tipo_documento_investigador", f200DataSets.get(p2).getId_tip_doc_inve());
            investigador.put("nombres_investigador", f200DataSets.get(p2).getNomb_inve());
            investigador.put("nro_documento_investigador", f200DataSets.get(p2).getDoc_inve());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int id_200 = f200DataSets.get(p2).get_id();

        JSONArray paises_array = new JSONArray();
        Cursor vCursorF200Viaje = mPrinciDbHelper.getAllPrinciOne(F200ViajeDbHelper.TableC.TableN, F200ViajeDbHelper.TableC._id_f200, String.valueOf(id_200));
        if(null != vCursorF200Viaje) {
            while(vCursorF200Viaje.moveToNext()) {
                JSONObject objectPais = new JSONObject();
                try {
                    objectPais.put("pais", vCursorF200Viaje.getInt(vCursorF200Viaje.getColumnIndex(F200ViajeDbHelper.TableC.id_pais)));
                    objectPais.put("ciudad", vCursorF200Viaje.getString(vCursorF200Viaje.getColumnIndex(F200ViajeDbHelper.TableC.ciudad)));
                    objectPais.put("fecha", f200DataSets.get(p2).getFec_notif().replace("-", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                paises_array.put(objectPais);
            }
        }

        JSONObject prueba_rapida = new JSONObject();
        try {
            prueba_rapida.put("fec_prueba", "");
            prueba_rapida.put("hor_prueba", "");
            prueba_rapida.put("id_tipo_resultado1", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray pruebas_array = new JSONArray();
        Cursor vCursorF200Laboratorio = mPrinciDbHelper.getAllPrinciOne(F200LaboratorioDbHelper.TableC.TableN, F200LaboratorioDbHelper.TableC._id_f200, String.valueOf(id_200));
        if(null != vCursorF200Laboratorio) {
            while(vCursorF200Laboratorio.moveToNext()) {
                String fecha = vCursorF200Laboratorio.getString(vCursorF200Laboratorio.getColumnIndex(F200LaboratorioDbHelper.TableC.fecha)).replace("-", "") + " " + vCursorF200Laboratorio.getString(vCursorF200Laboratorio.getColumnIndex(F200LaboratorioDbHelper.TableC.hora));
                String prueba = String.valueOf(vCursorF200Laboratorio.getInt(vCursorF200Laboratorio.getColumnIndex(F200LaboratorioDbHelper.TableC.id_tipo_muestra)));
                JSONObject objectPrueba = new JSONObject();
                try {
                    objectPrueba.put("fecha", fecha);
                    objectPrueba.put("prueba", prueba);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pruebas_array.put(objectPrueba);
            }
        }

        JSONArray riesgos_array = new JSONArray();
        Cursor vCursorF200Riesgo = mPrinciDbHelper.getAllPrinciOne(F200RiesgoDbHelper.TableC.TableN, F200RiesgoDbHelper.TableC._id_f200, String.valueOf(f200DataSets.get(p2).get_id()));
        if(null != vCursorF200Riesgo) {
            while(vCursorF200Riesgo.moveToNext()) {
                int id_riesgo = vCursorF200Riesgo.getInt(vCursorF200Riesgo.getColumnIndex(F200RiesgoDbHelper.TableC.id_riesgo));
                JSONObject objectRiesgo = new JSONObject();
                try {
                    objectRiesgo.put("riesgo", String.valueOf(id_riesgo));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                riesgos_array.put(objectRiesgo);
            }
        }

        JSONArray sintomas_array = new JSONArray();
        Cursor vCursorF200Sintoma = mPrinciDbHelper.getAllPrinciOne(F200SintomaDbHelper.TableC.TableN, F200SintomaDbHelper.TableC._id_f200, String.valueOf(f200DataSets.get(p2).get_id()));
        if(null != vCursorF200Sintoma) {
            while(vCursorF200Sintoma.moveToNext()) {
                int id_sintoma = vCursorF200Sintoma.getInt(vCursorF200Sintoma.getColumnIndex(F300SintomaDbHelper.TableC.id_sintoma));
                JSONObject objectSintoma = new JSONObject();
                try {
                    objectSintoma.put("sintoma", String.valueOf(id_sintoma));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sintomas_array.put(objectSintoma);
            }
        }

        JSONArray contactos_array = new JSONArray();
        Cursor vCursorF200Contacto = mPrinciDbHelper.getAllPrinciOne(F200ContactoDbHelper.TableC.TableN, F200ContactoDbHelper.TableC._id_f200, String.valueOf(f200DataSets.get(p2).get_id()));
        if(null != vCursorF200Contacto) {
            while(vCursorF200Contacto.moveToNext()) {

                int id_contacto = vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC._id_contacto));

                JSONArray condicion_array = new JSONArray();
                Cursor vCursorF200ContactoCondicion = mPrinciDbHelper.getAllPrinciOne(F200ContactoRiesgoDbHelper.TableC.TableN, F200ContactoRiesgoDbHelper.TableC._id_contacto, String.valueOf(id_contacto));
                if(null != vCursorF200ContactoCondicion) {
                    while(vCursorF200ContactoCondicion.moveToNext()) {
                        int id_condicion = vCursorF200ContactoCondicion.getInt(vCursorF200ContactoCondicion.getColumnIndex(F200ContactoRiesgoDbHelper.TableC.id_riesgo));
                        JSONObject objectCondicion = new JSONObject();
                        try {
                            objectCondicion.put("riesgo", String.valueOf(id_condicion));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        condicion_array.put(objectCondicion);
                    }
                }

                JSONObject objectContacto = new JSONObject();
                try {
                    objectContacto.put("tip_documento", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.id_tip_doc)));
                    objectContacto.put("num_documento", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.num_doc)));
                    objectContacto.put("fec_nacimiento", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.fec_nac)).replace("-", ""));
                    objectContacto.put("nombres", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.nombres)));
                    objectContacto.put("ape_paterno", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.paterno)));
                    objectContacto.put("ape_materno", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.materno)));
                    objectContacto.put("id_parentesco", vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.id_parentesco)));
                    objectContacto.put("sexo", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.id_sexo)));
                    objectContacto.put("edad", vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.edad)));
                    objectContacto.put("direccion", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.direccion)));
                    objectContacto.put("celular", vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.celular)));
                    objectContacto.put("f100", vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.f100)));
                    objectContacto.put("condicion_array", condicion_array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                contactos_array.put(objectContacto);
            }
        }

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        JSONObject object = new JSONObject();
        try {
            object.put("id_usuario", usuariDataSet.getUsuariNroDoc());
            object.put("paciente", paciente);
            object.put("investigacion", investigacion);
            object.put("investigador", investigador);
            object.put("otro_riesgo", f200DataSets.get(p2).getOtro_riesgo());
            object.put("otro_sintoma", f200DataSets.get(p2).getOtro_sintoma());
            object.put("paises_array", paises_array);
            object.put("prueba_rapida", prueba_rapida);
            object.put("pruebas_array", pruebas_array);
            object.put("riesgos_array", riesgos_array);
            object.put("sintomas_array", sintomas_array);
            object.put("contactos_array", contactos_array);
            object.put("es_conglomerado", f200DataSets.get(p2).getId_tipo_conglomerado());
            object.put("id_conglomerado", f200DataSets.get(p2).getId_conglomerado());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("XXX F200: ", object.toString());

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, vDstDirWeb.getDirWebApiUrl() + Constants.URL_REGISTRO_F200, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String cod_respuesta = response.getString("cod_respuesta");
                    if(cod_respuesta.equals("0000")) {

                        f200DataSets.get(p2).setId_f200(1);
                        f200SyncRecyclerViewAdapter.notifyDataSetChanged();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(F200DbHelper.TableC.id_f200, 1);

                        mPrinciDbHelper.updatePrinci(F200DbHelper.TableC.TableN, contentValues, F200DbHelper.TableC._id, String.valueOf(f200DataSets.get(p2).get_id()));

                        MSerSet200(p2+1);
                    } else {
                        MSerSet200(p2+1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    MSerSet200(p2+1);
                }

                progressDialog.setMessage("Enviado Fichas 200 (" + (p2+1) + " de " + f200DataSets.size() + ")");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();

                if((f200DataSets.size()-1) == p2) {
                    Utils.hideProgressDialog(progressDialog);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                headers.put("Content-Type", vDstDirWeb.getDirWebConTypL());
                return headers;
            }

        };

        jsonOblect.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonOblect);

    }

    private void MSerSet300(int p3){
        if(p3 >= f300DataSets.size()) {
            Utils.hideProgressDialog(progressDialog);
            efab.setEnabled(true);
            return;
        }

        if(f300DataSets.get(p3).getId_f300() > 0) {
            MSerSet300(p3+1);
            return;
        }

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        JSONObject seguimiento = new JSONObject();
        try {
            seguimiento.put("fechaSeguimiento", Utils.changeDateFormat(f300DataSets.get(p3).getFecha(), "yyyy-MM-dd", "yyyyMMdd"));
            seguimiento.put("tipoMonitoreo", f300DataSets.get(p3).getId_tipo_monitoreo());
            String presionArterial = "";
            if(!f300DataSets.get(p3).getPresion_arterial().isEmpty() && !f300DataSets.get(p3).getPresion_arterial_2().isEmpty()) {
                presionArterial = f300DataSets.get(p3).getPresion_arterial() + "/" + f300DataSets.get(p3).getPresion_arterial_2();
            }
            seguimiento.put("presionArterial", presionArterial);
            seguimiento.put("frecuenciaCardiaca", f300DataSets.get(p3).getFrecuencia_cardiaca());
            seguimiento.put("frecuenciaRespiratoria", f300DataSets.get(p3).getFrecuencia_respiratoria());
            seguimiento.put("temperatura", f300DataSets.get(p3).getTemperatura());
            seguimiento.put("idEvolucion", f300DataSets.get(p3).getId_evolucion());
            seguimiento.put("egreso", f300DataSets.get(p3).getEgreso_clinico() == 0 ? "" : String.valueOf(f300DataSets.get(p3).getEgreso_clinico()));
            seguimiento.put("condicionEgreso", f300DataSets.get(p3).getId_condicion_egreso() == 0 ? "" : String.valueOf(f300DataSets.get(p3).getId_condicion_egreso()));
            seguimiento.put("observacion", f300DataSets.get(p3).getObservacion());
            seguimiento.put("descEvolucion", f300DataSets.get(p3).getEvolucion());
            seguimiento.put("idUsuario", usuariDataSet.getUsuariNroDoc());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray sintomas = new JSONArray();
        Cursor vCursorF300Sintoma = mPrinciDbHelper.getAllPrinciOne(F300SintomaDbHelper.TableC.TableN, F300SintomaDbHelper.TableC._id_f300, String.valueOf(f300DataSets.get(p3).get_id()));
        if(null != vCursorF300Sintoma) {
            while(vCursorF300Sintoma.moveToNext()) {
                int id_sintoma = vCursorF300Sintoma.getInt(vCursorF300Sintoma.getColumnIndex(F300SintomaDbHelper.TableC.id_sintoma));
                JSONObject objectSintoma = new JSONObject();
                try {
                    objectSintoma.put("sintoma", String.valueOf(id_sintoma));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sintomas.put(objectSintoma);
            }
        }

        JSONArray signos = new JSONArray();
        Cursor vCursorF300Alarma = mPrinciDbHelper.getAllPrinciOne(F300AlarmaDbHelper.TableC.TableN, F300AlarmaDbHelper.TableC._id_f300, String.valueOf(f300DataSets.get(p3).get_id()));
        if(null != vCursorF300Alarma) {
            while(vCursorF300Alarma.moveToNext()) {
                int id_alarma = vCursorF300Alarma.getInt(vCursorF300Alarma.getColumnIndex(F300AlarmaDbHelper.TableC.id_alarma));
                JSONObject objectSigno = new JSONObject();
                try {
                    objectSigno.put("riesgo", String.valueOf(id_alarma));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                signos.put(objectSigno);
            }
        }

        JSONObject object = new JSONObject();
        try {
            object.put("tipDocumento", f300DataSets.get(p3).getId_tip_doc());
            object.put("numDocumento", f300DataSets.get(p3).getNum_doc());
            object.put("seguimiento", seguimiento);
            object.put("sintomas", sintomas);
            object.put("otroSintoma", f300DataSets.get(p3).getOtro_sintoma());
            object.put("signos", signos);
            object.put("otroSigno", f300DataSets.get(p3).getOtro_alarma());
            object.put("login", usuariDataSet.getUsuariNroDoc());
            object.put("id_lugaratencion", "9");
            object.put("es_conglomerado", f300DataSets.get(p3).getId_tipo_conglomerado());
            object.put("id_conglomerado", f300DataSets.get(p3).getId_conglomerado());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("XXX F300: ", object.toString());

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, vDstDirWeb.getDirWebApiUrl() + Constants.URL_REGISTRO_F300, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String cod_respuesta = response.getString("codMensaje");
                    if(cod_respuesta.equals("000")) {

                        f300DataSets.get(p3).setId_f300(1);
                        adapter.notifyDataSetChanged();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(F300DbHelper.TableC.id_f300, 1);

                        mPrinciDbHelper.updatePrinci(F300DbHelper.TableC.TableN, contentValues, F300DbHelper.TableC._id, String.valueOf(f300DataSets.get(p3).get_id()));

                        MSerSet300(p3+1);
                    } else {
                        MSerSet300(p3+1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    MSerSet300(p3+1);
                }

                progressDialog.setMessage("Enviado Fichas 300 (" + (p3+1) + " de " + f300DataSets.size() + ")");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();

                if((f300DataSets.size()-1) == p3) {
                    Utils.hideProgressDialog(progressDialog);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                headers.put("Content-Type", vDstDirWeb.getDirWebConTypL());
                return headers;
            }

        };

        jsonOblect.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonOblect);
    }

    private void MSerGetTokens(){
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.POST,
                vDstDirWeb.getDirWebApiUrl() + "/token",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myObject = new JSONObject(response);
                            String Tokens = myObject.getString("access_token");
                            vTokens = Tokens;
                            Log.i("Tokens",Tokens);

                            send();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthor());
                params.put("Content-Type", vDstDirWeb.getDirWebConTyp());
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", vDstDirWeb.getDirWebUseNam());
                params.put("password", vDstDirWeb.getDirWebPasswo());
                params.put("grant_type", vDstDirWeb.getDirWebGraTyp());
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                2500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    if(arg0.equalsIgnoreCase(DirWebDataSet.DirWebSLL))
                        return true;
                    else
                        return false;
                }
            });
        } catch (Exception ignored) {

        }
    }

}
