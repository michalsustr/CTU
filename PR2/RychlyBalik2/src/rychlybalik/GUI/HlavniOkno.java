/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HlavniOkno.java
 *
 * Created on 6.3.2011, 14:55:08
 */

package rychlybalik.GUI;

import javax.swing.*;

import rychlybalik.Personalistika.Smena;
import rychlybalik.VozovyPark.VozovyPark;
import rychlybalik.Zasilka.SeznamZasilek;

/**
 *
 * @author Vojta
 */
public class HlavniOkno extends JFrame {



  protected VozovyPark vozPark;
  protected Smena sezPrac;
  protected SeznamZasilek sezZas;
    public HlavniOkno(VozovyPark neco,Smena neco2,SeznamZasilek neco3) {
        this.vozPark=neco;
        this.sezPrac=neco2;
        this.sezZas=neco3;
        setSize(200,200);
        initComponents();
    }

    public void setVozPark(VozovyPark vozPark) {
        this.vozPark = vozPark;
    }

    public VozovyPark getVozPark() {
        return vozPark;
    }

    public Smena getSezPrac() {
        return sezPrac;
    }

    public void setSezPrac(Smena sezPrac) {
        this.sezPrac = sezPrac;
    }




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton7 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        SeznamVozu = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        SeznamPracovniku = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        SeznamZasilek = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        PridejPracovnika = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        jButton7.setText("jButton7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 400));

        SeznamVozu.setModel(vozPark);
        jScrollPane5.setViewportView(SeznamVozu);

        SeznamPracovniku.setModel(sezPrac);
        jScrollPane1.setViewportView(SeznamPracovniku);

        SeznamZasilek.setModel(sezZas);
        jScrollPane2.setViewportView(SeznamZasilek);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Oddělení dopravy"));
        jPanel3.setAlignmentX(50.0F);
        jPanel3.setAlignmentY(50.0F);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Přidej prostředek");
        jButton1.setMaximumSize(new java.awt.Dimension(20, 20));
        jButton1.setMinimumSize(new java.awt.Dimension(20, 20));
        jButton1.setPreferredSize(new java.awt.Dimension(20, 20));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PridejProstredek(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 150, 30));

        jButton2.setText("Odeber prostředek");
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 150, 30));

        jButton3.setText("Edituj Prostředek");
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 150, 30));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Personální oddělení"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PridejPracovnika.setText("Přidej pracovníka");
        PridejPracovnika.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PridejPracovnikaActionPerformed(evt);
            }
        });
        jPanel1.add(PridejPracovnika, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 160, 30));

        jButton5.setText("Odeber pracovníka");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 160, 30));

        jButton6.setText("Edituj pracovníka");
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 160, 30));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Expediční oddělení"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.0d, 4000.0d, 2.0d));
        jSpinner1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 100, 40));

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(4000.0f), Float.valueOf(1.0f)));
        jSpinner2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(jSpinner2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 100, 40));

        jTextPane1.setBorder(null);
        jScrollPane3.setViewportView(jTextPane1);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, 120));

        jTextField1.setBackground(new java.awt.Color(145, 171, 210));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("hmotnost(kg)");
        jTextField1.setBorder(null);
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 100, 20));

        jTextField2.setBackground(new java.awt.Color(145, 171, 210));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("vzdálenost(km)");
        jTextField2.setBorder(null);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 100, 20));

        jButton8.setText("Edituj");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 90, 30));

        jButton9.setText("Výpočet ceny");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 200, 30));

        jButton10.setBackground(new java.awt.Color(145, 171, 230));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 11));
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Expedice zásilky");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 200, 30));

        jButton11.setText("Odeber");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void PridejProstredek(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PridejProstredek
//VuzPridani okynko=new VuzPridani(this);
//okynko.setVisible(true);
//okynko.setTitle("NOVÝ PROSTŘEDEK");

    }//GEN-LAST:event_PridejProstredek

    private void PridejPracovnikaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PridejPracovnikaActionPerformed
//PracovnikPridani okynko=new PracovnikPridani(this);
//okynko.setVisible(true);
//okynko.setTitle("NOVÝ PRACOVNÍK");
    }//GEN-LAST:event_PridejPracovnikaActionPerformed

   
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new HlavniOkno().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PridejPracovnika;
    private javax.swing.JTable SeznamPracovniku;
    private javax.swing.JTable SeznamVozu;
    private javax.swing.JTable SeznamZasilek;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

    

  public void refreshVozovehoParku(){
      SeznamVozu.updateUI();
  }
   public void refreshSeznamuPracovniku(){
      SeznamPracovniku.updateUI();
  }
}
