/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.Personalistika;

import rychlybalik.TypDopravy.VycetOpravneni;

/**
 *
 * @author Vojta
 */
public class Vykonator extends PraPracovnik{

    protected VycetOpravneni opravneni;
    protected boolean naceste=false;

    public Vykonator(String jmeno, String prijmeni, VycetOpravneni opravneni) {
        super(jmeno,prijmeni);
        this.opravneni = opravneni;

    }

    public VycetOpravneni getOpravneni() {
        return opravneni;
    }

    public void setOpravneni(VycetOpravneni opravneni) {
        this.opravneni = opravneni;
    }

    @Override
    public String getTyp() {
        return("ANO");
    }

    @Override
    public boolean isExpeding() {
        return naceste;
    }

    @Override
    public void setExpeding(boolean naceste) {
        this.naceste=naceste;
    }



}
