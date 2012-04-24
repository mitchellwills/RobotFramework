package robot.io.binary;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual Binary output implementation
 *
 */
public class VirtualBinaryOutput implements BinaryOutput {
	
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
	
	@Override
	public void set(boolean value) {
		this.value = value;
		model.fireUpdateEvent();
	}
	
	@Override
	public void setValue(double value) {
		set(value!=0);
	}

}