package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearActivity extends AppCompatActivity {

    public static String KeyCrear="key_crear";

    private EditText Sala;
    private EditText Nombre;
    private boolean crear;
    private String Nusuarios;
    private int Nusuariosint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        Sala=(EditText)findViewById(R.id.Sala);
        Nombre=(EditText)findViewById(R.id.Nombre);
        crear=getIntent().getBooleanExtra(KeyCrear,false);
    }

    public void Aceptar(View view) {

        final Intent intent=new Intent(this,SelectionActivity.class);
        final String textSala=Sala.getText().toString();
        final String textNombre=Nombre.getText().toString();
        intent.putExtra(SelectionActivity.keysala,textSala);
        intent.putExtra(SelectionActivity.keynombre,textNombre);

        if (crear){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myref = database.getReference();
            myref.child(textSala).child("nombre").setValue(textSala);
            myref.child(textSala).child("Usuarios").child("Usuario1").setValue(textNombre);
            myref.child(textSala).child("Usuarios").child("Usuario2").setValue("");
            myref.child(textSala).child("Usuarios").child("Usuario3").setValue("");
            myref.child(textSala).child("Nusuarios").setValue("1");
            startActivity(intent);
            finish();
        }else {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(textSala+"/nombre");
            final DatabaseReference Refusu = database.getReference(textSala+"/Nusuarios");

            Refusu.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Nusuarios = dataSnapshot.getValue(String.class);
                    Nusuariosint= Integer.parseInt(Nusuarios);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    if(textSala.equals(value)){
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myref = database.getReference();
                        Nusuariosint++;
                        Nusuarios= String.valueOf(Nusuariosint);
                        myref.child(textSala).child("Nusuarios").setValue(Nusuarios);
                        myref.child(textSala).child("Usuarios").child("Usuario"+Nusuarios).setValue(textNombre);



                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(CrearActivity.this, R.string.NoSala, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {


                }
            });

        }

    }

    public void Cancelar(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
