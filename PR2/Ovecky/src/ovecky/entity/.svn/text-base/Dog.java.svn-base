/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ovecky.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import ovecky.KeyOptions;
import ovecky.Manager;
import ovecky.Manager.Edge;
import ovecky.Point;

/**
 *
 * @author michal
 */
public class Dog extends Chaser {
	private KeyOptions keys;
	/**
	 * Direction at which dog should be moving (user's input)
	 * Null for not moving
	 */
	private Double direction = null;

	private boolean isRunning = false;

	/**
	 * Speed at which dog moves
	 */
	private int speedNormal = 7;
	private int speedRunning = 20;


	private Point lastCoordinate;


	/**
	 * Possible directions for {@see direction}
	 */
	public static double
		RIGHT = 0,
		UP_RIGHT = Math.PI / 4,
		UP = Math.PI / 2,
		UP_LEFT = 3*Math.PI/4,
		LEFT = Math.PI,
		DOWN_LEFT = 5*Math.PI/4,
		DOWN = 3*Math.PI/2,
		DOWN_RIGHT = 7*Math.PI/4;

	public Dog(Point point, KeyOptions keysOpts) {
		coordinate = point;
		size = new Dimension(10,10);
		keys = keysOpts;
	}

	@Override
	public void run() {
		if(direction != null) {
			lastCoordinate = coordinate.clone();

			double moveX = Math.cos(direction)*getSpeed();
			double moveY = -Math.sin(direction)*getSpeed();

			coordinate.translate(moveX, moveY);
			getManager().correctPosition(this);

			Edge edge = getManager().getEdgeOfScreen(coordinate);
			if(edge != null &&
				// <editor-fold defaultstate="collapsed" desc=" edge conditions ">
				// bottom
				((edge == Edge.BOTTOM || edge == Edge.BOTTOM_LEFT || edge == Edge.BOTTOM_RIGHT)
				&& (direction == DOWN || direction == DOWN_LEFT || direction == DOWN_RIGHT))
				// top
				|| ((edge == Edge.TOP || edge == Edge.TOP_LEFT || edge == Edge.TOP_RIGHT)
				&& (direction == UP || direction == UP_LEFT || direction == UP_RIGHT))
				// left
				|| ((edge == Edge.LEFT || edge == Edge.TOP_LEFT || edge == Edge.BOTTOM_LEFT)
				&& (direction == LEFT || direction == UP_LEFT || direction == DOWN_LEFT))
				// right
				|| ((edge == Edge.RIGHT || edge == Edge.TOP_RIGHT || edge == Edge.BOTTOM_RIGHT)
				&& (direction == RIGHT || direction == UP_RIGHT || direction == DOWN_RIGHT))
				// </editor-fold>
			) {
				getManager().moveMapPosition((int) moveX, (int) moveY);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		Point coordinate = this.coordinate.clone();
		coordinate = Manager.getInstance().getPositionOnScreen(coordinate);

		g.setColor(Color.RED);
		g.fillRect((int) coordinate.x, (int) coordinate.y, size.width, size.height);
	}

	
	public void updateKeyState() {
		if(keys.isMoveUp && !keys.isMoveDown && !keys.isMoveLeft && !keys.isMoveRight) {
			setDirection(Dog.UP);
		}
		if(!keys.isMoveUp && keys.isMoveDown && !keys.isMoveLeft && !keys.isMoveRight) {
			setDirection(Dog.DOWN);
		}
		if(!keys.isMoveUp && !keys.isMoveDown && keys.isMoveLeft && !keys.isMoveRight) {
			setDirection(Dog.LEFT);
		}
		if(!keys.isMoveUp && !keys.isMoveDown && !keys.isMoveLeft && keys.isMoveRight) {
			setDirection(Dog.RIGHT);
		}
		if(keys.isMoveUp && !keys.isMoveDown && keys.isMoveLeft && !keys.isMoveRight) {
			setDirection(Dog.UP_LEFT);
		}
		if(keys.isMoveUp && !keys.isMoveDown && !keys.isMoveLeft && keys.isMoveRight) {
			setDirection(Dog.UP_RIGHT);
		}
		if(!keys.isMoveUp && keys.isMoveDown && keys.isMoveLeft && !keys.isMoveRight) {
			setDirection(Dog.DOWN_LEFT);
		}
		if(!keys.isMoveUp && keys.isMoveDown && !keys.isMoveLeft && keys.isMoveRight) {
			setDirection(Dog.DOWN_RIGHT);
		}
		if(!keys.isMoveUp && !keys.isMoveDown && !keys.isMoveLeft && !keys.isMoveRight) {
			setDirection(null);
		}
		setIsRunning(keys.isAccelerate);

		if(keys.isMoveMap && direction != null) { // move map only
			int dx = (int) (Math.cos(direction) * getSpeed());
			int dy = (int) (-Math.sin(direction) * getSpeed());
			setDirection(null);
			getManager().moveMapPosition(dx, dy);
			return;
		}
	}

	public void updateMouseState(MouseEvent e, boolean running) {
		throw new UnsupportedOperationException();
		/*setIsRunning(running);
		Point position = getInstance().getPositionOnMap(new Point(e.getXOnScreen(), e.getYOnScreen()));
		Point zeroPosition = position.clone();
		zeroPosition.x -= coordinate.x;
		zeroPosition.y = coordinate.y - position.y;
		if(coordinate.distance(position) < 15) {
			coordinate = position;
			setDirection(null);
		} else {
			setDirection(zeroPosition.angle());
		}*/
	}

	public Point locationDifference() {
		if(lastCoordinate == null) {
			return new Point(0,0);
		}

		return new Point(coordinate.x - lastCoordinate.x, coordinate.y - lastCoordinate.y);
	}

	// <editor-fold defaultstate="collapsed" desc=" Getters & Setters ">
	public KeyOptions getKeys() {
		return keys;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(Double direction) {
		this.direction = direction;
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public int getSpeed() {
		return isRunning ? speedRunning : speedNormal;
	}
	// </editor-fold>
}
