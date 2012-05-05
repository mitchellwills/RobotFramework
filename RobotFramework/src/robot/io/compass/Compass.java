package robot.io.compass;

import robot.io.UpdatableObject;
import robot.io.value.InputValue;


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
