package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.ClipData;
import android.content.Intent;
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

import java.util.ArrayList;

public class JuegoActivity extends AppCompatActivity {


    public static String KeyListaPersonajes="key_listaPersonajes";
    public static String KeyListaCartasDistancia="key_cartasDistancia";
    public static String KeyListaCartasCuerpo="key_cartasCuerpo";
    public static String KeyListaCartasEspeciales="key_cartasEspeciales";
    public static String KeyListaCartasOtras="key_cartasOtras";
    private TextView habAzul,habAmarilla, habNaranja1, habNaranja2, habRoja1, habRoja2,habRoja3,nombre;
    private ImageView foto;
    private ArrayList<Personaje> listaPersonajes;
    private RecyclerView viewPersonajes;
    private LinearLayoutManager linlayoutmanager;
    private PersonajesAdapter adapterPersonajes;
    private int idPersonaje,idPersonajeInt;
    private int c1,c2;
    private ImageView carta1,carta2,carta3,carta4,carta5;
    private ArrayList<Carta> CartasDistancia,CartasCuerpo,CartasEspeciales,CartasOtras;
    private Switch modozombie;
    private boolean intercambiar;
    ArrayList<BARRA> lista;
    ArrayList<Integer> lista_Draw;
    ArrayList<Integer> lista_red;
    RecyclerView recy;
    Button btn_plus, btn_less;
    int CONTADOR_VUELTA=0;
    int numero =1;
    int numero_less =0;
    boolean primera_vuelta=false;
    boolean end= false;
    boolean btn_mas_pulsado=false;
    boolean btn_menos_pulsado =false;
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
        modozombie = (Switch) findViewById(R.id.ModoZombie);

        listaPersonajes=new ArrayList<>();
        listaPersonajes= (ArrayList<Personaje>) getIntent().getSerializableExtra(KeyListaPersonajes);
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

        PersonajeSelec();

        lista =new ArrayList<>();
        lista_Draw = new ArrayList<>();
        lista_red =new ArrayList<>();


        recy = (RecyclerView) findViewById(R.id.ViewLevel);
        btn_plus = (Button)findViewById(R.id.BTNmas);
        btn_less = (Button)findViewById(R.id.BTNmenos);

