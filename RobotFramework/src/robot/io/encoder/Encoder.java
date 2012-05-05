package robot.io.encoder;

import robot.io.UpdatableObject;
import robot.io.value.InputValue;


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
