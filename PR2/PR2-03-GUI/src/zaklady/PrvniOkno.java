package zaklady;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PrvniOkno extends JFrame implements ActionListener{
    JButton tlacitko = new JButton("Konec");

    public PrvniOkno(){
        this.getContentPane().add(tlacitko);
        tlacitko.addActionListener(this);
    }

    public static void main(String[] args) {
        PrvniOkno po = new PrvniOkno();
        po.pack();
        po.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch (JOptionPane.showConfirmDialog(this, "Opravdu chcete ukoncit program?",
                "Ukoncovaci dialog",JOptionPane.WARNING_MESSAGE)){
            case JOptionPane.OK_OPTION:
                //ukonci program
                System.exit(0);break;
            case JOptionPane.CANCEL_OPTION:
                //rozmyslel si to, nedelej nic.
        }
    }

}
