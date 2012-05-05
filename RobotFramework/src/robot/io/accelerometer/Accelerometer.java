package robot.io.accelerometer;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * Represents a multi-axis accelerometer
 *
 */
public interface Accelerometer extends Input, UpdatableObject{
	/**
	 * @param axis the axis whose value to get
	 * @return the value of that axis
	 */
	public double getAcceleration(int axis); 
	
	/**
	 * @return the number of axes that this accelerometer supports
	 */
	public int getNumAxes();
}
