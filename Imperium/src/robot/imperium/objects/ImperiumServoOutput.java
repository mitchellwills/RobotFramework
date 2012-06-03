package robot.imperium.objects;

import robot.imperium.*;
import robot.imperium.packet.*;
import robot.imperium.resources.*;
import robot.io.*;
import robot.io.pwmms.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

/**
 * A servo output on an imperium device
 * 
 * @author Mitchell
 *
 */
public final class ImperiumServoOutput extends ImperiumDeviceObject implements MSPWMOutput {
	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private DeviceResource location;
	private int value;
	/**
	 * Create a new Servo Output
	 * 
	 * @param device
	 * @param location 
	 */
	@Inject public ImperiumServoOutput(
			@Assisted(ImperiumDeviceObject.PARAM_DEVICE) ImperiumDevice device,
			@Assisted(RobotObject.PARAM_LOCATION) String location) {
		super(ObjectTypeIds.SERVO_OUTPUT, device, 0, 2);
		this.location = device.acquireResource(location, this, ResourceState.DigitalOutput);
		value = MSPWMOutput.DISABLED;
		init();
	}

	@Override
	public String getName() {
		return "Imperium Servo Output";
	}

	@Override
	protected void appendConfiguration(ImperiumPacket packet) {
		packet.appendInteger(location.getId(), 1);
	}
	

	@Override
	public void set(int ms) {
		value = ms;
	}

	@Override
	public int get() {
		return value;
	}

	@Override
	public void setValue(double value) {
		set((int) value);
	}

	@Override
	public double getValue() {
		return get();
	}


	@Override
	protected void appendSetValue(ImperiumPacket packet) {
		packet.appendInteger(get(), 2);
	}

	@Override
	protected void readValue(ImperiumPacket packet) {
		//none
	}

}
