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
public class Krychle extends Kvadr {

	protected int hrana;

	public Krychle(Color barva, TypKostky typ, int hrana) {
		super(barva, typ, hrana, hrana, hrana);
	}

	@Override
	public String toString() {
		return "Krychle{" + "hrana=" + hrana + '}';
	}

	@Override
	public void setHloubka(int hloubka) {
		setHrana(hloubka);
	}

	@Override
	public void setSirka(int sirka) {
		setHrana(sirka);
	}

	@Override
	public void setVyska(int vyska) {
		setHrana(vyska);
	}



	public int getHrana() {
		return hrana;
	}

	public void setHrana(int hrana) {
		if(hrana < 0) {
			throw new IllegalArgumentException("zaporna hodnota");
		}
		this.hrana = hrana;
	}



}
