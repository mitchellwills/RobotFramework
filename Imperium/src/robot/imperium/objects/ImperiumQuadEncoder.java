package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.QUAD_ENCODER_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.encoder.Encoder;


/**
 * @author Mitchell
 * 
 * A quadrature encoder
 *
 */
public class ImperiumQuadEncoder extends ImperiumDeviceObject implements Encoder{

	/**
	 * name of the parameter in factories param map corresponding to one of the quadrature encoder's pins
	 */
	public static final String PARAM_LOCATION_A = "locationA";
	
	/**
	 * name of the parameter in factories param map corresponding to one of the quadrature encoder's pins
	 */
	public static final String PARAM_LOCATION_B = "locationB";


	private int position;
	

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
	 * Create a new ImeriumQuadEncoder
	 * @param device
	 * @param pinA 
	 * @param pinB 
	 */
	public ImperiumQuadEncoder(ImperiumDevice device, String pinA, String pinB) {
		super(QUAD_ENCODER_TYPE_ID, device, pinA, pinB);
		position = 0;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.Interrupt);
	}

	@Override
	public int getPosition() {
		return position;
	}
	@Override
	public double getValue(){
		return getPosition();
	}

	@Override
	public void setValue(int value) {
		position = value;
	}

	@Override
	public void message(long[] values) {
		//
	}

}
