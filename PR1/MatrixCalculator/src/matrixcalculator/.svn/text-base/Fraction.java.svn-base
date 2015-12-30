package matrixcalculator;

/**
 * An immutable class representing fractions as pairs of longs.
 * Fractions are always maintained in reduced form.
 **/
public class Fraction implements Cloneable, Comparable, java.io.Serializable {

	protected final long numerator;
	protected final long denominator;

	public final long numerator() {
		return this.numerator;
	}

	public final long denominator() {
		return this.denominator;
	}

	public Fraction(long num) {
		this(num, 1L);
	}

	public Fraction(long num, long den) {
		// normalize while constructing
		boolean numNonnegative = (num >= 0);
		boolean denNonnegative = (den >= 0);
		long a = numNonnegative ? num : -num;
		long b = denNonnegative ? den : -den;
		long g = gcd(a, b);

		this.numerator = (numNonnegative == denNonnegative) ? (a / g) : (-a / g);
		this.denominator = b / g;
	}

	/** Create a fraction with the same value as Fraction f **/
	public Fraction(Fraction f) {
		this.numerator = f.numerator();
		this.denominator = f.denominator();
	}

	public String toString() {
		if (this.denominator == 1) {
			return "" + this.numerator;
		} else {
			return this.numerator + "/" + this.denominator;
		}
	}

	public Object clone() {
		return new Fraction(this);
	}

	public double asDouble() {
		return ((double) (this.numerator)) / ((double) (this.denominator));
	}

	public int asInt() {
		return ((int) (this.numerator)) / ((int) (this.denominator));
	}

	/**
	 * Compute the nonnegative greatest common divisor of a and b.
	 * (This is needed for normalizing Fractions, but can be
	 * useful on its own.)
	 *
	 * @param a
	 * @param b
	 * @return greatest common divisor
	 */
	public static long gcd(long a, long b) {
		long x;
		long y;

		a = Math.abs(a);
		b = Math.abs(b);

		if (a >= b) {
			x = a;
			y = b;
		} else {
			x = b;
			y = a;
		}

		while (y != 0) {
			long t = x % y;
			x = y;
			y = t;
		}
		return x;
	}

	/** return a Fraction representing the negated value of this Fraction **/
	public Fraction negative() {
		long an = this.numerator;
		long ad = this.denominator;
		return new Fraction(-an, ad);
	}

	/** return a Fraction representing 1 / this Fraction **/
	public Fraction inverse() {
		long an = this.numerator;
		long ad = this.denominator;
		return new Fraction(ad, an);
	}

	/** return a Fraction representing this Fraction plus b **/
	public Fraction plus(Fraction b) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = b.numerator();
		long bd = b.denominator();
		return new Fraction(an * bd + bn * ad, ad * bd);
	}

	/** return a Fraction representing this Fraction plus n **/
	public Fraction plus(long n) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = n;
		long bd = 1;
		return new Fraction(an * bd + bn * ad, ad * bd);
	}

	/** return a Fraction representing this Fraction minus b **/
	public Fraction minus(Fraction b) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = b.numerator();
		long bd = b.denominator();
		return new Fraction(an * bd - bn * ad, ad * bd);
	}

	/** return a Fraction representing this Fraction minus n **/
	public Fraction minus(long n) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = n;
		long bd = 1;
		return new Fraction(an * bd - bn * ad, ad * bd);
	}

	/** return a Fraction representing this Fraction times b **/
	public Fraction times(Fraction b) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = b.numerator();
		long bd = b.denominator();
		return new Fraction(an * bn, ad * bd);
	}

	/** return a Fraction representing this Fraction times n **/
	public Fraction times(long n) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = n;
		long bd = 1;
		return new Fraction(an * bn, ad * bd);
	}

	/** return a Fraction representing this Fraction divided by b **/
	public Fraction dividedBy(Fraction b) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = b.numerator();
		long bd = b.denominator();
		return new Fraction(an * bd, ad * bn);
	}

	/** return a Fraction representing this Fraction divided by n **/
	public Fraction dividedBy(long n) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = n;
		long bd = 1;
		return new Fraction(an * bd, ad * bn);
	}

	/** return a number less, equal, or greater than zero
	 * reflecting whether this Fraction is less, equal or greater than
	 * the value of Fraction other.
	 **/
	public int compareTo(Object other) {
		Fraction b = (Fraction) (other);
		long an = this.numerator;
		long ad = this.denominator;
		long bn = b.numerator();
		long bd = b.denominator();
		long l = an * bd;
		long r = bn * ad;
		return (l < r) ? -1 : ((l == r) ? 0 : 1);
	}

	/** return a number less, equal, or greater than zero
	 * reflecting whether this Fraction is less, equal or greater than n.
	 **/
	public int compareTo(long n) {
		long an = this.numerator;
		long ad = this.denominator;
		long bn = n;
		long bd = 1;
		long l = an * bd;
		long r = bn * ad;
		return (l < r) ? -1 : ((l == r) ? 0 : 1);
	}

	public boolean equals(Object other) {
		return compareTo((Fraction) other) == 0;
	}

	public boolean equals(long n) {
		return compareTo(n) == 0;
	}

	public int hashCode() {
		return (int) (numerator ^ denominator);
	}

	public static Fraction[] fromArray(int[] array) {
		Fraction[] result = new Fraction[array.length];
		for(int i = 0; i < array.length; i++) {
			result[i] = new Fraction(array[i]);
		}
		return result;
	}
}
