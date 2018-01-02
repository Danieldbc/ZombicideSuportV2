package edu.eseiaat.upc.pma.manuel.daniel.zombicidesuport;

import java.io.Serializable;

/**
 * Created by PortatilDani on 17/11/2017.
 */

public class Personaje implements Serializable {
    public String nombre,habAzul, habAmarilla, habNaranja1,habNaranja2, habRoja1,HabRoja2,habRoja3;
    public int foto,cara;
    public String habAzulZ, habAmarillaZ, habNaranja1Z,habNaranja2Z, habRoja1Z,HabRoja2Z,habRoja3Z;
    public int fotoZ,caraZ;
    public Carta[] cartas;
    public boolean invisible,modozombie;
    public int[] level;
    public int puntuacion,vuelta;

    public Personaje(){};

    public Personaje(String nombre, String habAzul, String habAmarilla, String habNaranja1, String habNaranja2, String habRoja1, String habRoja2, String habRoja3, int foto, int cara, String habAzulZ, String habAmarillaZ, String habNaranja1Z, String habNaranja2Z, String habRoja1Z, String habRoja2Z, String habRoja3Z, int fotoZ, int caraZ, Carta carta1, Carta carta2, Carta carta3, Carta carta4, Carta carta5, boolean invisible, boolean modozombie, int[] level, int puntuacion,int vuelta) {
        this.nombre = nombre;
        this.habAzul = habAzul;
        this.habAmarilla = habAmarilla;
        this.habNaranja1 = habNaranja1;
        this.habNaranja2 = habNaranja2;
        this.habRoja1 = habRoja1;
        HabRoja2 = habRoja2;
        this.habRoja3 = habRoja3;
        this.foto = foto;
        this.cara = cara;
        this.habAzulZ = habAzulZ;
        this.habAmarillaZ = habAmarillaZ;
        this.habNaranja1Z = habNaranja1Z;
        this.habNaranja2Z = habNaranja2Z;
        this.habRoja1Z = habRoja1Z;
        HabRoja2Z = habRoja2Z;
        this.habRoja3Z = habRoja3Z;
        this.fotoZ = fotoZ;
        this.caraZ = caraZ;
        this.cartas = new Carta[5];
        cartas[0] = carta1;
        cartas[1] = carta2;
        cartas[2] = carta3;
        cartas[3] = carta4;
        cartas[4] = carta5;
        this.invisible = invisible;
        this.modozombie = modozombie;
        this.level = level;
        this.puntuacion = puntuacion;
        this.vuelta = vuelta;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHabAzul() {
        return habAzul;
    }

    public String getHabAmarilla() {
        return habAmarilla;
    }

    public String getHabNaranja1() {
        return habNaranja1;
    }

    public String getHabNaranja2() {
        return habNaranja2;
    }

    public String getHabRoja1() {
        return habRoja1;
    }

    public String getHabRoja2() {
        return HabRoja2;
    }

    public String getHabRoja3() {
        return habRoja3;
    }

    public int getFoto() {
        return foto;
    }

    public int getCara() {
        return cara;
    }

    public String getHabAzulZ() {
        return habAzulZ;
    }

    public String getHabAmarillaZ() {
        return habAmarillaZ;
    }

    public String getHabNaranja1Z() {
        return habNaranja1Z;
    }

    public String getHabNaranja2Z() {
        return habNaranja2Z;
    }

    public String getHabRoja1Z() {
        return habRoja1Z;
    }

    public String getHabRoja2Z() {
        return HabRoja2Z;
    }

    public String getHabRoja3Z() {
        return habRoja3Z;
    }

    public int getFotoZ() {
        return fotoZ;
    }

    public int getCaraZ() {
        return caraZ;
    }

    public Carta getCarta(int i) {
        return cartas[i];
    }

    public Carta setCarta(int i,Carta carta) {
        return this.cartas[i]=carta;
    }

    public Carta getCarta1() {
        return cartas[0];
    }

    public void setCarta1(Carta carta1) {
        this.cartas[0] = carta1;
    }

    public Carta getCarta2() {
        return cartas[1];
    }

    public void setCarta2(Carta carta2) {
        this.cartas[1] = carta2;
    }

    public Carta getCarta3() {
        return cartas[2];
    }

    public void setCarta3(Carta carta3) {
        this.cartas[2] = carta3;
    }

    public Carta getCarta4() {
        return cartas[3];
    }

    public void setCarta4(Carta carta4) {
        this.cartas[3] = carta4;
    }

    public Carta getCarta5() {
        return cartas[4];
    }

    public void setCarta5(Carta carta5) {
        this.cartas[4] = carta5;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isModozombie() {
        return modozombie;
    }

    public void setModozombie(boolean modozombie) {
        this.modozombie = modozombie;
    }

    public int[] getLevel() {
        return level;
    }

    public void setLevel(int[] level) {
        this.level = level;
    }

    public void intercambiar(Personaje p, int c1,int c2){
        Carta aux=p.getCarta(c1);
        p.setCarta(c1,this.getCarta(c2));
        this.setCarta(c2,aux);
    }


}

