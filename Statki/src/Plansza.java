package src;

import java.util.ArrayList;
public class Plansza {
    private int[][] plansza;
    private Statek[][] planszaStatkow = new Statek[10][10];
    public Plansza(int[][] plansza) {   //konstruktor wypelniajacy dwuwymiarowa tablice 10x10 zerami
        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                plansza[i][j] = 0;
            }
        }
        this.plansza = plansza;
    }

    public void zmienStatus(Pozycja pozycja, int wartosc){      //zmiana wartosci danego pola
        //0-nie ma statku, 1-jest statek; 2-statek plonie; 3-statek splonal; 4-tu nie stawiac; 5-pudlo, albo nie strzelac
        plansza[pozycja.getWiersz()][pozycja.getKolumna()] = wartosc;
    }

    public int[][] getPlansza() {
        return plansza;
    }

    public Statek[][] getPlanszaStatkow() {
        return planszaStatkow;
    }

    public void zmienStatusStatkow(ArrayList<Pozycja> wspolrzedne, Statek statek) {     //zmiana statusu statku na planszy statków
        for (int i = 0; i < wspolrzedne.size(); i++) {
            planszaStatkow[wspolrzedne.get(i).getWiersz()][wspolrzedne.get(i).getKolumna()] = statek;
        }
    }

}
