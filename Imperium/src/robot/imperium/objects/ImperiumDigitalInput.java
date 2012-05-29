package robot.imperium.objects;

import robot.imperium.*;
import robot.imperium.packet.*;
import robot.imperium.resources.*;
import robot.io.*;
import robot.io.binary.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

/**
 * A digital input on an imperium device
 * 
 * @author Mitchell
 *
 */
public class ImperiumDigitalInput extends ImperiumDeviceObject implements
		BinaryInput {
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
	private boolean pullupState;
	private DeviceResource location;
	/**
	 * Create a new Digital Output
	 * 
	 * @param device
	 * @param location 
	 */
	@Inject public ImperiumDigitalInput(
			@Assisted(ImperiumDeviceObject.PARAM_DEVICE) ImperiumDevice device,
			@Assisted(RobotObject.PARAM_LOCATION) String location) {
		super(ObjectTypeIds.DIGITAL_INPUT, device, 1, 1);
		this.location = device.acquireResource(location, this, ResourceState.DigitalInput);
		setPullupEnabled(false);
		init();
	}

	@Override
	public String getName() {
		return "Imperium Digital Input";
	}

	@Override
	protected void appendConfiguration(ImperiumPacket packet) {
		packet.appendInteger(location.getId(), 1);
	}

	@Override
	public boolean get() {
		return currentState;
	}

	@Override
	public double getValue() {
		return get() ? 1 : 0;
	}
	
	public boolean isPullupEnabled(){
		return pullupState;
	}
	
	public void setPullupEnabled(boolean enabled){
		if(enabled)
			location.acquire(this, ResourceState.DigitalInputPullup);
		else
			location.acquire(this, ResourceState.DigitalInput);
		pullupState = enabled;
	}

	@Override
	protected void appendSetValue(ImperiumPacket packet) {
		packet.appendInteger(pullupState ? 1 : 0, 1);
	}

	@Override
	protected void readValue(ImperiumPacket packet) {
		currentState = (packet.readUInteger(1)!=0);
	}

}
