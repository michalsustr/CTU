/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik;

/**
 *
 * @author michal
 */
public class Package {
	protected Integer mass; // in kg
	protected Integer distance;

	public Package(Integer mass, Integer distance) {
		this.mass = mass;
		this.distance = distance;
	}
	

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getMass() {
		return mass;
	}

	public void setMass(Integer mass) {
		this.mass = mass;
	}
	
}
