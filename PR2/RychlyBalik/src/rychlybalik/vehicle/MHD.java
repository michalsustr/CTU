/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik.vehicle;

/**
 *
 * @author michal
 */
public class MHD extends Vehicle{

	public MHD() {
		setName("MHD");
		setSpeed(30);
		setMaxPayload(15);
		setPricePerUse(0);
		setPricePerHour(200);
		setRequiredSkill(null);
	}

}
