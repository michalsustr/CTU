/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexeso;

import java.awt.Color;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author michal
 */
class ResultsPanel extends JPanel implements Serializable {
	private JLabel statusLabel, timeLabel;
	private int timeElapsed;
	private ResultsTimer timer;

	public ResultsPanel() {
		statusLabel = new JLabel();
		timeLabel = new JLabel();
		add(statusLabel);
		add(timeLabel);

		setBackground(new Color(0xDDDDDD));
	}

	public void newGame() {
		statusLabel.setText("Nová hra");
		timeElapsed = 0;
		if(timer == null || !timer.isAlive()) {
			timer = new ResultsTimer();
			timer.start();
		}
	}

	public void repeatGame() {
		statusLabel.setText("Opakovanie hry");
		timeElapsed = 0;
		if(timer == null || !timer.isAlive()) {
			timer = new ResultsTimer();
			timer.start();
		}
	}

	public void pauseGame() {
		timer.update = false;
		statusLabel.setText("Pauza");
	}

	public void resumeGame() {
		statusLabel.setText("Pokračovanie");
		if(timer == null || !timer.isAlive()) {
			timer = new ResultsTimer();
			timer.start();
		}

	}

	public void pairFound(int human, int comp) {
		statusLabel.setText("Nájdených dvojíc: "+human+", počítač: "+comp);
	}

	public void gameOver(int human, int comp) {
		String status = "vyhral si";
		if(comp > human) {
			status = "vyhral počítač";
		}
		if(comp == human) {
			status = "remíza s počítačom";
		}
		statusLabel.setText("Koniec hry, "+status);
		System.out.println("Koniec hry, "+status);
		timer.update = false;
	}



	private class ResultsTimer extends Thread {
		public volatile boolean update = true;

		public void run() {
			while(update) {
				try {
					timeElapsed++;
					String minutes = timeElapsed/60 < 10 ? "0"+timeElapsed/60 : ""+timeElapsed/60;
					String seconds = timeElapsed%60 < 10 ? "0"+timeElapsed%60 : ""+timeElapsed%60;
					timeLabel.setText("- "+minutes +":"+seconds);
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

}
