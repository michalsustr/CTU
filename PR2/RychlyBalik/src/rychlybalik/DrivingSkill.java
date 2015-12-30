/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik;

/**
 *
 * @author michal
 */
public enum DrivingSkill {
	DRIVING_LICENSE_B, DRIVING_LICENSE_C, DRIVING_BIKE;

	@Override
	public String toString() {
		switch(this)  {
			case DRIVING_BIKE:
				return "kolo";
			case DRIVING_LICENSE_B:
				return "RP. B";
			case DRIVING_LICENSE_C:
				return "RP. C";
			default:
				return "";
		}
	}
}
