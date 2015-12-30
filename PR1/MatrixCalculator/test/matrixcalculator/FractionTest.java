/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrixcalculator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michal
 */
public class FractionTest {
	/**
	 * Test of toString method, of class Fraction.
	 */ @Test
	public void testToString() {
		System.out.println("toString");
		assertEquals("2", new Fraction(6,3).toString());
		assertEquals("1/2", new Fraction(3,6).toString());
		assertEquals("5/8", new Fraction(5,8).toString());
	}

	/**
	 * Test of gcd method, of class Fraction.
	 */ @Test
	public void testGcd() {
		System.out.println("gcd");
		assertEquals(10, Fraction.gcd(100, 10));
		assertEquals(1, Fraction.gcd(13, 37));
		assertEquals(8, Fraction.gcd(8, 16));
	}

	/**
	 * Test of plus method, of class Fraction.
	 */ @Test
	public void testPlus_Fraction() {
		System.out.println("plus");
		assertEquals("3/4", new Fraction(1,2).plus(new Fraction(1,4)).toString());
		assertEquals("2", new Fraction(1,2).plus(new Fraction(2,4)).toString());
		fail("add more tests");
	}

	/**
	 * Test of plus method, of class Fraction.
	 */ @Test
	public void testPlus_long() {
		System.out.println("plus");
		long n = 0L;
		Fraction instance = null;
		Fraction expResult = null;
		Fraction result = instance.plus(n);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of minus method, of class Fraction.
	 */ @Test
	public void testMinus_Fraction() {
		System.out.println("minus");
		Fraction b = null;
		Fraction instance = null;
		Fraction expResult = null;
		Fraction result = instance.minus(b);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of minus method, of class Fraction.
	 */ @Test
	public void testMinus_long() {
		System.out.println("minus");
		long n = 0L;
		Fraction instance = null;
		Fraction expResult = null;
		Fraction result = instance.minus(n);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of times method, of class Fraction.
	 */ @Test
	public void testTimes_Fraction() {
		System.out.println("times");
		Fraction b = null;
		Fraction instance = null;
		Fraction expResult = null;
		Fraction result = instance.times(b);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of times method, of class Fraction.
	 */ @Test
	public void testTimes_long() {
		System.out.println("times");
		long n = 0L;
		Fraction instance = null;
		Fraction expResult = null;
		Fraction result = instance.times(n);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of dividedBy method, of class Fraction.
	 */ @Test
	public void testDividedBy_Fraction() {
		System.out.println("dividedBy");
		Fraction b = null;
		Fraction instance = null;
		Fraction expResult = null;
		Fraction result = instance.dividedBy(b);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of dividedBy method, of class Fraction.
	 */ @Test
	public void testDividedBy_long() {
		System.out.println("dividedBy");
		long n = 0L;
		Fraction instance = null;
		Fraction expResult = null;
		Fraction result = instance.dividedBy(n);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of compareTo method, of class Fraction.
	 */ @Test
	public void testCompareTo_Object() {
		System.out.println("compareTo");
		Object other = null;
		Fraction instance = null;
		int expResult = 0;
		int result = instance.compareTo(other);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of compareTo method, of class Fraction.
	 */ @Test
	public void testCompareTo_long() {
		System.out.println("compareTo");
		long n = 0L;
		Fraction instance = null;
		int expResult = 0;
		int result = instance.compareTo(n);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of equals method, of class Fraction.
	 */ @Test
	public void testEquals_Object() {
		System.out.println("equals");
		Object other = null;
		Fraction instance = null;
		boolean expResult = false;
		boolean result = instance.equals(other);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of equals method, of class Fraction.
	 */ @Test
	public void testEquals_long() {
		System.out.println("equals");
		long n = 0L;
		Fraction instance = null;
		boolean expResult = false;
		boolean result = instance.equals(n);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of hashCode method, of class Fraction.
	 */ @Test
	public void testHashCode() {
		System.out.println("hashCode");
		Fraction instance = null;
		int expResult = 0;
		int result = instance.hashCode();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

}