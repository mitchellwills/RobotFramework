package robot.io.frequency;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual frequency input whose value can be set
 *
 */
public class VirtualFrequencyInput implements FrequencyInput {

	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private long frequency = 0;

	@Override
	public long getFrequency() {
		return frequency;
	}

	@Override
	public double getValue() {
		return getFrequency();
	}
	
	/**
	 * Set the value of the frequency this input returns
	 * @param frequency
	 */
	public void setFrequency(final long frequency){
		this.frequency = frequency;
		model.fireUpdateEvent();
	}

}
