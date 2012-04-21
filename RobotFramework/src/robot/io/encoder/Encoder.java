package robot.io.encoder;

import robot.io.Input;
import robot.io.UpdatableObject;


/**
 * An encoder
 * 
 * @author Mitchell
 *
 */
public interface Encoder extends Input, UpdatableObject {
	/**
	 * @return the position
	 */
	public int getPosition();
}
