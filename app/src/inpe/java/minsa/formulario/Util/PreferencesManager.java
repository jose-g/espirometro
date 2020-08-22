package minsa.formulario.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private PreferencesManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.ID_APP, Context.MODE_PRIVATE);
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }

    public static void setIdConglomerado(Context context, String id_conglomerado) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("id_conglomerado", id_conglomerado);
        prefsEditor.commit();
    }

    public static String getIdConglomerado(Context c) {
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        return sharedPreferences.getString("id_conglomerado", "");
    }

    public static void setConglomerado(Context context, String conglomerado) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("conglomerado", conglomerado);
        prefsEditor.commit();
    }

    public static String getConglomerado(Context c) {
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        return sharedPreferences.getString("conglomerado", "");
    }

    public static void setIdTipoConglomerado(Context context, String id_tipo_conglomerado) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("id_tipo_conglomerado", id_tipo_conglomerado);
        prefsEditor.commit();
    }

    public static String getIdTipoConglomerado(Context c) {
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        return sharedPreferences.getString("id_tipo_conglomerado", "");
    }

    public static void setTipoConglomerado(Context context, String tipo_conglomerado) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("tipo_conglomerado", tipo_conglomerado);
        prefsEditor.commit();
    }

    public static String getTipoConglomerado(Context c) {
        SharedPreferences sharedPreferences = getSharedPreferences(c);
        return sharedPreferences.getString("tipo_conglomerado", "");
    }

}
