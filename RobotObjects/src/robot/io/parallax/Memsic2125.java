package robot.io.parallax;

import robot.error.*;
import robot.io.*;
import robot.io.accelerometer.*;
import robot.io.analog.*;
import robot.io.dutycycle.*;
import robot.io.factory.old.*;

/**
 * @author Mitchell
 * 
 * Represents the Memsic 2125 Accelerometer
 *
 */
public class Memsic2125 implements Accelerometer{
	private DutyCycleInput xAxis;
	private final DutyCycleInput yAxis;
	private final AnalogVoltageInput temp;
	

	private final ForwardingRobotObjectModel model = new ForwardingRobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	
	/**
	 * 
	 * Create a new Accelerometer
	 * 
	 * @param xAxis
	 * @param yAxis
	 * @param temp
	 */
	@FactoryConstructable
	public Memsic2125(DutyCycleInput xAxis, DutyCycleInput yAxis, AnalogVoltageInput temp){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.temp = temp;
		xAxis.addUpdateListener(model);
		yAxis.addUpdateListener(model);
		temp.addUpdateListener(model);
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

	@Override
	public double getLinearAcceleration(int axis) {
		if(axis==0)
			return getXAxis();
		else if(axis==1)
			return getYAxis();
		throw new RobotException("The Memsic 2125 Accelerometer only has X and Y axes");
	}

	@Override
	public int getNumAccelerometerAxes() {
		return 2;
	}
}
