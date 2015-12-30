/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.GUI;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Vojta
 */
public class HlavniOkno2 {

    JFrame frame;
    JPanel panel;
    JTable Vozy;
    JTable Pracovnici;
    JTable Zasilky;
    public HlavniOkno2() {
        this.frame=new JFrame();
        this.frame.setVisible(true);
        this.frame.setSize(800, 500);
        this.frame.setResizable(false);
        this.panel=new JPanel();
        this.panel.setLayout(new GridLayout(2,3));
        this.panel.setBackground(Color.blue);
        this.frame.add(panel);
        this.Vozy=new JTable();
        this.Vozy.setSize(200, 250);
        this.panel.add(Vozy);
        this.Pracovnici=new JTable();
        this.Pracovnici.setSize(200, 250);
        this.panel.add(Pracovnici);
        this.Zasilky=new JTable();
        this.Zasilky.setSize(200, 250);
        this.panel.add(Zasilky);

    }

}
