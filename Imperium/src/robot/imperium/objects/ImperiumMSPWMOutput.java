package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.MSPWM_OUTPUT_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.pwmms.MSPWMOutput;


/**
 * @author Mitchell
 * 
 * A pwm output that outputs a pulses that are a set length in milliseconds
 *
 */
public class ImperiumMSPWMOutput extends ImperiumDeviceObject implements MSPWMOutput{

	private int currentState;
	

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
	 * Create a new USBIODigitalOutput
	 * @param device
	 * @param pin
	 */
	public ImperiumMSPWMOutput(ImperiumDevice device, String pin) {
		super(MSPWM_OUTPUT_TYPE_ID, device, pin);
		currentState = DISABLED;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.MSPWM_Output);
	}

	@Override
	public void set(int ms) {
		getDevice().sendSetPacket(this, ms);
		currentState = ms;
		model.fireUpdateEvent();
	}

	@Override
	public int get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		//don't do anything or could get out of sync
	}

	@Override
	public void message(long[] values) {
		//
	}

}
