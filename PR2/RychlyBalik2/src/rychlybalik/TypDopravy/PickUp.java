/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.TypDopravy;

/**
 *
 * @author Vojta
 */
public class PickUp implements SpolecneAtributy,Riditelny{

    protected final double ZAKLADNI_SAZBA=80;
    protected final double SAZBA_KM=230;
    protected double nosnost;
    protected final double rychlost=40;
    protected VycetOpravneni opravneni=VycetOpravneni.PICK_UP;
    protected final String NAZEV="Pick-up";
    protected boolean naceste=false;

    public PickUp(double nosnost) {
        if(nosnost <= 2500 && nosnost >0)
        {this.nosnost=nosnost;}
        else{this.nosnost=2500;}
    }

     public double getNosnost() {
        return nosnost;
    }

     public void setNosnost(double nosnost) {
        if(nosnost <= 2500 && nosnost >0){this.nosnost=nosnost;}
        else {this.nosnost=2500;}
    }

    public double getRychlost() {
        return rychlost;
    }
    
    public double getCenaZaDoruceni(double kilometry) {
        return ZAKLADNI_SAZBA+SAZBA_KM*kilometry;
    }

    public VycetOpravneni getOpravneni() {
        return opravneni;
    }

    public void setOpravneni(VycetOpravneni opravneni) {
        if (opravneni==null) {this.opravneni=VycetOpravneni.PICK_UP;}
        else{this.opravneni=opravneni;}
    }

    public String getNazev() {
        return NAZEV;
    }

    public void setRychlost(double arg) {
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
