/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rychlybalik.DrivingSkill;
import rychlybalik.Package;
import rychlybalik.vehicle.*;

/**
 *
 * @author michal
 */
public class VehicleList extends AbstractTableModel {
	public ArrayList<Vehicle> vehicles;
	private String[] columns = {"Název", "Nosnost [kg]", "Rychlost", "Cena/Km", "Cena/Hodina", "Cena/Užití"};


	public VehicleList() {
		vehicles = new ArrayList<Vehicle>();

		// add default vehicles. This could also be retrieved from db or file
		vehicles.add(new Van());
		
		Pickup p1 = new Pickup();
		p1.setPayload(1500);
		vehicles.add(p1);

		Pickup p2 = new Pickup();
		p2.setPayload(2300);
		vehicles.add(p1);

		Bicycle b1 = new Bicycle();
		b1.setPayload(3);
		vehicles.add(b1);

		MHD m1 = new MHD();
		m1.setPayload(14);
		vehicles.add(m1);

		MHD m2 = new MHD();
		m2.setPayload(6);
		vehicles.add(m2);
	}

	public Vehicle findBestVehicle(Package pa) {
		Vehicle bestVeh = null;
		int bestPrice = Integer.MAX_VALUE;
		for(Vehicle veh : this.vehicles) {
			if(veh.canTakePackage(pa)) {
				if(veh.getPrice(pa) < bestPrice) {
					bestVeh = veh;
					bestPrice = veh.getPrice(pa);
				}
			}
		}

		return bestVeh;
	}

	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}

	public int getRowCount() {
		return vehicles.size();
	}

	public int getColumnCount() {
		return this.columns.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Vehicle row = vehicles.get(rowIndex);
		switch(columnIndex) {
			case 0: // name
				return row.getName();
			case 1: // payload
				return row.getPayload();
			case 2: // speed
				return row.getSpeed();
			case 3: // pricePerKm
				return row.getPricePerKm();
			case 4: // pricePerHour
				return row.getPricePerHour();
			case 5: // pricePerUsage
				return row.getPricePerUse();
			default:
				return "";
		}
	}

	@Override
	public String getColumnName(int column) {
		return this.columns[ column ];
	}

	
	

}
