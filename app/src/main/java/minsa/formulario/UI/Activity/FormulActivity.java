package minsa.formulario.UI.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minsa.formulario.AppController;
import minsa.formulario.DataSet.DepartDataSet;
import minsa.formulario.DataSet.DirWebDataSet;
import minsa.formulario.DataSet.DistriDataSet;
import minsa.formulario.DataSet.ProvinDataSet;
import minsa.formulario.DataSet.UbicacDataSet;
import minsa.formulario.DbHelper.DepartDbHelper;
import minsa.formulario.DbHelper.DistriDbHelper;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.DbHelper.ProvinDbHelper;
import minsa.formulario.R;

public class FormulActivity extends AppCompatActivity {
    private Spinner vLspPreN01;
    private TextView vLtvPreN01;
    private EditText vLetPreN02;
    private EditText vLetPreN03;
    private EditText vLetPreN04;
    private EditText vLetPreN05;
    private EditText vLetPreN06;
    private EditText vLetPreN07;
    private TextView vLtvPreN08;
    private Spinner vLspPreN08;
    private EditText vLetPreN09;
    private TextView vLtvPreN10;
    private TextView vLtvPreN11;
    private TextView vLtvPreN12;
    private TextView vLtvPreN13;
    private TextView vLtvPreN14;
    private TextView vLtvPreN15;
    private TextView vLtvPreN16;
    private EditText vLetPreN17;
    private TextView vLtvPreN18;
    private EditText vLetPreN19;
    private TextView vLtvPreN20;
    private TextView vLtvPreN21;
    private TextView vLtvPreN22;
    private TextView vLtvPreN23;
    private TextView vLtvPreN24;
    private TextView vLtvPreN24a;
    private EditText vLetPreN26;
    private TextView vLtvPreN27;
    private EditText vLetPreN28;
    private EditText vLetPreN29;
    private EditText vLetPreN31;
    private EditText vLetPreN32;
    private TextView vLtvPreN33;
    private TextView vLtvPreN34;
    private EditText vLetPreN35;
    private TextView vLtvPreN38;
    private EditText vLetPreN39;
    private TextView vLtvPreN40;
    private EditText vLetPreN41;
    private TextView vLtvPreN42;
    private TextView vLtvPreN43;
    private TextView vLtvPreN43a;
    private TextView vLtvPreN44;
    private TextView vLtvPreN44aa;
    private TextView vLtvPreN44ab;
    private TextView vLtvPreN44ac;
    private TextView vLtvPreN45;
    private EditText vLetPreN46;
    private TextView vLtvPreN47;
    private TextView vLtvPreN48;
    private TextView vLtvPreN48a;
    private TextView vLtvPreN48b;
    private TextView vLtvPreN48c;
    private TextView vLtvPreN48d;
    private TextView vLtvPreN48e;
    private TextView vLtvPreN48f;
    private TextView vLtvPreN48g;
    private TextView vLtvPreN48h;
    private TextView vLtvPreN48i;
    private TextView vLtvPreN48j;
    private TextView vLtvPreN49;
    private EditText vLetPreN50;
    private EditText vLetPreN51;
    private EditText vLetPreN52;
    private EditText vLetPreN53;
    private EditText vLetPreN54;
    private TextView vLtvPreN55;
    private EditText vLetPreN56;
    private EditText vLetPreN57;
    private EditText vLetPreN58;
    private EditText vLetPreN59;
    private TextView vLtvPreN60;
    private TextView vLtvPreN61;
    private TextView vLtvPreN62;
    private TextView vLtvPreN63;
    private TextView vLtvPreN18k;
    private List<DepartDataSet> vLisDepart;
    private List<ProvinDataSet> vLisProvin;
    private List<DistriDataSet> vLisDistri;
    private ArrayList<String> vALiDepart;
    private ArrayList<String> vALiProvin;
    private ArrayList<String> vALiDistri;
    private String vDepart;
    private String vProvin;
    private String vDistri;
    private PrinciDbHelper mPrinciDbHelper;
    private Cursor vCursorDepart;
    private Cursor vCursorProvin;
    private Cursor vCursorDistri;

    private TextView vLtvTitulo;
    private TextView vLtvSubTit;

    private ScrollView vLsvPart01;
    private ScrollView vLsvPart02;
    private ScrollView vLsvPart03;
    private ScrollView vLsvPart04;
    private ScrollView vLsvPart05;
    private ScrollView vLsvPart06;
    private ScrollView vLsvPart07;
    private ScrollView vLsvPart08;
    private ScrollView vLsvPart09;
    private ScrollView vLsvPart10;
    private ScrollView vLsvPart11;
    private ScrollView vLsvPart12;
    private ScrollView vLsvPart13;
    private ScrollView vLsvPart14;
    private ScrollView vLsvPart15;
    private TextView vLtvAnteri;
    private TextView vLtvSiguie;

    private LinearLayout vLllPreN01;
    private LinearLayout vLllPreN02;
    private LinearLayout vLllPreN03;
    private LinearLayout vLllPreN04;
    private LinearLayout vLllPreN05;
    private LinearLayout vLllPreN06;
    private LinearLayout vLllPreN07;
    private LinearLayout vLllPreN08;
    private LinearLayout vLllPreN09;
    private LinearLayout vLllPreN10;
    private LinearLayout vLllPreN11;
    private LinearLayout vLllPreN12;
    private LinearLayout vLllPreN13;
    private LinearLayout vLllPreN14;
    private LinearLayout vLllPreN15;
    private LinearLayout vLllPreN16;
    private LinearLayout vLllPreN17;
    private LinearLayout vLllPreN18;
    private LinearLayout vLllPreN18a;
    private LinearLayout vLllPreN18b;
    private LinearLayout vLllPreN19;
    private LinearLayout vLllPreN20;
    private LinearLayout vLllPreN21;
    private LinearLayout vLllPreN22;
    private LinearLayout vLllPreN22a;
    private LinearLayout vLllPreN23;
    private LinearLayout vLllPreN24;
    private LinearLayout vLllPreN24a;
    private LinearLayout vLllPreN25;
    private LinearLayout vLllPreN26;
    private LinearLayout vLllPreN27;
    private LinearLayout vLllPreN28;
    private LinearLayout vLllPreN29;
    private LinearLayout vLllPreN30;
    private LinearLayout vLllPreN31;
    private LinearLayout vLllPreN32;
    private LinearLayout vLllPreN33;
    private LinearLayout vLllPreN34;
    private LinearLayout vLllPreN35;
    private LinearLayout vLllPreN36;
    private LinearLayout vLllPreN37;
    private LinearLayout vLllPreN38;
    private LinearLayout vLllPreN39;
    private LinearLayout vLllPreN40;
    private LinearLayout vLllPreN41;
    private LinearLayout vLllPreN42;
    private LinearLayout vLllPreN42a;
    private LinearLayout vLllPreN43;
    private LinearLayout vLllPreN43a;
    private LinearLayout vLllPreN43b;
    private LinearLayout vLllPreN44;
    private LinearLayout vLllPreN44a;
    private LinearLayout vLllPreN45;
    private LinearLayout vLllPreN46;
    private LinearLayout vLllPreN47;
    private LinearLayout vLllPreN48;
    private LinearLayout vLllPreN48a;
    private LinearLayout vLllPreN48b;
    private LinearLayout vLllPreN48c;
    private LinearLayout vLllPreN48d;
    private LinearLayout vLllPreN48e;
    private LinearLayout vLllPreN48f;
    private LinearLayout vLllPreN48g;
    private LinearLayout vLllPreN48h;
    private LinearLayout vLllPreN48i;
    private LinearLayout vLllPreN48j;
    private LinearLayout vLllPreN49;
    private LinearLayout vLllPreN50;
    private LinearLayout vLllPreN51;
    private LinearLayout vLllPreN52;
    private LinearLayout vLllPreN53;
    private LinearLayout vLllPreN54;
    private LinearLayout vLllPreN55;
    private LinearLayout vLllPreN56;
    private LinearLayout vLllPreN57;
    private LinearLayout vLllPreN58;
    private LinearLayout vLllPreN59;
    private LinearLayout vLllPreN60;
    private LinearLayout vLllPreN61;
    private LinearLayout vLllPreN61a;
    private LinearLayout vLllPreN62;
    private LinearLayout vLllPreN62a;
    private LinearLayout vLllPreN63;

    private String vLstPreN31E;

    //Animación de Botones
    private Animation vAanteri;
    private Animation vAanteri2;
    private Animation vAsiguie;
    private Animation vAsiguie2;

    //Ubicación de la Página
    private int cPagina;
    private int cAnteri;
    private int cSiguie;

    private int iPreN01 = 0;
    private int iPreN08 = 0;
    private int iPreN10 = 0;
    private int iPreN11 = 0;
    private int iPreN12 = 0;
    private int iPreN14 = 0;
    private int iPreN15 = 0;
    private int iPreN16 = 0;
    private int iPreN18 = 0;
    private int iPreN18k = 0;
    private int iPreN20 = 0;
    private int iPreN21 = 0;
    private int iPreN22 = 0;
    private int iPreN23 = 0;
    private int iPreN24 = 0;
    private int iPreN27 = 0;
    private int iPreN33 = 0;
    private int iPreN34 = 0;
    private int iPreN38 = 0;
    private int iPreN40 = 0;
    private int iPreN42 = 0;
    private int iPreN43 = 0;
    private int iPreN43a = 0;
    private int iPreN44 = 0;
    private int iPreN45 = 0;
    private int iPreN47 = 0;
    private int iPreN48 = 0;
    private int iPreN49 = 0;
    private int iPreN55 = 0;
    private int iPreN60 = 0;
    private int iPreN61 = 0;
    private int iPreN62 = 0;
    private int iPreN63 = 0;

    private String vPren44aa = "";
    private String vPren44ab = "";
    private String vPren44ba = "";
    private String vPren44bb = "";
    private String vPren44ca = "";
    private String vPren44cb = "";

    private String vPren48aa = "";
    private String vPren48ab = "";
    private String vPren48ac = "9";
    private boolean vPren48ada = false;
    private boolean vPren48adb = false;
    private boolean vPren48adc = false;
    private boolean vPren48add = false;
    private boolean vPren48ade = false;
    private boolean vPren48adf = false;
    private boolean vPren48adg = false;
    private boolean vPren48adh = false;
    private boolean vPren48adi = false;
    private boolean vPren48adj = false;
    private boolean vPren48adk = false;
    private boolean vPren48adl = false;
    private boolean vPren48adm = false;
    private boolean vPren48aea = false;
    private boolean vPren48aeb = false;
    private boolean vPren48aec = false;
    private boolean vPren48aed = false;
    private boolean vPren48aee = false;
    private boolean vPren48aef = false;
    private boolean vPren48aeg = false;
    private boolean vPren48aeh = false;
    private boolean vPren48aei = false;
    private boolean vPren48aej = false;
    private boolean vPren48aek = false;
    private boolean vPren48ael = false;
    private boolean vPren48aem = false;
    private boolean vPren48aen = false;

    private UbicacDataSet vDsUbicac = UbicacDataSet.getInstance();

    private DirWebDataSet vDstDirWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formul_activity);

//        vLspPreN01 = (Spinner) findViewById(R.id.LspPreN01);
        vLtvPreN01 = (TextView) findViewById(R.id.LtvPreN01);
        vLetPreN02 = (EditText) findViewById(R.id.LetPreN02);
        vLetPreN03 = (EditText) findViewById(R.id.LetPreN03);
        vLetPreN03.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vLetPreN04 = (EditText) findViewById(R.id.LetPreN04);
        vLetPreN04.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vLetPreN05 = (EditText) findViewById(R.id.LetPreN05);
        vLetPreN05.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vLetPreN06 = (EditText) findViewById(R.id.LetPreN06);
        vLetPreN06.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vLetPreN07 = (EditText) findViewById(R.id.LetPreN07);
//        vLspPreN08 = (Spinner) findViewById(R.id.LspPreN08);
        vLtvPreN08 = (TextView) findViewById(R.id.LtvPreN08);
        vLetPreN09 = (EditText) findViewById(R.id.LetPreN09);
        vLetPreN09.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        vLspPreN10 = (Spinner) findViewById(R.id.LspPreN10);
