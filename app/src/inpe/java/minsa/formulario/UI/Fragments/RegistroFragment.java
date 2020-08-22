package minsa.formulario.UI.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import Activity.Ficha100_INPE;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import minsa.formulario.Adapters.recyclerview.CodigoPaisDialogRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.ListaInternoRecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.RecyclerViewAdapter;
import minsa.formulario.DataSet.CodigoPaisDataSet;
import minsa.formulario.DataSet.RecyInterDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.DatoPacienteDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.SintomDbHelper;
import minsa.formulario.R;
import minsa.formulario.Rest.ApiService;
import minsa.formulario.Rest.EnvioResponse;
import minsa.formulario.UI.Activity.AccesoActivity;
import minsa.formulario.UI.Activity.DownloadTask;
import minsa.formulario.UI.Activity.Ficha_Paciente;
import minsa.formulario.UI.Activity.SpinnerDialog;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static minsa.formulario.R.id.navigation_ll;

public class RegistroFragment extends Fragment {


    LinearLayout ly_datos;
    LinearLayout ly_documento;
    BottomNavigationView navigation_ll;
    @BindView(R.id.recyclerView_interno)
    RecyclerView recyclerView_interno;

    @BindView(R.id.rd_ce)
    RadioButton rd_ce;
    @BindView(R.id.rd_ptp)
    RadioButton rd_ptp;
    @BindView(R.id.rd_pass)
    RadioButton rd_pass;
    @BindView(R.id.rd_dni)
    RadioButton rd_dni;

    SpinnerDialog mSpinnerDialog;
    private ProgressDialog progressDialog;
    private PrinciDbHelper mPrinciDbHelper;
    private List<RecyInterDataSet> lista = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    String cod_radio = "";
    View root;
    String navi_esta = "";



    public static final int PICKFILE_RESULT_CODE = 1;

    private SharedPreferences sharedPreferences;
    private String strBase64 = "";
    private Uri fileUri;
    private String filePath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        sharedPreferences = this.getActivity().getSharedPreferences("4PPS1C0V1D", Context.MODE_PRIVATE);
        mPrinciDbHelper = new PrinciDbHelper(getContext());
        setRetainInstance(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_registro, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.btndescarga)
    public void onClicdescarga(View view) {
        progressDialog = Utils.showProgressDialog(getActivity(), "Validando...");
        progressDialog.show();
        consultaBandejaEntrada("");
    }

    //SELECCIONA UN ARCHIVO DESDE EL MOVIL Y LO GUARDA, EN FORMATO BASE64, EN UNA VARIABLE
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = fileUri.getPath();
                    Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath:" + "Valor-->:" + filePath);

