package main;

public class Vrchol {
    private int cena;
    private int predposlednyVrchol;
    private boolean kontrola;

    public Vrchol(int cena) {
        this.cena = cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public void setPredposlednyVrchol(int predposlednyVrchol) {
        this.predposlednyVrchol = predposlednyVrchol;
    }

    public void setKontrola(boolean kontrola) {
        this.kontrola = kontrola;
    }

    public int getCena() {
        return cena;
    }

    public int getPredposlednyVrchol() {
        return predposlednyVrchol;
    }

    public boolean isKontrola() {
        return kontrola;
    }
}
