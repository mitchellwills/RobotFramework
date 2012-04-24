package robot.io.accelerometer;

import java.util.EnumSet;
import java.util.Set;

import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.io.InputValue;
import robot.io.RobotObjectListener;

/**
 * @author Mitchell
 * 
 * An accelerometer that only has one axis and gets its value from another accelerometer
 *
 */
public class SingleAxisAccelerometer implements Accelerometer, InputValue {
	
	private final Accelerometer source;
	private final AccelerometerAxis sourceAxis;
	
	/**
	 * Create a new single axis accelerometer
	 * @param source the accelerometer the acceleration is from
	 * @param sourceAxis the axis to read from the source accelerometer
	 */
	public SingleAxisAccelerometer(Accelerometer source, AccelerometerAxis sourceAxis){
		if(!source.getAxes().contains(sourceAxis))
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
	public double getAcceleration(AccelerometerAxis axis) {
		if(axis==AccelerometerAxis.XAxis)
			return source.getAcceleration(sourceAxis);
		throw new RobotException("A single axis accelerometer only supports the X axis");
	}

	@Override
	public double getValue() {
		return getAcceleration(AccelerometerAxis.XAxis);
	}

	@Override
	public Set<AccelerometerAxis> getAxes() {
		return EnumSet.of(AccelerometerAxis.XAxis);
	}

}
