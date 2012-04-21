package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.ANALOG_INPUT_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.analog.AnalogVoltageInput;


/**
 * @author Mitchell
 * 
 * A digital input
 *
 */
public class ImperiumAnalogVoltageInput extends ImperiumDeviceObject implements AnalogVoltageInput{
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
	private final double maxVoltage;
	/**
	 * Create a new USBIOAnalogInput
	 * @param device
	 * @param pin
	 */
	public ImperiumAnalogVoltageInput(ImperiumDevice device, String pin) {
		super(ANALOG_INPUT_TYPE_ID, device, pin);
		maxVoltage = device.getHardwareConfiguration().getMaxAnalogInputVoltage();
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.AnalogInput);
	}
	
	@Override
	public void initialize(){
		currentVoltage = 0;
	}

	@Override
	public double getVoltage() {
		return currentVoltage;
	}

	@Override
	public void setValue(int value) {
		currentVoltage = maxVoltage * value / 1023;
		model.fireUpdateEvent();
	}

	@Override
	public double getMaxVoltage() {
		return maxVoltage;
	}

	@Override
	public void message(long[] values) {
		//
	}

}
