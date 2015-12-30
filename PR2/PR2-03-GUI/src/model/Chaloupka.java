package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Balik
 */
public class Chaloupka extends O2D {
    private int sirka;
    private Trojuhelnik strecha;
    private Ctverec dum;
    private Obdelnik okno;
    private Obdelnik dvere;

    public Chaloupka(TypySrafovani ts, Color c, Point p, int sirka) {
        super(ts, c, p);
        this.sirka = sirka;
        dum = new Ctverec(ts,c,p,sirka);
        strecha = new Trojuhelnik(ts,c,p,new Point(p.x+sirka,p.y),
                                       new Point(p.x+sirka/2,p.y-sirka/2));
        int dx = sirka/5;
        okno = new Obdelnik(ts, c, new Point(p.x+dx,p.y+dx), dx, dx);
        dvere = new Obdelnik(ts, c, new Point(p.x+3*dx,p.y+2*dx), dx, sirka - 2*dx);
    }



    @Override
    public double getObsah() {
        return strecha.getObsah()+dum.getObsah();
    }

    @Override
    public double getObvod() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void kresliSe(Graphics g) {
        strecha.kresliSe(g);
        dum.kresliSe(g);
        okno.kresliSe(g);
        dvere.kresliSe(g);
    }

}
