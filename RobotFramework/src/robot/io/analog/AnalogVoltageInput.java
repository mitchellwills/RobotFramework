package robot.io.analog;

import robot.io.Input;
import robot.pid.PIDInput;

/**
 * @author Mitchell
 * 
 * An input that reads a voltage
 *
 */
public interface AnalogVoltageInput extends Input, PIDInput{
	/**
	 * 
	 * @return The maximum voltage that can be read
	 */
	public double getMaxVoltage();
	/**
	 * @return The voltage the input is currently reading
	 * 
	 */
	public double getVoltage();
}
