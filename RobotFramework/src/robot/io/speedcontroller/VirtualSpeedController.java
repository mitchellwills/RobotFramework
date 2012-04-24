package robot.io.speedcontroller;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A Speed controller that does not map to any physical device and instead just stores it's current value
 *
 */
public class VirtualSpeedController implements SpeedController{
	private double value = 0;
	private RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void set(double value) {
		this.value = value;
		model.fireUpdateEvent();
	}

	@Override
	public double get() {
		return value;
	}

	@Override
	public void addUpdateListener(
			RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(
			RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	@Override
	public void setValue(double value) {
		set(value);
	}

	@Override
	public double getValue() {
		return get();
	}
}
