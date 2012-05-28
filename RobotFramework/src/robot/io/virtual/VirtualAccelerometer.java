package robot.io.virtual;

import robot.error.*;
import robot.io.*;
import robot.io.accelerometer.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

/**
 * A virtual accelerometer that can be given fake values
 *
 * @author Mitchell
 * 
 */
public class VirtualAccelerometer implements Accelerometer{
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
	 * Create a virtual accelerometer that supports the specified axes all with initial values of 0
	 * @param numAxes the number of axes the virtual accelerometer has
	 */
	@Inject public VirtualAccelerometer(@Assisted(Accelerometer.PARAM_NUM_AXES) int numAxes){
		values = new double[numAxes];
		for(int i = 0; i<values.length; ++i)
			values[i] = 0;
	}

	@Override
	public double getLinearAcceleration(int axis) {
		if(axis<0 || axis>=getNumAccelerometerAxes())
			throw new RobotException("The virtual accelerometer does not support the "+axis+" axis");
		return values[axis];
	}

	@Override
	public int getNumAccelerometerAxes() {
		return values.length;
	}
	
	/**
	 * Set the faked acceleration value
	 * @param axis the axis whose value will be set
	 * @param value the new value for the axis
	 */
	public void setAcceleration(int axis, double value){
		if(axis<0 || axis>=getNumAccelerometerAxes())
			throw new RobotException("The virtual accelerometer does not support the "+axis+" axis");
		values[axis] = value;
		model.fireUpdateEvent();
	}

}
