package minsa.formulario.UI.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import minsa.formulario.DbHelper.DatoPacienteDbHelper;
import minsa.formulario.UI.Fragments.Dialog.ConglomeradoDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.DepartamentoDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.DistritoDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.ProvinciaDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.TipConglomeradoDialogFragment;
import minsa.formulario.AppController;
import minsa.formulario.DataSet.ConglomeradoDataSet;
import minsa.formulario.DataSet.DepartDataSet;
import minsa.formulario.DataSet.DistriDataSet;
import minsa.formulario.DataSet.ProvinDataSet;
import minsa.formulario.DataSet.TipConglomeradoDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.AplPcrDbHelper;
import minsa.formulario.DbHelper.ConglomeradoDbHelper;
import minsa.formulario.DbHelper.EvolucionDbHelper;
import minsa.formulario.DbHelper.PaisDbHelper;
import minsa.formulario.DbHelper.ParentescoDbHelper;
import minsa.formulario.DbHelper.PerSalDbHelper;
import minsa.formulario.DbHelper.ProcedDbHelper;
import minsa.formulario.DbHelper.Riesgo2DbHelper;
import minsa.formulario.DbHelper.SeveriDbHelper;
import minsa.formulario.DbHelper.SexBioDbHelper;
import minsa.formulario.DbHelper.SigAlarm2DbHelper;
import minsa.formulario.DbHelper.SigAlarmDbHelper;
import minsa.formulario.DbHelper.TieSinDbHelper;
import minsa.formulario.DbHelper.TipConglomeradoDbHelper;
import minsa.formulario.DbHelper.TipMueDbHelper;
import minsa.formulario.DbHelper.TipSegDbHelper;
import minsa.formulario.DbHelper.TipVivDbHelper;
import minsa.formulario.DbHelper.UsuariDbHelper.UsuariTableC;
import minsa.formulario.DbHelper.TipDocDbHelper.TipDocTableC;
import minsa.formulario.DbHelper.ProfesDbHelper.ProfesTableC;
import minsa.formulario.DbHelper.SintomDbHelper.SintomTableC;
import minsa.formulario.DbHelper.TipResDbHelper.TipResTableC;
import minsa.formulario.DbHelper.RiesgoDbHelper.RiesgoTableC;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.PreferencesManager;
import minsa.formulario.Util.Utils;

