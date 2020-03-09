package src;

import java.util.ArrayList;

public class StrzelanieDoStatku {
    private Plansza plansza; //przeciwnika
    private int iloscZatopionych; //ile statkow zatopiono
    private boolean strzelono;  //czy strzal zostal oddany
    private boolean ruchgracza; //czy strzelanie ma byc kontynuowane
    private boolean zatopionoTmp = false;  //czy zatopiono statek aktualnie atakowany
    private static ArrayList<Integer> pola = new ArrayList<>();
    private boolean koniec = false; //czy ktos zakonczyl gre

    public boolean isZatopionoTmp() {
        return zatopionoTmp;
    }

    public void setRuchgracza(boolean ruchgracza) {
        this.ruchgracza = ruchgracza;
    }

    public void setZatopionoTmp(boolean zatopionoTmp) {
        this.zatopionoTmp = zatopionoTmp;
    }

    public StrzelanieDoStatku(Plansza plansza) {
        this.plansza = plansza;
        this.iloscZatopionych = 0;
        for (int i = 0; i < 10; i++) {
            pola.add(i);
        }
    }

    public int getIloscZatopionych() {
        return iloscZatopionych;
    }

    public boolean getruchgracza() {
        return ruchgracza;
    }

    public Plansza getPlansza() {
        return plansza;
    }

    public boolean getKoniec() {
        return koniec;
    }

    public boolean atakuj(Pozycja pozycja){    //kontroluje czyj jest ruch i wykonuje atak

        int tmp = 0;
        if(iloscZatopionych < 7) {   //sprawdza czy gra nie jest zakonczona
            tmp = sprawdzWspolrzedna(pozycja);
        }
        else{
            koniec = true;
        }
        if (tmp == 1 || tmp == 3) {ruchgracza = true; }
        else{ ruchgracza = false; }
        return  ruchgracza;
    }

    public void blokuj(ArrayList<Pozycja> wspolrzedne) {  // metoda do blokowania kratek wokol statku
        for (int i = 0; i < (wspolrzedne.get(wspolrzedne.size() - 1).getWiersz() - wspolrzedne.get(0).getWiersz())+ 3; i++) {
            for (int j = 0; j < (wspolrzedne.get(wspolrzedne.size() - 1).getKolumna() - wspolrzedne.get(0).getKolumna()) + 3; j++) {
                Pozycja x = new Pozycja(wspolrzedne.get(wspolrzedne.size() - 1).getWiersz() + 1 - i, wspolrzedne.get(0).getKolumna() - 1 + j);
                if (pola.contains(x.getKolumna()) && pola.contains(x.getWiersz()) && (plansza.getPlansza()[x.getWiersz()][x.getKolumna()] == 4)) {
                    plansza.zmienStatus(x, 5);
                }
            }
        }
    }

    public int sprawdzWspolrzedna(Pozycja pozycja){    //sprawdza jaki jest status pozycji, ktora jest atakowana i uaktualnia po ataku. zwraca wartosc przed atakiem
        int tmp =  plansza.getPlansza()[pozycja.getWiersz()][pozycja.getKolumna()];        //5 pudlo
        strzelono = false;
        if (tmp==1){           //jesli jest tam statek, to zmieniamy na trafiony
            plansza.zmienStatus(pozycja, 2 );
            Statek tmpstatek = plansza.getPlanszaStatkow()[pozycja.getWiersz()][pozycja.getKolumna()]; //pomocnicza zmienna typu Statek
            tmpstatek.plonie(); //zmiana statusu statku na płonacy
            tmpstatek.zwiekszIloscTrafien();
            tmpstatek.czyZatopiony(); //zmiana statusu na zatopiony
            strzelono = true;
            if(tmpstatek.getStanZatopienia() == 3) {  //jezeli udalo nam sie zatopic statek to wykonujemy kolejno
                iloscZatopionych += 1;
                zatopionoTmp = true;
                blokuj(tmpstatek.getWspolrzedne());  //wokol statki blokowanie wspolrzednych, czyli zmiana na planszy na 5
                for(int i = 0; i < tmpstatek.getIloscMasztow(); i++) {
                    plansza.zmienStatus((Pozycja) tmpstatek.getWspolrzedne().get(i), 3); //zmiana na planszy tam gdzie byl statek na 3
                }
            }
            strzelono = true;
        }
        else if(tmp==4||tmp==0){     //jesli brak statku to pudlo - 5
            plansza.zmienStatus(pozycja, 5);
            strzelono = true;
        }
        if(iloscZatopionych ==7){
            koniec = true;
        }
        return tmp;
    }

}