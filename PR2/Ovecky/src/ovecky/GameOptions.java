/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky;

import java.awt.Dimension;
import ovecky.Point;
import java.util.ArrayList;

/**
 *
 * @author michal
 */
class GameOptions {
	// map size
	// initial location of all entities:
	// - sheeps

	public ArrayList<Point> getSheep() {
		// create a temp grid 
		ArrayList<Point> arr = new ArrayList<Point>();
		for(int i = 0; i<=3; i++) {
			for(int j = 0; j<=4; j++) {
				arr.add(new Point(
					200+i*100,
					200+j*100
				));
			}
		}
		return arr;
	}

	public Point getDog() {
		return new Point(200,200);
	}

	Dimension getMapSize() {
		return new Dimension(1280, 800);
	}

	Point getMapPosition() {
		return new Point(0,0);
	}

	boolean getUseMouse() {
		return false;
	}
}
