package ukazkaMoznosti;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

enum Dialogy {

    OK("OK dialog"),
    YES_NO("Ano / Ne dialog"),
    SVOJE_TLACITKA("vlastní výběr"),
    MULTICHOISE("výběr z více možností"),
    INPUT("vstupní dialog");

    Dialogy(String n) {
        name = n;
    }
    private String name;

    public String getName() {
        return name;
    }
}

class Obsluha implements ActionListener {

    Dialogy dialog;
    JFrameDialogy frame;

    public Obsluha(Dialogy dialog, JFrameDialogy jfr) {
        this.dialog = dialog;
        frame = jfr;
    }

    public void actionPerformed(ActionEvent e) {
        switch (dialog) {
            case OK:
                JOptionPane.showMessageDialog(frame,
                        "Programování dialogů je snadné a zábavné",
                        "showMessageDialog", frame.messageType);
                frame.status.setText("Informační zpráva.");
                break;
            case YES_NO:
                int volba = JOptionPane.showConfirmDialog(
                        frame, "Máte rádi zvířata?",
                        "showConfirmDialog",
                        JOptionPane.YES_NO_OPTION);
                if (volba == JOptionPane.YES_OPTION) {
                    frame.status.setText("Jenom na pekáči? Opravdu?");
                } else if (volba == JOptionPane.NO_OPTION) {
                    frame.status.setText("Opravdu je nemáte rádi?");
                } else {
                    frame.status.setText("Proč nechceš odpovědět?");
                }
                break;
            case SVOJE_TLACITKA:
                Object[] options = {"slony", "kapry","blechy"};
                    volba = JOptionPane.showOptionDialog(frame,
                                    "Jaké zvířátka doma chováš?",
                                    "showOptionDialog",
                                    JOptionPane.NO_OPTION,
                                    frame.messageType,
                                    null,
                                    options,
                                    options[0]);
                    if (volba == 0) {
                    frame.status.setText("Pro slony musíš mít hodně místa.");
                    } else if (volba == 1) {
                    frame.status.setText("Nemáte doma mokro?");
                    } else if(volba == 2){
                    frame.status.setText("Dobrá volba. Začni je trénovat a můžeš mít bleší cirkus.");
                    }else{
                    frame.status.setText("To jsou ale blbiny. Co?");
                    }
                break;
            case MULTICHOISE:
                String moznosti[] = {"Java", "C", "C++", "PHP", "Čeština"};
                String odpoved = (String) JOptionPane.showInputDialog(frame,
                        "Vyber svůj neoblíbenější jazyk",
                        "showInputDialog", frame.messageType, null, moznosti, moznosti[0]);
                if(odpoved==null){
                    frame.status.setText("<html><b>Zapomněl sis vybrat.</b></html>");
                }else if (odpoved.equals("Java")) {
                    frame.status.setText("<html><b>Java </b>je dobrá volba.</html>");
                } else {
                    frame.status.setText("Tvoje volba je " + odpoved);
                }
                break;
            case INPUT:
                String s = (String) JOptionPane.showInputDialog(
                        frame,
                        "Doplň zbytek věty:\n" + "\"GUI v Javě může být \"",
                        "showInputDialog",
                        frame.messageType,
                        null,
                        null,
                        "zábavné");

                if ((s != null) && (s.length() > 0)) {
                    frame.status.setText("GUI v Javě může být " + s + "!");
                    return;
                }
        }
        frame.pack();
    }
}

/**
 *
 * @author Miroslav Balík
 */
public class JFrameDialogy extends JFrame implements ItemListener {

    JRadioButton jrPlain = new JRadioButton("Plain", true);
    JRadioButton jrInfo = new JRadioButton("Informace", new javax.swing.ImageIcon(getClass().
            getResource("/ukazkaMoznosti/ikony/metal-info.png")));
    JRadioButton jrWarning = new JRadioButton("Varování", new javax.swing.ImageIcon(getClass().
            getResource("/ukazkaMoznosti/ikony/metal-warning.png")));
    JRadioButton jrQuestion = new JRadioButton("Otázka", new javax.swing.ImageIcon(getClass().
            getResource("/ukazkaMoznosti/ikony/metal-question.png")));
    JRadioButton jrError = new JRadioButton("Chyba", new javax.swing.ImageIcon(getClass().
            getResource("/ukazkaMoznosti/ikony/metal-error.png")));
    JLabel status = new JLabel("Zde se budou zobrazvat návratové hodnoty");
    public int messageType = JOptionPane.INFORMATION_MESSAGE;

    public JFrameDialogy() {
        Dialogy dialogy[] = Dialogy.values();//načtu svoje dialogy
        JPanel tlacitka = new JPanel();
        JPanel radios = new JPanel();
        JPanel stav = new JPanel();
        stav.add(status);
        Container kam = getContentPane();

        tlacitka.setLayout(new GridLayout(dialogy.length, 0, 0, 5));
        for (int i = 0; i < dialogy.length; i++) {
            JButton jb = new JButton("Zobraz " + dialogy[i].getName());
            jb.addActionListener(new Obsluha(dialogy[i], this));
            tlacitka.add(jb);

        }
        kam.setLayout(new GridLayout(3, 0, 2, 2));



        radios.add(jrPlain);
        radios.add(jrInfo);
        radios.add(jrWarning);
        radios.add(jrQuestion);
        radios.add(jrError);

        ButtonGroup bg = new ButtonGroup();
        bg.add(jrPlain);
        bg.add(jrInfo);
        bg.add(jrWarning);
        bg.add(jrQuestion);
        bg.add(jrError);

        jrPlain.addItemListener(this);
        jrInfo.addItemListener(this);
        jrWarning.addItemListener(this);
        jrQuestion.addItemListener(this);
        jrError.addItemListener(this);


        kam.add(tlacitka);
        kam.add(radios);
        kam.add(stav);
        status.setFont(new Font("Dialog", Font.PLAIN, 32));
        stav.add(status);
        JButtonOtevriZdrojak jboz = new JButtonOtevriZdrojak(this.getClass().getSimpleName());
        stav.add(jboz);




    }

    public void itemStateChanged(ItemEvent e) {
        if (jrPlain.isSelected()) {
            messageType = JOptionPane.PLAIN_MESSAGE;
        } else if (jrInfo.isSelected()) {
            messageType = JOptionPane.INFORMATION_MESSAGE;
        } else if (jrWarning.isSelected()) {
            messageType = JOptionPane.WARNING_MESSAGE;
        } else if (jrQuestion.isSelected()) {
            messageType = JOptionPane.QUESTION_MESSAGE;
        } else if (jrError.isSelected()) {
            messageType = JOptionPane.ERROR_MESSAGE;
        }
    }
}
