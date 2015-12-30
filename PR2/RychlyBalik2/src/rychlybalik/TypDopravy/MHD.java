/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.TypDopravy;

/**
 *
 * @author Vojta
 */
public class MHD implements SpolecneAtributy{
    protected final double SAZBA_HOD=200;
    protected final double rychlost=30;
    protected double nosnost;
    protected final String NAZEV="MHD";
    protected final VycetOpravneni opravneni=VycetOpravneni.BEZOPRAVNENI;
    protected boolean naceste=false;

    public MHD(double nosnost) {
        if(nosnost <= 15 && nosnost > 0 ){this.nosnost = nosnost;}
            else {this.nosnost=15;}
    }

    public double getCenaZaDoruceni(double kilometry) {
       return SAZBA_HOD*(kilometry/rychlost);
    }

    public void setNosnost(double nosnost){
    if(nosnost <= 15 && nosnost > 0 ){this.nosnost = nosnost;}
    else {this.nosnost=15;}
    }

    public double getNosnost() {
        return nosnost;
    }

    public double getRychlost() {
        return rychlost;
    }

    public String getNazev() {
        return NAZEV;
    }

    public VycetOpravneni getOpravneni() {
        return opravneni;
    }

    public void setRychlost(double arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setOpravneni(VycetOpravneni opravneni) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getZkracenyToString() {
        return (NAZEV+","+nosnost);
    }
  public boolean isExpeding() {
        return naceste;
    }

    public void setExpeding(boolean naceste) {
        this.naceste=naceste;
    }

public int getCasNutnyProDoruceniVSec(double kilometry) {
        return (int)(kilometry/rychlost)*3600;
    }
}
