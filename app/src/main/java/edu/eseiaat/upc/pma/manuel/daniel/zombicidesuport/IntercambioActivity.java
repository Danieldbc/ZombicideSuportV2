package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IntercambioActivity extends AppCompatActivity {
    public static String Keycartas="key_cartas";
    public static String Keycartas2="key_cartas2";
    public static int pasarcartas=2;
    public static String KeySala="key_sala";
    private Personaje p1, p2;

    private ImageView carta11,carta12,carta13,carta14,carta15,carta21,carta22,carta23,carta24,carta25;
    private TextView nombrep,nombreq;
    private int c1,c2;
    boolean p1drag,p1drop;
    private String textSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercambio);
        carta11=(ImageView)findViewById(R.id.carta11);
        carta12=(ImageView)findViewById(R.id.carta12);
        carta13=(ImageView)findViewById(R.id.carta13);
        carta14=(ImageView)findViewById(R.id.carta14);
        carta15=(ImageView)findViewById(R.id.carta15);
        carta21=(ImageView)findViewById(R.id.carta21);
        carta22=(ImageView)findViewById(R.id.carta22);
        carta23=(ImageView)findViewById(R.id.carta23);
        carta24=(ImageView)findViewById(R.id.carta24);
        carta25=(ImageView)findViewById(R.id.carta25);
        nombrep=(TextView)findViewById(R.id.nombre1);
        nombreq=(TextView)findViewById(R.id.nombre2);
        textSala=getIntent().getExtras().getString(KeySala);
        p1 = (Personaje) getIntent().getSerializableExtra(Keycartas);
        p2 = (Personaje) getIntent().getSerializableExtra(Keycartas2);

        Mostrar();
        ListenerFireBase();
        ListenerCartas();

    }
    private void ListenerFireBase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference drintercambio = database.getReference().child(textSala).child("intercambio");
        final DatabaseReference drwatts = database.getReference().child(textSala).child("watts");
        final DatabaseReference drbelle = database.getReference().child(textSala).child("Belle");
        final DatabaseReference drgrindlock = database.getReference().child(textSala).child("Grindlock");
        final DatabaseReference drjoshua = database.getReference().child(textSala).child("Joshua");
        final DatabaseReference drkim = database.getReference().child(textSala).child("Kim");
        final DatabaseReference drshannon = database.getReference().child(textSala).child("Shannon");
        drintercambio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean intc= (boolean) dataSnapshot.child("intercambiar").getValue();

                if(!intc){
                    Intent data=new Intent();
                    setResult(RESULT_OK,data);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        drwatts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ComprobarPersonajeFB(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drbelle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ComprobarPersonajeFB(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drgrindlock.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ComprobarPersonajeFB(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drjoshua.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ComprobarPersonajeFB(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drkim.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ComprobarPersonajeFB(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drshannon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ComprobarPersonajeFB(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void ComprobarPersonajeFB(DataSnapshot dataSnapshot) {
        String PersonajeFireBase=dataSnapshot.child("nombre").getValue().toString();

        if (p1.getNombre().equals(PersonajeFireBase)){
            ActualizarPersonaje(dataSnapshot,p1);
        }

        if (p2.getNombre().equals(PersonajeFireBase)){
            ActualizarPersonaje(dataSnapshot,p2);
        }

        Mostrar();
    }
    private void ActualizarPersonaje(DataSnapshot dataSnapshot,Personaje p) {
        int i= Integer.parseInt((dataSnapshot.child("carta1").child("carta").getValue().toString()));
        String s=(dataSnapshot.child("carta1").child("nombre").getValue().toString());
        p.setCarta1(i,s);
        i=Integer.parseInt((dataSnapshot.child("carta2").child("carta").getValue().toString()));
        s=(dataSnapshot.child("carta2").child("nombre").getValue().toString());
        p.setCarta2(i,s);
        i=Integer.parseInt((dataSnapshot.child("carta3").child("carta").getValue().toString()));
        s=(dataSnapshot.child("carta3").child("nombre").getValue().toString());
        p.setCarta3(i,s);
        i=Integer.parseInt((dataSnapshot.child("carta4").child("carta").getValue().toString()));
        s=(dataSnapshot.child("carta4").child("nombre").getValue().toString());
        p.setCarta4(i,s);
        i=Integer.parseInt((dataSnapshot.child("carta5").child("carta").getValue().toString()));
        s=(dataSnapshot.child("carta5").child("nombre").getValue().toString());
        p.setCarta5(i,s);


    }

    private void ListenerCartas() {
        carta11.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                p1drag =true;
                c1=0;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta11.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=0;
                        p1drop=true;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                p1drag =true;
                c1=1;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta12.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=1;
                        p1drop=true;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta13.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                p1drag =true;
                c1=2;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta13.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=2;
                        p1drop=true;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta14.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                p1drag =true;
                c1=3;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta14.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=3;
                        p1drop=true;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta15.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                p1drag =true;
                c1=4;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta15.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=4;
                        p1drop=true;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta21.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                c1=0;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta21.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=0;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta22.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                c1=1;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta22.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=1;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta23.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                c1=2;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta23.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=2;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta24.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                c1=3;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta24.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=3;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta25.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                c1=4;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

            }
        });
        carta25.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        c2=4;
                        MovimientoCarta();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void MovimientoCarta() {
        if (p1drop){
            if (p1drag){
                p1.intercambiar(p1,c1,c2);
            }else{
                p1.intercambiar(p2,c1,c2);
            }
        }else{
            if (p1drag){
                p2.intercambiar(p1,c1,c2);
            }else{
                p2.intercambiar(p2,c1,c2);
            }
        }
        p1drop=false;
        p1drag=false;
        ModificarFireBase();
    }
    private void ModificarFireBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();

        myref.child(textSala).child(p1.getNombre()).child("carta1").setValue(p1.getCarta1());
        myref.child(textSala).child(p1.getNombre()).child("carta2").setValue(p1.getCarta2());
        myref.child(textSala).child(p1.getNombre()).child("carta3").setValue(p1.getCarta3());
        myref.child(textSala).child(p1.getNombre()).child("carta4").setValue(p1.getCarta4());
        myref.child(textSala).child(p1.getNombre()).child("carta5").setValue(p1.getCarta5());

        myref.child(textSala).child(p2.getNombre()).child("carta1").setValue(p2.getCarta1());
        myref.child(textSala).child(p2.getNombre()).child("carta2").setValue(p2.getCarta2());
        myref.child(textSala).child(p2.getNombre()).child("carta3").setValue(p2.getCarta3());
        myref.child(textSala).child(p2.getNombre()).child("carta4").setValue(p2.getCarta4());
        myref.child(textSala).child(p2.getNombre()).child("carta5").setValue(p2.getCarta5());


    }
    private void Mostrar() {
        nombrep.setText(p1.getNombre());
        carta11.setImageResource(p1.getCarta1().getCarta());
        carta12.setImageResource(p1.getCarta2().getCarta());
        carta13.setImageResource(p1.getCarta3().getCarta());
        carta14.setImageResource(p1.getCarta4().getCarta());
        carta15.setImageResource(p1.getCarta5().getCarta());

        nombreq.setText(p2.getNombre());
        carta21.setImageResource(p2.getCarta1().getCarta());
        carta22.setImageResource(p2.getCarta2().getCarta());
        carta23.setImageResource(p2.getCarta3().getCarta());
        carta24.setImageResource(p2.getCarta4().getCarta());
        carta25.setImageResource(p2.getCarta5().getCarta());
    }

    public void Aceptar(View view) {
        Intent data=new Intent();
        setResult(RESULT_OK,data);
        finish();
    }

}
