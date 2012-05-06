package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.PING_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.Input;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;
import robot.io.value.InputValue;

/**
 * A Parallax PING Ultrasonic Distance Sensor
 * 
 * @author Mitchell
 *
 */
public class ImperiumPing extends ImperiumDeviceObject implements Input, UpdatableObject, InputValue {
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	

	private int lastPing;
	/**
	 * Create a new object that represents a PING Ultrasonic Sensor
	 * @param device the Imperium device the sensor exists on
	 * @param pin
	 */
	public ImperiumPing(ImperiumDevice device, String pin) {
		super(PING_TYPE_ID, device, pin);
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.Interrupt, PinCapability.DigitalOutput);
	}
	
	

	@Override
	public void setValue(int value) {
		lastPing = value;
		model.fireUpdateEvent();
	}
	
	@Override
	public double getValue() {
		return lastPing;
	}
	


	@Override
	public void message(long[] values) {
		//
	}

}
