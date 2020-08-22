package minsa.formulario.UI.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import minsa.formulario.DataSet.CodigoPaisDataSet;
import minsa.formulario.DataSet.DepartDataSet;
import minsa.formulario.DataSet.DistriDataSet;
import minsa.formulario.DataSet.ProfesDataSet;
import minsa.formulario.DataSet.ProvinDataSet;
import minsa.formulario.DataSet.SintomDataSet;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DataSet.UbicacDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.DatoPacienteDbHelper;
import minsa.formulario.DbHelper.DatoPacienteSintomaDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.SintomDbHelper;
import minsa.formulario.R;
import minsa.formulario.UI.Activity.f100.F100Activity;
import minsa.formulario.UI.Fragments.Dialog.CalendarDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.CodigoPaisDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.DepartamentoDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.DistritoDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.ProfesionDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.ProvinciaDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.TipDocDialogFragment;
import minsa.formulario.Util.AppConstant;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.GpsUtil;
import minsa.formulario.Util.Utils;

public class Ficha_Paciente extends AppCompatActivity implements
        TipDocDialogFragment.TipDocDialogListener,
        DepartamentoDialogFragment.DepartamentoDialogListener,
        ProvinciaDialogFragment.ProvinciaDialogListener,
        DistritoDialogFragment.DistritoDialogListener,
        CalendarDialogFragment.CalendarDialogListener,
        ProfesionDialogFragment.ProfesionDialogListener,
        View.OnClickListener,
        CodigoPaisDialogFragment.CodigoPaisDialogListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rlContentTipDoc)
    RelativeLayout rlContentTipDoc;
    @BindView(R.id.tvTipDoc)
    TextView tvTipDoc;
    @BindView(R.id.tilNumberDocument)
    TextInputLayout tilNumberDocument;
    @BindView(R.id.tietNumberDocument)
    TextInputEditText tietNumberDocument;

    @BindView(R.id.tilcoDInt)
    TextInputLayout tilcoDInt;
    @BindView(R.id.tietcoDInt)
    TextInputEditText tietcoDInt;
    @BindView(R.id.tilName) TextInputLayout tilName;
    @BindView(R.id.tietName) TextInputEditText tietName;
    @BindView(R.id.tilPaternal) TextInputLayout tilPaternal;
    @BindView(R.id.tietPaternal) TextInputEditText tietPaternal;
    @BindView(R.id.tilMaternal) TextInputLayout tilMaternal;
    @BindView(R.id.tietMaternal) TextInputEditText tietMaternal;
    @BindView(R.id.tilFechaNac) TextInputLayout tilFechaNac;
    @BindView(R.id.tietFechaNac) TextInputEditText tietFechaNac;
    @BindView(R.id.rgSexo)
    RadioGroup rgSexo;
    @BindView(R.id.rbFemenino)
    RadioButton rbFemenino;
    @BindView(R.id.rbMasculino) RadioButton rbMasculino;
    @BindView(R.id.tilMobile) TextInputLayout tilMobile;
    @BindView(R.id.tietMobile) TextInputEditText tietMobile;
    @BindView(R.id.tilMobileContact) TextInputLayout tilMobileContact;
    @BindView(R.id.tietMobileContact) TextInputEditText tietMobileContact;
    @BindView(R.id.tilCorreoElectronico) TextInputLayout tilCorreoElectronico;
    @BindView(R.id.tietCorreoElectronico) TextInputEditText tietCorreoElectronico;
    @BindView(R.id.rgTipoResidencia) RadioGroup rgTipoResidencia;
    @BindView(R.id.rbSiTipoResidencia) RadioButton rbSiTipoResidencia;
    @BindView(R.id.rbNoTipoResidencia) RadioButton rbNoTipoResidencia;
    @BindView(R.id.tilDireccion) TextInputLayout tilDireccion;
    @BindView(R.id.tietDireccion) TextInputEditText tietDireccion;
    @BindView(R.id.rlContentDepartamento) RelativeLayout rlContentDepartamento;
    @BindView(R.id.tvDepartamento) TextView tvDepartamento;
    @BindView(R.id.rlContentProvincia) RelativeLayout rlContentProvincia;
    @BindView(R.id.tvProvincia) TextView tvProvincia;
    @BindView(R.id.rlContentDistrito) RelativeLayout rlContentDistrito;
    @BindView(R.id.tvDistrito) TextView tvDistrito;
    @BindView(R.id.tilLatitud) TextInputLayout tilLatitud;
    @BindView(R.id.tietLatitud) TextInputEditText tietLatitud;
    @BindView(R.id.tilLongitud) TextInputLayout tilLongitud;
    @BindView(R.id.tietLongitud) TextInputEditText tietLongitud;
    @BindView(R.id.rgPersonalSalud) RadioGroup rgPersonalSalud;
    @BindView(R.id.rbSiPersonalSalud) RadioButton rbSiPersonalSalud;
    @BindView(R.id.rbNoPersonalSalud) RadioButton rbNoPersonalSalud;
    @BindView(R.id.llContentProfesion)
    LinearLayout llContentProfesion;
    @BindView(R.id.rlProfesion) RelativeLayout rlProfesion;
    @BindView(R.id.tvProfesion) TextView tvProfesion;
    @BindView(R.id.rgTieneSintoma) RadioGroup rgTieneSintoma;
    @BindView(R.id.rbSiTieneSintoma) RadioButton rbSiTieneSintoma;
    @BindView(R.id.rbNoTieneSintoma) RadioButton rbNoTieneSintoma;
    @BindView(R.id.tietFechaSintoma) TextInputEditText tietFechaSintoma;
    @BindView(R.id.llContentSintomas) LinearLayout llContentSintomas;
    @BindView(R.id.llSintomas) LinearLayout llSintomas;
    @BindView(R.id.tilOtroSintoma) TextInputLayout tilOtroSintoma;
    @BindView(R.id.tietOtroSintoma) TextInputEditText tietOtroSintoma;
    @BindView(R.id.efab)
    ExtendedFloatingActionButton efab;
    @BindView(R.id.efa)
    ExtendedFloatingActionButton efa;
    @BindView(R.id.tietCodigoPaisCel) TextInputEditText tietCodigoPaisCel;
    @BindView(R.id.tietCodigoPaisTel) TextInputEditText tietCodigoPaisTel;

    ArrayList<SintomDataSet> sintomDataSets = new ArrayList<>();

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorSintomas;
    private Cursor vCursorprueva;
    private UbicacDataSet vDsUbicac;
    private UsuariDataSet vDsUsuari;

    private ColorStateList oldColors;

    private String id_tip_doc = "";
    private String fecha_nac = "";
    private String id_departamento = "";
    private String id_provincia = "";
    private String id_distrito = "";
    private String fecha_sintoma = "";
    private int id_profesion = 0;
    private String codigo_pais_celular = "";
    private String codigo_pais_telefono = "";
    private String id_dato_paciente = "";
    private String paciente = "";
    private View vView;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private Cursor vCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha__paciente);
        mPrinciDbHelper = new PrinciDbHelper(getApplicationContext());

        ButterKnife.bind(this);
        toolbar.setTitle("Datos del Interno");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oldColors =  tvTipDoc.getTextColors();

        tietCodigoPaisCel.setText("+51 PER");
        codigo_pais_celular = "+51";
        tietCodigoPaisTel.setText("+51 PER");
        codigo_pais_telefono = "+51";
