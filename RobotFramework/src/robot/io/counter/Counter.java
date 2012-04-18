package robot.io.counter;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * An object that counts something
 *
 */
public interface Counter  extends Input, UpdatableObject<Counter> {

	/**
	 * @return the value of the counter
	 */
	public int getCount();
}
