package robot.io.pwm;

import robot.io.Output;
import robot.io.UpdatableObject;


/**
 * @author Mitchell
 * 
 * An output that takes a value the represents the duty cycle
 *
 */
public interface PWMOutput extends Output, UpdatableObject {
	/**
	 * @param dutyCycle duty cycle (0.0 - 1.0)
	 */
	public void set(double dutyCycle);
	/**
	 * @return the current duty cycle (0.0 - 1.0)
	 */
	public double get();
	
	/**
	 * @return the frequency that is used to output the duty cycle
	 */
	public double getFrequency();
}
