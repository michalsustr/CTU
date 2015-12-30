package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Balik
 */
public class Trojuhelnik extends O2D{
    private Point druhyBod, tretiBod;

    public Trojuhelnik(TypySrafovani ts, Color c, Point p, Point druhyBod, Point tretiBod) {
        super(ts, c, p);
        this.druhyBod = druhyBod;
        this.tretiBod = tretiBod;
    }

    @Override
    public double getObsah() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getObvod() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void kresliSe(Graphics g) {
        Color c = g.getColor(); //uloz si barvu pera
        g.setColor(barva); //nastav svou barvu
        g.drawLine(pozice.x, pozice.y, druhyBod.x, druhyBod.y);
        g.drawLine(druhyBod.x, druhyBod.y, tretiBod.x, tretiBod.y);
        g.drawLine(tretiBod.x, tretiBod.y,pozice.x, pozice.y);
        g.setColor(c); //obnov barvu
    }

}
