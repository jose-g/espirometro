package minsa.formulario.UI.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import minsa.formulario.UI.Activity.f100.F100Activity;
import minsa.formulario.UI.Activity.f200.F200Activity;
import minsa.formulario.Adapters.recyclerview.DatoPacienteSearchRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.F100SearchRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.F200SearchRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.F300SearchRecyclerViewAdapter;
import minsa.formulario.DataSet.DatoPacienteDataSet;
import minsa.formulario.DataSet.F100DataSet;
import minsa.formulario.DataSet.F200DataSet;
import minsa.formulario.DataSet.F300DataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.DatoPacienteDbHelper;
import minsa.formulario.DbHelper.F100DbHelper;
import minsa.formulario.DbHelper.F200DbHelper;
import minsa.formulario.DbHelper.F300DbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;
import minsa.formulario.Rest.ApiService;
import minsa.formulario.Rest.EnvioResponse;
import minsa.formulario.Util.AppExecutors;
import minsa.formulario.Util.Utils;
import minsa.formulario.UI.Activity.f300.NewF300Activity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FichasFragment extends Fragment {

    @BindView(R.id.rvDatoPaciente) RecyclerView rvDatoPaciente;
    @BindView(R.id.rv100) RecyclerView rv100;
    @BindView(R.id.rv200) RecyclerView rv200;
    @BindView(R.id.rv300) RecyclerView rv300;

    private List<DatoPacienteDataSet> datoPacienteDataSets = new ArrayList<>();
    private DatoPacienteSearchRecyclerViewAdapter datoPacienteSearchRecyclerViewAdapter;

    private List<F100DataSet> f100DataSets = new ArrayList<>();
    private F100SearchRecyclerViewAdapter f100SearchRecyclerViewAdapter;

    private List<F200DataSet> f200DataSets = new ArrayList<>();
    private F200SearchRecyclerViewAdapter f200SearchRecyclerViewAdapter;

    private List<F300DataSet> f300DataSets = new ArrayList<>();
    private F300SearchRecyclerViewAdapter f300SearchRecyclerViewAdapter;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorDataPatient;
    private Cursor vCursorF100;
    private Cursor vCursorF200;
    private Cursor vCursorF300;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fichas, container, false);

        ButterKnife.bind(this, view);

        mPrinciDbHelper = new PrinciDbHelper(getContext());

        //getActivity().setTitle("Búsqueda de fichas");

        ////////////////////////////////////////////
        /// DATOS PACIENTES
        ////////////////////////////////////////////
        rvDatoPaciente.setLayoutManager(new LinearLayoutManager(getContext()));

        datoPacienteSearchRecyclerViewAdapter = new DatoPacienteSearchRecyclerViewAdapter(getContext(), datoPacienteDataSets);
        rvDatoPaciente.setAdapter(datoPacienteSearchRecyclerViewAdapter);

        datoPacienteSearchRecyclerViewAdapter.setOnItemClickListener(new DatoPacienteSearchRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });

        ////////////////////////////////////////////
        /// F100
        ////////////////////////////////////////////
        rv100.setLayoutManager(new LinearLayoutManager(getContext()));

        f100SearchRecyclerViewAdapter = new F100SearchRecyclerViewAdapter(getContext(), f100DataSets);
        rv100.setAdapter(f100SearchRecyclerViewAdapter);

        f100SearchRecyclerViewAdapter.setOnItemClickListener(new F100SearchRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(f100DataSets.get(position).getId_f100() > 0) {
                    return;
                }

                Intent intent = new Intent(getContext(), F100Activity.class);
                intent.putExtra("id", f100DataSets.get(position).get_id());
                intent.putExtra("id_tipo_documento", f100DataSets.get(position).getId_tip_doc());
                intent.putExtra("tipodocumento", f100DataSets.get(position).getTipo_doc());
                intent.putExtra("documento", f100DataSets.get(position).getNum_doc());
                intent.putExtra("paciente", f100DataSets.get(position).getPaciente());
                intent.putExtra("id_dato_paciente", f100DataSets.get(position).getId_dato_paciente());
                startActivityForResult(intent, 111);
            }
        });

        ////////////////////////////////////////////
        /// F200
        ////////////////////////////////////////////
        rv200.setLayoutManager(new LinearLayoutManager(getContext()));

        f200SearchRecyclerViewAdapter = new F200SearchRecyclerViewAdapter(getContext(), f200DataSets);
        rv200.setAdapter(f200SearchRecyclerViewAdapter);

        f200SearchRecyclerViewAdapter.setOnItemClickListener(new F200SearchRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(f200DataSets.get(position).getId_f200() > 0) {
                    return;
                }

                Intent intent = new Intent(getContext(), F200Activity.class);
                intent.putExtra("id", f200DataSets.get(position).get_id());
                intent.putExtra("id_tipo_documento", f200DataSets.get(position).getId_tip_doc());
                intent.putExtra("tipodocumento", f200DataSets.get(position).getTipo_doc());
                intent.putExtra("documento", f200DataSets.get(position).getNum_doc());
                intent.putExtra("paciente", f200DataSets.get(position).getPaciente());
                intent.putExtra("id_dato_paciente", f200DataSets.get(position).getId_dato_paciente());
                startActivityForResult(intent, 222);
            }
        });

        ////////////////////////////////////////////
        /// F300
        ////////////////////////////////////////////
        rv300.setLayoutManager(new LinearLayoutManager(getContext()));

        f300SearchRecyclerViewAdapter = new F300SearchRecyclerViewAdapter(getContext(), f300DataSets);
        rv300.setAdapter(f300SearchRecyclerViewAdapter);

        f300SearchRecyclerViewAdapter.setOnItemClickListener(new F300SearchRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(f300DataSets.get(position).getId_f300() > 0) {
                    return;
                }

                Intent intent = new Intent(getContext(), NewF300Activity.class);
                intent.putExtra("id", f300DataSets.get(position).get_id());
                intent.putExtra("id_tipo_documento", f300DataSets.get(position).getId_tip_doc());
                intent.putExtra("tipodocumento", f300DataSets.get(position).getTipo_doc());
                intent.putExtra("documento", f300DataSets.get(position).getNum_doc());
                intent.putExtra("paciente", f300DataSets.get(position).getPaciente());
                intent.putExtra("id_dato_paciente", f300DataSets.get(position).getId_dato_paciente());
                startActivityForResult(intent, 333);
            }
        });


        new AppExecutors().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                //getDataPatient();
                getF100();
                //getF200();
               // getF300();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fichas, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();
        String android_id = usuariDataSet.getUsuariIdenti() + "-" + Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        switch (item.getItemId()) {
            case R.id.action_update:

                progressDialog = Utils.showProgressDialog(getActivity(), "Descargando información...");
                progressDialog.show();

                Call<ResponseBody> call = Utils.getRetrofit("http://159.65.107.241:2000/api/").downloadFileWithDynamicUrlSync(android_id, android_id + ".sqlite");

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utils.hideProgressDialog(progressDialog);

                        if(response.code() == 200) {
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body(), "/data/data/minsa.formulario/databases/coronavirus.sqlite");
                            if(writtenToDisk) {
                                Toasty.success(getContext(), "Información descargada correctamente", Toasty.LENGTH_SHORT).show();
                            }
                        } else {
                            Toasty.info(getContext(), "Se presentó un inconveniente al descargar información", Toasty.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Utils.hideProgressDialog(progressDialog);
                    }
                });

                break;
            case R.id.action_send:

                progressDialog = Utils.showProgressDialog(getActivity(), "Enviando información...");
                progressDialog.show();

                File file = new File("/data/data/minsa.formulario/databases/coronavirus.sqlite");

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://159.65.107.241:2000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService api = retrofit.create(ApiService.class);

                RequestBody dni = RequestBody.create(MediaType.parse("multipart/form-data"), android_id);
                MultipartBody.Part body = MultipartBody.Part.createFormData("archivo", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));

                Call<EnvioResponse> uploadFileCall = api.sendFile(dni, body);

                uploadFileCall.enqueue(new Callback<EnvioResponse>() {
                    @Override
                    public void onResponse(Call<EnvioResponse> call, Response<EnvioResponse> response) {
                        Utils.hideProgressDialog(progressDialog);

                        if(null == response.body() || !response.isSuccessful()) {
                            Toasty.error(getContext(), "Se presentó un inconveniente al enviar información", Toasty.LENGTH_SHORT).show();
                            return;
                        }

                        if(response.body().isSuccess()) {
                            Toasty.success(getContext(), "La información se envió correctamente.", Toasty.LENGTH_SHORT).show();
                        } else {
                            Toasty.info(getContext(), "Se presentó un inconveniente al procesar la información", Toasty.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EnvioResponse> call, Throwable t) {
                        Utils.hideProgressDialog(progressDialog);
                    }
                });
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 111:
                if(resultCode == 1) {
                    getF100();
                }
                break;
            case 222:
                if(resultCode == 1) {
                    //getF200();
                }
                break;
            case 333:
                if(resultCode == 1) {
                    //getF300();
                }
                break;
        }
    }

    private void getDataPatient() {
        datoPacienteDataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorDataPatient = mPrinciDbHelper.getAllPrinciOne(DatoPacienteDbHelper.TableC.TableN, DatoPacienteDbHelper.TableC.id_usuario,
                "112133");
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
                datoPacienteDataSet.setId_usuario(vCursorDataPatient.getString(vCursorDataPatient.getColumnIndex(DatoPacienteDbHelper.TableC.id_usuario)));
                datoPacienteDataSets.add(datoPacienteDataSet);
            }
        }
        datoPacienteSearchRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getF100() {
        f100DataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorF100 = mPrinciDbHelper.getAllPrinciOne(F100DbHelper.TableC.TableN, F100DbHelper.TableC.id_usuario, "112133");
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
                f100DataSets.add(f100DataSet);
            }
        }
        f100SearchRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getF200() {
        f200DataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorF200 = mPrinciDbHelper.getAllPrinciOne(F200DbHelper.TableC.TableN, F200DbHelper.TableC.id_usuario, usuariDataSet.getUsuariIdenti());
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
                f200DataSets.add(f200DataSet);
            }
        }
        f200SearchRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getF300() {
        f300DataSets.clear();

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        vCursorF300 = mPrinciDbHelper.getAllPrinciOne(F300DbHelper.TableC.TableN, F300DbHelper.TableC.id_usuario, usuariDataSet.getUsuariIdenti());
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
                f300DataSets.add(f300DataSet);
            }
        }
        f300SearchRecyclerViewAdapter.notifyDataSetChanged();
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {

            UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

            File file = new File("/data/data/minsa.formulario/databases/coronavirus.sqlite");
            if(file.exists()) {
                file.renameTo( new File("/data/data/minsa.formulario/databases/old-" + usuariDataSet.getUsuariIdenti() + "-" + Utils.getDate("yyyyMMddHHmmss")  + " -coronavirus.sqlite"));
            }

            File futureStudioIconFile = new File(fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("XXX", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}