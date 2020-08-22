package minsa.formulario.UI.Fragments.Dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.R;

public class CalendarDialogFragment extends DialogFragment {

    @BindView(R.id.datePicker) DatePicker datePicker;

    Calendar myCalendar;
    public CalendarDialogListener delegate;

    private static ProgressDialog progressDialog;

    public interface CalendarDialogListener {
        void setDate(String date);
    }

    public static CalendarDialogFragment newInstance(int cantidad) {
        CalendarDialogFragment f = new CalendarDialogFragment();
        Bundle args = new Bundle();
        args.putInt("cantidad", cantidad);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_calendar_dialog, null);

        ButterKnife.bind(this, view);

        datePicker.setMaxDate(new Date().getTime());

        myCalendar = Calendar.getInstance();

        return new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setView(view)
                .create();
    }

    public void getData(final ProgressDialog progressDialog) {
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog) getDialog();

        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.set(Calendar.YEAR, datePicker.getYear());
                myCalendar.set(Calendar.MONTH, datePicker.getMonth());
                myCalendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                delegate.setDate(sdf.format(myCalendar.getTime()));
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}