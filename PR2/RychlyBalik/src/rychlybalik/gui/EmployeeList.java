/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rychlybalik.DrivingSkill;
import rychlybalik.Employee;
import rychlybalik.JobPosition;

/**
 *
 * @author michal
 */
public class EmployeeList extends AbstractTableModel {
	public ArrayList<Employee> employees;
	private String[] columns = {"Jméno", "Příjmení", "Licence", "Výkonný"};


	public EmployeeList() {
		employees = new ArrayList<Employee>();

		// add default vehicles. This could also be retrieved from db or file
		Employee karel = new Employee();
		karel.setName("Karel");
		karel.setSurname("Vomáčka");
		karel.setSkill(DrivingSkill.DRIVING_LICENSE_B);
		karel.setPosition(JobPosition.EXECUTIVE);
		employees.add(karel);

		Employee chuck = new Employee();
		chuck.setName("Chuck");
		chuck.setSurname("Norris");
		chuck.setSkill(DrivingSkill.DRIVING_BIKE);
		chuck.setPosition(JobPosition.EXECUTIVE);
		employees.add(chuck);
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public int getRowCount() {
		return employees.size();
	}

	public int getColumnCount() {
		return this.columns.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Employee row = employees.get(rowIndex);
		switch(columnIndex) {
			case 0:
				return row.getName();
			case 1:
				return row.getSurname();
			case 2:
				return row.getSkill();
			case 3:
				return row.getPosition();
			default:
				return "";
		}
	}

	@Override
	public String getColumnName(int column) {
		return this.columns[ column ];
	}
}
