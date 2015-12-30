/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExpeditionWindow.java
 *
 * Created on Mar 29, 2011, 8:29:22 PM
 */

package rychlybalik.gui;

import java.util.Date;
import javax.swing.SpinnerNumberModel;
import rychlybalik.Employee;
import rychlybalik.Expedition;
import rychlybalik.vehicle.Vehicle;
import rychlybalik.Package;

/**
 *
 * @author michal
 */
public class ExpeditionWindow extends javax.swing.JFrame {
	private Vehicle veh;
	private Employee emp;

    /** Creates new form ExpeditionWindow */
    public ExpeditionWindow(Employee emp, Vehicle veh) {
        initComponents();
		this.emp = emp;
		this.veh = veh;
		employee.setText(emp.toString());
		vehicle.setText(veh.toString());

		// set maximum payload
		payload.setModel(new SpinnerNumberModel(0, 0, (int) veh.getPayload(), 1));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        payload = new javax.swing.JSpinner();
        distance = new javax.swing.JSpinner();
        calculatePrice = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        employee = new javax.swing.JTextField();
        vehicle = new javax.swing.JTextField();
        price = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vytvorenie zásielky");

        jLabel1.setText("Hmotnosť");

        jLabel2.setText("Vzdialenosť");

        calculatePrice.setText("Prepočítaj cenu!");
        calculatePrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatePriceActionPerformed(evt);
            }
        });

        submitButton.setText("Pridať zásielku");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Zamestnanec");

        jLabel4.setText("Vozidlo");

        employee.setEditable(false);

        vehicle.setEditable(false);

        price.setText("Cena: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(employee)
                            .addComponent(vehicle)
                            .addComponent(payload)
                            .addComponent(distance, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(submitButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(calculatePrice)))
                    .addComponent(price))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(employee, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(payload, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(distance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(calculatePrice)
                .addGap(14, 14, 14)
                .addComponent(price)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(submitButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void calculatePriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatePriceActionPerformed
		Package pa = new Package((Integer) payload.getValue(), (Integer) distance.getValue());
		price.setText("Cena:"+veh.getPrice(pa));
	}//GEN-LAST:event_calculatePriceActionPerformed

	private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
		Expedition exp = new Expedition();
		exp.setVehicle(veh);
		exp.setEmployee(emp);
		exp.setPkg(new Package((Integer) payload.getValue(), (Integer) distance.getValue()));
		exp.setOnRoadFrom(new Date());

		if(editingRow == null) {
			MainWindow.addExpedition(exp);
		} else {
			MainWindow.editExpedition(editingRow, exp);
		}
		dispose();
	}//GEN-LAST:event_submitButtonActionPerformed

	private Integer editingRow;
	void setEditing(int atRow) {
		editingRow = atRow;
		submitButton.setText("Zmeniť");
	}

	void setPackage(Package pkg) {
		payload.setValue((int) pkg.getMass());
		distance.setValue((int) pkg.getDistance());
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculatePrice;
    private javax.swing.JSpinner distance;
    private javax.swing.JTextField employee;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSpinner payload;
    private javax.swing.JLabel price;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField vehicle;
    // End of variables declaration//GEN-END:variables

}