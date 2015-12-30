/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.TypDopravy;

/**
 *
 * @author Vojta
 */
public class DodavkaVelka implements SpolecneAtributy,Riditelny{

    protected final double ZAKLADNI_SAZBA=50;
    protected final double SAZBA_KM=250;
    protected final double nosnost=4000;
    protected final double rychlost=40;
    protected VycetOpravneni opravneni=VycetOpravneni.DODAVKA;
    protected final String NAZEV="Dodávka";
    protected boolean naceste=false;

    public DodavkaVelka() {
        
    }


   
    public double getNosnost() {
        return nosnost;
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
        if (opravneni==null) {this.opravneni=VycetOpravneni.DODAVKA;}
        else{this.opravneni=opravneni;}
    }

    public String getNazev() {
        return NAZEV;
    }

    public void setNosnost(double arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setRychlost(double arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getZkracenyToString() {
        return (NAZEV);
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
