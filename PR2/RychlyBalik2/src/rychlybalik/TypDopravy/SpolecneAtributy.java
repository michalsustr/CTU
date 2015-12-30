/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.TypDopravy;

/**
 *
 * @author Vojta
 */
public interface SpolecneAtributy {
public double getCenaZaDoruceni(double argument);
public int getCasNutnyProDoruceniVSec(double kilometry);
public double getNosnost();
public double getRychlost();
public void setNosnost(double arg);
public void setRychlost(double arg);
public String getNazev();
public String getZkracenyToString();
public VycetOpravneni getOpravneni();
public boolean isExpeding();
public void setExpeding(boolean naceste);

}
