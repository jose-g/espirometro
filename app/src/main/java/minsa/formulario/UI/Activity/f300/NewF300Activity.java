package minsa.formulario.UI.Activity.f300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
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
import minsa.formulario.UI.Fragments.Dialog.CondicionEgresoDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.EvolucionDialogFragment;
import minsa.formulario.DataSet.CondicionEgresoDataSet;
import minsa.formulario.DataSet.EvolucionDataSet;
import minsa.formulario.DataSet.SigAlarmDataSet;
import minsa.formulario.DataSet.SintomDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.F300AlarmaDbHelper;
import minsa.formulario.DbHelper.F300DbHelper;
import minsa.formulario.DbHelper.F300SintomaDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.SigAlarmDbHelper;
import minsa.formulario.DbHelper.SintomDbHelper;
import minsa.formulario.R;
import minsa.formulario.Util.PreferencesManager;
import minsa.formulario.Util.Utils;

public class NewF300Activity extends AppCompatActivity implements
        EvolucionDialogFragment.EvolucionDialogListener,
        CondicionEgresoDialogFragment.CondicionEgresoDialogListener,
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tilFecha) TextInputLayout tilFecha;
    @BindView(R.id.tietFecha) TextInputEditText tietFecha;
    @BindView(R.id.rgTipoMonitoreo) RadioGroup rgTipoMonitoreo;
    @BindView(R.id.rbSiTipoMonitoreo) RadioButton rbSiTipoMonitoreo;
    @BindView(R.id.rbNoTipoMonitoreo) RadioButton rbNoTipoMonitoreo;
    @BindView(R.id.llContentRealizarMediciones) LinearLayout llContentRealizarMediciones;
    @BindView(R.id.rgRealizarMedicion) RadioGroup rgRealizarMedicion;
    @BindView(R.id.rbSiRealizarMedicion) RadioButton rbSiRealizarMedicion;
    @BindView(R.id.rbNoRealizarMedicion) RadioButton rbNoRealizarMedicion;
    @BindView(R.id.llFuncionesVitales) LinearLayout llFuncionesVitales;
    @BindView(R.id.tilPresionArterial) TextInputLayout tilPresionArterial;
    @BindView(R.id.tietPresionArterial) TextInputEditText tietPresionArterial;
    @BindView(R.id.tilPresionArterial2) TextInputLayout tilPresionArterial2;
    @BindView(R.id.tietPresionArterial2) TextInputEditText tietPresionArterial2;
    @BindView(R.id.tilPresionArterialMedia) TextInputLayout tilPresionArterialMedia;
    @BindView(R.id.tietPresionArterialMedia) TextInputEditText tietPresionArterialMedia;
    @BindView(R.id.tilFrecuenciaCardiaca) TextInputLayout tilFrecuenciaCardiaca;
    @BindView(R.id.tietFrecuenciaCardiaca) TextInputEditText tietFrecuenciaCardiaca;
    @BindView(R.id.tilFrecuenciaRespiratoria) TextInputLayout tilFrecuenciaRespiratoria;
    @BindView(R.id.tietFrecuenciaRespiratoria) TextInputEditText tietFrecuenciaRespiratoria;
    @BindView(R.id.tilTemperatura) TextInputLayout tilTemperatura;
    @BindView(R.id.tietTemperatura) TextInputEditText tietTemperatura;
    @BindView(R.id.llContentSintomas) LinearLayout llContentSintomas;
    @BindView(R.id.tilOtroSigno) TextInputLayout tilOtroSigno;
    @BindView(R.id.tietOtroSigno) TextInputEditText tietOtroSigno;
    @BindView(R.id.llContentSignosAlarma) LinearLayout llContentSignosAlarma;
    @BindView(R.id.tilOtroAlarma) TextInputLayout tilOtroAlarma;
    @BindView(R.id.tietOtroAlarma) TextInputEditText tietOtroAlarma;
    @BindView(R.id.rlContentEvolucion) RelativeLayout rlContentEvolucion;
    @BindView(R.id.tvEvolucion) TextView tvEvolucion;
    @BindView(R.id.rgEgreSegCli) RadioGroup rgEgreSegCli;
    @BindView(R.id.rbSiEgreSegCli) RadioButton rbSiEgreSegCli;
    @BindView(R.id.rbNoEgreSegCli) RadioButton rbNoEgreSegCli;
    @BindView(R.id.llContentCondiEgre) LinearLayout llContentCondiEgre;
    @BindView(R.id.rlContentCondiEgre) RelativeLayout rlContentCondiEgre;
    @BindView(R.id.tvCondiEgre) TextView tvCondiEgre;
    @BindView(R.id.tilObservacion) TextInputLayout tilObservacion;
    @BindView(R.id.tietObservacion) TextInputEditText tietObservacion;
    @BindView(R.id.efab) ExtendedFloatingActionButton efab;

    public NewF300Delegate delegate;

    public interface NewF300Delegate {
        void load300();
    }

    private ColorStateList oldColors;

    ArrayList<SintomDataSet> sintomDataSets = new ArrayList<>();
    ArrayList<SigAlarmDataSet> sigAlarmDataSets = new ArrayList<>();

    private PrinciDbHelper mPrinciDbHelper;

    private Cursor vCursorF300Alarma;
    private Cursor vCursorF300Sintoma;

    private Cursor vCursorSintomas;
    private Cursor vCursorSigAlarm;

    private String fecha = "";
    private int id_evolucion = 0;
    private String evolucion = "";
    private int id_condicion_egreso = 0;

    private String paciente = "";
    private String id_tipo_documento = "";
    private String tipodocumento = "";
    private String documento = "";
    private int id_dato_paciente = 0;

    private String presion_arterial_before = "";
    private String presion_arterial_2_before = "";

    private String frecuencia_cardiaca_before = "";
    private String frecuencia_respiratoria_before = "";

    private String temperature_before = "";

    private int _id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_f300);

        ButterKnife.bind(this);

        _id = getIntent().getIntExtra("id", 0);
        paciente = getIntent().getStringExtra("paciente");
        id_tipo_documento = getIntent().getStringExtra("id_tipo_documento");
        tipodocumento = getIntent().getStringExtra("tipodocumento");
        documento = getIntent().getStringExtra("documento");
        id_dato_paciente = getIntent().getIntExtra("id_dato_paciente", 0);

        if(_id == 0) {
            toolbar.setTitle("Nuevo Seguimiento Clínico");
        } else {
            toolbar.setTitle("Modificar Seguimiento Clínico");
        }

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oldColors =  tvEvolucion.getTextColors();

        mPrinciDbHelper = new PrinciDbHelper(getApplicationContext());

        tietFecha.setText(Utils.getDate("dd/MM/yyyy"));
        fecha = Utils.changeDateFormat(tietFecha.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd");

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dpDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String newFormat = sdf.format(myCalendar.getTime());
                fecha = Utils.changeDateFormat(newFormat, "dd/MM/yyyy", "yyyy-MM-dd");
                tietFecha.setText(newFormat);
            }
        };

        tietPresionArterial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                presion_arterial_before = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() && tietPresionArterial2.getText().toString().isEmpty()) {
                    tietPresionArterialMedia.setText("");
                    return;
                }

                int a = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                if(a > 0) {
                    if(a > 300) {
                        tietPresionArterial.setText(presion_arterial_before);
                        tietPresionArterial.setSelection(presion_arterial_before.length()-1);
                        return;
                    }

                    int a2 = tietPresionArterial2.getText().toString().isEmpty() ? 0 : Integer.parseInt(tietPresionArterial2.getText().toString());

                    double pam = (0.33 * (a-a2)) + a2;
                    tietPresionArterialMedia.setText(String.valueOf(pam));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tietPresionArterial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(rbNoRealizarMedicion.isChecked()) {
                        return;
                    }

                    if(tietPresionArterial.getText().toString().isEmpty()) {
                        return;
                    }

                    double d = tietPresionArterial.getText().toString().isEmpty() ? 0 : Double.parseDouble(tietPresionArterial.getText().toString());
                    if(d < 20 || d > 300) {
                        Utils.showDialogWithAction(NewF300Activity.this, "Ficha 300", "La presión anterial sistólica debe tener un valor mínimo de 20 y un máximo de 300.", "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View current = getCurrentFocus();
                                if (current != null) current.clearFocus();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tietPresionArterial.requestFocus();
                                    }
                                }, 50);
                            }
                        });
                    }
                }
            }
        });

        tietPresionArterial2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                presion_arterial_2_before = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty() && tietPresionArterial.getText().toString().isEmpty()) {
                    tietPresionArterialMedia.setText("");
                    return;
                }

                int a2 = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                if(a2 > 0) {
                    if(a2 > 300) {
                        tietPresionArterial2.setText(presion_arterial_2_before);
                        tietPresionArterial2.setSelection(presion_arterial_2_before.length()-1);
                        return;
                    }

                    int a = tietPresionArterial.getText().toString().isEmpty() ? 0 : Integer.parseInt(tietPresionArterial.getText().toString());

                    double pam = (0.33 * (a-a2)) + a2;
                    tietPresionArterialMedia.setText(String.valueOf(pam));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tietPresionArterial2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(rbNoRealizarMedicion.isChecked()) {
                        return;
                    }

                    if(tietPresionArterial2.getText().toString().isEmpty()) {
                        return;
                    }

                    double d = tietPresionArterial2.getText().toString().isEmpty() ? 0 : Double.parseDouble(tietPresionArterial2.getText().toString());
                    if(d < 20 || d > 300) {
                        Utils.showDialogWithAction(NewF300Activity.this, "Ficha 300", "La presión anterial diastólica debe tener un valor mínimo de 20 y un máximo de 300.", "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View current = getCurrentFocus();
                                if (current != null) current.clearFocus();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tietPresionArterial2.requestFocus();
                                    }
                                }, 50);
                            }
                        });
                    }
                }
            }
        });

        tietFrecuenciaCardiaca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                frecuencia_cardiaca_before = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                if(a > 350) {
                    tietFrecuenciaCardiaca.setText(frecuencia_cardiaca_before);
                    tietFrecuenciaCardiaca.setSelection(frecuencia_cardiaca_before.length()-1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tietFrecuenciaCardiaca.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(rbNoRealizarMedicion.isChecked()) {
                        return;
                    }

                    if(tietFrecuenciaCardiaca.getText().toString().isEmpty()) {
                        return;
                    }

                    double d = tietFrecuenciaCardiaca.getText().toString().isEmpty() ? 0 : Double.parseDouble(tietFrecuenciaCardiaca.getText().toString());
                    if(d < 20 || d > 200) {
                        Utils.showDialogWithAction(NewF300Activity.this, "Ficha 300", "La frecuencia cardiaca debe tener un valor mínimo de 20 y un máximo de 200.", "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View current = getCurrentFocus();
                                if (current != null) current.clearFocus();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tietFrecuenciaCardiaca.requestFocus();
                                    }
                                }, 50);
                            }
                        });
                    }
                }
            }
        });

        tietFrecuenciaRespiratoria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                frecuencia_respiratoria_before = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                if(a > 150) {
                    tietFrecuenciaRespiratoria.setText(frecuencia_respiratoria_before);
                    tietFrecuenciaRespiratoria.setSelection(frecuencia_respiratoria_before.length()-1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tietFrecuenciaRespiratoria.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(rbNoRealizarMedicion.isChecked()) {
                        return;
                    }

                    if(tietFrecuenciaRespiratoria.getText().toString().isEmpty()) {
                        return;
                    }

                    double d = tietFrecuenciaRespiratoria.getText().toString().isEmpty() ? 0 : Double.parseDouble(tietFrecuenciaRespiratoria.getText().toString());
                    if(d < 10 || d > 50) {
                        Utils.showDialogWithAction(NewF300Activity.this, "Ficha 300", "La frecuencia respiratoria debe tener un valor mínimo de 10 y un máximo de 50.", "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View current = getCurrentFocus();
                                if (current != null) current.clearFocus();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tietFrecuenciaRespiratoria.requestFocus();
                                    }
                                }, 50);
                            }
                        });
                    }
                }
            }
        });

        tietTemperatura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temperature_before = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty() && s.toString().substring(0, 1).equals(".")) {
                    tietTemperatura.setText("");
                    return;
                }

                double d = s.toString().isEmpty() ? 0 : Double.parseDouble(s.toString());
                if(d > 50) {
                    tietTemperatura.setText(temperature_before);
                    tietTemperatura.setSelection(temperature_before.length()-1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tietTemperatura.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(rbNoRealizarMedicion.isChecked()) {
                        return;
                    }

                    if(tietTemperatura.getText().toString().isEmpty()) {
                        return;
                    }

                    double d = tietTemperatura.getText().toString().isEmpty() ? 0 : Double.parseDouble(tietTemperatura.getText().toString());
                    if(d < 30 || d > 50) {
                        Utils.showDialogWithAction(NewF300Activity.this, "Ficha 300", "La temperatura debe tener un valor mínimo de 30 y un máximo de 50.", "Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                View current = getCurrentFocus();
                                if (current != null) current.clearFocus();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tietTemperatura.requestFocus();
                                    }
                                }, 50);
                            }
                        });
                    }
                }
            }
        });

        tietFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendarMin = Calendar.getInstance();
                calendarMin.add(Calendar.DAY_OF_MONTH, -15);

                final Calendar calendarToday = Calendar.getInstance();
                int year = calendarToday.get(Calendar.YEAR);
                int month = calendarToday.get(Calendar.MONTH);
                int day = calendarToday.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewF300Activity.this, dpDate, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        rgTipoMonitoreo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = rgTipoMonitoreo.findViewById(checkedId);
                if(radioButton.getId() == R.id.rbSiTipoMonitoreo && radioButton.isChecked()) {
                    llContentRealizarMediciones.setVisibility(View.VISIBLE);
                    llFuncionesVitales.setVisibility(View.GONE);
                    tietPresionArterial.setText("");
                    tietPresionArterial2.setText("");
                    tietPresionArterialMedia.setText("");
                    tietFrecuenciaCardiaca.setText("");
                    tietFrecuenciaRespiratoria.setText("");
                    tietTemperatura.setText("");

                    rgRealizarMedicion.clearCheck();
                } else if(radioButton.getId() == R.id.rbNoTipoMonitoreo && radioButton.isChecked()) {
                    llContentRealizarMediciones.setVisibility(View.VISIBLE);
                    llFuncionesVitales.setVisibility(View.GONE);
                    rgRealizarMedicion.clearCheck();
                }
            }
        });

        rgRealizarMedicion.setOnCheckedChangeListener(this);

        rgEgreSegCli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = rgEgreSegCli.findViewById(checkedId);
                if(radioButton.getId() == R.id.rbSiEgreSegCli && radioButton.isChecked()) {
                    llContentCondiEgre.setVisibility(View.VISIBLE);
                } else if(radioButton.getId() == R.id.rbNoEgreSegCli && radioButton.isChecked()) {
                    llContentCondiEgre.setVisibility(View.GONE);
                    tvCondiEgre.setText("Seleccionar");
                    tvCondiEgre.setTextColor(oldColors);
                    id_condicion_egreso = 0;
                }
            }
        });

        //////////////////////////

        vCursorSintomas = mPrinciDbHelper.getAllPrinci(SintomDbHelper.SintomTableC.SintomTableN);
        while(vCursorSintomas.moveToNext()) {
            if(vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)) == 11 ||
                    vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)) == 12 ||
                    vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)) == 13 ||
                    vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)) == 14 ||
                    vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)) == 15) {
                continue;
            }

            SintomDataSet vDstDepart = new SintomDataSet();
            vDstDepart.setId(vCursorSintomas.getInt(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)));
            vDstDepart.setDescripcion(vCursorSintomas.getString(vCursorSintomas.getColumnIndex(SintomDbHelper.SintomTableC.SintomDescri)));
            sintomDataSets.add(vDstDepart);
        }

        for(int i=0; i < sintomDataSets.size(); i++) {
            CheckBox checkBox = new CheckBox(NewF300Activity.this);
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
                                tilOtroSigno.setVisibility(View.VISIBLE);
                            } else {
                                tilOtroSigno.setVisibility(View.GONE);
                                tietOtroSigno.setText("");
                            }

                            break;
                        case 16:

                            if(isChecked) {
                                for(int i=0; i < llContentSintomas.getChildCount(); i++) {
                                    if(((int) llContentSintomas.getChildAt(i).getTag()) != 16) {
                                        CheckBox boxs = llContentSintomas.findViewWithTag(llContentSintomas.getChildAt(i).getTag());
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
            llContentSintomas.addView(checkBox, i);
        }

        //////////////////////////

        vCursorSigAlarm = mPrinciDbHelper.getAllPrinci(SigAlarmDbHelper.TableC.TableN);
        while(vCursorSigAlarm.moveToNext()) {
            SigAlarmDataSet vDstDepart = new SigAlarmDataSet();
            vDstDepart.setId(vCursorSigAlarm.getInt(vCursorSigAlarm.getColumnIndex(SigAlarmDbHelper.TableC.id)));
            vDstDepart.setDescripcion(vCursorSigAlarm.getString(vCursorSigAlarm.getColumnIndex(SigAlarmDbHelper.TableC.descripcion)));
            sigAlarmDataSets.add(vDstDepart);
        }

        for(int i=0; i < sigAlarmDataSets.size(); i++) {
            CheckBox checkBox = new CheckBox(NewF300Activity.this);
            checkBox.setTag(sigAlarmDataSets.get(i).getId());
            checkBox.setId(sigAlarmDataSets.get(i).getId());
            checkBox.setText(sigAlarmDataSets.get(i).getDescripcion());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int id_signo_alarma2 = (int) buttonView.getTag();

                    switch (id_signo_alarma2) {
                        case 10:
                            if(isChecked) {
                                for(int i=0; i < llContentSignosAlarma.getChildCount(); i++) {
                                    if(((int) llContentSignosAlarma.getChildAt(i).getTag()) != 10) {
                                        CheckBox boxs = llContentSignosAlarma.findViewWithTag(llContentSignosAlarma.getChildAt(i).getTag());
                                        boxs.setChecked(false);
                                    }
                                }
                            }

                            break;
                        case 12:

                            if(isChecked) {
                                uncheckedAlarmas();
                                tilOtroAlarma.setVisibility(View.VISIBLE);
                            } else {
                                tilOtroAlarma.setVisibility(View.GONE);
                                tietOtroAlarma.setText("");
                            }

                            break;
                        default:
                            if(isChecked) {
                                uncheckedAlarmas();
                            }
                    }
                }
            });
            llContentSignosAlarma.addView(checkBox, i);
        }

        if(_id > 0) {
            Cursor cursoF300 = mPrinciDbHelper.getAllPrinciOne(F300DbHelper.TableC.TableN, F300DbHelper.TableC._id, String.valueOf(_id));
            if(cursoF300.moveToFirst()) {

                tietFecha.setText(Utils.changeDateFormat(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.fecha)), "yyyy-MM-dd", "dd/MM/yyyy"));
                fecha = cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.fecha));

                if (cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.id_tipo_monitoreo)) == 1) {
                    rbSiTipoMonitoreo.setChecked(true);
                } else if (cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.id_tipo_monitoreo)) == 2) {
                    rbNoTipoMonitoreo.setChecked(true);
                }

                if (cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.realizar_medicion)) == 1) {
                    rbSiRealizarMedicion.setChecked(true);

                    tietPresionArterial.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.presion_arterial)));
                    tietPresionArterial2.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.presion_arterial_2)));
                    tietPresionArterialMedia.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.presion_arterial_media)));

                    tietFrecuenciaCardiaca.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.frecuencia_cardiaca)));
                    tietFrecuenciaRespiratoria.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.frecuencia_respiratoria)));
                    tietTemperatura.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.temperatura)));
                } else if (cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.realizar_medicion)) == 2) {
                    rbNoRealizarMedicion.setChecked(true);
                }

                id_evolucion = cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.id_evolucion));
                evolucion = cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.evolucion));
                tvEvolucion.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.evolucion)));
                tvEvolucion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

                if (cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.egreso_clinico)) == 1) {
                    rbSiEgreSegCli.setChecked(true);

                    id_condicion_egreso = cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.id_condicion_egreso));
                    tvCondiEgre.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.condicion_egreso)));
                    tvCondiEgre.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                } else if (cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC.egreso_clinico)) == 2) {
                    rbNoEgreSegCli.setChecked(true);
                }

                int id_300 = cursoF300.getInt(cursoF300.getColumnIndex(F300DbHelper.TableC._id));

                vCursorF300Alarma = mPrinciDbHelper.getAllPrinciOne(F300AlarmaDbHelper.TableC.TableN, F300AlarmaDbHelper.TableC._id_f300, String.valueOf(id_300));
                if(null != vCursorF300Alarma) {
                    while(vCursorF300Alarma.moveToNext()) {
                        int id_alarma = vCursorF300Alarma.getInt(vCursorF300Alarma.getColumnIndex(F300AlarmaDbHelper.TableC.id_alarma));
                        CheckBox checkBox = llContentSignosAlarma.findViewWithTag(id_alarma);
                        checkBox.setChecked(true);
                    }
                }
                tietOtroAlarma.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.otro_alarma)));

                vCursorF300Sintoma = mPrinciDbHelper.getAllPrinciOne(F300SintomaDbHelper.TableC.TableN, F300SintomaDbHelper.TableC._id_f300, String.valueOf(id_300));
                if(null != vCursorF300Sintoma) {
                    while(vCursorF300Sintoma.moveToNext()) {
                        int id_alarma = vCursorF300Sintoma.getInt(vCursorF300Sintoma.getColumnIndex(F300SintomaDbHelper.TableC.id_sintoma));
                        CheckBox checkBox = llContentSintomas.findViewWithTag(id_alarma);
                        checkBox.setChecked(true);
                    }
                }
                tietOtroSigno.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.otro_sintoma)));

                tietObservacion.setText(cursoF300.getString(cursoF300.getColumnIndex(F300DbHelper.TableC.observacion)));

            }
        }

    }

    private void uncheckedSintomas() {
        CheckBox box16 = llContentSintomas.findViewWithTag(16);
        if(box16.isChecked()) {
            box16.setChecked(false);
        }
    }

    private void uncheckedAlarmas() {
        CheckBox box10 = llContentSignosAlarma.findViewWithTag(10);
        if(box10.isChecked()) {
            box10.setChecked(false);
        }
    }

    @OnClick(R.id.efab)
    public void onClicEFAB(View view) {
        efab.setEnabled(false);

        if(fecha.isEmpty()) {
            showMessageValidation("Seleccione la fecha.");
            return;
        }

        if(rgTipoMonitoreo.getCheckedRadioButtonId() == -1) {
            showMessageValidation("Seleccione el tipo de monitoreo.");
            return;
        }

        int countSintoma = 0;

        for(int i=0; i < llContentSintomas.getChildCount(); i++) {
            CheckBox checkBox = llContentSintomas.findViewWithTag(llContentSintomas.getChildAt(i).getTag());
            if(checkBox.isChecked()) {
                countSintoma += 1;
            }
        }

        if(countSintoma == 0) {
            showMessageValidation("Seleccione como mínimo un sintoma.");
            return;
        }

        int countAlarma = 0;

        for(int i=0; i < llContentSignosAlarma.getChildCount(); i++) {
            CheckBox checkBox = llContentSignosAlarma.findViewWithTag(llContentSignosAlarma.getChildAt(i).getTag());
            if(checkBox.isChecked()) {
                countAlarma += 1;
            }
        }

        if(countAlarma == 0) {
            showMessageValidation("Seleccione como mínimo una alarma.");
            return;
        }

        if(id_evolucion == 0) {
            showMessageValidation("Seleccione la evolución.");
            tvEvolucion.requestFocus();
            return;
        }

        if(rgEgreSegCli.getCheckedRadioButtonId() == -1) {
            showMessageValidation("Seleccione el egreso de seguimiento clínico.");
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(F300DbHelper.TableC.fecha, fecha);

        RadioButton tipo_monitoreo = rgTipoMonitoreo.findViewById(rgTipoMonitoreo.getCheckedRadioButtonId());

        contentValues.put(F300DbHelper.TableC.id_tipo_monitoreo, tipo_monitoreo.getId() == R.id.rbSiTipoMonitoreo ? 1 : 2);
        contentValues.put(F300DbHelper.TableC.tipo_monitoreo, tipo_monitoreo.getText().toString());
        contentValues.put(F300DbHelper.TableC.realizar_medicion, rgRealizarMedicion.getCheckedRadioButtonId() == -1 ? 0 : (rgRealizarMedicion.getCheckedRadioButtonId() == R.id.rbSiRealizarMedicion ? 1 : 2));
        contentValues.put(F300DbHelper.TableC.presion_arterial, tietPresionArterial.getText().toString());
        contentValues.put(F300DbHelper.TableC.presion_arterial_2, tietPresionArterial2.getText().toString());
        contentValues.put(F300DbHelper.TableC.presion_arterial_media, tietPresionArterialMedia.getText().toString());
        contentValues.put(F300DbHelper.TableC.frecuencia_cardiaca, tietFrecuenciaCardiaca.getText().toString());
        contentValues.put(F300DbHelper.TableC.frecuencia_respiratoria, tietFrecuenciaRespiratoria.getText().toString());
        contentValues.put(F300DbHelper.TableC.temperatura, tietTemperatura.getText().toString());
        contentValues.put(F300DbHelper.TableC.otro_sintoma, tietOtroSigno.getText().toString());
        contentValues.put(F300DbHelper.TableC.otro_alarma, tietOtroAlarma.getText().toString());
        contentValues.put(F300DbHelper.TableC.id_evolucion, id_evolucion);
        contentValues.put(F300DbHelper.TableC.evolucion, evolucion);
        contentValues.put(F300DbHelper.TableC.egreso_clinico, rgEgreSegCli.getCheckedRadioButtonId() == -1 ? 0 : (rgEgreSegCli.getCheckedRadioButtonId() == R.id.rbSiEgreSegCli ? 1 : 2));
        contentValues.put(F300DbHelper.TableC.id_condicion_egreso, id_condicion_egreso);
        contentValues.put(F300DbHelper.TableC.condicion_egreso, id_condicion_egreso > 0 ? tvCondiEgre.getText().toString() : "");
        contentValues.put(F300DbHelper.TableC.observacion, tietObservacion.getText().toString());
        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();
        contentValues.put(F300DbHelper.TableC.id_tip_doc, id_tipo_documento);
        contentValues.put(F300DbHelper.TableC.tipo_doc, tipodocumento);
        contentValues.put(F300DbHelper.TableC.num_doc, documento);
        contentValues.put(F300DbHelper.TableC.paciente, paciente);
        contentValues.put(F300DbHelper.TableC.id_usuario, usuariDataSet.getUsuariIdenti());
        if(_id == 0) {
            contentValues.put(F300DbHelper.TableC.id_conglomerado, PreferencesManager.getIdConglomerado(getApplicationContext()));
            contentValues.put(F300DbHelper.TableC.conglomerado, PreferencesManager.getConglomerado(getApplicationContext()));
            contentValues.put(F300DbHelper.TableC.id_tipo_conglomerado, PreferencesManager.getIdTipoConglomerado(getApplicationContext()));
            contentValues.put(F300DbHelper.TableC.tipo_conglomerado, PreferencesManager.getTipoConglomerado(getApplicationContext()));
            contentValues.put(F300DbHelper.TableC.id_dato_paciente, id_dato_paciente);
        }
        contentValues.put(F300DbHelper.TableC.id_f300, 0);

        int f300 = 0;

        if(_id == 0) {
            f300 = (int) mPrinciDbHelper.savePrinci(F300DbHelper.TableC.TableN, contentValues);
        } else {
            f300 = _id;

            mPrinciDbHelper.updatePrinci(F300DbHelper.TableC.TableN, contentValues, F300DbHelper.TableC._id, String.valueOf(f300));
            mPrinciDbHelper.deleteAll(F300SintomaDbHelper.TableC.TableN);
            mPrinciDbHelper.deleteAll(F300AlarmaDbHelper.TableC.TableN);
        }

        for(int i=0; i< sintomDataSets.size(); i++) {
            CheckBox checkBox = llContentSintomas.findViewWithTag(sintomDataSets.get(i).getId());
            if(checkBox.isChecked()) {
                ContentValues cv1 = new ContentValues();
                cv1.put(F300SintomaDbHelper.TableC._id_f300, f300);
                cv1.put(F300SintomaDbHelper.TableC.id_sintoma, sintomDataSets.get(i).getId());
                cv1.put(F300SintomaDbHelper.TableC.sintoma, sintomDataSets.get(i).getDescripcion());
                mPrinciDbHelper.savePrinci(F300SintomaDbHelper.TableC.TableN, cv1);
            }
        }

        for(int i=0; i<sigAlarmDataSets.size(); i++) {
            CheckBox checkBox = llContentSignosAlarma.findViewWithTag(sigAlarmDataSets.get(i).getId());
            if(checkBox.isChecked()) {
                ContentValues cv2 = new ContentValues();
                cv2.put(F300AlarmaDbHelper.TableC._id_f300, f300);
                cv2.put(F300AlarmaDbHelper.TableC.id_alarma, sigAlarmDataSets.get(i).getId());
                cv2.put(F300AlarmaDbHelper.TableC.alarma, sigAlarmDataSets.get(i).getDescripcion());
                mPrinciDbHelper.savePrinci(F300AlarmaDbHelper.TableC.TableN, cv2);
            }
        }

        if(_id > 0) {
            Toasty.success(getApplicationContext(), "Ficha 300 actualizada satisfactoriamente", Toasty.LENGTH_SHORT).show();
            setResult(1);
        } else {
            Toasty.success(getApplicationContext(), "Ficha 300 registrada satisfactoriamente", Toasty.LENGTH_SHORT).show();
        }

        onBackPressed();
    }

    private void showMessageValidation(String message) {
        Toasty.info(getApplicationContext(), message, Toasty.LENGTH_SHORT).show();
        efab.setEnabled(true);
    }

    @OnClick({R.id.rlContentEvolucion})
    public void onClicEvolucion(View view) {
        EvolucionDialogFragment fragment = EvolucionDialogFragment.newInstance();
        fragment.delegate = NewF300Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlContentCondiEgre})
    public void onClicCondiEgre(View view) {
        CondicionEgresoDialogFragment fragment = CondicionEgresoDialogFragment.newInstance();
        fragment.delegate = NewF300Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void setEvolucion(EvolucionDataSet dataSet) {
        tvEvolucion.setText(dataSet.getDescripcion());
        tvEvolucion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_evolucion = dataSet.getId();
        evolucion = dataSet.getDescripcion();
    }

    @Override
    public void setCondicionEgreso(CondicionEgresoDataSet dataSet) {
        tvCondiEgre.setText(dataSet.getDescripcion());
        tvCondiEgre.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_condicion_egreso = dataSet.getId();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rgRealizarMedicion:

                RadioButton radioButton = rgRealizarMedicion.findViewById(checkedId);
                if(null == radioButton) return;

                if(radioButton.getId() == R.id.rbSiRealizarMedicion && radioButton.isChecked()) {
                    llFuncionesVitales.setVisibility(View.VISIBLE);
                } else if(radioButton.getId() == R.id.rbNoRealizarMedicion && radioButton.isChecked()) {
                    llFuncionesVitales.setVisibility(View.GONE);
                    tietPresionArterial.setText("");
                    tietPresionArterial2.setText("");
                    tietPresionArterialMedia.setText("");
                    tietFrecuenciaCardiaca.setText("");
                    tietFrecuenciaRespiratoria.setText("");
                    tietTemperatura.setText("");
                }

                break;
        }
    }

}