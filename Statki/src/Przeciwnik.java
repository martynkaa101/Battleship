package src;

import java.util.*;
import java.util.List;

public class Przeciwnik {
    private UstawianieStatku ustawianieStatku;
    private StrzelanieDoStatku strzelanieDoStatku;
    private boolean oddanieStrzalu;
    private Plansza plansza; //gracza
    private ArrayList<Pozycja> aktualnieAtakowane = new ArrayList<>();  //Lista wspolrzednych trafionych w aktualnie atakowanym statku
    private boolean koniecGry = false;  //status zakonczenia gry
    private boolean ruchPrzeciwnika = true;  //czy to ruch przeciwnika
    private Pozycja ostatniStrzal;

    public Przeciwnik(UstawianieStatku ustawianieStatku, StrzelanieDoStatku strzelanieDoStatku, Plansza plansza) {
        this.ustawianieStatku = ustawianieStatku;
        this.strzelanieDoStatku = strzelanieDoStatku;
        this.plansza = plansza;
    }

    public boolean getKoniecGry() {
        return koniecGry;
    }

    public boolean getRuchPrzeciwnika() {
        return ruchPrzeciwnika;
    }

    public UstawianieStatku getUstawianieStatku() {
        return ustawianieStatku;
    }

    public StrzelanieDoStatku getStrzelanieDoStatku() {
        return strzelanieDoStatku;
    }

    public Pozycja getOstatniStrzal() {
        return ostatniStrzal;
    }

    public Pozycja losowaniePozycji(){
        Random losowanie = new Random();
        List <Integer> lista = new ArrayList<>(2);
        for (int i = 0; i < 2; i++){
            lista.add(losowanie.nextInt(10));
        }
        Pozycja p = new Pozycja(lista.get(0), lista.get(1));
        return p;
    }

    public void sprawdzanieZgodnosci(int iloscMasztow) {  //w metodzie losuja sie wspolrzedne dopoki nie znajdzie sie miejsce gdzie moga byc statki
        boolean x = false;
        while(x == false) {
            Random losowanie = new Random();
            int y = losowanie.nextInt(2);
            x = ustawianieStatku.sprawdzMasztowiec(ustawianieStatku.generowanieWspolrzednych(losowaniePozycji(), iloscMasztow, y));
        }
    }

    public void sprawdzanieStrzalu(Pozycja pozycja) {       //sprawdza czy strzal zostal oddany
        this.oddanieStrzalu = false;
        int x = strzelanieDoStatku.getPlansza().getPlansza()[pozycja.getWiersz()][pozycja.getKolumna()];
        if(x == 0 || x == 1 || x == 4) {
            this.oddanieStrzalu = true;
        }
    }


    public boolean czyNaPlanszy(Pozycja strzal) {       //sprawdza czy wylosowana pozycja na pewno znajduje sie w granicach planszy (przy strzelaniu po trafieniu)
        if (strzal.getWiersz() < 10 && strzal.getWiersz() >= 0 && strzal.getKolumna() <10 && strzal.getKolumna() >=0 ){
            return true;
        }
        else {
            return false;
        }
    }

    public void sortowanie(ArrayList<Pozycja> aktualnieAtakowane, int ulozenie){
        switch (ulozenie){
            case 1: //sortowanie po kolumnach, ulozenie poziome
                Collections.sort(aktualnieAtakowane, Pozycja.sortowanieKolumnami);
                break;
            case 2:  //sortowanie po wierszach, ulozenie pionowe
                Collections.sort(aktualnieAtakowane, Pozycja.sortowanieWierszami);
                break;
        }
    }

