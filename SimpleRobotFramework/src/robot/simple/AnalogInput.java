package robot.simple;

import robot.Robot;
import robot.io.RobotObjectListener;
import robot.io.analog.AnalogVoltageInput;

/**
 * @author Mitchell
 * 
 * An analog voltage input on the device
 *
 */
public class AnalogInput implements AnalogVoltageInput{

	private AnalogVoltageInput input;
	/**
	 * Create a new Analog Input
	 * @param robot
	 * @param location
	 */
	public AnalogInput(String location) {
		input = Robot.getInstance().getFactory().getAnalogVoltageInput(location);
	}
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		input.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		input.removeUpdateListener(listener);
	}
	@Override
	public double getMaxVoltage() {
		return input.getMaxVoltage();
	}
	@Override
	public double getVoltage() {
		return input.getVoltage();
	}
	@Override
	public double getValue() {
		return getVoltage();
	}

}
