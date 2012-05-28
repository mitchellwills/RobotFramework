package robot.io.value;

import robot.io.*;

/**
 * An input that has a single value associated with it
 *
 * @author Mitchell
 * 
 */
public interface InputValue extends Input{
	/**
	 * @return the value of the input
	 */
	public double getValue();
}