GET_DATA();
        //id_tip_doc = getIntent().getStringExtra("id_tipo_documento");

        //tvTipDoc.setText(getIntent().getStringExtra("tipodocumento"));
        tvTipDoc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        //tietNumberDocument.setText(getIntent().getStringExtra("documento"));
        //tietNumberDocument.setFocusable(false);
        //tietNumberDocument.setLongClickable(false);

        tietFechaNac.setOnClickListener(this);


        //vDsUbicac = UbicacDataSet.getInstance();
        //vDsUsuari = UsuariDataSet.getInstance();

        rgPersonalSalud.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);

                if(radioButton.getId() == R.id.rbSiPersonalSalud &&  radioButton.isChecked()) {
                    llContentProfesion.setVisibility(View.VISIBLE);
                } else {
                    llContentProfesion.setVisibility(View.GONE);
                    tvProfesion.setText("Seleccionar");
                    tvTipDoc.setTextColor(oldColors);
                    id_profesion = 0;
                }
            }
        });

        rgTieneSintoma.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);

                if(radioButton.getId() == R.id.rbSiTieneSintoma &&  radioButton.isChecked()) {
                    llContentSintomas.setVisibility(View.VISIBLE);
                } else {
                    llContentSintomas.setVisibility(View.GONE);
                    tietFechaSintoma.setText("");

                    for(int i=0; i < sintomDataSets.size(); i++) {
                        CheckBox checkBox = llContentSintomas.findViewWithTag(sintomDataSets.get(i).getId());
                        if(checkBox.isChecked()) {
                            checkBox.setChecked(false);
                        }
                    }

                }
            }
        });

        vCursorSintomas = mPrinciDbHelper.getAllPrinci(SintomDbHelper.SintomTableC.SintomTableN);
        while(vCursorSintomas.moveToNext()) {
            SintomDataSet vDstDepart = new SintomDataSet();
            vDstDepart.setId(vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)));
            vDstDepart.setDescripcion(vCursorSintomas.getString(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomDescri)));
            sintomDataSets.add(vDstDepart);
        }

        for(int i=0; i < sintomDataSets.size(); i++) {
            CheckBox checkBox = new CheckBox(Ficha_Paciente.this);
            checkBox.setTag(sintomDataSets.get(i).getId());
            checkBox.setId(sintomDataSets.get(i).getId());
            checkBox.setText(sintomDataSets.get(i).getDescripcion());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int id_signo_alarma2 = (int) buttonView.getTag();

                    switch (id_signo_alarma2) {
                        case 10:

                            if(isChecked) {
                                uncheckedSintomas();
                                tilOtroSintoma.setVisibility(View.VISIBLE);
                            } else {
                                tilOtroSintoma.setVisibility(View.GONE);
                                tietOtroSintoma.setText("");
                            }

                            break;
                        case 16:

                            if(isChecked) {
                                for(int i=0; i < llSintomas.getChildCount(); i++) {
                                    if(((int) llSintomas.getChildAt(i).getTag()) != 16) {
                                        CheckBox boxs = llSintomas.findViewWithTag(llSintomas.getChildAt(i).getTag());
                                        boxs.setChecked(false);
                                    }
                                }
                            }

                            break;
                        default:
                            if(isChecked) {
                                uncheckedSintomas();
                            }
                    }
                }
            });
            llSintomas.addView(checkBox, i);
        }

        /////////////////////////////

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dpDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String newFormat = sdf.format(myCalendar.getTime());
                fecha_sintoma = Utils.changeDateFormat(newFormat, "dd/MM/yyyy", "yyyy-MM-dd");
                tietFechaSintoma.setText(newFormat);
            }
        };

        tietFechaSintoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendarMin = Calendar.getInstance();
                calendarMin.add(Calendar.DAY_OF_MONTH, -15);

                final Calendar calendarToday = Calendar.getInstance();
                int year = calendarToday.get(Calendar.YEAR);
                int month = calendarToday.get(Calendar.MONTH);
                int day = calendarToday.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Ficha_Paciente.this, dpDate, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        //vDsUbicac.setUbicacLatitu("0");
        //vDsUbicac.setUbicacLongit("0");

       /* getLocation();

        tietLatitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vDsUbicac.getUbicacLatitu().equals("0")){
                    getLocation();
                }
            }
        });

        tietLongitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vDsUbicac.getUbicacLongit().equals("0")){
                    getLocation();
                }
            }
        });
*/
        tietMobile.setInputType(InputType.TYPE_CLASS_NUMBER);
        tietMobile.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });

        tietMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        tietMobileContact.setInputType(InputType.TYPE_CLASS_NUMBER);
        tietMobileContact.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });

        tietMobileContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }



    private void uncheckedSintomas() {
        CheckBox box16 = llContentSintomas.findViewWithTag(16);
        if(box16.isChecked()) {
            box16.setChecked(false);
        }
    }

    @OnClick(R.id.rlContentTipDoc)
    public void onClicTipoDoc(View view) {
        TipDocDialogFragment fragment = TipDocDialogFragment.newInstance(view.getId());
        fragment.delegate = Ficha_Paciente.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.tietCodigoPaisCel, R.id.tietCodigoPaisTel})
    public void onClicCodigoPais(View view) {
        CodigoPaisDialogFragment fragment = CodigoPaisDialogFragment.newInstance(view.getId());
        fragment.delegate = Ficha_Paciente.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.rlContentDepartamento)
    public void onClicDepartamento(View view) {
        DepartamentoDialogFragment fragment = DepartamentoDialogFragment.newInstance();
        fragment.delegate = Ficha_Paciente.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");

    }

    @OnClick(R.id.rlContentProvincia)
    public void onClicProvincia(View view) {
        if(id_departamento.isEmpty()) {
            Toasty.info(getApplicationContext(), "Seleccione departamento", Toasty.LENGTH_SHORT).show();
            return;
        }

        ProvinciaDialogFragment fragment = ProvinciaDialogFragment.newInstance(id_departamento);
        fragment.delegate = Ficha_Paciente.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.rlContentDistrito)
    public void onClicDistrito(View view) {
        if(id_provincia.isEmpty()) {
            Toasty.info(getApplicationContext(), "Seleccione provincia", Toasty.LENGTH_SHORT).show();
            return;
        }

        DistritoDialogFragment fragment = DistritoDialogFragment.newInstance(id_departamento, id_provincia);
        fragment.delegate = Ficha_Paciente.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.rlProfesion)
    public void onClicProfesion(View view) {
        ProfesionDialogFragment fragment = ProfesionDialogFragment.newInstance();
        fragment.delegate = Ficha_Paciente.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }
    @OnClick(R.id.efab)
    public void onClicEFAB(View view) {
     // clicFichaPaciente();
      clicFichaPaciente2();


    }

    private void clicFichaPaciente2() {
        // efab.setEnabled(false);

        if(id_tip_doc.isEmpty()) {
            showMessage("Seleccione tipo de documento.");
            return;
        }

        if(tietNumberDocument.getText().toString().isEmpty() && !id_tip_doc.equals("6")) {
            showMessage("Ingrese el número de documento.");
            return;
        }

        if(tietName.getText().toString().isEmpty()) {
            showMessage("Ingrese los nombres.");
            return;
        }

        if(tietPaternal.getText().toString().isEmpty()) {
            showMessage("Ingrese el apellido paterno.");
            return;
        }

        if(tietFechaNac.getText().toString().isEmpty()) {
            showMessage("Seleccione la fecha de nacimiento.");
            return;
        }

        if(rgSexo.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione el sexo.");
            return;
        }

        if(tietMobile.getText().toString().isEmpty()) {
            showMessage("Ingrese teléfono o celular.");
            tietMobile.requestFocus();
            return;
        }

        if(tietMobile.getText().toString().substring(0, 1).equals("9") && tietMobile.getText().toString().length() < 9) {
            showMessage("Completar el número de celular");
            tietMobile.requestFocus();
            return;
        } else if(tietMobile.getText().toString().length() < 7) {
            showMessage("Completar el número de teléfono");
            return;
        }

        if(tietMobileContact.getText().toString().isEmpty()) {
            showMessage("Ingrese teléfono o celular de contacto.");
            tietMobileContact.requestFocus();
            return;
        }

        if(tietMobileContact.getText().toString().substring(0, 1).equals("9") && tietMobileContact.getText().toString().length() < 9) {
            showMessage("Completar el número de celular");
            tietMobileContact.requestFocus();
            return;
        } else if(tietMobileContact.getText().toString().length() < 7) {
            showMessage("Completar el número de teléfono");
            return;
        }

        if(rgTipoResidencia.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione Tipo de Residencia.");
            return;
        }

        if(tietDireccion.getText().toString().isEmpty()) {
            showMessage("Ingrese la dirección.");
            return;
        }

        if(id_departamento.isEmpty()) {
            showMessage("Seleccione el departamento.");
            return;
        }

        if(id_provincia.isEmpty()) {
            showMessage("Seleccione la provincia.");
            return;
        }

        if(id_distrito.isEmpty()) {
            showMessage("Seleccione el distrito.");
            return;
        }

        if(rgPersonalSalud.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione si es personal de salud.");
            return;
        }

        if(rgTieneSintoma.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione si tiene sintomas.");
            return;
        }

        if(rgTieneSintoma.getCheckedRadioButtonId() == R.id.rbSiTieneSintoma) {

            if(tietFechaSintoma.getText().toString().isEmpty()) {
                showMessage("Seleccione fecha de inicio de sintomas.");
                return;
            }

            int count = 0;
            for(int i=0; i < sintomDataSets.size(); i++) {
                CheckBox checkBox = llContentSintomas.findViewWithTag(sintomDataSets.get(i).getId());
                if(checkBox.isChecked()) {
                    if(sintomDataSets.get(i).getId() == 10 && tietOtroSintoma.getText().toString().isEmpty()) {
                        showMessage("Especificar que otro síntoma presenta.");
                        tietOtroSintoma.requestFocus();
                        return;
                    }

                    count += 1;
                }
            }

            if(count == 0) {
                showMessage("Marque como mínimo un sintoma.");
                return;
            }
        }
/*
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatoPacienteDbHelper.TableC._id_interno, tietcoDInt.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.fecha, Utils.getDate("yyyy-MM-dd"));
        contentValues.put(DatoPacienteDbHelper.TableC.id_tipo_doc, id_tip_doc);

        contentValues.put(DatoPacienteDbHelper.TableC.tipo_doc, tvTipDoc.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.numero_doc, tietNumberDocument.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.nombres, tietName.getText().toString().toUpperCase());
        contentValues.put(DatoPacienteDbHelper.TableC.paterno, tietPaternal.getText().toString().toUpperCase());
        contentValues.put(DatoPacienteDbHelper.TableC.materno, tietMaternal.getText().toString().toUpperCase());
        contentValues.put(DatoPacienteDbHelper.TableC.fec_nac, fecha_nac);

        RadioButton rbSexo = rgSexo.findViewById(rgSexo.getCheckedRadioButtonId());

        contentValues.put(DatoPacienteDbHelper.TableC.id_sexo, rbSexo.getId() == R.id.rbFemenino ? Constants.Sexo.FEMENINO : Constants.Sexo.MASCULINO);
        contentValues.put(DatoPacienteDbHelper.TableC.sexo, rbSexo.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.celular, tietMobile.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.celular_contacto, tietMobileContact.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.correo, tietCorreoElectronico.getText().toString());

        RadioButton rbTipoResidencia = rgTipoResidencia.findViewById(rgTipoResidencia.getCheckedRadioButtonId());

        contentValues.put(DatoPacienteDbHelper.TableC.id_tipo_residencia, rbTipoResidencia.getId() == R.id.rbSiTipoResidencia ? Constants.TipoResidencia.INFORMACION_DOMICILIO : Constants.TipoResidencia.LUGAR_DONDE_SE_HOSPEDA);
        contentValues.put(DatoPacienteDbHelper.TableC.tipo_residencia, rbTipoResidencia.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.direccion, tietDireccion.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_departamento, id_departamento);
        contentValues.put(DatoPacienteDbHelper.TableC.departamento, tvDepartamento.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_provincia, id_provincia);
        contentValues.put(DatoPacienteDbHelper.TableC.provincia, tvProvincia.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_distrito, id_distrito);
        contentValues.put(DatoPacienteDbHelper.TableC.distrito, tvDistrito.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.latitud, "-11.2133899");//vDsUbicac.getUbicacLatitu()
     /   contentValues.put(DatoPacienteDbHelper.TableC.longitud, "-77.213300");//vDsUbicac.getUbicacLongit()
        contentValues.put(DatoPacienteDbHelper.TableC.es_pers_salud, rgPersonalSalud.getCheckedRadioButtonId() == R.id.rbSiPersonalSalud ? Constants.PersonalSalud.SI : Constants.PersonalSalud.NO);
        contentValues.put(DatoPacienteDbHelper.TableC.id_profesion, id_profesion);
        contentValues.put(DatoPacienteDbHelper.TableC.tiene_sintoma, rgTieneSintoma.getCheckedRadioButtonId() == R.id.rbSiTieneSintoma ? Constants.TieneSintoma.SI : Constants.TieneSintoma.NO);
        contentValues.put(DatoPacienteDbHelper.TableC.fecha_sintoma, fecha_sintoma);
        contentValues.put(DatoPacienteDbHelper.TableC.otro_sintoma, tietOtroSintoma.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_usuario, "112133");///vDsUsuari.getUsuariIdenti()
        contentValues.put(DatoPacienteDbHelper.TableC.id_dato_paciente, 0);
        contentValues.put(DatoPacienteDbHelper.TableC.codigo_pais_celular, codigo_pais_celular);
        contentValues.put(DatoPacienteDbHelper.TableC.codigo_pais_telefono, codigo_pais_telefono);

        int id_dato_paciente = (int) mPrinciDbHelper.savePrinci(DatoPacienteDbHelper.TableC.TableN, contentValues);
*//*
        for(int i=0; i< sintomDataSets.size(); i++) {
            CheckBox checkBox = llSintomas.findViewWithTag(sintomDataSets.get(i).getId());
            if(checkBox.isChecked()) {
                ContentValues cv1 = new ContentValues();
                cv1.put(DatoPacienteSintomaDbHelper.TableC._id_dato_paciente, id_dato_paciente);
                cv1.put(DatoPacienteSintomaDbHelper.TableC.id_sintoma, sintomDataSets.get(i).getId());
                cv1.put(DatoPacienteSintomaDbHelper.TableC.sintoma, sintomDataSets.get(i).getDescripcion());
                mPrinciDbHelper.savePrinci(DatoPacienteSintomaDbHelper.TableC.TableN, cv1);
            }
        }
*/
        Toasty.info(getApplicationContext(), "Registro exitoso id paciente"+id_dato_paciente, Toasty.LENGTH_SHORT).show();
        goFichas(F100Activity.class);
        /*
        Intent intent = new Intent();
        intent.putExtra("paciente", tietName.getText().toString() + " " + tietPaternal.getText().toString() + " " + tietMaternal.getText().toString());
        intent.putExtra("sexo", rbSexo.getId() == R.id.rbFemenino ? Constants.Sexo.FEMENINO : Constants.Sexo.MASCULINO);
        intent.putExtra("id_dato_paciente", id_dato_paciente);
        setResult(1, intent);
        onBackPressed();*/
    }
    private void goFichas(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        intent.putExtra("id_tipo_documento", id_tip_doc);
        intent.putExtra("documento", tietNumberDocument.getText().toString());
        intent.putExtra("paciente", paciente);
        intent.putExtra("tipodocumento", tvTipDoc.getText().toString());
        RadioButton rbSexo = rgSexo.findViewById(rgSexo.getCheckedRadioButtonId());
        intent.putExtra("sexo", rbSexo.getText().toString());
        intent.putExtra("id_dato_paciente", id_dato_paciente);
        startActivity(intent);
    }
    private void clicFichaPaciente() {
        // efab.setEnabled(false);

        if(id_tip_doc.isEmpty()) {
            showMessage("Seleccione tipo de documento.");
            return;
        }

        if(tietNumberDocument.getText().toString().isEmpty() && !id_tip_doc.equals("6")) {
            showMessage("Ingrese el número de documento.");
            return;
        }

        if(tietName.getText().toString().isEmpty()) {
            showMessage("Ingrese los nombres.");
            return;
        }

        if(tietPaternal.getText().toString().isEmpty()) {
            showMessage("Ingrese el apellido paterno.");
            return;
        }

        if(tietFechaNac.getText().toString().isEmpty()) {
            showMessage("Seleccione la fecha de nacimiento.");
            return;
        }

        if(rgSexo.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione el sexo.");
            return;
        }

        if(tietMobile.getText().toString().isEmpty()) {
            showMessage("Ingrese teléfono o celular.");
            tietMobile.requestFocus();
            return;
        }

        if(tietMobile.getText().toString().substring(0, 1).equals("9") && tietMobile.getText().toString().length() < 9) {
            showMessage("Completar el número de celular");
            tietMobile.requestFocus();
            return;
        } else if(tietMobile.getText().toString().length() < 7) {
            showMessage("Completar el número de teléfono");
            return;
        }

        if(tietMobileContact.getText().toString().isEmpty()) {
            showMessage("Ingrese teléfono o celular de contacto.");
            tietMobileContact.requestFocus();
            return;
        }

        if(tietMobileContact.getText().toString().substring(0, 1).equals("9") && tietMobileContact.getText().toString().length() < 9) {
            showMessage("Completar el número de celular");
            tietMobileContact.requestFocus();
            return;
        } else if(tietMobileContact.getText().toString().length() < 7) {
            showMessage("Completar el número de teléfono");
            return;
        }

        if(rgTipoResidencia.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione Tipo de Residencia.");
            return;
        }

        if(tietDireccion.getText().toString().isEmpty()) {
            showMessage("Ingrese la dirección.");
            return;
        }

        if(id_departamento.isEmpty()) {
            showMessage("Seleccione el departamento.");
            return;
        }

        if(id_provincia.isEmpty()) {
            showMessage("Seleccione la provincia.");
            return;
        }

        if(id_distrito.isEmpty()) {
            showMessage("Seleccione el distrito.");
            return;
        }

        if(rgPersonalSalud.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione si es personal de salud.");
            return;
        }

        if(rgTieneSintoma.getCheckedRadioButtonId() == -1) {
            showMessage("Seleccione si tiene sintomas.");
            return;
        }

        if(rgTieneSintoma.getCheckedRadioButtonId() == R.id.rbSiTieneSintoma) {

            if(tietFechaSintoma.getText().toString().isEmpty()) {
                showMessage("Seleccione fecha de inicio de sintomas.");
                return;
            }

            int count = 0;
            for(int i=0; i < sintomDataSets.size(); i++) {
                CheckBox checkBox = llContentSintomas.findViewWithTag(sintomDataSets.get(i).getId());
                if(checkBox.isChecked()) {
                    if(sintomDataSets.get(i).getId() == 10 && tietOtroSintoma.getText().toString().isEmpty()) {
                        showMessage("Especificar que otro síntoma presenta.");
                        tietOtroSintoma.requestFocus();
                        return;
                    }

                    count += 1;
                }
            }

            if(count == 0) {
                showMessage("Marque como mínimo un sintoma.");
                return;
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatoPacienteDbHelper.TableC._id_interno, tietcoDInt.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.fecha, Utils.getDate("yyyy-MM-dd"));
        contentValues.put(DatoPacienteDbHelper.TableC.id_tipo_doc, id_tip_doc);

        contentValues.put(DatoPacienteDbHelper.TableC.tipo_doc, tvTipDoc.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.numero_doc, tietNumberDocument.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.nombres, tietName.getText().toString().toUpperCase());
        contentValues.put(DatoPacienteDbHelper.TableC.paterno, tietPaternal.getText().toString().toUpperCase());
        contentValues.put(DatoPacienteDbHelper.TableC.materno, tietMaternal.getText().toString().toUpperCase());
        contentValues.put(DatoPacienteDbHelper.TableC.fec_nac, fecha_nac);

        RadioButton rbSexo = rgSexo.findViewById(rgSexo.getCheckedRadioButtonId());

        contentValues.put(DatoPacienteDbHelper.TableC.id_sexo, rbSexo.getId() == R.id.rbFemenino ? Constants.Sexo.FEMENINO : Constants.Sexo.MASCULINO);
        contentValues.put(DatoPacienteDbHelper.TableC.sexo, rbSexo.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.celular, tietMobile.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.celular_contacto, tietMobileContact.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.correo, tietCorreoElectronico.getText().toString());

        RadioButton rbTipoResidencia = rgTipoResidencia.findViewById(rgTipoResidencia.getCheckedRadioButtonId());

        contentValues.put(DatoPacienteDbHelper.TableC.id_tipo_residencia, rbTipoResidencia.getId() == R.id.rbSiTipoResidencia ? Constants.TipoResidencia.INFORMACION_DOMICILIO : Constants.TipoResidencia.LUGAR_DONDE_SE_HOSPEDA);
        contentValues.put(DatoPacienteDbHelper.TableC.tipo_residencia, rbTipoResidencia.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.direccion, tietDireccion.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_departamento, id_departamento);
        contentValues.put(DatoPacienteDbHelper.TableC.departamento, tvDepartamento.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_provincia, id_provincia);
        contentValues.put(DatoPacienteDbHelper.TableC.provincia, tvProvincia.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_distrito, id_distrito);
        contentValues.put(DatoPacienteDbHelper.TableC.distrito, tvDistrito.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.latitud, "-11.2133899"/*vDsUbicac.getUbicacLatitu()*/);
        contentValues.put(DatoPacienteDbHelper.TableC.longitud, "-77.213300"/*vDsUbicac.getUbicacLongit()*/);
        contentValues.put(DatoPacienteDbHelper.TableC.es_pers_salud, rgPersonalSalud.getCheckedRadioButtonId() == R.id.rbSiPersonalSalud ? Constants.PersonalSalud.SI : Constants.PersonalSalud.NO);
        contentValues.put(DatoPacienteDbHelper.TableC.id_profesion, id_profesion);
        contentValues.put(DatoPacienteDbHelper.TableC.tiene_sintoma, rgTieneSintoma.getCheckedRadioButtonId() == R.id.rbSiTieneSintoma ? Constants.TieneSintoma.SI : Constants.TieneSintoma.NO);
        contentValues.put(DatoPacienteDbHelper.TableC.fecha_sintoma, fecha_sintoma);
        contentValues.put(DatoPacienteDbHelper.TableC.otro_sintoma, tietOtroSintoma.getText().toString());
        contentValues.put(DatoPacienteDbHelper.TableC.id_usuario, "112133"/*vDsUsuari.getUsuariIdenti()*/);
        contentValues.put(DatoPacienteDbHelper.TableC.id_dato_paciente, 0);
        contentValues.put(DatoPacienteDbHelper.TableC.codigo_pais_celular, codigo_pais_celular);
        contentValues.put(DatoPacienteDbHelper.TableC.codigo_pais_telefono, codigo_pais_telefono);

        //int id_dato_paciente = (int) mPrinciDbHelper.savePrinci(DatoPacienteDbHelper.TableC.TableN, contentValues);

        for(int i=0; i< sintomDataSets.size(); i++) {
            CheckBox checkBox = llSintomas.findViewWithTag(sintomDataSets.get(i).getId());
            if(checkBox.isChecked()) {
                ContentValues cv1 = new ContentValues();
                cv1.put(DatoPacienteSintomaDbHelper.TableC._id_dato_paciente, id_dato_paciente);
                cv1.put(DatoPacienteSintomaDbHelper.TableC.id_sintoma, sintomDataSets.get(i).getId());
                cv1.put(DatoPacienteSintomaDbHelper.TableC.sintoma, sintomDataSets.get(i).getDescripcion());
                mPrinciDbHelper.savePrinci(DatoPacienteSintomaDbHelper.TableC.TableN, cv1);
            }
        }
        Toasty.info(getApplicationContext(), "Registro exitoso id paciente"+id_dato_paciente, Toasty.LENGTH_SHORT).show();
/*
        Intent intent = new Intent();
        intent.putExtra("paciente", tietName.getText().toString() + " " + tietPaternal.getText().toString() + " " + tietMaternal.getText().toString());
        intent.putExtra("sexo", rbSexo.getId() == R.id.rbFemenino ? Constants.Sexo.FEMENINO : Constants.Sexo.MASCULINO);
        intent.putExtra("id_dato_paciente", id_dato_paciente);
        setResult(1, intent);
        onBackPressed();*/
    }

    @OnClick(R.id.efa)
    public void onClicEFA(View view){
        //vCursorSintomas = mPrinciDbHelper.getAllPrinci(SintomDbHelper.SintomTableC.SintomTableN);
       // vCursorprueva = mPrinciDbHelper.getAllPrinci( DatoPacienteDbHelper.TableC.TableN);
        vCursorprueva = mPrinciDbHelper.getDISTINCTOne( DatoPacienteDbHelper.TableC.TableN,DatoPacienteDbHelper.TableC.id_tipo_doc,DatoPacienteDbHelper.TableC.tipo_doc);
        int count=0;
        String scv="";
        while(vCursorprueva.moveToNext()) {

            count++;
            scv =scv+" id"+vCursorprueva.getString(vCursorprueva.getColumnIndex(DatoPacienteDbHelper.TableC.id_tipo_doc))+" tipo "+vCursorprueva.getString(vCursorprueva.getColumnIndex(DatoPacienteDbHelper.TableC.tipo_doc))+" \n" ;
            /*SintomDataSet vDstDepart = new SintomDataSet();
            vDstDepart.setId(vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)));
            vDstDepart.setDescripcion(vCursorSintomas.getString(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomDescri)));
            sintomDataSets.add(vDstDepart);*/
        }
        Toasty.info(getApplicationContext(), ""+scv, Toasty.LENGTH_SHORT).show();
    }
    @Override
    public void setTipDoc(TipDocDataSet dataSet) {
        tvTipDoc.setText(dataSet.getTipDocDescri());
        tvTipDoc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_tip_doc = dataSet.getTipDocIdenti();

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
                tietNumberDocument.setEnabled(true);
                tietNumberDocument.setFilters(new InputFilter[] { new InputFilter.LengthFilter(0) });
                break;
        }
        Toasty.info(getApplicationContext(), " id documento "+dataSet.getTipDocIdenti(), Toasty.LENGTH_SHORT).show();
        Toasty.info(getApplicationContext(), " nombre documento "+dataSet.getTipDocDescri(), Toasty.LENGTH_SHORT).show();

        tietNumberDocument.requestFocus();
    }

    @Override
    public void setDepartamento(DepartDataSet dataSet) {
        tvDepartamento.setText(dataSet.getDepartNombre());
        tvDepartamento.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_departamento = dataSet.getDepartCodigo();
        Toasty.info(getApplicationContext(), " id departamento "+id_departamento, Toasty.LENGTH_SHORT).show();
        Toasty.info(getApplicationContext(), " nombre departamento "+dataSet.getDepartNombre(), Toasty.LENGTH_SHORT).show();

        tvProvincia.setText("Seleccionar");
        tvProvincia.setTextColor(oldColors);
        id_provincia = "";

        tvDistrito.setText("Seleccionar");
        tvDistrito.setTextColor(oldColors);
        id_distrito = "";
    }

    @Override
    public void setProvincia(ProvinDataSet dataSet) {
        tvProvincia.setText(dataSet.getProvinNombre());
        tvProvincia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_provincia = dataSet.getProvinCodigo();
        Toasty.info(getApplicationContext(), " id provincia "+id_provincia, Toasty.LENGTH_SHORT).show();
        Toasty.info(getApplicationContext(), " nombre provincia "+dataSet.getProvinNombre(), Toasty.LENGTH_SHORT).show();

        tvDistrito.setText("Seleccionar");
        tvDistrito.setTextColor(oldColors);
        id_distrito = "";
    }

    @Override
    public void setDistrito(DistriDataSet dataSet) {
        tvDistrito.setText(dataSet.getDistriNombre());
        tvDistrito.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_distrito = dataSet.getDistriCodigo();
        Toasty.info(getApplicationContext(), " id Distrito "+id_distrito, Toasty.LENGTH_SHORT).show();
        Toasty.info(getApplicationContext(), " nombre Distrito "+dataSet.getDistriNombre(), Toasty.LENGTH_SHORT).show();

    }

    @Override
    public void setDate(String date) {
        tietFechaNac.setText(date);
        fecha_nac = Utils.changeDateFormat(date, "dd/MM/yyyy", "yyyy-MM-dd");
    }

    @Override
    public void setProfesion(ProfesDataSet dataSet) {
        tvProfesion.setText(dataSet.getProfesDescri());
        tvProfesion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        id_profesion = dataSet.getProfesIdenti();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tietFechaNac:

                CalendarDialogFragment calendarDialogFragment = new CalendarDialogFragment();
                calendarDialogFragment.delegate = Ficha_Paciente.this;
                calendarDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
                calendarDialogFragment.show(getSupportFragmentManager(), "");

                break;
        }
    }

    private void showMessage(String message) {
        Toasty.info(getApplicationContext(), message, Toasty.LENGTH_SHORT).show();
        efab.setEnabled(true);
    }
