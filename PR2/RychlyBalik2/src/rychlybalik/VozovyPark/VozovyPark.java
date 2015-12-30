/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rychlybalik.VozovyPark;


import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rychlybalik.TypDopravy.SpolecneAtributy;

/**
 *
 * @author Vojta
 */
public class VozovyPark extends AbstractTableModel {

    protected List<SpolecneAtributy> VozovyPark = new ArrayList<SpolecneAtributy>();
    private String[] sloupce = {"Prostředek", "Nosnost", "Rychlost"};

    public VozovyPark() {
    }

    public void pridejVuz(SpolecneAtributy Prostredek) {
        VozovyPark.add(Prostredek);
    }
    public void odeberVuz(int index) {
        if(VozovyPark.isEmpty()==false){
        VozovyPark.remove(index);}
    }

    public List<SpolecneAtributy> getSeznamVozu() {
        return VozovyPark;
    }

    public int getPocetVozu() {
        return VozovyPark.size();
    }

    public String getNejlevnejsi(double hmotnost, double kilometry) {                               //metoda pro vypocet nejlevnejsiho zpusobu dopravy.
        double nejcena = 0;
        int citac = 0;
        String auta="";
        for (int i = 0; i < (VozovyPark.size()); i++) {
            if ((VozovyPark.get(i).getNosnost()) >= hmotnost) {
                citac++;
                if (citac == 1) {
                    nejcena = VozovyPark.get(i).getCenaZaDoruceni(kilometry);
                } else {
                    nejcena = Math.min(VozovyPark.get(i).getCenaZaDoruceni(kilometry), nejcena);
                }
            }
        }
        for (int i = 0; i < (VozovyPark.size()); i++) {
            if (nejcena== VozovyPark.get(i).getCenaZaDoruceni(kilometry)){
            auta += VozovyPark.get(i).getNazev()+"\n";
            }
        }
        return "Nejlevnější cena je: "+String.valueOf(nejcena)+"\nDosažená dopravními prostředky: \n"+auta;
    }

    @Override
    public String toString() {
        String vystup = "Seznam prostředků\n----------------\n";
        for (SpolecneAtributy Prostredek : VozovyPark) {
            vystup += Prostredek.getNazev() + "\n";
        }
        return vystup + "----------------";
    }

    public int getRowCount() {
       return VozovyPark.size();
    }

    public int getColumnCount() {     //sloupce počet sloupců v zobrazované tabulce.
        return 3;
    }

        public Object getValueAt(int rowIndex, int columnIndex){
        SpolecneAtributy k = VozovyPark.get(rowIndex);

        switch (columnIndex) {
            case 0: return k.getNazev();
            case 1: return k.getNosnost();
            case 2: return k.getRychlost();
            default: throw new IllegalArgumentException();
        }
      
    }
          @Override
    public String getColumnName(int column) {
        return sloupce[column];
    }
    
}
