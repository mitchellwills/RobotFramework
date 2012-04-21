package robot.io.accelerometer;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * Represents a single axis accelerometer
 *
 */
public interface Accelerometer extends Input, UpdatableObject{
	public static enum AccelerometerAxis{
		XAxis,
		YAxis,
		ZAxis;
	}
	public double getAcceleration(AccelerometerAxis axis); 
}
