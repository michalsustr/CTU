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
public class Administrator extends PraPracovnik{

    public Administrator(String jmeno, String prijmeni) {
        super(jmeno, prijmeni);
    }

    @Override
    public VycetOpravneni getOpravneni() {
        return VycetOpravneni.BEZOPRAVNENI;
    }

    @Override
    public String getTyp() {
        return("NE");
    }

    @Override
    public void setOpravneni(VycetOpravneni opravneni) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isExpeding() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setExpeding(boolean naceste) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
