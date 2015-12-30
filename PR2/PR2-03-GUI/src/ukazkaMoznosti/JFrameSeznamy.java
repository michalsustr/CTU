package ukazkaMoznosti;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Miroslav Balík
 */
public class JFrameSeznamy extends JFrame {

    JList list;
    JComboBox combo;
    JLabel label;
    public JFrameSeznamy() {
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Komponenty pro seznamy, JList a JComboBox");
        String seznam[] = {"Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek", "Sobota", "Neděle"};
        list = new JList(seznam);
        String seznam2[] = {"Jedna", "Dva", "Tři", "Čtyři"};
        combo = new JComboBox(seznam2);
        JPanel p = new JPanel(), l = new JPanel();
        AktualizatorVybranych av = new AktualizatorVybranych(this);
        list.addListSelectionListener(av);
        combo.addItemListener(av);
        l.add(list);
        l.add(combo);
        p.add(label = new JLabel("Zde se budou zobrazovat Vámi vybrané položky, můžete jich současně vybrat několik."));
        JButtonOtevriZdrojak jboz = new JButtonOtevriZdrojak(this.getClass().getSimpleName());
        l.add(jboz);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, p, l);
        getContentPane().add(splitPane);
    }
    class AktualizatorVybranych implements ListSelectionListener, ItemListener{
      JFrameSeznamy fr;

        public AktualizatorVybranych(JFrameSeznamy fr) {
            this.fr = fr;
        }



        public void valueChanged(ListSelectionEvent e) {
           String s = "<html>Seznam vybraných položek v komponentě List: <ul>";
            Object[] vybraneObjekty = fr.list.getSelectedValues();
            for (int i = 0; i < vybraneObjekty.length; i++) {
                s+= "<li>" + vybraneObjekty[i]+"</li>";
            }
            s+="</ul> \n Seznam vybraných položek v komponentě JComboBox: <ul>";
            vybraneObjekty = fr.combo.getSelectedObjects();
            for (int i = 0; i < vybraneObjekty.length; i++) {
                s+= "<li>" + vybraneObjekty[i]+"</li>";
            }
            s+="</ul>";
            fr.label.setText(s);
            fr.pack();
        }

        public void itemStateChanged(ItemEvent e) {
            //funkce nutná pro item listener
            valueChanged(null);
        }
    }
}
