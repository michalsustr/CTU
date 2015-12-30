package model;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Balik
 */
public abstract class O2D extends GrO{
    private TypySrafovani srafovani;

    public O2D(TypySrafovani ts, Color c, Point p) {
        super(c, p);
        srafovani = ts;
    }
    public O2D(){
        super(Color.BLACK,new Point(0,0));
        srafovani = TypySrafovani.NIC;
    }

    /**
     * @return the srafovani
     */
    public TypySrafovani getSrafovani() {
        return srafovani;
    }

    /**
     * @param srafovani the srafovani to set
     */
    public void setSrafovani(TypySrafovani srafovani) {
        this.srafovani = srafovani;
    }
    public abstract double getObsah();
    public abstract double getObvod();
}
