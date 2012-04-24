package robot.simple;

import robot.Robot;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryOutput;

/**
 * @author Mitchell
 * 
 * A Digital Output on the device
 *
 */
public class DigitalOutput implements BinaryOutput {
	private final BinaryOutput output;
	/**
	 * Create a new Digital output
	 * @param robot
	 * @param location
	 */
	public DigitalOutput(final Robot robot, final String location) {
		output = robot.getFactory().getBinaryOutput(location);
	}

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		output.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		output.removeUpdateListener(listener);
	}

	@Override
	public void set(boolean value) {
		output.set(value);
	}

	@Override
	public boolean get() {
		return output.get();
	}

	@Override
	public void setValue(double value) {
		set(value!=0);
	}

	@Override
	public double getValue() {
		return get()?1:0;
	}

}
