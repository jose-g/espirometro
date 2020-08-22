package minsa.formulario.UI.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import minsa.formulario.UI.Fragments.Dialog.DNIDialogFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.R;

public class ScannerActivity extends AppCompatActivity implements  DNIDialogFragment.DNIDialogListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bclens_surface) SurfaceView cameraView;
    @BindView(R.id.bclens_text) TextView LensValue;

    private static final int ZBAR_CAMERA_PERMISSION = 1;

    private CameraSource cameraSource;
    private String TEXTO;
    private String LOOP = "S";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        ButterKnife.bind(this);

//        LensValue.setVisibility(View.GONE);

        toolbar.setTitle("Escanear código de DNI");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();

                try {
                    if (items.size() != 0) {
                        if(LOOP.equals("S")){
                            LensValue.post(new Runnable() {
                                @Override
                                public void run() {
                                    StringBuilder StringBlock = new StringBuilder();
                                    outerloop: for(int i = 0; i <items.size(); i++){
                                        TextBlock item = items.valueAt(i);
                                        for (Text line : item.getComponents()) {
                                            for (Text element : line.getComponents()) {
                                                TEXTO = element.getValue();
                                                StringBlock.append(TEXTO);
                                                if (TEXTO.length() > 10){
                                                    if (TEXTO.contains("I<PER")){
                                                        LOOP = "N";
                                                        int ini = TEXTO.indexOf("I<PER");
                                                        String dni = TEXTO.substring(ini+5, 13);
                                                        if (dni.substring(0,1).equals("O")){
                                                            dni = dni.replace("O","0").replace("T", "7").replace("<", "");
                                                        }

//                                                        Toast toast1 = Toast.makeText(getApplicationContext(), dni, Toast.LENGTH_LONG);
//                                                        toast1.show();

                                                        if(!TEXTO.isEmpty()){
                                                            try {
                                                                Intent intent = new Intent();
                                                                intent.putExtra("code", dni);
                                                                setResult(2, intent);
                                                                onBackPressed();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        stopScanning();
                                                        break outerloop;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            });

                        }
                    }
                } catch (Exception e) {

                }
            }
        });

        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available.");
        }

        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                //.setRequestedFps(15.0f)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
                    return;
                }
                showDNI();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    private void showDNI() {
        SharedPreferences sharedPreferences = getSharedPreferences("4PPS1C0V1D", MODE_PRIVATE);
        int dniDialog = sharedPreferences.getInt("dniDialog", 0);
        if(dniDialog > 0) {
            try {
                cameraSource.start(cameraView.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    DNIDialogFragment dniDialogFragment = new DNIDialogFragment();
                    dniDialogFragment.delegate = ScannerActivity.this;
                    dniDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
                    dniDialogFragment.setCancelable(false);
                    dniDialogFragment.show(getSupportFragmentManager(), "");

                }
            }, 150);
        }
    }

    private void stopScanning() {
        cameraSource.stop();
        //cameraSource.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraSource.release();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDNI();
                } else {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
                        return;
                    }
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void dialogViewed() {
        showDNI();
    }

}