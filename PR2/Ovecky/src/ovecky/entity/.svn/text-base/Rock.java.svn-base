/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import ovecky.Point;

/**
 *
 * @author michal
 */
public class Rock extends Entity implements StaticEntity {

	public Rock(Point position, Dimension size) {
		this.coordinate = position;
		this.size = size;
	}


	@Override
	public void run() {
	}

	@Override
	public void paint(Graphics g) {
		Point position = getManager().getPositionOnScreen(coordinate);

		g.setColor(Color.black);
		g.fillRect((int) position.x, (int) position.y, size.width, size.height);
	}

}
