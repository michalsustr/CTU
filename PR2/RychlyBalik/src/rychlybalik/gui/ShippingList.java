/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.gui;

import rychlybalik.gui.VehicleList;
import rychlybalik.gui.EmployeeList;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.table.AbstractTableModel;
import rychlybalik.DrivingSkill;
import rychlybalik.Employee;
import rychlybalik.Expedition;
import rychlybalik.vehicle.Vehicle;

/**
 *
 * @author michal
 */
public class ShippingList extends AbstractTableModel {
	protected ArrayList<Expedition> onRoad;
	private String[] columns = {"Expeduje", "Cena", "Přijde za"};

	public ShippingList() {
		onRoad = new ArrayList<Expedition>();
	}


	public ArrayList<Vehicle> getDisposableVehicles(VehicleList vehList, EmployeeList emplList) {
		// get list of available driving skills
		HashSet<DrivingSkill> ds = new HashSet<DrivingSkill>();

		for(Employee empl : emplList.getEmployees()) {
			if(!isEmployeeOnRoad(empl)) {
				ds.add(empl.getSkill());
			}
		}

		// loop through vehicles, and add those that have an item in ds hashset
		ArrayList<Vehicle> disposableVehs = new ArrayList<Vehicle>();
		for(Vehicle veh : vehList.getVehicles()) {
			if(ds.contains(veh.getRequiredSkill()) && !isVehicleOnRoad(veh)) {
				disposableVehs.add(veh);
			}
		}

		return disposableVehs;
	}

	public boolean isEmployeeOnRoad(Employee empl) {
		for(Expedition exp : onRoad) {
			if(exp.getEmployee().equals(empl)) {
				return true;
			}
		}
		return false;
	}

	public boolean isVehicleOnRoad(Vehicle veh) {
		for(Expedition exp : onRoad) {
			if(exp.getVehicle().equals(veh)) {
				return true;
			}
		}
		return false;
	}


	public ArrayList<Expedition> getExpeditions() {
		return onRoad;
	}

	public int getRowCount() {
		return onRoad.size();
	}

	public int getColumnCount() {
		return this.columns.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Expedition row = onRoad.get(rowIndex);
		switch(columnIndex) {
			case 0:
				return row.getEmployee() + "("+row.getVehicle()+")";
			case 1:
				return row.getVehicle().getPrice( row.getPkg() );
			case 2:
				return row.getTimeToFinish();
			default:
				return "";
		}
	}

	@Override
	public String getColumnName(int column) {
		return this.columns[ column ];
	}
}