    //zmuszenie komputera do kontynuowania ataku jednego, danego statku
    public Pozycja strzelaniePoTrafieniu(Pozycja strzal, Statek statek, ArrayList<Pozycja> aktualnieAtakowane ){
        if (statek.getIloscTrafien() == 1) {    //jesli statek zostal trafiony po raz pierwszy - ma 4 mozliwosci ataku(jesli tyle w granicach planszy)
            ArrayList<Pozycja> mozliwosciAtaku = new ArrayList<>();
            int i = 0;
            if(czyNaPlanszy(new Pozycja(strzal.getWiersz(), strzal.getKolumna() - 1))){
                if(plansza.getPlansza()[strzal.getWiersz()][strzal.getKolumna() - 1] != 5) {
                    mozliwosciAtaku.add(new Pozycja(strzal.getWiersz(), strzal.getKolumna() - 1));
                    i++;
                }
            }
            if (czyNaPlanszy(new Pozycja(strzal.getWiersz(), strzal.getKolumna() + 1))) {
                if(plansza.getPlansza()[strzal.getWiersz()][strzal.getKolumna() + 1] != 5) {
                    mozliwosciAtaku.add(new Pozycja(strzal.getWiersz(), strzal.getKolumna() + 1));
                    i++;
                }
            }
            if(czyNaPlanszy(new Pozycja(strzal.getWiersz() + 1, strzal.getKolumna()))) {
                if(plansza.getPlansza()[strzal.getWiersz() + 1][strzal.getKolumna()] != 5) {
                    mozliwosciAtaku.add(new Pozycja(strzal.getWiersz() + 1, strzal.getKolumna()));
                    i++;
                }
            }
            if(czyNaPlanszy(new Pozycja(strzal.getWiersz() - 1, strzal.getKolumna()))) {
                if(plansza.getPlansza()[strzal.getWiersz() - 1][strzal.getKolumna()] != 5) {
                    mozliwosciAtaku.add(new Pozycja(strzal.getWiersz() - 1, strzal.getKolumna()));
                    i++;
                }
            }
            //losowanie pozycji sposrod mozliwosci
            Random losowanie = new Random();
            int los;
            if(mozliwosciAtaku.size() != 1) {
                los = losowanie.nextInt(mozliwosciAtaku.size() - 1);
            } else {
                los = 0;
            }
            Pozycja strzal2 = mozliwosciAtaku.get(los);
            return  strzal2;
        }
        else{   //jesli statek ma co najmniej dwa pola trafione, ma dwie mozliwosci ataku(jesli tyle na planszy)
            Pozycja strzal2;
            if (aktualnieAtakowane.get(0).getWiersz() == aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getWiersz()){  //ulozenie poziome
                ArrayList<Pozycja> mozliwosciAtaku = new ArrayList<>();
                int i = 0;
                sortowanie(aktualnieAtakowane, 1);
                if(czyNaPlanszy(new Pozycja(aktualnieAtakowane.get(0).getWiersz(), aktualnieAtakowane.get(0).getKolumna()-1)) ) {
                    if(plansza.getPlansza()[aktualnieAtakowane.get(0).getWiersz()][aktualnieAtakowane.get(0).getKolumna()-1] != 5) {
                        mozliwosciAtaku.add(new Pozycja(aktualnieAtakowane.get(0).getWiersz(), aktualnieAtakowane.get(0).getKolumna() - 1));
                        i++;
                    }
                }
                if(czyNaPlanszy(new Pozycja(aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getWiersz(), aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getKolumna()+1))) {
                    if(plansza.getPlansza()[aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getWiersz()][aktualnieAtakowane.get(0).getKolumna()+1] != 5) {
                        mozliwosciAtaku.add(new Pozycja(aktualnieAtakowane.get(aktualnieAtakowane.size() - 1).getWiersz(), aktualnieAtakowane.get(aktualnieAtakowane.size() - 1).getKolumna() + 1));
                        i++;
                    }
                }
                Random losowanie = new Random();
                if(mozliwosciAtaku.size()!= 1) {
                    strzal2 = mozliwosciAtaku.get(losowanie.nextInt(mozliwosciAtaku.size() - 1));
                } else {
                    strzal2 = mozliwosciAtaku.get(0);
                }
                return  strzal2;
            }
            else{ //ulozenie pionowe
                ArrayList<Pozycja> mozliwosciAtaku = new ArrayList<>();
                int i = 0;
                sortowanie(aktualnieAtakowane, 2);
                if(czyNaPlanszy(new Pozycja(aktualnieAtakowane.get(0).getWiersz() - 1, aktualnieAtakowane.get(0).getKolumna()))) {
                    if(plansza.getPlansza()[aktualnieAtakowane.get(0).getWiersz() - 1][aktualnieAtakowane.get(0).getKolumna()] != 5) {
                        mozliwosciAtaku.add(new Pozycja(aktualnieAtakowane.get(0).getWiersz() - 1, aktualnieAtakowane.get(0).getKolumna()));
                        i++;
                    }
                }
                if(czyNaPlanszy(new Pozycja(aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getWiersz()+1, aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getKolumna()))) {
                    if(plansza.getPlansza()[aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getWiersz()+1][aktualnieAtakowane.get(aktualnieAtakowane.size()-1).getKolumna()] != 5) {
                        mozliwosciAtaku.add(new Pozycja(aktualnieAtakowane.get(aktualnieAtakowane.size() - 1).getWiersz() + 1, aktualnieAtakowane.get(aktualnieAtakowane.size() - 1).getKolumna()));
                        i++;
                    }
                }
                Random losowanie = new Random();
                if(mozliwosciAtaku.size()!= 1) {
                    strzal2 = mozliwosciAtaku.get(losowanie.nextInt(mozliwosciAtaku.size() - 1));
                } else {
                    strzal2 = mozliwosciAtaku.get(0);
                }
                return  strzal2;
            }
        }
    }

