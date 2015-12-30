package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 *
 * @author Miroslav Balík
 * Pro předmět programování 2 na FEL ČVUT
 */
public class JFrameHlavniOkno extends JFrame {
    private JPanel nastroje = new JPanel();
    private KresliciPlocha plocha = new KresliciPlocha();
    private JLabel statusBar = new JLabel("status bar");
    private JTextArea text = new JTextArea("Tady se budou zobrazovat \n informace o objektech");

    public JFrameHlavniOkno(){
        Container c = this.getContentPane();
        nastroje.setLayout(new GridLayout(3, 1));
        c.setLayout(new BorderLayout());
        JToggleButton b = new JToggleButton("Bod");
        JToggleButton u = new JToggleButton("Usecka");

        Icon ikonka = new ImageIcon(getClass().getResource("/obr/obdelnik.gif"));
        JToggleButton o = new JToggleButton(ikonka);
        o.setToolTipText("Tento nástroj umožňuje kreslení obdélníků");
   
        ButtonGroup tlacitka = new ButtonGroup();
        tlacitka.add(b);
        tlacitka.add(u);
        tlacitka.add(o);
        JToolBar tb = new JToolBar("Nástroje pro malování");
        tb.add(b);
        tb.add(u);
        tb.add(o);
        c.add(tb,BorderLayout.WEST);
        c.add(statusBar,BorderLayout.SOUTH);
        c.add(text,BorderLayout.EAST);
        c.add(plocha, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrameHlavniOkno jfr = new JFrameHlavniOkno();
                jfr.pack();
                jfr.setVisible(true);
                JFrameEditObjs jfeo = new JFrameEditObjs();
                jfeo.setVisible(true);
            }
        });
    }
}
