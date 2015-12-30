/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import rychlybalik.*;
import rychlybalik.vehicle.*;

/**
 *
 * @author michal
 */
public class MainWindow extends JFrame {
	private static MainWindow win;
	public static void main(String[] args) {
		win = new MainWindow();
		win.setVisible(true);
	}







	private JTable vehicleTable, employeeTable, expeditionTable;
	private VehicleList vehicleList;
	private EmployeeList employeeList;
	private ShippingList shippingList;

	private JButton vehAdd, empAdd, expAdd, vehEdit, empEdit, expEdit, vehDel, empDel, expDel;
	public MainWindow() {
        setTitle("Rychlý Balík");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);

		setLayout(new GridLayout(1, 3));

		JPanel west = new JPanel();
		west.setLayout(new BorderLayout());
		west.add(new JLabel("Vozidlá"), BorderLayout.NORTH);

		vehicleList = new VehicleList();
		vehicleTable = new JTable(vehicleList);
		vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vehicleTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				vehEdit.setEnabled(!lsm.isSelectionEmpty());
				vehDel.setEnabled(!lsm.isSelectionEmpty());
				expAdd.setEnabled(!lsm.isSelectionEmpty() && !employeeTable.getSelectionModel().isSelectionEmpty());
			}
		});
		JScrollPane vehiclePanel = new JScrollPane(vehicleTable);
		west.add(vehiclePanel, BorderLayout.CENTER);

		JPanel westBottom = new JPanel();
		westBottom.setLayout(new FlowLayout());
		vehAdd = new JButton("Pridať");
		vehAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VehicleWindow().setVisible(true);
			}
		});
		westBottom.add(vehAdd);

		vehEdit = new JButton("Editovať");
		vehEdit.setEnabled(false);
		vehEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehicleWindow vw = new VehicleWindow();
				int atRow = vehicleTable.getSelectedRow();
				Vehicle v = vehicleList.getVehicles().get(atRow);
				vw.setVehicle(v);
				vw.setEditing(atRow);
				vw.setVisible(true);

			}
		});
		westBottom.add(vehEdit);

		vehDel = new JButton("Odobrať");
		vehDel.setEnabled(false);
		vehDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int atRow = vehicleTable.getSelectedRow();
				vehicleList.getVehicles().remove(atRow);
				vehicleTable.updateUI();
			}
		});
		westBottom.add(vehDel);

		west.add(westBottom, BorderLayout.SOUTH);
		add(west);

		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		center.add(new JLabel("Zaměstnanci"), BorderLayout.NORTH);
		
		employeeList = new EmployeeList();
		employeeTable = new JTable(employeeList);
		employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				empEdit.setEnabled(!lsm.isSelectionEmpty());
				empDel.setEnabled(!lsm.isSelectionEmpty());
				expAdd.setEnabled(!lsm.isSelectionEmpty() && !vehicleTable.getSelectionModel().isSelectionEmpty());
			}
		});
		JScrollPane employeePanel = new JScrollPane(employeeTable);
		
		center.add(employeePanel, BorderLayout.CENTER);

		JPanel centerBottom = new JPanel();
		centerBottom.setLayout(new FlowLayout());
		empAdd = new JButton("Pridať");
		empAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EmployeeWindow().setVisible(true);
			}
		});
		centerBottom.add(empAdd);


		empEdit = new JButton("Editovať");
		empEdit.setEnabled(false);
		empEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeWindow ew = new EmployeeWindow();
				int atRow = employeeTable.getSelectedRow();
				Employee emp = employeeList.getEmployees().get(atRow);
				ew.setEmployee(emp);
				ew.setEditing(atRow);
				ew.setVisible(true);

			}
		});
		centerBottom.add(empEdit);


		empDel = new JButton("Odobrať");
		empDel.setEnabled(false);
		empDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int atRow = employeeTable.getSelectedRow();
				employeeList.getEmployees().remove(atRow);
				employeeTable.updateUI();
			}
		});
		centerBottom.add(empDel);

		center.add(centerBottom, BorderLayout.SOUTH);
		add(center);

		JPanel east = new JPanel();
		east.setLayout(new BorderLayout());
		east.add(new JLabel("Expedované zásilky"), BorderLayout.NORTH);
		
		shippingList = new ShippingList();
		Expedition exp = new Expedition();
		exp.setEmployee(employeeList.getEmployees().get(1));
		exp.setVehicle(vehicleList.getVehicles().get(1));
		exp.setPkg(new rychlybalik.Package(100, 40));
		exp.setOnRoadFrom(new Date(new Date().getTime()-3590*1000));
		shippingList.getExpeditions().add(exp);

		expeditionTable = new JTable(shippingList);
		expeditionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		expeditionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				expEdit.setEnabled(!lsm.isSelectionEmpty());
				expDel.setEnabled(!lsm.isSelectionEmpty());
			}
		});
		new Thread(new Runnable() {
			public void run() {
				while(true)
				try {
					expeditionTable.updateUI();
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}).start();
		JScrollPane expeditionPanel = new JScrollPane(expeditionTable);
		east.add(expeditionPanel, BorderLayout.CENTER);

		JPanel eastBottom = new JPanel(new FlowLayout());
		
		expAdd = new JButton("Pridať");
		expAdd.setEnabled(false);
		expAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// find if this combination emp+veh is possible
				Employee emp = employeeList.getEmployees().get(employeeTable.getSelectedRow());
				Vehicle veh =  vehicleList.getVehicles().get(vehicleTable.getSelectedRow());
				if(!veh.canBeDrivenBy(emp)) {
					JOptionPane.showMessageDialog(win, "Zamestnanec nedokáže riadiť dané vozidlo");
				} else {
					new ExpeditionWindow(emp, veh).setVisible(true);
				}
				
			}
		});
		eastBottom.add(expAdd);

		

		expEdit = new JButton("Editovať");
		expEdit.setEnabled(false);
		expEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int atRow = expeditionTable.getSelectedRow();
				Expedition exp = shippingList.getExpeditions().get(atRow);
				ExpeditionWindow ew = new ExpeditionWindow(exp.getEmployee(), exp.getVehicle());
				ew.setPackage(exp.getPkg());
				ew.setEditing(atRow);
				ew.setVisible(true);

			}
		});
		eastBottom.add(expEdit);

		
		

		expDel = new JButton("Odobrať");
		expDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int atRow = expeditionTable.getSelectedRow();
				shippingList.getExpeditions().remove(atRow);
				expeditionTable.updateUI();
			}
		});
		expDel.setEnabled(false);
		eastBottom.add(expDel);

		east.add(eastBottom, BorderLayout.SOUTH);
		add(east);
    }

	public static void addVehicle(Vehicle v) {
		win.vehicleList.getVehicles().add(v);
		win.vehicleTable.updateUI();
	}

	public static void editVehicle(int row, Vehicle v) {
		win.vehicleList.getVehicles().set(row, v);
		win.vehicleTable.updateUI();
	}

	public static void addEmployee(Employee emp) {
		win.employeeList.getEmployees().add(emp);
		win.employeeTable.updateUI();
	}

	public static void editEmployee(Integer row, Employee emp) {
		win.employeeList.getEmployees().set(row, emp);
		win.employeeTable.updateUI();
	}

	public static void addExpedition(Expedition exp) {
		win.shippingList.getExpeditions().add(exp);
		win.expeditionTable.updateUI();
	}

	public static void editExpedition(Integer row, Expedition exp) {
		win.shippingList.getExpeditions().set(row, exp);
		win.expeditionTable.updateUI();
	}
}
