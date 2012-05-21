package robot.imperium.objects;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.packet.ImperiumPacket;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.binary.BinaryOutput;

/**
 * A digital input on an imperium device
 * 
 * @author Mitchell
 *
 */
public class ImperiumDigitalOutput extends ImperiumDeviceObject implements
		BinaryOutput {
	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private boolean currentState;
	private String location;
	/**
	 * Create a new Digital Output
	 * 
	 * @param device
	 * @param location 
	 */
	public ImperiumDigitalOutput(ImperiumDevice device, String location) {
		super(ObjectTypeIds.DIGITAL_OUTPUT_TYPE_ID, device, 0, 1);
		this.location = location;
		init();
	}

	@Override
	protected void appendConfiguration(ImperiumPacket packet) {
		packet.appendInteger(getDevice().getPin(location), 1);
	}

	@Override
	public void set(boolean value) {
		currentState = value;
		model.fireUpdateEvent();
	}

	@Override
	public boolean get() {
		return currentState;
	}

	@Override
	public void setValue(double value) {
		set(value != 0);
	}

	@Override
	public double getValue() {
		return get() ? 1 : 0;
	}

	@Override
	protected void appendSetValue(ImperiumPacket packet) {
		packet.appendInteger(get() ? 1 : 0, 1);
	}

	@Override
	protected void readValue(ImperiumPacket packet) {
		//none
	}

}
