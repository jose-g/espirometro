package minsa.formulario.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import minsa.formulario.BuildConfig;
import minsa.formulario.DataSet.UbicacDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.UsuariDbHelper;
import minsa.formulario.R;

public class InicioActivity extends AppCompatActivity {

    private View vView;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private UbicacDataSet vDsUbicac = UbicacDataSet.getInstance();
    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursor;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_activity);

        sharedPreferences = getSharedPreferences("4PPS1C0V1D", MODE_PRIVATE);

        mPrinciDbHelper = new PrinciDbHelper(this);
        try {
            deployDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // VersionChecker versionChecker = new VersionChecker();
       // versionChecker.execute();
    }

    public class VersionChecker extends AsyncTask<String, String, String> {

        private String newVersion = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "&hl=en")
                        .timeout(10000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.isEmpty()) {
                try {
                    deployDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            if(!s.equals(BuildConfig.VERSION_NAME)) {
                Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("market://details?id=" +  BuildConfig.APPLICATION_ID));
                startActivity(intent);
            } else {
                try {
                    deployDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean isDataBaseExist(String DB_PATH, String DB_NAME)
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        Log.d("Mensaje", "The database exist: " + String.valueOf(dbFile.exists()));
        return dbFile.exists();
    }

    private void deployDatabase() throws IOException {
        String packageName = getApplicationContext().getPackageName();
        String DB_PATH = "/data/data/" + packageName + "/databases/";
        File directory = new File(DB_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String DB_NAME = "coronavirus.sqlite";

        if(!isDataBaseExist(DB_PATH, DB_NAME)){
            InputStream myInput = getAssets().open("databases/" + DB_NAME);

            String outFileName = DB_PATH + DB_NAME;

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();

            Intent intent = new Intent(InicioActivity.this, AccesoActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(InicioActivity.this, AccesoActivity.class);
            startActivity(intent);
              /*

            if(sharedPreferences.getString("usuario", "").isEmpty()) {
                Intent intent = new Intent(InicioActivity.this, AccesoActivity.class);
                startActivity(intent);
                finish();
            } else {
                UsuariDataSet vDsUsuari = UsuariDataSet.getInstance();

                Cursor vUsuariCursor = mPrinciDbHelper.getAllTwoValues(UsuariDbHelper.UsuariTableC.UsuariTableN, UsuariDbHelper.UsuariTableC.UsuariUsuIng, UsuariDbHelper.UsuariTableC.UsuariConIng, sharedPreferences.getString("usuario", ""), sharedPreferences.getString("clave", ""));
                if(vUsuariCursor.getCount() != 0) {
                    while(vUsuariCursor.moveToNext()) {
                        vDsUsuari.setUsuariIdenti(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariIdenti)));
                        vDsUsuari.setUsuariNroDoc(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariNroDoc)));
                        vDsUsuari.setUsuariNomApe(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariNombre)) + " " +
                                vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariApePat)) + " " +
                                vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariApeMat)));
                        vDsUsuari.setUsuarioNom(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariNombre)));
                        vDsUsuari.setUsuarioPaterno(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariApePat)));
                        vDsUsuari.setUsuarioMaterno(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariApeMat)));
                        vDsUsuari.setUsuarioIdTipoDoc(vUsuariCursor.getString(vUsuariCursor.getColumnIndex(UsuariDbHelper.UsuariTableC.UsuariTipDoc)));
                    }
                    Intent intent = new Intent(InicioActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(InicioActivity.this, AccesoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }*/
        }

    }

}
