package robot.io.gyro;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * Represents a multi-axis gyroscope
 *
 * @author Mitchell
 * 
 */
public interface Gyroscope extends Input, UpdatableObject{
	/**
	 * @param axis
	 * @return the value of that axis
	 */
	public double getAngularAcceleration(int axis);
	
	/**
	 * @return the number of axes that this gyroscope supports
	 */
	public int getNumGyroAxes();
}
