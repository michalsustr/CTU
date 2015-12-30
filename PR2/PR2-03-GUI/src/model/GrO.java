package model;

import java.awt.*; //Point a Color jsou z teto knihovny

/**
 *
 * @author Balik
 */
public abstract class GrO {
    protected Color barva;
    protected Point pozice;

    public GrO(Color c, Point p){
        barva = c;
        pozice = p;
    }
    public abstract void kresliSe(Graphics g);

    /**
     * @return the barva
     */
    public Color getBarva() {
        return barva;
    }

    /**
     * @param barva the barva to set
     */
    public void setBarva(Color barva) {
        this.barva = barva;
    }

    /**
     * @return the pozice
     */
    public Point getPozice() {
        return pozice;
    }

    /**
     * @param pozice the pozice to set
     */
    public void setPozice(Point pozice) {
        this.pozice = pozice;
    }

}
