package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.DIGITAL_INPUT_TYPE_ID;

import java.util.EnumSet;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.PinCapability;
import robot.io.BinaryInput;


/**
 * @author Mitchell
 * 
 * A digital input
 *
 */
public class ImperiumDigitalInput extends ImperiumDeviceObject implements BinaryInput{

	private boolean currentState;
	/**
	 * Create a new USBIODigitalInput
	 * @param device
	 * @param pin
	 */
	public ImperiumDigitalInput(ImperiumDevice device, int pin) {
		super(DIGITAL_INPUT_TYPE_ID, device, pin);
	}

	@Override
	public EnumSet<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.DigitalInput);
	}
	
	@Override
	public void initialize(){
		currentState = false;
	}

	@Override
	public boolean get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		currentState = value!=0;
	}

}
