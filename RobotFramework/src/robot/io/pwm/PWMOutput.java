package robot.io.pwm;

import robot.io.UpdatableObject;
import robot.io.value.OutputValue;


/**
 * @author Mitchell
 * 
 * An output that takes a value the represents the duty cycle
 *
 */
public interface PWMOutput extends OutputValue, UpdatableObject {
	/**
	 * name of the parameter in factories param map corresponding to the PWM outputs frequency
	 */
	String PARAM_FREQUENCY = "frequency";

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
