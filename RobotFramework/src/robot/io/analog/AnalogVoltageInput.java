package robot.io.analog;

import robot.io.UpdatableObject;
import robot.io.value.InputValue;

/**
 * @author Mitchell
 * 
 * An input that reads a voltage
 *
 */
public interface AnalogVoltageInput extends InputValue, UpdatableObject{
	/**
	 * name of the parameter in factories param map corresponding to the voltage inputs maximum voltage
	 */
	String PARAM_MAX_VOLTAGE = "maxVoltage";
	
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
