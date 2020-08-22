package minsa.formulario.UI.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import minsa.formulario.UI.Activity.fpaciente.DatosPacienteActivity;
import minsa.formulario.UI.Activity.ScannerActivity;
import minsa.formulario.UI.Activity.f100.F100Activity;
import minsa.formulario.UI.Activity.f200.F200Activity;
import minsa.formulario.UI.Activity.f300.F300Activity;
import minsa.formulario.UI.Fragments.Dialog.TipDocDialogFragment;
import minsa.formulario.AppController;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DbHelper.DatoPacienteDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.PreferencesManager;
import minsa.formulario.Util.Utils;

public class RegistroFragment extends Fragment implements TipDocDialogFragment.TipDocDialogListener {

    @BindView(R.id.tvTipDoc) TextView tvTipDoc;
    @BindView(R.id.tilNumberDocument) TextInputLayout tilNumberDocument;
    @BindView(R.id.tietNumberDocument) TextInputEditText tietNumberDocument;
    @BindView(R.id.llContentFichas) LinearLayout llContentFichas;
    @BindView(R.id.ivQR) ImageView ivQR;
    @BindView(R.id.ivSearch) ImageView ivSearch;
    @BindView(R.id.ivClear) ImageView ivClear;
    @BindView(R.id.tvConglomerado) TextView tvConglomerado;

    private ColorStateList oldColors;

    private PrinciDbHelper mPrinciDbHelper;
    private DirWebDataSet vDstDirWeb;

    private String id_tip_doc = "";
    private String tipodocumento = "";
    private String documento = "";
    private String paciente = "";
    private int sexo = 0;
    private int id_dato_paciente = 0;

