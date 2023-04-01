package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Graf {
    private static final int NEKONECNO = Integer.MAX_VALUE/2;
    private int pocetVrcholov;
    private int pocetHran;
    private Hrana[] zoznamHran;
    private Vrchol[] zoznamVrcholov;

    public Graf(String nazovSuboru) throws FileNotFoundException {
        this.nacitajSubor(nazovSuboru);
    }

    // Kód na načitávanie súboru je zo stránky: https://frcatel.fri.uniza.sk/users/paluch/Grafy/Priklad1/Graf.html
    public void nacitajSubor(String nazovSuboru) throws FileNotFoundException {
        // otvorim subor a pripravim Scanner pre nacitavanie
        Scanner s = new Scanner(new FileInputStream(nazovSuboru));

        // najskor len zistim pocet vrcholov a pocet hran
        int vrcholyPocet = 1;
        int hranyPocet = 0;
        // prejdem cely subor
        while (s.hasNext()) {
            // nacitam udaje o hrane
            int hranaZaciatocny = s.nextInt();
            int hranaKoncovy = s.nextInt();
            int hranaCena = s.nextInt();

            // nacital som hranu, zvysim ich pocet o 1
            hranyPocet++;

            // skontrolujem, ci netreba zvysit pocet vrcholov
            if (vrcholyPocet < hranaZaciatocny) vrcholyPocet = hranaZaciatocny;
            if (vrcholyPocet < hranaKoncovy) vrcholyPocet = hranaKoncovy;
        }
        // ukoncim nacitavanie zo suboru
        s.close();

        // vytvorim objekt grafu s potrebnym poctom vrcholo v aj hran
        this.pocetVrcholov = vrcholyPocet;
        this.pocetHran = hranyPocet;
        this.zoznamHran = new Hrana[pocetHran+1];

        // po druhy krat otvorim ten isty subor,
        // uz pozanm pocet vrcholov aj hran a mam alokovanu pamat
        s = new Scanner(new FileInputStream(nazovSuboru));

        // postune nacitam vsetky hrany
        // tentokrat si ich uz budem aj ukladat do pamate
        for (int j = 1; j <= hranyPocet; j++) {
            int hZaciatocny = s.nextInt();
            int hKoncovy = s.nextInt();
            int hCena = s.nextInt();

            this.zoznamHran[j] = new Hrana(hZaciatocny, hKoncovy, hCena);
        }
    }

    public void printInfo() {
        System.out.println("Pocet vrcholov: " + pocetVrcholov);
        System.out.println("Pocet hran: " + pocetHran);
    }




    public void algoritmus() {

        // vstup od uživateľa - začiatočný vrchol a koncový vrchol
        Scanner userInput = new Scanner(System.in);
        System.out.println("od ktoreho vrcholu chces zacat? (vloz cislo) : ");
        int userZaciatocny = userInput.nextInt();
        System.out.println("do ktoreho vrcholu? : ");
        int userKoncovy = userInput.nextInt();

        // inicializácia premenných
        this.zoznamVrcholov = new Vrchol[pocetVrcholov+1];
        HashSet<Integer> mnozinaE = new HashSet<>();

        // začiatočny vrchol pridam do mnoziny na kontrolu
        mnozinaE.add(userZaciatocny);

        // vytvorím nové vrcholy a všetkým nastavím cenu na NEKONECNO (okrem začiatočného) a preposledný vrchol na 0
        for (int i = 1; i <= pocetVrcholov; i++) {
            this.zoznamVrcholov[i] = new Vrchol(i);
            if (i == userZaciatocny) {
                this.zoznamVrcholov[i].setCena(0);
            } else {
                this.zoznamVrcholov[i].setCena(NEKONECNO);
            }
            this.zoznamVrcholov[i].setPredposlednyVrchol(0);
        }


        int najlepsiVrchol = 0;
        while (!mnozinaE.isEmpty()) {

            // hľadanie vrchola s najmenšou cenou cesty v množine E
            int minCena = NEKONECNO;
            for (Integer i : mnozinaE) {
                if (this.zoznamVrcholov[i].getCena() < minCena) {
                    najlepsiVrchol = i;
                    minCena = this.zoznamVrcholov[i].getCena();
                }
            }

            // nájdený vrchol odstránim z množiny a nastavím kontrolu na true
            mnozinaE.remove(najlepsiVrchol);
            this.zoznamVrcholov[najlepsiVrchol].setKontrola(true);

            // kontrola či som sa dostal do koncového vrcholu
            if (najlepsiVrchol == userKoncovy) {
                break;
            }

            // cyklenie cez hrany
            for (int j = 1; j <= pocetHran; j++) {

                // riadiaci vrchol sa musí rovnať začiatočnému vrcholu hrany
                if (najlepsiVrchol == this.zoznamHran[j].getZaciatocny()) {

                    // vytvorenie lokálnych premenných, kvôli ľahšiemu čitaniu následujúceho vetvenia
                    Vrchol zaciatocnyVHrane = this.zoznamVrcholov[this.zoznamHran[j].getZaciatocny()];
                    Vrchol koncovyVHrane = this.zoznamVrcholov[this.zoznamHran[j].getKoncovy()];
                    int cenaHrany = this.zoznamHran[j].getCena();

                    // kontrola, či sa cena zlepšila
                    if (zaciatocnyVHrane.getCena() + cenaHrany < koncovyVHrane.getCena()) {

                        // nastavenie lepšej ceny koncovému vrcholu
                        koncovyVHrane.setCena(zaciatocnyVHrane.getCena() + cenaHrany);

                        // nastavím cestu späť poslednému vrcholu v hrane
                        koncovyVHrane.setPredposlednyVrchol(this.zoznamHran[j].getZaciatocny());

                        // ak koncový vrchol v hrane ešte nie je množineE, pridaj
                        if (!koncovyVHrane.isKontrola()) {
                            mnozinaE.add(this.zoznamHran[j].getKoncovy());
                        }
                    }
                }
            }
        }

        // výpis cesty pomocou ArrayListu a StringBuildera
        System.out.println("Cena cesty: " + this.zoznamVrcholov[userKoncovy].getCena());
        System.out.println("Do koncového vrchola sa dostaneš cestou: ");

        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> vypisVrcholov = new ArrayList<Integer>();
        vypisVrcholov.add(userKoncovy);

        int pomocny = userKoncovy;
        while(this.zoznamVrcholov[pomocny].getPredposlednyVrchol() != 0) {
            pomocny = this.zoznamVrcholov[pomocny].getPredposlednyVrchol();
            vypisVrcholov.add(pomocny);
        }

        Collections.reverse(vypisVrcholov);
        for (Integer vrchol : vypisVrcholov) {
            sb.append(vrchol+"->");
        } sb.setLength(sb.length() - 2);
        System.out.println(sb);

    }

}
