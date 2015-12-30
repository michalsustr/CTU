/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package truhlar;

/**
 *
 * @author michal
 */
public class SpolecnaData {

	private double cenaBarvyZaCM2 = 2.5;
	private double cenaDrevaZaM3 = 10000;
	private static SpolecnaData data;

	private SpolecnaData() {
	}

	public static SpolecnaData getData() {
		if(data == null) {
			data = new SpolecnaData();
		}
		return data;
	}


}
