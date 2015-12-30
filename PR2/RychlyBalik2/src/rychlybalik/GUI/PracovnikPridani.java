/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VuzPridani.java
 *
 * Created on 7.3.2011, 10:50:12
 */
package rychlybalik.GUI;


import java.awt.*;
import rychlybalik.Personalistika.*;
import rychlybalik.TypDopravy.VycetOpravneni;


/**
 *
 * @author Vojta
 */
public class PracovnikPridani extends javax.swing.JFrame {

    protected int stavbuttonu=3;
    VycetOpravneni licence;
    PraPracovnik k;
    /*HlavniOkno*/ RychlyBalik parent;

    /** Creates new form VuzPridani */
    public PracovnikPridani(/*HlavniOkno*/RychlyBalik neco1) {

        this.parent = neco1;
        setSize(300, 300);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //velikost okna
        setLocation(((d.width - getSize().width) / 2), ((d.height - getSize().height) / 2));
        
        initComponents();
        licenceB.setEnabled(false);
        licenceC.setEnabled(false);
        licenceKolo.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        radio_administrator = new javax.swing.JRadioButton();
        radio_vykonator = new javax.swing.JRadioButton();
        NazevTypPracovnika = new javax.swing.JTextField();
        nazevJmeno = new javax.swing.JTextField();
        zadaniJmena = new javax.swing.JTextField();
        nazevPrijmeni = new javax.swing.JTextField();
        zadaniPrijmeni = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        ridicaky = new javax.swing.JPanel();
        licenceB = new javax.swing.JRadioButton();
        licenceC = new javax.swing.JRadioButton();
        licenceKolo = new javax.swing.JRadioButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(145, 171, 210));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(145, 171, 210), 10));

        radio_administrator.setBackground(new java.awt.Color(145, 171, 210));
        buttonGroup1.add(radio_administrator);
        radio_administrator.setText("Administrativní");
        radio_administrator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_administratorActionPerformed(evt);
            }
        });

        radio_vykonator.setBackground(new java.awt.Color(145, 171, 210));
        buttonGroup1.add(radio_vykonator);
        radio_vykonator.setText("Výkonný");
        radio_vykonator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_vykonatorActionPerformed(evt);
            }
        });

        NazevTypPracovnika.setBackground(new java.awt.Color(145, 171, 210));
        NazevTypPracovnika.setEditable(false);
        NazevTypPracovnika.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        NazevTypPracovnika.setForeground(new java.awt.Color(255, 255, 255));
        NazevTypPracovnika.setText("Pracovník");
        NazevTypPracovnika.setBorder(null);

        nazevJmeno.setBackground(new java.awt.Color(145, 171, 210));
        nazevJmeno.setEditable(false);
        nazevJmeno.setForeground(new java.awt.Color(255, 255, 255));
        nazevJmeno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nazevJmeno.setText("Jméno");
        nazevJmeno.setBorder(null);

        zadaniJmena.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        nazevPrijmeni.setBackground(new java.awt.Color(145, 171, 210));
        nazevPrijmeni.setEditable(false);
        nazevPrijmeni.setForeground(new java.awt.Color(255, 255, 255));
        nazevPrijmeni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nazevPrijmeni.setText("Příjmení");
        nazevPrijmeni.setBorder(null);

        zadaniPrijmeni.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        jButton1.setBackground(new java.awt.Color(145, 171, 230));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Přidej");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ridicaky.setFocusable(false);
        ridicaky.setMaximumSize(new java.awt.Dimension(111, 110));
        ridicaky.setMinimumSize(new java.awt.Dimension(111, 110));

        buttonGroup2.add(licenceB);
        licenceB.setText("B / dodávka");
        licenceB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenceBActionPerformed(evt);
            }
        });

        buttonGroup2.add(licenceC);
        licenceC.setText("C / pick-up");
        licenceC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenceCActionPerformed(evt);
            }
        });

        buttonGroup2.add(licenceKolo);
        licenceKolo.setText("umí jet na kole");
        licenceKolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenceKoloActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setText("Řidičské oprávnění");
        jTextField1.setBorder(null);

        javax.swing.GroupLayout ridicakyLayout = new javax.swing.GroupLayout(ridicaky);
        ridicaky.setLayout(ridicakyLayout);
        ridicakyLayout.setHorizontalGroup(
            ridicakyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ridicakyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ridicakyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(licenceB)
                    .addComponent(licenceC)
                    .addComponent(licenceKolo)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ridicakyLayout.setVerticalGroup(
            ridicakyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ridicakyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(licenceB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(licenceC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(licenceKolo)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(NazevTypPracovnika, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(radio_administrator))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(radio_vykonator))
                    .addComponent(ridicaky, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(zadaniJmena, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nazevJmeno)
                        .addComponent(nazevPrijmeni, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(zadaniPrijmeni, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ridicaky, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(NazevTypPracovnika, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nazevJmeno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radio_administrator))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radio_vykonator)
                            .addComponent(zadaniJmena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(nazevPrijmeni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zadaniPrijmeni, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jButton1)))
                .addGap(23, 23, 23))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Smena smena = new Smena();
        smena = parent.getSezPrac();
        

        switch (stavbuttonu) {
            case 0:
                if(zadaniJmena.getText().isEmpty()==true || zadaniPrijmeni.getText().isEmpty()==true){
                    WarningVstup okynko = new WarningVstup(this, "Pracovník musí mít jméno \ni příjmení!!");
                    okynko.setVisible(true);
                    dispose();}else{


                k = new Administrator(zadaniJmena.getText(), zadaniPrijmeni.getText());
                    smena.pridejPracovnika(k);
                    parent.getOdeberPracovnika().setEnabled(true);
                    parent.getEditujPracovnika().setEnabled(true);}
                break;

            case 1:

                if(zadaniJmena.getText().isEmpty()==true || zadaniPrijmeni.getText().isEmpty()==true || (licenceB.isSelected()==false && licenceC.isSelected()==false && licenceKolo.isSelected()==false)){
                    WarningVstup okynko = new WarningVstup(this, "Pracovník musí mít jméno \ni příjmení a licenci!!");
                    okynko.setVisible(true);
                    dispose();}else{
                k = new Vykonator(zadaniJmena.getText(), zadaniPrijmeni.getText(), licence);
                    smena.pridejPracovnika(k);
                    parent.getOdeberPracovnika().setEnabled(true);
                    parent.getEditujPracovnika().setEnabled(true);}
                break;
            default:

                    WarningVstup okynko = new WarningVstup(this, "Nevybral jse typ pracovníka!!");
                    okynko.setVisible(true);
                    dispose();

                ;
        }

        
        parent.setSezPrac(smena);
        parent.refreshSeznamuPracovniku();
        
        dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void licenceBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenceBActionPerformed
        licence = VycetOpravneni.DODAVKA;
    }//GEN-LAST:event_licenceBActionPerformed

    private void licenceCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenceCActionPerformed
        licence = VycetOpravneni.PICK_UP;
    }//GEN-LAST:event_licenceCActionPerformed

    private void licenceKoloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenceKoloActionPerformed
        licence = VycetOpravneni.KOLO;
    }//GEN-LAST:event_licenceKoloActionPerformed

    private void radio_vykonatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_vykonatorActionPerformed
        licenceB.setEnabled(true);
        licenceC.setEnabled(true);
        licenceKolo.setEnabled(true);
        //ridicaky.setVisible(true);

        stavbuttonu = 1;
}//GEN-LAST:event_radio_vykonatorActionPerformed

    private void radio_administratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_administratorActionPerformed
        licenceB.setEnabled(false);
        licenceC.setEnabled(false);
        licenceKolo.setEnabled(false);
        stavbuttonu = 0;
}//GEN-LAST:event_radio_administratorActionPerformed
    /**
     * @param args the command line arguments
     */
    /* public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
    new VuzPridani(vozPark).setVisible(true);
    }
    });
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField NazevTypPracovnika;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JRadioButton licenceB;
    private javax.swing.JRadioButton licenceC;
    private javax.swing.JRadioButton licenceKolo;
    private javax.swing.JTextField nazevJmeno;
    private javax.swing.JTextField nazevPrijmeni;
    private javax.swing.JRadioButton radio_administrator;
    private javax.swing.JRadioButton radio_vykonator;
    private javax.swing.JPanel ridicaky;
    private javax.swing.JTextField zadaniJmena;
    private javax.swing.JTextField zadaniPrijmeni;
    // End of variables declaration//GEN-END:variables
}
