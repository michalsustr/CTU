
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author michal
 */
public class Posloupnost {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int n;
		int max = 0;
		int secondmax = 0;
		int entry;

		System.out.println("Zadejte pocet cisel:");
		while( true ) {
			n = sc.nextInt();
			if(n >= 1) {
				break;
			}
			System.out.println("Nespravny vstup. Zadejte pocet cisel:");
		}

		System.out.println("Zadejte cisla:");
		
		for(int i = 0; i < n; i++) {
			entry = sc.nextInt();
			if(entry % 2 != 0 || entry <= 0) {
				continue;
			}

			if(entry > secondmax && entry > max) {
				secondmax = max;
				max = entry;
				continue;
			}

			if(entry > secondmax && entry < max) {
				secondmax = entry;
			}
		}
		System.out.println("---Vysledky---");
		if(max == secondmax || secondmax == 0) {
			System.out.println("Druhe nejvetsi kladne sude cislo neexistuje.");
		} else {
			System.out.println("Druhe nejvetsi kladne sude cislo: "+secondmax);
		}
	}
}
