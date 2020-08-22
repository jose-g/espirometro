package minsa.formulario.UI.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

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

import butterknife.ButterKnife;
import butterknife.OnClick;
import minsa.formulario.AppController;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.TipDocDbHelper;
import minsa.formulario.R;
import minsa.formulario.UI.Activity.f200.F200Activity;
import minsa.formulario.UI.Activity.f300.F300Activity;
import minsa.formulario.Util.Constants;

public class MenPriActivity extends AppCompatActivity {

    private Cursor vCursorTipDoc;
    private String vTipDoc;
    private List<TipDocDataSet> vLisTipDoc;
    private ArrayList<String> vALiTipDoc;
    private PrinciDbHelper mPrinciDbHelper;
    private DirWebDataSet vDstDirWeb;
    private String vTokens;
    private String vFor100;
    private String vIdenti;

    private String vNroDoc;
    private String vApePat;
    private String vApeMat;
    private String vNombre;
    private String vFecNac;
    private String vTipSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menpri_activity);

        ButterKnife.bind(this);

        vLisTipDoc = new ArrayList<TipDocDataSet>();
        vALiTipDoc = new ArrayList<>();

        mPrinciDbHelper = new PrinciDbHelper(this);
        vDstDirWeb = DirWebDataSet.getInstance();

        vIdenti = "0";
        vTipDoc = "0";
        vNroDoc = "0";
        vApePat = "0";
        vApeMat = "0";
        vNombre = "0";
        vFecNac = "0";
        vTipSex = "0";

        vNroDoc = "0";

        handleSSLHandshake();
    }

    @OnClick(R.id.llF100)
    public void onClicF100(View view) {
        MButSerFic100();
    }

    @OnClick(R.id.llF200)
    public void onClicF200(View view) {
        Intent intent = new Intent(MenPriActivity.this, F200Activity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llF300)
    public void onClicF300(View view) {
//                Intent intent = new Intent(MenPriActivity.this, FormulActivity.class);
//                startActivity(intent);
        Intent intent = new Intent(MenPriActivity.this, F300Activity.class);
        startActivity(intent);
    }

    private void MButSerFic100(){

        LinearLayout layout = new LinearLayout(MenPriActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(70, 0, 70, 0);

        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lps.setMargins(75, 0, 70, 0);

        final TextView vLtvFic100a1 = new TextView(MenPriActivity.this);
        layout.addView(vLtvFic100a1);

        final TextView vLtvFic100a2 = new TextView(MenPriActivity.this);
        vLtvFic100a2.setTextSize(16);
        vLtvFic100a2.setTextColor(getResources().getColor(R.color.colorBlack));
        vLtvFic100a2.setLayoutParams(lp);
        vLtvFic100a2.setText(getResources().getString(R.string.formul_fic100_1));
        layout.addView(vLtvFic100a2);

        final Spinner vLspFic100a3 = new Spinner(MenPriActivity.this);

        vLisTipDoc.clear();

        vCursorTipDoc = mPrinciDbHelper.getAllPrinci(TipDocDbHelper.TipDocTableC.TipDocTableN);
        while(vCursorTipDoc.moveToNext()){
            TipDocDataSet vDstTipDoc = new TipDocDataSet();
            vDstTipDoc.setTipDocIdenti(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocIdenti)));
            vDstTipDoc.setTipDocDescri((vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocDescri))).toUpperCase());
            vLisTipDoc.add(vDstTipDoc);
        }

        vALiTipDoc.clear();
        for (TipDocDataSet o : vLisTipDoc) {
            vALiTipDoc.add(o.getTipDocDescri());
        }

        ArrayAdapter vAdaFic100TipDoc = new ArrayAdapter(this,R.layout.spinner_item,vALiTipDoc);
        vAdaFic100TipDoc.setDropDownViewResource(R.layout.model_spinner_item);
        vLspFic100a3.setAdapter(null);
        vLspFic100a3.setAdapter(vAdaFic100TipDoc);

        final EditText vLetFic100b3 = new EditText(MenPriActivity.this);
        vLetFic100b3.setText("");
        vLetFic100b3.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetFic100b3.setEnabled(true);
        vLetFic100b3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
        vLetFic100b3.setLayoutParams(lp);

        vLspFic100a3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View vies, int position, long id) {
                for (TipDocDataSet o : vLisTipDoc) {
                    if(o.getTipDocDescri().equals(vLspFic100a3.getSelectedItem().toString())){
                        vTipDoc = o.getTipDocIdenti();
                    }
                }
                if(vLspFic100a3.getSelectedItem().toString().equals(getResources().getString(R.string.formul_opcn00_pren01_01))){
                    vLetFic100b3.setText("");
                    vLetFic100b3.setInputType(InputType.TYPE_CLASS_NUMBER);
                    vLetFic100b3.setEnabled(true);
                    vLetFic100b3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
                }
                if(vLspFic100a3.getSelectedItem().toString().equals(getResources().getString(R.string.formul_opcn00_pren01_02))){
                    vLetFic100b3.setText("");
                    vLetFic100b3.setInputType(InputType.TYPE_CLASS_NUMBER);
                    vLetFic100b3.setEnabled(true);
                    vLetFic100b3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
                }
                if(vLspFic100a3.getSelectedItem().toString().equals(getResources().getString(R.string.formul_opcn00_pren01_03))){
                    vLetFic100b3.setText("");
                    vLetFic100b3.setInputType(InputType.TYPE_CLASS_TEXT);
                    vLetFic100b3.setEnabled(true);
                    vLetFic100b3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                }
                if(vLspFic100a3.getSelectedItem().toString().equals(getResources().getString(R.string.formul_opcn00_pren01_04))){
                    vLetFic100b3.setText("");
                    vLetFic100b3.setInputType(InputType.TYPE_CLASS_TEXT);
                    vLetFic100b3.setEnabled(true);
                    vLetFic100b3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                }
                if(vLspFic100a3.getSelectedItem().toString().equals(getResources().getString(R.string.formul_opcn00_pren01_05))){
                    vLetFic100b3.setText("");
                    vLetFic100b3.setInputType(InputType.TYPE_CLASS_TEXT);
                    vLetFic100b3.setEnabled(true);
                    vLetFic100b3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                }
                if(vLspFic100a3.getSelectedItem().toString().equals(getResources().getString(R.string.formul_opcn00_pren01_06))){
                    vLetFic100b3.setText("");
                    vLetFic100b3.setInputType(InputType.TYPE_NULL);
                    vLetFic100b3.setEnabled(false);
                    vLetFic100b3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(0) });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        vLspFic100a3.setLayoutParams(lps);
        layout.addView(vLspFic100a3);

