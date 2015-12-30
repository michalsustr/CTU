package ukazkaMoznosti;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Miroslav Balík
 */
public class JFrameHlavniOkno extends JFrame {
String localStyle = UIManager.getSystemLookAndFeelClassName();


    JFrameOAplikaci jfoa = null;
    JFrameTlacitka jftl = null;
    JFrameSeznamy jfsez;
    JFrameFrame jfrfr;
    JFrameDialogy jdialog;
    JFrameAplet jfraplet;
    public JFrameHlavniOkno() {
          try
        {
            UIManager.setLookAndFeel(localStyle);
        }
        catch (Exception e)
        {
        }
        Container kam = this.getContentPane();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//zavření
        JPanel p = new JPanel();
        p.add(new JLabel("<HTML>" +
                "<H1> Jednoduchá aplikace dokumentující definice <br> " +
                "a použití základních komponent ve Swingu </H1>" +
                "Vyberte si z menu příklad okna, které chcete zobrazit,<br> " +
                "vyzkoušejte, jak funguje a prohlédněte si zdrojový kód</HTML>"));

        // menu= proužek, sloupeček, položky
        JMenuBar jmb = new JMenuBar();

        JMenu mSoubor = new JMenu("Soubor");
        JMenuItem ms1 = new JMenuItem("O aplikaci"); //položka menu
        JSeparator ms2 = new JSeparator();
        JMenuItem ms3 = new JMenuItem("Konec");
        ms3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK)); //přiřazení klávesové zkratky ALT-X


        mSoubor.add(ms1);//položka do menu
        mSoubor.add(ms2);
        mSoubor.add(ms3);
        ms1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (jfoa == null) {
                    jfoa = new JFrameOAplikaci();
                    jfoa.pack();
                }
                jfoa.setVisible(true);
            }
        });
        ms3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0); // ukonči program
            }
        });

        jmb.add(mSoubor); //přidání sloupečku do menuBaru
        jmb.add(vytvorMenuKomponenty());
        jmb.add(vytvorMenuKontejnery());
        jmb.add(vytvorMenuSpravciRozvrzeni());
        jmb.add(vytvorMenuLookAndFeel());

        setJMenuBar(jmb); //přiřazení menu k oknu

        JButtonOtevriZdrojak jboz = new JButtonOtevriZdrojak(this.getClass().getSimpleName());
        p.add(jboz);
        kam.add(p);
        setDefaultCloseOperation(WIDTH);

    }

    JMenu vytvorMenuKomponenty() {
        JMenu menu = new JMenu("Komponenty");
        JMenuItem i1 = new JMenuItem("Tlačítka");
        i1.setToolTipText("JButton, JToggle button, JCheckBox, JRadioButton");
        JMenuItem i2 = new JMenuItem("Seznamy");
        i2.setToolTipText("JcomboBox a JList");
        JMenuItem i3 = new JMenuItem("Vstup dat");
        i3.setToolTipText("JTextField a JSlider");
        JMenuItem i4 = new JMenuItem("Needitovatelné informační komponenty");
        i4.setToolTipText("JLabel, Progress bar a tool tip, nápověda");
        JMenuItem i5 = new JMenuItem("Editovatelné informační komponenty");
        i5.setToolTipText("JText, JTable, JTree, JColorchooser a JFileChooser");
        i1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (jftl == null) {
                    jftl = new JFrameTlacitka();
                    jftl.pack();
                }
                jftl.setVisible(true);
            }
        });
        i2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (jfsez == null) {
                    jfsez = new JFrameSeznamy();
                    jfsez.pack();
                }
                jfsez.setVisible(true);
            }
        });

        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        menu.add(new JSeparator(JSeparator.HORIZONTAL));
        menu.add(i4);
        menu.add(i5);
        return menu;
    }

    JMenu vytvorMenuKontejnery() {
        JMenu menu = new JMenu("Kontejnery");
        JMenuItem mi = new JMenuItem("JFrame"),
                mi2 = new JMenuItem("JDialog"),
                mi3 = new JMenuItem("JApplet");
        menu.add(mi);
        menu.add(mi2);
        menu.add(mi3);
        mi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jfrfr == null) {
                    jfrfr = new JFrameFrame();
                    jfrfr.pack();
                }
                jfrfr.setVisible(true);
            }
        });
        mi2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jdialog == null) {
                    jdialog = new JFrameDialogy();
                    jdialog.pack();
                }
                jdialog.setVisible(true);
            }
        });

        mi3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (jfraplet == null) {
                    jfraplet = new JFrameAplet();
                    jfraplet.pack();
                }
                jfraplet.setVisible(true);
            }
        });


        return menu;
    }

    JMenu vytvorMenuSpravciRozvrzeni() {
        JMenu menu = new JMenu("Správci rozvržení");
        return menu;
    }

    /**
     * Vytvoří menu pro nastavování Look and Feel, přípustné hodnoty si automaticky zjistí a poté
     * pro ně vytvoří příslušné položky v menu
     * @return
     */
    public JMenu vytvorMenuLookAndFeel() {
        JMenu menu = new JMenu("L&F - vzhled a chování");
        //tady je trocha programování, menu se vytvoří podle dostupných LaF
        UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
        JMenuItem mi[] = new JMenuItem[info.length];


        for (int i = 0; i < info.length; i++) {
            // Get the name of the look and feel that is suitable for display to the user
            mi[i] = new JMenuItem(info[i].getName());
            mi[i].addActionListener(new MujLookAndFeelMenuAdapter(info[i], this));
            menu.add(mi[i]);
        }
        return menu;
    }

    public static void main(String[] args) {
        JFrameHlavniOkno jfho = new JFrameHlavniOkno();
        jfho.setTitle("Ukázka možností GUI v Javě");
        jfho.pack();
        jfho.setVisible(true);
    }

    /**
     * funkce pro propagaci změny Look and Feel
     */
    public void propagujZmenuLAF() {

        SwingUtilities.updateComponentTreeUI(this);
        this.pack();
        if (jfoa != null) {
            SwingUtilities.updateComponentTreeUI(jfoa);
            jfoa.pack();
        }
        if (jftl != null) {
            SwingUtilities.updateComponentTreeUI(jftl);
            jftl.pack();
        }
        if (jfsez != null) {
            SwingUtilities.updateComponentTreeUI(jfsez);
            jfsez.pack();
        }

        if (jfrfr != null) {
            SwingUtilities.updateComponentTreeUI(jfrfr);
            jfrfr.pack();
        }
        if (jdialog != null) {
            SwingUtilities.updateComponentTreeUI(jdialog);
            jdialog.pack();
        }
        if (jfraplet != null) {
            SwingUtilities.updateComponentTreeUI(jfraplet);
            jfraplet.pack();
        }


    }
}

class MujLookAndFeelMenuAdapter implements ActionListener {

    LookAndFeelInfo info = null;
    JFrameHlavniOkno jfr;

    public MujLookAndFeelMenuAdapter(LookAndFeelInfo li, JFrameHlavniOkno fr) {
        info = li;
        jfr = fr;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(info.getClassName());
            jfr.propagujZmenuLAF();
            jfr.pack();
            // System.out.println("Menim laf"+info.getClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
