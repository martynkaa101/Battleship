package src;

import java.util.Comparator;
public class Pozycja {
    private int wiersz;
    private int kolumna;

    public Pozycja(int wiersz, int kolumna) {
        this.wiersz = wiersz;
        this.kolumna = kolumna;
    }

    public int getWiersz() {
        return wiersz;
    }

    public int getKolumna() {
        return kolumna;
    }

    //sortowanie pozycji rosnąco względem wartości wierszy
    public static Comparator<Pozycja> sortowanieWierszami = new Comparator<Pozycja>() {
         public int compare(Pozycja p1, Pozycja p2){
             int w1 = p1.getWiersz();
             int w2 = p2.getWiersz();
             return w1-w2;

    }};

    //sortowanie pozycji rosnąco względem wartości kolumn
    public static Comparator<Pozycja> sortowanieKolumnami = new Comparator<Pozycja>() {
        public int compare(Pozycja p1, Pozycja p2){
            int k1 = p1.getKolumna();
            int k2 = p2.getKolumna();
            return k1-k2;
        }};




}