//        vLspPreN11 = (Spinner) findViewById(R.id.LspPreN11);
//        vLspPreN12 = (Spinner) findViewById(R.id.LspPreN12);
        vLtvPreN10 = (TextView) findViewById(R.id.LtvPreN10);
        vLtvPreN11 = (TextView) findViewById(R.id.LtvPreN11);
        vLtvPreN12 = (TextView) findViewById(R.id.LtvPreN12);
        vLtvPreN13 = (TextView) findViewById(R.id.LtvPreN13);
        vLtvPreN14 = (TextView) findViewById(R.id.LtvPreN14);
        vLtvPreN15 = (TextView) findViewById(R.id.LtvPreN15);
        vLtvPreN16 = (TextView) findViewById(R.id.LtvPreN16);
        vLetPreN17 = (EditText) findViewById(R.id.LetPreN17);
        vLtvPreN18 = (TextView) findViewById(R.id.LtvPreN18);
        vLtvPreN18k = (TextView) findViewById(R.id.LtvPreN18k);
        vLetPreN19 = (EditText) findViewById(R.id.LetPreN19);
        vLtvPreN20 = (TextView) findViewById(R.id.LtvPreN20);
        vLtvPreN21 = (TextView) findViewById(R.id.LtvPreN21);
        vLtvPreN22 = (TextView) findViewById(R.id.LtvPreN22);
        vLtvPreN23 = (TextView) findViewById(R.id.LtvPreN23);
        vLtvPreN24 = (TextView) findViewById(R.id.LtvPreN24);
        vLtvPreN24a = (TextView) findViewById(R.id.LtvPreN24a);
        vLetPreN26 = (EditText) findViewById(R.id.LetPreN26);
        vLtvPreN27 = (TextView) findViewById(R.id.LtvPreN27);
        vLetPreN28 = (EditText) findViewById(R.id.LetPreN28);
        vLetPreN29 = (EditText) findViewById(R.id.LetPreN29);
        vLetPreN31 = (EditText) findViewById(R.id.LetPreN31);
        vLetPreN32 = (EditText) findViewById(R.id.LetPreN32);
        vLtvPreN33 = (TextView) findViewById(R.id.LtvPreN33);
        vLtvPreN34 = (TextView) findViewById(R.id.LtvPreN34);
        vLetPreN35 = (EditText) findViewById(R.id.LetPreN35);
        vLtvPreN38 = (TextView) findViewById(R.id.LtvPreN38);
        vLetPreN39 = (EditText) findViewById(R.id.LetPreN39);
        vLtvPreN40 = (TextView) findViewById(R.id.LtvPreN40);
        vLetPreN41 = (EditText) findViewById(R.id.LetPreN41);
        vLtvPreN42 = (TextView) findViewById(R.id.LtvPreN42);
        vLtvPreN43 = (TextView) findViewById(R.id.LtvPreN43);
        vLtvPreN43a = (TextView) findViewById(R.id.LtvPreN43a);
        vLtvPreN44 = (TextView) findViewById(R.id.LtvPreN44);
        vLtvPreN44aa = (TextView) findViewById(R.id.LtvPreN44aa);
        vLtvPreN44ab = (TextView) findViewById(R.id.LtvPreN44ab);
        vLtvPreN44ac = (TextView) findViewById(R.id.LtvPreN44ac);
        vLtvPreN45 = (TextView) findViewById(R.id.LtvPreN45);
        vLetPreN46 = (EditText) findViewById(R.id.LetPreN46);
        vLtvPreN47 = (TextView) findViewById(R.id.LtvPreN47);
        vLtvPreN48 = (TextView) findViewById(R.id.LtvPreN48);
        vLtvPreN48a = (TextView) findViewById(R.id.LtvPreN48a);
        vLtvPreN48b = (TextView) findViewById(R.id.LtvPreN48b);
        vLtvPreN48c = (TextView) findViewById(R.id.LtvPreN48c);
        vLtvPreN48d = (TextView) findViewById(R.id.LtvPreN48d);
        vLtvPreN48e = (TextView) findViewById(R.id.LtvPreN48e);
        vLtvPreN48f = (TextView) findViewById(R.id.LtvPreN48f);
        vLtvPreN48g = (TextView) findViewById(R.id.LtvPreN48g);
        vLtvPreN48h = (TextView) findViewById(R.id.LtvPreN48h);
        vLtvPreN48i = (TextView) findViewById(R.id.LtvPreN48i);
        vLtvPreN48j = (TextView) findViewById(R.id.LtvPreN48j);
        vLtvPreN49 = (TextView) findViewById(R.id.LtvPreN49);
        vLetPreN50 = (EditText) findViewById(R.id.LetPreN50);
        vLetPreN51 = (EditText) findViewById(R.id.LetPreN51);
        vLetPreN52 = (EditText) findViewById(R.id.LetPreN52);
        vLetPreN53 = (EditText) findViewById(R.id.LetPreN53);
        vLetPreN54 = (EditText) findViewById(R.id.LetPreN54);
        vLtvPreN55 = (TextView) findViewById(R.id.LtvPreN55);
        vLetPreN56 = (EditText) findViewById(R.id.LetPreN56);
        vLetPreN57 = (EditText) findViewById(R.id.LetPreN57);
        vLetPreN58 = (EditText) findViewById(R.id.LetPreN58);
        vLetPreN59 = (EditText) findViewById(R.id.LetPreN59);
        vLtvPreN60 = (TextView) findViewById(R.id.LtvPreN60);
        vLtvPreN61 = (TextView) findViewById(R.id.LtvPreN61);
        vLtvPreN62 = (TextView) findViewById(R.id.LtvPreN62);
        vLtvPreN63 = (TextView) findViewById(R.id.LtvPreN63);

        vLllPreN01 = (LinearLayout) findViewById(R.id.LllPreN01);
        vLllPreN02 = (LinearLayout) findViewById(R.id.LllPreN02);
        vLllPreN03 = (LinearLayout) findViewById(R.id.LllPreN03);
        vLllPreN04 = (LinearLayout) findViewById(R.id.LllPreN04);
        vLllPreN05 = (LinearLayout) findViewById(R.id.LllPreN05);
        vLllPreN06 = (LinearLayout) findViewById(R.id.LllPreN06);
        vLllPreN07 = (LinearLayout) findViewById(R.id.LllPreN07);
        vLllPreN08 = (LinearLayout) findViewById(R.id.LllPreN08);
        vLllPreN09 = (LinearLayout) findViewById(R.id.LllPreN09);
        vLllPreN10 = (LinearLayout) findViewById(R.id.LllPreN10);
        vLllPreN11 = (LinearLayout) findViewById(R.id.LllPreN11);
        vLllPreN12 = (LinearLayout) findViewById(R.id.LllPreN12);
        vLllPreN13 = (LinearLayout) findViewById(R.id.LllPreN13);
        vLllPreN14 = (LinearLayout) findViewById(R.id.LllPreN14);
        vLllPreN15 = (LinearLayout) findViewById(R.id.LllPreN15);
        vLllPreN16 = (LinearLayout) findViewById(R.id.LllPreN16);
        vLllPreN17 = (LinearLayout) findViewById(R.id.LllPreN17);
        vLllPreN18 = (LinearLayout) findViewById(R.id.LllPreN18);
        vLllPreN18a = (LinearLayout) findViewById(R.id.LllPreN18a);
        vLllPreN18b = (LinearLayout) findViewById(R.id.LllPreN18b);
        vLllPreN19 = (LinearLayout) findViewById(R.id.LllPreN19);
        vLllPreN20 = (LinearLayout) findViewById(R.id.LllPreN20);
        vLllPreN21 = (LinearLayout) findViewById(R.id.LllPreN21);
        vLllPreN22 = (LinearLayout) findViewById(R.id.LllPreN22);
        vLllPreN22a = (LinearLayout) findViewById(R.id.LllPreN22a);
        vLllPreN23 = (LinearLayout) findViewById(R.id.LllPreN23);
        vLllPreN24 = (LinearLayout) findViewById(R.id.LllPreN24);
        vLllPreN24a = (LinearLayout) findViewById(R.id.LllPreN24a);
        vLllPreN25 = (LinearLayout) findViewById(R.id.LllPreN25);
        vLllPreN26 = (LinearLayout) findViewById(R.id.LllPreN26);
        vLllPreN27 = (LinearLayout) findViewById(R.id.LllPreN27);
        vLllPreN28 = (LinearLayout) findViewById(R.id.LllPreN28);
        vLllPreN29 = (LinearLayout) findViewById(R.id.LllPreN29);
        vLllPreN30 = (LinearLayout) findViewById(R.id.LllPreN30);
        vLllPreN31 = (LinearLayout) findViewById(R.id.LllPreN31);
        vLllPreN32 = (LinearLayout) findViewById(R.id.LllPreN32);
        vLllPreN33 = (LinearLayout) findViewById(R.id.LllPreN33);
        vLllPreN34 = (LinearLayout) findViewById(R.id.LllPreN34);
        vLllPreN35 = (LinearLayout) findViewById(R.id.LllPreN35);
        vLllPreN36 = (LinearLayout) findViewById(R.id.LllPreN36);
        vLllPreN37 = (LinearLayout) findViewById(R.id.LllPreN37);
        vLllPreN38 = (LinearLayout) findViewById(R.id.LllPreN38);
        vLllPreN39 = (LinearLayout) findViewById(R.id.LllPreN39);
        vLllPreN40 = (LinearLayout) findViewById(R.id.LllPreN40);
        vLllPreN41 = (LinearLayout) findViewById(R.id.LllPreN41);
        vLllPreN42 = (LinearLayout) findViewById(R.id.LllPreN42);
        vLllPreN42a = (LinearLayout) findViewById(R.id.LllPreN42a);
        vLllPreN43 = (LinearLayout) findViewById(R.id.LllPreN43);
        vLllPreN43a = (LinearLayout) findViewById(R.id.LllPreN43a);
        vLllPreN43b = (LinearLayout) findViewById(R.id.LllPreN43b);
        vLllPreN44 = (LinearLayout) findViewById(R.id.LllPreN44);
        vLllPreN44a = (LinearLayout) findViewById(R.id.LllPreN44a);
        vLllPreN45 = (LinearLayout) findViewById(R.id.LllPreN45);
        vLllPreN46 = (LinearLayout) findViewById(R.id.LllPreN46);
        vLllPreN47 = (LinearLayout) findViewById(R.id.LllPreN47);
        vLllPreN48 = (LinearLayout) findViewById(R.id.LllPreN48);
        vLllPreN48a = (LinearLayout) findViewById(R.id.LllPreN48a);
        vLllPreN48b = (LinearLayout) findViewById(R.id.LllPreN48b);
        vLllPreN48c = (LinearLayout) findViewById(R.id.LllPreN48c);
        vLllPreN48d = (LinearLayout) findViewById(R.id.LllPreN48d);
        vLllPreN48e = (LinearLayout) findViewById(R.id.LllPreN48e);
        vLllPreN48f = (LinearLayout) findViewById(R.id.LllPreN48f);
        vLllPreN48g = (LinearLayout) findViewById(R.id.LllPreN48g);
        vLllPreN48h = (LinearLayout) findViewById(R.id.LllPreN48h);
        vLllPreN48i = (LinearLayout) findViewById(R.id.LllPreN48i);
        vLllPreN48j = (LinearLayout) findViewById(R.id.LllPreN48j);
        vLllPreN49 = (LinearLayout) findViewById(R.id.LllPreN49);
        vLllPreN50 = (LinearLayout) findViewById(R.id.LllPreN50);
        vLllPreN51 = (LinearLayout) findViewById(R.id.LllPreN51);
        vLllPreN52 = (LinearLayout) findViewById(R.id.LllPreN52);
        vLllPreN53 = (LinearLayout) findViewById(R.id.LllPreN53);
        vLllPreN54 = (LinearLayout) findViewById(R.id.LllPreN54);
        vLllPreN55 = (LinearLayout) findViewById(R.id.LllPreN55);
        vLllPreN56 = (LinearLayout) findViewById(R.id.LllPreN56);
        vLllPreN57 = (LinearLayout) findViewById(R.id.LllPreN57);
        vLllPreN58 = (LinearLayout) findViewById(R.id.LllPreN58);
        vLllPreN59 = (LinearLayout) findViewById(R.id.LllPreN59);
        vLllPreN60 = (LinearLayout) findViewById(R.id.LllPreN60);
        vLllPreN61 = (LinearLayout) findViewById(R.id.LllPreN61);
        vLllPreN61a = (LinearLayout) findViewById(R.id.LllPreN61a);
        vLllPreN62 = (LinearLayout) findViewById(R.id.LllPreN62);
        vLllPreN62a = (LinearLayout) findViewById(R.id.LllPreN62a);
        vLllPreN63 = (LinearLayout) findViewById(R.id.LllPreN63);

        vLllPreN03.setVisibility(View.GONE);

        vLllPreN15.setVisibility(View.GONE);

        vLllPreN17.setVisibility(View.GONE);
        vLllPreN18.setVisibility(View.GONE);
        vLllPreN18a.setVisibility(View.GONE);
        vLllPreN18b.setVisibility(View.GONE);

        vLllPreN22.setVisibility(View.GONE);
        vLllPreN22a.setVisibility(View.GONE);
        vLllPreN23.setVisibility(View.GONE);
        vLllPreN24.setVisibility(View.GONE);
        vLllPreN24a.setVisibility(View.GONE);
        vLllPreN27.setVisibility(View.GONE);

        vLllPreN35.setVisibility(View.GONE);

        vLllPreN39.setVisibility(View.GONE);

        vLllPreN41.setVisibility(View.GONE);
        vLllPreN42.setVisibility(View.GONE);

        vLllPreN42a.setVisibility(View.GONE);

        vLllPreN43a.setVisibility(View.GONE);
        vLllPreN43b.setVisibility(View.GONE);

        vLllPreN44a.setVisibility(View.GONE);

        vLllPreN48a.setVisibility(View.GONE);
        vLllPreN48b.setVisibility(View.GONE);
        vLllPreN48c.setVisibility(View.GONE);
        vLllPreN48d.setVisibility(View.GONE);
        vLllPreN48e.setVisibility(View.GONE);
        vLllPreN48f.setVisibility(View.GONE);
        vLllPreN48g.setVisibility(View.GONE);
        vLllPreN48h.setVisibility(View.GONE);
        vLllPreN48i.setVisibility(View.GONE);
        vLllPreN48j.setVisibility(View.GONE);

        vLllPreN61.setVisibility(View.GONE);

        vLllPreN61a.setVisibility(View.GONE);

        vLllPreN62a.setVisibility(View.GONE);

        vLisDepart = new ArrayList<DepartDataSet>();
        vLisProvin = new ArrayList<ProvinDataSet>();
        vLisDistri = new ArrayList<DistriDataSet>();

        vALiDepart = new ArrayList<>();
        vALiProvin = new ArrayList<>();
        vALiDistri = new ArrayList<>();

        vLtvTitulo = (TextView) findViewById(R.id.LtvTitulo);
        vLtvSubTit = (TextView) findViewById(R.id.LtvSubTit);

        vLsvPart01 = (ScrollView) findViewById(R.id.LsvPart01);
        vLsvPart02 = (ScrollView) findViewById(R.id.LsvPart02);
        vLsvPart03 = (ScrollView) findViewById(R.id.LsvPart03);
        vLsvPart04 = (ScrollView) findViewById(R.id.LsvPart04);
        vLsvPart05 = (ScrollView) findViewById(R.id.LsvPart05);
        vLsvPart06 = (ScrollView) findViewById(R.id.LsvPart06);
        vLsvPart07 = (ScrollView) findViewById(R.id.LsvPart07);
        vLsvPart08 = (ScrollView) findViewById(R.id.LsvPart08);
        vLsvPart09 = (ScrollView) findViewById(R.id.LsvPart09);
        vLsvPart10 = (ScrollView) findViewById(R.id.LsvPart10);
        vLsvPart11 = (ScrollView) findViewById(R.id.LsvPart11);
        vLsvPart12 = (ScrollView) findViewById(R.id.LsvPart12);
        vLsvPart13 = (ScrollView) findViewById(R.id.LsvPart13);
        vLsvPart14 = (ScrollView) findViewById(R.id.LsvPart14);
        vLsvPart15 = (ScrollView) findViewById(R.id.LsvPart15);

        vLtvPreN13.setText("- " +getResources().getString(R.string.formul_opcn01_resn13a) + ": "+ vDsUbicac.getUbicacLatitu() + "\n" +
                "- " +getResources().getString(R.string.formul_opcn01_resn13b) + ": "+ vDsUbicac.getUbicacLongit());

        mPrinciDbHelper = new PrinciDbHelper(this);

        vDstDirWeb = DirWebDataSet.getInstance();

        String[] vArrPreN01 = getResources().getStringArray(R.array.formul_opcn01_pren01);

        vLtvPreN01.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn00_pren01))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN01, iPreN01, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN01 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN01.setText(vArrPreN01[iPreN01]);
                                if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01a))){
                                    vLetPreN02.setText("");
                                    vLetPreN02.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    vLetPreN02.setEnabled(true);
                                    vLetPreN02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
                                }
                                if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01b))){
                                    vLetPreN02.setText("");
                                    vLetPreN02.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    vLetPreN02.setEnabled(true);
                                    vLetPreN02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
                                }
                                if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01c))){
                                    vLetPreN02.setText("");
                                    vLetPreN02.setInputType(InputType.TYPE_CLASS_TEXT);
                                    vLetPreN02.setEnabled(true);
                                    vLetPreN02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                                }
                                if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01d))){
                                    vLetPreN02.setText("");
                                    vLetPreN02.setInputType(InputType.TYPE_CLASS_TEXT);
                                    vLetPreN02.setEnabled(true);
                                    vLetPreN02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                                }
                                if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01e))){
                                    vLetPreN02.setText("");
                                    vLetPreN02.setInputType(InputType.TYPE_CLASS_TEXT);
                                    vLetPreN02.setEnabled(true);
                                    vLetPreN02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vLetPreN02.setInputType(InputType.TYPE_NULL);
        vLetPreN02.setEnabled(false);

        vLetPreN02.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });

        vLetPreN02.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01a))){
                        MSerConsulPaciente("1",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01b))){
                        MSerConsulPaciente("2",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01c))){
                        MSerConsulPaciente("3",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01d))){
                        MSerConsulPaciente("4",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01e))){
                        MSerConsulPaciente("5",vLetPreN02.getText().toString());
                    }
                }
            }
        });

        vLetPreN02.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01a))){
                        MSerConsulPaciente("1",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01b))){
                        MSerConsulPaciente("2",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01c))){
                        MSerConsulPaciente("3",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01d))){
                        MSerConsulPaciente("4",vLetPreN02.getText().toString());
                    }
                    if(vArrPreN01[iPreN01].equals(getResources().getString(R.string.formul_opcn01_resn01e))){
                        MSerConsulPaciente("5",vLetPreN02.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });

        vLetPreN07.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetPreN07.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });

        vLetPreN07.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regex = "9[0-9]{8}";
                if(s.length() == 0){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 1 && !vLetPreN07.getText().toString().matches("9")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 2 && !vLetPreN07.getText().toString().matches("9[0-9]{1}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 3 && !vLetPreN07.getText().toString().matches("9[0-9]{2}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 4 && !vLetPreN07.getText().toString().matches("9[0-9]{3}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 5 && !vLetPreN07.getText().toString().matches("9[0-9]{4}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 6 && !vLetPreN07.getText().toString().matches("9[0-9]{5}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 7 && !vLetPreN07.getText().toString().matches("9[0-9]{6}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 8 && !vLetPreN07.getText().toString().matches("9[0-9]{7}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
                if(s.length() == 9 && !vLetPreN07.getText().toString().matches("9[0-9]{8}")){
                    vLetPreN07.setText("9");
                    vLetPreN07.requestFocus();
                    vLetPreN07.setSelection(vLetPreN07.getText().length());
                }
            }
        });

        String[] vArrPreN08 = getResources().getStringArray(R.array.formul_opcn01_pren08);

        vLtvPreN08.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren08))
                        .setSingleChoiceItems(vArrPreN08, iPreN08, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN08 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN08.setText(vArrPreN08[iPreN08]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vLisDepart.clear();

        vCursorDepart = mPrinciDbHelper.getAllPrinci(DepartDbHelper.DepartTableC.DepartTableN);
        while(vCursorDepart.moveToNext()){
            DepartDataSet vDstDepart = new DepartDataSet();
            vDstDepart.setDepartCodigo(vCursorDepart.getString(vCursorDepart.getColumnIndex(DepartDbHelper.DepartTableC.DepartCodigo)));
            vDstDepart.setDepartNombre(" " + vCursorDepart.getString(vCursorDepart.getColumnIndex(DepartDbHelper.DepartTableC.DepartDescri)));
            vLisDepart.add(vDstDepart);
        }

        vALiDepart.clear();
        for (DepartDataSet o : vLisDepart) {
            vALiDepart.add(o.getDepartNombre());
        }

        String[] vArrPreN10 = new String[vALiDepart.size()];

        vArrPreN10 = vALiDepart.toArray(vArrPreN10);

        String[] finalVArrPreN10 = vArrPreN10;

        vLtvPreN10.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren10))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(finalVArrPreN10, iPreN10, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN10 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN10.setText(finalVArrPreN10[iPreN10]);

                                for (DepartDataSet o : vLisDepart) {
                                    if(o.getDepartNombre().equals(finalVArrPreN10[iPreN10])){
                                        vDepart = o.getDepartCodigo();
                                    }
                                }

                                vLisProvin.clear();

                                vCursorProvin = mPrinciDbHelper.getAllPrinciOne(ProvinDbHelper.ProvinTableC.ProvinTableN, ProvinDbHelper.ProvinTableC.DepartCodigo, vDepart);
                                while(vCursorProvin.moveToNext()){
                                    ProvinDataSet vDstProvin = new ProvinDataSet();
                                    vDstProvin.setProvinCodigo(vCursorProvin.getString(vCursorProvin.getColumnIndex(ProvinDbHelper.ProvinTableC.ProvinCodigo)));
                                    vDstProvin.setProvinNombre(" " + vCursorProvin.getString(vCursorProvin.getColumnIndex(ProvinDbHelper.ProvinTableC.ProvinDescri)));
                                    vLisProvin.add(vDstProvin);
                                }

                                vALiProvin.clear();
                                for (ProvinDataSet o : vLisProvin) {
                                    vALiProvin.add(o.getProvinNombre());
                                }

                                String[] vArrPreN11 = new String[vALiProvin.size()];

                                vArrPreN11 = vALiProvin.toArray(vArrPreN11);

                                String[] finalVArrPreN11 = vArrPreN11;

                                vLtvPreN11.setOnClickListener(new AdapterView.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                                        builder.setTitle(getResources().getString(R.string.formul_opcn01_pren11))
                                                //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                                                .setSingleChoiceItems(finalVArrPreN11, iPreN10, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        iPreN11 = which;
                                                    }
                                                })
                                                .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        vLtvPreN11.setText(finalVArrPreN11[iPreN11]);

                                                        for (ProvinDataSet o : vLisProvin) {
                                                            if(o.getProvinNombre().equals(finalVArrPreN11[iPreN11])){
                                                                vProvin = o.getProvinCodigo();
                                                            }
                                                        }

                                                        vLisDistri.clear();

                                                        vCursorDistri = mPrinciDbHelper.getAllPrinciTwo(DistriDbHelper.DistriTableC.DistriTableN, DistriDbHelper.DistriTableC.DepartCodigo, vDepart, DistriDbHelper.DistriTableC.ProvinCodigo, vProvin);
                                                        while(vCursorDistri.moveToNext()){
                                                            DistriDataSet vDstDistri = new DistriDataSet();
                                                            vDstDistri.setDistriCodigo(vCursorDistri.getString(vCursorDistri.getColumnIndex(DistriDbHelper.DistriTableC.DistriCodigo)));
                                                            vDstDistri.setDistriNombre(" " + vCursorDistri.getString(vCursorDistri.getColumnIndex(DistriDbHelper.DistriTableC.DistriDescri)));
                                                            vLisDistri.add(vDstDistri);
                                                        }

                                                        vALiDistri.clear();
                                                        for (DistriDataSet o : vLisDistri) {
                                                            vALiDistri.add(o.getDistriNombre());
                                                        }

                                                        String[] vArrPreN12 = new String[vALiDistri.size()];

                                                        vArrPreN12 = vALiDistri.toArray(vArrPreN12);

                                                        String[] finalVArrPreN12 = vArrPreN12;

                                                        vLtvPreN12.setOnClickListener(new AdapterView.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                                                                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren12))
                                                                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                                                                        .setSingleChoiceItems(finalVArrPreN12, iPreN12, new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                iPreN12 = which;
                                                                            }
                                                                        })
                                                                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                vLtvPreN12.setText(finalVArrPreN12[iPreN12]);
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

        String[] vArrPreN14 = getResources().getStringArray(R.array.formul_opcn01_pren14);

        vLtvPreN14.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren14))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN14, iPreN14, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN14 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN14.setText(vArrPreN14[iPreN14]);
                                if(vArrPreN14[iPreN14].equals(getResources().getString(R.string.formul_opcn01_resn14a))){
                                    vLllPreN15.setVisibility(View.VISIBLE);
                                    vLllPreN15.requestFocus();
                                }
                                if(vArrPreN14[iPreN14].equals(getResources().getString(R.string.formul_opcn01_resn14b))){
                                    vLllPreN15.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN15 = getResources().getStringArray(R.array.formul_opcn01_pren15);

        vLtvPreN15.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren15))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN15, iPreN15, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN15 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN15.setText(vArrPreN15[iPreN15]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN16 = getResources().getStringArray(R.array.formul_opcn01_pren16);

        vLtvPreN16.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren16))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN16, iPreN16, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN16 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN16.setText(vArrPreN16[iPreN16]);
                                if(vArrPreN16[iPreN16].equals(getResources().getString(R.string.formul_opcn01_resn16a))){
                                    vLllPreN17.setVisibility(View.VISIBLE);
                                    vLllPreN18.setVisibility(View.VISIBLE);
                                    vLllPreN17.requestFocus();
                                }
                                if(vArrPreN16[iPreN16].equals(getResources().getString(R.string.formul_opcn01_resn16b))){
                                    vLllPreN17.setVisibility(View.GONE);
                                    vLllPreN18.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        Calendar vLcaPreN17 = Calendar.getInstance();

        int vLinPreN17Y = vLcaPreN17.get(Calendar.YEAR);
        int vLinPreN17M = vLcaPreN17.get(Calendar.MONTH);
        int vLinPreN17D = vLcaPreN17.get(Calendar.DAY_OF_MONTH);

        vLetPreN17.setText(vLinPreN17D + "/" + ((vLinPreN17M + 1) < 10 ? "0" + (vLinPreN17M + 1) : "" + (vLinPreN17M + 1)) + "/" + vLinPreN17Y);

        DatePickerDialog vLdpPreN17 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN17.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN17Y, vLinPreN17M, vLinPreN17D);

        Calendar vLcaPreN17N = Calendar.getInstance();
        vLcaPreN17N.add(Calendar.MINUTE, +1);

        vLdpPreN17.getDatePicker().setMaxDate(vLcaPreN17N.getTimeInMillis());

        vLetPreN17.setInputType(InputType.TYPE_NULL);
        vLetPreN17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN17.show();
            }
        });

        String[] vArrPreN18 = getResources().getStringArray(R.array.formul_opcn01_pren18);
        boolean[] vArrPreN18boolean = new boolean[vArrPreN18.length];
        for (int i = 0; i < vArrPreN18.length; i++) {
            vArrPreN18boolean[i] = false;
        }

        vLtvPreN18.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren18))
                        .setMultiChoiceItems(vArrPreN18, vArrPreN18boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN18boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN18.setText(" ");
                                iPreN18 = 0;
                                for (int i = 0; i < vArrPreN18.length; i++) {
                                    if (vArrPreN18boolean[i]) {
                                        if(iPreN18 == 0){
                                            vLtvPreN18.setText(vArrPreN18[i]);
                                            iPreN18 = 1;
                                        }else{
                                            vLtvPreN18.setText(vLtvPreN18.getText().toString() + "\n" + vArrPreN18[i]);
                                            iPreN18 = 1;
                                        }
                                    }
                                    if(vArrPreN18[i].equals(getResources().getString(R.string.formul_opcn01_resn18k))) {
                                        if (vArrPreN18boolean[i]) {
                                            vLllPreN18a.setVisibility(View.VISIBLE);
                                            vLllPreN18a.requestFocus();
                                        } else {
                                            vLllPreN18a.setVisibility(View.GONE);
                                        }
                                    }
                                    if(vArrPreN18[i].equals(getResources().getString(R.string.formul_opcn01_resn18l))){
                                        if (vArrPreN18boolean[i]) {
                                            vLllPreN18b.setVisibility(View.VISIBLE);
                                            vLllPreN18b.requestFocus();
                                        } else {
                                            vLllPreN18b.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                if(iPreN18 == 0){
                                    vLtvPreN18.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN18k = getResources().getStringArray(R.array.formul_opcn01_pren18k);
        boolean[] vArrPreN18kboolean = new boolean[vArrPreN18k.length];
        for (int i = 0; i < vArrPreN18k.length; i++) {
            vArrPreN18kboolean[i] = false;
        }

        vLtvPreN18k.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn01_pren18k))
                        .setMultiChoiceItems(vArrPreN18k, vArrPreN18kboolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN18kboolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN18k.setText(" ");
                                iPreN18k = 0;
                                for (int i = 0; i < vArrPreN18k.length; i++) {
                                    if (vArrPreN18kboolean[i]) {
                                        if(iPreN18k == 0){
                                            vLtvPreN18k.setText(vArrPreN18k[i]);
                                            iPreN18k = 1;
                                        }else{
                                            vLtvPreN18k.setText(vLtvPreN18k.getText().toString() + "\n" + vArrPreN18k[i]);
                                            iPreN18k = 1;
                                        }
                                    }
                                }
                                if(iPreN18k == 0){
                                    vLtvPreN18k.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        Calendar vLcaPreN19 = Calendar.getInstance();

        int vLinPreN19Y = vLcaPreN19.get(Calendar.YEAR);
        int vLinPreN19M = vLcaPreN19.get(Calendar.MONTH);
        int vLinPreN19D = vLcaPreN19.get(Calendar.DAY_OF_MONTH);

        vLetPreN19.setText(vLinPreN19D + "/" + ((vLinPreN19M + 1) < 10 ? "0" + (vLinPreN19M + 1) : "" + (vLinPreN19M + 1)) + "/" + vLinPreN19Y);

        DatePickerDialog vLdpPreN19 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN19.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN19Y, vLinPreN19M, vLinPreN19D);

        Calendar vLcaPreN19N = Calendar.getInstance();
        vLcaPreN19N.add(Calendar.MINUTE, +1);

        vLdpPreN19.getDatePicker().setMaxDate(vLcaPreN19N.getTimeInMillis());

        vLetPreN19.setInputType(InputType.TYPE_NULL);
        vLetPreN19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN19.show();
            }
        });

        String[] vArrPreN20 = getResources().getStringArray(R.array.formul_opcn02_subn01_pren20);

        vLtvPreN20.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn02_subn01_pren20))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN20, iPreN20, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN20 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN20.setText(vArrPreN20[iPreN20]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN21 = getResources().getStringArray(R.array.formul_opcn02_subn01_pren21);

        vLtvPreN21.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn02_subn01_pren21))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN21, iPreN21, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN21 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN21.setText(vArrPreN21[iPreN21]);
                                if(vArrPreN21[iPreN21].equals(getResources().getString(R.string.formul_opcn02_subn01_resn21a))){
                                    vLllPreN22.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                if(vArrPreN21[iPreN21].equals(getResources().getString(R.string.formul_opcn02_subn01_resn21b))){
                                    vLllPreN22.setVisibility(View.VISIBLE);
                                    vLllPreN23.setVisibility(View.GONE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN22.requestFocus();
                                }
                                if(vArrPreN21[iPreN21].equals(getResources().getString(R.string.formul_opcn02_subn01_resn21c))){
                                    vLllPreN22.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                if(vArrPreN21[iPreN21].equals(getResources().getString(R.string.formul_opcn02_subn01_resn21d))){
                                    vLllPreN22.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                if(vArrPreN21[iPreN21].equals(getResources().getString(R.string.formul_opcn02_subn01_resn21e))){
                                    vLllPreN22.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN22 = getResources().getStringArray(R.array.formul_opcn02_subn01_pren22);

        vLtvPreN22.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn02_subn01_pren22))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN22, iPreN22, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN22 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN22.setText(vArrPreN22[iPreN22]);
                                if(vArrPreN22[iPreN22].equals(getResources().getString(R.string.formul_opcn02_subn01_resn22a))){
                                    vLllPreN22a.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                if(vArrPreN22[iPreN22].equals(getResources().getString(R.string.formul_opcn02_subn01_resn22b))){
                                    vLllPreN22a.setVisibility(View.VISIBLE);
                                    vLllPreN23.setVisibility(View.GONE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.VISIBLE);
                                    vLllPreN22a.requestFocus();
                                }
                                if(vArrPreN22[iPreN22].equals(getResources().getString(R.string.formul_opcn02_subn01_resn22c))){
                                    vLllPreN22a.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                if(vArrPreN22[iPreN22].equals(getResources().getString(R.string.formul_opcn02_subn01_resn22d))){
                                    vLllPreN22a.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                if(vArrPreN22[iPreN22].equals(getResources().getString(R.string.formul_opcn02_subn01_resn22e))){
                                    vLllPreN22a.setVisibility(View.GONE);
                                    vLllPreN23.setVisibility(View.VISIBLE);
                                    vLllPreN24.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                    vLllPreN23.requestFocus();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN23 = getResources().getStringArray(R.array.formul_opcn02_subn01_pren23);

        vLtvPreN23.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn02_subn01_pren23))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN23, iPreN23, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN23 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN23.setText(vArrPreN23[iPreN23]);
                                vLllPreN24.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN24 = getResources().getStringArray(R.array.formul_opcn02_subn01_pren24);
        boolean[] vArrPreN24boolean = new boolean[vArrPreN24.length];
        for (int i = 0; i < vArrPreN24.length; i++) {
            vArrPreN24boolean[i] = false;
        }

        vLtvPreN24.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn02_subn01_pren24))
                        .setMultiChoiceItems(vArrPreN24, vArrPreN24boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN24boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN24.setText(" ");
                                iPreN24 = 0;
                                int Ninguno = 0;
                                int Otros = 0;
                                for (int i = 0; i < vArrPreN24.length; i++) {
                                    if (vArrPreN24boolean[i]) {
                                        if(iPreN24 == 0){
                                            vLtvPreN24.setText(vArrPreN24[i]);
                                            iPreN24 = 1;
                                        }else{
                                            vLtvPreN24.setText(vLtvPreN24.getText().toString() + "\n" + vArrPreN24[i]);
                                            iPreN24 = 1;
                                        }
                                    }
                                    if(vArrPreN24[i].equals(getResources().getString(R.string.formul_opcn02_subn01_resn24m))) {
                                        Ninguno = 1;
                                    }
                                }
                                if(iPreN24 == 0){
                                    vLtvPreN24.setText(getResources().getString(R.string.formul_selecc));
                                    vLllPreN24a.setVisibility(View.GONE);
                                    vLllPreN27.setVisibility(View.GONE);
                                }else{
                                    if(iPreN21 == 0 || iPreN22 == 0){
                                        if(iPreN23 == 0){
                                            if(Ninguno == 1){
                                                vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24aaa));
                                                vLllPreN24a.setVisibility(View.VISIBLE);
                                                vLllPreN27.setVisibility(View.VISIBLE);
                                            }else{
                                                if(iPreN24 == 1){
                                                    vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24aab));
                                                    vLllPreN24a.setVisibility(View.VISIBLE);
                                                    vLllPreN27.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                        if(iPreN23 == 1){
                                            if(Ninguno == 1){
                                                vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24aba));
                                                vLllPreN24a.setVisibility(View.VISIBLE);
                                                vLllPreN27.setVisibility(View.VISIBLE);
                                            }else{
                                                if(iPreN24 == 1){
                                                    vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24abb));
                                                    vLllPreN24a.setVisibility(View.VISIBLE);
                                                    vLllPreN27.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                        if(iPreN23 == 2){
                                            if(Ninguno == 1){
                                                vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24aca));
                                                vLllPreN24a.setVisibility(View.VISIBLE);
                                                vLllPreN27.setVisibility(View.VISIBLE);
                                            }else{
                                                if(iPreN24 == 1){
                                                    vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24acb));
                                                    vLllPreN24a.setVisibility(View.VISIBLE);
                                                    vLllPreN27.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    }
                                    if(iPreN21 == 2 || iPreN22 == 2 || iPreN21 == 3 || iPreN22 == 3 || iPreN21 == 4 || iPreN22 == 4){
                                        if(iPreN23 == 0){
                                            if(Ninguno == 1){
                                                vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24baa));
                                                vLllPreN24a.setVisibility(View.VISIBLE);
                                                vLllPreN27.setVisibility(View.VISIBLE);
                                            }else{
                                                if(iPreN24 == 1){
                                                    vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24bab));
                                                    vLllPreN24a.setVisibility(View.VISIBLE);
                                                    vLllPreN27.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                        if(iPreN23 == 1){
                                            vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24bba));
                                            vLllPreN24a.setVisibility(View.VISIBLE);
                                            vLllPreN27.setVisibility(View.VISIBLE);
                                        }
                                        if(iPreN23 == 2){
                                            vLtvPreN24a.setText(getResources().getString(R.string.formul_opcn02_subn01_pren24bca));
                                            vLllPreN24a.setVisibility(View.VISIBLE);
                                            vLllPreN27.setVisibility(View.VISIBLE);
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

        vLetPreN26.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetPreN26.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });

        String[] vArrPreN27 = getResources().getStringArray(R.array.formul_opcn02_subn02_pren27);

        vLtvPreN27.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn02_subn02_pren27))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN27, iPreN27, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN27 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN27.setText(vArrPreN27[iPreN27]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        Calendar vLcaPreN28 = Calendar.getInstance();

        int vLinPreN28Y = vLcaPreN28.get(Calendar.YEAR);
        int vLinPreN28M = vLcaPreN28.get(Calendar.MONTH);
        int vLinPreN28D = vLcaPreN28.get(Calendar.DAY_OF_MONTH);

        vLetPreN28.setText(vLinPreN28D + "/" + ((vLinPreN28M + 1) < 10 ? "0" + (vLinPreN28M + 1) : "" + (vLinPreN28M + 1)) + "/" + vLinPreN28Y);

        DatePickerDialog vLdpPreN28 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN28.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN28Y, vLinPreN28M, vLinPreN28D);

        Calendar vLcaPreN28N = Calendar.getInstance();
        vLcaPreN28N.add(Calendar.MINUTE, +1);

        vLdpPreN28.getDatePicker().setMaxDate(vLcaPreN28N.getTimeInMillis());

        vLetPreN28.setInputType(InputType.TYPE_NULL);
        vLetPreN28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN28.show();
            }
        });

        vLetPreN29.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetPreN29.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });

        Calendar vLcaPreN31 = Calendar.getInstance();

        int vLinPreN31Y = vLcaPreN31.get(Calendar.YEAR);
        int vLinPreN31M = vLcaPreN31.get(Calendar.MONTH);
        int vLinPreN31D = vLcaPreN31.get(Calendar.DAY_OF_MONTH);

        vLetPreN31.setText(vLinPreN31D + "/" + ((vLinPreN31M + 1) < 10 ? "0" + (vLinPreN31M + 1) : "" + (vLinPreN31M + 1)) + "/" + vLinPreN31Y);

        vLstPreN31E = "0";

        DatePickerDialog vLdpPreN31 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                java.util.Date fechadate = null;
                SimpleDateFormat formatoTexto = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fechadate = formatoTexto.parse(selectedDayOfMonth + "-" + selectedMonthOfYear + "-" + year);
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Calendar edad=Calendar.getInstance();
                edad.setTime(fechadate);

                Calendar hoy=Calendar.getInstance();
                int anios=hoy.get(Calendar.YEAR)-edad.get(Calendar.YEAR);
                edad.add(Calendar.YEAR,anios);
                if(hoy.before(edad)){
                    anios--;
                }
                vLstPreN31E = String.valueOf(anios);
                vLetPreN31.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
                vLetPreN32.setText(String.valueOf(anios));
            }

        }, vLinPreN31Y, vLinPreN31M, vLinPreN31D);

        Calendar vLcaPreN31N = Calendar.getInstance();
        vLcaPreN31N.add(Calendar.MINUTE, +1);

        vLdpPreN31.getDatePicker().setMaxDate(vLcaPreN31N.getTimeInMillis());

        vLetPreN31.setInputType(InputType.TYPE_NULL);
        vLetPreN31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN31.show();
            }
        });

        vLetPreN32.setText("0");
        vLetPreN32.setInputType(InputType.TYPE_NULL);
        vLetPreN32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        vLetPreN32.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!vLetPreN32.getText().toString().equals(vLstPreN31E)){
                    vLetPreN32.setText(vLstPreN31E);
                }
            }
        });

        String[] vArrPreN33 = getResources().getStringArray(R.array.formul_opcn03_subn02_pren33);

        vLtvPreN33.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn02_pren33))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN33, iPreN33, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN33 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN33.setText(vArrPreN33[iPreN33]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN34 = getResources().getStringArray(R.array.formul_opcn03_subn02_pren34);

        vLtvPreN34.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn02_pren34))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN34, iPreN34, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN34 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN34.setText(vArrPreN34[iPreN34]);
                                if(vArrPreN34[iPreN34].equals(getResources().getString(R.string.formul_opcn03_subn02_resn34f))){
                                    vLllPreN35.setVisibility(View.VISIBLE);
                                    vLllPreN35.requestFocus();
                                }
                                if(!vArrPreN34[iPreN34].equals(getResources().getString(R.string.formul_opcn03_subn02_resn34f))){
                                    vLllPreN35.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN38 = getResources().getStringArray(R.array.formul_opcn03_subn04_pren38);

        vLtvPreN38.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn04_pren38))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN38, iPreN38, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN38 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN38.setText(vArrPreN38[iPreN38]);
                                if(vArrPreN38[iPreN38].equals(getResources().getString(R.string.formul_opcn03_subn04_resn38a))){
                                    vLllPreN39.setVisibility(View.VISIBLE);
                                    vLllPreN39.requestFocus();
                                }
                                if(!vArrPreN38[iPreN38].equals(getResources().getString(R.string.formul_opcn03_subn04_resn38a))){
                                    vLllPreN39.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        Calendar vLcaPreN39 = Calendar.getInstance();

        int vLinPreN39Y = vLcaPreN39.get(Calendar.YEAR);
        int vLinPreN39M = vLcaPreN39.get(Calendar.MONTH);
        int vLinPreN39D = vLcaPreN39.get(Calendar.DAY_OF_MONTH);

        vLetPreN39.setText(vLinPreN39D + "/" + ((vLinPreN39M + 1) < 10 ? "0" + (vLinPreN39M + 1) : "" + (vLinPreN39M + 1)) + "/" + vLinPreN39Y);

        DatePickerDialog vLdpPreN39 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN39.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN39Y, vLinPreN39M, vLinPreN39D);

        Calendar vLcaPreN39N = Calendar.getInstance();
        vLcaPreN39N.add(Calendar.MINUTE, +1);

        vLdpPreN39.getDatePicker().setMaxDate(vLcaPreN39N.getTimeInMillis());

        vLetPreN39.setInputType(InputType.TYPE_NULL);
        vLetPreN39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN39.show();
            }
        });

        String[] vArrPreN40 = getResources().getStringArray(R.array.formul_opcn03_subn04_pren40);

        vLtvPreN40.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn04_pren40))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN40, iPreN40, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN40 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN40.setText(vArrPreN40[iPreN40]);
                                if(vArrPreN40[iPreN40].equals(getResources().getString(R.string.formul_opcn03_subn04_resn40a))){
                                    vLllPreN41.setVisibility(View.VISIBLE);
                                    vLllPreN42.setVisibility(View.VISIBLE);
                                    vLllPreN41.requestFocus();
                                }
                                if(!vArrPreN40[iPreN40].equals(getResources().getString(R.string.formul_opcn03_subn04_resn40a))){
                                    vLllPreN41.setVisibility(View.GONE);
                                    vLllPreN42.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN42 = getResources().getStringArray(R.array.formul_opcn03_subn04_pren42);
        boolean[] vArrPreN42boolean = new boolean[vArrPreN42.length];
        for (int i = 0; i < vArrPreN42.length; i++) {
            vArrPreN42boolean[i] = false;
        }

        vLtvPreN42.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn04_pren42))
                        .setMultiChoiceItems(vArrPreN42, vArrPreN42boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN42boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN42.setText(" ");
                                iPreN42 = 0;
                                for (int i = 0; i < vArrPreN42.length; i++) {
                                    if (vArrPreN42boolean[i]) {
                                        if(iPreN42 == 0){
                                            vLtvPreN42.setText(vArrPreN42[i]);
                                            iPreN42 = 1;
                                        }else{
                                            vLtvPreN42.setText(vLtvPreN42.getText().toString() + "\n" + vArrPreN42[i]);
                                            iPreN42 = 1;
                                        }
                                    }
                                    if(vArrPreN42[i].equals(getResources().getString(R.string.formul_opcn03_subn04_resn42f))) {
                                        if (vArrPreN42boolean[i]) {
                                            vLllPreN42a.setVisibility(View.VISIBLE);
                                            vLllPreN42a.requestFocus();
                                        } else {
                                            vLllPreN42a.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                if(iPreN42 == 0){
                                    vLtvPreN42.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN43 = getResources().getStringArray(R.array.formul_opcn03_subn04_pren43);
        boolean[] vArrPreN43boolean = new boolean[vArrPreN43.length];
        for (int i = 0; i < vArrPreN43.length; i++) {
            vArrPreN43boolean[i] = false;
        }

        vLtvPreN43.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn04_pren43))
                        .setMultiChoiceItems(vArrPreN43, vArrPreN43boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN43boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN43.setText(" ");
                                iPreN43 = 0;
                                for (int i = 0; i < vArrPreN43.length; i++) {
                                    if (vArrPreN43boolean[i]) {
                                        if(iPreN43 == 0){
                                            vLtvPreN43.setText(vArrPreN43[i]);
                                            iPreN43 = 1;
                                        }else{
                                            vLtvPreN43.setText(vLtvPreN43.getText().toString() + "\n" + vArrPreN43[i]);
                                            iPreN43 = 1;
                                        }
                                    }
                                    if(vArrPreN43[i].equals(getResources().getString(R.string.formul_opcn03_subn04_resn43k))) {
                                        if (vArrPreN43boolean[i]) {
                                            vLllPreN43a.setVisibility(View.VISIBLE);
                                            vLllPreN43a.requestFocus();
                                        } else {
                                            vLllPreN43a.setVisibility(View.GONE);
                                        }
                                    }
                                    if(vArrPreN43[i].equals(getResources().getString(R.string.formul_opcn03_subn04_resn43n))) {
                                        if (vArrPreN43boolean[i]) {
                                            vLllPreN43b.setVisibility(View.VISIBLE);
                                            vLllPreN43b.requestFocus();
                                        } else {
                                            vLllPreN43b.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                if(iPreN43 == 0){
                                    vLtvPreN43.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN43a = getResources().getStringArray(R.array.formul_opcn03_subn04_pren43a);

        vLtvPreN43a.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn04_pren43a))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN43a, iPreN43a, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN43a = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN43a.setText(vArrPreN43a[iPreN43a]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN44 = getResources().getStringArray(R.array.formul_opcn03_subn05_pren44);

        vLtvPreN44.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn05_pren44))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN44, iPreN44, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN44 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN44.setText(vArrPreN44[iPreN44]);
                                if(vArrPreN44[iPreN44].equals(getResources().getString(R.string.formul_opcn03_subn05_resn44a))){
                                    vLllPreN44a.setVisibility(View.VISIBLE);
                                    vLllPreN44a.requestFocus();
                                }
                                if(!vArrPreN44[iPreN40].equals(getResources().getString(R.string.formul_opcn03_subn05_resn44a))){
                                    vLllPreN44a.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vLtvPreN44aa.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout layout = new LinearLayout(FormulActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(70, 0, 70, 0);

                final TextView vLtvPren44aas = new TextView(FormulActivity.this);
                layout.addView(vLtvPren44aas);

                final TextView vLtvPren44aa = new TextView(FormulActivity.this);
                vLtvPren44aa.setTextSize(16);
                vLtvPren44aa.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren44aa.setLayoutParams(lp);
                vLtvPren44aa.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44aa));
                layout.addView(vLtvPren44aa);

                final EditText vLetPren44aa = new EditText(FormulActivity.this);
                vLetPren44aa.setText(vPren44aa);
                vLetPren44aa.setLayoutParams(lp);
                layout.addView(vLetPren44aa);

                final TextView vLtvPren44abs = new TextView(FormulActivity.this);
                layout.addView(vLtvPren44abs);

                final TextView vLtvPren44ab = new TextView(FormulActivity.this);
                vLtvPren44ab.setTextSize(16);
                vLtvPren44ab.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren44ab.setLayoutParams(lp);
                vLtvPren44ab.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44ab));
                layout.addView(vLtvPren44ab);

                final EditText vLetPren44ab = new EditText(FormulActivity.this);
                vLetPren44ab.setText(vPren44ab);
                vLetPren44ab.setLayoutParams(lp);
                layout.addView(vLetPren44ab);

                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setView(layout);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn05_pren44a))
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vPren44aa = vLetPren44aa.getText().toString();
                                vPren44ab = vLetPren44ab.getText().toString();
                                vLtvPreN44aa.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44a) + "\n" +
                                        " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44aa) + ": " + vLetPren44aa.getText().toString() + "\n" +
                                        " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44ab) + ": " + vLetPren44ab.getText().toString());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vLtvPreN44ab.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout layout = new LinearLayout(FormulActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(70, 0, 70, 0);

                final TextView vLtvPren44bas = new TextView(FormulActivity.this);
                layout.addView(vLtvPren44bas);

                final TextView vLtvPren44ba = new TextView(FormulActivity.this);
                vLtvPren44ba.setTextSize(16);
                vLtvPren44ba.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren44ba.setLayoutParams(lp);
                vLtvPren44ba.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44ba));
                layout.addView(vLtvPren44ba);

                final EditText vLetPren44ba = new EditText(FormulActivity.this);
                vLetPren44ba.setText(vPren44ba);
                vLetPren44ba.setLayoutParams(lp);
                layout.addView(vLetPren44ba);

                final TextView vLtvPren44bbs = new TextView(FormulActivity.this);
                layout.addView(vLtvPren44bbs);

                final TextView vLtvPren44bb = new TextView(FormulActivity.this);
                vLtvPren44bb.setTextSize(16);
                vLtvPren44bb.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren44bb.setLayoutParams(lp);
                vLtvPren44bb.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44bb));
                layout.addView(vLtvPren44bb);

                final EditText vLetPren44bb = new EditText(FormulActivity.this);
                vLetPren44bb.setText(vPren44bb);
                vLetPren44bb.setLayoutParams(lp);
                layout.addView(vLetPren44bb);

                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setView(layout);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn05_pren44b))
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vPren44ba = vLetPren44ba.getText().toString();
                                vPren44bb = vLetPren44bb.getText().toString();
                                vLtvPreN44ab.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44b) + "\n" +
                                        " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44ba) + ": " + vLetPren44ba.getText().toString() + "\n" +
                                        " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44bb) + ": " + vLetPren44bb.getText().toString());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vLtvPreN44ac.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout layout = new LinearLayout(FormulActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(70, 0, 70, 0);

                final TextView vLtvPren44cas = new TextView(FormulActivity.this);
                layout.addView(vLtvPren44cas);

                final TextView vLtvPren44ca = new TextView(FormulActivity.this);
                vLtvPren44ca.setTextSize(16);
                vLtvPren44ca.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren44ca.setLayoutParams(lp);
                vLtvPren44ca.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44ca));
                layout.addView(vLtvPren44ca);

                final EditText vLetPren44ca = new EditText(FormulActivity.this);
                vLetPren44ca.setText(vPren44ca);
                vLetPren44ca.setLayoutParams(lp);
                layout.addView(vLetPren44ca);

                final TextView vLtvPren44cbs = new TextView(FormulActivity.this);
                layout.addView(vLtvPren44cbs);

                final TextView vLtvPren44cb = new TextView(FormulActivity.this);
                vLtvPren44cb.setTextSize(16);
                vLtvPren44cb.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren44cb.setLayoutParams(lp);
                vLtvPren44cb.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44cb));
                layout.addView(vLtvPren44cb);

                final EditText vLetPren44cb = new EditText(FormulActivity.this);
                vLetPren44cb.setText(vPren44cb);
                vLetPren44cb.setLayoutParams(lp);
                layout.addView(vLetPren44cb);

                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setView(layout);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn05_pren44c))
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vPren44ca = vLetPren44ca.getText().toString();
                                vPren44cb = vLetPren44cb.getText().toString();
                                vLtvPreN44ac.setText(getResources().getString(R.string.formul_opcn03_subn05_pren44c) + "\n" +
                                        " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44ca) + ": " + vLetPren44ca.getText().toString() + "\n" +
                                        " - " + getResources().getString(R.string.formul_opcn03_subn05_pren44cb) + ": " + vLetPren44cb.getText().toString());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN45 = getResources().getStringArray(R.array.formul_opcn03_subn05_pren45);

        vLtvPreN45.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn05_pren45))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN45, iPreN45, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN45 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN45.setText(vArrPreN45[iPreN45]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        Calendar vLcaPreN46 = Calendar.getInstance();

        int vLinPreN46Y = vLcaPreN46.get(Calendar.YEAR);
        int vLinPreN46M = vLcaPreN46.get(Calendar.MONTH);
        int vLinPreN46D = vLcaPreN46.get(Calendar.DAY_OF_MONTH);

        vLetPreN46.setText(vLinPreN46D + "/" + ((vLinPreN46M + 1) < 10 ? "0" + (vLinPreN46M + 1) : "" + (vLinPreN46M + 1)) + "/" + vLinPreN46Y);

        DatePickerDialog vLdpPreN46 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN46.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN46Y, vLinPreN46M, vLinPreN46D);

        Calendar vLcaPreN46N = Calendar.getInstance();
        vLcaPreN46N.add(Calendar.MINUTE, +1);

        vLdpPreN46.getDatePicker().setMaxDate(vLcaPreN46N.getTimeInMillis());

        vLetPreN46.setInputType(InputType.TYPE_NULL);
        vLetPreN46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN46.show();
            }
        });

        String[] vArrPreN47 = getResources().getStringArray(R.array.formul_opcn03_subn06_pren47);
        boolean[] vArrPreN47boolean = new boolean[vArrPreN47.length];
        for (int i = 0; i < vArrPreN47.length; i++) {
            vArrPreN47boolean[i] = false;
        }

        vLtvPreN47.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn06_pren47))
                        .setMultiChoiceItems(vArrPreN47, vArrPreN47boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN47boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN47.setText(" ");
                                iPreN47 = 0;
                                for (int i = 0; i < vArrPreN47.length; i++) {
                                    if (vArrPreN47boolean[i]) {
                                        if(iPreN47 == 0){
                                            vLtvPreN47.setText(vArrPreN47[i]);
                                            iPreN47 = 1;
                                        }else{
                                            vLtvPreN47.setText(vLtvPreN47.getText().toString() + "\n" + vArrPreN47[i]);
                                            iPreN47 = 1;
                                        }
                                    }
                                }
                                if(iPreN47 == 0){
                                    vLtvPreN47.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

//        String[] vArrPreN48 = getResources().getStringArray(R.array.formul_opcn03_subn07_pren48);
//        boolean[] vArrPreN48boolean = new boolean[vArrPreN48.length];
//        for (int i = 0; i < vArrPreN48.length; i++) {
//            vArrPreN48boolean[i] = false;
//        }
//
//        vLtvPreN48.setOnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
//                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn07_pren48ad))
//                        .setMultiChoiceItems(vArrPreN48, vArrPreN48boolean, new DialogInterface.OnMultiChoiceClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                                vArrPreN48boolean[which] = isChecked;
//                            }
//                        })
//                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                vLtvPreN48.setText(" ");
//                                iPreN48 = 0;
//                                for (int i = 0; i < vArrPreN48.length; i++) {
//                                    if (vArrPreN48boolean[i]) {
//                                        if(iPreN48 == 0){
//                                            vLtvPreN48.setText(vArrPreN48[i]);
//                                            iPreN48 = 1;
//                                        }else{
//                                            vLtvPreN48.setText(vLtvPreN48.getText().toString() + "\n" + vArrPreN48[i]);
//                                            iPreN48 = 1;
//                                        }
//                                    }
//                                }
//                                if(iPreN48 == 0){
//                                    vLtvPreN48.setText(getResources().getString(R.string.formul_selecc));
//                                }
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
//                builder.create().show();
//            }
//        });

        String[] vArrPreN48 = getResources().getStringArray(R.array.formul_opcn03_subn07_pren48);

        vLtvPreN48.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn07_pren48))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN48, iPreN48, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN48 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN48.setText(vArrPreN48[iPreN48]);
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_01))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.GONE);
                                    vLllPreN48c.setVisibility(View.GONE);
                                    vLllPreN48d.setVisibility(View.GONE);
                                    vLllPreN48e.setVisibility(View.GONE);
                                    vLllPreN48f.setVisibility(View.GONE);
                                    vLllPreN48g.setVisibility(View.GONE);
                                    vLllPreN48h.setVisibility(View.GONE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_02))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.GONE);
                                    vLllPreN48d.setVisibility(View.GONE);
                                    vLllPreN48e.setVisibility(View.GONE);
                                    vLllPreN48f.setVisibility(View.GONE);
                                    vLllPreN48g.setVisibility(View.GONE);
                                    vLllPreN48h.setVisibility(View.GONE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_03))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.GONE);
                                    vLllPreN48e.setVisibility(View.GONE);
                                    vLllPreN48f.setVisibility(View.GONE);
                                    vLllPreN48g.setVisibility(View.GONE);
                                    vLllPreN48h.setVisibility(View.GONE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_04))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.VISIBLE);
                                    vLllPreN48e.setVisibility(View.GONE);
                                    vLllPreN48f.setVisibility(View.GONE);
                                    vLllPreN48g.setVisibility(View.GONE);
                                    vLllPreN48h.setVisibility(View.GONE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_05))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.VISIBLE);
                                    vLllPreN48e.setVisibility(View.VISIBLE);
                                    vLllPreN48f.setVisibility(View.GONE);
                                    vLllPreN48g.setVisibility(View.GONE);
                                    vLllPreN48h.setVisibility(View.GONE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_06))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.VISIBLE);
                                    vLllPreN48e.setVisibility(View.VISIBLE);
                                    vLllPreN48f.setVisibility(View.VISIBLE);
                                    vLllPreN48g.setVisibility(View.GONE);
                                    vLllPreN48h.setVisibility(View.GONE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_07))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.VISIBLE);
                                    vLllPreN48e.setVisibility(View.VISIBLE);
                                    vLllPreN48f.setVisibility(View.VISIBLE);
                                    vLllPreN48g.setVisibility(View.VISIBLE);
                                    vLllPreN48h.setVisibility(View.GONE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_08))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.VISIBLE);
                                    vLllPreN48e.setVisibility(View.VISIBLE);
                                    vLllPreN48f.setVisibility(View.VISIBLE);
                                    vLllPreN48g.setVisibility(View.VISIBLE);
                                    vLllPreN48h.setVisibility(View.VISIBLE);
                                    vLllPreN48i.setVisibility(View.GONE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_09))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.VISIBLE);
                                    vLllPreN48e.setVisibility(View.VISIBLE);
                                    vLllPreN48f.setVisibility(View.VISIBLE);
                                    vLllPreN48g.setVisibility(View.VISIBLE);
                                    vLllPreN48h.setVisibility(View.VISIBLE);
                                    vLllPreN48i.setVisibility(View.VISIBLE);
                                    vLllPreN48j.setVisibility(View.GONE);
                                }
                                if(vArrPreN48[iPreN48].equals(getResources().getString(R.string.formul_opcn03_subn07_pren48_10))){
                                    vLllPreN48a.setVisibility(View.VISIBLE);
                                    vLllPreN48b.setVisibility(View.VISIBLE);
                                    vLllPreN48c.setVisibility(View.VISIBLE);
                                    vLllPreN48d.setVisibility(View.VISIBLE);
                                    vLllPreN48e.setVisibility(View.VISIBLE);
                                    vLllPreN48f.setVisibility(View.VISIBLE);
                                    vLllPreN48g.setVisibility(View.VISIBLE);
                                    vLllPreN48h.setVisibility(View.VISIBLE);
                                    vLllPreN48i.setVisibility(View.VISIBLE);
                                    vLllPreN48j.setVisibility(View.VISIBLE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vLtvPreN48a.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout layout = new LinearLayout(FormulActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(70, 0, 70, 0);

                final TextView vLtvPren48aas = new TextView(FormulActivity.this);
                layout.addView(vLtvPren48aas);

                final TextView vLtvPren48aa = new TextView(FormulActivity.this);
                vLtvPren48aa.setTextSize(16);
                vLtvPren48aa.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren48aa.setLayoutParams(lp);
                vLtvPren48aa.setText(getResources().getString(R.string.formul_opcn03_subn07_pren48aa));
                layout.addView(vLtvPren48aa);

                final EditText vLetPren48aa = new EditText(FormulActivity.this);
                vLetPren48aa.setText(vPren48aa);
                vLetPren48aa.setLayoutParams(lp);
                layout.addView(vLetPren48aa);

                final TextView vLtvPren48abs = new TextView(FormulActivity.this);
                layout.addView(vLtvPren48abs);

                final TextView vLtvPren48ab = new TextView(FormulActivity.this);
                vLtvPren48ab.setTextSize(16);
                vLtvPren48ab.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren48ab.setLayoutParams(lp);
                vLtvPren48ab.setText(getResources().getString(R.string.formul_opcn03_subn07_pren48ab));
                layout.addView(vLtvPren48ab);

                final EditText vLetPren48ab = new EditText(FormulActivity.this);
                vLetPren48ab.setInputType(InputType.TYPE_CLASS_NUMBER);
                vLetPren48ab.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
                vLetPren48ab.setText(vPren48ab);
                vLetPren48ab.setLayoutParams(lp);
                layout.addView(vLetPren48ab);

                final TextView vLtvPren48acs = new TextView(FormulActivity.this);
                layout.addView(vLtvPren48acs);

                final TextView vLtvPren48ac = new TextView(FormulActivity.this);
                vLtvPren48ac.setTextSize(16);
                vLtvPren48ac.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren48ac.setLayoutParams(lp);
                vLtvPren48ac.setText(getResources().getString(R.string.formul_opcn03_subn07_pren48ac));
                layout.addView(vLtvPren48ac);

                final EditText vLetPren48ac = new EditText(FormulActivity.this);
                vLetPren48ac.setInputType(InputType.TYPE_CLASS_NUMBER);
                vLetPren48ac.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
                vLetPren48ac.setText(vPren48ac);
                vLetPren48ac.setLayoutParams(lp);
                layout.addView(vLetPren48ac);

                vLetPren48ac.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {}
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String regex = "9[0-9]{8}";
                        if(s.length() == 0){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 1 && !vLetPren48ac.getText().toString().matches("9")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 2 && !vLetPren48ac.getText().toString().matches("9[0-9]{1}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 3 && !vLetPren48ac.getText().toString().matches("9[0-9]{2}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 4 && !vLetPren48ac.getText().toString().matches("9[0-9]{3}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 5 && !vLetPren48ac.getText().toString().matches("9[0-9]{4}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 6 && !vLetPren48ac.getText().toString().matches("9[0-9]{5}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 7 && !vLetPren48ac.getText().toString().matches("9[0-9]{6}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 8 && !vLetPren48ac.getText().toString().matches("9[0-9]{7}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                        if(s.length() == 9 && !vLetPren48ac.getText().toString().matches("9[0-9]{8}")){
                            vLetPren48ac.setText("9");
                            vLetPren48ac.requestFocus();
                            vLetPren48ac.setSelection(vLetPren48ac.getText().length());
                        }
                    }
                });

                final TextView vLtvPren48ads = new TextView(FormulActivity.this);
                layout.addView(vLtvPren48ads);

                final TextView vLtvPren48ad = new TextView(FormulActivity.this);
                vLtvPren48ad.setTextSize(16);
                vLtvPren48ad.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren48ad.setLayoutParams(lp);
                vLtvPren48ad.setText(getResources().getString(R.string.formul_opcn03_subn07_pren48ad));
                layout.addView(vLtvPren48ad);

                final CheckBox vLcbPren48ada = new CheckBox(FormulActivity.this);
                final CheckBox vLcbPren48adm = new CheckBox(FormulActivity.this);

                vLcbPren48ada.setTextSize(16);
                vLcbPren48ada.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48ada.setLayoutParams(lp);
                vLcbPren48ada.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48ada));
                vLcbPren48ada.setChecked(vPren48ada);
                vLcbPren48ada.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            if(vLcbPren48adm.isChecked()){
                                vLcbPren48ada.setChecked(false);
                            }else{
                                vLcbPren48ada.setChecked(true);
                            }
                        }
                    }
                });
                layout.addView(vLcbPren48ada);

                final CheckBox vLcbPren48adb = new CheckBox(FormulActivity.this);
                vLcbPren48adb.setTextSize(16);
                vLcbPren48adb.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adb.setLayoutParams(lp);
                vLcbPren48adb.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adb));
                vLcbPren48adb.setChecked(vPren48adb);
                layout.addView(vLcbPren48adb);

                final CheckBox vLcbPren48adc = new CheckBox(FormulActivity.this);
                vLcbPren48adc.setTextSize(16);
                vLcbPren48adc.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adc.setLayoutParams(lp);
                vLcbPren48adc.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adc));
                vLcbPren48adc.setChecked(vPren48adc);
                layout.addView(vLcbPren48adc);

                final CheckBox vLcbPren48add = new CheckBox(FormulActivity.this);
                vLcbPren48add.setTextSize(16);
                vLcbPren48add.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48add.setLayoutParams(lp);
                vLcbPren48add.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48add));
                vLcbPren48add.setChecked(vPren48add);
                layout.addView(vLcbPren48add);

                final CheckBox vLcbPren48ade = new CheckBox(FormulActivity.this);
                vLcbPren48ade.setTextSize(16);
                vLcbPren48ade.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48ade.setLayoutParams(lp);
                vLcbPren48ade.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48ade));
                vLcbPren48ade.setChecked(vPren48ade);
                layout.addView(vLcbPren48ade);

                final CheckBox vLcbPren48adf = new CheckBox(FormulActivity.this);
                vLcbPren48adf.setTextSize(16);
                vLcbPren48adf.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adf.setLayoutParams(lp);
                vLcbPren48adf.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adf));
                vLcbPren48adf.setChecked(vPren48adf);
                layout.addView(vLcbPren48adf);

                final CheckBox vLcbPren48adg = new CheckBox(FormulActivity.this);
                vLcbPren48adg.setTextSize(16);
                vLcbPren48adg.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adg.setLayoutParams(lp);
                vLcbPren48adg.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adg));
                vLcbPren48adg.setChecked(vPren48adg);
                layout.addView(vLcbPren48adg);

                final CheckBox vLcbPren48adh = new CheckBox(FormulActivity.this);
                vLcbPren48adh.setTextSize(16);
                vLcbPren48adh.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adh.setLayoutParams(lp);
                vLcbPren48adh.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adh));
                vLcbPren48adh.setChecked(vPren48adh);
                layout.addView(vLcbPren48adh);

                final CheckBox vLcbPren48adi = new CheckBox(FormulActivity.this);
                vLcbPren48adi.setTextSize(16);
                vLcbPren48adi.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adi.setLayoutParams(lp);
                vLcbPren48adi.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adi));
                vLcbPren48adi.setChecked(vPren48adi);
                layout.addView(vLcbPren48adi);

                final CheckBox vLcbPren48adj = new CheckBox(FormulActivity.this);
                vLcbPren48adj.setTextSize(16);
                vLcbPren48adj.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adj.setLayoutParams(lp);
                vLcbPren48adj.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adj));
                vLcbPren48adj.setChecked(vPren48adj);
                layout.addView(vLcbPren48adj);

                final CheckBox vLcbPren48adk = new CheckBox(FormulActivity.this);
                vLcbPren48adk.setTextSize(16);
                vLcbPren48adk.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adk.setLayoutParams(lp);
                vLcbPren48adk.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adk));
                vLcbPren48adk.setChecked(vPren48adk);
                layout.addView(vLcbPren48adk);

                final CheckBox vLcbPren48adl = new CheckBox(FormulActivity.this);
                vLcbPren48adl.setTextSize(16);
                vLcbPren48adl.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adl.setLayoutParams(lp);
                vLcbPren48adl.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adl));
                vLcbPren48adl.setChecked(vPren48adl);
                layout.addView(vLcbPren48adl);

                vLcbPren48adm.setTextSize(16);
                vLcbPren48adm.setTextColor(getResources().getColor(R.color.colorBlack));
                vLcbPren48adm.setLayoutParams(lp);
                vLcbPren48adm.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48adm));
                vLcbPren48adm.setChecked(vPren48adm);
                vLcbPren48adm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            if(vLcbPren48ada.isChecked()){
                                vLcbPren48adm.setChecked(false);
                            }else{
                                vLcbPren48adm.setChecked(true);
                            }
                        }
                    }
                });
                layout.addView(vLcbPren48adm);

                final TextView vLtvPren48aes = new TextView(FormulActivity.this);
                layout.addView(vLtvPren48aes);

                final TextView vLtvPren48ae = new TextView(FormulActivity.this);
                vLtvPren48ae.setTextSize(16);
                vLtvPren48ae.setTextColor(getResources().getColor(R.color.colorBlack));
                vLtvPren48ae.setLayoutParams(lp);
                vLtvPren48ae.setText(getResources().getString(R.string.formul_opcn03_subn07_pren48ae));
                layout.addView(vLtvPren48ae);

                final RadioGroup vLrgPren48ae = new RadioGroup(FormulActivity.this);

                final RadioButton vLrbPren48aea = new RadioButton(FormulActivity.this);
                vLrbPren48aea.setTextSize(16);
                vLrbPren48aea.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aea.setLayoutParams(lp);
                vLrbPren48aea.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aea));
                vLrbPren48aea.setChecked(vPren48aea);
                vLrgPren48ae.addView(vLrbPren48aea);

                final RadioButton vLrbPren48aeb = new RadioButton(FormulActivity.this);
                vLrbPren48aeb.setTextSize(16);
                vLrbPren48aeb.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aeb.setLayoutParams(lp);
                vLrbPren48aeb.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aeb));
                vLrbPren48aeb.setChecked(vPren48aeb);
                vLrgPren48ae.addView(vLrbPren48aeb);

                final RadioButton vLrbPren48aec = new RadioButton(FormulActivity.this);
                vLrbPren48aec.setTextSize(16);
                vLrbPren48aec.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aec.setLayoutParams(lp);
                vLrbPren48aec.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aec));
                vLrbPren48aec.setChecked(vPren48aec);
                vLrgPren48ae.addView(vLrbPren48aec);

                final RadioButton vLrbPren48aed = new RadioButton(FormulActivity.this);
                vLrbPren48aed.setTextSize(16);
                vLrbPren48aed.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aed.setLayoutParams(lp);
                vLrbPren48aed.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aed));
                vLrbPren48aed.setChecked(vPren48aed);
                vLrgPren48ae.addView(vLrbPren48aed);

                final RadioButton vLrbPren48aee = new RadioButton(FormulActivity.this);
                vLrbPren48aee.setTextSize(16);
                vLrbPren48aee.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aee.setLayoutParams(lp);
                vLrbPren48aee.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aee));
                vLrbPren48aee.setChecked(vPren48aee);
                vLrgPren48ae.addView(vLrbPren48aee);

                final RadioButton vLrbPren48aef = new RadioButton(FormulActivity.this);
                vLrbPren48aef.setTextSize(16);
                vLrbPren48aef.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aef.setLayoutParams(lp);
                vLrbPren48aef.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aef));
                vLrbPren48aef.setChecked(vPren48aef);
                vLrgPren48ae.addView(vLrbPren48aef);

                final RadioButton vLrbPren48aeg = new RadioButton(FormulActivity.this);
                vLrbPren48aeg.setTextSize(16);
                vLrbPren48aeg.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aeg.setLayoutParams(lp);
                vLrbPren48aeg.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aeg));
                vLrbPren48aeg.setChecked(vPren48aeg);
                vLrgPren48ae.addView(vLrbPren48aeg);

                final RadioButton vLrbPren48aeh = new RadioButton(FormulActivity.this);
                vLrbPren48aeh.setTextSize(16);
                vLrbPren48aeh.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aeh.setLayoutParams(lp);
                vLrbPren48aeh.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aeh));
                vLrbPren48aeh.setChecked(vPren48aeh);
                vLrgPren48ae.addView(vLrbPren48aeh);

                final RadioButton vLrbPren48aei = new RadioButton(FormulActivity.this);
                vLrbPren48aei.setTextSize(16);
                vLrbPren48aei.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aei.setLayoutParams(lp);
                vLrbPren48aei.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aei));
                vLrbPren48aei.setChecked(vPren48aei);
                vLrgPren48ae.addView(vLrbPren48aei);

                final RadioButton vLrbPren48aej = new RadioButton(FormulActivity.this);
                vLrbPren48aej.setTextSize(16);
                vLrbPren48aej.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aej.setLayoutParams(lp);
                vLrbPren48aej.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aej));
                vLrbPren48aej.setChecked(vPren48aej);
                vLrgPren48ae.addView(vLrbPren48aej);

                final RadioButton vLrbPren48aek = new RadioButton(FormulActivity.this);
                vLrbPren48aek.setTextSize(16);
                vLrbPren48aek.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aek.setLayoutParams(lp);
                vLrbPren48aek.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aek));
                vLrbPren48aek.setChecked(vPren48aek);
                vLrgPren48ae.addView(vLrbPren48aek);

                final RadioButton vLrbPren48ael = new RadioButton(FormulActivity.this);
                vLrbPren48ael.setTextSize(16);
                vLrbPren48ael.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48ael.setLayoutParams(lp);
                vLrbPren48ael.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48ael));
                vLrbPren48ael.setChecked(vPren48ael);
                vLrgPren48ae.addView(vLrbPren48ael);

                final RadioButton vLrbPren48aem = new RadioButton(FormulActivity.this);
                vLrbPren48aem.setTextSize(16);
                vLrbPren48aem.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aem.setLayoutParams(lp);
                vLrbPren48aem.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aem));
                vLrbPren48aem.setChecked(vPren48aem);
                vLrgPren48ae.addView(vLrbPren48aem);

                final RadioButton vLrbPren48aen = new RadioButton(FormulActivity.this);
                vLrbPren48aen.setTextSize(16);
                vLrbPren48aen.setTextColor(getResources().getColor(R.color.colorBlack));
                vLrbPren48aen.setLayoutParams(lp);
                vLrbPren48aen.setText(getResources().getString(R.string.formul_opcn03_subn07_resn48aen));
                vLrbPren48aen.setChecked(vPren48aen);
                vLrgPren48ae.addView(vLrbPren48aen);

                layout.addView(vLrgPren48ae);

                ScrollView scroll = new ScrollView(FormulActivity.this);
                scroll.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                scroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT));
                scroll.addView(layout);

                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setView(scroll);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn07_pren48a))
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vPren48aa = vLetPren48aa.getText().toString();
                                vPren48ab = vLetPren48ab.getText().toString();
                                vPren48ac = vLetPren48ac.getText().toString();
                                String vTexto48 = getResources().getString(R.string.formul_opcn03_subn07_pren48a) + "\n" +
                                        "- " + getResources().getString(R.string.formul_opcn03_subn07_pren48aa) + ": " + vLetPren48aa.getText().toString() + "\n" +
                                        "- " + getResources().getString(R.string.formul_opcn03_subn07_pren48ab) + ": " + vLetPren48ab.getText().toString() + "\n" +
                                        "- " + getResources().getString(R.string.formul_opcn03_subn07_pren48ac) + ": " + vLetPren48ac.getText().toString();
                                if(vLcbPren48ada.isChecked()||vLcbPren48adb.isChecked()||vLcbPren48adc.isChecked()||vLcbPren48add.isChecked()||vLcbPren48ade.isChecked()||
                                        vLcbPren48adf.isChecked()||vLcbPren48adg.isChecked()||vLcbPren48adh.isChecked()||vLcbPren48adi.isChecked()||
                                        vLcbPren48adj.isChecked()||vLcbPren48adk.isChecked()||vLcbPren48adl.isChecked()||vLcbPren48adm.isChecked()){
                                    vTexto48 = vTexto48 + "\n" + "- " + getResources().getString(R.string.formul_opcn03_subn07_pren48ad) + ":";
                                    if(vLcbPren48ada.isChecked()){vPren48ada = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48ada);}else{vPren48ada = false;}
                                    if(vLcbPren48adb.isChecked()){vPren48adb = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adb);}else{vPren48adb = false;}
                                    if(vLcbPren48adc.isChecked()){vPren48adc = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adc);}else{vPren48adc = false;}
                                    if(vLcbPren48adb.isChecked()){vPren48add = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48add);}else{vPren48add = false;}
                                    if(vLcbPren48ade.isChecked()){vPren48ade = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48ade);}else{vPren48ade = false;}
                                    if(vLcbPren48adf.isChecked()){vPren48adf = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adf);}else{vPren48adf = false;}
                                    if(vLcbPren48adg.isChecked()){vPren48adg = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adg);}else{vPren48adg = false;}
                                    if(vLcbPren48adh.isChecked()){vPren48adh = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adh);}else{vPren48adh = false;}
                                    if(vLcbPren48adi.isChecked()){vPren48adi = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adi);}else{vPren48adi = false;}
                                    if(vLcbPren48adj.isChecked()){vPren48adj = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adj);}else{vPren48adj = false;}
                                    if(vLcbPren48adk.isChecked()){vPren48adk = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adk);}else{vPren48adk = false;}
                                    if(vLcbPren48adl.isChecked()){vPren48adl = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adl);}else{vPren48adl = false;}
                                    if(vLcbPren48adm.isChecked()){vPren48adm = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48adm);}else{vPren48adm = false;}
                                }
                                if(vLrbPren48aea.isChecked()||vLrbPren48aeb.isChecked()||vLrbPren48aec.isChecked()||vLrbPren48aed.isChecked()||vLrbPren48aee.isChecked()||
                                        vLrbPren48aef.isChecked()||vLrbPren48aeg.isChecked()||vLrbPren48aeh.isChecked()||vLrbPren48aei.isChecked()||
                                        vLrbPren48aej.isChecked()||vLrbPren48aek.isChecked()||vLrbPren48ael.isChecked()||vLrbPren48aem.isChecked()||vLrbPren48aen.isChecked()){
                                    vTexto48 = vTexto48 + "\n" + "- " + getResources().getString(R.string.formul_opcn03_subn07_pren48ae) + ":";
                                    if(vLrbPren48aea.isChecked()){vPren48aea = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aea);}else{vPren48aea = false;}
                                    if(vLrbPren48aeb.isChecked()){vPren48aeb = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aeb);}else{vPren48aeb = false;}
                                    if(vLrbPren48aec.isChecked()){vPren48aec = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aec);}else{vPren48aec = false;}
                                    if(vLrbPren48aeb.isChecked()){vPren48aed = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aed);}else{vPren48aed = false;}
                                    if(vLrbPren48aee.isChecked()){vPren48aee = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aee);}else{vPren48aee = false;}
                                    if(vLrbPren48aef.isChecked()){vPren48aef = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aef);}else{vPren48aef = false;}
                                    if(vLrbPren48aeg.isChecked()){vPren48aeg = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aeg);}else{vPren48aeg = false;}
                                    if(vLrbPren48aeh.isChecked()){vPren48aeh = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aeh);}else{vPren48aeh = false;}
                                    if(vLrbPren48aei.isChecked()){vPren48aei = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aei);}else{vPren48aei = false;}
                                    if(vLrbPren48aej.isChecked()){vPren48aej = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aej);}else{vPren48aej = false;}
                                    if(vLrbPren48aek.isChecked()){vPren48aek = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aek);}else{vPren48aek = false;}
                                    if(vLrbPren48ael.isChecked()){vPren48ael = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48ael);}else{vPren48ael = false;}
                                    if(vLrbPren48aem.isChecked()){vPren48aem = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aem);}else{vPren48aem = false;}
                                    if(vLrbPren48aen.isChecked()){vPren48aen = true;vTexto48=vTexto48+"\n"+"- - "+getResources().getString(R.string.formul_opcn03_subn07_resn48aen);}else{vPren48aen = false;}
                                }
                                vLtvPreN48a.setText(vTexto48);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN49 = getResources().getStringArray(R.array.formul_opcn03_subn07_pren49);

        vLtvPreN49.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn03_subn07_pren49))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN49, iPreN49, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN49 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN49.setText(vArrPreN49[iPreN49]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        Calendar vLcaPreN50 = Calendar.getInstance();

        int vLinPreN50Y = vLcaPreN50.get(Calendar.YEAR);
        int vLinPreN50M = vLcaPreN50.get(Calendar.MONTH);
        int vLinPreN50D = vLcaPreN50.get(Calendar.DAY_OF_MONTH);

        vLetPreN50.setText(vLinPreN50D + "/" + ((vLinPreN50M + 1) < 10 ? "0" + (vLinPreN50M + 1) : "" + (vLinPreN50M + 1)) + "/" + vLinPreN50Y);

        DatePickerDialog vLdpPreN50 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN50.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN50Y, vLinPreN50M, vLinPreN50D);

        Calendar vLcaPreN50N = Calendar.getInstance();
        vLcaPreN50N.add(Calendar.MINUTE, +1);

        vLdpPreN50.getDatePicker().setMaxDate(vLcaPreN50N.getTimeInMillis());

        vLetPreN50.setInputType(InputType.TYPE_NULL);
        vLetPreN50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN50.show();
            }
        });

        Calendar vLcaPreN51 = Calendar.getInstance();

        int vLinPreN51Y = vLcaPreN51.get(Calendar.YEAR);
        int vLinPreN51M = vLcaPreN51.get(Calendar.MONTH);
        int vLinPreN51D = vLcaPreN51.get(Calendar.DAY_OF_MONTH);

        vLetPreN51.setText(vLinPreN51D + "/" + ((vLinPreN51M + 1) < 10 ? "0" + (vLinPreN51M + 1) : "" + (vLinPreN51M + 1)) + "/" + vLinPreN51Y);

        DatePickerDialog vLdpPreN51 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN51.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN51Y, vLinPreN51M, vLinPreN51D);

        Calendar vLcaPreN51N = Calendar.getInstance();
        vLcaPreN51N.add(Calendar.MINUTE, +1);

        vLdpPreN51.getDatePicker().setMaxDate(vLcaPreN51N.getTimeInMillis());

        vLetPreN51.setInputType(InputType.TYPE_NULL);
        vLetPreN51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN51.show();
            }
        });

        Calendar vLcaPreN52 = Calendar.getInstance();

        int vLinPreN52Y = vLcaPreN52.get(Calendar.YEAR);
        int vLinPreN52M = vLcaPreN52.get(Calendar.MONTH);
        int vLinPreN52D = vLcaPreN52.get(Calendar.DAY_OF_MONTH);

        vLetPreN52.setText(vLinPreN52D + "/" + ((vLinPreN52M + 1) < 10 ? "0" + (vLinPreN52M + 1) : "" + (vLinPreN52M + 1)) + "/" + vLinPreN52Y);

        DatePickerDialog vLdpPreN52 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN52.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN52Y, vLinPreN52M, vLinPreN52D);

        Calendar vLcaPreN52N = Calendar.getInstance();
        vLcaPreN52N.add(Calendar.MINUTE, +1);

        vLdpPreN52.getDatePicker().setMaxDate(vLcaPreN52N.getTimeInMillis());

        vLetPreN52.setInputType(InputType.TYPE_NULL);
        vLetPreN52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN52.show();
            }
        });

        vLetPreN53.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetPreN53.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });

        Calendar vLcaPreN54 = Calendar.getInstance();

        int vLinPreN54Y = vLcaPreN54.get(Calendar.YEAR);
        int vLinPreN54M = vLcaPreN54.get(Calendar.MONTH);
        int vLinPreN54D = vLcaPreN54.get(Calendar.DAY_OF_MONTH);

        vLetPreN54.setText(vLinPreN54D + "/" + ((vLinPreN54M + 1) < 10 ? "0" + (vLinPreN54M + 1) : "" + (vLinPreN54M + 1)) + "/" + vLinPreN54Y);

        DatePickerDialog vLdpPreN54 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int ImonthOfYear = monthOfYear + 1;
                String selectedMonthOfYear = ImonthOfYear < 10 ? "0" + ImonthOfYear : "" + ImonthOfYear;
                String selectedDayOfMonth = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
                vLetPreN54.setText(selectedDayOfMonth + "/" + selectedMonthOfYear + "/" + year);
            }

        }, vLinPreN54Y, vLinPreN54M, vLinPreN54D);

        Calendar vLcaPreN54N = Calendar.getInstance();
        vLcaPreN54N.add(Calendar.MINUTE, +1);

        vLdpPreN54.getDatePicker().setMaxDate(vLcaPreN54N.getTimeInMillis());

        vLetPreN54.setInputType(InputType.TYPE_NULL);
        vLetPreN54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(FormulActivity.this);
                vLdpPreN54.show();
            }
        });

        String[] vArrPreN55 = getResources().getStringArray(R.array.formul_opcn04_subn02_pren55);

        vLtvPreN55.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn04_subn02_pren55))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN55, iPreN55, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN55 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN55.setText(vArrPreN55[iPreN55]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vLetPreN56.setInputType(InputType.TYPE_CLASS_TEXT);
        vLetPreN56.setFilters(new InputFilter[] { new PartialRegexInputFilter("^([2-9][0-9]|[1-2][0-9][0-9]|300)(\\/)([2-9][0-9]|[1-2][0-9][0-9]|300)$") });

        vLetPreN57.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetPreN57.setFilters(new InputFilter[] { new PartialRegexInputFilter("^([2-9][0-9]|[1][0-9][0-9]|200)$") });

        vLetPreN58.setInputType(InputType.TYPE_CLASS_NUMBER);
        vLetPreN58.setFilters(new InputFilter[] { new PartialRegexInputFilter("^([0-9]|[1-7][0-9]|80)$") });

        vLetPreN59.setInputType(InputType.TYPE_CLASS_TEXT);
        vLetPreN59.setFilters(new InputFilter[] { new PartialRegexInputFilter("^([0-9][\\.][0-9]|[1-4][0-9][\\.][0-9]|50[\\.][0])$") });

        String[] vArrPreN60 = getResources().getStringArray(R.array.formul_opcn04_subn04_pren60);

        vLtvPreN60.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn04_subn04_pren60))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN60, iPreN60, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN60 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN60.setText(vArrPreN60[iPreN60]);
                                if(vArrPreN60[iPreN60].equals(getResources().getString(R.string.formul_opcn04_subn04_resn60a))){
                                    vLllPreN61.setVisibility(View.VISIBLE);
                                    vLllPreN61.requestFocus();
                                }
                                if(vArrPreN60[iPreN60].equals(getResources().getString(R.string.formul_opcn04_subn04_resn60b))){
                                    vLllPreN61.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN61 = getResources().getStringArray(R.array.formul_opcn04_subn04_pren61);
        boolean[] vArrPreN61boolean = new boolean[vArrPreN61.length];
        for (int i = 0; i < vArrPreN61.length; i++) {
            vArrPreN61boolean[i] = false;
        }

        vLtvPreN61.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn04_subn04_pren61))
                        .setMultiChoiceItems(vArrPreN61, vArrPreN61boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN61boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN61.setText(" ");
                                iPreN61 = 0;
                                for (int i = 0; i < vArrPreN61.length; i++) {
                                    if (vArrPreN61boolean[i]) {
                                        if(iPreN61 == 0){
                                            vLtvPreN61.setText(vArrPreN61[i]);
                                            iPreN61 = 1;
                                        }else{
                                            vLtvPreN61.setText(vLtvPreN61.getText().toString() + "\n" + vArrPreN61[i]);
                                            iPreN61 = 1;
                                        }
                                    }
                                    if(vArrPreN61[i].equals(getResources().getString(R.string.formul_opcn04_subn04_resn61j))) {
                                        if (vArrPreN61boolean[i]) {
                                            vLllPreN61a.setVisibility(View.VISIBLE);
                                            vLllPreN61a.requestFocus();
                                        } else {
                                            vLllPreN61a.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                if(iPreN61 == 0){
                                    vLtvPreN61.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN62 = getResources().getStringArray(R.array.formul_opcn04_subn05_pren62);
        boolean[] vArrPreN62boolean = new boolean[vArrPreN62.length];
        for (int i = 0; i < vArrPreN62.length; i++) {
            vArrPreN62boolean[i] = false;
        }

        vLtvPreN62.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn04_subn05_pren62))
                        .setMultiChoiceItems(vArrPreN62, vArrPreN62boolean, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                vArrPreN62boolean[which] = isChecked;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN62.setText(" ");
                                iPreN62 = 0;
                                for (int i = 0; i < vArrPreN62.length; i++) {
                                    if (vArrPreN62boolean[i]) {
                                        if(iPreN62 == 0){
                                            vLtvPreN62.setText(vArrPreN62[i]);
                                            iPreN62 = 1;
                                        }else{
                                            vLtvPreN62.setText(vLtvPreN62.getText().toString() + "\n" + vArrPreN62[i]);
                                            iPreN62 = 1;
                                        }
                                    }
                                    if(vArrPreN62[i].equals(getResources().getString(R.string.formul_opcn04_subn05_resn62f))) {
                                        if (vArrPreN62boolean[i]) {
                                            vLllPreN62a.setVisibility(View.VISIBLE);
                                            vLllPreN62a.requestFocus();
                                        } else {
                                            vLllPreN62a.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                if(iPreN62 == 0){
                                    vLtvPreN62.setText(getResources().getString(R.string.formul_selecc));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        String[] vArrPreN63 = getResources().getStringArray(R.array.formul_opcn04_subn06_pren63);

        vLtvPreN63.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormulActivity.this);
                builder.setTitle(getResources().getString(R.string.formul_opcn04_subn06_pren63))
                        //.setMessage("You can buy our products without registration too. Enjoy the shopping")
                        .setSingleChoiceItems(vArrPreN63, iPreN63, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                iPreN63 = which;
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.formul_acepta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vLtvPreN63.setText(vArrPreN63[iPreN63]);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.formul_cancel), null);
                builder.create().show();
            }
        });

        vAanteri = AnimationUtils.loadAnimation(this, R.anim.anterior);
        vAanteri2 = AnimationUtils.loadAnimation(this, R.anim.anterior2);
        vAsiguie = AnimationUtils.loadAnimation(this, R.anim.siguiente);
        vAsiguie2 = AnimationUtils.loadAnimation(this, R.anim.siguiente2);
        cPagina = 1;

        vLtvAnteri = (TextView) findViewById(R.id.LtvAnteri);
        vLtvSiguie = (TextView) findViewById(R.id.LtvSiguie);

        vLtvAnteri.setVisibility(View.GONE);

        vLtvSiguie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                cSiguie = 0;
                if (cPagina == 1 && cSiguie == 0) {
                    vLsvPart01.startAnimation(vAsiguie2);
                    vLsvPart01.setVisibility(View.GONE);
                    vLsvPart02.startAnimation(vAsiguie);
                    vLsvPart02.setVisibility(View.VISIBLE);
                    vLtvAnteri.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn01));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn01_subn02));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 2;
                    cSiguie = 1;
                }
                if (cPagina == 2 && cSiguie == 0) {
                    vLsvPart02.startAnimation(vAsiguie2);
                    vLsvPart02.setVisibility(View.GONE);
                    vLsvPart03.startAnimation(vAsiguie);
                    vLsvPart03.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn01));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn01_subn03));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 3;
                    cSiguie = 1;
                }
                if (cPagina == 3 && cSiguie == 0) {
                    vLsvPart03.startAnimation(vAsiguie2);
                    vLsvPart03.setVisibility(View.GONE);
                    vLsvPart04.startAnimation(vAsiguie);
                    vLsvPart04.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn04));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 4;
                    cSiguie = 1;
                }
                if (cPagina == 4 && cSiguie == 0) {
                    vLsvPart04.startAnimation(vAsiguie2);
                    vLsvPart04.setVisibility(View.GONE);
                    vLsvPart05.startAnimation(vAsiguie);
                    vLsvPart05.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn05));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 5;
                    cSiguie = 1;
                }
                if(cPagina == 5 && cSiguie == 0){
                    vLsvPart05.startAnimation(vAsiguie2);
                    vLsvPart05.setVisibility(View.GONE);
                    vLsvPart06.startAnimation(vAsiguie);
                    vLsvPart06.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn07));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 6;
                    cSiguie = 1;
                }
                if(cPagina == 6 && cSiguie == 0) {
                    vLsvPart06.startAnimation(vAsiguie2);
                    vLsvPart06.setVisibility(View.GONE);
                    vLsvPart07.startAnimation(vAsiguie);
                    vLsvPart07.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn08));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 7;
                    cSiguie = 1;
                }
                if(cPagina == 7 && cSiguie == 0) {
                    vLsvPart07.startAnimation(vAsiguie2);
                    vLsvPart07.setVisibility(View.GONE);
                    vLsvPart08.startAnimation(vAsiguie);
                    vLsvPart08.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn09));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 8;
                    cSiguie = 1;
                }
                if(cPagina == 8 && cSiguie == 0){
                    vLsvPart08.startAnimation(vAsiguie2);
                    vLsvPart08.setVisibility(View.GONE);
                    vLsvPart09.startAnimation(vAsiguie);
                    vLsvPart09.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn10));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 9;
                    cSiguie = 1;
                }
                if(cPagina == 9 && cSiguie == 0){
                    vLsvPart09.startAnimation(vAsiguie2);
                    vLsvPart09.setVisibility(View.GONE);
                    vLsvPart10.startAnimation(vAsiguie);
                    vLsvPart10.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn11));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 10;
                    cSiguie = 1;
                }
                if(cPagina == 10 && cSiguie == 0){
                    vLsvPart10.startAnimation(vAsiguie2);
                    vLsvPart10.setVisibility(View.GONE);
                    vLsvPart11.startAnimation(vAsiguie);
                    vLsvPart11.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn12));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 11;
                    cSiguie = 1;
                }
                if(cPagina == 11 && cSiguie == 0){
                    vLsvPart11.startAnimation(vAsiguie2);
                    vLsvPart11.setVisibility(View.GONE);
                    vLsvPart12.startAnimation(vAsiguie);
                    vLsvPart12.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn13));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 12;
                    cSiguie = 1;
                }
                if(cPagina == 12 && cSiguie == 0){
                    vLsvPart12.startAnimation(vAsiguie2);
                    vLsvPart12.setVisibility(View.GONE);
                    vLsvPart13.startAnimation(vAsiguie);
                    vLsvPart13.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn14));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 13;
                    cSiguie = 1;
                }
                if(cPagina == 13 && cSiguie == 0){
                    vLsvPart13.startAnimation(vAsiguie2);
                    vLsvPart13.setVisibility(View.GONE);
                    vLsvPart14.startAnimation(vAsiguie);
                    vLsvPart14.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn15));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 14;
                    cSiguie = 1;
                }
                if(cPagina == 14 && cSiguie == 0){
                    vLsvPart14.startAnimation(vAsiguie2);
                    vLsvPart14.setVisibility(View.GONE);
                    vLsvPart15.startAnimation(vAsiguie);
                    vLsvPart15.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn16));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_finali));
                    cPagina = 15;
                    cSiguie = 1;
                }
            }
        });

        vLtvAnteri.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                cAnteri = 0;
                if(cPagina == 2 && cAnteri == 0){
                    vLsvPart02.startAnimation(vAanteri);
                    vLsvPart02.setVisibility(View.GONE);
                    vLsvPart01.startAnimation(vAanteri2);
                    vLsvPart01.setVisibility(View.VISIBLE);
                    vLtvAnteri.setVisibility(View.GONE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn00));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn00_subn01));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 1;
                    cAnteri = 1;
                }
                if(cPagina == 3 && cAnteri == 0){
                    vLsvPart03.startAnimation(vAanteri);
                    vLsvPart03.setVisibility(View.GONE);
                    vLsvPart02.startAnimation(vAanteri2);
                    vLsvPart02.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn01));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn01_subn02));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 2;
                    cAnteri = 1;
                }
                if(cPagina == 4 && cAnteri == 0){
                    vLsvPart04.startAnimation(vAanteri);
                    vLsvPart04.setVisibility(View.GONE);
                    vLsvPart03.startAnimation(vAanteri2);
                    vLsvPart03.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn01));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn01_subn03));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 3;
                    cAnteri = 1;
                }
                if(cPagina == 5 && cAnteri == 0){
                    vLsvPart05.startAnimation(vAanteri);
                    vLsvPart05.setVisibility(View.GONE);
                    vLsvPart04.startAnimation(vAanteri2);
                    vLsvPart04.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn04));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 4;
                    cAnteri = 1;
                }
                if(cPagina == 6 && cAnteri == 0){
                    vLsvPart06.startAnimation(vAanteri);
                    vLsvPart06.setVisibility(View.GONE);
                    vLsvPart05.startAnimation(vAanteri2);
                    vLsvPart05.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn05));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 5;
                    cAnteri = 1;
                }
                if(cPagina == 7 && cAnteri == 0){
                    vLsvPart07.startAnimation(vAanteri);
                    vLsvPart07.setVisibility(View.GONE);
                    vLsvPart06.startAnimation(vAanteri2);
                    vLsvPart06.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn07));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 6;
                    cAnteri = 1;
                }
                if(cPagina == 8 && cAnteri == 0){
                    vLsvPart08.startAnimation(vAanteri);
                    vLsvPart08.setVisibility(View.GONE);
                    vLsvPart07.startAnimation(vAanteri2);
                    vLsvPart07.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn08));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 7;
                    cAnteri = 1;
                }
                if(cPagina == 9 && cAnteri == 0){
                    vLsvPart09.startAnimation(vAanteri);
                    vLsvPart09.setVisibility(View.GONE);
                    vLsvPart08.startAnimation(vAanteri2);
                    vLsvPart08.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn09));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 8;
                    cAnteri = 1;
                }
                if(cPagina == 10 && cAnteri == 0){
                    vLsvPart10.startAnimation(vAanteri);
                    vLsvPart10.setVisibility(View.GONE);
                    vLsvPart09.startAnimation(vAanteri2);
                    vLsvPart09.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn02));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn02_subn10));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 9;
                    cAnteri = 1;
                }
                if(cPagina == 11 && cAnteri == 0){
                    vLsvPart11.startAnimation(vAanteri);
                    vLsvPart11.setVisibility(View.GONE);
                    vLsvPart10.startAnimation(vAanteri2);
                    vLsvPart10.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn11));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 10;
                    cAnteri = 1;
                }
                if(cPagina == 12 && cAnteri == 0){
                    vLsvPart12.startAnimation(vAanteri);
                    vLsvPart12.setVisibility(View.GONE);
                    vLsvPart11.startAnimation(vAanteri2);
                    vLsvPart11.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn12));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 11;
                    cAnteri = 1;
                }
                if(cPagina == 13 && cAnteri == 0){
                    vLsvPart13.startAnimation(vAanteri);
                    vLsvPart13.setVisibility(View.GONE);
                    vLsvPart12.startAnimation(vAanteri2);
                    vLsvPart12.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn13));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 12;
                    cAnteri = 1;
                }
                if(cPagina == 14 && cAnteri == 0){
                    vLsvPart14.startAnimation(vAanteri);
                    vLsvPart14.setVisibility(View.GONE);
                    vLsvPart13.startAnimation(vAanteri2);
                    vLsvPart13.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn14));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 13;
                    cAnteri = 1;
                }
                if(cPagina == 15 && cAnteri == 0){
                    vLsvPart15.startAnimation(vAanteri);
                    vLsvPart15.setVisibility(View.GONE);
                    vLsvPart14.startAnimation(vAanteri2);
                    vLsvPart14.setVisibility(View.VISIBLE);
                    vLtvTitulo.setText(getResources().getString(R.string.formul_opcn03));
                    vLtvSubTit.setText(getResources().getString(R.string.formul_opcn03_subn15));
                    vLtvSiguie.setText(getResources().getString(R.string.formul_siguie));
                    cPagina = 14;
                    cAnteri = 1;
                }
            }
        });

    }

