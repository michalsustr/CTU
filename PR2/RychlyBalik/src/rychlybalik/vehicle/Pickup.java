/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.vehicle;

import rychlybalik.DrivingSkill;

/**
 *
 * @author michal
 */
public class Pickup extends Vehicle {
	public Pickup() {
		setName("Pickup");
		setSpeed(40);
		setMaxPayload(2500);
		setPricePerUse(80);
		setPricePerKm(230);
		setRequiredSkill(DrivingSkill.DRIVING_LICENSE_C);
	}
}
