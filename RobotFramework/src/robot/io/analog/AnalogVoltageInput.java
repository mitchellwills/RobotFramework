package robot.io.analog;

import robot.control.ControlLoopInput;
import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * An input that reads a voltage
 *
 */
public interface AnalogVoltageInput extends Input, ControlLoopInput, UpdatableObject{
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
