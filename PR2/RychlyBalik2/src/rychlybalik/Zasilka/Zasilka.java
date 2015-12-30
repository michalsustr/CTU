/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.Zasilka;

import rychlybalik.Personalistika.PraPracovnik;

import rychlybalik.TypDopravy.SpolecneAtributy;
import java.util.Date;
    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
/**
 *
 * @author Vojta
 */
public class Zasilka {

    protected double hmotnost;
    protected double vzdalenost;
    protected final String NAZEV="Zásilka";
    protected SpolecneAtributy prostredek;
    protected PraPracovnik dopravator;
    protected String cas;

    public Zasilka(double hmotnost, double vzdalenost) {
        this.hmotnost = hmotnost;
        this.vzdalenost = vzdalenost;
        this.cas=getDateTime();
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public double getHmotnost() {
        return hmotnost;
    }

    public void setHmotnost(double hmotnost) {
        this.hmotnost = hmotnost;
    }

    public double getVzdalenost() {
        return vzdalenost;
    }

    public void setVzdalenost(double vzdalenost) {
        this.vzdalenost = vzdalenost;
    }
    public String getNazev(){
    return NAZEV;};

    public PraPracovnik getDopravator() {
        return dopravator;
    }

    public void setDopravator(PraPracovnik dopravator) {
        this.dopravator = dopravator;
    }

    public SpolecneAtributy getProstredek() {
        return prostredek;
    }

   

    public void setProstredek(SpolecneAtributy prostredek) {
        this.prostredek = prostredek;
    }

   public String getCasVyexpedovani(){return cas;}
public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//yyyy/MM/dd
        Date date = new Date();
        return dateFormat.format(date);
    }



}
