package src;

import java.util.ArrayList;
public class Statek {
    private int iloscMasztow;
    private int stanZatopienia;  //0 nie istnieje, 1 istnieje nie plonie, 2 plonie, 3 splonal
    private ArrayList<Pozycja> wspolrzedne;  //lista wspolrzednych kratek na ktorych jest statek
    private  int iloscTrafien = 0;

    public void zwiekszIloscTrafien() {
        this.iloscTrafien = this.iloscTrafien + 1;
    }

    public void czyZatopiony(){
        if (iloscMasztow == iloscTrafien){
            splonal();
        }
    }

    public Statek(ArrayList<Pozycja> wspolrzedne) { //konstruktor pobiera liste wspolrzednych
        iloscMasztow = wspolrzedne.size();
        stanZatopienia = 1;
        this.wspolrzedne = wspolrzedne;
    }
    public int getIloscMasztow() {
        return iloscMasztow;
    }

    public int getStanZatopienia() {
        return stanZatopienia;
    }

    public int getIloscTrafien() { return iloscTrafien; }

    public ArrayList getWspolrzedne() {
        return wspolrzedne;
    }

    public void plonie() {
        stanZatopienia = 2;
    }
    public void splonal() {
        stanZatopienia = 3;
    }

}