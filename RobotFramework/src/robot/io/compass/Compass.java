package robot.io.compass;

import robot.io.*;
import robot.io.value.*;


/**
 * A compass
 * 
 * @author Mitchell
 *
 */
public interface Compass extends InputValue, UpdatableObject {
	/**
	 * @return the angle from North
	 * 90 would correspond to 
	 */
	public double getHeading();
}
