package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.DIGITAL_OUTPUT_TYPE_ID;

import java.util.EnumSet;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.binary.BinaryOutput;


/**
 * @author Mitchell
 * 
 * A digital output
 *
 */
public class ImperiumDigitalOutput extends ImperiumDeviceObject implements BinaryOutput{

	private boolean currentState;
	/**
	 * Create a new USBIODigitalOutput
	 * @param device
	 * @param pin
	 */
	public ImperiumDigitalOutput(ImperiumDevice device, String pin) {
		super(DIGITAL_OUTPUT_TYPE_ID, device, pin);
	}

	@Override
	public EnumSet<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.DigitalOutput);
	}
	
	@Override
	public void initialize(){
		set(false);
	}

	@Override
	public void set(boolean value) {
		getDevice().sendSetPacket(this, value?1:0);
		currentState = value;
	}

	@Override
	public boolean get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		currentState = (value!=0);
	}

}
