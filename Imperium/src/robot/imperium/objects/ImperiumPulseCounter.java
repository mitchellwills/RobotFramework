package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.PULSE_COUNTER_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.counter.Counter;
import robot.util.ByteUtil;


/**
 * @author Mitchell
 * 
 * A digital input
 *
 */
public class ImperiumPulseCounter extends ImperiumDeviceObject implements Counter{

	private long count;
	

	private final RobotObjectModel<ImperiumPulseCounter> model = new RobotObjectModel<ImperiumPulseCounter>(this);

	@Override
	public void addUpdateListener(RobotObjectListener<Counter> listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener<Counter> listener) {
		model.removeUpdateListener(listener);
	}
	
	
	/**
	 * Create a new USBIODigitalInput
	 * @param device
	 * @param pin
	 */
	public ImperiumPulseCounter(ImperiumDevice device, String pin) {
		super(PULSE_COUNTER_TYPE_ID, device, pin);
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.Interrupt);
	}
	
	@Override
	public void initialize(){
		count = 0;
	}

	@Override
	public long getCount() {
		return count;
	}

	@Override
	public void setValue(int value) {
		count = ByteUtil.toUnsigned(value);
	}

	@Override
	public void message(long[] values) {
		//
	}

}
