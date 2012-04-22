package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.DIGITAL_INPUT_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.binary.BinaryInput;


/**
 * @author Mitchell
 * 
 * A digital input
 *
 */
public class ImperiumDigitalInput extends ImperiumDeviceObject implements BinaryInput{

	private boolean currentState;
	

	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	
	/**
	 * Create a new USBIODigitalInput
	 * @param device
	 * @param pin
	 */
	public ImperiumDigitalInput(ImperiumDevice device, String pin) {
		super(DIGITAL_INPUT_TYPE_ID, device, pin);
		currentState = false;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.DigitalInput);
	}

	@Override
	public boolean get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		currentState = value!=0;
	}

	@Override
	public void message(long[] values) {
		//
	}

}
