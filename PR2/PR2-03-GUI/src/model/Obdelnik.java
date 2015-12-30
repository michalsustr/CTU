package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import static model.TypySrafovani.*;

/**
 *
 * @author Balik
 */
public class Obdelnik extends O2D {

    private int sirka;
    private int vyska;

    public Obdelnik(TypySrafovani ts, Color c, Point p, int sirka, int vyska) {
        super(ts, c, p);
        this.sirka = sirka;
        this.vyska = vyska;
    }

    @Override
    public double getObsah() {
        return (double) sirka * vyska; //proc se pretypovava? Kolik by byl vysledek pro sirka = vyska = 1000000 bez a s pretypovanim?
    }

    @Override
    public double getObvod() {
        return sirka + vyska;
    }

    @Override
    public void kresliSe(Graphics g) {
        Color c = g.getColor(); //uloz si barvu pera
        g.setColor(barva); //nastav svou barvu
        g.drawRect(pozice.x, pozice.y, sirka, vyska);
        switch (getSrafovani()) {
            case VODOROVNEPRUHY:
                for (int y = pozice.y; y < pozice.y + vyska; y += 5) {
                    g.drawLine(pozice.x, y, pozice.x + sirka, y);
                }
                break;
            case SVISLEPRUHY:
                for (int x = pozice.x; x < pozice.x + sirka; x += 5) {
                    g.drawLine(x, pozice.y, x, pozice.y + vyska);
                }
                break;
            case PLNE:
                g.fillRect(pozice.x, pozice.y, sirka, vyska);
                break;
        }
        g.setColor(c); //obnov barvu
    }

    /**
     * @return the sirka
     */
    public int getSirka() {
        return sirka;
    }

    /**
     * @param sirka the sirka to set
     */
    public void setSirka(int sirka) {
        this.sirka = sirka;
    }

    /**
     * @return the vyska
     */
    public int getVyska() {
        return vyska;
    }

    /**
     * @param vyska the vyska to set
     */
    public void setVyska(int vyska) {
        this.vyska = vyska;
    }
}
