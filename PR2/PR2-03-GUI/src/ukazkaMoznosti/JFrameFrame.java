package ukazkaMoznosti;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

/**
 *
 * @author Miroslav Balík
 */
class JFrameFrame extends JFrame{
    JRadioButton jr[];
    public JFrameFrame() {
        JPanel p = new JPanel();
        setTitle("JFrame");
        jr = new JRadioButton[]{new JRadioButton("Exit on close"),
            new JRadioButton("Hide on close"), new JRadioButton("Do nothing")};
        jr[0].setToolTipText("Nastaví uzavření okna na Exit on close, tedy zavřením okna zavřu aplikaci.");
        jr[1].setToolTipText("Nastaví uzavření okna na Hide on close, tedy zavřením okna vypnu zobrazení okna.");
        jr[2].setToolTipText("Nastaví uzavření okna na Do nothing on close, tedy pokusem o zavření okna nic neudělám.");
        class Obsluha implements ItemListener{

            public void itemStateChanged(ItemEvent e) {
               if(jr[0].isSelected()){  //!!!!!ANO, opravdu vnitrni trida vidi promenne nadrazene tridy!!!!
                   setDefaultCloseOperation(EXIT_ON_CLOSE);
               }else if(jr[1].isSelected()){
                   setDefaultCloseOperation(HIDE_ON_CLOSE);
               }else if(jr[2].isSelected()){
                   setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
               }

            }

        }
        Obsluha o = new Obsluha();
        jr[2].setSelected(true);
        ButtonGroup gr = new ButtonGroup();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        for(int i = 0; i<jr.length; i++){
            p.add(jr[i]);
            jr[i].addItemListener(o);
            gr.add(jr[i]);
        }
       JButtonOtevriZdrojak jboz = new JButtonOtevriZdrojak(this.getClass().getSimpleName());
        p.add(jboz);
        getContentPane().add(p);
    }


}
