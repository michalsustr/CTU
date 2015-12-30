package truhlar.kostky;

/**
 *
 * @author michal
 */
public enum TypKostky {

	PRO_MALE(3), PRO_VELKE(6);

	private final int ROZMER;

	private TypKostky(int rozmer) {
		this.ROZMER = rozmer;
	}

	public int getROZMER() {
		return ROZMER;
	}
}
