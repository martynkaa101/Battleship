package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.plaf.metal.*;


public class RozgrywkaGUI extends JFrame {

    private JFrame f;
    private JPanel pKoniecGry;
    private JPanel pGracza;
    private JPanel pPrzyciskowGracza;
    private JPanel pPrzeciwnika;
    private JPanel pPrzyciskowPrzeciwnika;
    private JPanel pSrodek;
    private JPanel pGra;
    private JPanel pnGracz;
    private JPanel pnPrzeciwnik;
    private JPanel srodek1;
    private JPanel srodek2;
    private JPanel srodek3;
    private JPanel srodek4;
    private JPanel srodekStrzelanie;
    private JLabel lGracz;
    private JLabel lPrzeciwnik;
    private JLabel lGra;
    private JTextArea wskazowki;
    private JTextArea legenda;
    private JPanel komunikaty;
    private JTextArea pozostaleStatki;
    private JButton dwuMasztowiec;
    private JButton trojMasztowiec;
    private JButton czteroMasztowiec;
    private JButton piecioMasztowiec;
    private JButton pion;
    private JButton poziom;
    private JButton potwierdzenieUstawienia;
    public static JButton przyciskGracza[][] = new JButton[10][10];
    public static JButton przyciskPrzeciwnika[][] = new JButton[10][10];
    private int gridSize;
    private Rozgrywka rozgrywka;
    private JTextArea komunikat;
    private JTextArea ruchy;
    private Pozycja tmpPozycja;
    private int tmpUlozenie;
    private Pozycja tmpAtak;
    private JTextArea koniecGryZwyciestwo;
    private JTextArea koniecGryPrzegrana;
    private JTextArea twojRuch;
    ActionListener actionListener;
    private Dzwiek trafiony;
    private Dzwiek zatopiony;
    private Dzwiek pudlo;
    public RozgrywkaGUI(Rozgrywka rozgrywka){
        trafiony = new Dzwiek("./src/trafiony.wav");
        zatopiony = new Dzwiek("./src/zatopiony.wav");
        pudlo = new Dzwiek("./src/pudlo.wav");
        this.rozgrywka = rozgrywka;
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch(Exception e){}

        Thread gra = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!rozgrywka.czyKoniecGry()) {
                    rozgrywka.getStrzelanieDoStatkuNasze().setRuchgracza(true);
                    do {
                        for(int i = 0; i < 10; i++){
                            for(int j = 0; j < 10; j++) {
                                przyciskPrzeciwnika[i][j].setEnabled(true);
                            }
                        }
                        ruchy.setText("Twoj Ruch");
                        pozostaleStatki.setText("Pozostalo ci do strzelenia: " + (7 - rozgrywka.getStrzelanieDoStatkuNasze().getIloscZatopionych()) + " statkow, a przeciwnikowi: " + (7 - rozgrywka.getPrzeciwnik().getStrzelanieDoStatku().getIloscZatopionych()));
                        if(rozgrywka.getStrzelanieDoStatkuNasze().getKoniec()){
                            pozostaleStatki.setText("Pozostalo ci do strzelenia: " + (7 - rozgrywka.getStrzelanieDoStatkuNasze().getIloscZatopionych()) + " statkow, a przeciwnikowi: " + (7 - rozgrywka.getPrzeciwnik().getStrzelanieDoStatku().getIloscZatopionych()));
                            break;
                        }
                    } while (rozgrywka.getStrzelanieDoStatkuNasze().getruchgracza());
                    pozostaleStatki.setText("Pozostalo ci do strzelenia: " + (7 - rozgrywka.getStrzelanieDoStatkuNasze().getIloscZatopionych()) + " statkow, a przeciwnikowi: " + (7 - rozgrywka.getPrzeciwnik().getStrzelanieDoStatku().getIloscZatopionych()));
                    for(int i = 0; i < 10; i++){
                        for(int j = 0; j < 10; j++) {
                            przyciskPrzeciwnika[i][j].setEnabled(false);
                        }
                    }
                    ruchy.setText("Ruch Przeciwnika");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    do {
                        if(rozgrywka.czyKoniecGry()){
                            break;
                        }
                        try {
                            int x = rozgrywka.getPrzeciwnik().atakPrzeciwnika();
                            switch (x){
                                case 0: //blad, zignoruj
                                    break;
                                case 1: //trafiony
                                    trafiony.zagraj();  //efekt dzwiekowy przy trafieniu
                                    przyciskGracza[rozgrywka.getPrzeciwnik().getOstatniStrzal().getWiersz()][rozgrywka.getPrzeciwnik().getOstatniStrzal().getKolumna()].setBackground(Color.black);
                                    przyciskGracza[rozgrywka.getPrzeciwnik().getOstatniStrzal().getWiersz()][rozgrywka.getPrzeciwnik().getOstatniStrzal().getKolumna()].setOpaque(true);
                                    break;
                                case 2: //zatopiony
                                    zatopiony.zagraj();
                                    przyciskGracza[rozgrywka.getPrzeciwnik().getOstatniStrzal().getWiersz()][rozgrywka.getPrzeciwnik().getOstatniStrzal().getKolumna()].setBackground(Color.black);
                                    przyciskGracza[rozgrywka.getPrzeciwnik().getOstatniStrzal().getWiersz()][rozgrywka.getPrzeciwnik().getOstatniStrzal().getKolumna()].setOpaque(true);
                                    break;
                                case 5: //pudlo
                                    pudlo.zagraj();
                                    przyciskGracza[rozgrywka.getPrzeciwnik().getOstatniStrzal().getWiersz()][rozgrywka.getPrzeciwnik().getOstatniStrzal().getKolumna()].setBackground(Color.WHITE);
                                    przyciskGracza[rozgrywka.getPrzeciwnik().getOstatniStrzal().getWiersz()][rozgrywka.getPrzeciwnik().getOstatniStrzal().getKolumna()].setOpaque(true);
                                    break;
                            }
                            pozostaleStatki.setText("Pozostalo ci do strzelenia: " + (7 - rozgrywka.getStrzelanieDoStatkuNasze().getIloscZatopionych()) + " statkow, a przeciwnikowi: " + (7 - rozgrywka.getPrzeciwnik().getStrzelanieDoStatku().getIloscZatopionych()));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (rozgrywka.getPrzeciwnik().getRuchPrzeciwnika());

                }
                pGra.setVisible(false);
                pnGracz.setVisible(false);
                pnPrzeciwnik.setVisible(false);
                srodekStrzelanie.setVisible(false);
                pGracza.setVisible(false);
                pPrzeciwnika.setVisible(false);
                pKoniecGry.setVisible(true);
                if(rozgrywka.czyWygrana()) {
                    pKoniecGry.add(koniecGryZwyciestwo);
                } else {
                    pKoniecGry.add(koniecGryPrzegrana);
                }
            }
        });


        gridSize = 10;
        f = new JFrame("Bitwa Morska");

        f.setSize(1040, 590);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        pGracza = new JPanel();
        pPrzeciwnika = new JPanel();
        pSrodek = new JPanel();
        pGra = new JPanel();
        pnGracz = new JPanel();
        pnPrzeciwnik = new JPanel();
        srodek1 = new JPanel();
        srodek2 = new JPanel();
        srodek3 = new JPanel();
        srodek4 = new JPanel();
        srodekStrzelanie = new JPanel();
        pKoniecGry = new JPanel();
        pKoniecGry.setLayout(new GridBagLayout());
        pKoniecGry.setSize(1040,570);
        pKoniecGry.setLocation(0,0);
        pKoniecGry.setBackground(Color.WHITE);
        pKoniecGry.setVisible(false);
        pGra.setSize(1080,100);
        pGra.setLocation(0,0);
        pnGracz.setLocation(0,100);
        pnGracz.setSize(420,50);
        pnPrzeciwnik.setLocation(620,100);
        pnPrzeciwnik.setSize(420,50);
        pSrodek.setSize(200,470);
        pSrodek.setLocation(420,100);
        pSrodek.setLayout(new FlowLayout(FlowLayout.CENTER));
        srodekStrzelanie.setSize(200,470);
        srodekStrzelanie.setLocation(420,100);
        srodekStrzelanie.setLayout(new FlowLayout(FlowLayout.CENTER));
        srodekStrzelanie.setVisible(false);
        pGracza.setSize(new Dimension(420,420));
        pGracza.setLocation(0,150);
        pGracza.setLayout(new FlowLayout(FlowLayout.LEFT));
        srodek1.setSize(new Dimension(200,100));
        srodek2.setSize(new Dimension(200,100));
        srodek3.setSize(new Dimension(200,230));
        pPrzeciwnika.setSize(new Dimension(420,420));
        pPrzeciwnika.setLocation(620,150);
        pPrzeciwnika.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pPrzyciskowGracza = new JPanel();
        pPrzyciskowGracza.setSize(new Dimension(420,420));
        pPrzyciskowGracza.setLocation(620,150);
        pPrzyciskowGracza.setLayout(new GridLayout(10, 10));

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                przyciskGracza[i][j] = new JButton();
                przyciskGracza[i][j].setBackground(Color.GRAY);
                przyciskGracza[i][j].setPreferredSize(new Dimension(40, 40));
                setLayout(new FlowLayout());

                int finalI = i;
                int finalJ = j;
                przyciskGracza[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tmpPozycja = new Pozycja(finalI, finalJ);
                    }
                });
                pPrzyciskowGracza.add(przyciskGracza[i][j]);
            }
        }

        pion = new JButton("Pionowo");
        pion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tmpUlozenie = 0;
            }
        });

        poziom = new JButton("Poziomo");
        poziom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tmpUlozenie = 1;
            }
        });

        dwuMasztowiec = new JButton("Dwumasztowiec");
        dwuMasztowiec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pozycja> tmpwspolrzedne = rozgrywka.getUstawianieStatkuNasze().generowanieWspolrzednych(tmpPozycja,2,tmpUlozenie);
                rozgrywka.getUstawianieStatkuNasze().sprawdzMasztowiec(tmpwspolrzedne);
                for(int i = 0; i < rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().size(); i++) {
                    Pozycja x = rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().get(i);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setBackground(Color.RED);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setOpaque(true);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setEnabled(false);
                }
                if(rozgrywka.pokoloruj(tmpwspolrzedne)) {
                    for(int i = 0; i < tmpwspolrzedne.size(); i++) {
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setBackground(Color.BLUE);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setOpaque(true);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setEnabled(false);
                    }
                }
                if(rozgrywka.blokujPrzycisk(2, 2)) {
                    dwuMasztowiec.setEnabled(false);
                }
                if(rozgrywka.getUstawianieStatkuNasze().getStanUtworzenia()) {
                    potwierdzenieUstawienia.setEnabled(true);
                }
            }
        });

        trojMasztowiec = new JButton("Trojmasztowiec");
        trojMasztowiec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pozycja> tmpwspolrzedne = rozgrywka.getUstawianieStatkuNasze().generowanieWspolrzednych(tmpPozycja,3,tmpUlozenie);
                rozgrywka.getUstawianieStatkuNasze().sprawdzMasztowiec(tmpwspolrzedne);
                for(int i = 0; i < rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().size(); i++) {
                    Pozycja x = rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().get(i);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setBackground(Color.RED);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setOpaque(true);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setEnabled(false);
                }
                if(rozgrywka.pokoloruj(tmpwspolrzedne)) {
                    for(int i = 0; i < tmpwspolrzedne.size(); i++) {
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setBackground(Color.BLUE);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setOpaque(true);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setEnabled(false);
                    }
                }
                if(rozgrywka.blokujPrzycisk(3, 2)) {
                    trojMasztowiec.setEnabled(false);
                }
                if(rozgrywka.getUstawianieStatkuNasze().getStanUtworzenia()) {
                    potwierdzenieUstawienia.setEnabled(true);
                }
            }
        });

        czteroMasztowiec = new JButton("Czteromasztowiec");
        czteroMasztowiec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pozycja> tmpwspolrzedne = rozgrywka.getUstawianieStatkuNasze().generowanieWspolrzednych(tmpPozycja,4,tmpUlozenie);
                rozgrywka.getUstawianieStatkuNasze().sprawdzMasztowiec(tmpwspolrzedne);
                for(int i = 0; i < rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().size(); i++) {
                    Pozycja x = rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().get(i);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setBackground(Color.RED);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setOpaque(true);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setEnabled(false);
                }
                if(rozgrywka.pokoloruj(tmpwspolrzedne)) {
                    for(int i = 0; i < tmpwspolrzedne.size(); i++) {
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setBackground(Color.BLUE);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setOpaque(true);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setEnabled(false);
                    }
                }
                if(rozgrywka.blokujPrzycisk(4, 2)) {
                    czteroMasztowiec.setEnabled(false);
                }
                if(rozgrywka.getUstawianieStatkuNasze().getStanUtworzenia()) {
                    potwierdzenieUstawienia.setEnabled(true);
                }
            }
        });

        piecioMasztowiec = new JButton("Pieciomasztowiec");
        piecioMasztowiec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pozycja> tmpwspolrzedne = rozgrywka.getUstawianieStatkuNasze().generowanieWspolrzednych(tmpPozycja,5,tmpUlozenie);
                rozgrywka.getUstawianieStatkuNasze().sprawdzMasztowiec(tmpwspolrzedne);
                for(int i = 0; i < rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().size(); i++) {
                    Pozycja x = rozgrywka.getUstawianieStatkuNasze().getWspolrzedneDoKolorowania().get(i);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setBackground(Color.RED);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setOpaque(true);
                    przyciskGracza[x.getWiersz()][x.getKolumna()].setEnabled(false);
                }
                if(rozgrywka.pokoloruj(tmpwspolrzedne)) {
                    for(int i = 0; i < tmpwspolrzedne.size(); i++) {
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setBackground(Color.BLUE);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setOpaque(true);
                        przyciskGracza[tmpwspolrzedne.get(i).getWiersz()][tmpwspolrzedne.get(i).getKolumna()].setEnabled(false);
                    }
                }
                if(rozgrywka.blokujPrzycisk(5, 1)) {
                    piecioMasztowiec.setEnabled(false);
                }
                if(rozgrywka.getUstawianieStatkuNasze().getStanUtworzenia()) {
                    potwierdzenieUstawienia.setEnabled(true);
                }
            }
        });

        potwierdzenieUstawienia = new JButton("Potwierdz swoje ustawienie");
        potwierdzenieUstawienia.setEnabled(false);
        potwierdzenieUstawienia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rozgrywka.getPrzeciwnik().sprawdzanieZgodnosci(5);
                rozgrywka.getPrzeciwnik().sprawdzanieZgodnosci(4);
                rozgrywka.getPrzeciwnik().sprawdzanieZgodnosci(4);
                rozgrywka.getPrzeciwnik().sprawdzanieZgodnosci(3);
                rozgrywka.getPrzeciwnik().sprawdzanieZgodnosci(3);
                rozgrywka.getPrzeciwnik().sprawdzanieZgodnosci(2);
                rozgrywka.getPrzeciwnik().sprawdzanieZgodnosci(2);
                if(rozgrywka.sprawdzenieprawdzUstawienia()) {
                    pSrodek.setVisible(false);
                    srodekStrzelanie.setVisible(true);
                    srodekStrzelanie.setBackground(Color.GRAY);
                }
                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < gridSize; j++) {
                        przyciskPrzeciwnika[i][j].setEnabled(true);
                        przyciskGracza[i][j].setEnabled(false);

                    }

                }
                gra.start();
            }
        });


        pPrzyciskowPrzeciwnika = new JPanel();
        pPrzyciskowPrzeciwnika.setSize(new Dimension(420,420));
        pPrzyciskowPrzeciwnika.setLocation(620,150);
        pPrzyciskowPrzeciwnika.setLayout(new GridLayout(10, 10));
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                przyciskPrzeciwnika[i][j] = new JButton();
                przyciskPrzeciwnika[i][j].setBackground(Color.GRAY);
                przyciskPrzeciwnika[i][j].setPreferredSize(new Dimension(40, 40));
                setLayout(new FlowLayout());
                przyciskPrzeciwnika[i][j].setEnabled(false);
                przyciskPrzeciwnika[i][j].addActionListener(actionListener);

                int finalI = i;
                int finalJ = j;
                przyciskPrzeciwnika[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tmpAtak = new Pozycja(finalI, finalJ);
                        rozgrywka.getStrzelanieDoStatkuNasze().atakuj(tmpAtak);
                        int x = rozgrywka.pokoloruj(tmpAtak);
                        if(x != 2 && x != 3) {
                            pudlo.zagraj();     //efekt dzwiekowy pudla
                            przyciskPrzeciwnika[tmpAtak.getWiersz()][tmpAtak.getKolumna()].setBackground(Color.WHITE);
                            przyciskPrzeciwnika[tmpAtak.getWiersz()][tmpAtak.getKolumna()].setOpaque(true);
                            przyciskPrzeciwnika[tmpAtak.getWiersz()][tmpAtak.getKolumna()].setEnabled(false);
                            komunikat.replaceRange("PUDLO!!!", 0, komunikat.getTabSize());
                            komunikat.setFont(new Font("Cabin", Font.BOLD, 17));
                        }
                        else{
                            trafiony.zagraj();  //efekt dzwiekowy trafiony
                            przyciskPrzeciwnika[tmpAtak.getWiersz()][tmpAtak.getKolumna()].setBackground(Color.BLUE);
                            komunikat.replaceRange("PLONIE..", 0, komunikat.getTabSize());
                            komunikat.setFont(new Font("Cabin", Font.BOLD, 17));
                            przyciskPrzeciwnika[tmpAtak.getWiersz()][tmpAtak.getKolumna()].setOpaque(true);
                            przyciskPrzeciwnika[tmpAtak.getWiersz()][tmpAtak.getKolumna()].setEnabled(false);
                            if(rozgrywka.getPrzeciwnik().getUstawianieStatku().getPlansza().getPlanszaStatkow()[tmpAtak.getWiersz()][tmpAtak.getKolumna()].getStanZatopienia() == 3){
                                ArrayList<Pozycja> tmpWspolrzedne = rozgrywka.getPrzeciwnik().getUstawianieStatku().getPlansza().getPlanszaStatkow()[tmpAtak.getWiersz()][tmpAtak.getKolumna()].getWspolrzedne();
                                for(int i = 0; i < tmpWspolrzedne.size(); i++){
                                    zatopiony.zagraj(); //efekt dzwiekowy zatopienia
                                    przyciskPrzeciwnika[tmpWspolrzedne.get(i).getWiersz()][tmpWspolrzedne.get(i).getKolumna()].setBackground(Color.black);
                                    komunikat.replaceRange("SPLONAL!", 0, komunikat.getTabSize());
                                    komunikat.setFont(new Font("Cabin", Font.BOLD, 17));
                                    przyciskPrzeciwnika[tmpWspolrzedne.get(i).getWiersz()][tmpWspolrzedne.get(i).getKolumna()].setOpaque(true);
                                    przyciskPrzeciwnika[tmpWspolrzedne.get(i).getWiersz()][tmpWspolrzedne.get(i).getKolumna()].setEnabled(false);
                                }
                            }
                        }
                    }
                });
                pPrzyciskowPrzeciwnika.add(przyciskPrzeciwnika[i][j]);
            }
        }

        lGracz = new JLabel("Plansza Gracza");
        lGracz.setFont(new Font("Cabin", Font.BOLD, 20));
        lPrzeciwnik = new JLabel("Plansza Przeciwnika");
        lPrzeciwnik.setFont(new Font("Cabin", Font.BOLD, 20));
        lGra = new JLabel("GRA MORSKA");
        lGra.setFont(new Font("Cabin", Font.BOLD, 30));
        wskazowki = new JTextArea("WSKAZÓWKA: \n" + "Wybierz punkt na planszy gracza, ktory bedzie poczatkiem twojego statku (kolejne punkty ustawiaja sie po prawej lub na dole). Nastepnie wybierz ustawienie poziome lub pionowe, a na koncu ilosc masztow.");
        wskazowki.setSize(new Dimension(200,100));
        wskazowki.setLineWrap(true);
        wskazowki.setFont(new Font("Cabin", Font.BOLD, 13));
        wskazowki.setEditable(false);
        wskazowki.setWrapStyleWord(true);
        wskazowki.setBackground(Color.WHITE);
        legenda = new JTextArea("LEGENDA NA PLANSZY PRZECIWNIKA:\n" + "\n" + "BIALY - pudlo\n" + "NIEBIESKI - plonie\n" + "CZARNY - splonal\n");
        legenda.setSize(new Dimension(200,100));
        legenda.setLineWrap(true);
        legenda.setFont(new Font("Cabin", Font.BOLD, 13));
        legenda.setEditable(false);
        legenda.setWrapStyleWord(true);
        legenda.setBackground(Color.GRAY);
        ruchy = new JTextArea(" ");
        ruchy.setSize(new Dimension(200,100));
        ruchy.setLineWrap(true);
        ruchy.setFont(new Font("Cabin", Font.BOLD, 20));
        ruchy.setEditable(false);
        ruchy.setWrapStyleWord(true);
        ruchy.setBackground(Color.GRAY);
        ruchy.setForeground(Color.RED);
        ruchy.setLayout(new FlowLayout(FlowLayout.CENTER));
        pozostaleStatki = new JTextArea("Pozostalo ci do strzelenia: " + (7 - rozgrywka.getStrzelanieDoStatkuNasze().getIloscZatopionych()) + " statkow, a przeciwnikowi: " + (7 - rozgrywka.getPrzeciwnik().getStrzelanieDoStatku().getIloscZatopionych()));
        pozostaleStatki.setSize(new Dimension(200,100));
        pozostaleStatki.setLineWrap(true);
        pozostaleStatki.setFont(new Font("Cabin", Font.BOLD, 13));
        pozostaleStatki.setEditable(false);
        pozostaleStatki.setWrapStyleWord(true);
        pozostaleStatki.setBackground(Color.GRAY);
        komunikat = new JTextArea("STRZELAJ");
        komunikat.setEditable(false);
        komunikat.setFont(new Font("Cabin", Font.BOLD, 17));
        komunikat.setWrapStyleWord(true);
        komunikat.setBackground(Color.GRAY);
        komunikat.setSize(new Dimension(200,100));
        komunikaty = new JPanel();
        komunikaty.add(komunikat);
        srodekStrzelanie.add(legenda);
        srodekStrzelanie.add(komunikaty);
        srodekStrzelanie.add(pozostaleStatki);
        srodekStrzelanie.add(ruchy);
        twojRuch = new JTextArea("TWÓJ RUCH");
        twojRuch.setLineWrap(true);
        twojRuch.setFont(new Font("Cabin", Font.BOLD, 15));
        twojRuch.setEditable(false);
        twojRuch.setWrapStyleWord(true);
        twojRuch.setBackground(Color.white);
        twojRuch.setVisible(false);

        koniecGryZwyciestwo = new JTextArea("GRATULACJE, WYGRALES!");
        koniecGryZwyciestwo.setSize(1040, 570);
        koniecGryZwyciestwo.setLineWrap(true);
        koniecGryZwyciestwo.setFont(new Font("Cabin", Font.BOLD, 40));
        koniecGryZwyciestwo.setEditable(false);
        koniecGryZwyciestwo.setWrapStyleWord(true);
        koniecGryPrzegrana = new JTextArea("Przegrana.. ale nie zalamuj sie i sprobuj jeszcze raz, a moze uda ci sie przechytrzyc komputer!");
        koniecGryPrzegrana.setSize(1040,570);
        koniecGryPrzegrana.setLineWrap(true);
        koniecGryPrzegrana.setFont(new Font("Cabin", Font.BOLD, 20));
        koniecGryPrzegrana.setEditable(false);
        koniecGryPrzegrana.setWrapStyleWord(true);
        pnGracz.add(lGracz);
        pnPrzeciwnik.add(lPrzeciwnik);
        pGra.add(lGra);
        srodek1.add(wskazowki);
        srodek1.add(twojRuch);
        srodek2.add(pion);
        srodek2.add(poziom);
        srodek3.add(dwuMasztowiec);
        srodek3.add(trojMasztowiec);
        srodek3.add(czteroMasztowiec);
        srodek3.add(piecioMasztowiec);
        srodek3.setLayout(new GridLayout(4,1));
        srodek4.add(potwierdzenieUstawienia);
        pSrodek.add(srodek1);
        pSrodek.add(srodek2);
        pSrodek.add(srodek3);
        pSrodek.add(srodek4);
        pSrodek.setLayout(new FlowLayout());


        pGracza.add(pPrzyciskowGracza);
        pPrzeciwnika.add(pPrzyciskowPrzeciwnika);
        f.add(pGra);
        f.add(pnGracz);
        f.add(pnPrzeciwnik);
        f.add(pSrodek);
        f.add(srodekStrzelanie);
        f.add(pGracza);
        f.add(pPrzeciwnika);
        f.add(pKoniecGry);
        f.setVisible(true);
        f.setResizable(false);
    }
    public static void main(String[] args) {
        Rozgrywka r = new Rozgrywka();
        RozgrywkaGUI rozgrywkaGUI = new RozgrywkaGUI(r);
    }
}




