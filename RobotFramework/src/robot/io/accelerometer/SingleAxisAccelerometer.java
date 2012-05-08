package robot.io.accelerometer;

import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.io.RobotObjectListener;
import robot.io.value.InputValue;

/**
 * 
 * An accelerometer that only has one axis and gets its value from another accelerometer
 * 
 * @author Mitchell
 *
 */
public class SingleAxisAccelerometer implements Accelerometer, InputValue {
	
	private final Accelerometer source;
	private final int sourceAxis;
	
	/**
	 * Create a new single axis accelerometer
	 * @param source the accelerometer the acceleration is from
	 * @param sourceAxis the axis to read from the source accelerometer
	 */
	public SingleAxisAccelerometer(Accelerometer source, int sourceAxis){
		if(sourceAxis>=source.getNumAccelerometerAxes())
			throw new RobotInitializationException("The accelerometer "+source+" does not support the "+sourceAxis+" axis");
		this.source = source;
		this.sourceAxis = sourceAxis;
	}

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		source.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		source.removeUpdateListener(listener);
	}

	@Override
	public double getLinearAcceleration(int axis) {
		if(axis==0)
			return source.getLinearAcceleration(sourceAxis);
		throw new RobotException("A single axis accelerometer only supports one axis");
	}

	@Override
	public double getValue() {
		return getLinearAcceleration(0);
	}

	@Override
	public int getNumAccelerometerAxes() {
		return 1;
	}

}
