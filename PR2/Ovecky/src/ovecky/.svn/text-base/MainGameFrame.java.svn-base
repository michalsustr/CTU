/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author michal
 */
public class MainGameFrame extends JFrame {
	
	private GamePanel gamePanel;
	private Thread gameThread;

	public static void main(String[] args) {
        // TODO: open main menu first
		MainGameFrame game = new MainGameFrame();

    }

	public GraphicsDevice device;


	public MainGameFrame() {
		// init window options
		setTitle("Sheeps");
		setUndecorated(true);
		setFocusable(true);
		// fullscreen
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = env.getDefaultScreenDevice();
		device.setFullScreenWindow(this);

		this.setIgnoreRepaint(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(this);

		// add game panel
		gamePanel = GamePanel.getInstance(getScreenSize());
		add(gamePanel);
		addKeyListener(gamePanel);
		addMouseMotionListener(gamePanel);

		gameThread = new Thread(gamePanel);
		gameThread.start();

		setVisible(true);
	}

	Dimension getScreenSize() {
		return new Dimension(
			this.device.getDisplayMode().getWidth(),
			this.device.getDisplayMode().getHeight()
		);
	}


	// utils
	public static int randomNumber(int min, int max) {
		return min + (new Random()).nextInt(max-min);
	}

	
}
