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
public class Van extends Vehicle {
	public Van() {
		setName("Dodávka");
		setSpeed(40);
		setMaxPayload(4000);
		setPricePerUse(50);
		setPricePerKm(250);
		setRequiredSkill(DrivingSkill.DRIVING_LICENSE_B);
	}
}
