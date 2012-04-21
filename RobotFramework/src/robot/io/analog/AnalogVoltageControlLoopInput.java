package robot.io.analog;

import robot.control.ControlLoopInput;
import robot.io.RobotObjectListener;

/**
 * @author Mitchell
 * 
 * Creates a control loop input from an analog voltage input
 *
 */
public class AnalogVoltageControlLoopInput implements ControlLoopInput, AnalogVoltageInput {

	private final AnalogVoltageInput input;
	/**
	 * Create a control loop input
	 * @param input
	 */
	public AnalogVoltageControlLoopInput(AnalogVoltageInput input){
		this.input = input;
	}
	
	@Override
	public double get() {
		return getVoltage();
	}

	@Override
	public void addUpdateListener(
			RobotObjectListener<AnalogVoltageInput> listener) {
		input.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(
			RobotObjectListener<AnalogVoltageInput> listener) {
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

}
