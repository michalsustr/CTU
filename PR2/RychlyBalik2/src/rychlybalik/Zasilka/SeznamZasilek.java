/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.Zasilka;
  import java.util.Date;
    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Vojta
 */
public class SeznamZasilek extends AbstractTableModel{

    protected List<Zasilka> Zasilky = new ArrayList<Zasilka>();
    private String[] sloupce = {"Speditér", "Doprava", "Hmotnost","Vzdálenost","Čas odjezdu","Zbývá"};

    public SeznamZasilek() {
    }

    public void pridejZasilku(Zasilka krabka) {                          //Přidání pracovníka libovolného typu
        Zasilky.add(krabka);
    }

    public void odeberZasilku(int krabka) {                          //Přidání pracovníka libovolného typu
        Zasilky.remove(krabka);
    }

    public List<Zasilka> getSeznamZasilek() {                                          //getter array listu
        return Zasilky;
    }

    public int getPocetZasilek() {                                               //Počet pracovníků na směně
        return Zasilky.size();
    }

    public int getRowCount() {
        return Zasilky.size();
    }

    public int getColumnCount() {
        return 6;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Zasilka k = Zasilky.get(rowIndex);

        switch (columnIndex) {
            case 0: return k.getDopravator().getZkracenyToString();                    
            case 1: return k.getProstredek().getZkracenyToString();
            case 2: return k.getHmotnost()+" kg";
            case 3: return k.getVzdalenost()+" km";
            case 4: return k.getCasVyexpedovani();
            //case 5: return k.getZbyvajiciCas();
            default: throw new IllegalArgumentException();
        }
    }
     @Override
    public String getColumnName(int column) {
        return sloupce[column];
    }

     public void refreshCasu(){
         for (int i = 0; i < getRowCount(); i++) {
            Zasilka krabka = Zasilky.get(i);
            //int
             //if(){}else{cas--}
         }

     }

 private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//yyyy/MM/dd
        Date date = new Date();
        return dateFormat.format(date);
    }
}
