/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.TypDopravy;

/**
 *
 * @author Vojta
 */
public class Kolo implements  SpolecneAtributy,Riditelny{

    protected final double SAZBA_HOD=250;
    protected double nosnost;
    protected double rychlost;
    protected VycetOpravneni opravneni=VycetOpravneni.KOLO;
    protected String NAZEV="Kolo";
    protected boolean naceste=false;

    public Kolo(double nosnost, double rychlost) {
        if(nosnost <= 10  && nosnost >0){this.nosnost = nosnost;}
        else {this.nosnost=10;}
        this.rychlost = rychlost;
    }

    public double getNosnost() {
        return nosnost;
    }

    /* public double setNosnost(double nosnost) {
        if(nosnost <= 10 && nosnost >0){return this.nosnost=nosnost;}
        else {return this.nosnost=10;}
    }*/

    public double getRychlost() {
        return rychlost;
    }

   /* public double setRychlost(double rychlost) {
        return this.rychlost=rychlost;
    }*/

    public double getCenaZaDoruceni(double kilometry) {
        return SAZBA_HOD*(kilometry/rychlost);
    }

    public VycetOpravneni getOpravneni() {
        return opravneni;
    }

    public void setOpravneni(VycetOpravneni opravneni) {
        if (opravneni==null) {this.opravneni=VycetOpravneni.KOLO;}
        else{this.opravneni=opravneni;}
    }

    public String getNazev() {
        return NAZEV;
    }

    public void setNosnost(double arg) {
        if(arg <= 10 && arg >0){this.nosnost=arg;}
        else {this.nosnost=10;}
    }

    public void setRychlost(double arg) {
        this.rychlost=arg;
    }

    public String getZkracenyToString() {
        return (NAZEV+","+nosnost+","+rychlost);
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