//        final TextView vLtvFic100b1 = new TextView(MenPriActivity.this);
//        layout.addView(vLtvFic100b1);

        final TextView vLtvFic100b2 = new TextView(MenPriActivity.this);
        vLtvFic100b2.setTextSize(16);
        vLtvFic100b2.setTextColor(getResources().getColor(R.color.colorBlack));
        vLtvFic100b2.setLayoutParams(lp);
        vLtvFic100b2.setText(getResources().getString(R.string.formul_fic100_2));
        layout.addView(vLtvFic100b2);

        layout.addView(vLetFic100b3);

        AlertDialog.Builder builder = new AlertDialog.Builder(MenPriActivity.this);
        builder.setView(layout);
        builder.setTitle(getResources().getString(R.string.formul_opcn01))
                .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        vPren44aa = vLetPren44aa.getText().toString();
//                        vPren44ab = vLetPren44ab.getText().toString();
//                        vLtvPreN44aa.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44a) + "\n" +
//                                " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44aa) + ": " + vLetPren44aa.getText().toString() + "\n" +
//                                " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44ab) + ": " + vLetPren44ab.getText().toString());
                        vFor100 = vLetFic100b3.getText().toString();
                        MWebSerTokPac();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
        builder.create().show();

    }

    private void MWebSerTokPac(){

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
                            MWebSerConPac();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenPriActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
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

    private void MWebSerConPac(){

        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET,
                vDstDirWeb.getDirWebApiUrl() + Constants.URL_PACIENTE_CONFIRMADO + vTipDoc + "/" + vFor100,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Jdatos_response = new JSONObject(response);
                            if(Jdatos_response.getString("cod_respuesta").equals("0000")){
                                JSONObject Jdatos_paciente = Jdatos_response.getJSONObject("paciente");
                                JSONObject Jdatos_fichas = Jdatos_response.getJSONObject("fichas");
                                vIdenti = Jdatos_fichas.getString("ficha_0");
                                vTipDoc = Jdatos_paciente.getString("tip_documento");
                                vNroDoc = Jdatos_paciente.getString("num_documento");
                                vApePat = Jdatos_paciente.getString("ape_paterno");
                                vApeMat = Jdatos_paciente.getString("ape_materno");
                                vNombre = Jdatos_paciente.getString("nombre");
                                vFecNac = Jdatos_paciente.getString("fec_nacimiento");
                                vTipSex = Jdatos_paciente.getString("sexo");
                                Intent intent = new Intent(MenPriActivity.this, RegistActivity.class);
                                intent.putExtra("Identi", vIdenti);
                                intent.putExtra("TipDoc", vTipDoc);
                                intent.putExtra("NroDoc", vNroDoc);
                                intent.putExtra("ApePat", vApePat);
                                intent.putExtra("ApeMat", vApeMat);
                                intent.putExtra("Nombre", vNombre);
                                intent.putExtra("FecNac", vFecNac);
                                intent.putExtra("TipSex", vTipSex);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MenPriActivity.this, "No existe el registro de la persona", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenPriActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
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
