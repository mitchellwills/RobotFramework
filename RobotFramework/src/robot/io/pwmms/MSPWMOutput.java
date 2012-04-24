package robot.io.pwmms;

import robot.io.OutputValue;
import robot.io.UpdatableObject;


/**
 * @author Mitchell
 * 
 * An output that takes a value in milliseconds that is the length of the pulses
 *
 */
public interface MSPWMOutput extends OutputValue, UpdatableObject {
	/**
	 * value to set the output to to disable the output
	 */
	public static final int DISABLED = -1;
	/**
	 * @param ms the length of the pulses in milliseconds
	 * -1 will disable the pulse output
	 */
	public void set(int ms);
	/**
	 * @return the current pulse length in milliseconds
	 * -1 means the pulse output is disabled
	 */
	public int get();
}
