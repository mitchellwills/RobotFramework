package robot.io.accelerometer;

import robot.io.*;

/**
 * Represents a multi-axis accelerometer
 *
 * @author Mitchell
 * 
 */
public interface Accelerometer extends Input, UpdatableObject{
	/**
	 * name of the parameter in factories param map corresponding to the number of axes on the accelerometer
	 */
	String PARAM_NUM_AXES = "numAxes";

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
