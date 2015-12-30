/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.vehicle;

import java.util.Date;
import rychlybalik.DrivingSkill;
import rychlybalik.Employee;
import rychlybalik.Package;

/**
 *
 * @author michal
 */
public class Vehicle {
	protected String name;
	protected Integer speed;
	protected Integer payload; // in kg
	protected Integer maxPayload;
	protected Integer pricePerUse = 0;
	protected Integer pricePerKm = 0;
	protected Integer pricePerHour = 0;
	protected DrivingSkill requiredSkill;

	public boolean canTakePackage(Package pa) {
		return pa.getMass() <= this.getPayload();
	}

	public int getPrice(Package pa) {
		if(pricePerKm != 0) {
			return pricePerUse+pa.getDistance()*pricePerKm;
		} else if(pricePerHour != 0) {
			return pricePerUse+(int) (((float) pa.getDistance()/ (float) speed)*pricePerHour);
		}
		return pricePerUse;
	}

	/**
	 * 
	 * @param pa
	 * @return float time in hours
	 */
	public float getTime(Package pa) {
		return (float) pa.getDistance()/ (float) speed;
	}

	public Integer getMaxPayload() {
		return maxPayload;
	}

	public void setMaxPayload(Integer maxPayload) {
		this.maxPayload = maxPayload;
	}

	public Integer getPayload() {
		return payload != null ? payload : maxPayload;
	}

	public void setPayload(Integer amount) {
		if(amount > maxPayload) {
			throw new RuntimeException(this.getClass() + " cannot take more than "+maxPayload+" kg");
		}
		this.payload = amount;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Integer pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Integer getPricePerKm() {
		return pricePerKm;
	}

	public void setPricePerKm(Integer pricePerKm) {
		this.pricePerKm = pricePerKm;
	}

	public Integer getPricePerUse() {
		return pricePerUse;
	}

	public void setPricePerUse(Integer pricePerUse) {
		this.pricePerUse = pricePerUse;
	}

	public DrivingSkill getRequiredSkill() {
		return requiredSkill;
	}

	public void setRequiredSkill(DrivingSkill requiredSkill) {
		this.requiredSkill = requiredSkill;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return name;
	}



	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Vehicle other = (Vehicle) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.speed != other.speed && (this.speed == null || !this.speed.equals(other.speed))) {
			return false;
		}
		if (this.payload != other.payload && (this.payload == null || !this.payload.equals(other.payload))) {
			return false;
		}
		if (this.pricePerUse != other.pricePerUse && (this.pricePerUse == null || !this.pricePerUse.equals(other.pricePerUse))) {
			return false;
		}
		if (this.pricePerKm != other.pricePerKm && (this.pricePerKm == null || !this.pricePerKm.equals(other.pricePerKm))) {
			return false;
		}
		if (this.pricePerHour != other.pricePerHour && (this.pricePerHour == null || !this.pricePerHour.equals(other.pricePerHour))) {
			return false;
		}
		if (this.requiredSkill != other.requiredSkill) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 53 * hash + (this.speed != null ? this.speed.hashCode() : 0);
		hash = 53 * hash + (this.payload != null ? this.payload.hashCode() : 0);
		hash = 53 * hash + (this.pricePerUse != null ? this.pricePerUse.hashCode() : 0);
		hash = 53 * hash + (this.pricePerKm != null ? this.pricePerKm.hashCode() : 0);
		hash = 53 * hash + (this.pricePerHour != null ? this.pricePerHour.hashCode() : 0);
		hash = 53 * hash + (this.requiredSkill != null ? this.requiredSkill.hashCode() : 0);
		return hash;
	}

	public boolean canBeDrivenBy(Employee emp) {
		if(requiredSkill == null) return true;

		return requiredSkill.equals(emp.getSkill());
	}


}
