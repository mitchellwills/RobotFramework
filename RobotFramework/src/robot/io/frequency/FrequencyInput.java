package robot.io.frequency;

import robot.io.UpdatableObject;
import robot.io.value.InputValue;

/**
 * @author Mitchell
 * 
 * An input that gives the frequency of a pulse input
 *
 */
public interface FrequencyInput extends InputValue, UpdatableObject{
	/**
	 * @return the frequency of pulses
	 */
	public long getFrequency();
}
