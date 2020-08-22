package minsa.formulario.UI.Fragments.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import minsa.formulario.R;

public class DNIDialogFragment extends DialogFragment {

    @BindView(R.id.ivClose) ImageView ivClose;

    public DNIDialogListener delegate;

    public interface DNIDialogListener {
        void dialogViewed();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_dni_dialog, null);

        ButterKnife.bind(this, view);

        return new AlertDialog.Builder(getActivity(), R.style.AlertDialogThemeTransparent)
                .setView(view)
                .create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.ivClose)
    public void onClickClose(View view) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("4PPS1C0V1D", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("dniDialog", 1);
        editor.apply();

        delegate.dialogViewed();
        dismiss();
    }

}