package src;

import java.util.ArrayList;

public class Rozgrywka {
    private Plansza planszaNasza;
    private UstawianieStatku ustawianieStatkuNasze;
    private StrzelanieDoStatku strzelanieDoStatkuNasze;
    private Przeciwnik przeciwnik;
    private boolean zgodnoscUstawien = false; //czy poprawnie ustawiono wszystkie statki
    private boolean koniecGry;


    public Plansza getPlanszaNasza() {
        return planszaNasza;
    }

    public UstawianieStatku getUstawianieStatkuNasze() {
        return ustawianieStatkuNasze;
    }

    public StrzelanieDoStatku getStrzelanieDoStatkuNasze() {
        return strzelanieDoStatkuNasze;
    }

    public Przeciwnik getPrzeciwnik() {
        return przeciwnik;
    }

    public boolean czyKoniecGry(){
        if(strzelanieDoStatkuNasze.getKoniec() || przeciwnik.getKoniecGry()){
            koniecGry = true;
            return true;
        }
        else{ return false;}
    }
    public boolean czyWygrana() { //metode wykonujemy dopiero po metodzie czyKoniecGry
        if(strzelanieDoStatkuNasze.getKoniec()) {
            return true;
        } else {
            return false;
        }
    }
    public Rozgrywka() {

        int[][] plansza1 = new int[10][10];
        int[][] plansza2 = new int[10][10];
        Plansza planszaNasza = new Plansza(plansza1);
        Plansza planszaPrzeciwnika = new Plansza(plansza2);
        ArrayList<Integer> iloscMasztowcow1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            iloscMasztowcow1.add(0);
        }
        ArrayList<Integer> iloscMasztowcow2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            iloscMasztowcow2.add(0);
        }
        UstawianieStatku ustawianieStatkuNasze = new UstawianieStatku(planszaNasza, iloscMasztowcow1);
        UstawianieStatku ustawianieStatkuPrzeciwnika = new UstawianieStatku(planszaPrzeciwnika, iloscMasztowcow2);
        StrzelanieDoStatku strzelanieDoStatkuNasze = new StrzelanieDoStatku(planszaPrzeciwnika);
        StrzelanieDoStatku strzelanieDoStatkuPrzeciwnika = new StrzelanieDoStatku(planszaNasza);
        Przeciwnik przeciwnik = new Przeciwnik(ustawianieStatkuPrzeciwnika, strzelanieDoStatkuPrzeciwnika, planszaNasza);
        this.planszaNasza = planszaNasza;
        this.ustawianieStatkuNasze = ustawianieStatkuNasze;
        this.strzelanieDoStatkuNasze = strzelanieDoStatkuNasze;
        this.przeciwnik = przeciwnik;
    }

    public boolean sprawdzenieprawdzUstawienia() { //sprawdza czy wszystkie statki sa poprawnie ustawione
        ustawianieStatkuNasze.sprawdzUstawienie();
        przeciwnik.getUstawianieStatku().sprawdzUstawienie();
        if(ustawianieStatkuNasze.getStanUtworzenia() && przeciwnik.getUstawianieStatku().getStanUtworzenia()) {
            zgodnoscUstawien = true;
        }
        return zgodnoscUstawien;
    }


    public boolean pokoloruj(ArrayList<Pozycja> wspolrzedne) { //zmiana koloru przyciskow przy ustawianiu statku
        boolean x = true;
        for(int i = 0; i < wspolrzedne.size(); i++){
            if(planszaNasza.getPlansza()[wspolrzedne.get(i).getWiersz()][wspolrzedne.get(i).getKolumna()] != 1) {
                x = false;
            }
        }
        return x;
    }
    public int pokoloruj( Pozycja tmpAtak ) {  //zmiana koloru przyciskow w zaleznosci od statusu atakowanej pozycji
        return getPrzeciwnik().getUstawianieStatku().getPlansza().getPlansza()[tmpAtak.getWiersz()][tmpAtak.getKolumna()];
    }

    public boolean blokujPrzycisk(int iloscMasztow, int docelowe) {
        boolean x = false;
        if(ustawianieStatkuNasze.getIloscMasztowcow().get(iloscMasztow - 2) == docelowe) {
            x = true;
        }
        return x;
    }
    
}