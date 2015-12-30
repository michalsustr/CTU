
package ukazkaMoznosti;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Miroslav Balík
 */
public class JFrameOAplikaci extends JFrame{

    public JFrameOAplikaci() {
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("O aplikaci ukázka možností");
        JTextArea ta = new JTextArea("Toto je jednoduchá aplikace dokumentující \n" +
                "základní vlastnosti Swingových grafických komponent. \n" +
                "\n Pro tento text je použito JTextArea " +
                "\n autor Ing. Miroslav Balík, Ph.D.\n" +
                "prosinec 2009 "
                );
        ta.setEditable(false);
        ta.setBackground(Color.ORANGE);
        JPanel p = new JPanel();
        JButtonOtevriZdrojak jboz = new JButtonOtevriZdrojak(this.getClass().getSimpleName());
        p.add(ta);
        p.add(jboz);

        getContentPane().add(p);
    }


}