/*
    private void getLocation() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        new GpsUtil(this).turnGPSOn(new GpsUtil.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                isGPS = isGPSEnable;
            }
        });


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        if (!isContinue) {
                            vDsUbicac.setUbicacLatitu(String.valueOf(wayLatitude));
                            vDsUbicac.setUbicacLongit(String.valueOf(wayLongitude));
                            tietLatitud.setText(vDsUbicac.getUbicacLatitu());
                            tietLongitud.setText(vDsUbicac.getUbicacLongit());
                        }
                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    AppConstant.LOCATION_REQUEST);
        } else {
            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        vDsUbicac.setUbicacLatitu(String.valueOf(wayLatitude));
                        vDsUbicac.setUbicacLongit(String.valueOf(wayLongitude));
                        tietLatitud.setText(vDsUbicac.getUbicacLatitu());
                        tietLongitud.setText(vDsUbicac.getUbicacLongit());
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                });
            }
        }
    }

     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isContinue) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    } else {
                        mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, location -> {
                            if (location != null) {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                vDsUbicac.setUbicacLatitu(String.valueOf(wayLatitude));
                                vDsUbicac.setUbicacLongit(String.valueOf(wayLongitude));
                                tietLatitud.setText(vDsUbicac.getUbicacLatitu());
                                tietLongitud.setText(vDsUbicac.getUbicacLongit());
                            } else {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstant.GPS_REQUEST) {
                isGPS = true;
            }
        }
    }

    @Override
    public void setCodigoPais(CodigoPaisDataSet dataSet, int origin) {
        switch (origin) {
            case R.id.tietCodigoPaisCel:
                tietCodigoPaisCel.setText(dataSet.getCod_telefono() + " " + dataSet.getSufijo());
                codigo_pais_celular = dataSet.getCod_telefono();
                break;
            case R.id.tietCodigoPaisTel:
                tietCodigoPaisTel.setText(dataSet.getCod_telefono() + " " + dataSet.getSufijo());
                codigo_pais_telefono = dataSet.getCod_telefono();
                break;
        }
    }
    private void GET_DATA() {

        Cursor res= mPrinciDbHelper.getAllPrinciOne(DatoPacienteDbHelper.TableC.TableN, DatoPacienteDbHelper.TableC._id_interno,getIntent().getStringExtra("cod_interno"));
        if(res.moveToNext()) {

            this.id_dato_paciente= ""+res.getInt(res.getColumnIndex(DatoPacienteDbHelper.TableC._id));
            this.id_tip_doc = ""+res.getInt(res.getColumnIndex(DatoPacienteDbHelper.TableC.id_tipo_doc));
            paciente =res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.nombres)) + " " + res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.paterno)) + " " + res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.materno));
            tvTipDoc.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.tipo_doc)));
            tvTipDoc.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.disabled));
            tvTipDoc.setEnabled(false);
            tvTipDoc.setFocusable(false);
            tvTipDoc.setLongClickable(false);

            //rlContentTipDoc.setEnabled(false);
            // rlContentTipDoc.setFocusable(false);
            //rlContentTipDoc.setLongClickable(false);
            tietcoDInt.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC._id_interno)));
            tietcoDInt.setEnabled(false);
            tietcoDInt.setFocusable(false);
            tietcoDInt.setLongClickable(false);
            tietNumberDocument.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.numero_doc)));
            //tietNumberDocument.setFocusable(false);
            //tietNumberDocument.setLongClickable(false);
            tietName.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.nombres)));
            tietName.setEnabled(false);
            tietName.setFocusable(false);
            tietName.setLongClickable(false);
            tietPaternal.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.paterno)));
            tietPaternal.setEnabled(false);
            tietPaternal.setFocusable(false);
            tietPaternal.setLongClickable(false);
            tietMaternal.setText( res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.materno)));
            tietMaternal.setEnabled(false);
            tietMaternal.setFocusable(false);
            tietMaternal.setLongClickable(false);
            tietDireccion.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.direccion)));
            tietDireccion.setEnabled(false);
            tietDireccion.setFocusable(false);
            tietDireccion.setLongClickable(false);
            if (null != res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.fec_nac))&& res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.fec_nac)).length() == 10) {
                tietFechaNac.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.fec_nac)));
                tietFechaNac.setEnabled(false);
                tietFechaNac.setFocusable(false);
                tietFechaNac.setLongClickable(false);
                fecha_nac = Utils.changeDateFormat(tietFechaNac.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd");
            }
            tietMobile.setText( res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.celular)));
            tietMobile.setEnabled(false);
            tietMobile.setFocusable(false);
            tietMobile.setLongClickable(false);
            tietMobileContact.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.celular_contacto)));
            tietMobileContact.setEnabled(false);
            tietMobileContact.setFocusable(false);
            tietMobileContact.setLongClickable(false);
            tietLatitud.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.latitud)));
            tietLatitud.setEnabled(false);
            tietLatitud.setFocusable(false);
            tietLatitud.setLongClickable(false);
            tietLongitud.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.longitud)));
            tietLongitud.setEnabled(false);
            tietLongitud.setFocusable(false);
            tietLongitud.setLongClickable(false);
            rbSiTipoResidencia.setChecked(true);
            rbSiTipoResidencia.setEnabled(false);
            rbSiTipoResidencia.setFocusable(false);
            rbNoTipoResidencia.setEnabled(false);
            rbNoTipoResidencia.setFocusable(false);
            rgTipoResidencia.setFocusable(false);
            rgTipoResidencia.setEnabled(false);
            tietCorreoElectronico.setText(res.getString(res.getColumnIndex(DatoPacienteDbHelper.TableC.correo)));
            tietCorreoElectronico.setEnabled(false);
            tietCorreoElectronico.setFocusable(false);
            tietCorreoElectronico.setLongClickable(false);
            rbNoPersonalSalud.setChecked(true);
            rbNoPersonalSalud.setEnabled(false);
            rbNoPersonalSalud.setFocusable(false);
            rbSiPersonalSalud.setEnabled(false);
            rbSiPersonalSalud.setFocusable(false);
       /* if(getIntent().getStringExtra("TipSex").equals("1")){
            rbMasculino.setChecked(true);
        } else {
            rbFemenino.setChecked(true);
        }*/
        }



    }
}
