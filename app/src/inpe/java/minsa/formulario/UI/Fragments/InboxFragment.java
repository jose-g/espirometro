package minsa.formulario.UI.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import minsa.formulario.Adapters.recyclerview.RecyclerViewAdapter;
import minsa.formulario.DataSet.RecyInterDataSet;
import minsa.formulario.DbHelper.PrinciDbHelper;
import minsa.formulario.R;
import minsa.formulario.UI.Activity.CustomJsonArrayRequest;
import minsa.formulario.UI.Activity.Ficha_Paciente;
import minsa.formulario.UI.Activity.SpinnerDialog;
import minsa.formulario.Util.Utils;

//import Activity.Ficha100_INPE;

public class InboxFragment extends Fragment {


    LinearLayout ly_datos;
    LinearLayout ly_documento;
    BottomNavigationView navigation_ll;
    @BindView(R.id.recyclerView_interno)
    RecyclerView recyclerView_interno;


    SpinnerDialog mSpinnerDialog;
    private ProgressDialog progressDialog;
    private PrinciDbHelper mPrinciDbHelper;
    private List<RecyInterDataSet> lista = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    String cod_radio = "";
    View root;
    String navi_esta = "";



    public static final int PICKFILE_RESULT_CODE = 1;

    private SharedPreferences sharedPreferences;
    private String strBase64 = "";
    private Uri fileUri;
    private String filePath;
    JSONObject loginSession;
    JSONObject permisos;

    @BindView(R.id.inputSearch) EditText inputSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        sharedPreferences = this.getActivity().getSharedPreferences("4PPS1C0V1D", Context.MODE_PRIVATE);
        String strLoginSession = sharedPreferences.getString("loginSession", null);
        String strPermisos = sharedPreferences.getString("permisos", null);
        //JSONObject lsvm = new JSONObject();


        mPrinciDbHelper = new PrinciDbHelper(getContext());
        setRetainInstance(true);

