package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.PING_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.distance.DistanceInput;

/**
 * A Parallax PING Ultrasonic Distance Sensor
 * 
 * @author Mitchell
 *
 */
public class ImperiumPing extends ImperiumDeviceObject implements DistanceInput {
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	

	private double lastMeasure;
	/**
	 * Create a new object that represents a PING Ultrasonic Sensor
	 * @param device the Imperium device the sensor exists on
	 * @param pin
	 */
	public ImperiumPing(ImperiumDevice device, String pin) {
		super(PING_TYPE_ID, device, pin);
		lastMeasure = Double.NaN;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.Interrupt, PinCapability.DigitalOutput);
	}
	
	

	@Override
	public void setValue(int value) {
		lastMeasure = value/2d/29/100;
		model.fireUpdateEvent();
	}

	@Override
	public double getDistance() {
		return lastMeasure;
	}
	@Override
	public double getValue() {
		return getDistance();
	}
	


	@Override
	public void message(long[] values) {
		//
	}

}
