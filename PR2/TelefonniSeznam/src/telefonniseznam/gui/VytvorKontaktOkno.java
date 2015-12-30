package telefonniseznam.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import telefonniseznam.Kontakt;

/**
 * potomek JDialogu => okno je mozne nastavit jako modalni
 */
public class VytvorKontaktOkno extends JDialog implements ActionListener {

    /* ref. promenna na HlavniOkno */
    protected HlavniOkno hl;
    protected JLabel jmenoLabel, prijmeniLabel, cisloLabel;
    protected JTextField jmenoField, prijmeniField, cisloField;
    protected JButton ulozButton;

    public VytvorKontaktOkno(HlavniOkno hl) {
        this.hl = hl;
        /* modalni okno = okno v popredi pred vsemi okny */
        setModal(true);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(jmenoLabel = new JLabel("Jmeno: "));
        panel.add(jmenoField = new JTextField());
        panel.add(prijmeniLabel = new JLabel("Prijmeni: "));
        panel.add(prijmeniField = new JTextField());
        panel.add(cisloLabel = new JLabel("Cislo: "));
        panel.add(cisloField = new JTextField());
        add(panel, BorderLayout.CENTER);

        ulozButton = new JButton("Ulozit kontakt");
        ulozButton.addActionListener(this);
        add(ulozButton, BorderLayout.SOUTH);

        /* nastaveni minimalni velikost okna zobrazujici vsechny komponenty */
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        Kontakt k = new Kontakt(
                jmenoField.getText(),
                prijmeniField.getText(),
                cisloField.getText());

        hl.getSeznam().pridejKontakt(k);
        hl.aktualizujTabulku();
        /* zruseni okna */
        dispose();
    }
}
