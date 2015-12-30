package truhlar.kostky;

import java.awt.Color;

/**
 *
 * @author michal
 */
abstract public class PraKostka {
	// bez priznaku je pristupna u vsetkych potomkoch, ale iba v truhlar.kostky
	protected Color barva;
	protected final TypKostky typ;

	public PraKostka(Color barva, TypKostky typ) {
		this.barva = barva;
		this.typ = typ;
	}

	abstract public double getObjem();
	abstract public double getPovrch();

	public Color getBarva() {
		return barva;
	}

	public TypKostky getTyp() {
		return typ;
	}

	public void setBarva(Color barva) {
		this.barva = barva;
	}
}
