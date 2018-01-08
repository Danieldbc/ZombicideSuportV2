package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JuegoActivity extends AppCompatActivity {

    public static String KeyCargar="key_cargar";

    public static String KeyListaPersonajes="key_listaPersonajes";
    public static String KeyListaCartasDistancia="key_cartasDistancia";
    public static String KeyListaCartasCuerpo="key_cartasCuerpo";
    public static String KeyListaCartasEspeciales="key_cartasEspeciales";
    public static String KeyListaCartasOtras="key_cartasOtras";
    public static String KeyListaPersonajesOtros="kee_listapersonakesotros";
    public static String KeyNombreSala="key_nombresala";
    public static String KeyUsuario="key_nombre_usuario";


    private TextView habAzul,habAmarilla, habNaranja1, habNaranja2, habRoja1, habRoja2,habRoja3,nombre;
    private ImageView foto;
    private ArrayList<Personaje> listaPersonajes,listaPersonajesOtros;
    private RecyclerView viewPersonajes;
    private LinearLayoutManager linlayoutmanager;
    private PersonajesAdapter adapterPersonajes;
    private int idPersonaje,idPersonajeInt;
    private int c1,c2;
    private ImageView carta1,carta2,carta3,carta4,carta5;
    private ArrayList<Carta> CartasDistancia,CartasCuerpo,CartasEspeciales,CartasOtras;
    private Button modozombie;
    private boolean intercambiar,intercambio;
    private ArrayList<BARRA> lista;
    private ArrayList<Integer> lista_Draw;
    private ArrayList<Integer> lista_red;
    private RecyclerView recy;
    private Button btn_plus, btn_less;
    private AdaptadorBarra adaptarBarra;
    private boolean cargar;
    private RecyclerView viewPersonajesOtros;
    private PersonajesAdapter adapterPersonajesOtros;
    private LinearLayoutManager linlayoutmanagerOtros;
    private boolean miPersonaje=true;
    private String textSala;
    private Button btIntercambiar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        habAzul=(TextView) findViewById(R.id.HabAzul);
        habAmarilla=(TextView)findViewById(R.id.HabAmarilla);
        habNaranja1=(TextView) findViewById(R.id.HabNaranja1);
        habNaranja2=(TextView) findViewById(R.id.HabNaranja2);
        habRoja1=(TextView) findViewById(R.id.HabRoja1);
        habRoja2=(TextView) findViewById(R.id.HabRoja2);
        habRoja3=(TextView) findViewById(R.id.HabRoja3);
        carta1=(ImageView)findViewById(R.id.Carta1);
        carta2=(ImageView)findViewById(R.id.Carta2);
        carta3=(ImageView)findViewById(R.id.Carta3);
        carta4=(ImageView)findViewById(R.id.Carta4);
        carta5=(ImageView)findViewById(R.id.Carta5);
        foto=(ImageView)findViewById(R.id.foto);
        nombre=(TextView)findViewById(R.id.nombre);
        modozombie = (Button) findViewById(R.id.ModoZombie);
        btIntercambiar=(Button)findViewById(R.id.BTNCambio);
        cargar=getIntent().getBooleanExtra(KeyCargar,false);
        textSala=getIntent().getExtras().getString(KeyNombreSala);

        listaPersonajes=new ArrayList<>();
        listaPersonajes= (ArrayList<Personaje>) getIntent().getSerializableExtra(KeyListaPersonajes);
        listaPersonajesOtros=new ArrayList<>();
        listaPersonajesOtros= (ArrayList<Personaje>) getIntent().getSerializableExtra(KeyListaPersonajesOtros);
        CartasDistancia=new ArrayList<>();
        CartasDistancia=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasDistancia);
        CartasCuerpo=new ArrayList<>();
        CartasCuerpo=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasCuerpo);
        CartasEspeciales=new ArrayList<>();
        CartasEspeciales=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasEspeciales);
        CartasOtras=new ArrayList<>();
        CartasOtras=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasOtras);

        viewPersonajes =(RecyclerView)findViewById(R.id.ViewPersonajes);
        linlayoutmanager =new LinearLayoutManager(this);
        viewPersonajes.setLayoutManager(linlayoutmanager);
        adapterPersonajes =new PersonajesAdapter(this,listaPersonajes);
        viewPersonajes.setAdapter(adapterPersonajes);
        idPersonaje=0;

        viewPersonajesOtros =(RecyclerView)findViewById(R.id.ViewUsuarios);
        linlayoutmanagerOtros =new LinearLayoutManager(this);
        viewPersonajesOtros.setLayoutManager(linlayoutmanagerOtros);
        adapterPersonajesOtros =new PersonajesAdapter(this,listaPersonajesOtros);
        viewPersonajesOtros.setAdapter(adapterPersonajesOtros);

        lista =new ArrayList<>();
        lista_Draw = new ArrayList<>();
        lista_red =new ArrayList<>();

        recy = (RecyclerView) findViewById(R.id.ViewLevel);
        btn_plus = (Button)findViewById(R.id.BTNmas);
        btn_less = (Button)findViewById(R.id.BTNmenos);

        recy.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        llenar_DATOS();
        adaptarBarra = new AdaptadorBarra(lista);
        recy.setAdapter(adaptarBarra);


        PersonajeSelec();
        ListenerCartas();
        ListenerHabilidades();
        ListenerFireBase();



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
                intercambio= (boolean) dataSnapshot.child("intercambiar").getValue();
                boolean aceptar= (boolean) dataSnapshot.child("aceptar").getValue();
                String pers1=dataSnapshot.child("personaje1").getValue().toString();
                String pers2=dataSnapshot.child("personaje2").getValue().toString();
                int persp = 0,persq=0;
                boolean hayInt = false;
                boolean IntIndividualA = false;
                boolean IntIndividualB = false;
                boolean IntMaster=false;
                if(intercambio){
                    if (!pers1.equals("")){
                        if (!pers2.equals("")){
                            for (int i=0;i<listaPersonajes.size();i++){
                                Personaje p=listaPersonajes.get(i);
                                if (p.getNombre().equals(pers1)){
                                    hayInt=true;
                                    persp=i;
                                    IntMaster=true;
                                    IntIndividualA=true;
                                }else if (p.getNombre().equals(pers2)){
                                    hayInt=true;
                                    persq=i;
                                    IntIndividualB=true;
                                }

                            }
                            for (int i=0;i<listaPersonajesOtros.size();i++){
                                Personaje p=listaPersonajesOtros.get(i);
                                if (p.getNombre().equals(pers1)){
                                    persp=i;
                                }else if (p.getNombre().equals(pers2)){
                                    persq=i;
                                }

                            }
                            if (hayInt){
                                Personaje p;
                                Personaje q;
                                if (IntIndividualA&IntIndividualB){
                                   p=listaPersonajes.get(persp);
                                   q=listaPersonajes.get(persq);
                                }else{
                                    if (IntMaster){
                                        p=listaPersonajes.get(persp);
                                        q=listaPersonajesOtros.get(persq);
                                    }else{
                                        q=listaPersonajes.get(persq);
                                        p=listaPersonajesOtros.get(persp);
                                    }


                                }

                                IRaIntercambiar(p,q,IntIndividualB,aceptar);
                            }
                        }

                    }


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
        Personaje p;

        for (int i=0;i<listaPersonajes.size();i++){
            p=listaPersonajes.get(i);
            if (p.getNombre().equals(PersonajeFireBase)){
                ActualizarPersonaje(dataSnapshot,p);
            }

        }

        for (int i=0;i<listaPersonajesOtros.size();i++){
            p=listaPersonajesOtros.get(i);
            if (p.getNombre().equals(PersonajeFireBase)){
                ActualizarPersonaje(dataSnapshot,p);
            }
            if (p.intercambiar){
                p=p;
                p.intercambiar=false;
            }
        }

        if(miPersonaje) {
            p=listaPersonajes.get(idPersonaje);
        }else{
            p=listaPersonajesOtros.get(idPersonaje);
        }
        if(p.getNombre().equals(PersonajeFireBase)){
            PersonajeSelec();
        }
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

        p.setInvisible((Boolean) dataSnapshot.child("invisible").getValue());
        p.level[0]= Integer.parseInt(dataSnapshot.child("level0").getValue().toString());
        p.level[1]= Integer.parseInt(dataSnapshot.child("level1").getValue().toString());
        p.level[2]= Integer.parseInt(dataSnapshot.child("level2").getValue().toString());
        p.level[3]= Integer.parseInt(dataSnapshot.child("level3").getValue().toString());
        p.level[4]= Integer.parseInt(dataSnapshot.child("level4").getValue().toString());
        p.setModozombie((Boolean) dataSnapshot.child("modozombie").getValue());
        p.puntuacion= Integer.parseInt(dataSnapshot.child("puntuacion").getValue().toString());
        p.setSelected((Boolean) dataSnapshot.child("selected").getValue());

        adapterPersonajesOtros.notifyDataSetChanged();
        adapterPersonajes.notifyDataSetChanged();
    }

    private void ModificarFireBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();
        for (int i=0;i<listaPersonajes.size();i++){
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("carta1").setValue(listaPersonajes.get(i).getCarta1());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("carta2").setValue(listaPersonajes.get(i).getCarta2());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("carta3").setValue(listaPersonajes.get(i).getCarta3());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("carta4").setValue(listaPersonajes.get(i).getCarta4());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("carta5").setValue(listaPersonajes.get(i).getCarta5());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("invisible").setValue(listaPersonajes.get(i).isInvisible());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("modozombie").setValue(listaPersonajes.get(i).isModozombie());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("level0").setValue(listaPersonajes.get(i).level[0]);
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("level1").setValue(listaPersonajes.get(i).level[1]);
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("level2").setValue(listaPersonajes.get(i).level[2]);
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("level3").setValue(listaPersonajes.get(i).level[3]);
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("level4").setValue(listaPersonajes.get(i).level[4]);
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("puntuacion").setValue(listaPersonajes.get(i).getPuntuacion());
            myref.child(textSala).child(listaPersonajes.get(i).getNombre()).child("selected").setValue(listaPersonajes.get(i).isSelected());
        }
    }

    private void ListenerHabilidades() {
        habNaranja1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (miPersonaje){
                    Personaje p=listaPersonajes.get(idPersonaje);
                    if (p.level[0]==1){
                        p.level[0]=2;
                        if (p.level[1]==1){
                            p.level[1]=0;
                        }
                    }
                    ModificarFireBase();

                }

            }
        });
        habNaranja2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (miPersonaje){
                    Personaje p=listaPersonajes.get(idPersonaje);
                    if (p.level[1]==1){
                        p.level[1]=2;
                        if (p.level[0]==1){
                            p.level[0]=0;
                        }
                    }
                    ModificarFireBase();

                }

            }
        });
        habRoja1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (miPersonaje){
                    Personaje p=listaPersonajes.get(idPersonaje);
                    if (p.level[2]==1){
                        p.level[2]=2;
                        if (p.level[3]==1){
                            p.level[3]=0;
                        }
                        if (p.level[4]==1){
                            p.level[4]=0;
                        }
                    }
                    ModificarFireBase();

                }

            }
        });
        habRoja2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (miPersonaje){
                    Personaje p=listaPersonajes.get(idPersonaje);
                    if (p.level[3]==1){
                        p.level[3]=2;
                        if (p.level[2]==1){
                            p.level[2]=0;
                        }
                        if (p.level[4]==1){
                            p.level[4]=0;
                        }
                    }
                    ModificarFireBase();

                }

            }
        });
        habRoja3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (miPersonaje){
                    Personaje p=listaPersonajes.get(idPersonaje);
                    if (p.level[4]==1){
                        p.level[4]=2;
                        if (p.level[2]==1){
                            p.level[2]=0;
                        }
                        if (p.level[3]==1){
                            p.level[3]=0;
                        }
                    }
                    ModificarFireBase();

                }

            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (miPersonaje){
                    Personaje p=listaPersonajes.get(idPersonaje);
                    if (p.puntuacion != 43) {
                        p.puntuacion++;
                        if (p.puntuacion==19){
                            if (p.vuelta==1){
                                p.level[0]=1;
                                p.level[1]=1;
                            }else if (p.vuelta==2){
                                if (p.level[0] == 0) {
                                    p.level[0]=2;
                                }
                                if (p.level[1] == 0) {
                                    p.level[1]=2;
                                }
                            }
                        }
                        if (p.puntuacion==43){
                            if (p.vuelta==1){
                                p.level[2]=1;
                                p.level[3]=1;
                                p.level[4]=1;
                            }else if (p.vuelta==2){
                                if (p.level[2] == 0) {
                                    p.level[2]=1;
                                }
                                if (p.level[3] == 0) {
                                    p.level[3]=1;
                                }
                                if (p.level[4] == 0) {
                                    p.level[4]=1;
                                }
                            }else if (p.vuelta==3) {
                                if (p.level[2] == 0) {
                                    p.level[2] = 2;
                                }
                                if (p.level[3] == 0) {
                                    p.level[3] = 2;
                                }
                                if (p.level[4] == 0) {
                                    p.level[4] = 2;
                                }
                            }
                        }
                        ModificarFireBase();

                    }
                }


            }
        });


        btn_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (miPersonaje){
                    Personaje p=listaPersonajes.get(idPersonaje);
                    if (p.puntuacion!=0) {
                        if (p.puntuacion == 0) {
                            p.puntuacion = 1;
                        }
                        p.puntuacion--;
                        if (p.puntuacion == 18) {
                            p.level[0] = 0;
                            p.level[1] = 0;
                        }
                        if (p.puntuacion == 42) {
                            p.level[2] = 0;
                            p.level[3] = 0;
                            p.level[4] = 0;
                        }
                        ModificarFireBase();

                    }
                }

            }
        });

        adapterPersonajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intercambiar){
                    intercambiar=false;
                    idPersonajeInt=viewPersonajes.getChildAdapterPosition(view);
                    viewPersonajes.setBackgroundColor(getColor(android.R.color.transparent));
                    viewPersonajesOtros.setBackgroundColor(getColor(android.R.color.transparent));
                    btIntercambiar.setBackgroundColor(getColor(R.color.black_overlay));
                    Personaje p=listaPersonajes.get(idPersonaje);
                    Personaje q=listaPersonajes.get(idPersonajeInt);
                    if (!p.getNombre().equals(q.getNombre())){
                        IntercambiarCartas(p,q);
                    }

                }else{
                    idPersonaje=viewPersonajes.getChildAdapterPosition(view);
                    miPersonaje=true;
                    PersonajeSelec();
                }

            }
        });
        adapterPersonajesOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intercambiar){
                    intercambiar=false;
                    idPersonajeInt=viewPersonajesOtros.getChildAdapterPosition(view);
                    viewPersonajesOtros.setBackgroundColor(getColor(android.R.color.transparent));
                    viewPersonajes.setBackgroundColor(getColor(android.R.color.transparent));
                    btIntercambiar.setBackgroundColor(getColor(R.color.black_overlay));
                    Personaje p=listaPersonajes.get(idPersonaje);
                    Personaje q=listaPersonajesOtros.get(idPersonajeInt);
                    if (!p.getNombre().equals(q.getNombre())){
                        IntercambiarCartas(p,q);
                    }

                }else{
                    idPersonaje=viewPersonajes.getChildAdapterPosition(view);
                    miPersonaje=false;
                    PersonajeSelec();
                }

            }
        });


    }

    private void ListenerCartas() {
        carta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarCarta();
            }
        });
        carta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarCarta();
            }
        });
        carta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarCarta();
            }
        });
        carta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarCarta();
            }
        });
        carta5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeleccionarCarta();
            }
        });
        carta1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (miPersonaje){
                    c1=0;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    //view.startDragAndDrop(data,shadowBuilder,view,0);
                    return true;
                }
                return false;
            }
        });
        carta1.setOnDragListener(new View.OnDragListener() {
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
        carta2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (miPersonaje){
                    c1=1;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    //view.startDragAndDrop(data,shadowBuilder,view,0);
                    return true;
                }
                return false;

            }
        });
        carta2.setOnDragListener(new View.OnDragListener() {
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
        carta3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (miPersonaje){
                    c1=2;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    //view.startDragAndDrop(data,shadowBuilder,view,0);
                    return true;
                }
                return false;


            }
        });
        carta3.setOnDragListener(new View.OnDragListener() {
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
        carta4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (miPersonaje){
                    c1=3;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    //view.startDragAndDrop(data,shadowBuilder,view,0);
                    return true;
                }
                return false;


            }
        });
        carta4.setOnDragListener(new View.OnDragListener() {
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
        carta5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (miPersonaje){
                    c1=4;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    //view.startDragAndDrop(data,shadowBuilder,view,0);
                    return true;
                }
                return false;


            }
        });
        carta5.setOnDragListener(new View.OnDragListener() {
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
        Personaje p=listaPersonajes.get(idPersonaje);
        p.intercambiar(p,c1,c2);
        ModificarFireBase();
    }

    private void IntercambiarCartas(final Personaje p, final Personaje q) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myref = database.getReference().child(textSala);

        myref.child("intercambio").child("intercambiar").setValue(true);
        myref.child("intercambio").child("personaje1").setValue(p.getNombre());
        myref.child("intercambio").child("personaje2").setValue(q.getNombre());

        ModificarFireBase();
        Toast.makeText(JuegoActivity.this, R.string.EsperandoAceptar, Toast.LENGTH_SHORT).show();

    }

    private void IRaIntercambiar(final Personaje p, final Personaje q, boolean IntB,boolean aceptar) {
        if (IntB&!aceptar){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(R.string.Intercambio);
            builder.setMessage(p.getNombre()+" "+getString(R.string.QuiereIntercambiar)+" "+q.getNombre());
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myref = database.getReference();
                    myref.child(textSala).child("intercambio").child("aceptar").setValue(true);

                    Intent intent=new Intent(JuegoActivity.this,IntercambioActivity.class);
                    intent.putExtra(IntercambioActivity.KeySala,textSala);
                    intent.putExtra(IntercambioActivity.Keycartas,p);
                    intent.putExtra(IntercambioActivity.Keycartas2,q);
                    startActivityForResult(intent,IntercambioActivity.pasarcartas);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myref = database.getReference();
                    myref.child(textSala).child("intercambio").child("intercambiar").setValue(false);
                    myref.child(textSala).child("intercambio").child("aceptar").setValue(false);
                    myref.child(textSala).child("intercambio").child("personaje1").setValue("");
                    myref.child(textSala).child("intercambio").child("personaje2").setValue("");

                }
            });
            builder.create().show();
        }if (!IntB){
            if (aceptar){
                Intent intent=new Intent(JuegoActivity.this,IntercambioActivity.class);
                intent.putExtra(IntercambioActivity.KeySala,textSala);
                intent.putExtra(IntercambioActivity.Keycartas,p);
                intent.putExtra(IntercambioActivity.Keycartas2,q);
                startActivityForResult(intent,IntercambioActivity.pasarcartas);
            }
        }


    }

    private void SeleccionarCarta() {
        if (miPersonaje){
            Intent intent=new Intent(JuegoActivity.this,CardsActivity.class);
            Personaje p=listaPersonajes.get(idPersonaje);
            intent.putExtra(CardsActivity.KeyPersonaje,p);
            intent.putExtra(CardsActivity.KeyListaCartasDistancia,CartasDistancia );
            intent.putExtra(CardsActivity.KeyListaCartasCuerpo, CartasCuerpo);
            intent.putExtra(CardsActivity.KeyListaCartasEspeciales,CartasEspeciales);
            intent.putExtra(CardsActivity.KeyListaCartasOtras, CartasOtras);
            startActivityForResult(intent,CardsActivity.pasarcartas);
        }

    }

    private void PersonajeSelec() {
        Personaje p;
        if(miPersonaje){
            p = listaPersonajes.get(idPersonaje);
        }else{
            p = listaPersonajesOtros.get(idPersonaje);
        }


        nombre.setText(p.getNombre());
        if (p.modozombie) {
            habAzul.setText(p.getHabAzulZ());
            habAmarilla.setText(p.getHabAmarillaZ());
            habNaranja1.setText(p.getHabNaranja1Z());
            habNaranja2.setText(p.getHabNaranja2Z());
            habRoja1.setText(p.getHabRoja1Z());
            habRoja2.setText(p.getHabRoja2Z());
            habRoja3.setText(p.getHabRoja3Z());
            foto.setImageResource(p.getCaraZ());
            modozombie.setText(getString(R.string.ModoHumano));
        }else{
            habAzul.setText(p.getHabAzul());
            habAmarilla.setText(p.getHabAmarilla());
            habNaranja1.setText(p.getHabNaranja1());
            habNaranja2.setText(p.getHabNaranja2());
            habRoja1.setText(p.getHabRoja1());
            habRoja2.setText(p.getHabRoja2());
            habRoja3.setText(p.getHabRoja3());
            foto.setImageResource(p.getCara());
            modozombie.setText(getString(R.string.ModoZombie));
        }
        carta1.setImageResource(p.getCarta1().getCarta());
        carta2.setImageResource(p.getCarta2().getCarta());
        carta3.setImageResource(p.getCarta3().getCarta());
        carta4.setImageResource(p.getCarta4().getCarta());
        carta5.setImageResource(p.getCarta5().getCarta());

        lista.clear();
        llenar_DATOS();
        for (int i=0;i<p.puntuacion;i++){
            lista.set(i, new BARRA(lista_red.get(i)));
            lista.set(i+1, new BARRA(R.drawable.puntero, lista_red.get(i+1)));
        }
        adaptarBarra.notifyDataSetChanged();
        if (p.vuelta==1){
            if (p.puntuacion<7){
                habAmarilla.setBackgroundColor(getColor(android.R.color.white));

            }else{
                habAmarilla.setBackgroundColor(getColor(R.color.yellow));
            }
        }else{
            habAmarilla.setBackgroundColor(getColor(R.color.yellow));
        }


        if (p.level[0]==0) {
            habNaranja1.setBackgroundColor(getColor(android.R.color.white));
        } else if (p.level[0] == 1) {
            habNaranja1.setBackgroundColor(getColor(android.R.color.holo_green_dark));
        } else if (p.level[0] == 2) {
            habNaranja1.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
        }

        if (p.level[1]==0) {
            habNaranja2.setBackgroundColor(getColor(android.R.color.white));
        } else if (p.level[1] == 1) {
            habNaranja2.setBackgroundColor(getColor(android.R.color.holo_green_dark));
        } else if (p.level[1] == 2) {
            habNaranja2.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
        }

        if (p.level[2]==0) {
            habRoja1.setBackgroundColor(getColor(android.R.color.white));
        } else if (p.level[2] == 1) {
            habRoja1.setBackgroundColor(getColor(android.R.color.holo_green_dark));
        } else if (p.level[2] == 2) {
            habRoja1.setBackgroundColor(getColor(android.R.color.holo_red_dark));
        }

        if (p.level[3]==0) {
            habRoja2.setBackgroundColor(getColor(android.R.color.white));
        } else if (p.level[3] == 1) {
            habRoja2.setBackgroundColor(getColor(android.R.color.holo_green_dark));
        } else if (p.level[3] == 2) {
            habRoja2.setBackgroundColor(getColor(android.R.color.holo_red_dark));
        }

        if (p.level[4]==0) {
            habRoja3.setBackgroundColor(getColor(android.R.color.white));
        } else if (p.level[4] == 1) {
            habRoja3.setBackgroundColor(getColor(android.R.color.holo_green_dark));
        } else if (p.level[4] == 2) {
            habRoja3.setBackgroundColor(getColor(android.R.color.holo_red_dark));
        }
    }

    private void llenar_DATOS(){

        lista_Draw.add(R.drawable.level_43);
        lista_Draw.add(R.drawable.level_42);
        lista_Draw.add(R.drawable.level_41);
        lista_Draw.add(R.drawable.level_40);
        lista_Draw.add(R.drawable.level_39);
        lista_Draw.add(R.drawable.level_38);
        lista_Draw.add(R.drawable.level_37);
        lista_Draw.add(R.drawable.level_36);
        lista_Draw.add(R.drawable.level_35);
        lista_Draw.add(R.drawable.level_34);
        lista_Draw.add(R.drawable.level_33);
        lista_Draw.add(R.drawable.level_32);
        lista_Draw.add(R.drawable.level_31);
        lista_Draw.add(R.drawable.level_30);
        lista_Draw.add(R.drawable.level_29);
        lista_Draw.add(R.drawable.level_28);
        lista_Draw.add(R.drawable.level_27);
        lista_Draw.add(R.drawable.level_26);
        lista_Draw.add(R.drawable.level_25);
        lista_Draw.add(R.drawable.level_24);
        lista_Draw.add(R.drawable.level_23);
        lista_Draw.add(R.drawable.level_22);
        lista_Draw.add(R.drawable.level_21);
        lista_Draw.add(R.drawable.level_20);
        lista_Draw.add(R.drawable.level_19);
        lista_Draw.add(R.drawable.level_18);
        lista_Draw.add(R.drawable.level_17);
        lista_Draw.add(R.drawable.level_16);
        lista_Draw.add(R.drawable.level_15);
        lista_Draw.add(R.drawable.level_14);
        lista_Draw.add(R.drawable.level_13);
        lista_Draw.add(R.drawable.level_12);
        lista_Draw.add(R.drawable.level_11);
        lista_Draw.add(R.drawable.level_10);
        lista_Draw.add(R.drawable.level_9);
        lista_Draw.add(R.drawable.level_8);
        lista_Draw.add(R.drawable.level_7);
        lista_Draw.add(R.drawable.level_6);
        lista_Draw.add(R.drawable.level_5);
        lista_Draw.add(R.drawable.level_4);
        lista_Draw.add(R.drawable.level_3);
        lista_Draw.add(R.drawable.level_2);
        lista_Draw.add(R.drawable.level_1);
        lista_Draw.add(R.drawable.level_0);

        lista_red.add(R.drawable.red_0);
        lista_red.add(R.drawable.red_1);
        lista_red.add(R.drawable.red_2);
        lista_red.add(R.drawable.red_3);
        lista_red.add(R.drawable.red_4);
        lista_red.add(R.drawable.red_5);
        lista_red.add(R.drawable.red_6);
        lista_red.add(R.drawable.red_7);
        lista_red.add(R.drawable.red_8);
        lista_red.add(R.drawable.red_9);
        lista_red.add(R.drawable.red_10);
        lista_red.add(R.drawable.red_11);
        lista_red.add(R.drawable.red_12);
        lista_red.add(R.drawable.red_13);
        lista_red.add(R.drawable.red_14);
        lista_red.add(R.drawable.red_15);
        lista_red.add(R.drawable.red_16);
        lista_red.add(R.drawable.red_17);
        lista_red.add(R.drawable.red_18);
        lista_red.add(R.drawable.red_19);
        lista_red.add(R.drawable.red_20);
        lista_red.add(R.drawable.red_21);
        lista_red.add(R.drawable.red_22);
        lista_red.add(R.drawable.red_23);
        lista_red.add(R.drawable.red_24);
        lista_red.add(R.drawable.red_25);
        lista_red.add(R.drawable.red_26);
        lista_red.add(R.drawable.red_27);
        lista_red.add(R.drawable.red_28);
        lista_red.add(R.drawable.red_29);
        lista_red.add(R.drawable.red_30);
        lista_red.add(R.drawable.red_31);
        lista_red.add(R.drawable.red_32);
        lista_red.add(R.drawable.red_33);
        lista_red.add(R.drawable.red_34);
        lista_red.add(R.drawable.red_35);
        lista_red.add(R.drawable.red_36);
        lista_red.add(R.drawable.red_37);
        lista_red.add(R.drawable.red_38);
        lista_red.add(R.drawable.red_39);
        lista_red.add(R.drawable.red_40);
        lista_red.add(R.drawable.red_41);
        lista_red.add(R.drawable.red_42);
        lista_red.add(R.drawable.red_43);
        lista.add(new BARRA(R.drawable.puntero,R.drawable.red_0));
        lista.add(new BARRA(R.drawable.level_1));
        lista.add(new BARRA(R.drawable.level_2));
        lista.add(new BARRA(R.drawable.level_3));
        lista.add(new BARRA(R.drawable.level_4));
        lista.add(new BARRA(R.drawable.level_5));
        lista.add(new BARRA(R.drawable.level_6));
        lista.add(new BARRA(R.drawable.level_7));
        lista.add(new BARRA(R.drawable.level_8));
        lista.add(new BARRA(R.drawable.level_9));
        lista.add(new BARRA(R.drawable.level_10));
        lista.add(new BARRA(R.drawable.level_11));
        lista.add(new BARRA(R.drawable.level_12));
        lista.add(new BARRA(R.drawable.level_13));
        lista.add(new BARRA(R.drawable.level_14));
        lista.add(new BARRA(R.drawable.level_15));
        lista.add(new BARRA(R.drawable.level_16));
        lista.add(new BARRA(R.drawable.level_17));
        lista.add(new BARRA(R.drawable.level_18));
        lista.add(new BARRA(R.drawable.level_19));
        lista.add(new BARRA(R.drawable.level_20));
        lista.add(new BARRA(R.drawable.level_21));
        lista.add(new BARRA(R.drawable.level_22));
        lista.add(new BARRA(R.drawable.level_23));
        lista.add(new BARRA(R.drawable.level_24));
        lista.add(new BARRA(R.drawable.level_25));
        lista.add(new BARRA(R.drawable.level_26));
        lista.add(new BARRA(R.drawable.level_27));
        lista.add(new BARRA(R.drawable.level_28));
        lista.add(new BARRA(R.drawable.level_29));
        lista.add(new BARRA(R.drawable.level_30));
        lista.add(new BARRA(R.drawable.level_31));
        lista.add(new BARRA(R.drawable.level_32));
        lista.add(new BARRA(R.drawable.level_33));
        lista.add(new BARRA(R.drawable.level_34));
        lista.add(new BARRA(R.drawable.level_35));
        lista.add(new BARRA(R.drawable.level_36));
        lista.add(new BARRA(R.drawable.level_37));
        lista.add(new BARRA(R.drawable.level_38));
        lista.add(new BARRA(R.drawable.level_39));
        lista.add(new BARRA(R.drawable.level_40));
        lista.add(new BARRA(R.drawable.level_41));
        lista.add(new BARRA(R.drawable.level_42));
        lista.add(new BARRA(R.drawable.level_43));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CardsActivity.pasarcartas){
            if (resultCode==RESULT_OK){
                Personaje p=listaPersonajes.get(idPersonaje);
                Personaje pcard= (Personaje) data.getExtras().getSerializable(CardsActivity.KeyPersonaje);
                p.setCarta1(pcard.getCarta1());
                p.setCarta2(pcard.getCarta2());
                p.setCarta3(pcard.getCarta3());
                p.setCarta4(pcard.getCarta4());
                p.setCarta5(pcard.getCarta5());
                ModificarFireBase();

            }
        }
        if (requestCode==IntercambioActivity.pasarcartas){
            if (resultCode==RESULT_OK){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myref = database.getReference();
                myref.child(textSala).child("intercambio").child("intercambiar").setValue(false);
                myref.child(textSala).child("intercambio").child("aceptar").setValue(false);
                myref.child(textSala).child("intercambio").child("personaje1").setValue("");
                myref.child(textSala).child("intercambio").child("personaje2").setValue("");

            }
        }

    }

   public void Intercambiar(View view) {
        if (miPersonaje){
            if (!intercambio){
                if (!intercambiar){
                    viewPersonajes.setBackgroundColor(getColor(android.R.color.holo_green_dark));
                    viewPersonajesOtros.setBackgroundColor(getColor(android.R.color.holo_green_dark));
                    btIntercambiar.setBackgroundColor(getColor(android.R.color.holo_red_dark));
                    intercambiar=true;
                }else {
                    viewPersonajes.setBackgroundColor(getColor(android.R.color.transparent));
                    viewPersonajesOtros.setBackgroundColor(getColor(android.R.color.transparent));
                    btIntercambiar.setBackgroundColor(getColor(R.color.black_overlay));
                    intercambiar=false;
                }
            }else{
                Toast.makeText(JuegoActivity.this, R.string.HayIntercambio, Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void ModoZombie(View view) {
        if (miPersonaje){
            Personaje p=listaPersonajes.get(idPersonaje);
            p.modozombie=!p.modozombie;
            if (p.puntuacion>18){
                p.level[0]=1;
                p.level[1]=1;
            }
            if (p.puntuacion==43){
                p.level[2]=1;
                p.level[3]=1;
                p.level[4]=1;

            }
            ModificarFireBase();
            PersonajeSelec();
            adapterPersonajes.notifyDataSetChanged();
        }
    }
}
