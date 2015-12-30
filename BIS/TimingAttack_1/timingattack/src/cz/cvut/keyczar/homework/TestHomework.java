/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.keyczar.homework;

import cz.cvut.keyczar.Verifier;
import cz.cvut.keyczar.exceptions.KeyczarException;
//import org.junit.Test;

public class TestHomework {
        public static void main() throws KeyczarException {
            System.out.println("Uspech :)");
            TestHomework hw = new TestHomework();
            hw.testCrack();
        }

	//@Test
	public void testCrack() throws KeyczarException {
		Verifier v = new Verifier("keys");
		boolean souhlasi = v.verify("Message".getBytes(), new byte[]{1,5,5,4,8});
                System.out.println("Souhlasi "+souhlasi);
		// posielam spravu a chcem zistit aky je jej hash na zaklade timing erroru
		// hash kluca ma dlzku 25B
	}
}
