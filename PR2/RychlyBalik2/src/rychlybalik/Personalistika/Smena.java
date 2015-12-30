/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rychlybalik.Personalistika;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rychlybalik.TypDopravy.Riditelny;
import rychlybalik.TypDopravy.SpolecneAtributy;
import rychlybalik.VozovyPark.VozovyPark;

/**
 *
 * @author Vojta
 */
public class Smena extends AbstractTableModel {

    protected List<PraPracovnik> Smena = new ArrayList<PraPracovnik>();             //Array List reprezentující seznam pracovníků na směně.
    private String[] sloupce = {"Jméno", "Příjmení", "Výkonný ?", "Oprávnění"};

    public Smena() {
    }

    public boolean getMuzeRidit(int index, SpolecneAtributy Vuz) {

        if (Vuz instanceof Riditelny == true) {
            if (Smena.get(index).getOpravneni() == Vuz.getOpravneni()) {
                return true;
            } else {
                return false;
            }
        }

    else

    {
        return true;
    }}

    public void pridejPracovnika(PraPracovnik pracovnik) {                          //Přidání pracovníka libovolného typu
        Smena.add(pracovnik);
    }

    public void odeberPracovnika(int pracovnik) {                          //Přidání pracovníka libovolného typu
        Smena.remove(pracovnik);
    }

    public List<PraPracovnik> getSmena() {                                          //getter array listu
        return Smena;
    }

    public int getPocetPracovniku() {                                               //Počet pracovníků na směně
        return Smena.size();
    }

    public VozovyPark getObsluhovatelna(VozovyPark vozidla) {                                   //problém nefunguje porovnávání řidičských licencí, je potřeba vnutit metody výše?
        VozovyPark Obsluhovatelna = new VozovyPark();
        for (int i = 0; i < (Smena.size()); i++) {
            if (Smena.get(i) instanceof Vykonator) {

                for (int j = 0; j < (vozidla.getPocetVozu()); j++) {
                    if ((Smena.get(i)).getOpravneni().equals((vozidla.getSeznamVozu().get(j)).getOpravneni()));
                    {
                        Obsluhovatelna.pridejVuz((vozidla.getSeznamVozu()).get(j));
                    }
                }
            }
        }



        return Obsluhovatelna;
    }

    public int getRowCount() {
        return Smena.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        PraPracovnik k = Smena.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return k.getJmeno();
            case 1:
                return k.getPrijmeni();
            case 2:
                return k.getTyp();
            case 3:
                return k.getOpravneni().toString();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getColumnName(int column) {
        return sloupce[column];
    }
}
