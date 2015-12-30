/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky;

import java.awt.event.KeyEvent;

/**
 *
 * @author michal
 */
public class KeyOptions {
	static int PAUSE_GAME = KeyEvent.VK_ESCAPE;

	int MOVE_RIGHT = KeyEvent.VK_RIGHT;
	public boolean isMoveRight = false;

	int MOVE_LEFT = KeyEvent.VK_LEFT;
	public boolean isMoveLeft = false;

	int MOVE_UP = KeyEvent.VK_UP;
	public boolean isMoveUp = false;

	int MOVE_DOWN = KeyEvent.VK_DOWN;
	public boolean isMoveDown = false;

	int ACCELERATE = KeyEvent.VK_SHIFT;
	public boolean isAccelerate = false;

	int MOVE_MAP = KeyEvent.VK_CONTROL;
	public boolean isMoveMap = false;

	public static KeyOptions getPlayer1() {
		return new KeyOptions();
	}
	public static KeyOptions getPlayer2() {
		KeyOptions keyOptions = new KeyOptions();
		keyOptions.MOVE_RIGHT = KeyEvent.VK_D;
		keyOptions.MOVE_LEFT = KeyEvent.VK_A;
		keyOptions.MOVE_UP = KeyEvent.VK_W;
		keyOptions.MOVE_DOWN = KeyEvent.VK_S;
		keyOptions.ACCELERATE = KeyEvent.VK_E;
		keyOptions.MOVE_MAP = KeyEvent.VK_Q;
		return keyOptions;
	}

	public void updateKeyPressedState(KeyEvent e) {
		if(e.getKeyCode() == MOVE_DOWN)  isMoveDown = true;
		if(e.getKeyCode() == MOVE_UP)    isMoveUp = true;
		if(e.getKeyCode() == MOVE_LEFT)  isMoveLeft = true;
		if(e.getKeyCode() == MOVE_RIGHT) isMoveRight = true;
		if(e.getKeyCode() == ACCELERATE) isAccelerate = true;
		if(e.getKeyCode() == MOVE_MAP)   isMoveMap = true;
	}

	public void updateKeyReleasedState(KeyEvent e) {
		if(e.getKeyCode() == MOVE_DOWN)  isMoveDown = false;
		if(e.getKeyCode() == MOVE_UP)    isMoveUp = false;
		if(e.getKeyCode() == MOVE_LEFT)  isMoveLeft = false;
		if(e.getKeyCode() == MOVE_RIGHT) isMoveRight = false;
		if(e.getKeyCode() == ACCELERATE) isAccelerate = false;
		if(e.getKeyCode() == MOVE_MAP)   isMoveMap = false;
	}

	@Override
	public String toString() {
		return "KeyOptions{" + "isMoveRight=" + isMoveRight + "isMoveLeft=" + isMoveLeft + "isMoveUp=" + isMoveUp + "isMoveDown=" + isMoveDown + "isAccelerate=" + isAccelerate + '}';
	}


}
