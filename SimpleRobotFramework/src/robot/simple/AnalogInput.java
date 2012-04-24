package robot.simple;

import robot.Robot;
import robot.io.RobotObjectListener;
import robot.io.analog.AnalogVoltageInput;

public class AnalogInput implements AnalogVoltageInput{

	private AnalogVoltageInput input;
	public AnalogInput(Robot robot, String location) {
		input = robot.getFactory().getAnalogVoltageInput(location);
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