    private String vTokens;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_registro, container, false);

        ButterKnife.bind(this, root);

        oldColors =  tvTipDoc.getTextColors();

        //getActivity().setTitle("Bandeja de Salida");

        mPrinciDbHelper = new PrinciDbHelper(getContext());
        vDstDirWeb = DirWebDataSet.getInstance();

        if(!PreferencesManager.getIdConglomerado(getContext()).isEmpty()) {
            tvConglomerado.setVisibility(View.VISIBLE);
            tvConglomerado.setText("* Usted se encuentra en el conglomerado: " + PreferencesManager.getConglomerado(getContext()));
        }

        tietNumberDocument.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });

        return root;
    }

    @OnClick(R.id.ivSearch)
    public void onClicSearch(View view) {
        search();
    }

    private void search() {
        if(id_tip_doc.isEmpty()) {
            Toasty.info(getActivity(), "Seleccione tipo de documento.", Toasty.LENGTH_SHORT).show();
            return;
        }

        if(tietNumberDocument.getText().toString().isEmpty()) {
            Toasty.info(getActivity(), "Seleccione número de documento.", Toasty.LENGTH_SHORT).show();
            return;
        }

        Cursor vCursor = mPrinciDbHelper.getAllPrinciTwo(DatoPacienteDbHelper.TableC.TableN, DatoPacienteDbHelper.TableC.id_tipo_doc, id_tip_doc, DatoPacienteDbHelper.TableC.numero_doc, tietNumberDocument.getText().toString().trim());
        if(vCursor.moveToFirst()) {
            tipodocumento = vCursor.getString(vCursor.getColumnIndex(DatoPacienteDbHelper.TableC.tipo_doc));
            documento = vCursor.getString(vCursor.getColumnIndex(DatoPacienteDbHelper.TableC.numero_doc));
            paciente = vCursor.getString(vCursor.getColumnIndex(DatoPacienteDbHelper.TableC.nombres)) + " " + vCursor.getString(vCursor.getColumnIndex(DatoPacienteDbHelper.TableC.paterno)) + " " + vCursor.getString(vCursor.getColumnIndex(DatoPacienteDbHelper.TableC.materno));
            sexo = vCursor.getInt(vCursor.getColumnIndex(DatoPacienteDbHelper.TableC.id_sexo));
            id_dato_paciente = vCursor.getInt(vCursor.getColumnIndex(DatoPacienteDbHelper.TableC._id));

            llContentFichas.setVisibility(View.VISIBLE);
            tietNumberDocument.setEnabled(false);
        } else {
            progressDialog = Utils.showProgressDialog(getActivity(), "Validando...");
            progressDialog.show();

            MWebSerTokPac();
        }
    }

    @OnClick(R.id.ivClear)
    public void onClicClear(View view) {
        ivQR.setVisibility(View.GONE);
        tilNumberDocument.setHint("Número de documento");
        tvTipDoc.setText("Seleccionar");
        tvTipDoc.setTextColor(oldColors);
        id_tip_doc = "";
        tipodocumento = "";
        documento = "";
        paciente = "";
        id_dato_paciente = 0;
        tietNumberDocument.setText("");
        tietNumberDocument.setEnabled(true);
        llContentFichas.setVisibility(View.GONE);
    }

    @OnClick(R.id.rlContentTipDoc)
    public void onClicTipoDoc(View view) {
        if(llContentFichas.getVisibility() == View.VISIBLE) {
            return;
        }

        TipDocDialogFragment fragment = TipDocDialogFragment.newInstance(0);
        fragment.delegate = RegistroFragment.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getActivity().getSupportFragmentManager(), "");
    }

    @OnClick(R.id.llF100)
    public void onClicF100(View view) {
        goFichas(F100Activity.class);
    }

    @OnClick(R.id.llF200)
    public void onClicF200(View view) {
        goFichas(F200Activity.class);
    }

    @OnClick(R.id.llF300)
    public void onClicF300(View view) {
        goFichas(F300Activity.class);
    }

    private void goFichas(Class c) {
        Intent intent = new Intent(getContext(), c);
        intent.putExtra("id_tipo_documento", id_tip_doc);
        intent.putExtra("documento", tietNumberDocument.getText().toString());
        intent.putExtra("paciente", paciente);
        intent.putExtra("tipodocumento", tipodocumento);
        intent.putExtra("sexo", sexo);
        intent.putExtra("id_dato_paciente", id_dato_paciente);
        startActivity(intent);
    }

    @OnClick(R.id.ivQR)
    public void onClicQR(View view) {
        Intent intent = new Intent(getContext(), ScannerActivity.class);
        startActivityForResult(intent, 442);
    }

    @Override
    public void setTipDoc(TipDocDataSet dataSet) {
        tvTipDoc.setText(dataSet.getTipDocDescri());
        tvTipDoc.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));

        id_tip_doc = dataSet.getTipDocIdenti();
        tipodocumento = dataSet.getTipDocDescri();

        if(id_tip_doc.equals("1")) {
            tilNumberDocument.setHint("N° de documento");
            ivQR.setVisibility(View.VISIBLE);
        } else {
            tilNumberDocument.setHint("Número de documento");
            ivQR.setVisibility(View.GONE);
        }

        switch (dataSet.getTipDocIdenti()) {
            case "1":
                tietNumberDocument.setText("");
                tietNumberDocument.setInputType(InputType.TYPE_CLASS_NUMBER);
                tietNumberDocument.setEnabled(true);
                tietNumberDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
                break;
            case "2":
                tietNumberDocument.setText("");
                tietNumberDocument.setInputType(InputType.TYPE_CLASS_NUMBER);
                tietNumberDocument.setEnabled(true);
                tietNumberDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
                break;
            case "3":
            case "4":
            case "5":
                tietNumberDocument.setText("");
                tietNumberDocument.setInputType(InputType.TYPE_CLASS_TEXT);
                tietNumberDocument.setEnabled(true);
                tietNumberDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                break;
            case "6":
                tietNumberDocument.setText("");
                tietNumberDocument.setInputType(InputType.TYPE_CLASS_TEXT);
                tietNumberDocument.setEnabled(false);
                tietNumberDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                break;
        }

        if(dataSet.getTipDocIdenti().equals("6")) {
            goRegistrar();
            return;
        }

        tietNumberDocument.setText("");
        tietNumberDocument.requestFocus();

        llContentFichas.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 442:
                if(resultCode == 2) {
                    String code = data.getStringExtra("code");
                    tietNumberDocument.setText(code);

                    progressDialog = Utils.showProgressDialog(getActivity(), "Validando...");
                    progressDialog.show();

                    MWebSerTokPac();
                }
                break;
            case 123:
                if(resultCode == 1) {

                    if(null != data) {
                        paciente = data.getStringExtra("paciente");
                        sexo = data.getIntExtra("sexo", 0);
                        id_dato_paciente = data.getIntExtra("id_dato_paciente", 0);
                    }

                    Utils.showDialog(getActivity(), "Registro de paciente exitoso", "Debe Registrar una de las siguientes fichas:\n\n" +
                            " - F100 Prueba Rápida\n" +
                            " - F200 Investigación Epidemiológica\n" +
                            " - F300 Seguimiento Clínico\n");

                    llContentFichas.setVisibility(View.VISIBLE);
                }
                break;
        }
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
                        Utils.hideProgressDialog(progressDialog);

                        tietNumberDocument.setEnabled(false);
                        llContentFichas.setVisibility(View.GONE);
                        Intent intent = new Intent(getContext(), DatosPacienteActivity.class);
                        intent.putExtra("estado", "0");
                        intent.putExtra("id_tipo_documento", id_tip_doc);
                        intent.putExtra("tipodocumento", tipodocumento);
                        intent.putExtra("documento", tietNumberDocument.getText().toString());
                        startActivityForResult(intent, 123);
                        Toast.makeText(getContext(), "El servicio no se encuentra disponible, se pasara al ingreso manual", Toast.LENGTH_SHORT).show();
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
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(2500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    private void MWebSerConPac(){
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET,
                vDstDirWeb.getDirWebApiUrl() + Constants.URL_PACIENTE_CONFIRMADO + id_tip_doc + "/" + tietNumberDocument.getText().toString().trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog(progressDialog);

                        try {
                            JSONObject Jdatos_response = new JSONObject(response);
                            if(Jdatos_response.getString("cod_respuesta").equals("0000")){
                                JSONObject Jdatos_paciente = Jdatos_response.getJSONObject("paciente");
                                JSONObject Jdatos_fichas = Jdatos_response.getJSONObject("fichas");
                                tietNumberDocument.setEnabled(false);
                                llContentFichas.setVisibility(View.GONE);
                                Intent intent = new Intent(getContext(), DatosPacienteActivity.class);
                                intent.putExtra("estado", "1");
                                intent.putExtra("id_tipo_documento", id_tip_doc);
                                intent.putExtra("tipodocumento", tipodocumento);
                                intent.putExtra("documento", tietNumberDocument.getText().toString());
                                intent.putExtra("Identi", Jdatos_fichas.getString("ficha_0"));
                                intent.putExtra("TipDoc", Jdatos_paciente.getString("tip_documento"));
                                intent.putExtra("NroDoc", Jdatos_paciente.getString("num_documento"));
                                intent.putExtra("ApePat", Jdatos_paciente.getString("ape_paterno"));
                                intent.putExtra("ApeMat", Jdatos_paciente.getString("ape_materno"));
                                intent.putExtra("Nombre", Jdatos_paciente.getString("nombre"));
                                intent.putExtra("FecNac", Jdatos_paciente.getString("fec_nacimiento"));
                                intent.putExtra("TipSex", Jdatos_paciente.getString("sexo"));
                                startActivityForResult(intent, 123);
                            }else{
                                tietNumberDocument.setEnabled(false);
                                llContentFichas.setVisibility(View.GONE);

                                goRegistrar();

                                Toast.makeText(getContext(), "No existe el registro de la persona, se pasara al ingreso manual", Toast.LENGTH_SHORT).show();
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

                        tietNumberDocument.setEnabled(false);
                        llContentFichas.setVisibility(View.GONE);

                        goRegistrar();

                        Toast.makeText(getContext(), "El servicio no se encuentra disponible, se pasara al ingreso manual", Toast.LENGTH_SHORT).show();
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

    private void goRegistrar() {
        Intent intent = new Intent(getContext(), DatosPacienteActivity.class);
        intent.putExtra("estado", "0");
        intent.putExtra("id_tipo_documento", id_tip_doc);
        intent.putExtra("tipodocumento", tipodocumento);
        intent.putExtra("documento", tietNumberDocument.getText().toString());
        startActivityForResult(intent, 123);
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