package src;

import java.util.ArrayList;

public class UstawianieStatku {
    private Plansza plansza;
    private ArrayList<Integer> iloscMasztowcow;
    private boolean stanUtworzenia;
    private static ArrayList<Integer> pola = new ArrayList<>(); //lista mozliwych wartosci, czyli 0-9
    private ArrayList<Pozycja> wspolrzedneDoKolorowania = new ArrayList<>();

    public Plansza getPlansza() {
        return plansza;
    }

    public ArrayList<Pozycja> getWspolrzedneDoKolorowania() {
        return wspolrzedneDoKolorowania;
    }

    public ArrayList<Integer> getIloscMasztowcow() {
        return iloscMasztowcow;
    }

    public UstawianieStatku(Plansza plansza, ArrayList<Integer> iloscMasztowcow) {
        this.plansza = plansza;
        for (int i = 0; i < 10; i++) {
            pola.add(i);
        }
        this.iloscMasztowcow = iloscMasztowcow;
    }

    public boolean getStanUtworzenia() {
        return stanUtworzenia;
    }

    public ArrayList<Pozycja> generowanieWspolrzednych(Pozycja p, int ilosc_masztow, int x) { //i=0 - pionowo i=1 - poziomo
        ArrayList<Pozycja> wspolrzedne = new ArrayList<>();
        wspolrzedne.add(p);
        if((x == 0 && p.getWiersz() + ilosc_masztow <= 10) || (x == 1 && p.getKolumna() + ilosc_masztow <= 10) == true) {
            for (int i = 1; i < ilosc_masztow; i++) {
                if (x == 0) {
                    Pozycja p_new = new Pozycja(p.getWiersz() + i, p.getKolumna());
                    wspolrzedne.add(p_new);
                } else if (x == 1) {
                    Pozycja p_new = new Pozycja(p.getWiersz(), p.getKolumna() + i);
                    wspolrzedne.add(p_new);
                }
            }
        } else {
            wspolrzedne.remove(p);
        }
        return wspolrzedne;
    }

    public void blokuj(ArrayList<Pozycja> wspolrzedne) {  // metoda do blokowania kratek wokol statku
        wspolrzedneDoKolorowania.clear();
        for (int i = 0; i < (wspolrzedne.get(wspolrzedne.size() - 1).getWiersz() - wspolrzedne.get(0).getWiersz())+ 3; i++) {
            for (int j = 0; j < (wspolrzedne.get(wspolrzedne.size() - 1).getKolumna() - wspolrzedne.get(0).getKolumna()) + 3; j++) {
                Pozycja x = new Pozycja(wspolrzedne.get(wspolrzedne.size() - 1).getWiersz() + 1 - i, wspolrzedne.get(0).getKolumna() - 1 + j);
                if (pola.contains(x.getKolumna()) && pola.contains(x.getWiersz()) && (plansza.getPlansza()[x.getWiersz()][x.getKolumna()] == 0)) {
                    plansza.zmienStatus(x, 4);
                    wspolrzedneDoKolorowania.add(x);
                }
            }
        }
    }
    public void utworzMasztowiec(ArrayList<Pozycja> wspolrzedne) {  //tworzenie statkow i zmienianie planszy
        Statek statek = new Statek(wspolrzedne);                         //generuje za soba metode utworzMasztowiec
        for (int i = 0; i < wspolrzedne.size(); i++) {
            plansza.zmienStatus(wspolrzedne.get(i), 1);
        }
        plansza.zmienStatusStatkow(wspolrzedne, statek);
        blokuj(wspolrzedne);
        iloscMasztowcow.set(wspolrzedne.size() - 2, iloscMasztowcow.get(wspolrzedne.size() - 2) + 1);
    }

    public boolean sprawdzMasztowiec(ArrayList<Pozycja> wspolrzedne) { //sprawdza czy mozna w danym miejscu postawic statek
        boolean x = true;                                              //zwraca boolean czy udalo sie utworzyc jesli tak to
        if(wspolrzedne.size() != 0) {
            for (int i = 0; i < wspolrzedne.size(); i++) {                 //generuje za soba metody blokuj i utworzMastzowiec
                if (plansza.getPlansza()[wspolrzedne.get(i).getWiersz()][wspolrzedne.get(i).getKolumna()] != 0) {
                    x = false;
                }
            }
        } else {
            x = false;
        }
        if (x == true) {
            utworzMasztowiec(wspolrzedne);
        }
        sprawdzUstawienie();
        return x;
    }

    public void sprawdzUstawienie() {  //sprawdza czy na planszy jest odpowiednia ilosc masztowcow
        if (iloscMasztowcow.get(0) == 2 && iloscMasztowcow.get(1) == 2 && iloscMasztowcow.get(2) == 2  && iloscMasztowcow.get(3) == 1) {
            stanUtworzenia = true;
        }
    }

}