package robot.simple;

import robot.Robot;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryOutput;

public class DigitalOutput implements BinaryOutput {
	private final BinaryOutput output;
	public DigitalOutput(Robot robot, String location) {
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

}
