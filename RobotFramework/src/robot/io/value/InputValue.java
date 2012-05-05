package robot.io.value;

import robot.io.Input;

/**
 * @author Mitchell
 * 
 * An input that has a single value associated with it
 *
 */
public interface InputValue extends Input{
	/**
	 * @return the value of the input
	 */
	public double getValue();
}
