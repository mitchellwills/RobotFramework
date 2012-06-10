package robot.imperium.objects;

import robot.imperium.*;
import robot.imperium.packet.*;
import robot.imperium.resources.*;
import robot.io.*;
import robot.io.analog.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;
/**
 * An analog input on an imperium device
 * 
 * @author Mitchell
 *
 */
public final class ImperiumAnalogInput extends ImperiumDeviceObject implements AnalogVoltageInput {
	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private double currentVoltage;
	private DeviceResource location;
	/**
	 * Create a new Analog Output
	 * 
	 * @param device
	 * @param location 
	 */
	@Inject public ImperiumAnalogInput(
			@Assisted(ImperiumDeviceObject.PARAM_DEVICE) ImperiumDevice device,
			@Assisted(RobotObject.PARAM_LOCATION) String location) {
		super(ObjectTypeIds.ANALOG_INPUT, device, 2, 0);
		this.location = device.acquireResource(location, this, ResourceState.AnalogInput);
		currentVoltage = 0;
		init();
	}

	@Override
	public String getName() {
		return "Imperium Analog Input";
	}

	@Override
	protected void appendConfiguration(ImperiumPacket packet) {
		packet.appendInteger(location.getId(), 1);
	}


	@Override
	protected void readValue(ImperiumPacket packet) {
		currentVoltage = packet.readUInteger(2)*getMaxVoltage()/1024.0;
	}

	@Override
	public double getVoltage() {
		return currentVoltage;
	}

	@Override
	public double getValue() {
		return getVoltage();
	}

	@Override
	public double getMaxVoltage() {
		return 5;
	}

	@Override
	protected void appendSetValue(ImperiumPacket packet) {
		//nothing to set
	}

}