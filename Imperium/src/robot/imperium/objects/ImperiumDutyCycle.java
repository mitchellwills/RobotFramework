package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.DUTY_CYCLE_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.dutycycle.DutyCycleInput;
import robot.util.ByteUtil;


/**
 * @author Mitchell
 * 
 * A pulse counter
 *
 */
public class ImperiumDutyCycle extends ImperiumDeviceObject implements DutyCycleInput{

	private double duty;
	

	private final RobotObjectModel<ImperiumDutyCycle> model = new RobotObjectModel<ImperiumDutyCycle>(this);

	@Override
	public void addUpdateListener(RobotObjectListener<DutyCycleInput> listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener<DutyCycleInput> listener) {
		model.removeUpdateListener(listener);
	}
	
	
	/**
	 * Create a new Imperium Duty Cycle
	 * @param device
	 * @param pin
	 */
	public ImperiumDutyCycle(ImperiumDevice device, String pin) {
		super(DUTY_CYCLE_TYPE_ID, device, pin);
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.Interrupt);
	}
	
	@Override
	public void initialize(){
		duty = 0;
	}

	@Override
	public double getDutyCycle() {
		return duty;
	}

	@Override
	public void setValue(int value) {
		duty = ByteUtil.toUnsigned(value)/5000d;
	}

	@Override
	public void message(long[] values) {
		//
	}

}