    public int atakPrzeciwnika() throws InterruptedException {       //5 - pudlo, 1-trafiony 2-zatopiony, 0 -blad
        Thread.sleep(500);
        int x;
        if(aktualnieAtakowane.isEmpty()) {  //jesli aktualnie zaden statek nie jest atakowany
            ruchPrzeciwnika = false;
            Pozycja atak1;          //pierwszy dowolny strzal
            do {
                atak1 = losowaniePozycji();
                sprawdzanieStrzalu(atak1);
            } while (!this.oddanieStrzalu);  //upewnienie sie, ze mozliwe jest atakowanie tej pozycji
            ostatniStrzal = atak1;
            strzelanieDoStatku.atakuj(atak1); //oddanie strzalu
            if (strzelanieDoStatku.getruchgracza()) { //jesli trafiono to dodaj statek do aktualnie pod atakiem
                aktualnieAtakowane.add(atak1);
                ruchPrzeciwnika = true;
                x = 1;
            }
            else {
                x = 5;
            }
        }

        else{   //jesli kontynuujemy atak jakiegos statku
            ruchPrzeciwnika = false;
            Pozycja atak = null;
            oddanieStrzalu = false;
            do {
                for (int i = 0; i < aktualnieAtakowane.size(); i++) {
                    Pozycja atak1 = aktualnieAtakowane.get(i);
                    atak = strzelaniePoTrafieniu(atak1, plansza.getPlanszaStatkow()[atak1.getWiersz()][atak1.getKolumna()], aktualnieAtakowane);
                    sprawdzanieStrzalu(atak);
                    if (oddanieStrzalu) {
                        break;
                    }
                }
            }while(!oddanieStrzalu);    //do skutku szuka mozliwej pozycji do ataku
            ostatniStrzal = atak;
            strzelanieDoStatku.atakuj(atak);

            if (strzelanieDoStatku.getruchgracza()) { //jesli trafiono to dodaj pozycje do aktualnie pod atakiem
                Thread.sleep(500);
                aktualnieAtakowane.add(atak);
                ruchPrzeciwnika = true;
                x = 1;
                if(strzelanieDoStatku.isZatopionoTmp()){ //jesli zatopiono to ma obrac nowy cel
                    aktualnieAtakowane.clear();
                    strzelanieDoStatku.setZatopionoTmp(false);
                    x = 2;
                }
                if (strzelanieDoStatku.getIloscZatopionych() == 7){ koniecGry = true;} //jesli zatopi wszystkie to status konca gry na true
            }
            else{
                Thread.sleep(500);
                ruchPrzeciwnika = false;
                x = 5;
            }
        }
        return x;
    }
}