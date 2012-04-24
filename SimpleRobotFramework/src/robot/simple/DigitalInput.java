package robot.simple;

import robot.Robot;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryInput;

/**
 * @author Mitchell
 * 
 * A Digital input on the device
 *
 */
public class DigitalInput implements BinaryInput {
	private final BinaryInput input;
	/**
	 * Create a new Digital Input
	 * @param robot
	 * @param location
	 */
	public DigitalInput(final Robot robot, final String location) {
		input = robot.getFactory().getBinaryInput(location);
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
	public boolean get() {
		return input.get();
	}

	@Override
	public double getValue() {
		return get()?1:0;
	}

}
