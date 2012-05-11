package robot.io.dutycycle;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual duty cycle input whose value can be set
 *
 */
public class VirtualDutyCycleInput implements DutyCycleInput {

	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private double value = 0.0;

	@Override
	public double getDutyCycle() {
		return value;
	}

	@Override
	public double getValue() {
		return getDutyCycle();
	}
	
	/**
	 * Set the value of the duty cycle this input returns
	 * @param value
	 */
	public void setDutyCycle(final double value){
		this.value = value;
		model.fireUpdateEvent();
	}

}
