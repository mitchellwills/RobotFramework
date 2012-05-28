package robot.io.encoder;

import robot.io.*;
import robot.io.value.*;


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
