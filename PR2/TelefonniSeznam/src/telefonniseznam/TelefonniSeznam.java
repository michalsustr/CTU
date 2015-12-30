package telefonniseznam;

import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author balikm1
 */
public class TelefonniSeznam extends AbstractTableModel {

    private String[] sloupce = {"jmeno", "prijmeni", "cislo"};
    private List<Kontakt> seznam = new ArrayList<Kontakt>();

    @Override
    public String getColumnName(int column) {
        return sloupce[column];
    }

    public TelefonniSeznam() {
        seznam.add(new Kontakt("Karel", "Vomacka", "123"));
        seznam.add(new Kontakt("Maxipes", "Fik", "456"));
    }

    public void pridejKontakt(Kontakt k) {
        seznam.add(k);
    }

    public void smazKontakt(Kontakt k) {
        if (this.obsahujeKontakt(k)) {
            seznam.remove(k);
        }
    }

    public boolean obsahujeKontakt(Kontakt k) {
        return seznam.contains(k);
    }

    public int getRowCount() {
        return seznam.size();
    }

    public int getColumnCount() {
        return Kontakt.pocet;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Kontakt k = seznam.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return k.getJmeno();
            case 1:
                return k.getPrijmeni();
            case 2:
                return k.getTelefonniCislo();
            default:
                throw new IllegalArgumentException("nedefinovany sloupec");
        }


    }
}
