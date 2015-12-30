package dubins;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Tuple3d;

public class Euclidean2dYawPoint extends Point3d {

	public Euclidean2dYawPoint(Point2d position, double yaw) {
		super(position.x, position.y, yaw);
	}

	public Euclidean2dYawPoint(double x, double y, double z) {
		super(x, y, z);
	}

	public Euclidean2dYawPoint(Tuple3d t1) {
		super(t1);
	}

	public Point2d getPosition() {
		return new Point2d(x,y);
	}

	/* Yaw in radians: (-pi/2 to +pi/2) */
	public double getYaw() {
		return z;
	}

}
