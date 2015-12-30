/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrixcalculator;

import java.util.HashMap;
import java.util.Map;
import javax.swing.text.html.HTMLEditorKit.Parser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michal
 */
public class MathParserTest {
	/**
	 * Test of parseEquation method, of class MathParser.
	 */ @Test
	public void testInfix2Postfix() throws MatrixException, ParserException {
		System.out.println("infix2postfix");

		assertEquals("[A]", new MathParser("A").infix2postfix().toString());
		assertEquals("[A, B, *]", new MathParser("A*B").infix2postfix().toString());
		assertEquals("[A, B, *, C, *]", new MathParser("A*B*C").infix2postfix().toString());
		assertEquals("[A, B, *, C, *, D, *]", new MathParser("A*B*C*D").infix2postfix().toString());
		assertEquals("[A, B, +]", new MathParser("A+B").infix2postfix().toString());
		assertEquals("[A, B, +, C, +]", new MathParser("A+B+C").infix2postfix().toString());
		assertEquals("[A, B, +, C, +, D, +]", new MathParser("A+B+C+D").infix2postfix().toString());
		assertEquals("[A, B, *, C, D, *, +]", new MathParser("A*B+C*D").infix2postfix().toString());
		assertEquals("[E, F, *, G, *, Z, +]", new MathParser("E*F*G+Z").infix2postfix().toString());
		assertEquals("[F, 2, ^]", new MathParser("F^2").infix2postfix().toString());
		assertEquals("[F, 2, ^, B, +]", new MathParser("F^2+B").infix2postfix().toString());
		assertEquals("[F, 1, *, 2, /, B, +]", new MathParser("F*1/2+B").infix2postfix().toString());
		assertEquals("[F, 3, ^, B, *, C, +]", new MathParser("F^3*B+C").infix2postfix().toString());
		assertEquals("[A, 2, *, 5, C, *, -]", new MathParser("A*2-5*C").infix2postfix().toString());
		assertEquals("[A, 2, *, A, C, *, -]", new MathParser("A*2-(A*C)").infix2postfix().toString());
		assertEquals("[A, -1, ^]", new MathParser("A^-1").infix2postfix().toString());
		assertEquals("[F, -2, ^, B, C, +, *]", new MathParser("F^-2*(B+C)").infix2postfix().toString());
		assertEquals("[1, -2, *]", new MathParser("1*(-2)").infix2postfix().toString());
		assertEquals("[-2, B, *]", new MathParser("-2*B").infix2postfix().toString());
		assertEquals("[-1, 2, B, *, *]", new MathParser("-(2*B)").infix2postfix().toString());
		assertEquals("[2, B, *, -3, ^]", new MathParser("(2*B)^-3").infix2postfix().toString());
		assertEquals("[-2, 2, ^]", new MathParser("-2^2").infix2postfix().toString());
		assertEquals("[-1, -2, 2, ^, *]", new MathParser("-(-2)^2").infix2postfix().toString());
		assertEquals("[3, 4, 2, 1, 5, -, 2, 3, ^, ^, ^, *, +]", new MathParser("3+4*2^(1-5)^2^3").infix2postfix().toString());
		assertEquals("[5, 2, 2, 1, +, *, ^]", new MathParser("5^(2*(2+1))").infix2postfix().toString());
		assertEquals("[-1, -1, A, *, 2, ^, *]", new MathParser("-(-A)^2").infix2postfix().toString());
		assertEquals("[1, 3, /, A, *]", new MathParser("1/3*A").infix2postfix().toString());
	}

	 @Test
	public void testGetMatrices() throws MatrixException, ParserException {
		System.out.println("getMatrices");
		MathParser p = new MathParser("A*B+C+A^3-F");
		assertArrayEquals(new Character[] {'F','A','B','C'}, p.getMatrices().toArray());

	 }

	 @Test
	 public void testProcessPostfix() throws MatrixException, ParserException, IllegalAccessException, Exception {
		System.out.println("processPostfix");
		Map<Character, Matrix> matrices = new HashMap<Character, Matrix>();
		matrices.put('A', new Matrix(2,2, Fraction.fromArray(new int[]{1,2,3,4})));
		matrices.put('B', new Matrix(2,2, Fraction.fromArray(new int[]{1,2,3,4})));
		assertEquals(new Matrix(2,2, Fraction.fromArray(new int[]{2,4,6,8})), new MathParser("A+B").processPostfix(matrices));

		matrices.clear();
		Matrix matrix = new Matrix(3,3, Fraction.fromArray(new int[]{0,0,1, 1,2,3, 2,2,1}));
		Matrix inverse = new Matrix(3,3, Fraction.fromArray(new int[]{-2,4,2,  3,-5,-4,  -2,2,2}));
		matrices.put('A', matrix);
		assertTrue(new MathParser("A^-1").processPostfix(matrices).equivalent(inverse));

		Matrix A = new Matrix(2,2, Fraction.fromArray(new int[]{1,2,3,6}));
		Matrix B = new Matrix(2,3, Fraction.fromArray(new int[]{1,2,-3,2,0,1}));
		Matrix C = new Matrix(3,3, Fraction.fromArray(new int[]{-1,-1,1,-2,-2,2,1,1,-1}));
		Matrix D = new Matrix(2,2, Fraction.fromArray(new int[]{3,9,3,6}));
		Matrix AB = new Matrix(2,3, Fraction.fromArray(new int[]{5,2,-1,15,6,-3}));
		Matrix BC = new Matrix(2,3, Fraction.fromArray(new int[]{-8,-8,8,-1,-1,1}));
		matrices.clear();
		matrices.put('A', A);
		matrices.put('B', B);
		matrices.put('C', C);
		matrices.put('D', D);
		assertEquals(AB, new MathParser("A*B").processPostfix(matrices));
		assertEquals(BC, new MathParser("B*C").processPostfix(matrices));
		assertEquals(new Matrix(2,2, Fraction.fromArray(new int[]{1,3,1,2})), new MathParser("1/3*D").processPostfix(matrices));
	 }
}