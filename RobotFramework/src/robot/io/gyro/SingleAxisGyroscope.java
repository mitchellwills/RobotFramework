package robot.io.gyro;

import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.io.RobotObjectListener;
import robot.io.value.InputValue;

/**
 * A gyroscope that only has one axis and gets its value from another gyroscope
 * 
 * @author Mitchell
 * 
 *
 */
public class SingleAxisGyroscope implements Gyroscope, InputValue {
	
	private final Gyroscope source;
	private final int sourceAxis;
	
	/**
	 * Create a new single axis gyroscope
	 * @param source the gyroscope the angular acceleration is from
	 * @param sourceAxis the axis to read from the source gyroscope
	 */
	public SingleAxisGyroscope(Gyroscope source, int sourceAxis){
		if(sourceAxis>=source.getNumGyroAxes())
			throw new RobotInitializationException("The gyroscope "+source+" does not support the "+sourceAxis+" axis");
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
	public double getAngularAcceleration(int axis) {
		if(axis==0)
			return source.getAngularAcceleration(sourceAxis);
		throw new RobotException("A single axis gyroscope only supports one axis");
	}

	@Override
	public double getValue() {
		return getAngularAcceleration(0);
	}

	@Override
	public int getNumGyroAxes() {
		return 1;
	}

}