package robot.io.gyro;

import robot.error.RobotInitializationException;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual gyroscope that can be given fake values
 *
 */
public class VirtualGyro implements Gyroscope{
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private double[] values;
	/**
	 * Create a virtual gyroscope that supports the specified axes all with initial values of 0
	 * @param axisCount the number of axes the virtual gyroscope has
	 */
	public VirtualGyro(int axisCount){
		values = new double[axisCount];
		for(int i = 0; i<values.length; ++i)
			values[i] = 0;
	}

	@Override
	public double getAngularAcceleration(int axis) {
		if(axis>=getNumGyroAxes())
			throw new RobotInitializationException("The virtual gyroscope does not support the "+axis+" axis");
		return values[axis];
	}

	@Override
	public int getNumGyroAxes() {
		return values.length;
	}
	
	/**
	 * Set the faked angular acceleration value
	 * @param axis the axis whose value will be set
	 * @param value the new value for the axis
	 */
	public void setAcceleration(int axis, double value){
		if(axis>=getNumGyroAxes())
			throw new RobotInitializationException("The virtual gyroscope does not support the "+axis+" axis");
		values[axis] = value;
		model.fireUpdateEvent();
	}

}
