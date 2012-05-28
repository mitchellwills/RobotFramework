package robot.io.gyro;

import robot.io.*;

/**
 * Represents a multi-axis gyroscope
 *
 * @author Mitchell
 * 
 */
public interface Gyroscope extends Input, UpdatableObject{
	/**
	 * name of the parameter in factories param map corresponding to the number of axes on the gyroscope
	 */
	String PARAM_NUM_AXES = "numAxes";
	
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
