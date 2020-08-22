package minsa.formulario.UI.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import minsa.formulario.R;

public class SpinnerDialog extends Dialog {
    private ArrayList<String> mList;
    private Context mContext;
    public Spinner mSpinner;

    public interface DialogListener {
        public void ready(int n);
        public void cancelled();
        public void selectFile();
    }

    private DialogListener mReadyListener;

    public SpinnerDialog(Context context, ArrayList<String> list, DialogListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = new ArrayList<String>();
        mList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_dialog);
        mSpinner = (Spinner) findViewById (R.id.dialog_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        mSpinner.setAdapter(adapter);

        Button buttonOK = (Button) findViewById(R.id.buttonOk);
        Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        Button dialogSelectFile = (Button) findViewById(R.id.buttonAdjntar);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                mReadyListener.ready(n);
                SpinnerDialog.this.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                SpinnerDialog.this.dismiss();
            }
        });
        dialogSelectFile.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.selectFile();
                //SpinnerDialog.this.dismiss();
            }
        });

    }
}
