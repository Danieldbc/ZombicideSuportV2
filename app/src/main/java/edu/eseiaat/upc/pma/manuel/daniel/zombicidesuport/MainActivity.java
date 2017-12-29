package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Crear(View view) {
        Intent intent=new Intent(this,CrearActivity.class);
        boolean crear=true;
        intent.putExtra(CrearActivity.KeyCrear,crear);
        startActivity(intent);
        finish();
    }

    public void Cargar(View view) {
        Intent intent=new Intent(this,JuegoActivity.class);
        boolean cargar=true;
        intent.putExtra(JuegoActivity.KeyCargar,cargar);
        startActivity(intent);
        finish();
    }

    public void Entrar(View view) {
        Intent intent=new Intent(this,CrearActivity.class);
        boolean crear=false;
        intent.putExtra(CrearActivity.KeyCrear,crear);
        startActivity(intent);
        finish();
    }
}