        recy.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        llenar_DATOS();
        final AdaptadorBarra adaptarBarra = new AdaptadorBarra(lista);
        recy.setAdapter(adaptarBarra);

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Move_plus(adaptarBarra);
                if (end) btn_plus.setEnabled(false);
            }
        });


        btn_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numero_less>0) move_less(adaptarBarra);
            }
        });


        adapterPersonajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intercambiar){
                    intercambiar=false;
                    idPersonajeInt=viewPersonajes.getChildAdapterPosition(view);
                    viewPersonajes.setBackgroundColor(getColor(android.R.color.transparent));
                    if (idPersonaje!=idPersonajeInt){
                        IntercambiarCartas();
                    }

                }else{
                    idPersonaje=viewPersonajes.getChildAdapterPosition(view);
                    PersonajeSelec();
                }

            }
        });
        carta1.setOnLongClickListener(new View.OnLongClickListener() {
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
                c1=1;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

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
                c1=2;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

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
                c1=3;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

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
                c1=4;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;

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
        modozombie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Personaje p=listaPersonajes.get(idPersonaje);
                p.modozombie=!p.modozombie;
                PersonajeSelec();
                adapterPersonajes.notifyDataSetChanged();
            }
        });
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
    }

    private void MovimientoCarta() {
        Personaje p=listaPersonajes.get(idPersonaje);
        p.intercambiar(p,c1,c2);
        PersonajeSelec();
    }

    private void IntercambiarCartas() {
        Intent intent=new Intent(JuegoActivity.this,IntercambioActivity.class);
        Personaje p=listaPersonajes.get(idPersonaje);
        Personaje q=listaPersonajes.get(idPersonajeInt);

        intent.putExtra(IntercambioActivity.Keycartas,p);
        intent.putExtra(IntercambioActivity.Keycartas2,q);
        startActivityForResult(intent,IntercambioActivity.pasarcartas);
    }

    private void SeleccionarCarta() {
        Intent intent=new Intent(JuegoActivity.this,CardsActivity.class);
        Personaje p=listaPersonajes.get(idPersonaje);
        intent.putExtra(CardsActivity.KeyPersonaje,p);
        intent.putExtra(CardsActivity.KeyListaCartasDistancia,CartasDistancia );
        intent.putExtra(CardsActivity.KeyListaCartasCuerpo, CartasCuerpo);
        intent.putExtra(CardsActivity.KeyListaCartasEspeciales,CartasEspeciales);
        intent.putExtra(CardsActivity.KeyListaCartasOtras, CartasOtras);
        startActivityForResult(intent,CardsActivity.pasarcartas);
    }


    private void PersonajeSelec() {
        Personaje p = listaPersonajes.get(idPersonaje);
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
        }else{
            habAzul.setText(p.getHabAzul());
            habAmarilla.setText(p.getHabAmarilla());
            habNaranja1.setText(p.getHabNaranja1());
            habNaranja2.setText(p.getHabNaranja2());
            habRoja1.setText(p.getHabRoja1());
            habRoja2.setText(p.getHabRoja2());
            habRoja3.setText(p.getHabRoja3());
            foto.setImageResource(p.getCara());
        }
        carta1.setImageResource(p.getCarta1().getCarta());
        carta2.setImageResource(p.getCarta2().getCarta());
        carta3.setImageResource(p.getCarta3().getCarta());
        carta4.setImageResource(p.getCarta4().getCarta());
        carta5.setImageResource(p.getCarta5().getCarta());

        if (!p.level[0]){
            habAmarilla.setBackgroundColor(getColor(android.R.color.white));
        }else{
            habAmarilla.setBackgroundColor(getColor(R.color.yellow));
        }
        if (!p.level[1]){
            habNaranja1.setBackgroundColor(getColor(android.R.color.white));
        }else{
            habNaranja1.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
        }
        if (!p.level[2]){
            habNaranja2.setBackgroundColor(getColor(android.R.color.white));
        }else {
            habNaranja2.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
        }
        if (!p.level[3]){
            habRoja1.setBackgroundColor(getColor(android.R.color.white));
        }else{
            habRoja1.setBackgroundColor(getColor(android.R.color.holo_red_dark));
        }
        if (!p.level[4]){
            habRoja2.setBackgroundColor(getColor(android.R.color.white));
        }else{
            habRoja2.setBackgroundColor(getColor(android.R.color.holo_red_dark));
        }
        if (!p.level[5]){
            habRoja3.setBackgroundColor(getColor(android.R.color.white));
        }else{
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
    private void move_less(AdaptadorBarra adaptarBarra) {

        if(btn_mas_pulsado&&!primera_vuelta) numero_less =numero-1;
        if (primera_vuelta){
            numero_less = 43;
            primera_vuelta=false;
            CONTADOR_VUELTA --;
        }
        if(end) {numero_less=43;end =false;}

        if(numero_less==43){
            lista.set(43,new BARRA(lista_red.get(43)));
            lista.set(42,new BARRA(R.drawable.puntero,lista_red.get(42)));
            btn_plus.setEnabled(true);
            primera_vuelta = false;
            numero_less = 42;
            numero =43;
        }

        else if (numero_less>0){
            lista.set(numero_less,new BARRA(lista_red.get(numero_less)));
            lista.set(numero_less-1,new BARRA(R.drawable.puntero,lista_red.get(numero_less-1)));
            btn_plus.setEnabled(true);
            numero_less--;
        }

        btn_menos_pulsado = true;
        btn_mas_pulsado =false;
        adaptarBarra.notifyDataSetChanged();
    }
    private void Move_plus(AdaptadorBarra adaptarBarra) {


        if (!primera_vuelta) {
            if(btn_menos_pulsado) numero =numero_less +1;
            lista.set(numero - 1, new BARRA(lista_red.get(numero - 1)));
            lista.set(numero, new BARRA(R.drawable.puntero, lista_red.get(numero)));

            if (numero < 43){
                numero++;numero_less++;
            }else {
                if (CONTADOR_VUELTA < 1) {
                    numero = 1;
                    CONTADOR_VUELTA++;
                    primera_vuelta = true;

                } else {

                    end = true;}
            }


        }else{
            lista.set(43, new BARRA(lista_red.get(43)));
            lista.set(0, new BARRA(R.drawable.puntero, lista_red.get(0)));

            primera_vuelta = false;
            numero_less =0;

        }

        btn_mas_pulsado =true;
        btn_menos_pulsado = false;
        adaptarBarra.notifyDataSetChanged();
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
                PersonajeSelec();

            }
        }
        if (requestCode==IntercambioActivity.pasarcartas){
            if (resultCode==RESULT_OK){
                Personaje p1=listaPersonajes.get(idPersonaje);
                Personaje pcard1= (Personaje) data.getExtras().getSerializable(IntercambioActivity.Keycartas);
                p1.setCarta1(pcard1.getCarta1());
                p1.setCarta2(pcard1.getCarta2());
                p1.setCarta3(pcard1.getCarta3());
                p1.setCarta4(pcard1.getCarta4());
                p1.setCarta5(pcard1.getCarta5());
                Personaje p2=listaPersonajes.get(idPersonajeInt);
                Personaje pcard2= (Personaje) data.getExtras().getSerializable(IntercambioActivity.Keycartas2);
                p2.setCarta1(pcard2.getCarta1());
                p2.setCarta2(pcard2.getCarta2());
                p2.setCarta3(pcard2.getCarta3());
                p2.setCarta4(pcard2.getCarta4());
                p2.setCarta5(pcard2.getCarta5());
                PersonajeSelec();

            }
        }

    }

   public void Intercambiar(View view) {
        viewPersonajes.setBackgroundColor(getColor(android.R.color.holo_green_dark));
        intercambiar=true;
    }
}
