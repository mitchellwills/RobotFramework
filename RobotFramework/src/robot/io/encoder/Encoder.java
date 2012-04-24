package robot.io.encoder;

import robot.io.InputValue;
import robot.io.UpdatableObject;


/**
 * An encoder
 * 
 * @author Mitchell
 *
 */
public interface Encoder extends InputValue, UpdatableObject {
	/**
	 * @return the position
	 */
	public int getPosition();
}
