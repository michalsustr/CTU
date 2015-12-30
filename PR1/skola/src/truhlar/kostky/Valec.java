/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package truhlar.kostky;

import java.awt.Color;

/**
 *
 * @author michal
 */
public class Valec extends PraKostka {
	protected int polomer, vyska;

	public Valec(Color barva, TypKostky typ, int polomer, int vyska) {
		super(barva, typ);

		this.polomer = polomer;
		this.vyska = vyska;
	}

	@Override
	public double getObjem() {
		return Math.PI * polomer * polomer * vyska * Math.pow(typ.getROZMER(), 3);
	}

	@Override
	public double getPovrch() {
		return (Math.PI * polomer * polomer * 2 + 2*Math.PI*polomer*vyska) * typ.getROZMER()*typ.getROZMER();
	}



	public int getPolomer() {
		return polomer;
	}

	public int getVyska() {
		return vyska;
	}

	public void setPolomer(int polomer) {
		if(polomer < 0) {
			throw new IllegalArgumentException("zaporna hodnota");
		}
		this.polomer = polomer;
	}

	public void setVyska(int vyska) {
		if(vyska < 0) {
			throw new IllegalArgumentException("zaporna hodnota");
		}
		this.vyska = vyska;
	}






}
