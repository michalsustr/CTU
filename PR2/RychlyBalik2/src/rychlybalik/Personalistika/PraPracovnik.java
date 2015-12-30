/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.Personalistika;

import java.lang.String;
import rychlybalik.TypDopravy.VycetOpravneni;

/**
 *
 * @author Vojta
 */
public abstract class PraPracovnik {
protected String jmeno;
protected String prijmeni;

    public PraPracovnik(String jmeno, String prijmeni) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getZkracenyToString(){
    String prvnipismeno=String.valueOf(jmeno.charAt(0));
    return(prvnipismeno+"."+prijmeni);
}

public abstract VycetOpravneni getOpravneni();
public abstract void setOpravneni(VycetOpravneni opravneni);
public abstract String getTyp();
public abstract boolean isExpeding();
public abstract void setExpeding(boolean naceste);

}
