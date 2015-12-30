package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Balik
 */
public class Bod extends GrO{
    public Bod(Color c, Point p){
        super(c,p);
    }
    @Override
    public void kresliSe(Graphics g) {
        Color c = g.getColor(); //uloz si barvu pera
        g.setColor(barva); //nastav svou barvu
        g.fillOval(pozice.x-2, pozice.y-2, 4,4); //bod je takove mal kolecko
        g.setColor(c); //obnov barvu

    }
}
