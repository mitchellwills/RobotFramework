package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.QUAD_ENCODER_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.counter.Counter;


/**
 * @author Mitchell
 * 
 * A quadrature encoder
 *
 */
public class ImperiumQuadEncoder extends ImperiumDeviceObject implements Counter{

	private long count;
	

	private final RobotObjectModel<ImperiumQuadEncoder> model = new RobotObjectModel<ImperiumQuadEncoder>(this);

	@Override
	public void addUpdateListener(RobotObjectListener<Counter> listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener<Counter> listener) {
		model.removeUpdateListener(listener);
	}
	
	
	/**
	 * Create a new ImeriumQuadEncoder
	 * @param device
	 * @param pinA 
	 * @param pinB 
	 */
	public ImperiumQuadEncoder(ImperiumDevice device, String pinA, String pinB) {
		super(QUAD_ENCODER_TYPE_ID, device, pinA, pinB);
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
		count = value;
	}

	@Override
	public void message(long[] values) {
		//
	}

}