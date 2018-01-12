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
    private boolean noRepetir=false;
    private ValueEventListener Vcrear,Vcargar,Ventrar;
    private boolean bcrear, bcargar,bentrar;
    private Intent intent;
    private String textSala;
    private String textNombre;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        Sala=(EditText)findViewById(R.id.Sala);
        Nombre=(EditText)findViewById(R.id.Nombre);
        estado =getIntent().getStringExtra(Keyestado);
        TextView textEstado = (TextView) findViewById(R.id.Estado);
        textEstado.setText(estado+" sala");
        database = FirebaseDatabase.getInstance();
        Eventos();
    }



    // Si se pulsa aceptar en función del estado creara, cargará o entrará en una sala
    public void Aceptar(View view) {

        intent=new Intent(this,SelectionActivity.class);
        textSala=Sala.getText().toString();
        textNombre=Nombre.getText().toString();
        intent.putExtra(SelectionActivity.keysala,textSala);
        intent.putExtra(SelectionActivity.keynombre,textNombre);
        intent.putExtra(SelectionActivity.Keyestado,estado);

        //Se comprueba que se haya escrito texto en los dos campos de escritura
        if (textSala.equals("")){
            Toast.makeText(CrearActivity.this, R.string.NOEscritoSala, Toast.LENGTH_SHORT).show();
        }else if (textNombre.equals("")){
            Toast.makeText(CrearActivity.this, R.string.NOEscritonombre, Toast.LENGTH_SHORT).show();
        }else{
            /*En el caso que se quiera crear una sala se comprueba que la sala no exista
              Si no existe se crean los evento con la sala y la información inicial necesaria
              pasamos la variable del numero de personaje a la siguiente pantalla*/
            if (estado.equals("Crear")){
                bcrear=true;
                myRef = database.getReference(textSala+"/nombre");
                myRef.addValueEventListener(Vcrear);


              /*En el caso que se quiera unir a una sala se comprueba que la sala exista y la partida no este empezada
              Se comprueba que no hay ningun personaje con el mismo nombre y si cumple los requisitos se añade un personaje
              más al Firebase*/
            }else if (estado.equals("Entrar")) {
                bentrar=true;
                myRef = database.getReference(textSala);
                myRef.addValueEventListener(Ventrar);
              /*En el caso que se quiera cargar una partida se comprueba que la sala exista y la partida este empezada
              Se comprueba que haya un personaje con el mismo nombre y se pasa el número de personaje a la siguiente actividad*/
            }else if (estado.equals("Cargar")){
                bcargar=true;
                myRef = database.getReference().child(textSala);
                myRef.addValueEventListener(Vcargar);
            }
        }
    }
    private void Eventos() {
        Vcrear=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!noRepetir) {
                    String value = dataSnapshot.getValue(String.class);
                    if(textSala.equals(value)){
                        Toast.makeText(CrearActivity.this, R.string.SalaExiste, Toast.LENGTH_SHORT).show();
                    }else{
                        noRepetir=true;
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myref = database.getReference();
                        myref.child(textSala).child("nombre").setValue(textSala);
                        myref.child(textSala).child("Usuarios").child("Usuario1").child("nombre").setValue(textNombre);
                        myref.child(textSala).child("Usuarios").child("Nusuarios").setValue(1);
                        myref.child(textSala).child("Usuarios").child("Naceptados").setValue("0");
                        myref.child(textSala).child("intercambio").child("intercambiar").setValue(false);
                        myref.child(textSala).child("intercambio").child("aceptar").setValue(false);
                        myref.child(textSala).child("intercambio").child("personaje1").setValue("");
                        myref.child(textSala).child("intercambio").child("personaje2").setValue("");
                        myref.child(textSala).child("Pempezada").setValue(false);
                        myref.child(textSala).child("finalizar").setValue(false);
                        intent.putExtra(SelectionActivity.KeyNumUsuario,1);
                        startActivity(intent);
                        EliminarEventos();
                        finish();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        };
        Ventrar=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!noRepetir){
                    String value = dataSnapshot.child("nombre").getValue(String.class);
                    boolean same=false;
                    if(textSala.equals(value)){
                        boolean val = (boolean) dataSnapshot.child("Pempezada").getValue();
                        if (!val){
                            int Nusuarios= Integer.parseInt(dataSnapshot.child("Usuarios").child("Nusuarios").getValue().toString());
                            for (int i=1;i<Nusuarios+1;i++){
                                String name=dataSnapshot.child("Usuarios").child("Usuario"+i).child("nombre").getValue().toString();
                                if (textNombre.equals(name)){
                                    same=true;
                                }
                            }
                            if(same){
                                Toast.makeText(CrearActivity.this, R.string.NombreEnUso, Toast.LENGTH_SHORT).show();
                            }else{
                                noRepetir=true;
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myref = database.getReference().child(textSala).child("Usuarios");
                                Nusuarios++;
                                myref.child("Nusuarios").setValue(Nusuarios);
                                myref.child("Usuario"+Nusuarios).child("nombre").setValue(textNombre);
                                intent.putExtra(SelectionActivity.KeyNumUsuario,Nusuarios);
                                startActivity(intent);
                                EliminarEventos();
                                finish();
                            }
                        }else{
                            Toast.makeText(CrearActivity.this, R.string.PartidaEmpezada, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CrearActivity.this, R.string.NoSala, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        };
        Vcargar=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!noRepetir){
                    String value = dataSnapshot.child("nombre").getValue(String.class);
                    boolean same=false;
                    if(textSala.equals(value)){
                        boolean val = (boolean) dataSnapshot.child("Pempezada").getValue();
                        if (val){
                            int Nusuarios= Integer.parseInt(dataSnapshot.child("Usuarios").child("Nusuarios").getValue().toString());
                            for (int i=1;i<Nusuarios+1;i++){
                                String name=dataSnapshot.child("Usuarios").child("Usuario"+i).child("nombre").getValue().toString();
                                if (textNombre.equals(name)){
                                    same=true;
                                    intent.putExtra(SelectionActivity.KeyCargar,true);
                                    intent.putExtra(SelectionActivity.KeyNumUsuario,i);
                                }
                            }
                            if(same){
                                noRepetir=true;
                                startActivity(intent);
                                EliminarEventos();
                                finish();
                            }else{
                                Toast.makeText(CrearActivity.this, R.string.NombreNoUso, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CrearActivity.this, R.string.PartidaNoEmpezada, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CrearActivity.this, R.string.NoSala, Toast.LENGTH_SHORT).show();
                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
    }
    // Si se pulsa cancelar vuelve a la pantalla anterior
    public void Cancelar(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        EliminarEventos();
        startActivity(intent);
        finish();
    }

    private void EliminarEventos() {
        if (bcrear){
            myRef.removeEventListener(Vcrear);
            bcrear=false;
        }
        if(bcargar){
            myRef.removeEventListener(Vcargar);
            bcargar=false;
        }if (bentrar){
            myRef.removeEventListener(Ventrar);
        }
    }
}