public class AccesoActivity extends AppCompatActivity implements
        DepartamentoDialogFragment.DepartamentoDialogListener,
        ProvinciaDialogFragment.ProvinciaDialogListener,
        DistritoDialogFragment.DistritoDialogListener,
        TipConglomeradoDialogFragment.TipConglomeradoDialogListener,
        ConglomeradoDialogFragment.ConglomeradoDialogListener {

    @BindView(R.id.tietUsuario) TextInputEditText tietUsuario;
    @BindView(R.id.tietClave) TextInputEditText tietClave;
    @BindView(R.id.cbConglomerado) CheckBox cbConglomerado;
    @BindView(R.id.llConglomerado) LinearLayout llConglomerado;

    @BindView(R.id.tvDepartamento) TextView tvDepartamento;
    @BindView(R.id.tvProvincia) TextView tvProvincia;
    @BindView(R.id.tvDistrito) TextView tvDistrito;
    @BindView(R.id.tvTipoConglomerado) TextView tvTipoConglomerado;
    @BindView(R.id.tvConglomerado) TextView tvConglomerado;

    private String vTokens;
    private DirWebDataSet vDstDirWeb;
    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vUsuariCursor;
    private UsuariDataSet vDsUsuari = UsuariDataSet.getInstance();

    private ProgressDialog progressDialog;

    private ColorStateList oldColors;

    private String id_departamento = "";
    private String id_provincia = "";
    private String id_distrito = "";

    private String id_tipo_conglomerado = "";
    private String tipo_conglomerado = "";

    private String id_conglomerado = "";
    private String conglomerado = "";

    JSONObject loginSession;
    JSONObject permisos;
//    CheckBox keeplog = (CheckBox) findViewById(R.id.tietClave);
//    boolean isChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_menu);
        setContentView(R.layout.acceso_activity);
        mPrinciDbHelper = new PrinciDbHelper(getApplicationContext());
        ButterKnife.bind(this);

        SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
        //String miclave = settings1.getString("clave", "");
        Log.i("INFORMACION : ","ANTES");
        String idEspirometro = settings1.getString("idEspirometro", "");
        String valor=tietUsuario.getText().toString();
        //int id=Integer.parseInt(idEspirometro);

        if  (valor.equalsIgnoreCase(idEspirometro) )  {
            Intent intent = new Intent(AccesoActivity.this, PrincipalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK );
            //intent.setFlags(Intent.FLAG);
            startActivity(intent);
            Log.i("INFORMACION:", "Metodo-->onCreate:" + "Variable-->ingreso con clave:" + "Valor-->:" + "ingreso con clave");
        }
        else {
            Log.i("INFORMACION:", "Metodo-->onCreate:" + "Variable-->ingreso:" + "Valor-->:" + "sin clave");

        }

    }



    @OnClick(R.id.btnIngresar)
    public void onClicIngresar(View view) {

        if(tietUsuario.getText().toString().isEmpty()) {
            showMessage("Por favor, ingrese usuario");

            return;
        }
        if(tietClave.getText().toString().isEmpty()) {
            showMessage("Por favor, ingrese clave");

            return;
        }
        MDbsSeaUsuaio();
    }

    private void MDbsSeaUsuaio(){
        progressDialog = Utils.showProgressDialog(AccesoActivity.this, "Validando...");
        progressDialog.show();
        validarLogin("");
    }

    private void validarLogin(String s) {
        String strUrl = getResources().getString(R.string.url_ws_rest_login) + "ValidarUsuarioEquipo";
        Log.i("INFORMACION:", "Metodo-->validarLogin:" + "Variable-->strUrl:" + "Valor-->:" + strUrl);

        Map<String, String> params = new HashMap();
        //params.put("KEY_SISTEMA", getResources().getString(R.string.KEY_SISTEMA));
        params.put("codigo_equipo", tietUsuario.getText().toString());
        params.put("codigo_usuario", tietClave.getText().toString());

        JSONObject parameters = new JSONObject(params);
        Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->parameters:" + "Valor-->:" + parameters.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, strUrl, parameters,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("INFORMACION:", "Metodo-->validarLogin:" + "Variable-->response:" + "Valor-->:" + response.toString());
                            Utils.hideProgressDialog(progressDialog);
                            if(response.length()>0){
                                loginSession = response;
                                Log.i("INFORMACION:", "Metodo-->validarLogin:" + "Variable-->loginSession.length():" + "Valor-->:" + loginSession.length());
                                int j=0;
                                //Log.i("INFORMACION:", "Metodo-->validarLogin:1" + "Variable-->lloginSession.getString(\"USUARIO_ID\"):" + "Valor-->:" + loginSession.getString("USUARIO_ID"));
                                if (loginSession.getString("usuario_existe").equals("0")) {
                                    showMessage("Codigo de usuario incorrecto.");
                                    return;


                                } else {
                                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.ID_APP, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                                    SharedPreferences.Editor editor2 = settings.edit();
                                    editor2.putString("clave", tietClave.getText().toString());
                                    editor2.putString("idEspirometro", tietUsuario.getText().toString());
                                    editor2.commit();


                                    editor.putString("clave", tietClave.getText().toString());
                                    editor.apply();
                                    showMessage("Bienvenido: " + sharedPreferences.getString("usuario", ""));
                                    Intent intent = new Intent(AccesoActivity.this, PrincipalActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK );
                                    startActivity(intent);
                                }


                            }
                            else {
                                showMessage("Datos incorrectos");
                                return;
                            }

                        } catch (Exception e) { Log.i("err" , e.toString()); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFORMACION:", "Metodo-->validarLogin:" + "Variable-->error:" + "Valor-->:" + error.toString());
                showMessage("Metodo-->validarLogin:" + "Variable-->error:" + "Valor-->:" + error.toString());
                Utils.hideProgressDialog(progressDialog);
            }
        }){
            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");

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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    private void getPermisos() {
        String strUrl = getResources().getString(R.string.url_ws_rest_login) + "GetPermisos";

        Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->loginSession:" + "Valor-->:" + loginSession.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, strUrl, loginSession,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->response:" + "Valor-->:" + response.toString());
                            Utils.hideProgressDialog(progressDialog);

                            if (response.length() > 0) {
                                permisos = response;

                                SharedPreferences sharedPreferences = getSharedPreferences(Constants.ID_APP, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //editor.putString("loginSession", response.toString());
                                editor.putString("permisos", permisos.toString());
                                //editor.putString("clave", tietClave.getText().toString());
                                editor.apply();
                                showMessage("Bienvenido: " + sharedPreferences.getString("usuario", ""));
                                Intent intent = new Intent(AccesoActivity.this, PrincipalActivity.class);
                                startActivity(intent);

                            } else {
                                showMessage("Ocurrio un error.");
                                return;
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
                showMessage("Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
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
        RequestQueue requestQueue = Volley.newRequestQueue(AccesoActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private void showMessage(String message) {
        Toasty.info(getApplicationContext(), message, Toasty.LENGTH_SHORT).show();

    }




    //TODO: POR REVISAR
    private void setPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.ID_APP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", tietUsuario.getText().toString());
        editor.putString("clave", tietClave.getText().toString());
        editor.apply();
    }

    private void loginLocal() {
        vUsuariCursor = mPrinciDbHelper.getAllTwoValues(UsuariTableC.UsuariTableN,UsuariTableC.UsuariUsuIng,UsuariTableC.UsuariConIng,tietUsuario.getText().toString().trim(),tietClave.getText().toString().trim());
        if(vUsuariCursor.getCount() != 0) {
            while(vUsuariCursor.moveToNext()) {
                vDsUsuari.setUsuariIdenti(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariIdenti)));
                vDsUsuari.setUsuariNroDoc(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariNroDoc)));
                vDsUsuari.setUsuariNomApe(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariNombre)) + " " +
                        vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariApePat)) + " " +
                        vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariApeMat)));
                vDsUsuari.setUsuarioNom(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariNombre)));
                vDsUsuari.setUsuarioPaterno(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariApePat)));
                vDsUsuari.setUsuarioMaterno(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariApeMat)));
                vDsUsuari.setUsuarioIdTipoDoc(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariTableC.UsuariTipDoc)));

                if(!id_conglomerado.isEmpty()) {
                    PreferencesManager.setIdConglomerado(getApplicationContext(), id_conglomerado);
                    PreferencesManager.setConglomerado(getApplicationContext(), conglomerado);

                    PreferencesManager.setIdTipoConglomerado(getApplicationContext(), id_tipo_conglomerado);
                    PreferencesManager.setTipoConglomerado(getApplicationContext(), tipo_conglomerado);
                }
            }

            setPreferences();

            Intent intent = new Intent(AccesoActivity.this, MenuActivity.class);
            startActivity(intent);
        } else {
            Utils.showDialog(AccesoActivity.this, "Inicio de sesión - Local", "El usuario ingresado no es válido");
        }
    }

    private void MSerGetTokens(int origin) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, vDstDirWeb.getDirWebApiUrl() + "/token",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myObject = new JSONObject(response);
                            String Tokens = myObject.getString("access_token");
                            vTokens = Tokens;

                            if(origin == 0) {
                                MSerGetTipDoc();
                            } else if (origin == 1) {
                                getTipoConglomerado();
                            } else {
                                getConglomerado(id_tipo_conglomerado, id_departamento + id_provincia + id_distrito);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);
                        switch (origin) {
                            case 0:
/*                                Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                                VolleyLog.d("volley", "Error: " + error.getMessage());
                                error.printStackTrace();*/

                                Utils.showDialogWithTwoAction(AccesoActivity.this, "Inicio de sesión", "El servicio no se encuentra disponible, presione REINTENTAR para establecer una nueva conexión o presione SIN INTERNET para iniciar sesión de forma local.", "SIN INTERNET", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        loginLocal();
                                    }
                                }, "REINTENTAR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MSerGetTokens(0);
                                    }
                                });

                                break;
                            case 1:
                                showTipoConglomerado(-1);
                                break;
                            case 2:
                                showConglomerado();
                                break;
                        }
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetAcceso() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,vDstDirWeb.getDirWebApiUrl() + Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog(progressDialog);
                        try {
                            JSONObject myObject = new JSONObject(response);
                            if(myObject.getString("code").equals("200")){
                                JSONObject vLogged = myObject.getJSONObject("logged");
                                JSONObject vPersonal= vLogged.getJSONObject("personal");

                                ContentValues UsuariValues = new ContentValues();
                                UsuariValues.put(UsuariTableC.UsuariIdenti, vPersonal.getString("idUsuario"));
                                UsuariValues.put(UsuariTableC.UsuariUsuIng, tietUsuario.getText().toString());
                                UsuariValues.put(UsuariTableC.UsuariConIng, tietClave.getText().toString());
                                UsuariValues.put(UsuariTableC.UsuariTipDoc, vPersonal.getString("idTipoDoc"));
                                UsuariValues.put(UsuariTableC.UsuariNroDoc, vPersonal.getString("nroDocumento"));
                                UsuariValues.put(UsuariTableC.UsuariNombre, vPersonal.getString("nombres"));
                                UsuariValues.put(UsuariTableC.UsuariApePat, vPersonal.getString("apePaterno"));
                                UsuariValues.put(UsuariTableC.UsuariApeMat, vPersonal.getString("apeMaterno").replace("null", ""));
                                UsuariValues.put(UsuariTableC.UsuariActivo, "1");

                                Cursor cursorUs = mPrinciDbHelper.getAllPrinciOne(UsuariTableC.UsuariTableN, UsuariTableC.UsuariIdenti, vPersonal.getString("idUsuario"));
                                if(cursorUs.getCount() == 0) {
                                    mPrinciDbHelper.savePrinci(UsuariTableC.UsuariTableN, UsuariValues);
                                }

                                vDsUsuari.setUsuariIdenti(vPersonal.getString("idUsuario"));
                                vDsUsuari.setUsuariNroDoc(vPersonal.getString("nroDocumento"));
                                vDsUsuari.setUsuariNomApe(vPersonal.getString("nombres") + " " + vPersonal.getString("apePaterno") + " " + vPersonal.getString("apeMaterno"));
                                vDsUsuari.setUsuarioNom(vPersonal.getString("nombres"));
                                vDsUsuari.setUsuarioPaterno(vPersonal.getString("apePaterno"));
                                vDsUsuari.setUsuarioMaterno(vPersonal.getString("apeMaterno").replace("null", ""));
                                vDsUsuari.setUsuarioIdTipoDoc(vPersonal.getString("idTipoDoc"));

                                if(!id_conglomerado.isEmpty()) {
                                    PreferencesManager.setIdConglomerado(getApplicationContext(), id_conglomerado);
                                    PreferencesManager.setConglomerado(getApplicationContext(), conglomerado);
                                    PreferencesManager.setIdTipoConglomerado(getApplicationContext(), id_tipo_conglomerado);
                                    PreferencesManager.setTipoConglomerado(getApplicationContext(), tipo_conglomerado);
                                }

                                setPreferences();

                                Intent intent = new Intent(AccesoActivity.this, MenuActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AccesoActivity.this, "El usuario ingresado no es válido", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                params.put("Content-Type", vDstDirWeb.getDirWebConTypL());
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String str =
                        "{\"username\":"+"\""+tietUsuario.getText().toString()+"\""+","+
                         "\"password\":"+"\""+tietClave.getText().toString()+"\""+"}";
                return str.getBytes();
            };

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetTipDoc() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_TIPO_DOCUMENTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(TipDocTableC.TipDocTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectTipDoc = myArray.getJSONObject(i);
                                ContentValues TipDocValues = new ContentValues();
                                TipDocValues.put(TipDocTableC.TipDocIdenti, jObjectTipDoc.getString("id"));
                                TipDocValues.put(TipDocTableC.TipDocDescri, jObjectTipDoc.getString("descripcion"));
                                TipDocValues.put(TipDocTableC.slug, jObjectTipDoc.getString("slug"));
                                mPrinciDbHelper.savePrinci(TipDocTableC.TipDocTableN, TipDocValues);
                            }
                            MSerGetProfes();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);
                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetProfes(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_PROFESIONES, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(ProfesTableC.ProfesTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectProfes = myArray.getJSONObject(i);
                                ContentValues ProfesValues = new ContentValues();
                                ProfesValues.put(ProfesTableC.ProfesIdenti, jObjectProfes.getString("id"));
                                ProfesValues.put(ProfesTableC.ProfesDescri, jObjectProfes.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(ProfesTableC.ProfesTableN, ProfesValues);
                            }
                            MSerGetSintom();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetSintom(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_SINTOMAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(SintomTableC.SintomTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectSintom = myArray.getJSONObject(i);
                                ContentValues SintomValues = new ContentValues();
                                SintomValues.put(SintomTableC.SintomIdenti, jObjectSintom.getString("id"));
                                SintomValues.put(SintomTableC.SintomDescri, jObjectSintom.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(SintomTableC.SintomTableN, SintomValues);
                            }
                            MSerGetTipRes();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetTipRes(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_TIPO_RESULTADO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(TipResTableC.TipResTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectTipRes = myArray.getJSONObject(i);
                                ContentValues TipResValues = new ContentValues();
                                TipResValues.put(TipResTableC.TipResIdenti, jObjectTipRes.getString("id"));
                                TipResValues.put(TipResTableC.TipResDescri, jObjectTipRes.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(TipResTableC.TipResTableN, TipResValues);
                            }
                            MSerGetRiesgo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetRiesgo(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_RIESGOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(RiesgoTableC.RiesgoTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues RiesgoValues = new ContentValues();
                                RiesgoValues.put(RiesgoTableC.RiesgoIdenti, jObjectRiesgo.getString("id"));
                                RiesgoValues.put(RiesgoTableC.RiesgoDescri, jObjectRiesgo.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(RiesgoTableC.RiesgoTableN, RiesgoValues);
                            }
                            MSerGetTipoSeguro();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetTipoSeguro(){
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_TIPOS_SEGUROS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(TipSegDbHelper.TipSegTableC.TipSegTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues TipSegValues = new ContentValues();
                                TipSegValues.put(TipSegDbHelper.TipSegTableC.TipSegIdenti, jObjectRiesgo.getString("id"));
                                TipSegValues.put(TipSegDbHelper.TipSegTableC.TipSegDescri, jObjectRiesgo.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(TipSegDbHelper.TipSegTableC.TipSegTableN, TipSegValues);
                            }
                            MSerGetSignosAlarmas2();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetSignosAlarmas2() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_SIGNOS_ALARMAS_2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2TableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues TipSegValues = new ContentValues();
                                TipSegValues.put(SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Identi, jObjectRiesgo.getString("id"));
                                TipSegValues.put(SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Descri, jObjectRiesgo.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2TableN, TipSegValues);
                            }
                            MSerGetRiesgos2();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetRiesgos2(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_RIESGOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(Riesgo2DbHelper.RiesgoTableC.RiesgoTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues TipSegValues = new ContentValues();
                                TipSegValues.put(Riesgo2DbHelper.RiesgoTableC.RiesgoIdenti, jObjectRiesgo.getString("id"));
                                TipSegValues.put(Riesgo2DbHelper.RiesgoTableC.RiesgoDescri, jObjectRiesgo.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(Riesgo2DbHelper.RiesgoTableC.RiesgoTableN, TipSegValues);
                            }
                            MSerGetParentescos();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetParentescos(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_PARENTESCOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(ParentescoDbHelper.ParentescoTableC.ParentescoTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues TipSegValues = new ContentValues();
                                TipSegValues.put(ParentescoDbHelper.ParentescoTableC.ParentescoIdenti, jObjectRiesgo.getString("id"));
                                TipSegValues.put(ParentescoDbHelper.ParentescoTableC.ParentescoDescri, jObjectRiesgo.getString("parentesco"));
                                mPrinciDbHelper.savePrinci(ParentescoDbHelper.ParentescoTableC.ParentescoTableN, TipSegValues);
                            }
                            MSerGetPais();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetPais(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_PAISES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(PaisDbHelper.PaisTableC.PaisTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues TipSegValues = new ContentValues();
                                TipSegValues.put(PaisDbHelper.PaisTableC.PaisIdenti, jObjectRiesgo.getString("id"));
                                TipSegValues.put(PaisDbHelper.PaisTableC.PaisDescri, jObjectRiesgo.getString("pais"));
                                mPrinciDbHelper.savePrinci(PaisDbHelper.PaisTableC.PaisTableN, TipSegValues);
                            }
                            MSerGetTiposMuestras();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetTiposMuestras(){
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_TIPOS_MUESTRAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray myArray = object.getJSONArray("lstMuestras");
                            mPrinciDbHelper.deleteAll(TipMueDbHelper.TipMueTableC.TipMueTableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues TipSegValues = new ContentValues();
                                TipSegValues.put(TipMueDbHelper.TipMueTableC.TipMueIdenti, jObjectRiesgo.getString("id"));
                                TipSegValues.put(TipMueDbHelper.TipMueTableC.TipMueDescri, jObjectRiesgo.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(TipMueDbHelper.TipMueTableC.TipMueTableN, TipSegValues);
                            }
                            MSerGetSignosAlarmas();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetSignosAlarmas(){
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET,
                vDstDirWeb.getDirWebApiUrl() + Constants.URL_SIGNOS_ALARMAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(SigAlarmDbHelper.TableC.TableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues TipSegValues = new ContentValues();
                                TipSegValues.put(SigAlarmDbHelper.TableC.id, jObjectRiesgo.getString("id"));
                                TipSegValues.put(SigAlarmDbHelper.TableC.descripcion, jObjectRiesgo.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(SigAlarmDbHelper.TableC.TableN, TipSegValues);
                            }

                            MSerGetEvoluciones();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MSerGetEvoluciones() {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + Constants.URL_EVOLUCIONES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray myArray = new JSONArray(response);
                            mPrinciDbHelper.deleteAll(EvolucionDbHelper.TableC.TableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject object = myArray.getJSONObject(i);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(EvolucionDbHelper.TableC.id, object.getString("id"));
                                contentValues.put(EvolucionDbHelper.TableC.descripcion, object.getString("descripcion"));
                                mPrinciDbHelper.savePrinci(EvolucionDbHelper.TableC.TableN, contentValues);
                            }

                            MStrGetSexBio();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);

                        Toast.makeText(AccesoActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void MStrGetSexBio(){
        String[] vArrSexBio = getResources().getStringArray(R.array.formul_opcn00_pren07);

        mPrinciDbHelper.deleteAll(SexBioDbHelper.SexBioTableC.SexBioTableN);
        for (int i = 0; i < vArrSexBio.length; i++) {
            String sArrSexBio[] = vArrSexBio[i].split("-");
            ContentValues SexBioValues = new ContentValues();
            SexBioValues.put(SexBioDbHelper.SexBioTableC.SexBioIdenti, sArrSexBio[0]);
            SexBioValues.put(SexBioDbHelper.SexBioTableC.SexBioDescri, sArrSexBio[1]);
            mPrinciDbHelper.savePrinci(SexBioDbHelper.SexBioTableC.SexBioTableN, SexBioValues);
        }

        MStrGetTipViv();
    }

    private void MStrGetTipViv(){

        String[] vArrTipViv = getResources().getStringArray(R.array.formul_opcn00_pren11);

        mPrinciDbHelper.deleteAll(TipVivDbHelper.TipVivTableC.TipVivTableN);
        for (int i = 0; i < vArrTipViv.length; i++) {
            String sArrTipViv[] = vArrTipViv[i].split("-");
            ContentValues TipVivValues = new ContentValues();
            TipVivValues.put(TipVivDbHelper.TipVivTableC.TipVivIdenti, sArrTipViv[0]);
            TipVivValues.put(TipVivDbHelper.TipVivTableC.TipVivDescri, sArrTipViv[1]);
            mPrinciDbHelper.savePrinci(TipVivDbHelper.TipVivTableC.TipVivTableN, TipVivValues);
        }

        MSerGetPerSal();

    }

    private void MSerGetPerSal(){

        String[] vArrPerSal = getResources().getStringArray(R.array.formul_opcn00_pren17);

        mPrinciDbHelper.deleteAll(PerSalDbHelper.PerSalTableC.PerSalTableN);
        for (int i = 0; i < vArrPerSal.length; i++) {
            String sArrPerSal[] = vArrPerSal[i].split("-");
            ContentValues PerSalValues = new ContentValues();
            PerSalValues.put(PerSalDbHelper.PerSalTableC.PerSalIdenti, sArrPerSal[0]);
            PerSalValues.put(PerSalDbHelper.PerSalTableC.PerSalDescri, sArrPerSal[1]);
            mPrinciDbHelper.savePrinci(PerSalDbHelper.PerSalTableC.PerSalTableN, PerSalValues);
        }

        MSerGetTieSin();
    }

    private void MSerGetTieSin(){
        String[] vArrTieSin = getResources().getStringArray(R.array.formul_opcn00_pren19);

        mPrinciDbHelper.deleteAll(TieSinDbHelper.TieSinTableC.TieSinTableN);
        for (int i = 0; i < vArrTieSin.length; i++) {
            String sArrTieSin[] = vArrTieSin[i].split("-");
            ContentValues TieSinValues = new ContentValues();
            TieSinValues.put(TieSinDbHelper.TieSinTableC.TieSinIdenti, sArrTieSin[0]);
            TieSinValues.put(TieSinDbHelper.TieSinTableC.TieSinDescri, sArrTieSin[1]);
            mPrinciDbHelper.savePrinci(TieSinDbHelper.TieSinTableC.TieSinTableN, TieSinValues);
        }

        MSerGetProced();
    }

    private void MSerGetProced() {
        String[] vArrProced = getResources().getStringArray(R.array.formul_opcn01_pren03);

        mPrinciDbHelper.deleteAll(ProcedDbHelper.ProcedTableC.ProcedTableN);
        for (int i = 0; i < vArrProced.length; i++) {
            String sArrProced[] = vArrProced[i].split("-");
            ContentValues ProcedValues = new ContentValues();
            ProcedValues.put(ProcedDbHelper.ProcedTableC.ProcedIdenti, sArrProced[0]);
            ProcedValues.put(ProcedDbHelper.ProcedTableC.ProcedDescri, sArrProced[1]);
            mPrinciDbHelper.savePrinci(ProcedDbHelper.ProcedTableC.ProcedTableN, ProcedValues);
        }
        MSerGetSeveri();
    }

    private void MSerGetSeveri() {
        String[] vArrSeveri = getResources().getStringArray(R.array.formul_opcn01_pren06);

        mPrinciDbHelper.deleteAll(SeveriDbHelper.SeveriTableC.SeveriTableN);
        for (int i = 0; i < vArrSeveri.length; i++) {
            String sArrSeveri[] = vArrSeveri[i].split("-");
            ContentValues SeveriValues = new ContentValues();
            SeveriValues.put(SeveriDbHelper.SeveriTableC.SeveriIdenti, sArrSeveri[0]);
            SeveriValues.put(SeveriDbHelper.SeveriTableC.SeveriDescri, sArrSeveri[1]);
            mPrinciDbHelper.savePrinci(SeveriDbHelper.SeveriTableC.SeveriTableN, SeveriValues);
        }

        MSerGetAplPcr();
    }

    private void MSerGetAplPcr(){

        String[] vArrAplPcr = getResources().getStringArray(R.array.formul_opcn01_pren09);

        mPrinciDbHelper.deleteAll(AplPcrDbHelper.AplPcrTableC.AplPcrTableN);
        for (int i = 0; i < vArrAplPcr.length; i++) {
            String sArrAplPcr[] = vArrAplPcr[i].split("-");
            ContentValues AplPcrValues = new ContentValues();
            AplPcrValues.put(AplPcrDbHelper.AplPcrTableC.AplPcrIdenti, sArrAplPcr[0]);
            AplPcrValues.put(AplPcrDbHelper.AplPcrTableC.AplPcrDescri, sArrAplPcr[1]);
            mPrinciDbHelper.savePrinci(AplPcrDbHelper.AplPcrTableC.AplPcrTableN, AplPcrValues);
        }

        MSerGetAcceso();
    }

    private void getTipoConglomerado() {
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET,
                vDstDirWeb.getDirWebApiUrl() + Constants.URL_TIPO_CONGLOMERADO_2 + id_departamento + id_provincia + id_distrito,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog(progressDialog);

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray myArray = object.getJSONArray("lista");
                            mPrinciDbHelper.deleteAll(TipConglomeradoDbHelper.TableC.TableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(TipConglomeradoDbHelper.TableC.id, jObjectRiesgo.getString("id_tipo_conglomerado"));
                                contentValues.put(TipConglomeradoDbHelper.TableC.descripcion, jObjectRiesgo.getString("descripcion"));
                                contentValues.put(TipConglomeradoDbHelper.TableC.habitacional_concurrencia, "");
                                mPrinciDbHelper.savePrinci(TipConglomeradoDbHelper.TableC.TableN, contentValues);
                            }

                            showTipoConglomerado(0);
                        } catch (JSONException e) {
                            showTipoConglomerado(-1);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);
                        showTipoConglomerado(-1);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void getConglomerado(String tipConglomerado, String codUbigeo) {
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,vDstDirWeb.getDirWebApiUrl() + Constants.URL_CONGLOMERADO + tipConglomerado + "/" + codUbigeo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog(progressDialog);

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray myArray = object.getJSONArray("lista");
                            mPrinciDbHelper.deleteAll(ConglomeradoDbHelper.TableC.TableN);
                            for (int i = 0; i < myArray.length(); i++) {
                                JSONObject jObjectRiesgo = myArray.getJSONObject(i);
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(ConglomeradoDbHelper.TableC.id, jObjectRiesgo.getString("id"));
                                contentValues.put(ConglomeradoDbHelper.TableC.nombre, jObjectRiesgo.getString("nombre"));
                                contentValues.put(ConglomeradoDbHelper.TableC.numero_puestos, jObjectRiesgo.getString("numero_puestos"));
                                contentValues.put(ConglomeradoDbHelper.TableC.tamano_muestral, jObjectRiesgo.getString("tamano_muestral"));
                                contentValues.put(ConglomeradoDbHelper.TableC.ubigeo, jObjectRiesgo.getString("ubigeo"));
                                contentValues.put(ConglomeradoDbHelper.TableC.direccion, jObjectRiesgo.getString("direccion"));
                                contentValues.put(ConglomeradoDbHelper.TableC.latitud, jObjectRiesgo.getString("latitud"));
                                contentValues.put(ConglomeradoDbHelper.TableC.longitud, jObjectRiesgo.getString("longitud"));
                                contentValues.put(ConglomeradoDbHelper.TableC.id_tipo_conglomerado, jObjectRiesgo.getString("id_tipo_conglomerado"));
                                contentValues.put(ConglomeradoDbHelper.TableC.eliminado, jObjectRiesgo.getString("eliminado"));
                                contentValues.put(ConglomeradoDbHelper.TableC.id_usuario, jObjectRiesgo.getString("id_usuario"));
                                mPrinciDbHelper.savePrinci(ConglomeradoDbHelper.TableC.TableN, contentValues);
                            }

                            showConglomerado();
                        } catch (JSONException e) {
                            showConglomerado();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.hideProgressDialog(progressDialog);
                        showConglomerado();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }
        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void showTipoConglomerado(int origin) {
        TipConglomeradoDialogFragment fragment = TipConglomeradoDialogFragment.newInstance(origin, id_departamento + id_provincia + id_distrito);
        fragment.delegate = AccesoActivity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    private void showConglomerado() {
        ConglomeradoDialogFragment fragment = ConglomeradoDialogFragment.newInstance(id_tipo_conglomerado, id_departamento + id_provincia + id_distrito);
        fragment.delegate = AccesoActivity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
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

    @Override
    public void setDepartamento(DepartDataSet dataSet) {
        tvDepartamento.setText(dataSet.getDepartNombre());
        tvDepartamento.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_departamento = dataSet.getDepartCodigo();

        tvProvincia.setText("Seleccionar");
        tvProvincia.setTextColor(oldColors);
        id_provincia = "";

        tvDistrito.setText("Seleccionar");
        tvDistrito.setTextColor(oldColors);
        id_distrito = "";

        tvConglomerado.setText("Seleccionar");
        tvConglomerado.setTextColor(oldColors);
        id_conglomerado = "";
        conglomerado = "";
    }

    @Override
    public void setProvincia(ProvinDataSet dataSet) {
        tvProvincia.setText(dataSet.getProvinNombre());
        tvProvincia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_provincia = dataSet.getProvinCodigo();

        tvDistrito.setText("Seleccionar");
        tvDistrito.setTextColor(oldColors);
        id_distrito = "";
    }


    @Override
    public void setDistrito(DistriDataSet dataSet) {
        tvDistrito.setText(dataSet.getDistriNombre());
        tvDistrito.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_distrito = dataSet.getDistriCodigo();

        tvTipoConglomerado.setText("Seleccionar");
        tvTipoConglomerado.setTextColor(oldColors);
        id_tipo_conglomerado = "";
        tipo_conglomerado = "";


        tvConglomerado.setText("Seleccionar");
        tvConglomerado.setTextColor(oldColors);
        id_conglomerado = "";
        conglomerado = "";
    }

    @Override
    public void setTipConglomerado(TipConglomeradoDataSet dataSet) {
        tvTipoConglomerado.setText(dataSet.getDescripcion());
        tvTipoConglomerado.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_tipo_conglomerado = dataSet.getId();
        tipo_conglomerado = dataSet.getDescripcion();

        tvConglomerado.setText("Seleccionar");
        tvConglomerado.setTextColor(oldColors);
        id_conglomerado = "";
        conglomerado = "";
    }

    @Override
    public void setConglomerado(ConglomeradoDataSet dataSet) {
        tvConglomerado.setText(dataSet.getNombre());
        tvConglomerado.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_conglomerado = dataSet.getId();
        conglomerado = dataSet.getNombre();
    }

}