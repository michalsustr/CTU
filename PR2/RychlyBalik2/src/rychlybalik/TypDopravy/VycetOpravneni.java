/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.TypDopravy;

/**
 *
 * @author Vojta
 */
public enum VycetOpravneni {

DODAVKA("B"),PICK_UP("C"),KOLO("kolo"),BEZOPRAVNENI("");

private final String OPRAVNENI;

    private VycetOpravneni(String OPRAVNENI) {
        this.OPRAVNENI = OPRAVNENI;
    }

    public String getOPRAVNENI() {
        return OPRAVNENI;
    }

    @Override
    public String toString() {
        return OPRAVNENI;
    }






}
