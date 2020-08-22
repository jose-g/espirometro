package minsa.formulario.UI.Activity.f200;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import minsa.formulario.UI.Activity.ScannerActivity;
import minsa.formulario.UI.Fragments.Dialog.AddContactsF200DialogFragment;
import minsa.formulario.UI.Fragments.Dialog.AddLaboratoryDataF200DialogFragment;
import minsa.formulario.UI.Fragments.Dialog.ContactoCasoConfirmadoDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.EmbarazoTriDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.PaisDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.TipDocDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.TypeSureDialogFragment;
import minsa.formulario.Adapters.recyclerview.ContactF200RecyclerViewAdapter;
import minsa.formulario.Adapters.recyclerview.LaboratoryDataAdapter;
import minsa.formulario.Adapters.recyclerview.TestInformationF200Adapter;
import minsa.formulario.Adapters.recyclerview.ViajeRecyclerViewAdapter;
import minsa.formulario.AppController;
import minsa.formulario.DataSet.ContactF200DataSet;
import minsa.formulario.DataSet.ContactoCasoConfirmadoDataSet;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DataSet.EmbarazoTriDataSet;
import minsa.formulario.DataSet.LaboratoryDataDataSet;
import minsa.formulario.DataSet.PaisDataSet;
import minsa.formulario.DataSet.Riesgo2DataSet;
import minsa.formulario.DataSet.SigAlarm2DataSet;
import minsa.formulario.DataSet.TestInformationF200DataSet;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DataSet.TipSegDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DataSet.ViajeF200DataSet;
import minsa.formulario.DbHelper.F100DbHelper;
import minsa.formulario.DbHelper.F200ContactoDbHelper;
import minsa.formulario.DbHelper.F200ContactoRiesgoDbHelper;
import minsa.formulario.DbHelper.F200DbHelper;
import minsa.formulario.DbHelper.F200LaboratorioDbHelper;
import minsa.formulario.DbHelper.F200RiesgoDbHelper;
import minsa.formulario.DbHelper.F200SintomaDbHelper;
import minsa.formulario.DbHelper.F200ViajeDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.Riesgo2DbHelper;
import minsa.formulario.DbHelper.SigAlarm2DbHelper;
import minsa.formulario.DbHelper.TipDocDbHelper;
import minsa.formulario.R;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.PreferencesManager;
import minsa.formulario.Util.Utils;

