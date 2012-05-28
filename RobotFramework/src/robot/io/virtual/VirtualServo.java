package robot.io.virtual;

import robot.io.*;
import robot.io.servo.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

/**
 * @author Mitchell
 * 
 * A Speed controller that does not map to any physical device and instead just stores it's current value
 *
 */
public class VirtualServo implements Servo{
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private final double minAngle;
	private final double maxAngle;
	private double angle;
	/**
	 * Create a new virtual servo with given bounding angles
	 * @param minAngle
	 * @param maxAngle
	 */
	@Inject public VirtualServo(@Assisted(Servo.PARAM_MIN_ANGLE) double minAngle, @Assisted(Servo.PARAM_MAX_ANGLE) double maxAngle){
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		angle = minAngle;
	}

	@Override
	public void setAngle(double angle) {
		this.angle = angle;
		model.fireUpdateEvent();
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public double getMinAngle() {
		return minAngle;
	}

	@Override
	public double getMaxAngle() {
		return maxAngle;
	}

	@Override
	public void setValue(double value) {
		setAngle(value);
	}

	@Override
	public double getValue() {
		return getAngle();
	}
	
}
