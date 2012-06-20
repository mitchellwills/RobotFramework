package robot.imperium.objects;

import robot.imperium.*;
import robot.imperium.packet.*;
import robot.imperium.resources.*;
import robot.io.*;
import robot.io.pwm.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

/**
 * A pwm output on an imperium device
 * 
 * @author Mitchell
 *
 */
public final class ImperiumPWMOutput extends ImperiumDeviceObject implements PWMOutput {
	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private DeviceResource location;
	private double dutyCycle;
	/**
	 * Create a new PWM Output
	 * 
	 * @param device
	 * @param location 
	 */
	@Inject public ImperiumPWMOutput(
			@Assisted(ImperiumDeviceObject.PARAM_DEVICE) ImperiumDevice device,
			@Assisted(RobotObject.PARAM_LOCATION) String location) {
		super(ObjectTypeIds.PWM_OUTPUT, device, 0, 1);
		this.location = device.acquireResource(location, this, ResourceState.DigitalOutput);
		dutyCycle = 0;
		init();
	}

	@Override
	public String getName() {
		return "Imperium PWM Output";
	}

	@Override
	protected void appendConfiguration(ImperiumPacket packet) {
		packet.appendInteger(location.getId(), 1);
	}
	

	@Override
	public void setValue(double value) {
		set((int) value);
	}

	@Override
	public double getValue() {
		return get();
	}


	@Override
	protected void appendSetValue(ImperiumPacket packet) {
		packet.appendInteger((long) (dutyCycle*255), 1);
	}

	@Override
	protected void readValue(ImperiumPacket packet) {
		//none
	}

	@Override
	public void set(double dutyCycle) {
		this.dutyCycle = dutyCycle;
	}

	@Override
	public double get() {
		return dutyCycle;
	}

	@Override
	public double getFrequency() {
		// TODO Auto-generated method stub
		return 0;
	}

}
