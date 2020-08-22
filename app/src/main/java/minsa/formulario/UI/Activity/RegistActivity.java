package minsa.formulario.UI.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import minsa.formulario.AppController;
import minsa.formulario.DataSet.AplPcrDataSet;
import minsa.formulario.DataSet.DepartDataSet;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DataSet.DistriDataSet;
import minsa.formulario.DataSet.PerSalDataSet;
import minsa.formulario.DataSet.ProcedDataSet;
import minsa.formulario.DataSet.ProfesDataSet;
import minsa.formulario.DataSet.ProvinDataSet;
import minsa.formulario.DataSet.RiesgoDataSet;
import minsa.formulario.DataSet.SeveriDataSet;
import minsa.formulario.DataSet.SexBioDataSet;
import minsa.formulario.DataSet.SintomDataSet;
import minsa.formulario.DataSet.TieSinDataSet;
import minsa.formulario.DataSet.TipDocDataSet;
import minsa.formulario.DataSet.TipResDataSet;
import minsa.formulario.DataSet.TipVivDataSet;
import minsa.formulario.DataSet.UbicacDataSet;
import minsa.formulario.DataSet.UsuariDataSet;
import minsa.formulario.DbHelper.AplPcrDbHelper;
import minsa.formulario.DbHelper.DepartDbHelper;
import minsa.formulario.DbHelper.DistriDbHelper;
import minsa.formulario.DbHelper.For000DbHelper;
import minsa.formulario.DbHelper.For100DbHelper;
import minsa.formulario.DbHelper.PerSalDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.ProcedDbHelper;
import minsa.formulario.DbHelper.ProfesDbHelper;
import minsa.formulario.DbHelper.ProvinDbHelper;
import minsa.formulario.DbHelper.RiesgoDbHelper;
import minsa.formulario.DbHelper.SeveriDbHelper;
import minsa.formulario.DbHelper.SexBioDbHelper;
import minsa.formulario.DbHelper.SintomDbHelper;
import minsa.formulario.DbHelper.TieSinDbHelper;
import minsa.formulario.DbHelper.TipDocDbHelper;
import minsa.formulario.DbHelper.TipResDbHelper;
import minsa.formulario.DbHelper.TipVivDbHelper;
import minsa.formulario.R;
import minsa.formulario.Util.Constants;

public class RegistActivity extends AppCompatActivity {

    private TextView vLtvTitulo;

    private ScrollView vLsvFicN00;

    private LinearLayout vLllF00P01;
    private LinearLayout vLllF00P02;
    private LinearLayout vLllF00P03;
    private LinearLayout vLllF00P04;
    private LinearLayout vLllF00P05;
    private LinearLayout vLllF00P06;
    private LinearLayout vLllF00P07;
    private LinearLayout vLllF00P08;
    private LinearLayout vLllF00P09;
    private LinearLayout vLllF00P10;
    private LinearLayout vLllF00P11;
    private LinearLayout vLllF00P12;
    private LinearLayout vLllF00P13;
    private LinearLayout vLllF00P14;
    private LinearLayout vLllF00P15;
    private LinearLayout vLllF00P16;
    private LinearLayout vLllF00P17;
    private LinearLayout vLllF00P18;
    private LinearLayout vLllF00P19;
    private LinearLayout vLllF00P20;
    private LinearLayout vLllF00P21;
    private LinearLayout vLllF00P22;

    private TextView vLtvF00P01;
    private EditText vLetF00P02;
    private EditText vLetF00P03;
    private EditText vLetF00P04;
    private EditText vLetF00P05;
    private EditText vLetF00P06;
    private TextView vLtvF00P07;
    private EditText vLetF00P08;
    private EditText vLetF00P09;
    private EditText vLetF00P10;
    private TextView vLtvF00P11;
    private EditText vLetF00P12;
    private TextView vLtvF00P13;
    private TextView vLtvF00P14;
    private TextView vLtvF00P15;
    private TextView vLtvF00P16;
    private TextView vLtvF00P17;
    private TextView vLtvF00P18;
    private TextView vLtvF00P19;
    private EditText vLetF00P20;
    private TextView vLtvF00P21;
    private EditText vLetF00P22;

    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorDepart;
    private Cursor vCursorProvin;
    private Cursor vCursorDistri;
    private String vDepart;
    private String vProvin;
    private String vDistri;
    private List<DepartDataSet> vLisDepart;
    private List<ProvinDataSet> vLisProvin;
    private List<DistriDataSet> vLisDistri;
    private ArrayList<String> vALiDepart;
    private ArrayList<String> vALiProvin;
    private ArrayList<String> vALiDistri;
    private int iPreF00P13 = 0;
    private int iPreF00P14 = 0;
    private int iPreF00P15 = 0;

    private Cursor vCursorTipDoc;
    private Cursor vCursorSexBio;
    private Cursor vCursorTipViv;
    private Cursor vCursorPerSal;
    private Cursor vCursorProfes;
    private Cursor vCursorTieSin;
    private Cursor vCursorSintom;
    private String vTipDoc;
    private String vSexBio;
    private String vTipViv;
    private String vPerSal;
    private String vProfes;
    private String vTieSin;
    private String vSintom;
    private List<TipDocDataSet> vLisTipDoc;
    private List<SexBioDataSet> vLisSexBio;
    private List<TipVivDataSet> vLisTipViv;
    private List<PerSalDataSet> vLisPerSal;
    private List<ProfesDataSet> vLisProfes;
    private List<TieSinDataSet> vLisTieSin;
    private List<SintomDataSet> vLisSintom;
    private ArrayList<String> vALiTipDoc;
    private ArrayList<String> vALiSexBio;
    private ArrayList<String> vALiTipViv;
    private ArrayList<String> vALiPerSal;
    private ArrayList<String> vALiProfes;
    private ArrayList<String> vALiTieSin;
    private ArrayList<String> vALiSintom;
    private String[] vArrTipDoc;
    private int iPreF00P01 = 0;
    private int iPreF00P07 = 0;
    private int iPreF00P11 = 0;
    private int iPreF00P17 = 0;
    private int iPreF00P18 = 0;
    private int iPreF00P19 = 0;
    private int iPreF00P21 = 0;

    private Cursor vCursorProced;
    private Cursor vCursorPriRes;
    private Cursor vCursorSegRes;
    private Cursor vCursorSeveri;
    private Cursor vCursorRiesgo;
    private Cursor vCursorAplPcr;
    private String vProced;
    private String vPriRes;
    private String vSegRes;
    private String vSeveri;
    private String vRiesgo;
    private String vAplPcr;
    private List<ProcedDataSet> vLisProced;
    private List<TipResDataSet> vLisPriRes;
    private List<TipResDataSet> vLisSegRes;
    private List<SeveriDataSet> vLisSeveri;
    private List<RiesgoDataSet> vLisRiesgo;
    private List<AplPcrDataSet> vLisAplPcr;
    private ArrayList<String> vALiProced;
    private ArrayList<String> vALiPriRes;
    private ArrayList<String> vALiSegRes;
    private ArrayList<String> vALiSeveri;
    private ArrayList<String> vALiRiesgo;
    private ArrayList<String> vALiAplPcr;
    private int iPreF01P03 = 0;
    private int iPreF01P04 = 0;
    private int iPreF01P05 = 0;
    private int iPreF01P06 = 0;
    private int iPreF01P07 = 0;
    private int iPreF01P09 = 0;

    private String vTokens;

    private UbicacDataSet vDsrUbicac;

    private DirWebDataSet vDstDirWeb;

    private ScrollView vLsvFicN01;

    private LinearLayout vLllF01P01;
    private LinearLayout vLllF01P02;
    private LinearLayout vLllF01P03;
    private LinearLayout vLllF01P04;
    private LinearLayout vLllF01P05;
    private LinearLayout vLllF01P06;
    private LinearLayout vLllF01P07;
    private LinearLayout vLllF01P07o;
    private LinearLayout vLllF01P08;
    private LinearLayout vLllF01P09;
    private LinearLayout vLllF01P10;

    private EditText vLetF01P01;
    private EditText vLetF01P02;
    private TextView vLtvF01P03;
    private TextView vLtvF01P04;
    private TextView vLtvF01P05;
    private TextView vLtvF01P06;
    private TextView vLtvF01P07;
    private TextView vLtvF01P07o;
    private EditText vLetF01P08;
    private TextView vLtvF01P09;
    private EditText vLetF01P10;

    private TextView vLtvPrimer;
    private TextView vLtvSegund;

    private int cPagina;
    private int cAnteri;
    private int cSiguie;

    private Animation vAanteri;
    private Animation vAanteri2;
    private Animation vAsiguie;
    private Animation vAsiguie2;

    private Bundle AvaBundle;
    private String vIdenti;
    //    private String vTipDoc;
    private String vNroDoc;
    private String vApePat;
    private String vApeMat;
    private String vNombre;
    private String vFecNac;
    private String vTipSex;

