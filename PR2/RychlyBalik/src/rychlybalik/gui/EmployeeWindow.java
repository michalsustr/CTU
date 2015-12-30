/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmployeeWindow.java
 *
 * Created on Mar 29, 2011, 7:21:40 PM
 */

package rychlybalik.gui;

import rychlybalik.DrivingSkill;
import rychlybalik.Employee;
import rychlybalik.JobPosition;

/**
 *
 * @author michal
 */
public class EmployeeWindow extends javax.swing.JFrame {

    /** Creates new form EmployeeWindow */
    public EmployeeWindow() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        position = new javax.swing.ButtonGroup();
        driving = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        adminButton = new javax.swing.JRadioButton();
        executiveButton = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        drivingB = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        drivingC = new javax.swing.JRadioButton();
        drivingBicycle = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        surname = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nový zamestnanec");

        jLabel1.setText("Pracovník");

        position.add(adminButton);
        adminButton.setSelected(true);
        adminButton.setText("Administratívny");
        adminButton.setName("admin"); // NOI18N
        adminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminButtonActionPerformed(evt);
            }
        });

        position.add(executiveButton);
        executiveButton.setText("Výkonný");
        executiveButton.setName("executive"); // NOI18N
        executiveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executiveButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(197, 197, 197));

        driving.add(drivingB);
        drivingB.setText("B / dodávka");
        drivingB.setEnabled(false);
        drivingB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drivingBActionPerformed(evt);
            }
        });

        jLabel2.setText("Vodičské oprávnenie");

        driving.add(drivingC);
        drivingC.setText("C / pickup");
        drivingC.setEnabled(false);

        driving.add(drivingBicycle);
        drivingBicycle.setText("vie jazdiť na bicykli");
        drivingBicycle.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(drivingB)
                    .addComponent(drivingC)
                    .addComponent(drivingBicycle)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(drivingB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drivingC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drivingBicycle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Meno");

        jLabel4.setText("Priezvisko");

        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        surname.setName("surname"); // NOI18N
        surname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                surnameActionPerformed(evt);
            }
        });

        submitButton.setText("Pridať");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adminButton)
                            .addComponent(executiveButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(surname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(adminButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(executiveButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submitButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void drivingBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drivingBActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_drivingBActionPerformed

	private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_nameActionPerformed

	private void surnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_surnameActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_surnameActionPerformed

	private Integer editingRow;
	void setEditing(int atRow) {
		editingRow = atRow;
		submitButton.setText("Zmeniť");
	}

	void setEmployee(Employee emp) {
		name.setText(emp.getName());
		surname.setText(emp.getSurname());
		if(emp.getPosition() == JobPosition.ADMINISTRATOR) {
			adminButton.setSelected(true);
		} else {
			executiveButton.setSelected(true);
			drivingB.setEnabled(true);
			drivingC.setEnabled(true);
			drivingBicycle.setEnabled(true);
		}

		switch(emp.getSkill()) {
			case DRIVING_BIKE:
				drivingBicycle.setSelected(true);
				break;
			case DRIVING_LICENSE_B:
				drivingB.setSelected(true);
				break;
			case DRIVING_LICENSE_C:
				drivingC.setSelected(true);
				break;
		}
	}

	private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
		Employee emp = new Employee();
		emp.setName(name.getText());
		emp.setSurname(surname.getText());
		emp.setPosition( adminButton.isSelected() ? JobPosition.ADMINISTRATOR : JobPosition.EXECUTIVE );
		if(executiveButton.isSelected()) {
			if(drivingB.isSelected()) emp.setSkill(DrivingSkill.DRIVING_LICENSE_B);
			else if(drivingC.isSelected()) emp.setSkill(DrivingSkill.DRIVING_LICENSE_C);
			else if(drivingBicycle.isSelected()) emp.setSkill(DrivingSkill.DRIVING_BIKE);
		}

		if(editingRow == null) {
			MainWindow.addEmployee(emp);
		} else {
			MainWindow.editEmployee(editingRow, emp);
		}
		dispose();
	}//GEN-LAST:event_submitButtonActionPerformed

	private void executiveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executiveButtonActionPerformed
		drivingB.setEnabled(true);
		drivingC.setEnabled(true);
		drivingBicycle.setEnabled(true);
	}//GEN-LAST:event_executiveButtonActionPerformed

	private void adminButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminButtonActionPerformed
		drivingB.setEnabled(false);
		drivingC.setEnabled(false);
		drivingBicycle.setEnabled(false);
	}//GEN-LAST:event_adminButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton adminButton;
    private javax.swing.ButtonGroup driving;
    private javax.swing.JRadioButton drivingB;
    private javax.swing.JRadioButton drivingBicycle;
    private javax.swing.JRadioButton drivingC;
    private javax.swing.JRadioButton executiveButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField name;
    private javax.swing.ButtonGroup position;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField surname;
    // End of variables declaration//GEN-END:variables



}
