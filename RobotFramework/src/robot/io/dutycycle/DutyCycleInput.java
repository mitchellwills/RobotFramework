package robot.io.dutycycle;

import robot.io.InputValue;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * An input that gives the duty cycle of pulses
 *
 */
public interface DutyCycleInput extends InputValue, UpdatableObject{
	/**
	 * @return a value between 0.0 and 1.0 representing the duty cycle of the pulses on a pin
	 */
	public double getDutyCycle();
}