                    if (filePath != null && !TextUtils.isEmpty(filePath)) {
                        InputStream imageStream = null;
                        try {
                            imageStream = getContext().getContentResolver().openInputStream(fileUri);
                            byte[] inputData = getBytes(imageStream);
                            strBase64 = Base64.getEncoder().encodeToString(inputData);
                        } catch (FileNotFoundException e) {

                        } catch (IOException e) {

                        }
                    } else {
                        strBase64 = "";
                    }
                }
                Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->encodedImage:" + "Valor-->:" + strBase64);
                break;
        }
    }
    //CONVIERTE INPUTSTRING EN ARREGLO DE BYTES
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void consultaBandejaEntrada(String s) {
        String strUrl = getResources().getString(R.string.url_ws_rest_login) + "obtenerResolucionesJudicialesBE";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, strUrl, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->response:" + "Valor-->:" + response.toString());
                            Utils.hideProgressDialog(progressDialog);
                            lista.clear();

                            if (response.length() > 0) {
                                JSONArray jsonArray = response;
                                Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->jsonArray.length():" + "Valor-->:" + jsonArray.length());
                                int j = 0;

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject res = jsonArray.getJSONObject(i);
                                    RecyInterDataSet lista_total = new RecyInterDataSet();

                                    lista_total.setId(res.getString("NumeroOficio"));
                                    lista_total.setApellidos(res.getString("DocumentoNombre"));
                                    lista_total.setNombres(res.getString("NombreInterno"));
                                    lista_total.setTipo_doc(res.getString("FilenameSolicitud"));
                                    String Estado = "";

                                    if (res.getString("Estado") == "1") {
                                        Estado = "ENVIADO";
                                    } else if (res.getString("Estado") == "2") {
                                        Estado = "ACLARATORIA";
                                    } else if (res.getString("Estado") == "3") {
                                        Estado = "CONFORME";
                                    } else if (res.getString("Estado") == "4") {
                                        Estado = "LEIDO";
                                    } else if (res.getString("Estado") == "5") {
                                        Estado = "DEVUELTO";
                                    } else {
                                        Estado = "SIN ESTADO";
                                    }
                                    lista_total.setDoc(Estado);

                                    lista.add(lista_total);

                                }

                                recyclerView_interno.setLayoutManager(new LinearLayoutManager(getContext()));
                                adapter = new RecyclerViewAdapter(getContext(), lista);
                                recyclerView_interno.setAdapter(adapter);

                                adapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener() {
                                    @Override
                                    public void onItemClick(int position, View v) {
                                        showMessage("Id de item seleccionado: " + lista.get(position).getId());
                                        ArrayList<String> cars = new ArrayList<String>();
                                        cars.add("Ver Archivo");
                                        cars.add("Conforme");
                                        cars.add("Aclaracion");
                                        cars.add("Devolucion");

                                        mSpinnerDialog = new SpinnerDialog(getContext(), cars, new SpinnerDialog.DialogListener() {
                                            public void cancelled() {
                                                // do your code here
                                            }
                                            public void ready(int n) {
                                                // do your code here
                                                String text = mSpinnerDialog.mSpinner.getSelectedItem().toString();
                                                String strURL = getResources().getString(R.string.url_repositorio_pj_inbox) + lista.get(position).getTipo_doc();
                                                Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->strURL:" + "Valor-->:" + strURL);
                                                if (text == "Ver Archivo") {
                                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                                    intent.setDataAndType(Uri.parse(strURL), "application/pdf");
                                                    startActivity(intent);
                                                }
                                            }
                                            public void selectFile() {
                                                // do your code here
                                                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                                                chooseFile.setType("*/*");
                                                chooseFile = Intent.createChooser(chooseFile, "Por favor, seleccione un archivo.");
                                                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                                            }
                                        });
                                        mSpinnerDialog.show();
                                    }
                                });
                                showMessage("Cantidad de resoluciones: " + j);
                            } else {
                                showMessage("No existen resoluciones para el usuario " + sharedPreferences.getString("usuario", ""));
                                return;
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->error:" + "Valor-->:" + error.toString());
                showMessage("Metodo-->consultaBandejaEntrada:" + "Variable-->error:" + "Valor-->:" + error.toString());
                Utils.hideProgressDialog(progressDialog);
               }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("pen_id", sharedPreferences.getString("pen_id", ""));
                headers.put("usuario", sharedPreferences.getString("usuario", ""));
                headers.put("clave", sharedPreferences.getString("clave", ""));
                return headers;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private void showMessage(String message) {
        Toasty.info(getContext(), message, Toasty.LENGTH_SHORT).show();
    }






    //TODO: POR VERIFICAR
    private void state_radio(String s) {
        this.cod_radio = s;
    }

    private void rest_dato(String s) {
        String url = "http://websvc.inpe.gob.pe:7575/wsminsa/DatoPaciente/Obtener";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String respo) {
                        Log.i("json1", respo);
                        Toast.makeText(getContext(), " " + respo, Toast.LENGTH_SHORT).show();
                        JSONObject response = null;
                        JSONObject res = null;
                        try {
                            response = new JSONObject(respo);
                            String Objson = response.getString("ObtenerResult");
                            res = new JSONObject(Objson);

                            Log.i("jsonObj", Objson);
                            String re = res.getString("paterno");
                            Intent intent = new Intent(getContext(), Ficha_Paciente.class);
                            intent.putExtra("fecha", "");
                            intent.putExtra("id_tipo_doc", res.getString("id_tipo_doc"));
                            intent.putExtra("tipo_doc", res.getString("tipo_doc"));
                            intent.putExtra("cod_interno", res.getString("tipo_doc"));
                            intent.putExtra("numero_doc", res.getString("numero_doc"));
                            intent.putExtra("nombres", res.getString("nombres"));
                            intent.putExtra("paterno", res.getString("paterno"));
                            intent.putExtra("materno", res.getString("materno"));
                            intent.putExtra("fec_nac", res.getString("fec_nac"));
                            //intent.putExtra("id_sexo", "");
                            intent.putExtra("TipSex", res.getString("id_sexo"));
                            intent.putExtra("celular", "");
                            intent.putExtra("celular_contacto", "");
                            intent.putExtra("correo", "");
                            //intent.putExtra("id_tipo_residencia", "");
                            //intent.putExtra("tipo_residencia", "");
                            intent.putExtra("direccion", res.getString("direccion"));
                            //  intent.putExtra("id_departamento", "");
                            //  intent.putExtra("departamento", "");
                            //  intent.putExtra("id_provincia", "");
                            //  intent.putExtra("provincia", "");
                            //  intent.putExtra("id_distrito", "");
                            //  intent.putExtra("distrito", "");
                            //  intent.putExtra("latitud", "");
                            //  intent.putExtra("longitud", "");
                            intent.putExtra("es_pers_salud", "");
                            intent.putExtra("id_profesion", "");
                            intent.putExtra("tiene_sintoma", "");
                            intent.putExtra("fecha_sintoma", "");
                            intent.putExtra("otro_sintoma", "");
                            intent.putExtra("id_usuario", "");
                            intent.putExtra("id_dato_paciente", "");
                            intent.putExtra("codigo_pais_celular", "");
                            intent.putExtra("codigo_pais_telefono", "");
                            startActivity(intent);
                            //JSONArray jsonArray = response.getJSONArray("ObtenerResult");
                            //JSONObject result = jsonArray.getJSONObject(0);
                            //Toast.makeText(getContext(), " " +re.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();

                            // Toast.makeText(getContext(), " errorkkkkk  "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("codigo_rp", "tiettietcod_inter.getText().toString()");
                //headers.put("Token", "{B709CE08-D2DE-4201-962B-3BBAC74C5952}");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //con.setRequestProperty("Token", "{B709CE08-D2DE-4201-962B-3BBAC74C5952}");
        requestQueue.add(stringRequest);


    }


    protected Message doInBackground(Message... params) {
        try {


            URL url = new URL("http://websvc.inpe.gob.pe:7575/wsminsa/datopaciente/obtener"); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("codigo_rp", "0253000549");

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());

            conn.disconnect();

        } catch (Exception e) {

        }
        return null;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                Log.i("STATUS", "5555");
                URL url = new URL("http://websvc.inpe.gob.pe:7575/wsminsa/datopaciente/obtener"); //in the real code, there is an ip and a port
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("codigo_rp", "0253000549");
                //jsonParam.put("message", params[0].getMessage());


                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

                os.flush();
                os.close();
                //showMessage("json "+conn.getResponseMessage());
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getContext(),
                    "ProgressDialog",
                    "Wait for " + " seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_doc:

                    lista.clear();
                    //Log.i("json1" , "navigation_doc");
                    //Toast.makeText(getContext(), " errorkkkkk  ", Toast.LENGTH_SHORT).show();
                    ly_datos.setVisibility(View.INVISIBLE);
                    ly_documento.setVisibility(View.INVISIBLE);
                    //showMessage("navigation_doc");
                    state_radio("");
                    navigation_doc("1");
                    ly_datos.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    ly_documento.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    return true;
                case R.id.navigation_apell:
                    //Log.i("json1" , "navigation_apell");
                    lista.clear();
                    state_radio("");
                    navigation_doc("2");
                    ly_datos.setVisibility(View.INVISIBLE);
                    ly_documento.setVisibility(View.INVISIBLE);
                    ly_datos.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    ly_documento.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    showMessage("navigation_apell");
                    //Toast.makeText(getContext(), " errorkkkkk  ", Toast.LENGTH_SHORT).show();
                    return true;

            }
            return false;
        }
    };

    private void navigation_doc(String s) {
        this.navi_esta = s;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigation_ll = (BottomNavigationView) root.findViewById(R.id.navigation_ll);
        ly_documento = (LinearLayout) root.findViewById(R.id.ly_documento);
        ly_datos = (LinearLayout) root.findViewById(R.id.ly_datos);
        //BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_ll);
        navigation_ll.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation_ll.setSelectedItemId(R.id.navigation_doc);
    }

    @OnClick(R.id.rd_dni)
    public void onClicrd_dni(View view) {
        state_radio("10");
        rd_ce.setChecked(false);
        rd_ptp.setChecked(false);
        rd_pass.setChecked(false);
    }

    @OnClick(R.id.rd_pass)
    public void onClicrd_pass(View view) {
        state_radio("4");
        rd_ce.setChecked(false);
        rd_ptp.setChecked(false);
        rd_dni.setChecked(false);
    }

    @OnClick(R.id.rd_ptp)
    public void onClicrd_ptp(View view) {
        state_radio("");
        rd_ce.setChecked(false);
        rd_pass.setChecked(false);
        rd_dni.setChecked(false);
    }

    @OnClick(R.id.rd_ce)
    public void onClicrd_ce(View view) {
        state_radio("3");
        rd_ptp.setChecked(false);
        rd_pass.setChecked(false);
        rd_dni.setChecked(false);
    }
}