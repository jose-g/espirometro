package minsa.formulario.UI.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import minsa.formulario.UI.Fragments.FichasFragment;
import minsa.formulario.UI.Fragments.HomeFragment;
import minsa.formulario.R;
import minsa.formulario.UI.Fragments.RegistroFragment;
import minsa.formulario.Util.Utils;

public class MenuActivity extends AppCompatActivity {

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

                        RegistroFragment dashboardFragment = new RegistroFragment();
                        Utils.setFragmentFromMenu(getSupportFragmentManager(), dashboardFragment, dashboardFragment.getClass().getSimpleName());

                        break;
                    case R.id.navigation_fichas:


                        FichasFragment fichasFragment = new FichasFragment();
                        Utils.setFragmentFromMenu(getSupportFragmentManager(), fichasFragment, fichasFragment.getClass().getSimpleName());
                        break;

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
        AlertDialog.Builder dialog = new AlertDialog.Builder(MenuActivity.this, R.style.CustomDialog);
        dialog.setTitle("Cerrar Sesión");
        dialog.setMessage("¿Seguro que desea cerrar sesión1?");

        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

                ActivityCompat.finishAffinity(MenuActivity.this);
                startActivity(new Intent(getApplicationContext(), AccesoActivity.class));
            }
        });
    }

}