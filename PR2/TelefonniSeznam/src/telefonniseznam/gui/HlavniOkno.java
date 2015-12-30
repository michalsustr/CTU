package telefonniseznam.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import telefonniseznam.TelefonniSeznam;

public class HlavniOkno extends JFrame {

    protected JButton tlacitko;
    protected JLabel popisek;
    protected JTable tabulka;
    /* seznam implenetuje AbstractTableModel */
    protected TelefonniSeznam seznam = new TelefonniSeznam();

    public HlavniOkno() {
        setTitle("Telefonni seznam");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);
        setLayout(new BorderLayout());

        popisek = new JLabel("vyber radek z tabulky");
        popisek.setHorizontalAlignment(SwingConstants.CENTER);

        tabulka = new JTable(seznam);

        tabulka.setRowSelectionAllowed(true);
        ListSelectionModel model = tabulka.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int radek = tabulka.getSelectedRow();
                String text = "";

                for (int i = 0; i < tabulka.getColumnCount(); i++) {
                    text += tabulka.getValueAt(radek, i) + " ";
                }

                popisek.setText(text);
            }
        });

        /* pridani scroll baru do tabulky */
        JScrollPane scrollPanel = new JScrollPane(tabulka);

        tlacitko = new JButton("Pridej noveho uzivatele");
        /* do nove vytvoreneho okna se pres posluchac pomoci konstruktoru
         * predava referencni promenna na HlavniOkno */
        tlacitko.addActionListener(new Posluchac(this));

        add(popisek, BorderLayout.NORTH);
        add(scrollPanel, BorderLayout.CENTER);
        add(tlacitko, BorderLayout.SOUTH);
    }

    public void aktualizujTabulku() {
        /* aktualizace tabulky po pridani novych polozek */
        tabulka.updateUI();
    }

    public TelefonniSeznam getSeznam() {
        return seznam;
    }

    class Posluchac implements ActionListener {

        private HlavniOkno hl;

        public Posluchac(HlavniOkno hl) {
            this.hl = hl;
        }

        public void actionPerformed(ActionEvent e) {
            /* do nove vytvoreneho okna se pomoci konstruktoru predava
             * referencni promenna na HlavniOkno */
            new VytvorKontaktOkno(hl).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new HlavniOkno().setVisible(true);
    }
}
