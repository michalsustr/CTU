
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Vstup extends Thread {
	static public boolean hotovo = false;
	public void run() {
		byte[] pole = new byte[10];
		Thread.currentThread().setPriority(MAX_PRIORITY);
		while(hotovo == false) {
			try {
				System.in.read(pole);
				if(pole[0] == 'K') {
					hotovo =true;
				}
			} catch(IOException e) {
				System.out.println("Chyba vstupu");
				hotovo = true;
			}
		}
	}
}

/**
 *
 * @author michal
 */
public class vlakna extends Thread{
	public void run() {
		long i = 0;
		while(Vstup.hotovo == false) {
			System.out.print(i++ +"\n");
		}
	}

	public static void main(String[] args) {
		Vstup v1Vstup = new Vstup();
		v1Vstup.start();
		vlakna v1Vlakno = new vlakna();
		v1Vlakno.start();
	}
}
