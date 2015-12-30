/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik;

import rychlybalik.vehicle.Vehicle;
import java.util.Date;

/**
 *
 * @author michal
 */
public class Expedition {
	protected Vehicle vehicle;
	protected Employee employee;
	protected Date onRoadFrom;
	protected Package pkg;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getOnRoadFrom() {
		return onRoadFrom;
	}

	public void setOnRoadFrom(Date onRoadFrom) {
		this.onRoadFrom = onRoadFrom;
	}

	public String getTimeToFinish() {
		long diff = onRoadFrom.getTime() + (long) (vehicle.getTime(pkg) * 3600 * 1000) - (new Date()).getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (3600*1000) % 3600;

		if(diff <= 0) {
			return "Hotovo";
		}

		if(diffHours > 0) {
			return diffHours + ":" + (diffMinutes < 10 ? "0"+diffMinutes : diffMinutes) + ":" +
				(diffSeconds < 10 ? "0"+diffSeconds : diffSeconds);
		}
		
		return (diffMinutes < 10 ? "0"+diffMinutes : diffMinutes) + ":" +
				(diffSeconds < 10 ? "0"+diffSeconds : diffSeconds);
	}

	public Package getPkg() {
		return pkg;
	}

	public void setPkg(Package pkg) {
		this.pkg = pkg;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}
