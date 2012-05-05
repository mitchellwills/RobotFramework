package robot.io.compass;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual compass whose value can be set
 *
 */
public class VirtualCompass implements Compass{

	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private double angle = 0;

	@Override
	public double getHeading() {
		return angle;
	}

	@Override
	public double getValue() {
		return getHeading();
	}
	
	/**
	 * Set the exact angle of the compass
	 * @param angle
	 */
	public void setHeading(double angle){
		this.angle = angle;
		model.fireUpdateEvent();
	}

}
