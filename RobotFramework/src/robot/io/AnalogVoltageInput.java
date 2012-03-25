package robot.io;

/**
 * @author Mitchell
 * 
 * An analog input
 *
 */
public abstract class AnalogVoltageInput implements Input{
	/**
	 * @return a value between 0.0 and 1.0 of the percentage of the max voltage the input is
	 */
	public abstract double get();
	/**
	 * @return the maximum voltage of the analog input (when get() will return 1.0)
	 */
	public abstract double getMaxVoltage();
	
	/**
	 * @return the current input voltage
	 */
	public double getVoltage(){
		return get() * getMaxVoltage();
	}
}
