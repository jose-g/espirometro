package minsa.formulario.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import minsa.formulario.R;
import minsa.formulario.Rest.ApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    public static String changeDateFormat(String sdate, String OLD_FORMART, String NEW_FORMAT) {
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMART, new Locale("es", "ES"));
        try {
            Date date = sdf.parse(sdate);
            sdf.applyPattern(NEW_FORMAT);
            return sdf.format(date);
        } catch (Exception ex) {
        }
        return "";
    }

    public static String getDate(String format) {
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static boolean setFragmentFromMenu(FragmentManager fragmentManager, Fragment fragment, String tag) {
        boolean existFragment = false;

        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments.size() > 0) {
            if (fragments.get(fragments.size() - 1).getTag().equals(tag)) {
                existFragment = true;
            }
        }

        if (!existFragment) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, tag)
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
        return existFragment;
    }

    public static void setFragment(FragmentManager fragmentManager, Fragment fragment, int option) {
        if (null != fragment) {
            if (option == 1) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }  else {
                fragmentManager
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();

            }
        }
    }

    public static void setFragmentMenuBottom(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static ProgressDialog showProgressDialog(Activity activity, String message) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static void hideProgressDialog(ProgressDialog progressDialog) {
        if(null != progressDialog) {
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    public static void showDialog(Activity activity, String title, String message) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static void showDialogWithAction(Activity activity, String title, String message, String positive, DialogInterface.OnClickListener actionPositive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positive, actionPositive);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showDialogWithTwoAction(Activity activity, String title, String message, String positive, DialogInterface.OnClickListener actionPositive, String negative, DialogInterface.OnClickListener actionNegative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positive, actionPositive);
        builder.setNegativeButton(negative, actionNegative);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static ApiService getRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(ApiService.class);
    }

}