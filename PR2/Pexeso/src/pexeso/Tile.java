/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexeso;

import java.awt.Color;
import java.awt.Cursor;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;

/**
 *
 * @author michal
 */
public class Tile extends JButton implements Serializable {
	private int id;
	private int position;
	private boolean show = true, done = false;

	public Tile(int id) {
		this.id = id;
		TileMap.getIcon(id);
		setBorderPainted(false);
		setBackground(Color.white);
		setShow(false);
		setFocusable(false);
		//setPreferredSize(new Dimension(50,50));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}

	public void setShow(boolean show) {
		if(done) return;

		this.show = show;
		if(show) {
			setIcon(TileMap.getIcon(id));
		} else {
			setIcon(TileMap.getIcon(TileMap.HIDDEN_TILE));
		}
	}

	public void setDone(boolean is) {
		if(is) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					setVisible(false);
				}
			}, 500);
		} else {
			setVisible(true);
		}
		done = is;
	}

	public boolean isDone() {
		return done;
	}

	void setFocused(boolean b) {
		if(done) return;
		
		if(b) {
			setBackground(new Color(0xDDDDDD));
		} else {
			setBackground(Color.white);
		}
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Tile{" + "id=" + id + "position=" + position + '}';
	}

	

}