    private UbicacDataSet vDsUbicac;
    private UsuariDataSet vDsUsuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_activity);

        vLtvTitulo = (TextView) findViewById(R.id.LtvTitulo);

        vLsvFicN00 = (ScrollView) findViewById(R.id.LsvFicN00);

        vLllF00P01 = (LinearLayout) findViewById(R.id.LllF00P01);
        vLllF00P02 = (LinearLayout) findViewById(R.id.LllF00P02);
        vLllF00P03 = (LinearLayout) findViewById(R.id.LllF00P03);
        vLllF00P04 = (LinearLayout) findViewById(R.id.LllF00P04);
        vLllF00P05 = (LinearLayout) findViewById(R.id.LllF00P05);
        vLllF00P06 = (LinearLayout) findViewById(R.id.LllF00P06);
        vLllF00P07 = (LinearLayout) findViewById(R.id.LllF00P07);
        vLllF00P08 = (LinearLayout) findViewById(R.id.LllF00P08);
        vLllF00P09 = (LinearLayout) findViewById(R.id.LllF00P09);
        vLllF00P10 = (LinearLayout) findViewById(R.id.LllF00P10);
        vLllF00P11 = (LinearLayout) findViewById(R.id.LllF00P11);
        vLllF00P12 = (LinearLayout) findViewById(R.id.LllF00P12);
        vLllF00P13 = (LinearLayout) findViewById(R.id.LllF00P13);
        vLllF00P14 = (LinearLayout) findViewById(R.id.LllF00P14);
        vLllF00P15 = (LinearLayout) findViewById(R.id.LllF00P15);
        vLllF00P16 = (LinearLayout) findViewById(R.id.LllF00P16);
        vLllF00P17 = (LinearLayout) findViewById(R.id.LllF00P17);
        vLllF00P18 = (LinearLayout) findViewById(R.id.LllF00P18);
        vLllF00P19 = (LinearLayout) findViewById(R.id.LllF00P19);
        vLllF00P20 = (LinearLayout) findViewById(R.id.LllF00P20);
        vLllF00P21 = (LinearLayout) findViewById(R.id.LllF00P21);
        vLllF00P22 = (LinearLayout) findViewById(R.id.LllF00P22);

        vLtvF00P01 = (TextView) findViewById(R.id.LtvF00P01);
        vLetF00P02 = (EditText) findViewById(R.id.LetF00P02);
        vLetF00P03 = (EditText) findViewById(R.id.LetF00P03);
        vLetF00P04 = (EditText) findViewById(R.id.LetF00P04);
        vLetF00P05 = (EditText) findViewById(R.id.LetF00P05);
        vLetF00P06 = (EditText) findViewById(R.id.LetF00P06);
        vLtvF00P07 = (TextView) findViewById(R.id.LtvF00P07);
        vLetF00P08 = (EditText) findViewById(R.id.LetF00P08);
        vLetF00P09 = (EditText) findViewById(R.id.LetF00P09);
        vLetF00P10 = (EditText) findViewById(R.id.LetF00P10);
        vLtvF00P11 = (TextView) findViewById(R.id.LtvF00P11);
        vLetF00P12 = (EditText) findViewById(R.id.LetF00P12);
        vLtvF00P13 = (TextView) findViewById(R.id.LtvF00P13);
        vLtvF00P14 = (TextView) findViewById(R.id.LtvF00P14);
        vLtvF00P15 = (TextView) findViewById(R.id.LtvF00P15);
        vLtvF00P16 = (TextView) findViewById(R.id.LtvF00P16);
        vLtvF00P17 = (TextView) findViewById(R.id.LtvF00P17);
        vLtvF00P18 = (TextView) findViewById(R.id.LtvF00P18);
        vLtvF00P19 = (TextView) findViewById(R.id.LtvF00P19);
        vLetF00P20 = (EditText) findViewById(R.id.LetF00P20);
        vLtvF00P21 = (TextView) findViewById(R.id.LtvF00P21);
        vLetF00P22 = (EditText) findViewById(R.id.LetF00P22);

        vLsvFicN01 = (ScrollView) findViewById(R.id.LsvFicN01);

        vLllF01P01 = (LinearLayout) findViewById(R.id.LllF01P01);
        vLllF01P02 = (LinearLayout) findViewById(R.id.LllF01P02);
        vLllF01P03 = (LinearLayout) findViewById(R.id.LllF01P03);
        vLllF01P04 = (LinearLayout) findViewById(R.id.LllF01P04);
        vLllF01P05 = (LinearLayout) findViewById(R.id.LllF01P05);
        vLllF01P06 = (LinearLayout) findViewById(R.id.LllF01P06);
        vLllF01P07 = (LinearLayout) findViewById(R.id.LllF01P07);
        vLllF01P07o = (LinearLayout) findViewById(R.id.LllF01P07o);
        vLllF01P08 = (LinearLayout) findViewById(R.id.LllF01P08);
        vLllF01P09 = (LinearLayout) findViewById(R.id.LllF01P09);
        vLllF01P10 = (LinearLayout) findViewById(R.id.LllF01P10);

        vLetF01P01 = (EditText) findViewById(R.id.LetF01P01);
        vLetF01P02 = (EditText) findViewById(R.id.LetF01P02);
        vLtvF01P03 = (TextView) findViewById(R.id.LtvF01P03);
        vLtvF01P04 = (TextView) findViewById(R.id.LtvF01P04);
        vLtvF01P05 = (TextView) findViewById(R.id.LtvF01P05);
        vLtvF01P06 = (TextView) findViewById(R.id.LtvF01P06);
        vLtvF01P07 = (TextView) findViewById(R.id.LtvF01P07);
        vLtvF01P07o = (TextView) findViewById(R.id.LtvF01P07o);
        vLetF01P08 = (EditText) findViewById(R.id.LetF01P08);
        vLtvF01P09 = (TextView) findViewById(R.id.LtvF01P09);
        vLetF01P10 = (EditText) findViewById(R.id.LetF01P10);

        vLtvPrimer = (TextView) findViewById(R.id.LtvPrimer);
        vLtvSegund = (TextView) findViewById(R.id.LtvSegund);

        mPrinciDbHelper = new PrinciDbHelper(this);

        vDsrUbicac = UbicacDataSet.getInstance();

        vDstDirWeb = DirWebDataSet.getInstance();

        vLisDepart = new ArrayList<DepartDataSet>();
        vLisProvin = new ArrayList<ProvinDataSet>();
        vLisDistri = new ArrayList<DistriDataSet>();

        vALiDepart = new ArrayList<>();
        vALiProvin = new ArrayList<>();
        vALiDistri = new ArrayList<>();

        vLisTipDoc = new ArrayList<TipDocDataSet>();
        vLisSexBio = new ArrayList<SexBioDataSet>();
        vLisTipViv = new ArrayList<TipVivDataSet>();
        vLisPerSal = new ArrayList<PerSalDataSet>();
        vLisProfes = new ArrayList<ProfesDataSet>();
        vLisTieSin = new ArrayList<TieSinDataSet>();
        vLisSintom = new ArrayList<SintomDataSet>();

        vALiTipDoc = new ArrayList<>();
        vALiSexBio = new ArrayList<>();
        vALiTipViv = new ArrayList<>();
        vALiPerSal = new ArrayList<>();
        vALiProfes = new ArrayList<>();
        vALiTieSin = new ArrayList<>();
        vALiSintom = new ArrayList<>();

        vLisProced = new ArrayList<ProcedDataSet>();
        vLisPriRes = new ArrayList<TipResDataSet>();
        vLisSegRes = new ArrayList<TipResDataSet>();
        vLisSeveri = new ArrayList<SeveriDataSet>();
        vLisRiesgo = new ArrayList<RiesgoDataSet>();
        vLisAplPcr = new ArrayList<AplPcrDataSet>();

        vALiProced = new ArrayList<>();
        vALiPriRes = new ArrayList<>();
        vALiSegRes = new ArrayList<>();
        vALiSeveri = new ArrayList<>();
        vALiRiesgo = new ArrayList<>();
        vALiAplPcr = new ArrayList<>();

        vAanteri = AnimationUtils.loadAnimation(this, R.anim.anterior);
        vAanteri2 = AnimationUtils.loadAnimation(this, R.anim.anterior2);
        vAsiguie = AnimationUtils.loadAnimation(this, R.anim.siguiente);
        vAsiguie2 = AnimationUtils.loadAnimation(this, R.anim.siguiente2);

        AvaBundle = getIntent().getExtras();
        vIdenti = AvaBundle.getString("Identi");
        vTipDoc = AvaBundle.getString("TipDoc");
        vNroDoc = AvaBundle.getString("NroDoc");
        vApePat = AvaBundle.getString("ApePat");
        vApeMat = AvaBundle.getString("ApeMat");
        vNombre = AvaBundle.getString("Nombre");
        vFecNac = AvaBundle.getString("FecNac");
        vTipSex = AvaBundle.getString("TipSex");

        vDsUbicac = UbicacDataSet.getInstance();
        vDsUsuari = UsuariDataSet.getInstance();

        cPagina = 1;

        MDbsSerUbigeo();

        MDbsSerTipDoc();

        MDbsSerProfes();

        MStrSerTipViv();

        MStrSerPerSal();

        MStrSerTieSin();

        MStrSerSexBio();

        MDbsSerSintom();

        MEdiSerNroDoc();

        MEdiSerFecNac();

        MTexSerPrimer();

        MTexSerSegund();

        MEdiSerFecEje();

        MEdiSerHorEje();

        MEdiSerTelCel();

        MEdiSerTelCOn();

        MTexSerGeoloc();

        MEdiSerFecSin();

        MEdiSerFecSin();

        MDbsSerProced();

        MDbsSerPriRes();

        MDbsSerSegRes();

        MDbsSerSeveri();

        MDbsSerRiesgo();

        MDbsSerAplPcr();

        handleSSLHandshake();

        if(vIdenti.equals("1")){
            cPagina = 2;
            vLsvFicN00.setVisibility(View.GONE);
            vLsvFicN01.setVisibility(View.VISIBLE);
            vLtvPrimer.setVisibility(View.GONE);
            vLtvTitulo.setText(getResources().getString(R.string.formul_opcn01_titulo));
            vLtvSegund.setText(getResources().getString(R.string.formul_finali));
        }else{
            vLtvF00P01.setText(vArrTipDoc[Integer.parseInt(vTipDoc)-1]);
            vLtvF00P01.setInputType(InputType.TYPE_NULL);
            vLtvF00P01.setEnabled(false);
            vLetF00P02.setText(vNroDoc);
            vLetF00P02.setInputType(InputType.TYPE_NULL);
            vLetF00P02.setEnabled(false);
            vLetF00P03.setText(vNombre);
            vLetF00P03.setInputType(InputType.TYPE_NULL);
            vLetF00P03.setEnabled(false);
            vLetF00P04.setText(vApePat);
            vLetF00P04.setInputType(InputType.TYPE_NULL);
            vLetF00P04.setEnabled(false);
            vLetF00P05.setText(vApeMat);
            vLetF00P05.setInputType(InputType.TYPE_NULL);
            vLetF00P05.setEnabled(false);
            vLetF00P06.setText(vFecNac.substring(6, 8) + "/" + vFecNac.substring(4, 6) + "/" + vFecNac.substring(0, 4));
            vLetF00P06.setInputType(InputType.TYPE_NULL);
            vLetF00P06.setEnabled(false);
            if(vTipSex.equals("1")){
                vLtvF00P07.setText("MASCULINO");
            }else{
                vLtvF00P07.setText("FEMENINO");
            }
            vLtvF00P07.setInputType(InputType.TYPE_NULL);
            vLtvF00P07.setEnabled(false);
        }

        vLllF00P18.setVisibility(View.GONE);
        vLllF00P20.setVisibility(View.GONE);
        vLllF00P21.setVisibility(View.GONE);
        vLllF00P22.setVisibility(View.GONE);
        vLllF01P05.setVisibility(View.GONE);
        vLllF01P06.setVisibility(View.GONE);
        vLllF01P07.setVisibility(View.GONE);
        vLllF01P07o.setVisibility(View.GONE);
        vLllF01P08.setVisibility(View.GONE);
        vLllF01P09.setVisibility(View.GONE);

    }

    private void MDbsSerAplPcr(){

        vLisAplPcr.clear();

        vCursorAplPcr = mPrinciDbHelper.getAllPrinci(AplPcrDbHelper.AplPcrTableC.AplPcrTableN);
        while(vCursorAplPcr.moveToNext()){
            AplPcrDataSet vDstAplPcr = new AplPcrDataSet();
            vDstAplPcr.setAplPcrIdenti(vCursorAplPcr.getString(vCursorAplPcr.getColumnIndex(AplPcrDbHelper.AplPcrTableC.AplPcrIdenti)));
            vDstAplPcr.setAplPcrDescri((vCursorAplPcr.getString(vCursorAplPcr.getColumnIndex(AplPcrDbHelper.AplPcrTableC.AplPcrDescri))).toUpperCase());
            vLisAplPcr.add(vDstAplPcr);
        }

        vALiAplPcr.clear();
        for (AplPcrDataSet o : vLisAplPcr) {
            vALiAplPcr.add(o.getAplPcrDescri());
        }

        String[] vArrF01P09 = new String[vALiAplPcr.size()];

        vArrF01P09 = vALiAplPcr.toArray(vArrF01P09);

        String[] finalVArrF01P09 = vArrF01P09;

        vLtvF01P09.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren09))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF01P09, iPreF01P09, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF01P09 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF01P09.setText(finalVArrF01P09[iPreF01P09]);
                                for (AplPcrDataSet o : vLisAplPcr) {
                                    if(o.getAplPcrDescri().equals(finalVArrF01P09[iPreF01P09])){
                                        vAplPcr = o.getAplPcrIdenti();
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MDbsSerRiesgo(){

        vLisRiesgo.clear();

        vCursorRiesgo = mPrinciDbHelper.getAllPrinci(RiesgoDbHelper.RiesgoTableC.RiesgoTableN);
        while(vCursorRiesgo.moveToNext()){
            RiesgoDataSet vDstRiesgo = new RiesgoDataSet();
            vDstRiesgo.setRiesgoIdenti(vCursorRiesgo.getString(vCursorRiesgo.getColumnIndex(RiesgoDbHelper.RiesgoTableC.RiesgoIdenti)));
            vDstRiesgo.setRiesgoDescri((vCursorRiesgo.getString(vCursorRiesgo.getColumnIndex(RiesgoDbHelper.RiesgoTableC.RiesgoDescri))).toUpperCase());
            vLisRiesgo.add(vDstRiesgo);
        }

        vALiRiesgo.clear();
        for (RiesgoDataSet o : vLisRiesgo) {
            vALiRiesgo.add(o.getRiesgoDescri());
        }

        String[] vArrF01P07 = new String[vALiRiesgo.size()];

        vArrF01P07 = vALiRiesgo.toArray(vArrF01P07);

        String[] finalVArrF01P07 = vArrF01P07;

        boolean[] vArrF01P07boolean = new boolean[vArrF01P07.length];
        for (int i = 0; i < vArrF01P07.length; i++) {
            vArrF01P07boolean[i] = false;
        }

        String[] finalVArrF01P0 = vArrF01P07;

        final int[] vNinguno = {0};

        vLtvF01P07.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren07))
                        .setMultiChoiceItems(finalVArrF01P07, vArrF01P07boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrF01P07boolean[which] = isChecked;
                                if(vNinguno[0] == 0){
                                    if(finalVArrF01P0[which].equals(getResources().getString(R.string.formul_opcn01_pren07_02))){
                                        vNinguno[0] = 1;
                                        for (int i = 0; i < vArrF01P07boolean.length; i++) {
                                            if (i == which) {
                                                vArrF01P07boolean[i]=true;
                                                ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                                            }
                                            else {
                                                vArrF01P07boolean[i]=false;
                                                ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                                            }
                                        }
                                    }
                                }else{
                                    vNinguno[0] = 0;
                                    if(!finalVArrF01P0[which].equals(getResources().getString(R.string.formul_opcn01_pren07_02))){
                                        for (int i = 0; i < vArrF01P07boolean.length; i++) {
                                            if (i == which) {
                                                vArrF01P07boolean[i]=true;
                                                ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                                            }
                                            else {
                                                vArrF01P07boolean[i]=false;
                                                ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                                            }
                                        }
                                    }
                                }

                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF01P07.setText(" ");
                                iPreF01P07 = 0;
                                for (int i = 0; i < finalVArrF01P07.length; i++) {
                                    if (vArrF01P07boolean[i]) {
                                        if(iPreF01P07 == 0){
                                            vLtvF01P07.setText(finalVArrF01P07[i]);
                                            iPreF01P07 = 1;
                                        }else{
                                            vLtvF01P07.setText(vLtvF01P07.getText().toString() + "\n" + finalVArrF01P07[i]);
                                            iPreF01P07 = 1;
                                        }
                                    }
                                    if(finalVArrF01P07[i].equals(getResources().getString(R.string.formul_opcn01_pren07_01))){
                                        if (vArrF01P07boolean[i]) {
                                            vLllF01P08.setVisibility(View.VISIBLE);
                                            vLllF01P08.requestFocus();
                                        } else {
                                            vLllF01P08.setVisibility(View.GONE);
                                        }
                                    }
                                    if(vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_02)) ||
                                            (vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_01)) &&
                                                    vLtvF01P05.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren05_02)))){
                                        if(vLtvF01P06.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren06_01))){
                                            if(vLtvF01P07.getText().toString().contains(getResources().getString(R.string.formul_opcn01_pren07_02))){
                                                vLllF01P07o.setVisibility(View.VISIBLE);
                                                vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M01));
                                                vLllF01P09.setVisibility(View.GONE);
                                            }else{
                                                if(!vLtvF01P07.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                                    vLllF01P07o.setVisibility(View.VISIBLE);
                                                    vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M02));
                                                    vLllF01P09.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                        if(vLtvF01P06.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren06_02))){
                                            if(!vLtvF01P07.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                                vLllF01P07o.setVisibility(View.VISIBLE);
                                                vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M03));
                                                vLllF01P09.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        if(vLtvF01P06.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren06_03))){
                                            if(!vLtvF01P07.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                                vLllF01P07o.setVisibility(View.VISIBLE);
                                                vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M04));
                                                vLllF01P09.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                    if((vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_03a)) ||
                                            vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_03b)) ||
                                            vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_03c))) ||
                                            (vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_01)) &&
                                                    (vLtvF01P05.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren05_03a)) ||
                                                            vLtvF01P05.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren05_03b)) ||
                                                            vLtvF01P05.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren05_03c))))){
                                        if(vLtvF01P06.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren06_01))){
                                            if(vLtvF01P07.getText().toString().contains(getResources().getString(R.string.formul_opcn01_pren07_02))){
                                                vLllF01P07o.setVisibility(View.VISIBLE);
                                                vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M05));
                                                vLllF01P09.setVisibility(View.GONE);
                                            }else{
                                                if(!vLtvF01P07.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                                    vLllF01P07o.setVisibility(View.VISIBLE);
                                                    vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M06));
                                                    vLllF01P09.setVisibility(View.GONE);
                                                }
                                            }
                                        }
                                        if(vLtvF01P06.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren06_02))){
                                            if(!vLtvF01P07.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                                vLllF01P07o.setVisibility(View.VISIBLE);
                                                vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M07));
                                                vLllF01P09.setVisibility(View.GONE);
                                            }
                                        }
                                        if(vLtvF01P06.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren06_03))){
                                            if(!vLtvF01P07.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                                vLllF01P07o.setVisibility(View.VISIBLE);
                                                vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M08));
                                                vLllF01P09.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                                if(iPreF01P07 == 0){
                                    vLtvF01P07.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

//        vLtvF01P07.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
//                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren07))
//                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
//                        .setSingleChoiceItems(finalVArrF01P07, iPreF01P07, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                iPreF01P07 = which;
//                            }
//                        })
//                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                vLtvF01P07.setText(finalVArrF01P07[iPreF01P07]);
//                                for (RiesgoDataSet o : vLisRiesgo) {
//                                    if(o.getRiesgoDescri().equals(finalVArrF01P07[iPreF01P07])){
//                                        vRiesgo = o.getRiesgoIdenti();
//                                    }
//                                }
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
//                builder.create().show();
//            }
//        });

    }

    private void MDbsSerSeveri(){

        vLisSeveri.clear();

        vCursorSeveri = mPrinciDbHelper.getAllPrinci(SeveriDbHelper.SeveriTableC.SeveriTableN);
        while(vCursorSeveri.moveToNext()){
            SeveriDataSet vDstSeveri = new SeveriDataSet();
            vDstSeveri.setId(vCursorSeveri.getInt(vCursorSeveri.getColumnIndex(SeveriDbHelper.SeveriTableC.SeveriIdenti)));
            vDstSeveri.setDescripcion((vCursorSeveri.getString(vCursorSeveri.getColumnIndex(SeveriDbHelper.SeveriTableC.SeveriDescri))).toUpperCase());
            vLisSeveri.add(vDstSeveri);
        }

        vALiSeveri.clear();
        for (SeveriDataSet o : vLisSeveri) {
            vALiSeveri.add(o.getDescripcion());
        }

        String[] vArrF01P06 = new String[vALiSeveri.size()];

        vArrF01P06 = vALiSeveri.toArray(vArrF01P06);

        String[] finalVArrF01P06 = vArrF01P06;

        vLtvF01P06.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren06))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF01P06, iPreF01P06, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF01P06 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF01P06.setText(finalVArrF01P06[iPreF01P06]);
                                for (SeveriDataSet o : vLisSeveri) {
                                    if(o.getDescripcion().equals(finalVArrF01P06[iPreF01P06])){
                                        vSeveri = String.valueOf(o.getId());
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MDbsSerSegRes(){

        vLisSegRes.clear();

        vCursorSegRes = mPrinciDbHelper.getAllPrinci(TipResDbHelper.TipResTableC.TipResTableN);
        while(vCursorSegRes.moveToNext()){
            TipResDataSet vDstSegRes = new TipResDataSet();
            vDstSegRes.setTipResIdenti(vCursorSegRes.getInt(vCursorSegRes.getColumnIndex(TipResDbHelper.TipResTableC.TipResIdenti)));
            vDstSegRes.setTipResDescri((vCursorSegRes.getString(vCursorSegRes.getColumnIndex(TipResDbHelper.TipResTableC.TipResDescri))).toUpperCase());
            vLisSegRes.add(vDstSegRes);
        }

        vALiSegRes.clear();
        for (TipResDataSet o : vLisSegRes) {
            vALiSegRes.add(o.getTipResDescri());
        }

        String[] vArrF01P05 = new String[vALiSegRes.size()];

        vArrF01P05 = vALiSegRes.toArray(vArrF01P05);

        String[] finalVArrF01P05 = vArrF01P05;

        vLtvF01P05.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren05))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF01P05, iPreF01P05, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF01P05 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF01P05.setText(finalVArrF01P05[iPreF01P05]);
                                for (TipResDataSet o : vLisSegRes) {
                                    if(o.getTipResDescri().equals(finalVArrF01P05[iPreF01P05])){
                                        vSegRes = String.valueOf(o.getTipResIdenti());
                                    }
                                }
                                for (TipResDataSet o : vLisSegRes) {
                                    if(o.getTipResDescri().equals(finalVArrF01P05[iPreF01P05])){
                                        vSegRes = String.valueOf(o.getTipResIdenti());
                                        if(o.getTipResDescri().equals(getResources().getString(R.string.formul_opcn01_pren04_01))){
                                            vLllF01P06.setVisibility(View.GONE);
                                            vLllF01P07.setVisibility(View.GONE);
                                            vLllF01P07o.setVisibility(View.VISIBLE);
                                            vLtvF01P07o.setText(getResources().getString(R.string.formul_opcn01_pren07_M09));
                                        }else{
                                            vLllF01P06.setVisibility(View.VISIBLE);
                                            vLllF01P07.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MDbsSerPriRes(){

        vLisPriRes.clear();

        vCursorPriRes = mPrinciDbHelper.getAllPrinci(TipResDbHelper.TipResTableC.TipResTableN);
        while(vCursorPriRes.moveToNext()){
            TipResDataSet vDstPriRes = new TipResDataSet();
            vDstPriRes.setTipResIdenti(vCursorPriRes.getInt(vCursorPriRes.getColumnIndex(TipResDbHelper.TipResTableC.TipResIdenti)));
            vDstPriRes.setTipResDescri((vCursorPriRes.getString(vCursorPriRes.getColumnIndex(TipResDbHelper.TipResTableC.TipResDescri))).toUpperCase());
            vLisPriRes.add(vDstPriRes);
        }

        vALiPriRes.clear();
        for (TipResDataSet o : vLisPriRes) {
            vALiPriRes.add(o.getTipResDescri());
        }

        String[] vArrF01P04 = new String[vALiPriRes.size()];

        vArrF01P04 = vALiPriRes.toArray(vArrF01P04);

        String[] finalVArrF01P04 = vArrF01P04;

        vLtvF01P04.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren04))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF01P04, iPreF01P04, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF01P04 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF01P04.setText(finalVArrF01P04[iPreF01P04]);
                                for (TipResDataSet o : vLisPriRes) {
                                    if(o.getTipResDescri().equals(finalVArrF01P04[iPreF01P04])){
                                        vPriRes = String.valueOf(o.getTipResIdenti());
                                        if(o.getTipResDescri().equals(getResources().getString(R.string.formul_opcn01_pren04_01))){
                                            vLllF01P05.setVisibility(View.VISIBLE);
                                            vLllF01P06.setVisibility(View.GONE);
                                            vLllF01P07.setVisibility(View.GONE);
                                        }else{
                                            vLllF01P05.setVisibility(View.GONE);
                                            vLllF01P06.setVisibility(View.VISIBLE);
                                            vLllF01P07.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MDbsSerProced(){

        vLisProced.clear();

        vCursorProced = mPrinciDbHelper.getAllPrinci(ProcedDbHelper.ProcedTableC.ProcedTableN);
        while(vCursorProced.moveToNext()){
            ProcedDataSet vDstProced = new ProcedDataSet();
            vDstProced.setProcedIdenti(vCursorProced.getInt(vCursorProced.getColumnIndex(ProcedDbHelper.ProcedTableC.ProcedIdenti)));
            vDstProced.setProcedDescri((vCursorProced.getString(vCursorProced.getColumnIndex(ProcedDbHelper.ProcedTableC.ProcedDescri))).toUpperCase());
            vLisProced.add(vDstProced);
        }

        vALiProced.clear();
        for (ProcedDataSet o : vLisProced) {
            vALiProced.add(o.getProcedDescri());
        }

        String[] vArrF01P03 = new String[vALiProced.size()];

        vArrF01P03 = vALiProced.toArray(vArrF01P03);

        String[] finalVArrF01P03 = vArrF01P03;

        vLtvF01P03.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren03))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF01P03, iPreF01P03, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF01P03 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF01P03.setText(finalVArrF01P03[iPreF01P03]);
                                for (ProcedDataSet o : vLisProced) {
                                    if(o.getProcedDescri().equals(finalVArrF01P03[iPreF01P03])){
                                        vProfes = String.valueOf(o.getProcedIdenti());
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MEdiSerFecSin(){

        Calendar vLcaF00P20 = Calendar.getInstance();

        int vLinF00P20Y = vLcaF00P20.get(Calendar.YEAR);
        int vLinF00P20M = vLcaF00P20.get(Calendar.MONTH);
        int vLinF00P20D = vLcaF00P20.get(Calendar.DAY_OF_MONTH);

        vLetF00P20.setText(vLinF00P20D + "/" + ((vLinF00P20M + 1) < 10 ? "0" + (vLinF00P20M + 1) : "" + (vLinF00P20M + 1)) + "/" + vLinF00P20Y);

        DatePickerDialog vLdpF00P20 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetF00P20.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinF00P20Y, vLinF00P20M, vLinF00P20D);

        Calendar vLcaF00P20N = Calendar.getInstance();
        vLcaF00P20N.add(Calendar.MINUTE, +1);

        vLdpF00P20.getDatePicker().setMaxDate(vLcaF00P20N.getTimeInMillis());

        vLetF00P20.setInputType(InputType.TYPE_NULL);
        vLetF00P20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(RegistActivity.this);
                vLdpF00P20.show();
            }
        });

    }

    private void MTexSerGeoloc(){

        vLtvF00P16.setText("- " +getResources().getString(R.string.formul_opcn00_pren16_01) + ": "+ vDsUbicac.getUbicacLatitu() + "\n" +
                "- " +getResources().getString(R.string.formul_opcn00_pren16_02) + ": "+ vDsUbicac.getUbicacLongit());

    }

    private void MEdiSerTelCOn(){

        vLetF00P09.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetF00P09.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });

        vLetF00P09.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regex = "9[0-9]{8}";
                if(s.length() == 0){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 1 && !vLetF00P09.getText().toString().matches("9")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 2 && !vLetF00P09.getText().toString().matches("9[0-9]{1}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 3 && !vLetF00P09.getText().toString().matches("9[0-9]{2}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 4 && !vLetF00P09.getText().toString().matches("9[0-9]{3}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 5 && !vLetF00P09.getText().toString().matches("9[0-9]{4}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 6 && !vLetF00P09.getText().toString().matches("9[0-9]{5}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 7 && !vLetF00P09.getText().toString().matches("9[0-9]{6}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 8 && !vLetF00P09.getText().toString().matches("9[0-9]{7}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
                if(s.length() == 9 && !vLetF00P09.getText().toString().matches("9[0-9]{8}")){
                    vLetF00P09.setText("9");
                    vLetF00P09.requestFocus();
                    vLetF00P09.setSelection(vLetF00P09.getText().length());
                }
            }
        });

    }

    private void MEdiSerTelCel(){

        vLetF00P08.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetF00P08.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });

        vLetF00P08.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regex = "9[0-9]{8}";
                if(s.length() == 0){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 1 && !vLetF00P08.getText().toString().matches("9")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 2 && !vLetF00P08.getText().toString().matches("9[0-9]{1}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 3 && !vLetF00P08.getText().toString().matches("9[0-9]{2}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 4 && !vLetF00P08.getText().toString().matches("9[0-9]{3}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 5 && !vLetF00P08.getText().toString().matches("9[0-9]{4}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 6 && !vLetF00P08.getText().toString().matches("9[0-9]{5}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 7 && !vLetF00P08.getText().toString().matches("9[0-9]{6}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 8 && !vLetF00P08.getText().toString().matches("9[0-9]{7}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
                if(s.length() == 9 && !vLetF00P08.getText().toString().matches("9[0-9]{8}")){
                    vLetF00P08.setText("9");
                    vLetF00P08.requestFocus();
                    vLetF00P08.setSelection(vLetF00P08.getText().length());
                }
            }
        });

    }

    private void MEdiSerHorEje(){

        Calendar vLcaF01P02 = Calendar.getInstance();

        int vLinF01P02H = vLcaF01P02.get(Calendar.HOUR);
        int vLinF01P02M = vLcaF01P02.get(Calendar.MINUTE);

        vLetF01P02.setText(formatNumber(vLinF01P02H) + ":" + formatNumber(vLinF01P02M));

        TimePickerDialog vLtpF01P02 = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        vLetF01P02.setText(formatNumber(hourOfDay) + ":" + formatNumber(minute));
                    }
                }, vLinF01P02H, vLinF01P02M, false);

        vLetF01P02.setInputType(InputType.TYPE_NULL);
        vLetF01P02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(RegistActivity.this);
                vLtpF01P02.show();
            }
        });

    }

    private void MEdiSerFecEje(){

        Calendar vLcaF01P01 = Calendar.getInstance();

        int vLinF01P01Y = vLcaF01P01.get(Calendar.YEAR);
        int vLinF01P01M = vLcaF01P01.get(Calendar.MONTH);
        int vLinF01P01D = vLcaF01P01.get(Calendar.DAY_OF_MONTH);

        vLetF01P01.setText(vLinF01P01D + "/" + ((vLinF01P01M + 1) < 10 ? "0" + (vLinF01P01M + 1) : "" + (vLinF01P01M + 1)) + "/" + vLinF01P01Y);

        DatePickerDialog vLdpF01P01 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetF01P01.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinF01P01Y, vLinF01P01M, vLinF01P01D);

        Calendar vLcaF01P01N = Calendar.getInstance();
        vLcaF01P01N.add(Calendar.MINUTE, +1);

        vLdpF01P01.getDatePicker().setMaxDate(vLcaF01P01N.getTimeInMillis());

        vLetF01P01.setInputType(InputType.TYPE_NULL);
        vLetF01P01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(RegistActivity.this);
                vLdpF01P01.show();
            }
        });

    }

    private void MTexSerPrimer(){

        vLtvPrimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                cAnteri = 0;
                if(cPagina == 2 && cAnteri == 0){
                    vLsvFicN01.startAnimation(vAanteri);
                    vLsvFicN01.setVisibility(View.GONE);
                    vLsvFicN00.startAnimation(vAanteri2);
                    vLsvFicN00.setVisibility(View.VISIBLE);
                    vLtvPrimer.setVisibility(View.GONE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn00_titulo));
                    vLtvSegund.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 1;
                    cAnteri = 1;
                }
