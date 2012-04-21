package robot.io.parallax;

import robot.io.analog.AnalogVoltageInput;
import robot.io.dutycycle.DutyCycleInput;

/**
 * @author Mitchell
 * 
 * Represents the Memsic 2125 Accelerometer
 *
 */
public class Memsic2125{
	private DutyCycleInput xAxis;
	private final DutyCycleInput yAxis;
	private final AnalogVoltageInput temp;
	/**
	 * 
	 * Create a new Accelerometer
	 * 
	 * @param xAxis
	 * @param yAxis
	 * @param temp
	 */
	public Memsic2125(DutyCycleInput xAxis, DutyCycleInput yAxis, AnalogVoltageInput temp){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.temp = temp;
	}
	
	private double getAxisValue(DutyCycleInput input){
		return (input.getDutyCycle()-.5)/0.125;
	}
	
	/**
	 * @return the acceleration of the x axis (in Gs)
	 */
	public double getXAxis(){
		return getAxisValue(xAxis);
	}
	
	/**
	 * @return the acceleration of the y axis (in Gs)
	 */
	public double getYAxis(){
		return getAxisValue(yAxis);
	}

	/**
	 * @return the temperature in degrees C
	 */
	public double getTemp() {
		if(temp==null)
			return Double.NaN;
		return (temp.getVoltage()-1.25)/.05 + 25;
	}
}
