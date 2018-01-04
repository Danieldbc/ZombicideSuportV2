package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Crear(View view) {
        Intent intent=new Intent(this,CrearActivity.class);
        estado ="Crear";
        intent.putExtra(CrearActivity.Keyestado, estado);
        startActivity(intent);
        finish();
    }

    public void Cargar(View view) {
        Intent intent=new Intent(this,CrearActivity.class);
        estado ="Cargar";
        intent.putExtra(CrearActivity.Keyestado,estado);
        startActivity(intent);
        finish();
    }

    public void Entrar(View view) {
        Intent intent=new Intent(this,CrearActivity.class);
        estado ="Entrar";
        intent.putExtra(CrearActivity.Keyestado, estado);
        startActivity(intent);
        finish();
    }
}
