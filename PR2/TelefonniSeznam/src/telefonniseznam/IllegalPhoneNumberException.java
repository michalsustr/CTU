/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package telefonniseznam;

/**
 *
 * @author michal
 */
public class IllegalPhoneNumberException extends Exception {
	private String cislo;

	public IllegalPhoneNumberException(String cislo) {
		super("wrong # format");
		this.cislo = cislo;

	}

	@Override
	public String toString() {
		return "spatny format tel. cisla "+ cislo;
	}




}
