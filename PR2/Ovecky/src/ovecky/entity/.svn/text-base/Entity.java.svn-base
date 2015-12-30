/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky.entity;

import java.awt.Dimension;
import java.awt.Graphics;
import ovecky.Manager;
import ovecky.Point;

/**
 *
 * @author michal
 */
abstract public class Entity {
	Point coordinate;
	Dimension size;
	
	public Dimension getSize() {
		return size;
	}

	public Point getCoordinate() {
		return coordinate;
	}

	protected Manager getManager() {
		return Manager.getInstance();
	}

	public boolean isOverlapping(Entity other) {
		return
			(coordinate.x < other.coordinate.x+other.size.width
			&& coordinate.x+size.width > other.coordinate.x
			&& coordinate.y < other.coordinate.y+other.size.height
			&& coordinate.y+size.height > other.coordinate.y);
	}

	public boolean contains(Entity other) {
		return
			(coordinate.x <= other.coordinate.x)
			&& (coordinate.y <= other.coordinate.y)
			&& (coordinate.x+size.width >= other.coordinate.x+other.size.width)
			&& (coordinate.y+size.height >= other.coordinate.y+other.size.height);
	}


	public boolean toBeRemoved = false;
	public void remove() {
		this.toBeRemoved = true;
	}

	abstract public void run();
	abstract public void paint(Graphics g);
}
