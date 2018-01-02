package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.Serializable;

/**
 * Created by PortatilDani on 11/12/2017.
 */

public class Carta implements Serializable {
    public int carta;
    public String nombre;

    public Carta() {
    }

    public Carta(int carta, String nombre) {
        this.carta = carta;
        this.nombre = nombre;
    }

    public int getCarta() {
        return carta;
    }

    public void setCarta(int carta) {
        this.carta = carta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
