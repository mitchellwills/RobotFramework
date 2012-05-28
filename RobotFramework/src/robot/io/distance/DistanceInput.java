package robot.io.distance;

import robot.io.*;
import robot.io.value.*;

/**
 * An object that measures a distance
 * 
 * @author Mitchell
 *
 */
public interface DistanceInput extends InputValue, UpdatableObject{
	/**
	 * @return the distance in meters
	 */
	public double getDistance();
}