//    public void MGetDepart(){
//        vLisDepart.clear();
//        DepartDataSet vDstDeparta = new DepartDataSet();
//        vDstDeparta.setDepartCodigo("00");
//        vDstDeparta.setDepartNombre(" " + "SELECCIONAR");
//        vLisDepart.add(vDstDeparta);
//
//        vCursorDepart = mPrinciDbHelper.getAllPrinci(DepartDbHelper.DepartTable.TABLE_NAME);
//        while(vCursorDepart.moveToNext()){
//            DepartDataSet vDstDepartb = new DepartDataSet();
//            vDstDepartb.setDepartCodigo(vCursorDepart.getString(vCursorDepart.getColumnIndex(DepartDbHelper.DepartTable.DepartCodigo)));
//            vDstDepartb.setDepartNombre(" " + vCursorDepart.getString(vCursorDepart.getColumnIndex(DepartDbHelper.DepartTable.DepartDescri)));
//            vLisDepart.add(vDstDepartb);
//        }
//        CarLisDepart();
//    }
//
//    private void CarLisDepart(){
//
//        vALiDepart.clear();
//        for (DepartDataSet o : vLisDepart) {
//            vALiDepart.add(o.getDepartNombre());
//        }
//
//        ArrayAdapter vAdaDepart = new ArrayAdapter(this,R.layout.spinner_item,vALiDepart);
//        vAdaDepart.setDropDownViewResource(R.layout.model_spinner_item);
//        vLspPreN10.setAdapter(null);
//        vLspPreN10.setAdapter(vAdaDepart);
//
//    }
//
//    public void MGetProvin(){
//        vLisProvin.clear();
//        ProvinDataSet vDstProvina = new ProvinDataSet();
//        vDstProvina.setProvinCodigo("0000");
//        vDstProvina.setProvinNombre(" " + "SELECCIONAR");
//        vLisProvin.add(vDstProvina);
//
//        vCursorProvin = mPrinciDbHelper.getAllPrinciOne(ProvinDbHelper.ProvinTable.TABLE_NAME, ProvinDbHelper.ProvinTable.DepartCodigo, vDepart);
//        while(vCursorProvin.moveToNext()){
//            ProvinDataSet vDstProvinb = new ProvinDataSet();
//            vDstProvinb.setProvinCodigo(vCursorProvin.getString(vCursorProvin.getColumnIndex(ProvinDbHelper.ProvinTable.ProvinCodigo)));
//            vDstProvinb.setProvinNombre(" " + vCursorProvin.getString(vCursorProvin.getColumnIndex(ProvinDbHelper.ProvinTable.ProvinDescri)));
//            vLisProvin.add(vDstProvinb);
//        }
//        CarLisProvin();
//    }
//
//    private void CarLisProvin(){
//
//        vALiProvin.clear();
//        for (ProvinDataSet o : vLisProvin) {
//            vALiProvin.add(o.getProvinNombre());
//        }
//
//        ArrayAdapter vAdaProvin = new ArrayAdapter(this,R.layout.spinner_item,vALiProvin);
//        vAdaProvin.setDropDownViewResource(R.layout.model_spinner_item);
//        vLspPreN11.setAdapter(null);
//        vLspPreN11.setAdapter(vAdaProvin);
//
//    }
//
//    public void MGetDistri(){
//        vLisDistri.clear();
//        DistriDataSet vDstDistria = new DistriDataSet();
//        vDstDistria.setDistriCodigo("000000");
//        vDstDistria.setDistriNombre(" " + "SELECCIONAR");
//        vLisDistri.add(vDstDistria);
//
//        vCursorDistri = mPrinciDbHelper.getAllPrinciTwo(DistriDbHelper.DistriTable.TABLE_NAME, DistriDbHelper.DistriTable.DepartCodigo, vDepart, DistriDbHelper.DistriTable.ProvinCodigo, vProvin);
//        while(vCursorDistri.moveToNext()){
//            DistriDataSet vDstDistrib = new DistriDataSet();
//            vDstDistrib.setDistriCodigo(vCursorDistri.getString(vCursorDistri.getColumnIndex(DistriDbHelper.DistriTable.DistriCodigo)));
//            vDstDistrib.setDistriNombre(" " + vCursorDistri.getString(vCursorDistri.getColumnIndex(DistriDbHelper.DistriTable.DistriDescri)));
//            vLisDistri.add(vDstDistrib);
//        }
//        CarLisDistri();
//    }
//
//    private void CarLisDistri(){
//
//        vALiDistri.clear();
//        for (DistriDataSet o : vLisDistri) {
//            vALiDistri.add(o.getDistriNombre());
//        }
//
//        ArrayAdapter vAdaDistri = new ArrayAdapter(this,R.layout.spinner_item,vALiDistri);
//        vAdaDistri.setDropDownViewResource(R.layout.model_spinner_item);
//        vLspPreN12.setAdapter(null);
//        vLspPreN12.setAdapter(vAdaDistri);
//
//    }

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

    public class PartialRegexInputFilter implements InputFilter {

        private Pattern mPattern;

        public PartialRegexInputFilter(String pattern){
            mPattern = Pattern.compile(pattern);
        }

        @Override
        public CharSequence filter(CharSequence source,
                                   int sourceStart, int sourceEnd,
                                   Spanned destination, int destinationStart,
                                   int destinationEnd)
        {
            String textToCheck = destination.subSequence(0, destinationStart).
                    toString() + source.subSequence(sourceStart, sourceEnd) +
                    destination.subSequence(
                            destinationEnd, destination.length()).toString();

            Matcher matcher = mPattern.matcher(textToCheck);

            // Entered text does not match the pattern
            if(!matcher.matches()){

                // It does not match partially too
                if(!matcher.hitEnd()){
                    return "";
                }

            }

            return null;
        }

    }

    private void MSerConsulPaciente(String TipDoc, String Numero){
        JSONObject request = new JSONObject();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, vDstDirWeb.getDirWebApiUrl() + "/mcs-consulta-atencion-covid/v1.0/getPacienteConfirmado/"+TipDoc+"/"+Numero, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject Jdatos_paciente = response.getJSONObject("paciente");
                    vLetPreN04.setText(Jdatos_paciente.getString("nombre"));
                    vLetPreN05.setText(Jdatos_paciente.getString("ape_paterno"));
                    vLetPreN06.setText(Jdatos_paciente.getString("ape_materno"));
                    vLetPreN04.setInputType(InputType.TYPE_NULL);
                    vLetPreN05.setInputType(InputType.TYPE_NULL);
                    vLetPreN06.setInputType(InputType.TYPE_NULL);
                    vLetPreN04.setEnabled(false);
                    vLetPreN05.setEnabled(false);
                    vLetPreN06.setEnabled(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormulActivity.this, "El servicio no se encuentra disponible", Toast.LENGTH_SHORT).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
//                params.put("Authorization", "Bearer " + Tokens);
//                params.put("Content-Type", "application/json");
                return params;
            }
        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                2500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}
