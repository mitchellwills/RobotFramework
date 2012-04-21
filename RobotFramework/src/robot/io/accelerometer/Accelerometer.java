package robot.io.accelerometer;

import java.util.Set;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * Represents a single axis accelerometer
 *
 */
public interface Accelerometer extends Input, UpdatableObject{
	/**
	 * @author Mitchell
	 * 
	 * The axis of an accelerometer
	 * All are perpendicular
	 *
	 */
	public static enum AccelerometerAxis{
		/**
		 * The X axis
		 */
		XAxis,
		/**
		 * The Y Axis
		 */
		YAxis,
		/**
		 * The Z axis
		 */
		ZAxis;
	}
	/**
	 * @param axis the axis whose value to get
	 * @return the value of that axis
	 */
	public double getAcceleration(AccelerometerAxis axis); 
	
	/**
	 * @return the axes that this accelerometer supports
	 */
	public Set<AccelerometerAxis> getAxes();
}
