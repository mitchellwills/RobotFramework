package robot.simple;

import robot.Robot;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryInput;

public class DigitalInput implements BinaryInput {
	private final BinaryInput input;
	public DigitalInput(Robot robot, String location) {
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

}
