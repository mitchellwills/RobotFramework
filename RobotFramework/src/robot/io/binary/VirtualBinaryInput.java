package robot.io.binary;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A Binary input whose value can be set
 *
 */
public class VirtualBinaryInput implements BinaryInput {
	
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private boolean value = false;

	@Override
	public boolean get() {
		return value;
	}

	@Override
	public double getValue() {
		return get()?1:0;
	}
	
	/**
	 * Set the value that the binary input returns
	 * @param value
	 */
	public void set(final boolean value) {
		this.value = value;
		model.fireUpdateEvent();
	}

}
