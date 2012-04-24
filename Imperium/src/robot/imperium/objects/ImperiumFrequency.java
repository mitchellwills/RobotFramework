package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.FREQUENCY_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.frequency.FrequencyInput;
import robot.util.ByteUtil;


/**
 * @author Mitchell
 * 
 * A pulse counter
 *
 */
public class ImperiumFrequency extends ImperiumDeviceObject implements FrequencyInput{

	private long frequency;
	

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
	 * Create a new Imperium Frequency
	 * @param device
	 * @param pin
	 */
	public ImperiumFrequency(ImperiumDevice device, String pin) {
		super(FREQUENCY_TYPE_ID, device, pin);
		frequency = 0;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.Interrupt);
	}

	@Override
	public long getFrequency() {
		return frequency;
	}
	@Override
	public double getValue(){
		return getFrequency();
	}

	@Override
	public void setValue(int value) {
		frequency = ByteUtil.toUnsigned(value);
	}

	@Override
	public void message(long[] values) {
		//
	}

}
