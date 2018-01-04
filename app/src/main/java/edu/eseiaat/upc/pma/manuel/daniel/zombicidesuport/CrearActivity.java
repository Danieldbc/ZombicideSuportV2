package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearActivity extends AppCompatActivity {

    public static String Keyestado ="key_estado";

    private EditText Sala;
    private EditText Nombre;
    private String estado;
    private String Nusuarios;
    private int Nusuariosint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        Sala=(EditText)findViewById(R.id.Sala);
        Nombre=(EditText)findViewById(R.id.Nombre);
        estado =getIntent().getStringExtra(Keyestado);
        TextView textEstado = (TextView) findViewById(R.id.Estado);
        textEstado.setText(estado+" sala");
    }

    public void Aceptar(View view) {

        final Intent intent=new Intent(this,SelectionActivity.class);
        final String textSala=Sala.getText().toString();
        final String textNombre=Nombre.getText().toString();
        intent.putExtra(SelectionActivity.keysala,textSala);
        intent.putExtra(SelectionActivity.keynombre,textNombre);

        if (estado.equals("Crear")){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(textSala+"/nombre");
            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    if(textSala.equals(value)){
                        Toast.makeText(CrearActivity.this, R.string.SalaExiste, Toast.LENGTH_SHORT).show();
                    }else{
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myref = database.getReference();
                        myref.child(textSala).child("nombre").setValue(textSala);
                        myref.child(textSala).child("Usuarios").child("Usuario1").setValue(textNombre);
                        myref.child(textSala).child("Nusuarios").setValue("1");
                        myref.child(textSala).child("Naceptados").setValue("0");
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {


                }
            });


        }else if (estado.equals("Entrar")) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(textSala+"/nombre");
            final DatabaseReference Refusu = database.getReference(textSala+"/Nusuarios");

            Refusu.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Nusuarios =dataSnapshot.getValue().toString();
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
                        Nusuariosint= Integer.parseInt(Nusuarios);
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
