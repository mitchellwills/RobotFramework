package robot.io.frequency;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * An input that gives the frequency of a pulse input
 *
 */
public interface FrequencyInput extends Input, UpdatableObject{
	/**
	 * @return the frequency of pulses
	 */
	public long getFrequency();
}