//                if(cPagina == 3 && cAnteri == 0){
//                    vLsvPart03.startAnimation(vAanteri);
//                    vLsvPart03.setVisibility(View.GONE);
//                    vLsvPart02.startAnimation(vAanteri2);
//                    vLsvPart02.setVisibility(View.VISIBLE);
//                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn01));
//                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn01_subn02));
//                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
//                    cPagina = 2;
//                    cAnteri = 1;
//                }
//                if(cPagina == 14 && cAnteri == 0){
//                    vLsvPart14.startAnimation(vAanteri);
//                    vLsvPart14.setVisibility(View.GONE);
//                    vLsvPart13.startAnimation(vAanteri2);
//                    vLsvPart13.setVisibility(View.VISIBLE);
//                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
//                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn14));
//                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
//                    cPagina = 13;
//                    cAnteri = 1;
//                }
//                if(cPagina == 15 && cAnteri == 0){
//                    vLsvPart15.startAnimation(vAanteri);
//                    vLsvPart15.setVisibility(View.GONE);
//                    vLsvPart14.startAnimation(vAanteri2);
//                    vLsvPart14.setVisibility(View.VISIBLE);
//                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
//                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn15));
//                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
//                    cPagina = 14;
//                    cAnteri = 1;
//                }
            }
        });

    }

    private void MTexSerSegund(){

        vLtvSegund.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                cSiguie = 0;
                if (cPagina == 1 && cSiguie == 0) {
                    if(!vLtvF00P17.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                        if((vLtvF00P17.getText().toString().equals(getResources().getString(R.string.formul_opcn00_pren17_01)) &&
                                !vLtvF00P18.getText().toString().equals(getResources().getString(R.string.formul_selecc))) ||
                                (vLtvF00P17.getText().toString().equals(getResources().getString(R.string.formul_opcn00_pren17_02)))){
                            if(!vLtvF00P19.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                if((vLtvF00P19.getText().toString().equals(getResources().getString(R.string.formul_opcn00_pren19_01)) &&
                                        !vLtvF00P21.getText().toString().equals(getResources().getString(R.string.formul_selecc))) ||
                                        (vLtvF00P19.getText().toString().equals(getResources().getString(R.string.formul_opcn00_pren19_02)))){
                                    if((vLtvF00P21.getText().toString().contains(getResources().getString(R.string.formul_opcn00_pren21_01)) &&
                                            !vLetF00P22.getText().toString().equals("")) ||
                                            (!vLtvF00P21.getText().toString().contains(getResources().getString(R.string.formul_opcn00_pren21_01)))){
                                        vLsvFicN00.startAnimation(vAsiguie2);
                                        vLsvFicN00.setVisibility(View.GONE);
                                        vLsvFicN01.startAnimation(vAsiguie);
                                        vLsvFicN01.setVisibility(View.VISIBLE);
                                        vLtvPrimer.setVisibility(View.VISIBLE);
                                        vLtvTitulo.setText(getResources().getString(R.string.formul_opcn01_titulo));
                                        vLtvSegund.setText(getResources().getString(R.string.formul_finali));
                                        cPagina = 2;
                                        cSiguie = 1;
                                    }else{
                                        Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn00_pren22), Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn00_pren21), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn00_pren19), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn00_pren18), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn00_pren17), Toast.LENGTH_SHORT).show();
                    }

                }
                if (cPagina == 2 && cSiguie == 0) {
                    if(!vLetF01P01.getText().toString().equals("")){
                        if(!vLetF01P02.getText().toString().equals("")){
                            if(!vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                if((vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_01)) &&
                                        !vLtvF01P05.getText().toString().equals(getResources().getString(R.string.formul_selecc))) ||
                                        (!vLtvF01P04.getText().toString().equals(getResources().getString(R.string.formul_opcn01_pren04_01)))){
                                    if(!vLtvF01P06.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                        if(!vLtvF01P07.getText().toString().equals(getResources().getString(R.string.formul_selecc))){
                                            if((vLtvF01P07.getText().toString().contains(getResources().getString(R.string.formul_opcn01_pren07_01)) &&
                                                    !vLetF01P08.getText().toString().equals("")) ||
                                                    (!vLtvF01P07.getText().toString().contains(getResources().getString(R.string.formul_opcn01_pren07_01)))){
                                                Toast.makeText(RegistActivity.this, "Registro completado", Toast.LENGTH_SHORT).show();
                                                ContentValues Fic000Values = new ContentValues();
                                                Fic000Values.put(For000DbHelper.For000TableC.For000UsuCod, vDsUsuari.getUsuariNroDoc());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000For100, "1");
                                                Fic000Values.put(For000DbHelper.For000TableC.For000For200, "0");
                                                Fic000Values.put(For000DbHelper.For000TableC.For000For300, "0");
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P01, vLtvF00P01.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P02, vLetF00P02.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P03, vLetF00P03.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P04, vLetF00P04.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P05, vLetF00P05.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P06, vLetF00P06.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P07, vLtvF00P07.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P08, vLetF00P08.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P09, vLetF00P09.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P10, vLetF00P10.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P11, vLtvF00P11.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P12, vLetF00P12.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P13, vLtvF00P13.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P14, vLtvF00P14.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P15, vLtvF00P15.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P16, vLtvF00P16.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P17, vLtvF00P17.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P18, vLtvF00P18.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P19, vLtvF00P19.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P20, vLetF00P20.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P21, vLtvF00P21.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000F00P22, vLetF00P22.getText().toString());
                                                Fic000Values.put(For000DbHelper.For000TableC.For000ESTADO, "1");
                                                mPrinciDbHelper.savePrinci(For000DbHelper.For000TableC.For000TableN, Fic000Values);
                                                ContentValues Fic100Values = new ContentValues();
                                                Fic100Values.put(For100DbHelper.For100TableC.For100FN0Cod, vLetF00P02.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P01, vLetF01P01.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P02, vLetF01P02.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P03, vLtvF01P03.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P04, vLtvF01P04.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P05, vLtvF01P05.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P06, vLtvF01P06.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P07, vLtvF01P07.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P08, vLetF01P08.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P09, vLtvF01P09.getText().toString());
                                                Fic100Values.put(For100DbHelper.For100TableC.For100F01P10, vLetF01P10.getText().toString());
                                                mPrinciDbHelper.savePrinci(For100DbHelper.For100TableC.For100TableN, Fic100Values);
//                                                MSerGetTokens();
                                                finish();
                                                cPagina = 2;
                                                cSiguie = 1;
                                            }else{
                                                Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn01_pren08), Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn01_pren07), Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn01_pren06), Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn01_pren05), Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn01_pren04), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn01_pren02), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegistActivity.this, "Debe responder la pregunta " + getResources().getString(R.string.formul_opcn01_pren01), Toast.LENGTH_SHORT).show();
                    }
                }
