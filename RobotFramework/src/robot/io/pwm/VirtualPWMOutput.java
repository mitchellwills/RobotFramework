package robot.io.pwm;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A PWMOutput whose value can be set but will not effect any real world change
 *
 */
public class VirtualPWMOutput implements PWMOutput{
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	

	private double frequency;
	private double dutyCycle;
	/**
	 * @param frequency the frequency the pwm is "output" at
	 */
	public VirtualPWMOutput(double frequency){
		this.frequency = frequency;
		dutyCycle = 0;
	}


	@Override
	public void set(double dutyCycle) {
		this.dutyCycle = dutyCycle;
		model.fireUpdateEvent();
	}

	@Override
	public double get() {
		return dutyCycle;
	}

	@Override
	public double getFrequency() {
		return frequency;
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
