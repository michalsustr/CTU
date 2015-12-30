
/*
 * VuzPridani.java
 *
 * Created on 7.3.2011, 10:50:12
 */
package rychlybalik.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import rychlybalik.TypDopravy.*;
import rychlybalik.VozovyPark.*;

/**
 *
 * @author Vojta
 */
public class VuzEdit extends javax.swing.JFrame {

    protected double nosnost;
    protected double rychlost;
    protected int stavbuttonu = 5;
    protected SpolecneAtributy k;
    protected SpolecneAtributy editVuz;
    protected VozovyPark park;
    RychlyBalik parent;

    public VuzEdit(RychlyBalik neco1) {
        this.parent = neco1;
        setSize(200, 100);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //velikost okna
        setLocation(((d.width - getSize().width) / 2), ((d.height - getSize().height) / 2));
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {





        jPanel1 = new javax.swing.JPanel();
        NazevNosnost = new javax.swing.JTextField();
        zadaniNosnosti = new javax.swing.JTextField();
        NazevRychlost = new javax.swing.JTextField();
        zadaniRychlosti = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(145, 171, 210));
        jPanel1.setSize(300, 100);

        NazevNosnost.setBackground(new java.awt.Color(145, 171, 210));
        NazevNosnost.setEditable(false);
        NazevNosnost.setForeground(new java.awt.Color(255, 255, 255));
        NazevNosnost.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NazevNosnost.setText("Nosnost");
        NazevNosnost.setBorder(null);
   

        zadaniNosnosti.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        NazevRychlost.setBackground(new java.awt.Color(145, 171, 210));
        NazevRychlost.setEditable(false);
        NazevRychlost.setForeground(new java.awt.Color(255, 255, 255));
        NazevRychlost.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NazevRychlost.setText("Rychlost");
        NazevRychlost.setBorder(null);

        zadaniRychlosti.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        jButton1.setBackground(new java.awt.Color(145, 171, 230));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ulož změny");

        jButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(zadaniNosnosti, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(NazevNosnost, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(zadaniRychlosti, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(NazevRychlost, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))).addGroup(jPanel1Layout.createSequentialGroup().addGap(55, 55, 55).addComponent(jButton1))).addContainerGap(0,0 )));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(10, 10, 10).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(NazevRychlost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(NazevNosnost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(zadaniRychlosti, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(zadaniNosnosti, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton1).addGap(10, 10, 10)));

        getContentPane().add(jPanel1);

        editVuz = parent.getEditovanyVuz();
        zadaniRychlosti.setText("" + editVuz.getRychlost());
            zadaniNosnosti.setText("" + editVuz.getNosnost());
        if (editVuz instanceof DodavkaVelka == true) {
            stavbuttonu = 0;
            
            zadaniRychlosti.setBackground(Color.gray);
            zadaniNosnosti.setBackground(Color.gray);
            zadaniRychlosti.disable();
            zadaniNosnosti.disable();
        } else {
            if (editVuz instanceof PickUp == true) {
                stavbuttonu = 1;
                zadaniRychlosti.setBackground(Color.gray);
                zadaniRychlosti.disable();
            } else {
                if (editVuz instanceof Kolo == true) {
                    stavbuttonu = 2;
                } else {
                    stavbuttonu = 3;
                    zadaniRychlosti.setBackground(Color.gray);
                    zadaniRychlosti.disable();
                }
            }
        }
        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        switch (stavbuttonu) {
            case 0:
                break;

            case 1:

                try {
                    Double.valueOf(zadaniNosnosti.getText());
                } catch (java.lang.NumberFormatException e) {
                    WarningVstup okynko = new WarningVstup(this, "Nosnost musí být větší než 0,\nmaximální nosnost je 2500 (kg)!");
                    okynko.setVisible(true);
                    dispose();
                }
                nosnost = Double.valueOf(zadaniNosnosti.getText());
                if (nosnost <= 2500 && nosnost > 0) {

                    editVuz.setNosnost(nosnost);
                    parent.vozPark.getSeznamVozu().set(parent.getIndex(), editVuz);
                    
                } else {
                    WarningVstup okynko = new WarningVstup(this, "Nosnost musí být větší než 0,\nmaximální nosnost je 2500 (kg)!");
                    okynko.setVisible(true);
                    dispose();
                }


                break;//
            case 2:

                try {
                    Double.valueOf(zadaniNosnosti.getText());
                    Double.valueOf(zadaniRychlosti.getText());
                } catch (java.lang.NumberFormatException e) {
                    WarningVstup okynko = new WarningVstup(this, "Nosnost musí být z interv. (0,10>.\nRychlost musí být z inter. (0,100>.");
                    okynko.setVisible(true);
                    dispose();
                }

                nosnost = Double.valueOf(zadaniNosnosti.getText());
                rychlost = Double.valueOf(zadaniRychlosti.getText());
                if ((nosnost <= 10 && nosnost > 0) && (rychlost <= 100 && rychlost > 0)) {

                    editVuz.setNosnost(nosnost);
                    editVuz.setRychlost(rychlost);
                    parent.vozPark.getSeznamVozu().set(parent.getIndex(), editVuz);
                } else {
                    WarningVstup okynko = new WarningVstup(this, "Nosnost musí být z interv. (0,10>.\nRychlost musí být z inter. (0,100>.");
                    okynko.setVisible(true);
                    dispose();
                }



                break;
            case 3:
                try {
                    Double.valueOf(zadaniNosnosti.getText());
                } catch (java.lang.NumberFormatException e) {
                    WarningVstup okynko = new WarningVstup(this, "Nosnost musí být větší než 0,\nmaximální nosnost je 15 (kg)!");
                    okynko.setVisible(true);
                    dispose();
                }
                nosnost = Double.valueOf(zadaniNosnosti.getText());
                if (nosnost <= 15 && nosnost > 0) {


                    editVuz.setNosnost(nosnost);
                    parent.vozPark.getSeznamVozu().set(parent.getIndex(), editVuz);
                } else {
                    WarningVstup okynko = new WarningVstup(this, "Nosnost musí být větší než 0,\nmaximální nosnost je 15 (kg)!");
                    okynko.setVisible(true);
                    dispose();
                }
                break;
            default:
                dispose();
                break;//
        }

        parent.refreshVozovehoParku();
        dispose();

    }

    private JTextField NazevNosnost;
    private JTextField NazevRychlost;
    private JButton jButton1;
    private JPanel jPanel1;
    private JTextField zadaniNosnosti;
    private JTextField zadaniRychlosti;
    
}
