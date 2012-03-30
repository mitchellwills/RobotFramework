package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.PWM_OUTPUT_TYPE_ID;

import java.util.EnumSet;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.PinCapability;
import robot.io.pwm.PWMOutput;
import robot.util.RobotUtil;


/**
 * @author Mitchell
 * 
 * A pwm output that outputs a duty cycle
 *
 */
public class ImperiumPWMOutput extends ImperiumDeviceObject implements PWMOutput{

	private double currentState;
	/**
	 * Create a new USBIODigitalOutput
	 * @param device
	 * @param pin
	 */
	public ImperiumPWMOutput(ImperiumDevice device, String pin) {
		super(PWM_OUTPUT_TYPE_ID, device, pin);
	}

	@Override
	public EnumSet<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.PWM_Output);
	}
	
	@Override
	public void initialize(){
		set(0);
	}

	@Override
	public void set(double dutyCycle) {
		getDevice().sendSetPacket(this, dutyCycleToValue(dutyCycle));
		currentState = dutyCycle;
	}
	
	private static int dutyCycleToValue(double dutyCycle){
		return (int) RobotUtil.limit(dutyCycle, 0, 1.0)*255;
	}

	@Override
	public double get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		currentState = value/255.0;
	}

	@Override
	public double getFrequency() {
		return getDevice().getHardwareConfiguration().getPWMFrequency();
	}

}
