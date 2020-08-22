package minsa.formulario.UI.Fragments.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import minsa.formulario.AppController;
import minsa.formulario.DataSet.ContactF200DataSet;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DataSet.ParentescoDataSet;
import minsa.formulario.DataSet.Riesgo2DataSet;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.Riesgo2DbHelper;
import minsa.formulario.R;
import minsa.formulario.UI.Activity.ScannerActivity;
import minsa.formulario.Util.Age;
import minsa.formulario.Util.AgeCalculator;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.Utils;

public class AddContactsF200DialogFragment extends DialogFragment implements
        TipDocDialogFragment.TipDocDialogListener,
        ParentescoDialogFragment.ParentescoDialogListener,
        CalendarDialogFragment.CalendarDialogListener,
        View.OnFocusChangeListener {

    @BindView(R.id.tvTipDoc) TextView tvTipDoc;
    @BindView(R.id.tilNumberDocument) TextInputLayout tilNumberDocument;
    @BindView(R.id.tietNumberDocument) TextInputEditText tietNumberDocument;
    @BindView(R.id.tilDate) TextInputLayout tilDate;
    @BindView(R.id.tietDate) TextInputEditText tietDate;
    @BindView(R.id.tilName) TextInputLayout tilName;
    @BindView(R.id.tietName) TextInputEditText tietName;
    @BindView(R.id.tilPaternal) TextInputLayout tilPaternal;
    @BindView(R.id.tietPaternal) TextInputEditText tietPaternal;
    @BindView(R.id.tilMaternal) TextInputLayout tilMaternal;
    @BindView(R.id.tietMaternal) TextInputEditText tietMaternal;
    @BindView(R.id.tvRelationship) TextView tvRelationship;
    @BindView(R.id.tietEdad) TextInputEditText tietEdad;
    @BindView(R.id.rgSexo) RadioGroup rgSexo;
    @BindView(R.id.tilMobile) TextInputLayout tilMobile;
    @BindView(R.id.tietMobile) TextInputEditText tietMobile;
    @BindView(R.id.tilAddress) TextInputLayout tilAddress;
    @BindView(R.id.tietAddress) TextInputEditText tietAddress;
    @BindView(R.id.rgF100) RadioGroup rgF100;
    @BindView(R.id.rbFemenino) RadioButton rbFemenino;
    @BindView(R.id.rbMasculino) RadioButton rbMasculino;
    @BindView(R.id.llContentConditionsComorbidity) LinearLayout llContentConditionsComorbidity;
    @BindView(R.id.ivQR) ImageView ivQR;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorRiesgo2;

    public AddContactsF200DialogListener delegate;

    private ArrayList<Riesgo2DataSet> conditionsComorbidityList = new ArrayList<>();

    private String id_tip_doc = "";
    //private String fecha_nac = "";
    private int id_parentesco = 0;

    private int edad = 0;

    private DirWebDataSet vDstDirWeb;

    private String vTokens;

    public interface AddContactsF200DialogListener {
        void addContact(ContactF200DataSet dataSet);
    }

    public static AddContactsF200DialogFragment newInstance() {
        AddContactsF200DialogFragment f = new AddContactsF200DialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_add_contacts_f200_dialog, null);

        ButterKnife.bind(this, view);

        ivQR.setVisibility(View.GONE);

        mPrinciDbHelper = new PrinciDbHelper(getContext());

        tietNumberDocument.setOnFocusChangeListener(this);

        ///////////////////////////////
        /// RIESGOS
        ///////////////////////////////

        vCursorRiesgo2 = mPrinciDbHelper.getAllPrinci(Riesgo2DbHelper.RiesgoTableC.RiesgoTableN);
        while(vCursorRiesgo2.moveToNext()) {
            Riesgo2DataSet vDstDepart = new Riesgo2DataSet();
            vDstDepart.setRiesgoIdenti(vCursorRiesgo2.getInt(vCursorRiesgo2.getColumnIndex(Riesgo2DbHelper.RiesgoTableC.RiesgoIdenti)));
            vDstDepart.setRiesgoDescri(vCursorRiesgo2.getString(vCursorRiesgo2.getColumnIndex(Riesgo2DbHelper.RiesgoTableC.RiesgoDescri)));
            conditionsComorbidityList.add(vDstDepart);
        }

        for(int i=0; i < conditionsComorbidityList.size(); i++) {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
            checkBox.setId(conditionsComorbidityList.get(i).getRiesgoIdenti());
            checkBox.setText(conditionsComorbidityList.get(i).getRiesgoDescri());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int id_signo_alarma2 = (int) buttonView.getTag();

                    switch (id_signo_alarma2) {
                        case 1:
                            if(isChecked) {
                                uncheckedConditionsComorbidity();

                                CheckBox box = llContentConditionsComorbidity.findViewWithTag(11);
                                if(box.isChecked()) {
                                    box.setChecked(false);
                                }
                            }
                            break;
                        case 11:

                            if(isChecked) {
                                if(rbMasculino.isChecked()) {
                                    CheckBox box = llContentConditionsComorbidity.findViewWithTag(11);
                                    if(box.isChecked()) {
                                        box.setChecked(false);
                                    }
                                    return;
                                }
                                uncheckedConditionsComorbidity();

                                CheckBox box = llContentConditionsComorbidity.findViewWithTag(1);
                                if(box.isChecked()) {
                                    box.setChecked(false);
                                }
                            }

                            break;
                        case 13:

                            if(isChecked) {
                                for(int i=0; i < llContentConditionsComorbidity.getChildCount(); i++) {
                                    if(((int) llContentConditionsComorbidity.getChildAt(i).getTag()) != 13) {
                                        CheckBox boxs = llContentConditionsComorbidity.findViewWithTag(llContentConditionsComorbidity.getChildAt(i).getTag());
                                        boxs.setChecked(false);
                                    }
                                }
                            }

                            break;
                        default:
                            if(isChecked) {
                                uncheckedConditionsComorbidity();
                            }
                    }
                }
            });
            llContentConditionsComorbidity.addView(checkBox, i);
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme)
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                )
                .setView(view);

        rgSexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = rgSexo.findViewById(checkedId);

                if(null != radioButton && radioButton.getId() == R.id.rbMasculino && radioButton.isChecked()) {
                    CheckBox box = llContentConditionsComorbidity.findViewWithTag(11);
                    if(box.isChecked()) {
                        box.setChecked(false);
                    }
                }
            }
        });

        return alertDialog.create();
    }

    private void uncheckedConditionsComorbidity() {
        CheckBox box13 = llContentConditionsComorbidity.findViewWithTag(13);
        if(box13.isChecked()) {
            box13.setChecked(false);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.tietNumberDocument:
                if (!hasFocus) {
                    if(tietNumberDocument.getText().toString().isEmpty()) {
                        return;
                    }

                    MWebSerTokPac();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog)getDialog();

        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick) {

                if(tietName.getText().toString().isEmpty()) {
                    Toasty.info(getContext(), "Ingrese nombres.", Toasty.LENGTH_SHORT).show();
                    return;
                }

                if(tietPaternal.getText().toString().isEmpty()) {
                    Toasty.info(getContext(), "Ingrese el apellido paterno.", Toasty.LENGTH_SHORT).show();
                    return;
                }

                if(id_parentesco == 0) {
                    Toasty.info(getContext(), "Seleccione el parentesco.", Toasty.LENGTH_SHORT).show();
                    return;
                }

                if(rgSexo.getCheckedRadioButtonId() == -1) {
                    Toasty.info(getContext(), "Seleccione el sexo.", Toasty.LENGTH_SHORT).show();
                    return;
                }

                if(!tietMobile.getText().toString().trim().isEmpty()) {
                    if(!tietMobile.getText().toString().substring(0, 1).equals("9")) {
                        Toasty.info(getContext(), "Los números de celulares deben iniciar con 9...", Toasty.LENGTH_SHORT).show();
                        tietMobile.requestFocus();
                        return;
                    } else if(tietMobile.getText().toString().length() < 9) {
                        Toasty.info(getContext(), "Completar número de celular.", Toasty.LENGTH_SHORT).show();
                        tietMobile.requestFocus();
                        return;
                    }
                }

                ContactF200DataSet contactF200DataSet = new ContactF200DataSet();
                contactF200DataSet.setId_document_type(id_tip_doc);
                contactF200DataSet.setDocument_type(id_tip_doc.isEmpty() ? "" : tvTipDoc.getText().toString());
                contactF200DataSet.setNumber_document(tietNumberDocument.getText().toString());
                contactF200DataSet.setDate_birth(tietDate.getText().toString().isEmpty() ? "" : Utils.changeDateFormat(tietDate.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd"));
                contactF200DataSet.setName(tietName.getText().toString().toUpperCase());
                contactF200DataSet.setPaternal(tietPaternal.getText().toString().toUpperCase());
                contactF200DataSet.setMaternal(tietMaternal.getText().toString().toUpperCase());
                contactF200DataSet.setId_relationship(id_parentesco);
                contactF200DataSet.setRelationship(tvRelationship.getText().toString());
                contactF200DataSet.setEdad(edad);
                RadioButton rbSex = rgSexo.findViewById(rgSexo.getCheckedRadioButtonId());

                contactF200DataSet.setId_sex(rbSex.getId() == R.id.rbFemenino ? "F" : "M");
                contactF200DataSet.setSex(rbSex.getText().toString());
                contactF200DataSet.setMobile(tietMobile.getText().toString());
                contactF200DataSet.setAddress(tietAddress.getText().toString());

                if(rgF100.getCheckedRadioButtonId() != -1) {
                    RadioButton rbF100 = rgF100.findViewById(rgF100.getCheckedRadioButtonId());
                    contactF200DataSet.setF100(rbF100.getId() == R.id.rbYesF100 ? 1 : 0);
                } else {
                    contactF200DataSet.setF100(0);
                }

                ArrayList<ContactF200DataSet.RiskFactors> riskFactors = new ArrayList<>();
                for(int i=0; i < conditionsComorbidityList.size(); i++) {
                    CheckBox checkBox = llContentConditionsComorbidity.findViewWithTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
                    if(checkBox.isChecked()) {
                        ContactF200DataSet.RiskFactors factors = new ContactF200DataSet.RiskFactors();
                        factors.setId_risk_factor(conditionsComorbidityList.get(i).getRiesgoIdenti());
                        factors.setRisk_factor(conditionsComorbidityList.get(i).getRiesgoDescri());
                        riskFactors.add(factors);
                    }
                }
                contactF200DataSet.setRiskFactors(riskFactors);

                delegate.addContact(contactF200DataSet);
                dismiss();
            }
        });

        vDstDirWeb = DirWebDataSet.getInstance();

        tietNumberDocument.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(tietNumberDocument.getText().toString().isEmpty()) {
                        return false;
                    }

                    MWebSerTokPac();
                    return true;
                }
                return false;
            }
        });

        tietMobile.setInputType(InputType.TYPE_CLASS_NUMBER);
        tietMobile.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 444:
                if(resultCode == 2) {
                    String code = data.getStringExtra("code");
                    tietNumberDocument.setText(code);
                    tietNumberDocument.setEnabled(false);

                    tietName.setText("");
                    tietName.setEnabled(true);

                    tietPaternal.setText("");
                    tietPaternal.setEnabled(true);

                    tietMaternal.setText("");
                    tietMaternal.setEnabled(true);

                    tietDate.setText("");
                    tietDate.setEnabled(true);

                    tietEdad.setText("");

                    rgSexo.clearCheck();
                    rbFemenino.setEnabled(true);
                    rbMasculino.setEnabled(true);

                    MWebSerTokPac();
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.ivQR)
    public void onClicQR(View view) {
        Intent intent = new Intent(getContext(), ScannerActivity.class);
        startActivityForResult(intent, 444);
    }

    @OnClick({R.id.tietDate})
    public void onCickDateBirth(View view) {
        CalendarDialogFragment calendarDialogFragment = new CalendarDialogFragment();
        calendarDialogFragment.delegate = AddContactsF200DialogFragment.this;
        calendarDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        calendarDialogFragment.show(getActivity().getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlContentTipDoc})
    public void onClicTipDoc(View view) {
        TipDocDialogFragment fragment = TipDocDialogFragment.newInstance(0);
        fragment.delegate = AddContactsF200DialogFragment.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getActivity().getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlContentRelationship})
    public void onClicRelationship(View view) {
        ParentescoDialogFragment fragment = ParentescoDialogFragment.newInstance();
        fragment.delegate = AddContactsF200DialogFragment.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void setTipDoc(TipDocDataSet dataSet) {
        tvTipDoc.setText(dataSet.getTipDocDescri());
        tvTipDoc.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        id_tip_doc = dataSet.getTipDocIdenti();

        if(dataSet.getTipDocIdenti().equals("1")) {
            ivQR.setVisibility(View.VISIBLE);
        } else {
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
                tietNumberDocument.setInputType(InputType.TYPE_NULL);
                tietNumberDocument.setEnabled(false);
                tietNumberDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(0) });
                break;
        }

        tietName.setText("");
        tietName.setEnabled(true);

        tietPaternal.setText("");
        tietPaternal.setEnabled(true);

        tietMaternal.setText("");
        tietMaternal.setEnabled(true);

        tietDate.setText("");
        tietDate.setEnabled(true);

        tietEdad.setText("");

        rgSexo.clearCheck();
        rbFemenino.setEnabled(true);
        rbMasculino.setEnabled(true);
    }

    @Override
    public void setParentesco(ParentescoDataSet dataSet) {
        tvRelationship.setText(dataSet.getParentesco());
        tvRelationship.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

        id_parentesco = dataSet.getId();
    }

    @Override
    public void setDate(String date) {
        tietDate.setText(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date birthDate = null;
        try {
            birthDate = sdf.parse(date);
            Age age = AgeCalculator.calculateAge(birthDate);
            tietEdad.setText(age.toString());

            edad = age.getYears();
        } catch (ParseException e) {
            e.printStackTrace();
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
                            Log.i("Tokens",Tokens);
                            MWebSerConPac();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.info(getContext(), "El servicio no se encuentra disponible, ingrese los datos manualmente", Toast.LENGTH_SHORT).show();
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
                        try {
                            Log.i("response",response);
                            JSONObject Jdatos_response = new JSONObject(response);
                            if(Jdatos_response.getString("cod_respuesta").equals("0000")) {
                                JSONObject Jdatos_paciente = Jdatos_response.getJSONObject("paciente");
                                JSONObject Jdatos_fichas = Jdatos_response.getJSONObject("fichas");

                                tietNumberDocument.setEnabled(false);
                                tietName.setText(Jdatos_paciente.getString("nombre"));
                                tietName.setEnabled(false);
                                tietPaternal.setText(Jdatos_paciente.getString("ape_paterno"));
                                tietPaternal.setEnabled(false);
                                tietMaternal.setText(Jdatos_paciente.getString("ape_materno"));
                                tietMaternal.setEnabled(false);

                                if(null != Jdatos_paciente.getString("fec_nacimiento") && Jdatos_paciente.getString("fec_nacimiento").length() == 8) {
                                    tietDate.setText(Jdatos_paciente.getString("fec_nacimiento").substring(6, 8) + "/" + Jdatos_paciente.getString("fec_nacimiento").substring(4, 6) + "/" + Jdatos_paciente.getString("fec_nacimiento").substring(0, 4));
                                    tietDate.setEnabled(false);
                                } else {
                                    tietDate.setText("");
                                }

                                if(Jdatos_paciente.getString("sexo").equals("1")){
                                    rbMasculino.setChecked(true);
                                    CheckBox box = llContentConditionsComorbidity.findViewWithTag(11);
                                    if(box.isChecked()) {
                                        box.setChecked(false);
                                    }
                                } else {
                                    rbFemenino.setChecked(true);
                                }
                                rbMasculino.setEnabled(false);
                                rbFemenino.setEnabled(false);

                                if(id_tip_doc.equals("2")) {
                                    rgSexo.setEnabled(true);
                                    rgSexo.clearCheck();
                                    rbMasculino.setEnabled(true);
                                    rbFemenino.setEnabled(true);
                                }

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Date birthDate = null;
                                try {
                                    birthDate = sdf.parse(tietDate.getText().toString());
                                    Age age = AgeCalculator.calculateAge(birthDate);
                                    tietEdad.setText(age.toString());
                                    edad = age.getYears();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                tietEdad.setEnabled(false);
                            } else if(Jdatos_response.getString("cod_respuesta").equals("7000")) {
                                tietNumberDocument.setText("");
                                Toasty.info(getContext(), "El DNI ingresado corresponde a una persona fallecida.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toasty.info(getContext(), "No existe el registro de la persona, ingrese los datos manualmente", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.info(getContext(), "El servicio no se encuentra disponible, ingrese los datos manualmente", Toast.LENGTH_SHORT).show();
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