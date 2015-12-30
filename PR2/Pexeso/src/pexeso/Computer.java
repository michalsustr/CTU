/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pexeso;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author michal
 */
public class Computer {
	/**
	 * # of pexeso cards computer should remember, -1 to remeber all cards
	 */
	private int difficulty = INSANE;

	public static final int INSANE = -1, HARD = 12, MEDIUM = 8, EASY = 4;

	private List<Tile> positions = new ArrayList<Tile>();

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	void rememberPosition(Tile tile) {
		if(difficulty == 0) return;

		boolean unique = true;
		for(Tile position : positions) {
			if(position == tile) unique = false;
		}
		if(unique) {
			positions.add(tile);
		}

		if(difficulty != -1) {
			while(positions.size() > difficulty) {
				positions.remove(0);
			}
		}
	}

	void forgetPosition(Tile tile) {
		positions.remove(tile);
	}

	/**
	 * Get a turn that the computer may do
	 * @param tm
	 * @return 2-item Tile[] array
	 */
	Tile[] takeTurn(TileMap tm) {
		Tile[] ret = new Tile[2];
		ret[0] = null;
		ret[1] = null;
		for(int i = positions.size()-1; i >= 0; i--) {
			Tile one = positions.get(i);
			for(int j = positions.size()-1; j >= 0; j--) {
				Tile two = positions.get(j);
				if(one.getId() == two.getId() && one != two) {
					ret[0] = one;
					ret[1] = two;
					forgetPosition(one);
					forgetPosition(two);
					break;
				}
			}
			if(ret[0] != null) break;
		}

		if(ret[0] == null) { // choose some tiles
			int i=0;
			for(Component comp : tm.getComponents()) {
				Tile tile = (Tile) comp;
				if(tile.isDone()) continue;
				if(positions.indexOf(tile) == -1) {
					ret[i] = tile;
					rememberPosition(tile);
					i++;
					if(i == 2) break;
				}
			}
			if(i == 1) {
				ret[1] = (Tile) positions.iterator().next();
			}

		}

		return ret;
	}

	
}