//                if(cPagina == 13 && cSiguie == 0){
//                    vLsvPart13.startAnimation(vAsiguie2);
//                    vLsvPart13.setVisibility(View.GONE);
//                    vLsvPart14.startAnimation(vAsiguie);
//                    vLsvPart14.setVisibility(View.VISIBLE);
//                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
//                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn15));
//                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
//                    cPagina = 14;
//                    cSiguie = 1;
//                }
//                if(cPagina == 14 && cSiguie == 0){
//                    vLsvPart14.startAnimation(vAsiguie2);
//                    vLsvPart14.setVisibility(View.GONE);
//                    vLsvPart15.startAnimation(vAsiguie);
//                    vLsvPart15.setVisibility(View.VISIBLE);
//                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
//                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn16));
//                    vLtvSiguie.setText(getResources().getString(R.string.formul_finali));
//                    cPagina = 15;
//                    cSiguie = 1;
//                }
            }
        });

    }

    private void MSerGetTokens(){

        StringRequest jsonObjRequest = new StringRequest(

                Request.Method.POST,
                vDstDirWeb.getDirWebApiUrl() + "/token",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myObject = new JSONObject(response);
                            String Tokens = myObject.getString("access_token");
                            vTokens = Tokens;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthor());
                params.put("Content-Type", vDstDirWeb.getDirWebConTyp());
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", vDstDirWeb.getDirWebUseNam());
                params.put("password", vDstDirWeb.getDirWebPasswo());
                params.put("grant_type", vDstDirWeb.getDirWebGraTyp());
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                2500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    private void MEdiSerFecNac(){

        Calendar vLcaF00P06 = Calendar.getInstance();

        int vLinF00P06Y = vLcaF00P06.get(Calendar.YEAR);
        int vLinF00P06M = vLcaF00P06.get(Calendar.MONTH);
        int vLinF00P06D = vLcaF00P06.get(Calendar.DAY_OF_MONTH);

        vLetF00P06.setText(vLinF00P06D + "/" + ((vLinF00P06M + 1) < 10 ? "0" + (vLinF00P06M + 1) : "" + (vLinF00P06M + 1)) + "/" + vLinF00P06Y);

        DatePickerDialog vLdpF00P06 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetF00P06.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinF00P06Y, vLinF00P06M, vLinF00P06D);

        Calendar vLcaF00P06N = Calendar.getInstance();
        vLcaF00P06N.add(Calendar.MINUTE, +1);

        vLdpF00P06.getDatePicker().setMaxDate(vLcaF00P06N.getTimeInMillis());

        vLetF00P06.setInputType(InputType.TYPE_NULL);
        vLetF00P06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(RegistActivity.this);
                vLdpF00P06.show();
            }
        });

    }

    private void MWebSerConPac(){

        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET,
                vDstDirWeb.getDirWebApiUrl() + Constants.URL_PACIENTE_CONFIRMADO + vTipDoc + "/" + vLetF00P02.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Jdatos_response = new JSONObject(response);
                            if(Jdatos_response.getString("cod_respuesta").equals("0000")){
                                JSONObject Jdatos_paciente = Jdatos_response.getJSONObject("paciente");
                                vLetF00P03.setText(Jdatos_paciente.getString("nombre"));
                                vLetF00P04.setText(Jdatos_paciente.getString("ape_paterno"));
                                vLetF00P05.setText(Jdatos_paciente.getString("ape_materno"));
                                vLetF00P03.setInputType(InputType.TYPE_NULL);
                                vLetF00P04.setInputType(InputType.TYPE_NULL);
                                vLetF00P05.setInputType(InputType.TYPE_NULL);
                                vLetF00P03.setEnabled(false);
                                vLetF00P04.setEnabled(false);
                                vLetF00P05.setEnabled(false);
                            }else{
                                Toast.makeText(RegistActivity.this, "No existe el registro de la persona", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthorL() + vTokens);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                2500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    private void MWebSerTokPac(){

        StringRequest jsonObjRequest = new StringRequest(

                Request.Method.POST,
                vDstDirWeb.getDirWebApiUrl() + "/token",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myObject = new JSONObject(response);
                            String Tokens = myObject.getString("access_token");
                            vTokens = Tokens;
                            MWebSerConPac();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", vDstDirWeb.getDirWebAuthor());
                params.put("Content-Type", vDstDirWeb.getDirWebConTyp());
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", vDstDirWeb.getDirWebUseNam());
                params.put("password", vDstDirWeb.getDirWebPasswo());
                params.put("grant_type", vDstDirWeb.getDirWebGraTyp());
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                2500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    private void MEdiSerNroDoc(){

//        vLetF00P02.setInputType(InputType.TYPE_NULL);
//        vLetF00P02.setEnabled(false);
//        vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(0) });

        vLetF00P02.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    MWebSerTokPac();
                }
            }
        });

        vLetF00P02.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    MWebSerTokPac();
                    return true;
                }
                return false;
            }
        });

    }

    private void MDbsSerSintom(){

        vLisSintom.clear();

        vCursorSintom = mPrinciDbHelper.getAllPrinci(SintomDbHelper.SintomTableC.SintomTableN);
        while(vCursorSintom.moveToNext()){
            SintomDataSet vDstSintom = new SintomDataSet();
            vDstSintom.setId(vCursorSintom.getInt(vCursorSintom.getColumnIndex(SintomDbHelper.SintomTableC.SintomIdenti)));
            vDstSintom.setDescripcion((vCursorSintom.getString(vCursorSintom.getColumnIndex(SintomDbHelper.SintomTableC.SintomDescri))).toUpperCase());
            vLisSintom.add(vDstSintom);
        }

        vALiSintom.clear();
        for (SintomDataSet o : vLisSintom) {
            vALiSintom.add(o.getDescripcion());
        }

        String[] vArrF00P21 = new String[vALiSintom.size()];

        vArrF00P21 = vALiSintom.toArray(vArrF00P21);

        String[] finalVArrF00P21 = vArrF00P21;
        boolean[] vArrF00P21boolean = new boolean[vArrF00P21.length];
        for (int i = 0; i < vArrF00P21.length; i++) {
            vArrF00P21boolean[i] = false;
        }

        vLtvF00P21.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren21))
                        .setMultiChoiceItems(finalVArrF00P21, vArrF00P21boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrF00P21boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P21.setText(" ");
                                iPreF00P21 = 0;
                                for (int i = 0; i < finalVArrF00P21.length; i++) {
                                    if (vArrF00P21boolean[i]) {
                                        if(iPreF00P21 == 0){
                                            vLtvF00P21.setText(finalVArrF00P21[i]);
                                            iPreF00P21 = 1;
                                        }else{
                                            vLtvF00P21.setText(vLtvF00P21.getText().toString() + "\n" + finalVArrF00P21[i]);
                                            iPreF00P21 = 1;
                                        }
                                    }
                                    if(finalVArrF00P21[i].equals(getResources().getString(R.string.formul_opcn00_pren21_01))){
                                        if (vArrF00P21boolean[i]) {
                                            vLllF00P22.setVisibility(View.VISIBLE);
                                            vLllF00P22.requestFocus();
                                        } else {
                                            vLllF00P22.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                if(iPreF00P21 == 0){
                                    vLtvF00P21.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MStrSerSexBio(){

        vLisSexBio.clear();

        vCursorSexBio = mPrinciDbHelper.getAllPrinci(SexBioDbHelper.SexBioTableC.SexBioTableN);
        while(vCursorSexBio.moveToNext()){
            SexBioDataSet vDstSexBio = new SexBioDataSet();
            vDstSexBio.setSexBioIdenti(vCursorSexBio.getString(vCursorSexBio.getColumnIndex(SexBioDbHelper.SexBioTableC.SexBioIdenti)));
            vDstSexBio.setSexBioDescri((vCursorSexBio.getString(vCursorSexBio.getColumnIndex(SexBioDbHelper.SexBioTableC.SexBioDescri))).toUpperCase());
            vLisSexBio.add(vDstSexBio);
        }

        vALiSexBio.clear();
        for (SexBioDataSet o : vLisSexBio) {
            vALiSexBio.add(o.getSexBioDescri());
        }

        String[] vArrF00P07 = new String[vALiSexBio.size()];

        vArrF00P07 = vALiSexBio.toArray(vArrF00P07);

        String[] finalVArrF00P07 = vArrF00P07;

        vLtvF00P07.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren07))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF00P07, iPreF00P07, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF00P07 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P07.setText(finalVArrF00P07[iPreF00P07]);
                                for (SexBioDataSet o : vLisSexBio) {
                                    if(o.getSexBioDescri().equals(finalVArrF00P07[iPreF00P07])){
                                        vProfes = o.getSexBioIdenti();
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

//        String[] vArrF00P07 = getResources().getStringArray(R.array.formul_opcn00_pren07);
//
//        vLtvF00P07.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
//                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren07))
//                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
//                        .setSingleChoiceItems(vArrF00P07, iPreF00P07, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                iPreF00P07 = which;
//                            }
//                        })
//                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                vLtvF00P07.setText(vArrF00P07[iPreF00P07]);
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
//                builder.create().show();
//            }
//        });

    }

    private void MStrSerTieSin(){

        vLisTieSin.clear();

        vCursorTieSin = mPrinciDbHelper.getAllPrinci(TieSinDbHelper.TieSinTableC.TieSinTableN);
        while(vCursorTieSin.moveToNext()){
            TieSinDataSet vDstTieSin = new TieSinDataSet();
            vDstTieSin.setTieSinIdenti(vCursorTieSin.getString(vCursorTieSin.getColumnIndex(TieSinDbHelper.TieSinTableC.TieSinIdenti)));
            vDstTieSin.setTieSinDescri((vCursorTieSin.getString(vCursorTieSin.getColumnIndex(TieSinDbHelper.TieSinTableC.TieSinDescri))).toUpperCase());
            vLisTieSin.add(vDstTieSin);
        }

        vALiTieSin.clear();
        for (TieSinDataSet o : vLisTieSin) {
            vALiTieSin.add(o.getTieSinDescri());
        }

        String[] vArrF00P19 = new String[vALiTieSin.size()];

        vArrF00P19 = vALiTieSin.toArray(vArrF00P19);

        String[] finalVArrF00P19 = vArrF00P19;

        String[] finalVArrF00P19identi = getResources().getStringArray(R.array.formul_opcn00_pren19_identi);

        vLtvF00P19.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren19))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF00P19, iPreF00P19, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF00P19 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P19.setText(finalVArrF00P19[iPreF00P19]);
                                for (TieSinDataSet o : vLisTieSin) {
                                    if(o.getTieSinDescri().equals(finalVArrF00P19[iPreF00P19])){
                                        vTieSin = o.getTieSinIdenti();
                                        if(o.getTieSinDescri().equals(finalVArrF00P19identi[0])){
                                            vLllF00P20.setVisibility(View.VISIBLE);
                                            vLllF00P21.setVisibility(View.VISIBLE);
                                        }else{
                                            vLllF00P20.setVisibility(View.GONE);
                                            vLllF00P21.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

//        String[] vArrF00P19 = getResources().getStringArray(R.array.formul_opcn00_pren19);
//
//        vLtvF00P19.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
//                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren19))
//                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
//                        .setSingleChoiceItems(vArrF00P19, iPreF00P19, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                iPreF00P19 = which;
//                            }
//                        })
//                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                vLtvF00P19.setText(vArrF00P19[iPreF00P19]);
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
//                builder.create().show();
//            }
//        });

    }

    private void MStrSerPerSal(){

        vLisPerSal.clear();

        vCursorPerSal = mPrinciDbHelper.getAllPrinci(PerSalDbHelper.PerSalTableC.PerSalTableN);
        while(vCursorPerSal.moveToNext()){
            PerSalDataSet vDstPerSal = new PerSalDataSet();
            vDstPerSal.setPerSalIdenti(vCursorPerSal.getString(vCursorPerSal.getColumnIndex(PerSalDbHelper.PerSalTableC.PerSalIdenti)));
            vDstPerSal.setPerSalDescri((vCursorPerSal.getString(vCursorPerSal.getColumnIndex(PerSalDbHelper.PerSalTableC.PerSalDescri))).toUpperCase());
            vLisPerSal.add(vDstPerSal);
        }

        vALiPerSal.clear();
        for (PerSalDataSet o : vLisPerSal) {
            vALiPerSal.add(o.getPerSalDescri());
        }

        String[] vArrF00P17 = new String[vALiPerSal.size()];

        vArrF00P17 = vALiPerSal.toArray(vArrF00P17);

        String[] finalVArrF00P17 = vArrF00P17;

        String[] finalVArrF00P17identi = getResources().getStringArray(R.array.formul_opcn00_pren17_identi);

        vLtvF00P17.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren17))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF00P17, iPreF00P17, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF00P17 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P17.setText(finalVArrF00P17[iPreF00P17]);
                                for (PerSalDataSet o : vLisPerSal) {
                                    if(o.getPerSalDescri().equals(finalVArrF00P17[iPreF00P17])){
                                        vPerSal = o.getPerSalIdenti();
                                        if(o.getPerSalIdenti().equals(finalVArrF00P17identi[0])){
                                            vLllF00P18.setVisibility(View.VISIBLE);
                                        }else{
                                            vLllF00P18.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

//        String[] vArrF00P17 = getResources().getStringArray(R.array.formul_opcn00_pren17);
//
//        vLtvF00P17.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
//                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren17))
//                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
//                        .setSingleChoiceItems(vArrF00P17, iPreF00P17, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                iPreF00P17 = which;
//                            }
//                        })
//                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                vLtvF00P17.setText(vArrF00P17[iPreF00P17]);
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
//                builder.create().show();
//            }
//        });

    }

    private void MStrSerTipViv(){

        vLisTipViv.clear();

        vCursorTipViv = mPrinciDbHelper.getAllPrinci(TipVivDbHelper.TipVivTableC.TipVivTableN);
        while(vCursorTipViv.moveToNext()){
            TipVivDataSet vDstTipViv = new TipVivDataSet();
            vDstTipViv.setTipVivIdenti(vCursorTipViv.getString(vCursorTipViv.getColumnIndex(TipVivDbHelper.TipVivTableC.TipVivIdenti)));
            vDstTipViv.setTipVivDescri((vCursorTipViv.getString(vCursorTipViv.getColumnIndex(TipVivDbHelper.TipVivTableC.TipVivDescri))).toUpperCase());
            vLisTipViv.add(vDstTipViv);
        }

        vALiTipViv.clear();
        for (TipVivDataSet o : vLisTipViv) {
            vALiTipViv.add(o.getTipVivDescri());
        }

        String[] vArrF00P11 = new String[vALiTipViv.size()];

        vArrF00P11 = vALiTipViv.toArray(vArrF00P11);

        String[] finalVArrF00P11 = vArrF00P11;

        vLtvF00P11.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren11))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF00P11, iPreF00P11, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF00P11 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P11.setText(finalVArrF00P11[iPreF00P11]);
                                for (TipVivDataSet o : vLisTipViv) {
                                    if(o.getTipVivDescri().equals(finalVArrF00P11[iPreF00P11])){
                                        vProfes = o.getTipVivIdenti();
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

//        String[] vArrF00P11 = getResources().getStringArray(R.array.formul_opcn00_pren11);
//
//        vLtvF00P11.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
//                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren11))
//                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
//                        .setSingleChoiceItems(vArrF00P11, iPreF00P11, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                iPreF00P11 = which;
//                            }
//                        })
//                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                vLtvF00P11.setText(vArrF00P11[iPreF00P11]);
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
//                builder.create().show();
//            }
//        });

    }

    private void MDbsSerTipDoc(){

        vLisTipDoc.clear();

        vCursorTipDoc = mPrinciDbHelper.getAllPrinci(TipDocDbHelper.TipDocTableC.TipDocTableN);
        while(vCursorTipDoc.moveToNext()){
            TipDocDataSet vDstTipDoc = new TipDocDataSet();
            vDstTipDoc.setTipDocIdenti(vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocIdenti)));
            vDstTipDoc.setTipDocDescri((vCursorTipDoc.getString(vCursorTipDoc.getColumnIndex(TipDocDbHelper.TipDocTableC.TipDocDescri))).toUpperCase());
            vLisTipDoc.add(vDstTipDoc);
        }

        vALiTipDoc.clear();
        for (TipDocDataSet o : vLisTipDoc) {
            vALiTipDoc.add(o.getTipDocDescri());
        }

        String[] vArrF00P01 = new String[vALiTipDoc.size()];

        vArrF00P01 = vALiTipDoc.toArray(vArrF00P01);

        String[] finalVArrF00P01 = vArrF00P01;

        vArrTipDoc = finalVArrF00P01;

        vLtvF00P01.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren01))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF00P01, iPreF00P01, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF00P01 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P01.setText(finalVArrF00P01[iPreF00P01]);
                                for (TipDocDataSet o : vLisTipDoc) {
                                    if(o.getTipDocDescri().equals(finalVArrF00P01[iPreF00P01])){
                                        vTipDoc = o.getTipDocIdenti();
                                    }
                                }
                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_01))){
//                                    vLetF00P02.setText("");
                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    vLetF00P02.setEnabled(true);
                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
                                }
                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_02))){
//                                    vLetF00P02.setText("");
                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    vLetF00P02.setEnabled(true);
                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
                                }
                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_03))){
//                                    vLetF00P02.setText("");
                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_TEXT);
                                    vLetF00P02.setEnabled(true);
                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                                }
                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_04))){
//                                    vLetF00P02.setText("");
                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_TEXT);
                                    vLetF00P02.setEnabled(true);
                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                                }
                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_05))){
//                                    vLetF00P02.setText("");
                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_TEXT);
                                    vLetF00P02.setEnabled(true);
                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                                }
                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_06))){
//                                    vLetF00P02.setText("");
                                    vLetF00P02.setInputType(InputType.TYPE_NULL);
                                    vLetF00P02.setEnabled(false);
                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(0) });
                                }
