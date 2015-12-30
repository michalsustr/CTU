/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package threads;

/**
 *
 * @author michal
 */
public class Main {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {
		public void run() {
			while(!ready) Thread.yield();
			System.out.println(number);
		}
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ReaderThread().start();
		number = 42;
		ready = true;
    }

}
