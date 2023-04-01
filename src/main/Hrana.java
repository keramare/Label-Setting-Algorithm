package main;

public class Hrana {
    private int zaciatocny;
    private int koncovy;
    private int cena;

    public Hrana(int zaciatocny, int koncovy, int cena) {
        this.zaciatocny = zaciatocny;
        this.koncovy = koncovy;
        this.cena = cena;
    }

    public void setZaciatocny(int zaciatocny) {
        this.zaciatocny = zaciatocny;
    }

    public void setKoncovy(int koncovy) {
        this.koncovy = koncovy;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getZaciatocny() {
        return zaciatocny;
    }

    public int getKoncovy() {
        return koncovy;
    }

    public int getCena() {
        return cena;
    }
}
