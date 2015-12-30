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
public class Kvadr extends PraKostka {

	protected int sirka, vyska, hloubka;

	public Kvadr(Color barva, TypKostky typ, int sirka, int vyska, int hloubka) {
		super(barva, typ);
		
		this.sirka = sirka;
		this.vyska = vyska;
		this.hloubka = hloubka;
	}

	@Override
	public double getObjem() {
		return sirka * vyska * hloubka * Math.pow(typ.getROZMER(), 3);
	}

	@Override
	public double getPovrch() {
		return 2*(sirka * vyska + vyska * hloubka + sirka * hloubka) * typ.getROZMER()*typ.getROZMER();
	}

	@Override
	public String toString() {
		return "Hranol{" + "sirka=" + sirka + ", vyska=" + vyska + ", hloubka=" + hloubka + '}';
	}



	public int getHloubka() {
		return hloubka;
	}

	public int getSirka() {
		return sirka;
	}

	public int getVyska() {
		return vyska;
	}

	public void setHloubka(int hloubka) {
		if(hloubka < 0) {
			throw new IllegalArgumentException("zaporna hodnota");
		}
		this.hloubka = hloubka;
	}

	public void setSirka(int sirka) {
		if(sirka < 0) {
			throw new IllegalArgumentException("zaporna hodnota");
		}
		this.sirka = sirka;
	}

	public void setVyska(int vyska) {
		if(vyska < 0) {
			throw new IllegalArgumentException("zaporna hodnota");
		}
		this.vyska = vyska;
	}

}
