package robot.math;

import java.awt.*;

/**
 * @author Mitchell
 * @author Brendan
 */
public class LinearVector implements Cloneable {
	private final double xComponent;
	private final double yComponent;

	/**
	 * A default constructor that initializes both values to zero
	 */
	public LinearVector() {
		this(0, 0);
	}

	/**
	 * A constructor that makes vector have the components given
	 * 
	 * @param x
	 *            The X component of the Vector
	 * @param y
	 *            The Y component of the Vector
	 */
	public LinearVector(int x, int y) {
		this((double) x, (double) y);
	}

	/**
	 * A constructor that makes vector have the components given
	 * 
	 * @param x
	 *            The X component of the Vector
	 * @param y
	 *            The Y component of the Vector
	 */
	public LinearVector(double x, double y) {
		xComponent = x;
		yComponent = y;
	}

	/**
	 * Makes a new vector from one point to another
	 * 
	 * @param startPoint
	 *            the location of the base of the vector
	 * @param endPoint
	 *            the location of the end of the vector
	 */
	public LinearVector(Point startPoint, Point endPoint) {
		this(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
	}

	@Override
	public LinearVector clone() {
		return new LinearVector(xComponent, yComponent);
	}

	/**
	 * Gets the X component of the Vector
	 * 
	 * @return X component
	 */
	public double getX() {
		return xComponent;
	}

	/**
	 * Gets the Y component of the Vector
	 * 
	 * @return Y component
	 */
	public double getY() {
		return yComponent;
	}

	/**
	 * Returns the magnitude of the vector
	 * 
	 * @return Magnitude
	 */
	public double getMagnitude() {
		return (Math.sqrt(Math.pow(xComponent, 2) + Math.pow(yComponent, 2)));
	}

	/**
	 * Produces the negative vector
	 * 
	 * @return Negated vector
	 */
	public LinearVector getNegative() {
		return new LinearVector(-xComponent, -yComponent);
	}

	/**
	 * @return true if this vector is the null vector (0, 0)
	 */
	public boolean isNullVector() {
		return (xComponent == 0) && (yComponent == 0);
	}

	/**
	 * returns a scaled vector without modifying the original one
	 * 
	 * @param k
	 *            the scaler value
	 * @return a scaled vector
	 */
	public LinearVector getScaler(double k) {
		return new LinearVector(xComponent * k, yComponent * k);
	}

	/**
	 * Produces a unit vector
	 * 
	 * @return The unit vector
	 */
	public LinearVector getUnitVector() {
		return new LinearVector(xComponent / getMagnitude(), yComponent
				/ getMagnitude());
	}

	public LinearVector getOrthogonalVector(double x) {
		if (yComponent == 0) {
			return (new LinearVector(0.0, -(Math.abs(xComponent) / xComponent)
					* (Math.abs(x) / x)));
		}
		double newSlope = -xComponent / yComponent;

		double tmpX = x * Math.abs(yComponent) / yComponent;
		return new LinearVector(tmpX, newSlope * tmpX);
	}

	/**
	 * Adds the two vectors together
	 * 
	 * @param vector1
	 *            the first vector
	 * @param vector2
	 *            the second vector
	 * @return a vector thats the addition of the two given
	 */
	public static LinearVector addVectors(LinearVector vector1,
			LinearVector vector2) {
		return new LinearVector(vector1.getX() + vector2.getX(), vector1.getY()
				+ vector2.getY());
	}

	/**
	 * produces the dot product of two vectors
	 * 
	 * @param vector1
	 *            the first vector
	 * @param vector2
	 *            the second vector
	 * @return the Dot Product
	 */
	public static double dotProduct(LinearVector vector1, LinearVector vector2) {
		return ((vector1.getX() * vector2.getX()) + (vector1.getY() * vector2
				.getY()));
	}

	/**
	 * Does a vector projection and projects vector a onto b
	 * 
	 * @param a
	 * @param b
	 * @return the projected vector
	 */
	public static LinearVector projectVector(LinearVector a, LinearVector b) {
		return b.getUnitVector().getScaler(
				LinearVector.dotProduct(a, b.getUnitVector()));
	}

	public static double scalarProjection(LinearVector a, LinearVector b) {
		if (a.isNullVector() || b.isNullVector())
			return 0;
		return LinearVector.dotProduct(a, b) / b.getMagnitude();
	}

	public LinearVector getScaledForRobot() {
		double x = xComponent;
		double y = yComponent;
		if ((x == 0.0) && (y == 0)) {
			return new LinearVector();
		}
		if (Math.abs(x) > Math.abs(y)) {
			x = Math.abs(x) / xComponent;
			y = y / Math.abs(xComponent);
		} else {
			x = x / Math.abs(yComponent);
			y = Math.abs(y) / yComponent;

		}

		return getScaler(1 / new LinearVector(x, y).getMagnitude());
	}

	@Override
	public String toString() {
		return "LinearVector [X Component: " + xComponent + " Y Component: "
				+ yComponent + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(xComponent);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yComponent);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinearVector other = (LinearVector) obj;
		if (Double.doubleToLongBits(xComponent) != Double
				.doubleToLongBits(other.xComponent))
			return false;
		if (Double.doubleToLongBits(yComponent) != Double
				.doubleToLongBits(other.yComponent))
			return false;
		return true;
	}
	
	

}
