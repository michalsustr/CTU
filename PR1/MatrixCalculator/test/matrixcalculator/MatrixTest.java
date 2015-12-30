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
public class MatrixTest {

    public MatrixTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	/**
	 * Test of addItem method, of class Matrix.
	 */ @Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testInsertItem() {
		System.out.println("insertItem");
		Matrix matrix = new Matrix(2, 2);
		matrix.insertItem(new Fraction(1));
		matrix.insertItem(new Fraction(2));
		matrix.insertItem(new Fraction(3));
		matrix.insertItem(new Fraction(4));
		matrix.insertItem(new Fraction(5));
	}

	/**
	 * Test of getItemAt method, of class Matrix.
	 */ @Test
	public void testGetItemAt() throws Exception, MatrixException {
		System.out.println("getItemAt");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[] {1,2,3,4,5,6,7,8,9}));

		assertEquals(new Fraction(1), instance.getItemAt(0, 0));
		assertEquals(new Fraction(2), instance.getItemAt(0, 1));
		assertEquals(new Fraction(3), instance.getItemAt(0, 2));
		assertEquals(new Fraction(4), instance.getItemAt(1, 0));
		assertEquals(new Fraction(5), instance.getItemAt(1, 1));
		assertEquals(new Fraction(6), instance.getItemAt(1, 2));
		assertEquals(new Fraction(7), instance.getItemAt(2, 0));
		assertEquals(new Fraction(8), instance.getItemAt(2, 1));
		assertEquals(new Fraction(9), instance.getItemAt(2, 2));

	}

	/**
	 * Test of setItemAt method, of class Matrix.
	 */ @Test
	public void testSetItemAt() throws MatrixException {
		System.out.println("setItemAt");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[] {1,2,3,4,5,6,7,8,9}));
		assertEquals(new Fraction(45), instance.sumItems());

		instance.setItemAt(new Fraction(1), 0, 0);
		instance.setItemAt(new Fraction(1), 0, 1);
		instance.setItemAt(new Fraction(1), 0, 2);
		instance.setItemAt(new Fraction(1), 1, 0);
		instance.setItemAt(new Fraction(1), 1, 1);
		instance.setItemAt(new Fraction(1), 1, 2);
		instance.setItemAt(new Fraction(1), 2, 0);
		instance.setItemAt(new Fraction(1), 2, 1);
		instance.setItemAt(new Fraction(1), 2, 2);
		
		assertEquals(new Fraction(9), instance.sumItems());

		try {
			instance.setItemAt(new Fraction(5), 4, 4);
			fail("item should not have been set");
		} catch(ArrayIndexOutOfBoundsException e) {}
	}

	/**
	 * Test of getRow method, of class Matrix.
	 */ @Test
	public void testGetRow() throws MatrixException {
		System.out.println("getRow");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[] {1,2,3,4,5,6,7,8,9}));
		Fraction[] row = Fraction.fromArray(new int[] {7,8,9});
		assertEquals(row, instance.getRow(2));

		instance.setItemAt(new Fraction(7), 1, 0);
		instance.setItemAt(new Fraction(8), 1, 1);
		instance.setItemAt(new Fraction(9), 1, 2);

		assertEquals(row, instance.getRow(1));

		try {
			instance.getRow(3);
			fail("row should not exist");
		} catch(ArrayIndexOutOfBoundsException e) {}
	}

	/**
	 * Test of getCol method, of class Matrix.
	 */ @Test
	public void testGetCol() throws MatrixException {
		System.out.println("getCol");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[] {1,2,3,4,5,6,7,8,9}));
		Fraction[] col = Fraction.fromArray(new int[] {3,6,9});
		assertEquals(col, instance.getCol(2));

		instance.setItemAt(new Fraction(3), 0, 1);
		instance.setItemAt(new Fraction(6), 1, 1);
		instance.setItemAt(new Fraction(9), 2, 1);

		assertEquals(col, instance.getCol(1));

		try {
			instance.getCol(3);
			fail("col should not exist");
		} catch(ArrayIndexOutOfBoundsException e) {}
	}

	/**
	 * Test of getMainDiagonal method, of class Matrix.
	 */ @Test
	public void testGetMainDiagonal() throws MatrixException {
		System.out.println("getMainDiagonal");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[] {1,2,3,4,5,6,7,8,9}));
		Fraction[] diag = Fraction.fromArray(new int[] {1,5,9});
		assertEquals(diag, instance.getMainDiagonal());
		
		Matrix instance2 = new Matrix(5,5, Fraction.fromArray(new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25}));
		Fraction[] diag2 = Fraction.fromArray(new int[]{1,7,13,19,25});
		assertEquals(diag2, instance2.getMainDiagonal());

		Matrix instance3 = new Matrix(1,1, Fraction.fromArray(new int[]{5}));
		Fraction[] diag3 = Fraction.fromArray(new int[]{5});
		assertEquals(diag3, instance3.getMainDiagonal());
	}

	/**
	 * Test of add method, of class Matrix.
	 */ @Test
	public void testAdd_int() throws MatrixException, IllegalAccessException {
		System.out.println("add");
		Fraction[] items = Fraction.fromArray(new int[]{1,2,3,4,5,6,7,8,9});
		Matrix instance = new Matrix(3,3, items);
		assertEquals(new Fraction(45), instance.sumItems());
		instance.plus(new Fraction(1));
		assertEquals(new Fraction(54), instance.sumItems());
		instance.plus(new Fraction(-2));
		assertEquals(new Fraction(36), instance.sumItems());
		instance.plus(new Fraction(10));
		assertEquals(new Fraction(126), instance.sumItems());
	}

	/**
	 * Test of add method, of class Matrix.
	 */ @Test
	public void testAdd_Matrix() throws Exception, MatrixException {
		System.out.println("add");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[]{1,2,3,4,5,6,7,8,9}));
		Matrix instance2 = new Matrix(3,3, Fraction.fromArray(new int[]{9,8,7,6,5,4,3,2,1}));

		instance.plus(instance2);
		assertEquals(new Fraction(90), instance.sumItems());
		assertEquals(new Fraction(10), instance.getItemAt(1,2));

		try {
			Matrix instance3 = new Matrix(2,2, Fraction.fromArray(new int[]{4,3,2,1}));
			instance.plus(instance3);
			fail("matrices of different dimension cannot be added together");
		} catch(MatrixException e) {}
	}

	/**
	 * Test of multiply method, of class Matrix.
	 */ @Test
	public void testMultiply_int() throws IllegalAccessException, MatrixException {
		System.out.println("multiply");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[]{1,2,3,4,5,6,7,8,9}));
		assertEquals(new Fraction(45), instance.sumItems());
		instance.times(new Fraction(-1));
		assertEquals(new Fraction(-45), instance.sumItems());
		instance.times(new Fraction(-2));
		assertEquals(new Fraction(90), instance.sumItems());
		instance.times(new Fraction(10));
		assertEquals(new Fraction(900), instance.sumItems());
		assertEquals(new Fraction(180), instance.getItemAt(2, 2));
	}

	/**
	 * Test of multiply method, of class Matrix.
	 */ @Test
	public void testMultiply_Matrix() throws Exception, MatrixException {
		System.out.println("multiply");
		Matrix A = new Matrix(2,2, Fraction.fromArray(new int[]{1,2,3,6}));
		Matrix B = new Matrix(2,3, Fraction.fromArray(new int[]{1,2,-3,2,0,1}));
		Matrix C = new Matrix(3,3, Fraction.fromArray(new int[]{-1,-1,1,-2,-2,2,1,1,-1}));

		Matrix AB = new Matrix(2,3, Fraction.fromArray(new int[]{5,2,-1,15,6,-3}));
		Matrix BC = new Matrix(2,3, Fraction.fromArray(new int[]{-8,-8,8,-1,-1,1}));

		assertEquals(AB, A.times(B));
		assertEquals(BC, B.times(C));
	}

	@Test
	public void testTranspose() throws MatrixException {
		System.out.println("tranpose");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[]{1,2,3,4,5,6,7,8,9}));
		instance.transpose();
		Fraction[] result = Fraction.fromArray(new int[]{1,4,7,2,5,8,3,6,9});
		assertArrayEquals(result, instance.getLinearItems());
		System.out.println(instance.getLinearItems());
	}

	@Test
	public void testUpperTriangularMatrix() throws IllegalAccessException, MatrixException {
		System.out.println("upper triangular matrix");
		/*Matrix instance = new Matrix(4,4, Fraction.fromArray(new int[]{1,3,1,-2,  2,4,5,3,  -2,-1,7,3,  0,1,0,-2}));
		instance.upperTriangularMatrix();
		assertArrayEquals(Fraction.fromArray(new int[]{1,3,1,-2,  0,-2,3,7,  0,0,33/2,33/2}), instance.getLinearItems());*/
/*
		Matrix instance2 = new Matrix(4,4, Fraction.fromArray(new int[]{2,1,11,2, 1,0,4,-1,  11,4,56,5,  2,-1,5,-6}));
		instance2.upperTriangularMatrix();
		assertArrayEquals(Fraction.fromArray(new int[]{1,0,4,-1, 0,1,3,4}), instance2.getLinearItems());*/

		/*Matrix instance3 = new Matrix(4,4, Fraction.fromArray(new int[]{1,2,3,1,  2,4,7,7,  1,0,2,0,   3,7,10,6}));
		instance3.upperTriangularMatrix();
		assertArrayEquals(Fraction.fromArray(new int[]{1,2,3,1,  0,-2,-1,-1,  0,0,1,5}), instance2.getLinearItems());*/

		Matrix instance3 = new Matrix(3,3, Fraction.fromArray(new int[]{1,4,16,  1,5,25,  1,9,81}));
		instance3.upperTriangularMatrix();
		assertArrayEquals(Fraction.fromArray(new int[]{1,4,16,  0,1,9,  0,0,20}), instance3.getLinearItems());
	}

	@Test
	public void testSwitchRows() throws MatrixException {
		System.out.println("switch rows");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[]{1,2,3,4,5,6,7,8,9}));
		instance.switchRows(1, 2);

		Fraction[] result = Fraction.fromArray(new int[]{1,2,3,7,8,9,4,5,6});
		assertArrayEquals(result, instance.getLinearItems());
	}

	@Test
	public void testSwitchCols() throws MatrixException {
		System.out.println("switch cols");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[]{1,2,3,4,5,6,7,8,9}));
		instance.switchCols(1, 2);

		Fraction[] result = Fraction.fromArray(new int[]{1,3,2,4,6,5,7,9,8});
		assertArrayEquals(result, instance.getLinearItems());
	}

	@Test
	public void testFirstItemNotZeroOnRow() throws IllegalAccessException, MatrixException {
		System.out.println("firstItemNotZeroOnRow");
		Matrix instance = new Matrix(3,3, Fraction.fromArray(new int[]{1,2,3,0,5,6,0,0,9}));
		assertEquals(0, instance.firstItemNotZeroOnRow(0));
		assertEquals(1, instance.firstItemNotZeroOnRow(1));
		assertEquals(2, instance.firstItemNotZeroOnRow(2));
	}

	@Test
	public void testEquivalent() throws IllegalAccessException, MatrixException {
		System.out.println("equivalent");
		Matrix instance = new Matrix(2,4, Fraction.fromArray(new int[]{0,-2,3,7,  0,0,16,16}));
		Matrix other    = new Matrix(2,4, Fraction.fromArray(new int[]{0,1,0,-2,  0,0,1,1}));
		assertTrue(instance.equivalent(other));

		Matrix instance2 = new Matrix(4,5, Fraction.fromArray(new int[]{-4,4,-1,1,-7,  2,-2,1,0,3,  4,-4,5,1,7,  -6,6,-4,1,-12}));
		Matrix other2    = new Matrix(3,5, Fraction.fromArray(new int[]{2,-2,1,0,3,  0,0,1,1,-2,  0,0,0,1,-2}));
		assertTrue(instance2.equivalent(other2));

		Matrix instance3 = new Matrix(3,6, Fraction.fromArray(new int[]{1,1,2,3,3,3,  1,1,1,3,1,1,   2,2,2,6,2,8}));
		Matrix other3    = new Matrix(3,6, Fraction.fromArray(new int[]{1,1,0,3,-1,0,  0,0,1,0,2,0,  0,0,0,0,0,1}));
		assertTrue(instance3.equivalent(other3));
	}

	@Test
	public void testGetDeterminant() throws IllegalAccessException, MatrixException {
		System.out.println("getDeterminant");
		Matrix instance = new Matrix(4,4, Fraction.fromArray(new int[]{1,2,4,-1,  2,1,2,2,  1,3,1,2,   2,1,2,1}));
		assertEquals(new Fraction(-15), instance.getDeterminant());

		Matrix instance2 = new Matrix(3,3, Fraction.fromArray(new int[]{1,4,16,  1,5,25,  1,9,81}));
		assertEquals(new Fraction(20), instance2.getDeterminant());

		Matrix instance3 = new Matrix(4,4, Fraction.fromArray(new int[]{1,1,1,1,  1,1,-1,-1,  -1,1,1,-1,  1,-1,-1,1}));
		assertEquals(new Fraction(0), instance3.getDeterminant());
	}

	@Test
	public void testGetInverse() throws MatrixException, IllegalAccessException {
		System.out.println("getInverse");
		Matrix matrix = new Matrix(3,3, Fraction.fromArray(new int[]{0,0,1, 1,2,3, 2,2,1}));
		Matrix inverse = new Matrix(3,3, Fraction.fromArray(new int[]{-2,4,2,  3,-5,-4,  -2,2,2}));
		assertTrue(matrix.getInverse().equivalent(inverse));
	}

}