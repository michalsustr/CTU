package ukazkaMoznosti;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

/**
 *
 * @author Miroslav Balík
 */
public class JFrameTlacitka extends JFrame {

    JToggleButton tb[] = new JToggleButton[4];
    JCheckBox jc[] = new JCheckBox[4];
    JRadioButton jr[] = new JRadioButton[4];
    JCheckBox jcPaintBorder = new JCheckBox("Kreslit rámeček");
    JCheckBox jcFokus = new JCheckBox("Kreslit fokus");
    JCheckBox jcEnabled = new JCheckBox("Enabled");
    JCheckBox jcSeskupit = new JCheckBox("Seskupit");
    JCheckBox jcKreslitObsah = new JCheckBox("KreslitObsah");
    JCheckBox jcTextNahore = new JCheckBox("Text nahoře");
    JCheckBox jcTextVpravo = new JCheckBox("Text napravo");

    public JFrameTlacitka() {
        this.setTitle("Tlačítka");
        Container kam = getContentPane();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        JPanel p = new JPanel(), l = new JPanel();
        JPanel ptl = new JPanel(),
                pjc = new JPanel(),
                pr = new JPanel();
        p.add(ptl);
        p.add(pjc);
        p.add(pr);

        for (int i = 0; i < tb.length; i++) {
            tb[i] = new JToggleButton("-" + i + "-");
            ptl.add(tb[i]);
        }
        for (int i = 0; i < jc.length; i++) {
            jc[i] = new JCheckBox("*" + i + "*");
            pjc.add(jc[i]);
        }
        for (int i = 0; i < jr.length; i++) {
            jr[i] = new JRadioButton("+" + i + "+");
            pr.add(jr[i]);
        }
        JLabel nadpis = new JLabel("<html><H2>Zkuste změnit stav nějakých přepínačů <br> a " +
                "podívejte se na chování prvků nahoře</H2></html>");
        l.add(nadpis);

        l.add(jcEnabled);
        l.add(jcFokus);
        l.add(jcPaintBorder);
        l.add(jcSeskupit);
        l.add(jcKreslitObsah);
        l.add(jcTextNahore);
        l.add(jcTextVpravo);
        JButtonOtevriZdrojak jboz = new JButtonOtevriZdrojak(this.getClass().getSimpleName());
        l.add(jboz);

        l.setPreferredSize(new Dimension((int) nadpis.getPreferredSize().getWidth() + 10, 200));
        p.setPreferredSize(new Dimension((int) nadpis.getPreferredSize().getWidth() + 10, 200));
        jr[0].setSelected(true);
        tb[1].setSelected(true);
        jc[2].setSelected(true);



        jcEnabled.addItemListener(new MojeObsluha(Zmena.ZNEPLATNIT, this));
        jcEnabled.setToolTipText("zneplatní a znovu povolí tlačítka");
        jcEnabled.setSelected(true);
        jcKreslitObsah.setSelected(true);
        jcKreslitObsah.addItemListener(new MojeObsluha(Zmena.KRESLIT_OBSAH, this));
        jcFokus.addItemListener(new MojeObsluha(Zmena.FOKUS, this));
        jcFokus.setToolTipText("Musíte skočit na tlačítko, nebo fokus přepnout pomocí tabelátoru, abyste viděli změnu.");
        jcPaintBorder.addItemListener(new MojeObsluha(Zmena.RAMECEK, this));
        jcTextNahore.addItemListener(new MojeObsluha(Zmena.TEXT_NAHORE, this));
        jcTextVpravo.addItemListener(new MojeObsluha(Zmena.TEXT_VPRAVO, this));
        jcTextVpravo.setSelected(true);
        jcSeskupit.addItemListener(new MojeObsluha(Zmena.SESKUPIT, this));
        jcSeskupit.setToolTipText("Seskupení Toggle a Radio tlačítek, po seskupení lze vybrat pouze jeden z nich");

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, p, l);
        kam.add(splitPane);
        this.pack();


    }
}

enum Zmena {

    FOKUS, ZNEPLATNIT, RAMECEK, SESKUPIT, KRESLIT_OBSAH, TEXT_VPRAVO, TEXT_NAHORE;
}

class MojeObsluha implements ItemListener {

    ButtonGroup bg1 = new ButtonGroup();
    ButtonGroup bg2 = new ButtonGroup();
    Zmena z;
    JFrameTlacitka fr;

    public MojeObsluha(Zmena z, JFrameTlacitka jf) {
        this.z = z;
        fr = jf;
    }

    public void itemStateChanged(ItemEvent e) {
        JCheckBox jc = (JCheckBox) e.getItem();
        boolean changeToTrue = jc.isSelected();
        for (int i = 0; i < fr.tb.length; i++) {
            switch (z) {
                case FOKUS:
                    fr.tb[i].setFocusPainted(changeToTrue);
                    fr.jc[i].setFocusPainted(changeToTrue);
                    fr.jr[i].setFocusPainted(changeToTrue);
                    break;
                case ZNEPLATNIT:
                    fr.tb[i].setEnabled(changeToTrue);
                    fr.jc[i].setEnabled(changeToTrue);
                    fr.jr[i].setEnabled(changeToTrue);
                    break;
                case RAMECEK:
                    fr.tb[i].setBorderPainted(changeToTrue);
                    fr.jc[i].setBorderPainted(changeToTrue);
                    fr.jr[i].setBorderPainted(changeToTrue);
                    break;
                case TEXT_NAHORE:
                    fr.tb[i].setVerticalTextPosition(changeToTrue ? SwingConstants.BOTTOM : SwingConstants.TOP);
                    fr.jc[i].setVerticalTextPosition(changeToTrue ? SwingConstants.BOTTOM : SwingConstants.TOP);
                    fr.jr[i].setVerticalTextPosition(changeToTrue ? SwingConstants.BOTTOM : SwingConstants.TOP);
                    break;
                case TEXT_VPRAVO:
                    fr.tb[i].setHorizontalTextPosition(changeToTrue ? SwingConstants.RIGHT : SwingConstants.LEFT);
                    fr.jc[i].setHorizontalTextPosition(changeToTrue ? SwingConstants.RIGHT : SwingConstants.LEFT);
                    fr.jr[i].setHorizontalTextPosition(changeToTrue ? SwingConstants.RIGHT : SwingConstants.LEFT);
                    break;
                case KRESLIT_OBSAH:
                    fr.tb[i].setContentAreaFilled(changeToTrue);
                    fr.jc[i].setContentAreaFilled(changeToTrue);
                    fr.jr[i].setContentAreaFilled(changeToTrue);
                    break;
                case SESKUPIT:
                    if (changeToTrue) {
                        bg1.add(fr.tb[i]);
                        bg2.add(fr.jr[i]);
                    } else {
                        if (bg1.getButtonCount() > 0) {
                            bg1.remove(fr.tb[i]);
                            bg2.remove(fr.jr[i]);
                        }
                    }
                    break;
            }
        }
        this.fr.pack();
    }
}