//                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_01))){
//                                    vLetF00P02.setText("");
//                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_NUMBER);
//                                    vLetF00P02.setEnabled(true);
//                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
//                                }
//                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_02))){
//                                    vLetF00P02.setText("");
//                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_NUMBER);
//                                    vLetF00P02.setEnabled(true);
//                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
//                                }
//                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_03))){
//                                    vLetF00P02.setText("");
//                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_TEXT);
//                                    vLetF00P02.setEnabled(true);
//                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
//                                }
//                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_04))){
//                                    vLetF00P02.setText("");
//                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_TEXT);
//                                    vLetF00P02.setEnabled(true);
//                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
//                                }
//                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_05))){
//                                    vLetF00P02.setText("");
//                                    vLetF00P02.setInputType(InputType.TYPE_CLASS_TEXT);
//                                    vLetF00P02.setEnabled(true);
//                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
//                                }
//                                if(finalVArrF00P01[iPreF00P01].equals(getResources().getString(R.string.formul_opcn00_pren01_06))){
//                                    vLetF00P02.setText("");
//                                    vLetF00P02.setInputType(InputType.TYPE_NULL);
//                                    vLetF00P02.setEnabled(false);
//                                    vLetF00P02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(0) });
//                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MDbsSerProfes(){

        vLisProfes.clear();

        vCursorProfes = mPrinciDbHelper.getAllPrinci(ProfesDbHelper.ProfesTableC.ProfesTableN);
        while(vCursorProfes.moveToNext()){
            ProfesDataSet vDstProfes = new ProfesDataSet();
            vDstProfes.setProfesIdenti(vCursorProfes.getInt(vCursorProfes.getColumnIndex(ProfesDbHelper.ProfesTableC.ProfesIdenti)));
            vDstProfes.setProfesDescri((vCursorProfes.getString(vCursorProfes.getColumnIndex(ProfesDbHelper.ProfesTableC.ProfesDescri))).toUpperCase());
            vLisProfes.add(vDstProfes);
        }

        vALiProfes.clear();
        for (ProfesDataSet o : vLisProfes) {
            vALiProfes.add(o.getProfesDescri());
        }

        String[] vArrF00P18 = new String[vALiProfes.size()];

        vArrF00P18 = vALiProfes.toArray(vArrF00P18);

        String[] finalVArrF00P18 = vArrF00P18;

        vLtvF00P18.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren18))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF00P18, iPreF00P18, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF00P18 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P18.setText(finalVArrF00P18[iPreF00P18]);
                                for (ProfesDataSet o : vLisProfes) {
                                    if(o.getProfesDescri().equals(finalVArrF00P18[iPreF00P18])){
                                        vProfes = String.valueOf(o.getProfesIdenti());
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    private void MDbsSerUbigeo(){

        vLisDepart.clear();

        vCursorDepart = mPrinciDbHelper.getAllPrinci(DepartDbHelper.DepartTableC.DepartTableN);
        while(vCursorDepart.moveToNext()){
            DepartDataSet vDstDepart = new DepartDataSet();
            vDstDepart.setDepartCodigo(vCursorDepart.getString(vCursorDepart.getColumnIndex(DepartDbHelper.DepartTableC.DepartCodigo)));
            vDstDepart.setDepartNombre(vCursorDepart.getString(vCursorDepart.getColumnIndex(DepartDbHelper.DepartTableC.DepartDescri)));
            vLisDepart.add(vDstDepart);
        }

        vALiDepart.clear();
        for (DepartDataSet o : vLisDepart) {
            vALiDepart.add(o.getDepartNombre());
        }

        String[] vArrF00P13 = new String[vALiDepart.size()];

        vArrF00P13 = vALiDepart.toArray(vArrF00P13);

        String[] finalVArrF00P13 = vArrF00P13;

        vLtvF00P13.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren13))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrF00P13, iPreF00P13, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreF00P13 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvF00P13.setText(finalVArrF00P13[iPreF00P13]);

                                for (DepartDataSet o : vLisDepart) {
                                    if(o.getDepartNombre().equals(finalVArrF00P13[iPreF00P13])){
                                        vDepart = o.getDepartCodigo();
                                    }
                                }

                                vLisProvin.clear();

                                vCursorProvin = mPrinciDbHelper.getAllPrinciOne(ProvinDbHelper.ProvinTableC.ProvinTableN, ProvinDbHelper.ProvinTableC.DepartCodigo, vDepart);
                                while(vCursorProvin.moveToNext()){
                                    ProvinDataSet vDstProvin = new ProvinDataSet();
                                    vDstProvin.setProvinCodigo(vCursorProvin.getString(vCursorProvin.getColumnIndex(ProvinDbHelper.ProvinTableC.ProvinCodigo)));
                                    vDstProvin.setProvinNombre(vCursorProvin.getString(vCursorProvin.getColumnIndex(ProvinDbHelper.ProvinTableC.ProvinDescri)));
                                    vLisProvin.add(vDstProvin);
                                }

                                vALiProvin.clear();
                                for (ProvinDataSet o : vLisProvin) {
                                    vALiProvin.add(o.getProvinNombre());
                                }

                                String[] vArrF00P14 = new String[vALiProvin.size()];

                                vArrF00P14 = vALiProvin.toArray(vArrF00P14);

                                String[] finalVArrF00P14 = vArrF00P14;

                                vLtvF00P14.setOnClickListener(new AdapterView.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                                        builder.setTitle(getResources().getString(R.string.formul_opcn00_pren14))
                                                //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                                                .setSingleChoiceItems(finalVArrF00P14, iPreF00P14, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        iPreF00P14 = which;
                                                    }
                                                })
                                                .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        vLtvF00P14.setText(finalVArrF00P14[iPreF00P14]);

                                                        for (ProvinDataSet o : vLisProvin) {
                                                            if(o.getProvinNombre().equals(finalVArrF00P14[iPreF00P14])){
                                                                vProvin = o.getProvinCodigo();
                                                            }
                                                        }

                                                        vLisDistri.clear();

                                                        vCursorDistri = mPrinciDbHelper.getAllPrinciTwo(DistriDbHelper.DistriTableC.DistriTableN, DistriDbHelper.DistriTableC.DepartCodigo, vDepart, DistriDbHelper.DistriTableC.ProvinCodigo, vProvin);
                                                        while(vCursorDistri.moveToNext()){
                                                            DistriDataSet vDstDistri = new DistriDataSet();
                                                            vDstDistri.setDistriCodigo(vCursorDistri.getString(vCursorDistri.getColumnIndex(DistriDbHelper.DistriTableC.DistriCodigo)));
                                                            vDstDistri.setDistriNombre(vCursorDistri.getString(vCursorDistri.getColumnIndex(DistriDbHelper.DistriTableC.DistriDescri)));
                                                            vLisDistri.add(vDstDistri);
                                                        }

                                                        vALiDistri.clear();
                                                        for (DistriDataSet o : vLisDistri) {
                                                            vALiDistri.add(o.getDistriNombre());
                                                        }

                                                        String[] vArrF00P15 = new String[vALiDistri.size()];

                                                        vArrF00P15 = vALiDistri.toArray(vArrF00P15);

                                                        String[] finalVArrF00P15 = vArrF00P15;

                                                        vLtvF00P15.setOnClickListener(new AdapterView.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);
                                                                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren15))
                                                                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                                                                        .setSingleChoiceItems(finalVArrF00P15, iPreF00P15, new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                iPreF00P15 = which;
                                                                            }
                                                                        })
                                                                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                vLtvF00P15.setText(finalVArrF00P15[iPreF00P15]);
                                                                                for (DistriDataSet o : vLisDistri) {
                                                                                    if(o.getDistriNombre().equals(finalVArrF00P15[iPreF00P15])){
                                                                                        vDistri = o.getDistriCodigo();
                                                                                    }
                                                                                }
                                                                                dialog.dismiss();
                                                                            }
                                                                        })
                                                                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                                                                builder.create().show();
                                                            }
                                                        });

                                                        dialog.dismiss();
                                                    }
                                                })
                                                .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                                        builder.create().show();
                                    }
                                });

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    if(arg0.equalsIgnoreCase(DirWebDataSet.DirWebSLL))
                        return true;
                    else
                        return false;
                }
            });
        } catch (Exception ignored) {
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private String formatNumber(int n){
        return n < 10 ? "0"+n : n+"";
    }

}
