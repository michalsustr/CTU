/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ovecky;

/**
 *
 * @author michal
 */
public class Point {

	public double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public void setLocation(Point other) {
		this.x = other.x;
		this.y = other.y;
	}

	public double distance(Point other) {
		return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
	}

	public void translate(int dx, int dy) {
		translate((double) dx, (double) dy);
	}

	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}

	public void translate(Point translationPoint) {
		x += translationPoint.x;
		y += translationPoint.y;
	}

	/**
	 * Rotate this point by a specified angle
	 * @param angle
	 */
	public void rotate(double angle) {
		this.x = (x * Math.cos(angle) - y * Math.sin(angle));
		this.y = (x * Math.sin(angle) + y * Math.cos(angle));
	}

	/**
	 * Get the angle between (0,0) and this point in radians
	 * @param point
	 * @return double angle
	 */
	public double angle() {
		double angle;
		if (x == 0) { // prevent division by zero
			angle = Math.PI / 2;
			// correction around x-axis
			if (y < 0) {
				angle += Math.PI;
			}
		} else {
			angle = Math.atan(y / x);
			// correction around y axis
			if (x < 0) {
				angle += Math.PI;
			}
		}
		return angle;
	}

	@Override
	public Point clone() {
		return new Point(this.x, this.y);
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ']';
	}




}
