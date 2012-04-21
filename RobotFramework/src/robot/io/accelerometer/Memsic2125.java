package robot.io.accelerometer;

import robot.io.analog.AnalogVoltageInput;
import robot.io.dutycycle.DutyCycleInput;

/**
 * @author Mitchell
 * 
 * Represents the Memsic 2125 Accelerometer
 *
 */
public class Memsic2125 {
	private DutyCycleInput xAxis;
	private final DutyCycleInput yAxis;
	private final AnalogVoltageInput temp;
	public Memsic2125(DutyCycleInput xAxis, DutyCycleInput yAxis, AnalogVoltageInput temp){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.temp = temp;
	}
	
	private double getAxisValue(DutyCycleInput input){
		return (input.getDutyCycle()-.5)/0.125;
	}
	
	public double getXAxis(){
		return getAxisValue(xAxis);
	}
	
	public double getYAxis(){
		return getAxisValue(yAxis);
	}

	public double getTemp() {
		return (temp.getVoltage()-1.25)/.05 + 25;
	}
}
