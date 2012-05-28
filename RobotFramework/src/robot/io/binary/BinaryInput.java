package robot.io.binary;

import robot.io.*;
import robot.io.value.*;


/**
 * @author Mitchell
 * 
 * an input that can be active of not active (such as a digital input pin)
 *
 */
public interface BinaryInput extends InputValue, UpdatableObject{
	/**
	 * @return true if the input is active
	 */
	public boolean get();
}
