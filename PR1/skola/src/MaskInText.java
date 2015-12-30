import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author michal
 */
public class MaskInText {
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Zadejte nazev souboru: ");
		String name = sc.nextLine();
		System.out.println("---Vysledky---");
		
		File file = new File(name);
		if(!file.exists()) {
			System.out.println("Soubor neexistuje.");
			System.exit(1);
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String mask, text;
			while (br.ready()) {
				mask = br.readLine();
				text = br.readLine();

				System.out.println("maska: "+mask);
				if(text == null) {
					System.out.println("Pro masku neni text.");
					break;
				}

				System.out.println("text: "+text);
				System.out.println("obsahuje: "+ (testMask(mask, text) ? "ano" : "ne"));
				System.out.println("");
			}

		} catch (IOException ex) {
			System.out.println("Chyba pri praci se souborem.");
			System.exit(1);
		}


	}

	private static boolean testMask(String mask, String text) {
		int textPosition = 0;

		for(int maskPosition = 0; maskPosition < mask.length(); maskPosition++) {
			char currentMaskChar = mask.charAt(maskPosition);

			if(currentMaskChar != '*') { // ordinary character
				// check if this character is in the text
				if(text.length() <= textPosition) {
					//System.out.println("neobsahuje 0");
					return false;
				}
				char currentTextChar = text.charAt(textPosition);
				textPosition++;

				if(currentMaskChar != currentTextChar) {
					return false;
				}
			} else { // * wildcard
				if(maskPosition+1 == mask.length()) { // the end of the mask, there is no next char
					// if there is no next char, and the string has been 
					// ok so far, the text matches the mask
					return true;
				} else {
					char nextMaskChar = mask.charAt(maskPosition+1);
					if(nextMaskChar == '*') { // means the mask is something like abc**def
						continue; // next cycle will take care of this
					} else { // mask is something like abc*def
						// load the text that follows the * until the end of string or next *
						String checkForString = "" + nextMaskChar;
						maskPosition += 2;
						for(; maskPosition < mask.length(); maskPosition++) {
							if(mask.charAt(maskPosition) == '*') {
								break;
							}
							checkForString += mask.charAt(maskPosition);
						}
						maskPosition--; // the position should be on *, not afterwards

						// look for checkForString in the text
						// this operator must be greedy
						int checkForStringTextPosition;
						int lastOccurence = -1;
						int checkCounter = 0; // determines how many chars match checkForString
						for(checkForStringTextPosition = textPosition; checkForStringTextPosition < text.length(); checkForStringTextPosition++) {
							if(text.charAt(checkForStringTextPosition) == checkForString.charAt(checkCounter)) {
								checkCounter++;
							} else {
								checkCounter = 0;
								// case mask: abc*ghi text: abcdefgghi
								if(text.charAt(checkForStringTextPosition) == checkForString.charAt(0)) {
									checkCounter++;
								}
							}

							if(checkCounter == checkForString.length()) {
								lastOccurence = checkForStringTextPosition;
								checkCounter = 0;
							}
						}
						// the character is not found
						// example: mask ab*e text abcdf
						if(lastOccurence == -1) {
							return false;
						}

						// set the text position
						if(maskPosition+1 == mask.length()) {
							lastOccurence++;
						}
						textPosition = lastOccurence;
					}
				}
			}
		}
		if(textPosition != text.length()) {
			return false;
		}
		return true;
	}
}
