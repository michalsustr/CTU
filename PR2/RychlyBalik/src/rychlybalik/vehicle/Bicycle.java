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
public class Bicycle extends Vehicle {
	public Bicycle() {
		setName("Sportovní kolo");
		setSpeed(35);
		setMaxPayload(10);
		setPricePerUse(0);
		setPricePerHour(250);
		setRequiredSkill(DrivingSkill.DRIVING_BIKE);

	}

}
