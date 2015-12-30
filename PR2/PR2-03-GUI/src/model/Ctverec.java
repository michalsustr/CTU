package model;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Balik
 */
public class Ctverec extends Obdelnik{

    public Ctverec(TypySrafovani ts, Color c, Point p, int strana) {
        super(ts, c, p, strana, strana);
    }

}