        //consultarBandeja();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, root);


/*
        inputSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                inputSearch.setSelection(inputSearch.getText().length());

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (inputSearch.getRight() - inputSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        adapter.filter(inputSearch.getText().toString());

                        showMessage(inputSearch.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
*/
        return root;
    }

    //@Override


    public void consultarBandeja() {


        progressDialog = Utils.showProgressDialog(getActivity(), "Buscando resoluciones...");
        progressDialog.show();
        consultaBandejaEntrada("");
    }

    //SELECCIONA UN ARCHIVO DESDE EL MOVIL Y LO GUARDA, EN FORMATO BASE64, EN UNA VARIABLE
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    fileUri = data.getData();
                    filePath = fileUri.getPath();
                    Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->filePath:" + "Valor-->:" + filePath);

                    if (filePath != null && !TextUtils.isEmpty(filePath)) {
                        InputStream imageStream = null;
                        try {
                            imageStream = getContext().getContentResolver().openInputStream(fileUri);
                            byte[] inputData = getBytes(imageStream);
                            strBase64 = Base64.getEncoder().encodeToString(inputData);
                        } catch (FileNotFoundException e) {

                        } catch (IOException e) {

                        }
                    } else {
                        strBase64 = "";
                    }
                }
                Log.i("INFORMACION:", "Metodo-->onActivityResult:" + "Variable-->encodedImage:" + "Valor-->:" + strBase64);
                break;
        }
    }
    //CONVIERTE INPUTSTRING EN ARREGLO DE BYTES
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void getPermisos() {
        String strUrl = getResources().getString(R.string.url_ws_rest_login) + "GetPermisos";

        Log.i("INFORMACION:", "Metodo-->getPermisos:" + "Variable-->strUrl:" + "Valor-->:" + strUrl.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, strUrl, loginSession,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->response:" + "Valor-->:" + response.toString());
                            Utils.hideProgressDialog(progressDialog);

                            if (response.length() > 0) {
                                permisos = response;

                                try {
                                    if (permisos.getString("CONSULTAR") == "false"
                                            && permisos.getString("ELIMINAR") == "false"
                                            && permisos.getString("EXPORTAR_GRILLA") == "false"
                                            && permisos.getString("EXPORTAR_REPORTE") == "false"
                                            && permisos.getString("IMPRESION_REPORTE") == "false"
                                            && permisos.getString("MODIFICAR") == "false"
                                            && permisos.getString("NUEVO") == "false"
                                            && permisos.getString("REPORTE") == "false") {
                                        showMessage("No tiene autorizacion para este modulo");

                                    } else {
                                        consultarBandeja();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            } else {
                                showMessage("Ocurrio un error.");
                                return;
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
                showMessage("Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
                Utils.hideProgressDialog(progressDialog);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public void methodOnBtnClick(int position ) {
        showMessage("Metodo-->leer:" + "Variable-->position:" + "Valor-->:" + position);
    }

    private void consultaBandejaEntrada(String s) {
        String strUrl = getResources().getString(R.string.url_ws_rest_inbox) + "obtenerResolucionesJudicialesBE";
        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->strUrl:" + "Valor-->:" + strUrl);
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(Request.Method.POST, strUrl, loginSession,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->response:" + "Valor-->:" + response.toString());
                            Utils.hideProgressDialog(progressDialog);
                            lista.clear();

                            if (response.length() > 0) {
                                JSONArray jsonArray = response;
                                Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->jsonArray.length():" + "Valor-->:" + jsonArray.length());
                                int j = 0;

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    j++;
                                    JSONObject res = jsonArray.getJSONObject(i);
                                    RecyInterDataSet lista_total = new RecyInterDataSet();

                                    lista_total.setId(res.getString("Codigo"));
                                    lista_total.setApellidos(res.getString("DocumentoNombre"));
                                    lista_total.setNombres(res.getString("NombreInterno"));
                                    lista_total.setTipo_doc(res.getString("FilenameSolicitud"));
                                    lista_total.setEstado(res.getString("Estado"));

                                    String fecha2 = res.getString("fecha");
                                    Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->jfecha2:" + "Valor-->:" + fecha2);
                                    String fecha = res.getString("fecha").substring(6, 18);
                                    Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->fecha:" + "Valor-->:" + fecha);
                                    ///SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                                    //Date date = new Date(Long.parseLong(fecha));
                                    //System.out.println(sf.format(date));
                                    String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (Long.parseLong(fecha)*1000));
                                    Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->date:" + "Valor-->:" + date.toString());

                                    String Estado = "";

                                    if (res.getString("Estado") == "1") {
                                        Estado = "ENVIADO";
                                    } else if (res.getString("Estado") == "2") {
                                        Estado = "ACLARATORIA";
                                    } else if (res.getString("Estado") == "3") {
                                        Estado = "CONFORME";
                                    } else if (res.getString("Estado") == "4") {
                                        Estado = "LEIDO";
                                    } else if (res.getString("Estado") == "5") {
                                        Estado = "DEVUELTO";
                                    } else {
                                        Estado = "SIN ESTADO";
                                    }
                                    lista_total.setDoc(Estado);

                                    lista.add(lista_total);

                                }

                                recyclerView_interno.setLayoutManager(new LinearLayoutManager(getContext()));
                                adapter = new RecyclerViewAdapter(getContext(), lista);
                                recyclerView_interno.setAdapter(adapter);

                                inputSearch.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {

                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Write whatever to want to do after delay specified (1 sec)
                                                inputSearch.setSelection(inputSearch.getText().length());
                                                //adapter = new RecyclerViewAdapter(getContext(), lista);
                                                //recyclerView_interno.setAdapter(adapter);

                                                final int DRAWABLE_LEFT = 0;
                                                final int DRAWABLE_TOP = 1;
                                                final int DRAWABLE_RIGHT = 2;
                                                final int DRAWABLE_BOTTOM = 3;

                                                inputSearch.setSelection(inputSearch.getText().length());

                                                if(event.getAction() == MotionEvent.ACTION_UP) {
                                                    if(event.getRawX() >= (inputSearch.getRight() - inputSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                                        // your action here
                                                        adapter.filter(inputSearch.getText().toString());

                                                        showMessage(inputSearch.getText().toString());
                                                        //return true;
                                                    }
                                                }
                                                adapter.filter(inputSearch.getText().toString());
                                                showMessage(inputSearch.getText().toString());
                                            }
                                        }, 1000);

                                        return false;
                                    }
                                });


                                adapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener() {
                                    @Override
                                    public void onItemClick(int position, View v) {
                                        //showMessage("Id de item seleccionado: " + lista.get(position).getId());
                                        ArrayList<String> cars = new ArrayList<String>();
                                        cars.add("CONFORME");
                                        cars.add("ACLARACION");
                                        cars.add("DEVOLUCION");

                                        //String fullName = getResources().getResourceName(v.getId());
                                        //String name = fullName.substring(fullName.lastIndexOf("/") + 1);
                                        String name = v.getTag().toString();
                                        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->name:" + "Valor-->:" + name);

                                        RecyclerView.ViewHolder holder = recyclerView_interno.findViewHolderForAdapterPosition(position);
                                        ImageView imageView = holder.itemView.findViewById(R.id.img_row_add);

                                        String fullName = getResources().getResourceName(imageView.getId());
                                        String name2 = fullName.substring(fullName.lastIndexOf("/") + 1);

                                        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->name2:" + "Valor-->:" + name2);

                                        //RecyclerViewAdapter.ViewHolder holder = (RecyclerViewAdapter.ViewHolder) v.getTag();

                                        //String fullName3 = v.getId();
                                        //
                                        // String name3 = fullName.substring(fullName.lastIndexOf("/") + 1);

                                        //Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->name3:" + "Valor-->:" + name3);
                                        //int position = holder.getPosition();


                                        if (v.getId() == holder.itemView.findViewById(R.id.img_row_delete).getId()){
                                            String strURL = getResources().getString(R.string.url_repositorio_pj_inbox) + lista.get(position).getTipo_doc();
                                            Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->strURL:" + "Valor-->:" + strURL);
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setDataAndType(Uri.parse(strURL), "application/pdf");
                                            startActivity(intent);

                                            leer(lista.get(position).getTipo_doc(), lista.get(position).getId());
                                            consultarBandeja();
                                            strBase64 = "";

                                        } else if (v.getId() == holder.itemView.findViewById(R.id.img_row_add).getId()){
                                            mSpinnerDialog = new SpinnerDialog(getContext(), cars, new SpinnerDialog.DialogListener() {
                                                public void cancelled() {
                                                    // do your code here
                                                }
                                                public void ready(int n) {
                                                    // do your code here
                                                    String text = mSpinnerDialog.mSpinner.getSelectedItem().toString();
                                                    String strURL = getResources().getString(R.string.url_repositorio_pj_inbox) + lista.get(position).getTipo_doc();
                                                    Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->strURL:" + "Valor-->:" + strURL);

                                                    if (text == "CONFORME") {
                                                        responder(lista.get(position).getTipo_doc(), "", "", lista.get(position).getId(),  "3", strBase64);
                                                        consultarBandeja();
                                                        strBase64 = "";
                                                    }
                                                    else if (text == "ACLARACION") {
                                                        if (strBase64.length() > 0) {
                                                            responder(lista.get(position).getTipo_doc(), "", "", lista.get(position).getId(), "2", strBase64);
                                                            consultarBandeja();
                                                            strBase64 = "";
                                                        } else {
                                                            showMessage("Seleccione archivo a enviar");
                                                        }
                                                    }
                                                    else if (text == "DEVOLUCION") {
                                                        if (strBase64.length() > 0) {
                                                            responder(lista.get(position).getTipo_doc(), "", "", lista.get(position).getId(), "5", strBase64);
                                                            consultarBandeja();
                                                            strBase64 = "";
                                                        } else {
                                                            showMessage("Seleccione archivo a enviar");
                                                        }
                                                    }
                                                }
                                                public void selectFile() {
                                                    // do your code here
                                                    Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);

                                                    chooseFile = Intent.createChooser(chooseFile, "Por favor, seleccione un archivo.");
                                                    startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
                                                }
                                            });
                                            mSpinnerDialog.show();

                                        } else {

                                            Toasty.info(getContext(), "RecyclerView Item onClick at " + position, Toasty.LENGTH_SHORT).show();
                                        }





                                        //int position1 = recyclerView_interno.getChildPosition((View)v.getParent());
                                        //Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->position:" + "Valor-->:" + position);

                                        /*

                                        */
                                    }
                                });
                                //showMessage("Cantidad de resoluciones: " + j);
                            } else {
                                showMessage("No existen resoluciones para el usuario " + sharedPreferences.getString("usuario", ""));
                                return;
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->error:" + "Valor-->:" + error.toString());
                showMessage("Metodo-->consultaBandejaEntrada:" + "Variable-->error:" + "Valor-->:" + error.toString());
                Utils.hideProgressDialog(progressDialog);
               }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("pen_id", sharedPreferences.getString("pen_id", ""));
                headers.put("usuario", sharedPreferences.getString("usuario", ""));
                headers.put("clave", sharedPreferences.getString("clave", ""));
                return headers;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private void leer(String ImageName, String aud_id) {
        String strUrl = getResources().getString(R.string.url_ws_rest_inbox) + "DownloadBE";
        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->strUrl:" + "Valor-->:" + strUrl);
        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->ImageName:" + "Valor-->:" + ImageName);
        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->aud_id:" + "Valor-->:" + aud_id);
        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->usuario:" + "Valor-->:" + sharedPreferences.getString("usuario", ""));
        Log.i("INFORMACION:", "Metodo-->consultaBandejaEntrada:" + "Variable-->clave:" + "Valor-->:" + sharedPreferences.getString("clave", ""));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, strUrl, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->response:" + "Valor-->:" + response.toString());
                            Utils.hideProgressDialog(progressDialog);

                            if (response.length() > 0) {
                                JSONObject jsonArray = response;
                                Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->jsonArray.length():" + "Valor-->:" + jsonArray.length());
                                showMessage(jsonArray.getString("errorMsg"));


                            } else {
                                showMessage("Ocurrio un error.");
                                return;
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
                showMessage("Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
                Utils.hideProgressDialog(progressDialog);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("usuario", sharedPreferences.getString("usuario", ""));
                headers.put("clave", sharedPreferences.getString("clave", ""));
                headers.put("ImageName", ImageName);
                headers.put("aud_id", aud_id);
                return headers;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private void responder(String FilenameSolicitud, String FilenameRespuesta, String FilenameDevolucion, String Codigo, String Accion, String Archivo) {
        String strUrl = getResources().getString(R.string.url_ws_rest_inbox) + "GuardarDatosRespuesta";

        Map<String, String> params = new HashMap();
        params.put("Archivo", Archivo);
        params.put("FilenameSolicitud", FilenameSolicitud);
        params.put("FilenameRespuesta", FilenameRespuesta);
        params.put("FilenameDevolucion", FilenameDevolucion);
        params.put("Codigo", Codigo);
        params.put("Accion", Accion);
        JSONObject parameters = new JSONObject(params);
        Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->parameters:" + "Valor-->:" + parameters.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, strUrl, parameters,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->response:" + "Valor-->:" + response.toString());
                            Utils.hideProgressDialog(progressDialog);

                            if (response.length() > 0) {
                                JSONObject jsonArray = response;
                                Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->jsonArray.length():" + "Valor-->:" + jsonArray.length());
                                showMessage(jsonArray.getString("errorMsg"));


                            } else {
                                showMessage("Ocurrio un error.");
                                return;
                            }
                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("INFORMACION:", "Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
                showMessage("Metodo-->leer:" + "Variable-->error:" + "Valor-->:" + error.toString());
                Utils.hideProgressDialog(progressDialog);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("usuario", sharedPreferences.getString("usuario", ""));
                headers.put("clave", sharedPreferences.getString("clave", ""));
                //headers.put("FilenameSolicitud", FilenameSolicitud);
                //headers.put("FilenameRespuesta", FilenameRespuesta);
                //headers.put("FilenameDevolucion", FilenameDevolucion);
                //headers.put("Codigo", Codigo);
                //headers.put("Accion", Accion);
                //headers.put("Archivo", Archivo);
                return headers;
            }
        };
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    private void showMessage(String message) {
        Toasty.info(getContext(), message, Toasty.LENGTH_SHORT).show();
    }






    //TODO: POR VERIFICAR
    private void state_radio(String s) {
        this.cod_radio = s;
    }

    private void rest_dato(String s) {
        String url = "http://websvc.inpe.gob.pe:7575/wsminsa/DatoPaciente/Obtener";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String respo) {
                        Log.i("json1", respo);
                        Toast.makeText(getContext(), " " + respo, Toast.LENGTH_SHORT).show();
                        JSONObject response = null;
                        JSONObject res = null;
                        try {
                            response = new JSONObject(respo);
                            String Objson = response.getString("ObtenerResult");
                            res = new JSONObject(Objson);

                            Log.i("jsonObj", Objson);
                            String re = res.getString("paterno");
                            Intent intent = new Intent(getContext(), Ficha_Paciente.class);
                            intent.putExtra("fecha", "");
                            intent.putExtra("id_tipo_doc", res.getString("id_tipo_doc"));
                            intent.putExtra("tipo_doc", res.getString("tipo_doc"));
                            intent.putExtra("cod_interno", res.getString("tipo_doc"));
                            intent.putExtra("numero_doc", res.getString("numero_doc"));
                            intent.putExtra("nombres", res.getString("nombres"));
                            intent.putExtra("paterno", res.getString("paterno"));
                            intent.putExtra("materno", res.getString("materno"));
                            intent.putExtra("fec_nac", res.getString("fec_nac"));
                            //intent.putExtra("id_sexo", "");
                            intent.putExtra("TipSex", res.getString("id_sexo"));
                            intent.putExtra("celular", "");
                            intent.putExtra("celular_contacto", "");
                            intent.putExtra("correo", "");
                            //intent.putExtra("id_tipo_residencia", "");
                            //intent.putExtra("tipo_residencia", "");
                            intent.putExtra("direccion", res.getString("direccion"));
                            //  intent.putExtra("id_departamento", "");
                            //  intent.putExtra("departamento", "");
                            //  intent.putExtra("id_provincia", "");
                            //  intent.putExtra("provincia", "");
                            //  intent.putExtra("id_distrito", "");
                            //  intent.putExtra("distrito", "");
                            //  intent.putExtra("latitud", "");
                            //  intent.putExtra("longitud", "");
                            intent.putExtra("es_pers_salud", "");
                            intent.putExtra("id_profesion", "");
                            intent.putExtra("tiene_sintoma", "");
                            intent.putExtra("fecha_sintoma", "");
                            intent.putExtra("otro_sintoma", "");
                            intent.putExtra("id_usuario", "");
                            intent.putExtra("id_dato_paciente", "");
                            intent.putExtra("codigo_pais_celular", "");
                            intent.putExtra("codigo_pais_telefono", "");
                            startActivity(intent);
                            //JSONArray jsonArray = response.getJSONArray("ObtenerResult");
                            //JSONObject result = jsonArray.getJSONObject(0);
                            //Toast.makeText(getContext(), " " +re.toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();

                            // Toast.makeText(getContext(), " errorkkkkk  "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("codigo_rp", "tiettietcod_inter.getText().toString()");
                //headers.put("Token", "{B709CE08-D2DE-4201-962B-3BBAC74C5952}");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //con.setRequestProperty("Token", "{B709CE08-D2DE-4201-962B-3BBAC74C5952}");
        requestQueue.add(stringRequest);


    }


    protected Message doInBackground(Message... params) {
        try {


            URL url = new URL("http://websvc.inpe.gob.pe:7575/wsminsa/datopaciente/obtener"); //in the real code, there is an ip and a port
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("codigo_rp", "0253000549");

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());

            conn.disconnect();

        } catch (Exception e) {

        }
        return null;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                Log.i("STATUS", "5555");
                URL url = new URL("http://websvc.inpe.gob.pe:7575/wsminsa/datopaciente/obtener"); //in the real code, there is an ip and a port
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("codigo_rp", "0253000549");
                //jsonParam.put("message", params[0].getMessage());


                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

                os.flush();
                os.close();
                //showMessage("json "+conn.getResponseMessage());
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getContext(),
                    "ProgressDialog",
                    "Wait for " + " seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_doc:

                    //lista.clear();
                    //Log.i("json1" , "navigation_doc");
                    //Toast.makeText(getContext(), " errorkkkkk  ", Toast.LENGTH_SHORT).show();
                    ly_datos.setVisibility(View.INVISIBLE);
                    ly_documento.setVisibility(View.INVISIBLE);
                    //showMessage("navigation_doc");
                    state_radio("");
                    navigation_doc("1");
                    ly_datos.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    ly_documento.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    return true;
                case R.id.navigation_apell:
                    //Log.i("json1" , "navigation_apell");
                    //lista.clear();
                    state_radio("");
                    navigation_doc("2");
                    ly_datos.setVisibility(View.INVISIBLE);
                    ly_documento.setVisibility(View.INVISIBLE);
                    ly_datos.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    ly_documento.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
                    showMessage("navigation_apell");
                    //Toast.makeText(getContext(), " errorkkkkk  ", Toast.LENGTH_SHORT).show();
                    return true;

            }
            return false;
        }
    };

    private void navigation_doc(String s) {
        this.navi_esta = s;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //navigation_ll = (BottomNavigationView) root.findViewById(R.id.navigation_ll);
        ly_documento = (LinearLayout) root.findViewById(R.id.ly_documento);
        ly_datos = (LinearLayout) root.findViewById(R.id.ly_datos);
        //BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_ll);
        //navigation_ll.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation_ll.setSelectedItemId(R.id.navigation_doc);
    }


}