public class F200Activity extends AppCompatActivity implements
        TypeSureDialogFragment.TypeSafeDialogListener,
        TipDocDialogFragment.TipDocDialogListener,
        AddLaboratoryDataF200DialogFragment.AddLaboratoryDataDialogListener,
        AddContactsF200DialogFragment.AddContactsF200DialogListener,
        ContactoCasoConfirmadoDialogFragment.ContactoCasoConfirmadoDialogListener,
        PaisDialogFragment.PaisDialogListener,
        ViajeRecyclerViewAdapter.ViajeListener,
        EmbarazoTriDialogFragment.EmbarazoTriDialogListener,
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.nestedScrollView) NestedScrollView nestedScrollView;
    @BindView(R.id.tvPaciente) TextView tvPaciente;
    @BindView(R.id.tvTipoDocumento) TextView tvTipoDocumento;
    @BindView(R.id.tvDocumento) TextView tvDocumento;

    @BindView(R.id.tvTypeSure) TextView tvTypeSure;

    @BindView(R.id.tilOtroSeguro) TextInputLayout tilOtroSeguro;
    @BindView(R.id.tietOtroSeguro) TextInputEditText tietOtroSeguro;
    @BindView(R.id.tietProfessional) TextInputEditText tietProfessional;

    @BindView(R.id.tilOtroSigno) TextInputLayout tilOtroSigno;
    @BindView(R.id.tietOtroSigno) TextInputEditText tietOtroSigno;
    @BindView(R.id.tilOtroRiesgo) TextInputLayout tilOtroRiesgo;
    @BindView(R.id.tietOtroRiesgo) TextInputEditText tietOtroRiesgo;

    @BindView(R.id.llContentTrimestre) LinearLayout llContentTrimestre;

    @BindView(R.id.rgHospitalized) RadioGroup rgHospitalized;
    @BindView(R.id.rbYesHospitalized) RadioButton rbYesHospitalized;
    @BindView(R.id.rbNoHospitalized) RadioButton rbNoHospitalized;
    @BindView(R.id.rgHaViajado) RadioGroup rgHaViajado;
    @BindView(R.id.rbYesHaViajado) RadioButton rbYesHaViajado;
    @BindView(R.id.rbNoHaViajado) RadioButton rbNoHaViajado;
    @BindView(R.id.tvEmbarazoTri) TextView tvEmbarazoTri;

    @BindView(R.id.tilDateHospitalized) TextInputLayout tilDateHospitalized;
    @BindView(R.id.tietDateHospitalized) TextInputEditText tietDateHospitalized;
    @BindView(R.id.tietTemperature) TextInputEditText tietTemperature;

    @BindView(R.id.tvTipDocInve) TextView tvTipDocInve;
    @BindView(R.id.rvTestInfomationF200) RecyclerView rvTestInfomationF200;
    @BindView(R.id.rvLaboratoryData) RecyclerView rvLaboratoryData;
    @BindView(R.id.rvContacto) RecyclerView rvContacto;
    @BindView(R.id.rvViajes) RecyclerView rvViajes;
    @BindView(R.id.tvTitleViaje) TextView tvTitleViaje;
    @BindView(R.id.tvContactoCasoConfirmado) TextView tvContactoCasoConfirmado;
    @BindView(R.id.llAddViajes) LinearLayout llAddViajes;

    @BindView(R.id.tietDate) TextInputEditText tietDate;
    @BindView(R.id.llContentSign) LinearLayout llContentSign;
    @BindView(R.id.llContentConditionsComorbidity) LinearLayout llContentConditionsComorbidity;

    @BindView(R.id.tietInvestigatorDocument) TextInputEditText tietInvestigatorDocument;
    @BindView(R.id.ivQR) ImageView ivQR;
    @BindView(R.id.tietInvestigatorName) TextInputEditText tietInvestigatorName;
    @BindView(R.id.tietInvestigatorPaternal) TextInputEditText tietInvestigatorPaternal;
    @BindView(R.id.tietInvestigatorMaternal) TextInputEditText tietInvestigatorMaternal;
    @BindView(R.id.tietObservation) TextInputEditText tietObservation;
    @BindView(R.id.efab) ExtendedFloatingActionButton efab;

    private ColorStateList oldColors;

    private List<TestInformationF200DataSet> testInformationF200DataSets = new ArrayList<>();
    private TestInformationF200Adapter testInformationF200Adapter;

    private ArrayList<LaboratoryDataDataSet> laboratoryDataDataSets = new ArrayList<>();
    private LaboratoryDataAdapter laboratoryDataAdapter;

    private List<ContactF200DataSet> contactF200DataSets = new ArrayList<>();
    private ContactF200RecyclerViewAdapter contactF200RecyclerViewAdapter;

    private List<ViajeF200DataSet> viajeF200DataSets = new ArrayList<>();
    private ViajeRecyclerViewAdapter viajeRecyclerViewAdapter;

    ArrayList<SigAlarm2DataSet> signList = new ArrayList<>();
    ArrayList<Riesgo2DataSet> conditionsComorbidityList = new ArrayList<>();

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorF100;
    private Cursor vCursorSigAlarm2;
    private Cursor vCursorRiesgo2;

    private int id_tip_seg = 0;
    private String date = "";
    private String dateHospitalized = "";
    private int id_cont_caso_conf = 0;
    private String contacto_caso_confirmado = "";

    private String paciente = "";
    private String id_tipo_documento = "";
    private String tipodocumento = "";
    private String documento = "";
    private int sexo = 0;
    private int id_dato_paciente = 0;

    private String id_tip_doc = "";

    private String temperature_before = "";

    private int _id;

    private int id_trimestre = 0;

    private String id_tip_doc_inve = "";
    private DirWebDataSet vDstDirWeb;

    private String vTokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f200);

        ButterKnife.bind(this);

        _id = getIntent().getIntExtra("id", 0);
        paciente = getIntent().getStringExtra("paciente");
        id_tipo_documento = getIntent().getStringExtra("id_tipo_documento");
        tipodocumento = getIntent().getStringExtra("tipodocumento");
        documento = getIntent().getStringExtra("documento");
        sexo = getIntent().getIntExtra("sexo", 0);
        id_dato_paciente = getIntent().getIntExtra("id_dato_paciente", 0);

        tvPaciente.setText("Paciente: " + paciente);
        tvTipoDocumento.setText("Tipo de documento: " + tipodocumento);
        tvDocumento.setText("Número de documento: " + documento);

        toolbar.setTitle("F200 - Investigación Epidemiológica");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oldColors =  tvTypeSure.getTextColors();

        vDstDirWeb = DirWebDataSet.getInstance();

        mPrinciDbHelper = new PrinciDbHelper(getApplicationContext());

        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        inputTypeByTypeDocument(String.valueOf(usuariDataSet.getUsuarioIdTipoDoc()));

        id_tip_doc_inve = String.valueOf(usuariDataSet.getUsuarioIdTipoDoc());
        tietInvestigatorDocument.setText(usuariDataSet.getUsuariNroDoc());
        tietInvestigatorName.setText(usuariDataSet.getUsuarioNom());
        tietInvestigatorPaternal.setText(usuariDataSet.getUsuarioPaterno());
        tietInvestigatorMaternal.setText(usuariDataSet.getUsuarioMaterno());

        if(id_tip_doc_inve.equals("1")) {
            ivQR.setVisibility(View.VISIBLE);
        } else {
            ivQR.setVisibility(View.GONE);
        }

        Cursor cursorTipDoc = mPrinciDbHelper.getAllPrinci(TipDocDbHelper.TipDocTableC.TipDocTableN);
        if(cursorTipDoc.getCount() != 0) {
            while(cursorTipDoc.moveToNext()) {
                if(cursorTipDoc.getString(cursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.slug)).equals(id_tip_doc_inve)) {
                    tvTipDocInve.setText(cursorTipDoc.getString(cursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocDescri)));
                    tvTipDocInve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                }
            }
        }

        tietDate.setText(Utils.getDate("dd/MM/yyyy"));
        date = Utils.changeDateFormat(tietDate.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd");

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dpDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String newFormat = sdf.format(myCalendar.getTime());
                date = Utils.changeDateFormat(newFormat, "dd/MM/yyyy", "yyyy-MM-dd");
                tietDate.setText(newFormat);
            }
        };

        tietDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendarMin = Calendar.getInstance();
                calendarMin.add(Calendar.DAY_OF_MONTH, -15);

                final Calendar calendarToday = Calendar.getInstance();
                int year = calendarToday.get(Calendar.YEAR);
                int month = calendarToday.get(Calendar.MONTH);
                int day = calendarToday.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(F200Activity.this, dpDate, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        final DatePickerDialog.OnDateSetListener dpDateHospitalized = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String newFormat = sdf.format(myCalendar.getTime());
                dateHospitalized = Utils.changeDateFormat(newFormat, "dd/MM/yyyy", "yyyy-MM-dd");
                tietDateHospitalized.setText(newFormat);
            }
        };

        tietDateHospitalized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendarMin = Calendar.getInstance();
                calendarMin.add(Calendar.DAY_OF_MONTH, -15);

                final Calendar calendarToday = Calendar.getInstance();
                int year = calendarToday.get(Calendar.YEAR);
                int month = calendarToday.get(Calendar.MONTH);
                int day = calendarToday.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(F200Activity.this, dpDateHospitalized, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        tietTemperature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temperature_before = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty() && s.toString().substring(0, 1).equals(".")) {
                    tietTemperature.setText("");
                    return;
                }

                double d = s.toString().isEmpty() ? 0 : Double.parseDouble(s.toString());
                if(d > 49) {
                    tietTemperature.setText(temperature_before);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rgHospitalized.setOnCheckedChangeListener(this);
        rgHaViajado.setOnCheckedChangeListener(this);

        /////////////////////////////////

        vCursorF100 = mPrinciDbHelper.getAllPrinciTwo(F100DbHelper.TableC.TableN, F100DbHelper.TableC.num_doc, documento, F100DbHelper.TableC.id_dato_paciente, String.valueOf(id_dato_paciente));
        if(vCursorF100.getCount() != 0) {
            while(vCursorF100.moveToNext()) {
                testInformationF200DataSets.add(new TestInformationF200DataSet("PRUEBA RAPIDA", Utils.changeDateFormat(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.fecha)), "yyyy-MM-dd", "dd/MM/yyyy") + " " + vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.hora)), vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.resultado_prueba_rapida))));
                if(vCursorF100.getInt(vCursorF100.getColumnIndex(F100DbHelper.TableC.id_resultado_prueba_rapida)) == 3) {
                    testInformationF200DataSets.add(new TestInformationF200DataSet("PRUEBA RAPIDA", Utils.changeDateFormat(vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.fecha)), "yyyy-MM-dd", "dd/MM/yyyy") + " " + vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.hora)), vCursorF100.getString(vCursorF100.getColumnIndex(F100DbHelper.TableC.resultado_prueba_rapida_2))));
                }
            }
        }

        rvTestInfomationF200.setNestedScrollingEnabled(false);
        rvTestInfomationF200.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        testInformationF200Adapter = new TestInformationF200Adapter(getApplicationContext(), testInformationF200DataSets);
        rvTestInfomationF200.setAdapter(testInformationF200Adapter);

        testInformationF200Adapter.setOnItemClickListener(new TestInformationF200Adapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });

        ////////////////////////////////

        rvLaboratoryData.setNestedScrollingEnabled(false);
        rvLaboratoryData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        laboratoryDataAdapter = new LaboratoryDataAdapter(getApplicationContext(), laboratoryDataDataSets);
        rvLaboratoryData.setAdapter(laboratoryDataAdapter);

        laboratoryDataAdapter.setOnItemClickListener(new LaboratoryDataAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(v.getId() == R.id.ivTrash) {
                    laboratoryDataDataSets.remove(position);
                    laboratoryDataAdapter.notifyDataSetChanged();
                }
            }
        });

        /////////////////////////////////

        rvContacto.setNestedScrollingEnabled(false);
        rvContacto.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        contactF200RecyclerViewAdapter = new ContactF200RecyclerViewAdapter(getApplicationContext(), contactF200DataSets);
        rvContacto.setAdapter(contactF200RecyclerViewAdapter);

        contactF200RecyclerViewAdapter.setOnItemClickListener(new ContactF200RecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(v.getId() == R.id.ivTrash) {
                    contactF200DataSets.remove(position);
                    contactF200RecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        /////////////////////////////////

        viajeF200DataSets.add(new ViajeF200DataSet(0, "", ""));

        rvViajes.setNestedScrollingEnabled(false);
        rvViajes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        viajeRecyclerViewAdapter = new ViajeRecyclerViewAdapter(getApplicationContext(), viajeF200DataSets);
        viajeRecyclerViewAdapter.delegate = F200Activity.this;
        rvViajes.setAdapter(viajeRecyclerViewAdapter);

        viajeRecyclerViewAdapter.setOnItemClickListener(new ViajeRecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(v.getId() == R.id.ivTrash && position > 0) {
                    viajeF200DataSets.remove(position);
                    viajeRecyclerViewAdapter.notifyDataSetChanged();

                    requestFocusCiudad();

                    if(viajeF200DataSets.size() < 3) {
                        llAddViajes.setVisibility(View.VISIBLE);
                    }
                } else if(v.getId() == R.id.rlContentCountry) {

                    PaisDialogFragment fragment = PaisDialogFragment.newInstance(position);
                    fragment.delegate = F200Activity.this;
                    fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
                    fragment.show(getSupportFragmentManager(), "");

                }
            }
        });

        ///////////////////////////////
        /// SIGNOS ALARMA
        ///////////////////////////////

        vCursorSigAlarm2 = mPrinciDbHelper.getAllPrinci(SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2TableN);
        while(vCursorSigAlarm2.moveToNext()) {
            SigAlarm2DataSet vDstDepart = new SigAlarm2DataSet();
            vDstDepart.setId(vCursorSigAlarm2.getInt(vCursorSigAlarm2.getColumnIndex(SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Identi)));
            vDstDepart.setDescripcion(vCursorSigAlarm2.getString(vCursorSigAlarm2.getColumnIndex(SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Descri)));
            signList.add(vDstDepart);
        }

        for(int i=0; i < signList.size(); i++) {
            CheckBox checkBox = new CheckBox(F200Activity.this);
            checkBox.setTag(signList.get(i).getId());
            checkBox.setId(signList.get(i).getId());
            checkBox.setText(signList.get(i).getDescripcion());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int id_signo_alarma2 = (int) buttonView.getTag();

                    if(id_signo_alarma2 == 11) {
                        if(isChecked) {
                            tilOtroSigno.setVisibility(View.VISIBLE);
                        } else {
                            tilOtroSigno.setVisibility(View.GONE);
                            tietOtroSigno.setText("");
                        }
                    }
                }
            });
            llContentSign.addView(checkBox, i);
        }

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
            CheckBox checkBox = new CheckBox(F200Activity.this);
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
                                if(sexo == 1) {
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

                                llContentTrimestre.setVisibility(View.VISIBLE);
                                llContentTrimestre.requestFocus();
                            } else {
                                llContentTrimestre.setVisibility(View.GONE);
                                id_trimestre = 0;

                                tvEmbarazoTri.setText("Seleccione");
                                tvEmbarazoTri.setTextColor(oldColors);
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
                        case 14:

                            if(isChecked) {
                                uncheckedConditionsComorbidity();

                                tilOtroRiesgo.setVisibility(View.VISIBLE);
                                tietOtroRiesgo.requestFocus();
                            } else {
                                tilOtroRiesgo.setVisibility(View.GONE);
                                tietOtroRiesgo.setText("");
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

        tietInvestigatorDocument.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    MWebSerTokPac();
                }
            }
        });

        tietInvestigatorDocument.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    MWebSerTokPac();
                    return true;
                }
                return false;
            }
        });

        if(_id > 0) {
            Cursor cursoF200 = mPrinciDbHelper.getAllPrinciOne(F200DbHelper.TableC.TableN, F200DbHelper.TableC._id, String.valueOf(_id));
            if(cursoF200.moveToFirst()) {

                id_tip_seg = cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC.id_tip_seg));
                tvTypeSure.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.tip_seg)));
                tvTypeSure.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

                date = cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.fec_notif));
                tietDate.setText(Utils.changeDateFormat(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.fec_notif)), "yyyy-MM-dd", "dd/MM/yyyy"));
                tietProfessional.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.profesion)));

                if(cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC.hospitalizado)) == 1) {
                    rbYesHospitalized.setChecked(true);

                    dateHospitalized = cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.fec_hospi));
                    tietDateHospitalized.setText(Utils.changeDateFormat(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.fec_hospi)), "yyyy-MM-dd", "dd/MM/yyyy"));
                } else if (cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC.hospitalizado)) == 2) {
                    rbNoHospitalized.setChecked(true);
                }

                tietTemperature.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.temperatura)));
                tietTemperature.clearFocus();

                int id_200 = cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC._id));

                if(cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC.ha_viajado)) == 1) {
                    rbYesHaViajado.setChecked(true);

                    viajeF200DataSets.clear();
                    Cursor vCursorF200Viaje = mPrinciDbHelper.getAllPrinciOne(F200ViajeDbHelper.TableC.TableN, F200ViajeDbHelper.TableC._id_f200, String.valueOf(id_200));
                    if(null != vCursorF200Viaje) {
                        while(vCursorF200Viaje.moveToNext()) {
                            ViajeF200DataSet dataSet = new ViajeF200DataSet(
                                vCursorF200Viaje.getInt(vCursorF200Viaje.getColumnIndex(F200ViajeDbHelper.TableC.id_pais)),
                                vCursorF200Viaje.getString(vCursorF200Viaje.getColumnIndex(F200ViajeDbHelper.TableC.pais)),
                                vCursorF200Viaje.getString(vCursorF200Viaje.getColumnIndex(F200ViajeDbHelper.TableC.ciudad))
                            );

                            viajeF200DataSets.add(dataSet);
                        }
                        viajeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                } else if (cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC.ha_viajado)) == 2) {
                    rbNoHaViajado.setChecked(true);
                }

                id_cont_caso_conf = cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC.id_cont_caso_conf));
                contacto_caso_confirmado = cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.contacto_caso_confirmado));
                tvContactoCasoConfirmado.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.contacto_caso_confirmado)));
                tvContactoCasoConfirmado.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

                id_tip_doc_inve = cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.id_tip_doc_inve));
                tvTipDocInve.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.tip_doc_inve)));
                tvTipDocInve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

                tietInvestigatorDocument.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.doc_inve)));
                tietInvestigatorName.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.nomb_inve)));
                tietInvestigatorPaternal.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.paterno_inve)));
                tietInvestigatorMaternal.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.materno_inve)));
                tietObservation.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.observacion)));

                Cursor vCursorF200Laboratorio = mPrinciDbHelper.getAllPrinciOne(F200LaboratorioDbHelper.TableC.TableN, F200LaboratorioDbHelper.TableC._id_f200, String.valueOf(id_200));
                if(null != vCursorF200Laboratorio) {
                    while(vCursorF200Laboratorio.moveToNext()) {
                        LaboratoryDataDataSet dataSet = new LaboratoryDataDataSet();
                        dataSet.setDate(vCursorF200Laboratorio.getString(vCursorF200Laboratorio.getColumnIndex(F200LaboratorioDbHelper.TableC.fecha)));
                        dataSet.setHour(vCursorF200Laboratorio.getString(vCursorF200Laboratorio.getColumnIndex(F200LaboratorioDbHelper.TableC.hora)));
                        dataSet.setId_type_sample(vCursorF200Laboratorio.getInt(vCursorF200Laboratorio.getColumnIndex(F200LaboratorioDbHelper.TableC.id_tipo_muestra)));
                        dataSet.setType_sample(vCursorF200Laboratorio.getString(vCursorF200Laboratorio.getColumnIndex(F200LaboratorioDbHelper.TableC.tipo_muestra)));
                        laboratoryDataDataSets.add(dataSet);
                    }
                    laboratoryDataAdapter.notifyDataSetChanged();
                }

                Cursor vCursorF200Sintoma = mPrinciDbHelper.getAllPrinciOne(F200SintomaDbHelper.TableC.TableN, F200SintomaDbHelper.TableC._id_f200, String.valueOf(id_200));
                if(null != vCursorF200Sintoma) {
                    while(vCursorF200Sintoma.moveToNext()) {
                        int id_sintoma = vCursorF200Sintoma.getInt(vCursorF200Sintoma.getColumnIndex(F200SintomaDbHelper.TableC.id_sintoma));
                        CheckBox checkBox = llContentSign.findViewWithTag(id_sintoma);
                        checkBox.setChecked(true);
                        checkBox.clearFocus();
                    }
                }
                tietOtroSigno.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.otro_sintoma)));
                tietOtroSigno.clearFocus();

                Cursor vCursorF200Riesgo = mPrinciDbHelper.getAllPrinciOne(F200RiesgoDbHelper.TableC.TableN, F200RiesgoDbHelper.TableC._id_f200, String.valueOf(id_200));
                if(null != vCursorF200Riesgo) {
                    while(vCursorF200Riesgo.moveToNext()) {
                        int id_riesgo = vCursorF200Riesgo.getInt(vCursorF200Riesgo.getColumnIndex(F200RiesgoDbHelper.TableC.id_riesgo));
                        CheckBox checkBox = llContentConditionsComorbidity.findViewWithTag(id_riesgo);
                        checkBox.setChecked(true);
                        checkBox.clearFocus();
                    }
                }

                CheckBox cb11 = llContentConditionsComorbidity.findViewWithTag(11);
                if(cb11.isChecked()) {
                    tvEmbarazoTri.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.trimestre)));
                    tvEmbarazoTri.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                    id_trimestre = cursoF200.getInt(cursoF200.getColumnIndex(F200DbHelper.TableC.id_trimestre));
                }

                tietOtroRiesgo.setText(cursoF200.getString(cursoF200.getColumnIndex(F200DbHelper.TableC.otro_riesgo)));
                tietOtroRiesgo.clearFocus();

                Cursor vCursorF200Contacto = mPrinciDbHelper.getAllPrinciOne(F200ContactoDbHelper.TableC.TableN, F200ContactoDbHelper.TableC._id_f200, String.valueOf(id_200));
                if(null != vCursorF200Contacto) {
                    while(vCursorF200Contacto.moveToNext()) {
                        ContactF200DataSet contactF200DataSet = new ContactF200DataSet();
                        contactF200DataSet.setId_document_type(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.id_tip_doc)));
                        contactF200DataSet.setDocument_type(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.tip_doc)));
                        contactF200DataSet.setNumber_document(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.num_doc)));
                        contactF200DataSet.setDate_birth(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.fec_nac)));
                        contactF200DataSet.setName(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.nombres)));
                        contactF200DataSet.setPaternal(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.paterno)));
                        contactF200DataSet.setMaternal(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.materno)));
                        contactF200DataSet.setId_relationship(vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.id_parentesco)));
                        contactF200DataSet.setRelationship(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.parentesco)));
                        contactF200DataSet.setEdad(vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.edad)));
                        contactF200DataSet.setId_sex(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.id_sexo)));
                        contactF200DataSet.setSex(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.sexo)));
                        contactF200DataSet.setMobile(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.celular)));
                        contactF200DataSet.setAddress(vCursorF200Contacto.getString(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.direccion)));
                        contactF200DataSet.setF100(vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC.f100)));

                        int id_contacto = vCursorF200Contacto.getInt(vCursorF200Contacto.getColumnIndex(F200ContactoDbHelper.TableC._id_contacto));

                        ArrayList<ContactF200DataSet.RiskFactors> riskFactors = new ArrayList<>();

                        Cursor vCursorF200ContactoRiesgo = mPrinciDbHelper.getAllPrinciOne(F200ContactoRiesgoDbHelper.TableC.TableN, F200ContactoRiesgoDbHelper.TableC._id_contacto, String.valueOf(id_contacto));
                        if(null != vCursorF200ContactoRiesgo) {
                            while(vCursorF200ContactoRiesgo.moveToNext()) {
                                ContactF200DataSet.RiskFactors factors = new ContactF200DataSet.RiskFactors();
                                factors.setId_risk_factor(vCursorF200ContactoRiesgo.getInt(vCursorF200ContactoRiesgo.getColumnIndex(F200ContactoRiesgoDbHelper.TableC.id_riesgo)));
                                factors.setRisk_factor(vCursorF200ContactoRiesgo.getString(vCursorF200ContactoRiesgo.getColumnIndex(F200ContactoRiesgoDbHelper.TableC.riesgo)));
                                riskFactors.add(factors);
                            }
                            contactF200DataSet.setRiskFactors(riskFactors);
                        }
                        this.contactF200DataSets.add(contactF200DataSet);
                    }
                    contactF200RecyclerViewAdapter.notifyDataSetChanged();
                }

                (new Thread(new Runnable(){
                    public void run(){
                        nestedScrollView.fullScroll(View.FOCUS_UP);
                    }
                })).start();
            }
        }

    }

    @OnClick(R.id.ivQR)
    public void onClicQR(View view) {
        Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
        startActivityForResult(intent, 443);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment;

        switch (requestCode) {
            case 443:

                if(resultCode == 2) {
                    String code = data.getStringExtra("code");
                    tietInvestigatorDocument.setText(code);
                    tietInvestigatorDocument.setEnabled(false);

                    tietInvestigatorName.setText("");
                    tietInvestigatorName.setEnabled(true);
                    tietInvestigatorPaternal.setText("");
                    tietInvestigatorPaternal.setEnabled(true);
                    tietInvestigatorMaternal.setText("");
                    tietInvestigatorMaternal.setEnabled(true);

                    MWebSerTokPac();
                }

                break;
            case 444:

                if(resultCode == 2) {
                    fragment = getSupportFragmentManager().findFragmentByTag("ContactoCasoConfirmadoDialogFragment");
                    fragment.onActivityResult(requestCode, resultCode, data);
                }

                break;
        }
    }

    private void uncheckedConditionsComorbidity() {
        CheckBox box13 = llContentConditionsComorbidity.findViewWithTag(13);
        if(box13.isChecked()) {
            box13.setChecked(false);
        }
    }

    @OnClick({R.id.rlContentTypeSure})
    public void onClicAddTypeSure(View view) {
        TypeSureDialogFragment fragment = TypeSureDialogFragment.newInstance();
        fragment.delegate = F200Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlContentContactoCasoConfirmado})
    public void onClicContentContactoCasoConfirmado(View view) {
        ContactoCasoConfirmadoDialogFragment fragment = ContactoCasoConfirmadoDialogFragment.newInstance();
        fragment.delegate = F200Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), fragment.getClass().getSimpleName());
    }

    @OnClick({R.id.llAddLaboratoryData, R.id.btnAddLaboratoryData})
    public void onClicAddLaboratoryData(View view) {
        AddLaboratoryDataF200DialogFragment fragment = AddLaboratoryDataF200DialogFragment.newInstance(laboratoryDataDataSets);
        fragment.delegate = F200Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.llAddViajes, R.id.btnAddViajes})
    public void onClicAddViajes(View view) {
        if(viajeF200DataSets.size() == 2) {
            llAddViajes.setVisibility(View.GONE);
        }
        if(viajeF200DataSets.size() == 3) {
            return;
        }

        viajeF200DataSets.add(new ViajeF200DataSet(0, "", ""));
        viajeRecyclerViewAdapter.notifyDataSetChanged();

        requestFocusCiudad();
    }

    private void requestFocusCiudad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolder = rvViajes.getChildViewHolder(rvViajes.getChildAt(viajeF200DataSets.size()-1));
                View v = viewHolder.itemView;
                TextInputEditText tietCiudad = v.findViewById(R.id.tietCiudad);
                tietCiudad.requestFocus();
            }
        }, 50);
    }

    @OnClick({R.id.llAddContact, R.id.btnAddContact})
    public void onClicAddContact(View view) {
        AddContactsF200DialogFragment fragment = AddContactsF200DialogFragment.newInstance();
        fragment.delegate = F200Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlContentTipDoc})
    public void onClicTipDoc(View view) {
        TipDocDialogFragment fragment = TipDocDialogFragment.newInstance(1);
        fragment.delegate = F200Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlContentEmbarazoTri})
    public void onClicEmbarazoTri(View view) {
        EmbarazoTriDialogFragment fragment = EmbarazoTriDialogFragment.newInstance();
        fragment.delegate = F200Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void setTypeSafe(TipSegDataSet dataSet) {
        tvTypeSure.setText(dataSet.getTip_seg());
        tvTypeSure.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_tip_seg = dataSet.getId_tip_seg();

        if(dataSet.getId_tip_seg() == 6) {
            tilOtroSeguro.setVisibility(View.VISIBLE);
            tietOtroSeguro.requestFocus();
        } else {
            tilOtroSeguro.setVisibility(View.GONE);
            tietOtroSeguro.setText("");
        }
    }

    @Override
    public void addLaboratoryData(LaboratoryDataDataSet dataSet) {
        laboratoryDataDataSets.add(dataSet);
        laboratoryDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void addContact(ContactF200DataSet dataSet) {
        contactF200DataSets.add(dataSet);
        contactF200RecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void setTipDoc(TipDocDataSet dataSet) {
        tvTipDocInve.setText(dataSet.getTipDocDescri());
        tvTipDocInve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_tip_doc_inve = dataSet.getTipDocIdenti();

        if(dataSet.getTipDocIdenti().equals("1")) {
            ivQR.setVisibility(View.VISIBLE);
        } else {
            ivQR.setVisibility(View.GONE);
        }

        inputTypeByTypeDocument(dataSet.getTipDocIdenti());
    }

    public void inputTypeByTypeDocument(String tipo_doc) {
        switch (tipo_doc) {
            case "1":
                tietInvestigatorDocument.setText("");
                tietInvestigatorDocument.setInputType(InputType.TYPE_CLASS_NUMBER);
                tietInvestigatorDocument.setEnabled(true);
                tietInvestigatorDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
                break;
            case "2":
                tietInvestigatorDocument.setText("");
                tietInvestigatorDocument.setInputType(InputType.TYPE_CLASS_NUMBER);
                tietInvestigatorDocument.setEnabled(true);
                tietInvestigatorDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
                break;
            case "3":
            case "4":
            case "5":
                tietInvestigatorDocument.setText("");
                tietInvestigatorDocument.setInputType(InputType.TYPE_CLASS_TEXT);
                tietInvestigatorDocument.setEnabled(true);
                tietInvestigatorDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                break;
            case "6":
                tietInvestigatorDocument.setText("");
                tietInvestigatorDocument.setInputType(InputType.TYPE_NULL);
                tietInvestigatorDocument.setEnabled(true);
                tietInvestigatorDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(0) });
                break;
        }

        tietInvestigatorName.setEnabled(true);
        tietInvestigatorName.setText("");

        tietInvestigatorPaternal.setEnabled(true);
        tietInvestigatorPaternal.setText("");

        tietInvestigatorMaternal.setEnabled(true);
        tietInvestigatorMaternal.setText("");
    }

    @Override
    public void setContactoCasoConfirmado(ContactoCasoConfirmadoDataSet dataSet) {
        tvContactoCasoConfirmado.setText(dataSet.getDescripcion());
        tvContactoCasoConfirmado.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_cont_caso_conf = dataSet.getId();
        contacto_caso_confirmado = dataSet.getDescripcion();
    }

    @Override
    public void setPais(PaisDataSet dataSet, int position) {
        viajeF200DataSets.get(position).setId_pais(dataSet.getId_pais());
        viajeF200DataSets.get(position).setPais(dataSet.getPais());
        viajeRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCiudad(int position, String ciudad) {
        viajeF200DataSets.get(position).setCiudad(ciudad);
    }

    @Override
    public void setEmbarazoTri(EmbarazoTriDataSet dataSet) {
        tvEmbarazoTri.setText(dataSet.getDescripcion());
        tvEmbarazoTri.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_trimestre = dataSet.getId();
    }

    @OnClick(R.id.efab)
    public void onClicEFAB(View view) {
        efab.setEnabled(false);

        if(id_tip_seg == 0) {
            showMessageValidation("Seleccione el tipo de seguro.");
            return;
        }

        if(date.isEmpty()) {
            showMessageValidation("Seleccione la fecha de notificación.");
            return;
        }

        if(rgHospitalized.getCheckedRadioButtonId() != -1 && rgHospitalized.getCheckedRadioButtonId() == R.id.rbYesHospitalized && tietDateHospitalized.getText().toString().isEmpty()) {
            showMessageValidation("Seleccione la fecha de hospitalización.");
            return;
        }

        int riesgo = 0;
        for(int i=0; i < conditionsComorbidityList.size(); i++) {
            CheckBox checkBox = llContentConditionsComorbidity.findViewWithTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
            if(checkBox.isChecked()) {
                riesgo++;
            }
        }

        if(riesgo == 0) {
            showMessageValidation("Seleccione una o más condiciones de comorbilidad.");
            return;
        }

        if(rgHaViajado.getCheckedRadioButtonId() == -1) {
            showMessageValidation("Seleccione si ha viajado.");
            return;
        }

        if(rgHaViajado.getCheckedRadioButtonId() == R.id.rbYesHaViajado) {
            for(int i=0; i < viajeF200DataSets.size(); i++) {
                if(viajeF200DataSets.get(i).getId_pais() == 0) {
                    showMessageValidation("Seleccione país.");
                    return;
                }
            }
        }

        if(id_cont_caso_conf == 0) {
            showMessageValidation("Debe seleccionar si ha tenido contacto con algun caso probable.");
            return;
        }

        if(tietInvestigatorDocument.getText().toString().isEmpty()) {
            showMessageValidation("Ingrese el número de documento del investigador.");
            return;
        }

        if(tietInvestigatorName.getText().toString().isEmpty()) {
            showMessageValidation("Ingrese nombre del investigador.");
            return;
        }

        if(tietInvestigatorPaternal.getText().toString().isEmpty()) {
            showMessageValidation("Ingrese el apellido paterno del investigador.");
            return;
        }

        int _id_tip_doc_inv = 0;

        Cursor cursorTipDoc = mPrinciDbHelper.getAllPrinci(TipDocDbHelper.TipDocTableC.TipDocTableN);
        if(cursorTipDoc.getCount() != 0) {
            while(cursorTipDoc.moveToNext()) {
                if(cursorTipDoc.getString(cursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.slug)).equals(id_tip_doc_inve)) {
                    _id_tip_doc_inv = cursorTipDoc.getInt(cursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocIdenti));
                }
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(F200DbHelper.TableC.id_tip_seg, id_tip_seg);
        contentValues.put(F200DbHelper.TableC.tip_seg, tvTypeSure.getText().toString());
        contentValues.put(F200DbHelper.TableC.otro_seguro, tietOtroSeguro.getText().toString());
        contentValues.put(F200DbHelper.TableC.fec_notif, date);
        contentValues.put(F200DbHelper.TableC.profesion, tietProfessional.getText().toString());
        contentValues.put(F200DbHelper.TableC.hospitalizado, rgHospitalized.getCheckedRadioButtonId() == -1 ? 0 : (rgHospitalized.getCheckedRadioButtonId() == R.id.rbYesHospitalized ? 1 : 2));
        contentValues.put(F200DbHelper.TableC.fec_hospi, dateHospitalized);
        contentValues.put(F200DbHelper.TableC.temperatura, tietTemperature.getText().toString());
        contentValues.put(F200DbHelper.TableC.otro_sintoma, tietOtroSigno.getText().toString());
        contentValues.put(F200DbHelper.TableC.otro_riesgo, tietOtroRiesgo.getText().toString());
        contentValues.put(F200DbHelper.TableC.id_trimestre, id_trimestre);
        contentValues.put(F200DbHelper.TableC.trimestre, id_trimestre > 0 ? tvEmbarazoTri.getText().toString() : "");
        contentValues.put(F200DbHelper.TableC.ha_viajado, rgHaViajado.getCheckedRadioButtonId() == -1 ? 0 : (rgHaViajado.getCheckedRadioButtonId() == R.id.rbYesHaViajado ? 1 : 0));
        contentValues.put(F200DbHelper.TableC.id_cont_caso_conf, id_cont_caso_conf);
        contentValues.put(F200DbHelper.TableC.contacto_caso_confirmado, tvContactoCasoConfirmado.getText().toString());
        contentValues.put(F200DbHelper.TableC.id_tip_doc_inve, _id_tip_doc_inv);
        contentValues.put(F200DbHelper.TableC.tip_doc_inve, tvTipDocInve.getText().toString());
        contentValues.put(F200DbHelper.TableC.doc_inve, tietInvestigatorDocument.getText().toString());
        contentValues.put(F200DbHelper.TableC.nomb_inve, tietInvestigatorName.getText().toString().toUpperCase());
        contentValues.put(F200DbHelper.TableC.paterno_inve, tietInvestigatorPaternal.getText().toString().toUpperCase());
        contentValues.put(F200DbHelper.TableC.materno_inve, tietInvestigatorMaternal.getText().toString().toUpperCase());
        contentValues.put(F200DbHelper.TableC.observacion, tietObservation.getText().toString());
        contentValues.put(F200DbHelper.TableC.id_tip_doc, id_tipo_documento);
        contentValues.put(F200DbHelper.TableC.tipo_doc, tipodocumento);
        contentValues.put(F200DbHelper.TableC.num_doc, documento);
        contentValues.put(F200DbHelper.TableC.paciente, paciente);
        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();

        contentValues.put(F200DbHelper.TableC.id_usuario, usuariDataSet.getUsuariIdenti());

        if(_id == 0) {
            contentValues.put(F200DbHelper.TableC.id_conglomerado, PreferencesManager.getIdConglomerado(getApplicationContext()));
            contentValues.put(F200DbHelper.TableC.conglomerado, PreferencesManager.getConglomerado(getApplicationContext()));
            contentValues.put(F200DbHelper.TableC.id_tipo_conglomerado, PreferencesManager.getIdTipoConglomerado(getApplicationContext()));
            contentValues.put(F200DbHelper.TableC.tipo_conglomerado, PreferencesManager.getTipoConglomerado(getApplicationContext()));
            contentValues.put(F200DbHelper.TableC.id_dato_paciente, id_dato_paciente);
        }
        contentValues.put(F200DbHelper.TableC.id_f200, 0);

        int f200 = 0;

        if(_id == 0) {
            f200 = (int) mPrinciDbHelper.savePrinci(F200DbHelper.TableC.TableN, contentValues);
        } else {
            f200 = _id;

            mPrinciDbHelper.updatePrinci(F200DbHelper.TableC.TableN, contentValues, F200DbHelper.TableC._id, String.valueOf(f200));

            mPrinciDbHelper.deleteAll(F200SintomaDbHelper.TableC.TableN);
            mPrinciDbHelper.deleteAll(F200RiesgoDbHelper.TableC.TableN);
            mPrinciDbHelper.deleteAll(F200ViajeDbHelper.TableC.TableN);
            mPrinciDbHelper.deleteAll(F200LaboratorioDbHelper.TableC.TableN);
            mPrinciDbHelper.deleteAll(F200ContactoDbHelper.TableC.TableN);
            mPrinciDbHelper.deleteAll(F200ContactoRiesgoDbHelper.TableC.TableN);
        }

        for(int i=0; i<signList.size(); i++) {
            CheckBox checkBox = llContentSign.findViewWithTag(signList.get(i).getId());
            if(checkBox.isChecked()) {
                ContentValues cv1 = new ContentValues();
                cv1.put(F200SintomaDbHelper.TableC._id_f200, f200);
                cv1.put(F200SintomaDbHelper.TableC.id_sintoma, signList.get(i).getId());
                cv1.put(F200SintomaDbHelper.TableC.sintoma, signList.get(i).getDescripcion());
                mPrinciDbHelper.savePrinci(F200SintomaDbHelper.TableC.TableN, cv1);
            }
        }

        for(int i=0; i<conditionsComorbidityList.size(); i++) {
            CheckBox checkBox = llContentConditionsComorbidity.findViewWithTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
            if(checkBox.isChecked()) {
                ContentValues cv2 = new ContentValues();
                cv2.put(F200RiesgoDbHelper.TableC._id_f200, f200);
                cv2.put(F200RiesgoDbHelper.TableC.id_riesgo, conditionsComorbidityList.get(i).getRiesgoIdenti());
                cv2.put(F200RiesgoDbHelper.TableC.riesgo, conditionsComorbidityList.get(i).getRiesgoDescri());
                mPrinciDbHelper.savePrinci(F200RiesgoDbHelper.TableC.TableN, cv2);
            }
        }

        for(int i=0; i<viajeF200DataSets.size(); i++) {
            ContentValues cv1 = new ContentValues();
            cv1.put(F200ViajeDbHelper.TableC._id_f200, (int) f200);
            cv1.put(F200ViajeDbHelper.TableC.id_pais, viajeF200DataSets.get(i).getId_pais());
            cv1.put(F200ViajeDbHelper.TableC.pais, viajeF200DataSets.get(i).getPais());
            cv1.put(F200ViajeDbHelper.TableC.ciudad, viajeF200DataSets.get(i).getCiudad());
            mPrinciDbHelper.savePrinci(F200ViajeDbHelper.TableC.TableN, cv1);
        }

        for(int i=0; i<laboratoryDataDataSets.size(); i++) {
            ContentValues cv1 = new ContentValues();
            cv1.put(F200LaboratorioDbHelper.TableC._id_f200, (int) f200);
            cv1.put(F200LaboratorioDbHelper.TableC.fecha, laboratoryDataDataSets.get(i).getDate());
            cv1.put(F200LaboratorioDbHelper.TableC.hora, laboratoryDataDataSets.get(i).getHour());
            cv1.put(F200LaboratorioDbHelper.TableC.id_tipo_muestra, laboratoryDataDataSets.get(i).getId_type_sample());
            cv1.put(F200LaboratorioDbHelper.TableC.tipo_muestra, laboratoryDataDataSets.get(i).getType_sample());
            mPrinciDbHelper.savePrinci(F200LaboratorioDbHelper.TableC.TableN, cv1);
        }

        for(int i=0; i<contactF200DataSets.size(); i++) {
            ContentValues cv1 = new ContentValues();
            cv1.put(F200ContactoDbHelper.TableC._id_f200, (int) f200);
            cv1.put(F200ContactoDbHelper.TableC.id_tip_doc, contactF200DataSets.get(i).getId_document_type());
            cv1.put(F200ContactoDbHelper.TableC.tip_doc, contactF200DataSets.get(i).getDocument_type());
            cv1.put(F200ContactoDbHelper.TableC.num_doc, contactF200DataSets.get(i).getNumber_document());
            cv1.put(F200ContactoDbHelper.TableC.fec_nac, contactF200DataSets.get(i).getDate_birth());
            cv1.put(F200ContactoDbHelper.TableC.nombres, contactF200DataSets.get(i).getName());
            cv1.put(F200ContactoDbHelper.TableC.paterno, contactF200DataSets.get(i).getPaternal());
            cv1.put(F200ContactoDbHelper.TableC.materno, contactF200DataSets.get(i).getMaternal());
            cv1.put(F200ContactoDbHelper.TableC.id_parentesco, contactF200DataSets.get(i).getId_relationship());
            cv1.put(F200ContactoDbHelper.TableC.parentesco, contactF200DataSets.get(i).getRelationship());
            cv1.put(F200ContactoDbHelper.TableC.edad, contactF200DataSets.get(i).getEdad());
            cv1.put(F200ContactoDbHelper.TableC.id_sexo, contactF200DataSets.get(i).getId_sex());
            cv1.put(F200ContactoDbHelper.TableC.sexo, contactF200DataSets.get(i).getSex());
            cv1.put(F200ContactoDbHelper.TableC.celular, contactF200DataSets.get(i).getMobile());
            cv1.put(F200ContactoDbHelper.TableC.direccion, contactF200DataSets.get(i).getAddress());
            cv1.put(F200ContactoDbHelper.TableC.f100, contactF200DataSets.get(i).getF100());
            long contacto = mPrinciDbHelper.savePrinci(F200ContactoDbHelper.TableC.TableN, cv1);

            for(int j=0; j < contactF200DataSets.get(i).getRiskFactors().size(); j++) {
                ContentValues cv2 = new ContentValues();
                cv2.put(F200ContactoRiesgoDbHelper.TableC._id_contacto, (int) contacto);
                cv2.put(F200ContactoRiesgoDbHelper.TableC.id_riesgo, contactF200DataSets.get(i).getRiskFactors().get(j).getId_risk_factor());
                cv2.put(F200ContactoRiesgoDbHelper.TableC.riesgo, contactF200DataSets.get(i).getRiskFactors().get(j).getRisk_factor());
                mPrinciDbHelper.savePrinci(F200ContactoRiesgoDbHelper.TableC.TableN, cv2);
            }
        }

        if(_id > 0) {
            Toasty.success(getApplicationContext(), "Ficha 200 actualizada satisfactoriamente", Toasty.LENGTH_SHORT).show();
            setResult(1);
        } else {
            Toasty.success(getApplicationContext(), "Ficha 200 registrada satisfactoriamente", Toasty.LENGTH_SHORT).show();
        }

        onBackPressed();
    }

    private void showMessageValidation(String message) {
        Toasty.info(getApplicationContext(), message, Toasty.LENGTH_SHORT).show();
        efab.setEnabled(true);
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
                        Toast.makeText(getApplicationContext(), "El servicio no se encuentra disponible, se pasara al ingreso manual", Toast.LENGTH_SHORT).show();
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
                vDstDirWeb.getDirWebApiUrl() + Constants.URL_PACIENTE_CONFIRMADO + id_tip_doc_inve + "/" + tietInvestigatorDocument.getText().toString().trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("response",response);
                            JSONObject Jdatos_response = new JSONObject(response);
                            if(Jdatos_response.getString("cod_respuesta").equals("0000")) {
                                JSONObject Jdatos_paciente = Jdatos_response.getJSONObject("paciente");
                                JSONObject Jdatos_fichas = Jdatos_response.getJSONObject("fichas");
                                tvTipDocInve.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.disabled));
                                tvTipDocInve.setEnabled(false);
                                tietInvestigatorDocument.setEnabled(false);
                                tietInvestigatorName.setText(Jdatos_paciente.getString("nombre"));
                                tietInvestigatorName.setEnabled(false);
                                tietInvestigatorPaternal.setText(Jdatos_paciente.getString("ape_paterno"));
                                tietInvestigatorPaternal.setEnabled(false);
                                tietInvestigatorMaternal.setText(Jdatos_paciente.getString("ape_materno"));
                                tietInvestigatorMaternal.setEnabled(false);
                            } else if(Jdatos_response.getString("cod_respuesta").equals("7000")) {
                                tietInvestigatorDocument.setText("");
                                Toasty.info(getApplicationContext(), "El DNI ingresado corresponde a una persona fallecida.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "No existe el registro de la persona, se pasara al ingreso manual", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "El servicio no se encuentra disponible, se pasara al ingreso manual", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rgHospitalized:

                RadioButton rbHospitalizado = rgHospitalized.findViewById(checkedId);

                if(rbHospitalizado.getId() == R.id.rbYesHospitalized && rbHospitalizado.isChecked()) {
                    tilDateHospitalized.setVisibility(View.VISIBLE);
                } else {
                    tilDateHospitalized.setVisibility(View.GONE);
                    tietDateHospitalized.setText("");
                }

                break;
            case R.id.rgHaViajado:

                RadioButton rbViajado = rgHaViajado.findViewById(checkedId);
                viajeF200DataSets.clear();

                if(rbViajado.getId() == R.id.rbYesHaViajado && rbViajado.isChecked()) {
                    tvTitleViaje.setVisibility(View.VISIBLE);
                    rvViajes.setVisibility(View.VISIBLE);
                    llAddViajes.setVisibility(View.VISIBLE);

                    viajeF200DataSets.add(new ViajeF200DataSet(0, "", ""));
                    viajeRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    tvTitleViaje.setVisibility(View.GONE);
                    rvViajes.setVisibility(View.GONE);
                    llAddViajes.setVisibility(View.GONE);
                }

                break;
        }
    }

}