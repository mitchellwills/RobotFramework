package robot.imperium.objects;

import robot.imperium.DeviceFeature;
import robot.imperium.DeviceFeatureCapability;
import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.packet.ImperiumPacket;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.binary.BinaryInput;

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
	private DeviceFeature location;
	/**
	 * Create a new Digital Output
	 * 
	 * @param device
	 * @param location 
	 */
	public ImperiumDigitalInput(ImperiumDevice device, String location) {
		super(ObjectTypeIds.DIGITAL_INPUT, device, 1, 1);
		this.location = device.acquireFeature(location, this, DeviceFeatureCapability.DigitalOutput);
		pullupState = false;
		init();
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
