package minsa.formulario.UI.Fragments.Dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

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
import minsa.formulario.DataSet.LaboratoryDataDataSet;
import minsa.formulario.DataSet.TipMueDataSet;
import minsa.formulario.R;
import minsa.formulario.Util.Utils;

public class AddLaboratoryDataF200DialogFragment extends DialogFragment implements TipoMuestraDialogFragment.TipMueDialogListener {

    @BindView(R.id.tilDate) TextInputLayout tilDate;
    @BindView(R.id.tietDate) TextInputEditText tietDate;
    @BindView(R.id.tilTime) TextInputLayout tilTime;
    @BindView(R.id.tietTime) TextInputEditText tietTime;

    @BindView(R.id.tvTypeSample) TextView tvTypeSample;

    public AddLaboratoryDataDialogListener delegate;

    private String date = "";
    private int id_type_sample = 0;

    public interface AddLaboratoryDataDialogListener {
        void addLaboratoryData(LaboratoryDataDataSet dataSet);
    }

    public static AddLaboratoryDataF200DialogFragment newInstance(ArrayList<LaboratoryDataDataSet> arrayList) {
        AddLaboratoryDataF200DialogFragment f = new AddLaboratoryDataF200DialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", arrayList);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_add_laboratory_data_dialog, null);

        ButterKnife.bind(this, view);

        tietDate.setText(Utils.getDate("dd/MM/yyyy"));
        date = Utils.changeDateFormat(tietDate.getText().toString(), "dd/MM/yyyy", "yyyy-MM-dd");

        tietTime.setText(Utils.getDate("HH':'mm"));

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dpDate, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        tietTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tietTime.setText(hourOfDay + ":" + (minute > 9 ? minute : "0" + minute));
                            }
                        }, Integer.parseInt(Utils.getDate("HH")), Integer.parseInt(Utils.getDate("mm")), false);
                timePickerDialog.show();
            }
        });

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

        return alertDialog.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog)getDialog();

        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick) {
                if(id_type_sample == 0) {
                    Toasty.info(getContext(), "Seleccione el tipo de muestra.", Toasty.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<LaboratoryDataDataSet> arrayList = (ArrayList<LaboratoryDataDataSet>) getArguments().getSerializable("data");
                if(arrayList.size() > 0) {
                    for(int i=0; i < arrayList.size(); i++) {
                        if(arrayList.get(i).getHour().equals(tietTime.getText().toString())
                                && arrayList.get(i).getDate().equals(date)
                                && arrayList.get(i).getId_type_sample() == id_type_sample) {
                            Toasty.info(getContext(), "Los datos de laboratorio que desea agregar ya existen.", Toasty.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                LaboratoryDataDataSet dataSet = new LaboratoryDataDataSet();
                dataSet.setDate(date);
                dataSet.setHour(tietTime.getText().toString());
                dataSet.setId_type_sample(id_type_sample);
                dataSet.setType_sample(tvTypeSample.getText().toString());

                delegate.addLaboratoryData(dataSet);
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.rlContentTypeSample})
    public void onClicTipMue(View view) {
        TipoMuestraDialogFragment fragment = TipoMuestraDialogFragment.newInstance();
        fragment.delegate = AddLaboratoryDataF200DialogFragment.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void setTipMue(TipMueDataSet dataSet) {
        tvTypeSample.setText(dataSet.getTip_mue());
        tvTypeSample.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

        id_type_sample = dataSet.getId_tip_mue();
    }

}