/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ukazkaMoznosti;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Miroslav Balík
 */
class JFrameAplet extends JFrame{

    public JFrameAplet() {
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Applet");
        JTextArea ta = new JTextArea("Applety jsou jednoduché Java aplikace vložené do HTML stránky " +
                " \n a interpretované v HTML prohlížeči. " +
                "\n Vidíš tady někde HTML prohlížeč?" +
                "\n Ne, tak se nediv, že tu není applet. "
                );
        ta.setEditable(false);
        JPanel p = new JPanel();
        JButtonOtevriZdrojak jboz = new JButtonOtevriZdrojak(this.getClass().getSimpleName());
        p.add(ta);
        p.add(jboz);

        getContentPane().add(p);
    }





}
