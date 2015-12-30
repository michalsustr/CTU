package ukazkaMoznosti;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import ukazkaMoznosti.syntaxHighLighting.*;

/**
 * Tlačítko, které otevře třídu(zadána v konstruktoru) se zdrojovým kódem
 * @author Miroslav Balík
 */
public class JButtonOtevriZdrojak extends JButton implements ActionListener {

    String jmenoTridy;

    public JButtonOtevriZdrojak(String jmTridy) {
        String fileSeparator = System.getProperty("file.separator");
     //   System.out.println(System.getProperty("user.dir"));
        jmenoTridy = System.getProperty("user.dir") + fileSeparator + 
                "src" + fileSeparator +"ukazkaMoznosti"+ fileSeparator+  jmTridy + ".java";
       // System.out.println("Sestaveny nazev tridy : " + jmenoTridy);
        this.setText("<html>Otevři zdroják:<br>" + jmTridy + "</html>");
        this.setToolTipText("<html>Otevře zdroják:<br>" + jmenoTridy + "</html>");
        this.setBackground(Color.GREEN);
        this.addActionListener(this); //poslouchej sám sebe
    }

    public void actionPerformed(ActionEvent e) {
        HighlightEdit.main(new String[]{jmenoTridy});
    }
}
