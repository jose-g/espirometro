package minsa.formulario.UI.Fragments.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import minsa.formulario.UI.Activity.f200.F200Activity;
import minsa.formulario.UI.Activity.f300.F300Activity;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DbHelper.For000DbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;

public class ConsultaPorDocumentoDialogFragment extends DialogFragment implements TipDocDialogFragment.TipDocDialogListener {

    @BindView(R.id.tvTipDoc) TextView tvTipDoc;
    @BindView(R.id.tilNumberDocument) TextInputLayout tilNumberDocument;
    @BindView(R.id.tietNumberDocument) TextInputEditText tietNumberDocument;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursor;

    private String id_tip_doc = "";

    public static ConsultaPorDocumentoDialogFragment newInstance(int origin) {
        ConsultaPorDocumentoDialogFragment f = new ConsultaPorDocumentoDialogFragment();
        Bundle args = new Bundle();
        args.putInt("origin", origin);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_consultar_por_documento_dialog, null);

        ButterKnife.bind(this, view);

        mPrinciDbHelper = new PrinciDbHelper(getContext());

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
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tietNumberDocument.getWindowToken(), 0);

                if(id_tip_doc.isEmpty()) {
                    Toasty.info(getContext(), "Seleccione tipo de documento.", Toasty.LENGTH_SHORT).show();
                    return;
                }

                if(tietNumberDocument.getText().toString().isEmpty()) {
                    Toasty.info(getContext(), "Ingrese número de documento.", Toasty.LENGTH_SHORT).show();
                    return;
                }

                vCursor = mPrinciDbHelper.getAllPrinciOne(For000DbHelper.For000TableC.For000TableN, For000DbHelper.For000TableC.For000F00P02, tietNumberDocument.getText().toString());
                if(vCursor.moveToFirst()) {
                    dismiss();

                    Intent intent;
                    if(getArguments().getInt("origin", 0) == 200) {
                        intent = new Intent(getContext(), F200Activity.class);
                    } else {
                        intent = new Intent(getContext(), F300Activity.class);
                    }

                    intent.putExtra("id_tipo_documento", id_tip_doc);
                    intent.putExtra("documento", tietNumberDocument.getText().toString());
                    startActivity(intent);
                } else {
                    Toasty.info(getContext(), "Tiene que ingresar la ficha 100.", Toasty.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.rlContentTipDoc})
    public void onClicTipDoc(View view) {
        TipDocDialogFragment fragment = TipDocDialogFragment.newInstance(0);
        fragment.delegate = ConsultaPorDocumentoDialogFragment.this;
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
        fragment.show(getActivity().getSupportFragmentManager(), "");
    }


    @Override
    public void setTipDoc(TipDocDataSet dataSet) {
        tvTipDoc.setText(dataSet.getTipDocDescri());
        tvTipDoc.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        id_tip_doc = dataSet.getTipDocIdenti();
    }

}