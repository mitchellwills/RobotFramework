package robot.io.binary;

import robot.io.Input;


/**
 * @author Mitchell
 * 
 * an input that can be active of not active (such as a digital input pin)
 *
 */
public interface BinaryInput extends Input{
	/**
	 * @return true if the input is active
	 */
	public boolean get();
}