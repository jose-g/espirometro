package minsa.formulario.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.R;
import minsa.formulario.UI.Fragments.FichasFragment;
import minsa.formulario.UI.Fragments.InboxFragment;
import minsa.formulario.UI.Fragments.OutboxFragment;
import minsa.formulario.UI.Fragments.RegistroFragment;
import minsa.formulario.Util.Utils;

public class PrincipalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bottom_nav_view) BottomNavigationView bottom_nav_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_registro:

                        InboxFragment dashboardFragment = new InboxFragment();
                        Utils.setFragmentFromMenu(getSupportFragmentManager(), dashboardFragment, dashboardFragment.getClass().getSimpleName());

                        break;
//                    case R.id.navigation_fichas:
//
//
//                        OutboxFragment fichasFragment = new OutboxFragment();
//                        Utils.setFragmentFromMenu(getSupportFragmentManager(), fichasFragment, fichasFragment.getClass().getSimpleName());
//                        break;
                    
                    case R.id.navigation_logout:
                        logout();
                        break;
                }
                return true;
            }
        });

        bottom_nav_view.setSelectedItemId(R.id.navigation_registro);
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment;

        switch (requestCode) {
            case 442:
            case 123:
                fragment = getSupportFragmentManager().findFragmentByTag("DashboardFragment");
                fragment.onActivityResult(requestCode, resultCode, data);
                break;
            case 111:
            case 222:
            case 333:
                fragment = getSupportFragmentManager().findFragmentByTag("FichasFragment");
                fragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void logout() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PrincipalActivity.this, R.style.CustomDialog);
        dialog.setTitle("Cerrar Sesión");
        dialog.setMessage("¿Está seguro que desea cerrar sesión?");

        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("INFORMACION:", "click rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            }
        });

        final AlertDialog dialogo = dialog.create();
        dialogo.show();

        dialogo.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("4PPS1C0V1D", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                dialogo.dismiss();

                //ActivityCompat.finishAffinity(PrincipalActivity.this);
                //startActivity(new Intent(getApplicationContext(), AccesoActivity.class));
                Log.i("INFORMACION:", "Metodo-->logout:" + "Variable-->slair:" + "Valor-->:" + "salir 66666666666");
                finish();
//                System.exit(0);


//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            }
        });
    }

}