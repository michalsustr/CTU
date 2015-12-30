package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Balik
 */
public class Usecka extends GrO {
    private Point druhyBod;
    public Usecka(Color c, Point pocatek, Point konec) {
        super(c, pocatek);
        druhyBod = konec;
    }

    @Override
    public void kresliSe(Graphics g) {
        Color c = g.getColor(); //uloz si barvu pera
        g.setColor(barva); //nastav svou barvu
        g.drawLine(pozice.x,pozice.y,druhyBod.x,druhyBod.y);
        g.setColor(c); //obnov barvu

    }

    /**
     * @return the druhyBod
     */
    public Point getDruhyBod() {
        return druhyBod;
    }

    /**
     * @param druhyBod the druhyBod to set
     */
    public void setDruhyBod(Point druhyBod) {
        this.druhyBod = druhyBod;
    }
}
