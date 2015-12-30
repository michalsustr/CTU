/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package udalosti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author michal
 */
public class Posluchac1 implements ActionListener {
	private Citac citac;

	Posluchac1(Citac citac) {
		this.citac = citac;
	}

	public void actionPerformed(ActionEvent ae) {
		int hodnota = citac.getHodnota();
		citac.setHodnotaCitace(++hodnota);
	}
}
