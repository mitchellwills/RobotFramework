package robot.imperium.objects;

import robot.imperium.*;
import robot.imperium.packet.*;
import robot.imperium.resources.*;
import robot.io.*;
import robot.io.binary.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

/**
 * A digital output on an imperium device
 * 
 * @author Mitchell
 *
 */
public final class ImperiumDigitalOutput extends ImperiumDeviceObject implements
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
	private DeviceResource location;
	/**
	 * Create a new Digital Output
	 * 
	 * @param device
	 * @param location 
	 */
	@Inject public ImperiumDigitalOutput(
			@Assisted(ImperiumDeviceObject.PARAM_DEVICE) ImperiumDevice device,
			@Assisted(RobotObject.PARAM_LOCATION) String location) {
		super(ObjectTypeIds.DIGITAL_OUTPUT, device, 0, 1);
		this.location = device.acquireResource(location, this, ResourceState.DigitalOutput);
		init();
	}

	@Override
	public String getName() {
		return "Imperium Digital Output";
	}

	@Override
	protected void appendConfiguration(ImperiumPacket packet) {
		packet.appendInteger(location.getId(), 1);
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
