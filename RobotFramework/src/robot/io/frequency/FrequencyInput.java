package robot.io.frequency;

import robot.io.*;
import robot.io.value.*;

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
