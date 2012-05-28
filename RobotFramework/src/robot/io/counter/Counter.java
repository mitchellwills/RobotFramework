package robot.io.counter;

import robot.io.*;
import robot.io.value.*;

/**
 * @author Mitchell
 * 
 * An object that counts something
 *
 */
public interface Counter  extends InputValue, UpdatableObject {

	/**
	 * @return the value of the counter
	 */
	public int getCount();
}
