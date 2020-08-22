package minsa.formulario.UI.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import minsa.formulario.R;

public class ListarActivity extends AppCompatActivity {
    private TextView vLtvNuevos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_activity);

        vLtvNuevos = (TextView) findViewById(R.id.LtvNuevos);

        vLtvNuevos.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarActivity.this, FormulActivity.class);
                startActivity(intent);
            }
        });
    }
}
