package minsa.formulario.UI.Activity.f100;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

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
import minsa.formulario.UI.Fragments.Dialog.EmbarazoTriDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.ProcedenciaDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.ResultadoPruebaRapidaDialogFragment;
import minsa.formulario.UI.Fragments.Dialog.SeveridadDialogFragment;
import minsa.formulario.DataSet.EmbarazoTriDataSet;
import minsa.formulario.DataSet.ProcedDataSet;
import minsa.formulario.DataSet.Riesgo2DataSet;
import minsa.formulario.DataSet.SeveriDataSet;
import minsa.formulario.DataSet.TipResDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.F100DbHelper;
import minsa.formulario.DbHelper.F100RiesgoDbHelper;
import minsa.formulario.DbHelper.F300DbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.Riesgo2DbHelper;
import minsa.formulario.R;
import minsa.formulario.Util.Constants;
import minsa.formulario.Util.PreferencesManager;
import minsa.formulario.Util.Utils;

public class F100Activity extends AppCompatActivity implements
        ProcedenciaDialogFragment.ProcedenciaDialogListener,
        ResultadoPruebaRapidaDialogFragment.ResultadoPruebaRapidaDialogListener,
        SeveridadDialogFragment.SeveridadDialogListener,
        EmbarazoTriDialogFragment.EmbarazoTriDialogListener {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.tvPaciente) TextView tvPaciente;
    @BindView(R.id.tvTipoDocumento) TextView tvTipoDocumento;
    @BindView(R.id.tvDocumento) TextView tvDocumento;

    @BindView(R.id.tilDate) TextInputLayout tilDate;
    @BindView(R.id.tietDate) TextInputEditText tietDate;
    @BindView(R.id.tilTime) TextInputLayout tilTime;
    @BindView(R.id.tietTime) TextInputEditText tietTime;
    @BindView(R.id.tilMarcaPrueba) TextInputLayout tilMarcaPrueba;
    @BindView(R.id.tietMarcaPrueba) TextInputEditText tietMarcaPrueba;
    @BindView(R.id.tilLotePrueba) TextInputLayout tilLotePrueba;
    @BindView(R.id.tietLotePrueba) TextInputEditText tietLotePrueba;
    @BindView(R.id.tilIndustriaPrueba) TextInputLayout tilIndustriaPrueba;
    @BindView(R.id.tietIndustriaPrueba) TextInputEditText tietIndustriaPrueba;
    @BindView(R.id.tilFechaPrueba) TextInputLayout tilFechaPrueba;
    @BindView(R.id.tietFechaPrueba) TextInputEditText tietFechaPrueba;
    @BindView(R.id.rlProcedencia) RelativeLayout rlProcedencia;
    @BindView(R.id.tvProcedencia) TextView tvProcedencia;
    @BindView(R.id.rlResultadoPrueba) RelativeLayout rlResultadoPrueba;
    @BindView(R.id.tvResultadoPrueba) TextView tvResultadoPrueba;
    @BindView(R.id.llContentSegundaPrueba) LinearLayout llContentSegundaPrueba;
    @BindView(R.id.rlResultadoPrueba2) RelativeLayout rlResultadoPrueba2;
    @BindView(R.id.tvResultadoPrueba2) TextView tvResultadoPrueba2;
    @BindView(R.id.llContentSeveridad) LinearLayout llContentSeveridad;
    @BindView(R.id.rlClinicaSeveridad) RelativeLayout rlClinicaSeveridad;
    @BindView(R.id.tvClinicaSeveridad) TextView tvClinicaSeveridad;
    @BindView(R.id.llContentRiesgo) LinearLayout llContentRiesgo;
    @BindView(R.id.llRiesgo) LinearLayout llRiesgo;

    @BindView(R.id.llContentTrimestre) LinearLayout llContentTrimestre;
    @BindView(R.id.rlContentEmbarazoTri) RelativeLayout rlContentEmbarazoTri;
    @BindView(R.id.tvEmbarazoTri) TextView tvEmbarazoTri;

    @BindView(R.id.tvMensaje) TextView tvMensaje;
    @BindView(R.id.tilOtroRiesgo) TextInputLayout tilOtroRiesgo;
    @BindView(R.id.tietOtroRiesgo) TextInputEditText tietOtroRiesgo;

    @BindView(R.id.llContentPCR) LinearLayout llContentPCR;
    @BindView(R.id.rgPCR) RadioGroup rgPCR;
    @BindView(R.id.rbSiPCR) RadioButton rbSiPCR;
    @BindView(R.id.rbNoPCR) RadioButton rbNoPCR;

    @BindView(R.id.tvMensaje2) TextView tvMensaje2;
    @BindView(R.id.tilObservacion) TextInputLayout tilObservacion;
    @BindView(R.id.tietObservacion) TextInputEditText tietObservacion;
    @BindView(R.id.efab) ExtendedFloatingActionButton efab;

    private ColorStateList oldColors;

    private PrinciDbHelper mPrinciDbHelper;

    ArrayList<Riesgo2DataSet> conditionsComorbidityList = new ArrayList<>();

    private String paciente = "";
    private String id_tipo_documento = "";
    private String tipodocumento = "";
    private String documento = "";
    private int sexo = 0;
    private int id_dato_paciente = 0;

    private String date = "";
    private String time = "";
    private String fecha_vencimiento = "";
    private int id_resultado_prueba = 0;
    private int id_resultado_prueba_2 = 0;
    private int id_severidad = 0;

    private int id_procedencia = 0;

    private int id_trimestre = 0;

    private int _id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f100);

        mPrinciDbHelper = new PrinciDbHelper(getApplicationContext());

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

        toolbar.setTitle("F100: Prueba Rápida");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        oldColors =  tvProcedencia.getTextColors();

        if(!PreferencesManager.getIdConglomerado(getApplicationContext()).isEmpty()) {
            id_procedencia = 16;
            tvProcedencia.setText("PERSONA QUE VIVE, TRABAJA O ASISTE A CONGLOMERADOS");
        } else {
            id_procedencia = 2;
            tvProcedencia.setText("LLAMÓ AL 113");
        }
        tvProcedencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        //////////////////////////////////////////////

        tietDate.setText(Utils.getDate("dd/MM/yyyy"));
        date = Utils.changeDateFormat(tietDate.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd");

        tietTime.setText(Utils.getDate("HH':'mm"));
        time = Utils.getDate("HH':'mm':'ss");

        final Calendar myCalendar = Calendar.getInstance();

        //////////////////////////////////
        /// FECHA
        /////////////////////////////////

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(F100Activity.this, dpDate, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        //////////////////////////////////
        /// FECHA VENCIMIENTO
        /////////////////////////////////

        final DatePickerDialog.OnDateSetListener dpDateFin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String newFormat = sdf.format(myCalendar.getTime());
                fecha_vencimiento = Utils.changeDateFormat(newFormat, "dd/MM/yyyy", "yyyy-MM-dd");
                tietFechaPrueba.setText(newFormat);
            }
        };

        tietFechaPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendarMin = Calendar.getInstance();
                calendarMin.add(Calendar.DAY_OF_MONTH, -15);

                final Calendar calendarToday = Calendar.getInstance();
                int year = calendarToday.get(Calendar.YEAR);
                int month = calendarToday.get(Calendar.MONTH);
                int day = calendarToday.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(F100Activity.this, dpDateFin, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendarToday.getTimeInMillis());
                //datePickerDialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        tietTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(F100Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tietTime.setText((hourOfDay > 9 ? hourOfDay : "0" + hourOfDay) + ":" + (minute > 9 ? minute : "0"+minute));
                                time = tietTime.getText() + ":00";
                            }
                        }, Integer.parseInt(Utils.getDate("HH")), Integer.parseInt(Utils.getDate("mm")), false);
                timePickerDialog.show();
            }
        });

        /////////////////////////////////////////////

        Cursor vCursorRiesgo2 = mPrinciDbHelper.getAllPrinci(Riesgo2DbHelper.RiesgoTableC.RiesgoTableN);
        while(vCursorRiesgo2.moveToNext()) {
            Riesgo2DataSet vDstDepart = new Riesgo2DataSet();
            vDstDepart.setRiesgoIdenti(vCursorRiesgo2.getInt(vCursorRiesgo2.getColumnIndex(Riesgo2DbHelper.RiesgoTableC.RiesgoIdenti)));
            vDstDepart.setRiesgoDescri(vCursorRiesgo2.getString(vCursorRiesgo2.getColumnIndex(Riesgo2DbHelper.RiesgoTableC.RiesgoDescri)));
            conditionsComorbidityList.add(vDstDepart);
        }

        for(int i=0; i < conditionsComorbidityList.size(); i++) {
            CheckBox checkBox = new CheckBox(F100Activity.this);
            checkBox.setTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
            checkBox.setId(conditionsComorbidityList.get(i).getRiesgoIdenti());
            checkBox.setText(conditionsComorbidityList.get(i).getRiesgoDescri());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int id_signo_alarma2 = (int) buttonView.getTag();

                    switch (id_signo_alarma2) {
                        case 1: // Mayor de 60 años

                            if(isChecked) {
                                uncheckedConditionsComorbidity();

                                CheckBox box = llRiesgo.findViewWithTag(11);
                                if(box.isChecked()) {
                                    box.setChecked(false);
                                }
                            }

                            break;
                        case 11: // Embarazo

                            if(isChecked) {
                                if(sexo == 1) {
                                    CheckBox box = llRiesgo.findViewWithTag(11);
                                    if(box.isChecked()) {
                                        box.setChecked(false);
                                    }
                                    return;
                                }

                                uncheckedConditionsComorbidity();

                                CheckBox box = llRiesgo.findViewWithTag(1);
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
                        case 13: // Ninguna

                            if(isChecked) {
                                for(int i=0; i < llRiesgo.getChildCount(); i++) {
                                    if(((int) llRiesgo.getChildAt(i).getTag()) != 13) {
                                        CheckBox boxs = llRiesgo.findViewWithTag(llRiesgo.getChildAt(i).getTag());
                                        boxs.setChecked(false);
                                    }
                                }
                            }
                            break;
                        case 14: // Otra condición

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

                    validarMensajes();
                }
            });
            llRiesgo.addView(checkBox, i);
        }

        rgPCR.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);

                if(null != radioButton && radioButton.getId() == R.id.rbNoPCR && radioButton.isChecked()) {
                    tvMensaje2.setVisibility(View.VISIBLE);
                } else {
                    tvMensaje2.setVisibility(View.GONE);
                }
            }
        });

        if(_id > 0) {
            Cursor cursoF100 = mPrinciDbHelper.getAllPrinciOne(F100DbHelper.TableC.TableN, F100DbHelper.TableC._id, String.valueOf(_id));
            if(cursoF100.moveToFirst()) {

                tietDate.setText(Utils.changeDateFormat(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.fecha)), "yyyy-MM-dd", "dd/MM/yyyy"));
                date = cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.fecha));

                tietMarcaPrueba.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.marca_prueba)));
                tietLotePrueba.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.lote_prueba)));
                tietIndustriaPrueba.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.industria_prueba)));
                tietFechaPrueba.setText(Utils.changeDateFormat(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.fecha_prueba)), "yyyy-MM-dd", "dd/MM/yyyy"));

                fecha_vencimiento = cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.fecha_prueba));

                tvProcedencia.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.procedencia)));
                tvProcedencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

                id_procedencia = cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.id_procedencia));

                tvResultadoPrueba.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.resultado_prueba_rapida)));
                tvResultadoPrueba.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                id_resultado_prueba = cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.id_resultado_prueba_rapida));

                if(id_resultado_prueba == 3) {
                    tvResultadoPrueba2.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.resultado_prueba_rapida_2)));
                    tvResultadoPrueba2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                    id_resultado_prueba_2 = cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.id_resultado_prueba_rapida_2));
                }

                if((id_resultado_prueba == 3 && (id_resultado_prueba_2 == 2 || id_resultado_prueba_2 > 3)) || cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.id_severidad)) > 0) {
                    tvClinicaSeveridad.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.severidad)));
                    tvClinicaSeveridad.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                    id_severidad = cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.id_severidad));
                }

                int id_100 = cursoF100.getInt(cursoF100.getColumnIndex(F300DbHelper.TableC._id));

                Cursor vCursorF100Riesgo = mPrinciDbHelper.getAllPrinciOne(F100RiesgoDbHelper.TableC.TableN, F100RiesgoDbHelper.TableC._id_f100, String.valueOf(id_100));
                if(null != vCursorF100Riesgo) {
                    while(vCursorF100Riesgo.moveToNext()) {
                        int id_riesgo = vCursorF100Riesgo.getInt(vCursorF100Riesgo.getColumnIndex(F100RiesgoDbHelper.TableC.id_riesgo));
                        CheckBox checkBox = llRiesgo.findViewWithTag(id_riesgo);
                        checkBox.setChecked(true);
                    }
                }

                CheckBox cb11 = llRiesgo.findViewWithTag(11);
                if(cb11.isChecked()) {
                    rlContentEmbarazoTri.setVisibility(View.VISIBLE);
                    tvEmbarazoTri.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.trimestre)));
                    tvEmbarazoTri.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                    id_trimestre = cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.id_trimestre));
                }

                tietOtroRiesgo.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.otro_riesgo)));
                tietObservacion.setText(cursoF100.getString(cursoF100.getColumnIndex(F100DbHelper.TableC.observacion)));

                validarMensajes();

                if(cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.pcr)) == Constants.PCR.SI) {
                    rbSiPCR.setChecked(true);
                } else if (cursoF100.getInt(cursoF100.getColumnIndex(F100DbHelper.TableC.pcr)) == Constants.PCR.NO) {
                    rbNoPCR.setChecked(true);
                }

            }
        }

    }

    private void validarMensajes() {
        CheckBox cbNinguno = llRiesgo.findViewWithTag(13);
        int count = 0;

        for(int i=0; i < llRiesgo.getChildCount(); i++) {
            CheckBox checkBox = llRiesgo.findViewWithTag(llRiesgo.getChildAt(i).getTag());
            if(checkBox.isChecked()) {
                count++;
            }
        }

        if(id_resultado_prueba == 2 && (id_severidad == 1 || id_severidad == 4) && cbNinguno.isChecked()) {
            tvMensaje.setText("Requiere aislamiento domiciliario. Repetir la prueba rápida en 7 días. Se realizará seguimiento remoto. No es necesario continuar con F200.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            rgPCR.clearCheck();
            llContentPCR.setVisibility(View.GONE);
            tvMensaje2.setVisibility(View.GONE);
        } else if(id_resultado_prueba == 2 && (id_severidad == 1 || id_severidad == 4) && count > 0) {
            tvMensaje.setText("Requiere aislamiento domiciliario. Aplicar prueba PCR y continuar con F200. Se realizará seguimiento clínico remoto (cada 24 hs) y seguimiento clínico presencial (cada 7 días)");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            llContentPCR.setVisibility(View.VISIBLE);
        } else if(id_resultado_prueba == 2 && id_severidad == 2 && count > 0) {
            tvMensaje.setText("Gestionar traslado a hospital. Aplicar prueba PCR y continuar con F200.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            llContentPCR.setVisibility(View.VISIBLE);
        } else if(id_resultado_prueba == 2 && id_severidad == 3 && (cbNinguno.isChecked() || count > 0)) {
            tvMensaje.setText("Gestionar traslado a hospital. Aplicar prueba PCR y continuar con F200");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            llContentPCR.setVisibility(View.VISIBLE);
        } else if (id_resultado_prueba >= 4 && (id_severidad == 1 || id_severidad == 4) && cbNinguno.isChecked()) {
            tvMensaje.setText("Aislamiento domiciliario. Se realizará seguimiento clínico remoto cada 24 horas y presencial cada 7 días. Continuar con F200  y F300. No es necesario realizar prueba PCR, pero sí se debe tomar prueba rápida a todos los miembros de la casa y aplicar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else if (id_resultado_prueba >= 4 && (id_severidad == 1 || id_severidad == 4) && count > 0) {
            tvMensaje.setText("Aislamiento domiciliario. Se realizará seguimiento clínico remoto cada 24 horas y presencial cada 72 horas. Continuar con F200  y F300. No es necesario realizar prueba PCR, pero sí se debe aplicar prueba rápida a todos los miembros del hogar a y realizar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else if (id_resultado_prueba >= 4 && id_severidad == 2 && count > 0) {
            tvMensaje.setText("Gestionar traslado a hospital. Continuar con F200  y F300. No es necesario aplicar prueba PCR, pero sí se debe tomar prueba rápida a todos los miembros del hogar y aplicar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else if (id_resultado_prueba >= 4 && id_severidad == 3 && count > 0) {
            tvMensaje.setText("Gestionar traslado a hospital. Continuar con F200  y F300. No es necesario aplicar prueba PCR, pero sí se debe tomar prueba rápida a todos los miembros del hogar y aplicar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 == 3) {
            llContentSegundaPrueba.setVisibility(View.VISIBLE);

            tvMensaje.setText("1. Se consignará como prueba no válida: EVALUAR EL CASO Y DE NO TENER FACTORES DE RIESGO REPETIR VISITA EN 7 DÍAS.\n" +
                    "2. Paciente en estado grave o moderado. ACCIÓN: APLICAR PCR Y CONTUNUAR CON F200.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            llContentSeveridad.setVisibility(View.GONE);
            llContentRiesgo.setVisibility(View.GONE);

            llContentPCR.setVisibility(View.VISIBLE);
        } else if (id_resultado_prueba == 3 && (id_resultado_prueba_2 == 2  || id_resultado_prueba_2 >= 4) && id_severidad == 0) {
            llContentSeveridad.setVisibility(View.VISIBLE);
            llContentRiesgo.setVisibility(View.VISIBLE);

            tvMensaje.setVisibility(View.GONE);

            rgPCR.clearCheck();
            llContentPCR.setVisibility(View.GONE);
            tvMensaje2.setVisibility(View.GONE);
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 == 2 && (id_severidad == 1 || id_severidad == 4) && cbNinguno.isChecked()) {
            tvMensaje.setText("Requiere aislamiento domiciliario. Repetir la prueba rápida en 7 días. Se realizará seguimiento remoto. No es necesario continuar con F200.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            rgPCR.clearCheck();
            llContentPCR.setVisibility(View.GONE);
            tvMensaje2.setVisibility(View.GONE);
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 == 2 && (id_severidad == 1 || id_severidad == 4) && count > 0) {
            tvMensaje.setText("Requiere aislamiento domiciliario. Aplicar prueba PCR y continuar con F200. Se realizará seguimiento clínico remoto (cada 24 hs) y seguimiento clínico presencial (cada 7 días)");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            llContentPCR.setVisibility(View.VISIBLE);
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 == 2 && id_severidad == 2 && count > 0) {
            tvMensaje.setText("Gestionar traslado a hospital. Aplicar prueba PCR y continuar con F200.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            llContentSegundaPrueba.setVisibility(View.VISIBLE);
            llContentPCR.setVisibility(View.VISIBLE);
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 == 2 && id_severidad == 3 && (cbNinguno.isChecked() || count > 0)) {
            tvMensaje.setText("Gestionar traslado a hospital. Aplicar prueba PCR y continuar con F200");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();

            llContentPCR.setVisibility(View.VISIBLE);
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 >= 4 && (id_severidad == 1 || id_severidad == 4) && cbNinguno.isChecked()) {
            tvMensaje.setText("Aislamiento domiciliario. Se realizará seguimiento clínico remoto cada 24 horas y presencial cada 7 días. Continuar con F200  y F300. No es necesario realizar prueba PCR, pero sí se debe tomar prueba rápida a todos los miembros de la casa y aplicar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 >= 4 && (id_severidad == 1 || id_severidad == 4) && count > 0) {
            tvMensaje.setText("Aislamiento domiciliario. Se realizará seguimiento clínico remoto cada 24 horas y presencial cada 72 horas. Continuar con F200  y F300. No es necesario realizar prueba PCR, pero sí se debe aplicar prueba rápida a todos los miembros del hogar a y realizar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 >= 4 && id_severidad == 2 && count > 0) {
            tvMensaje.setText("Gestionar traslado a hospital. Continuar con F200  y F300. No es necesario aplicar prueba PCR, pero sí se debe tomar prueba rápida a todos los miembros del hogar y aplicar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else if (id_resultado_prueba == 3 && id_resultado_prueba_2 >= 4 && id_severidad == 3 && count > 0) {
            tvMensaje.setText("Gestionar traslado a hospital. Continuar con F200  y F300. No es necesario aplicar prueba PCR, pero sí se debe tomar prueba rápida a todos los miembros del hogar y aplicar F100 para cada uno.");
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.requestFocus();
        } else {
            tvMensaje.setText("");
            tvMensaje.setVisibility(View.GONE);
            llContentPCR.setVisibility(View.GONE);
        }
    }

    private void uncheckedConditionsComorbidity() {
        CheckBox box13 = llRiesgo.findViewWithTag(13);
        if(box13.isChecked()) {
            box13.setChecked(false);
        }
    }

    @OnClick({R.id.rlProcedencia})
    public void onClicProcedencia(View view) {
        ProcedenciaDialogFragment fragment = ProcedenciaDialogFragment.newInstance();
        fragment.delegate = F100Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlResultadoPrueba})
    public void onClicResultadoPrueba(View view) {
        ResultadoPruebaRapidaDialogFragment fragment = ResultadoPruebaRapidaDialogFragment.newInstance(1);
        fragment.delegate = F100Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlResultadoPrueba2})
    public void onClicResultadoPrueba2(View view) {
        ResultadoPruebaRapidaDialogFragment fragment = ResultadoPruebaRapidaDialogFragment.newInstance(2);
        fragment.delegate = F100Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlClinicaSeveridad})
    public void onClicClinicaSeveridad(View view) {
        SeveridadDialogFragment fragment = SeveridadDialogFragment.newInstance();
        fragment.delegate = F100Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick({R.id.rlContentEmbarazoTri})
    public void onClicEmbarazoTri(View view) {
        EmbarazoTriDialogFragment fragment = EmbarazoTriDialogFragment.newInstance();
        fragment.delegate = F100Activity.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.efab)
    public void onClicEFAB(View view) {
        efab.setEnabled(false);

        if(tietTime.getText().toString().isEmpty()) {
            showMessageValidation("Seleccione la hora");
            return;
        }

        if(id_procedencia == 0) {
            showMessageValidation("Seleccione procedencia de la solicitud de diagnóstico.");
            return;
        }

        if(id_resultado_prueba == 0) {
            showMessageValidation("Seleccione resultado de la prueba rápida.");
            return;
        }

        if(id_resultado_prueba == 3) {
            if(id_resultado_prueba_2 == 0) {
                showMessageValidation("Seleccione resultado de la segunda prueba rápida.");
                return;
            }
        }

        if(id_resultado_prueba_2 != 3) {
            int count = 0;

            for(int i=0; i < conditionsComorbidityList.size(); i++) {
                CheckBox checkBox = llRiesgo.findViewWithTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
                if(checkBox.isChecked()) {
                    if(conditionsComorbidityList.get(i).getRiesgoIdenti() == 11 && id_trimestre == 0) {
                        showMessageValidation("Seleccione trimestre del embarazo.");
                        return;
                    }

                    if(conditionsComorbidityList.get(i).getRiesgoIdenti() == 14 && tietOtroRiesgo.getText().toString().isEmpty()) {
                        showMessageValidation("Especificar que otra condición de riesgo presenta.");
                        tietOtroRiesgo.requestFocus();
                        return;
                    }

                    count += 1;
                }
            }

            if(count == 0) {
                showMessageValidation("Seleccione si el paciente cumple con alguna condición de riesgo.");
                return;
            }
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(F100DbHelper.TableC.fecha, date);
        contentValues.put(F100DbHelper.TableC.hora, time);
        contentValues.put(F100DbHelper.TableC.marca_prueba, tietMarcaPrueba.getText().toString());
        contentValues.put(F100DbHelper.TableC.lote_prueba, tietLotePrueba.getText().toString());
        contentValues.put(F100DbHelper.TableC.industria_prueba, tietIndustriaPrueba.getText().toString());
        contentValues.put(F100DbHelper.TableC.fecha_prueba, fecha_vencimiento);
        contentValues.put(F100DbHelper.TableC.id_procedencia, id_procedencia);
        contentValues.put(F100DbHelper.TableC.procedencia, id_procedencia > 0 ? tvProcedencia.getText().toString() : "");
        contentValues.put(F100DbHelper.TableC.id_resultado_prueba_rapida, id_resultado_prueba);
        contentValues.put(F100DbHelper.TableC.resultado_prueba_rapida, id_resultado_prueba > 0 ? tvResultadoPrueba.getText().toString() : "");
        contentValues.put(F100DbHelper.TableC.id_resultado_prueba_rapida_2, id_resultado_prueba_2);
        contentValues.put(F100DbHelper.TableC.resultado_prueba_rapida_2, id_resultado_prueba_2 > 0 ? tvResultadoPrueba2.getText().toString() : "");
        contentValues.put(F100DbHelper.TableC.id_severidad, id_severidad);
        contentValues.put(F100DbHelper.TableC.severidad, id_severidad > 0 ? tvClinicaSeveridad.getText().toString() : "");
        contentValues.put(F100DbHelper.TableC.id_trimestre, id_trimestre);
        contentValues.put(F100DbHelper.TableC.trimestre, id_trimestre > 0 ? tvEmbarazoTri.getText().toString() : "");
        contentValues.put(F100DbHelper.TableC.otro_riesgo, tietOtroRiesgo.getText().toString());
        contentValues.put(F100DbHelper.TableC.pcr, rgPCR.getCheckedRadioButtonId() == -1 ? 0 : rgPCR.getCheckedRadioButtonId() == R.id.rbSiPCR ? 1 : 2);
        contentValues.put(F100DbHelper.TableC.observacion, tietObservacion.getText().toString());
        contentValues.put(F100DbHelper.TableC.id_tip_doc, id_tipo_documento);
        contentValues.put(F100DbHelper.TableC.tipo_doc, tipodocumento);
        contentValues.put(F100DbHelper.TableC.num_doc, documento);
        contentValues.put(F100DbHelper.TableC.paciente, paciente);
        UsuariDataSet usuariDataSet = UsuariDataSet.getInstance();
        contentValues.put(F100DbHelper.TableC.id_usuario, usuariDataSet.getUsuariIdenti());

        if(_id == 0) {
            contentValues.put(F100DbHelper.TableC.id_conglomerado, PreferencesManager.getIdConglomerado(getApplicationContext()));
            contentValues.put(F100DbHelper.TableC.conglomerado, PreferencesManager.getConglomerado(getApplicationContext()));
            contentValues.put(F100DbHelper.TableC.id_tipo_conglomerado, PreferencesManager.getIdTipoConglomerado(getApplicationContext()));
            contentValues.put(F100DbHelper.TableC.tipo_conglomerado, PreferencesManager.getTipoConglomerado(getApplicationContext()));
            contentValues.put(F100DbHelper.TableC.id_dato_paciente, id_dato_paciente);
        }

        contentValues.put(F100DbHelper.TableC.id_f100, 0);

        int f100 = 0;

        if(_id == 0) {
            f100 = (int)  mPrinciDbHelper.savePrinci(F100DbHelper.TableC.TableN, contentValues);
        } else {
            f100 = _id;

            mPrinciDbHelper.updatePrinci(F100DbHelper.TableC.TableN, contentValues, F100DbHelper.TableC._id, String.valueOf(f100));
            mPrinciDbHelper.deleteAll(F100RiesgoDbHelper.TableC.TableN);
        }

        for(int i=0; i < conditionsComorbidityList.size(); i++) {
            CheckBox checkBox = llRiesgo.findViewWithTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
            if(checkBox.isChecked()) {
                ContentValues cv1 = new ContentValues();
                cv1.put(F100RiesgoDbHelper.TableC._id_f100, (int) f100);
                cv1.put(F100RiesgoDbHelper.TableC.id_riesgo, conditionsComorbidityList.get(i).getRiesgoIdenti());
                cv1.put(F100RiesgoDbHelper.TableC.riesgo, conditionsComorbidityList.get(i).getRiesgoDescri());
                mPrinciDbHelper.savePrinci(F100RiesgoDbHelper.TableC.TableN, cv1);
            }
        }

        if(_id > 0) {
            Toasty.success(getApplicationContext(), "Ficha 100 actualizada satisfactoriamente", Toasty.LENGTH_SHORT).show();
            setResult(1);
        } else {
            Toasty.success(getApplicationContext(), "Ficha 100 registrada satisfactoriamente", Toasty.LENGTH_SHORT).show();
        }

        onBackPressed();
    }

    private void showMessageValidation(String message) {
        Toasty.info(getApplicationContext(), message, Toasty.LENGTH_SHORT).show();
        efab.setEnabled(true);
    }

    @Override
    public void setProcedencia(ProcedDataSet dataSet) {
        tvProcedencia.setText(dataSet.getProcedDescri());
        tvProcedencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_procedencia = dataSet.getProcedIdenti();
    }

    @Override
    public void setResultadoPrueba(TipResDataSet dataSet, int origin) {
        if(origin == 1) {
            tvResultadoPrueba.setText(dataSet.getTipResDescri());
            tvResultadoPrueba.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            id_resultado_prueba = dataSet.getTipResIdenti();

            tvClinicaSeveridad.setText("Seleccione");
            tvClinicaSeveridad.setTextColor(oldColors);
            id_severidad = 0;

            if(dataSet.getTipResIdenti() == 3) {
                llContentSegundaPrueba.setVisibility(View.VISIBLE);
            } else {
                llContentSegundaPrueba.setVisibility(View.GONE);
                llContentSeveridad.setVisibility(View.VISIBLE);
                llContentRiesgo.setVisibility(View.VISIBLE);
            }

            id_resultado_prueba_2 = 0;

            tvResultadoPrueba2.setText("Seleccione");
            tvResultadoPrueba2.setTextColor(oldColors);

            for(int i=0; i < llRiesgo.getChildCount(); i++) {
                CheckBox checkBox = llRiesgo.findViewWithTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
                checkBox.setChecked(false);
            }

            tietOtroRiesgo.setText("");
            id_trimestre = 0;
            tvEmbarazoTri.setText("Seleccionar");
            tvEmbarazoTri.setTextColor(oldColors);
            rgPCR.clearCheck();
        }

        if(origin == 2) {
            tvResultadoPrueba2.setText(dataSet.getTipResDescri());
            tvResultadoPrueba2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            id_resultado_prueba_2 = dataSet.getTipResIdenti();

            tvClinicaSeveridad.setText("Seleccionar");
            tvClinicaSeveridad.setTextColor(oldColors);
            id_severidad = 0;

            for(int i=0; i < llRiesgo.getChildCount(); i++) {
                CheckBox checkBox = llRiesgo.findViewWithTag(conditionsComorbidityList.get(i).getRiesgoIdenti());
                checkBox.setChecked(false);
            }

            tietOtroRiesgo.setText("");
            id_trimestre = 0;
            tvEmbarazoTri.setText("Seleccionar");
            tvEmbarazoTri.setTextColor(oldColors);
            rgPCR.clearCheck();
        }

        validarMensajes();
    }

    @Override
    public void setSeveridad(SeveriDataSet dataSet) {
        tvClinicaSeveridad.setText(dataSet.getDescripcion());
        tvClinicaSeveridad.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_severidad = dataSet.getId();

        validarMensajes();
    }

    @Override
    public void setEmbarazoTri(EmbarazoTriDataSet dataSet) {
        tvEmbarazoTri.setText(dataSet.getDescripcion());
        tvEmbarazoTri.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));

        id_trimestre = dataSet.getId();
    }

}