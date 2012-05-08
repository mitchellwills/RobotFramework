package robot.io.accelerometer;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * Represents a multi-axis accelerometer
 *
 * @author Mitchell
 * 
 */
public interface Accelerometer extends Input, UpdatableObject{
	/**
	 * @param axis
	 * @return the linear acceleration of that axis
	 */
	public double getLinearAcceleration(int axis); 
	
	/**
	 * @return the number of axes that this accelerometer supports
	 */
	public int getNumAccelerometerAxes();
}
