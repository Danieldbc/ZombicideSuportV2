package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {
    public static String KeyPersonaje ="key_personaje";
    public static int pasarcartas=1;

    public static String KeyListaCartasDistancia="key_cartasDistancia";
    public static String KeyListaCartasCuerpo="key_cartasCuerpo";
    public static String KeyListaCartasEspeciales="key_cartasEspeciales";
    public static String KeyListaCartasOtras="key_cartasOtras";

    private ArrayList<Carta> listacartas;
    private ArrayList<Carta> CartasDistancia,CartasCuerpo,CartasEspeciales,CartasOtras;
    private CartasAdapter adaptercartas;
    private RecyclerView viewCartas;
    private LinearLayoutManager linlayoutmanager;
    private ImageView carta1,carta2,carta3,carta4,carta5;
    private int idcarta;
    private Personaje p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        listacartas=new ArrayList<>();

        p= (Personaje) getIntent().getSerializableExtra(KeyPersonaje);
        CartasDistancia=new ArrayList<>();
        CartasDistancia=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasDistancia);
        CartasCuerpo=new ArrayList<>();
        CartasCuerpo=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasCuerpo);
        CartasEspeciales=new ArrayList<>();
        CartasEspeciales=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasEspeciales);
        CartasOtras=new ArrayList<>();
        CartasOtras=(ArrayList<Carta>)getIntent().getSerializableExtra(KeyListaCartasOtras);

        Crear();
        viewCartas=(RecyclerView)findViewById(R.id.ViewCartas);
        linlayoutmanager =new LinearLayoutManager(this);
        viewCartas.setLayoutManager(linlayoutmanager);
        adaptercartas =new CartasAdapter(this,listacartas);
        viewCartas.setAdapter(adaptercartas);


        carta1=(ImageView)findViewById(R.id.Carta1);
        carta2=(ImageView)findViewById(R.id.Carta2);
        carta3=(ImageView)findViewById(R.id.Carta3);
        carta4=(ImageView)findViewById(R.id.Carta4);
        carta5=(ImageView)findViewById(R.id.Carta5);


        Mostrar();
        Listeners();


    }

    //se muestran las cartas del personaje
    private void Mostrar() {
        carta1.setImageResource(p.getCarta1().getCarta());
        carta2.setImageResource(p.getCarta2().getCarta());
        carta3.setImageResource(p.getCarta3().getCarta());
        carta4.setImageResource(p.getCarta4().getCarta());
        carta5.setImageResource(p.getCarta5().getCarta());
    }

    //se crean los listener del drag and drop de las cartas
    private void Listeners() {
        adaptercartas.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                idcarta=viewCartas.getChildAdapterPosition(view);
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.startDragAndDrop(data,shadowBuilder,view,0);
                return true;
            }
        });
        carta1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Seleccionar(1);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Seleccionar(2);;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Seleccionar(3);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Seleccionar(4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        carta5.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Seleccionar(5);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    //Al soltar la carta se cambia la carta seleccioanda por la que habia anteriormente
    private void Seleccionar(int i) {
        Carta c=listacartas.get(idcarta);
        if (i==1) {
            p.setCarta1(c);
        }else if (i==2) {
            p.setCarta2(c);
        }else if (i==3) {
            p.setCarta3(c);
        }else if (i==4) {
            p.setCarta4(c);
        }else if (i==5) {
            p.setCarta5(c);
        }

        Mostrar();

    }


    //se crean las cartas
    private void Crear() {
        OtrosCartas();
        EspecialesCartas();
        CuerpoCartas();
        DistanciaCartas();
    }

    //se rellenan las cartas segun la seleccion del boton
    private void DistanciaCartas() {
        for (int i=0;i<CartasDistancia.size();i++) {
            listacartas.add(CartasDistancia.get(i));
        }
    }
    private void CuerpoCartas() {
        for (int i=0;i<CartasCuerpo.size();i++) {
            listacartas.add(CartasCuerpo.get(i));
        }
    }
    private void EspecialesCartas() {
        for (int i=0;i<CartasEspeciales.size();i++) {
            listacartas.add(CartasEspeciales.get(i));
        }
    }
    private void OtrosCartas() {
        for (int i=0;i<CartasOtras.size();i++) {
            listacartas.add(CartasOtras.get(i));
        }
    }

    //al pulsar el boton se borra la lista y se rellena de las cartas del tipo seleccionado
    public void Todas(View view) {
        listacartas.clear();
        Crear();
        adaptercartas.notifyDataSetChanged();
        viewCartas.smoothScrollToPosition(0);
    }
    public void Distancia(View view) {
        listacartas.clear();
        DistanciaCartas();
        adaptercartas.notifyDataSetChanged();
        viewCartas.smoothScrollToPosition(0);
    }
    public void Cuerpo(View view) {
        listacartas.clear();
        CuerpoCartas();
        adaptercartas.notifyDataSetChanged();
        viewCartas.smoothScrollToPosition(0);
    }
    public void Especiales(View view) {
        listacartas.clear();
        EspecialesCartas();
        adaptercartas.notifyDataSetChanged();
        viewCartas.smoothScrollToPosition(0);
    }
    public void Otros(View view) {
        listacartas.clear();
        OtrosCartas();
        adaptercartas.notifyDataSetChanged();
        viewCartas.smoothScrollToPosition(0);
    }

    public void Aceptar(View view) {
        Intent data=new Intent();
        data.putExtra(KeyPersonaje,p);
        setResult(RESULT_OK,data);
        finish();
    }

    public void Cancelar(View view) {
        finish();
    }
}
