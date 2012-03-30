package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.MSPWM_OUTPUT_TYPE_ID;

import java.util.EnumSet;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.PinCapability;
import robot.io.pwmms.MSPWMOutput;


/**
 * @author Mitchell
 * 
 * A pwm output that outputs a pulses that are a set length in milliseconds
 *
 */
public class ImperiumMSPWMOutput extends ImperiumDeviceObject implements MSPWMOutput{

	private int currentState;
	/**
	 * Create a new USBIODigitalOutput
	 * @param device
	 * @param pin
	 */
	public ImperiumMSPWMOutput(ImperiumDevice device, String pin) {
		super(MSPWM_OUTPUT_TYPE_ID, device, pin);
	}

	@Override
	public EnumSet<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.MSPWM_Output);
	}
	
	@Override
	public void initialize(){
		set(DISABLED);
	}

	@Override
	public void set(int ms) {
		getDevice().sendSetPacket(this, ms);
		currentState = ms;
	}

	@Override
	public int get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		currentState = value;
	}

}
