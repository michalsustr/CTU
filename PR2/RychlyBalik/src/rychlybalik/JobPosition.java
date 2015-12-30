/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rychlybalik;

/**
 *
 * @author michal
 */
public enum JobPosition {
	ADMINISTRATOR, EXECUTIVE;

	@Override
	public String toString() {
		switch(this) {
			case ADMINISTRATOR:
				return "Admin.";
			case EXECUTIVE:
				return "Výkonný";
			default:
				return "";

		}
	}
}
