package robot.io.accelerometer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import robot.error.RobotInitializationException;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual accelerometer that can be given fake values
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
	
	private Map<AccelerometerAxis, Double> values = new HashMap<AccelerometerAxis, Double>();
	/**
	 * Create a virtual accelerometer that supports the specified axes all with initial values of 0
	 * @param axes
	 */
	public VirtualAccelerometer(AccelerometerAxis... axes){
		for(AccelerometerAxis axis:axes)
			values.put(axis, 0d);
	}

	@Override
	public double getAcceleration(AccelerometerAxis axis) {
		Double value = values.get(axis);
		if(value!=null)
			return value;
		throw new RobotInitializationException("The virtual accelerometer does not support the "+axis+" axis");
	}

	@Override
	public Set<AccelerometerAxis> getAxes() {
		return values.keySet();
	}
	
	/**
	 * Set the faked acceleration value
	 * @param axis the axis whose value will be set
	 * @param value the new value for the axis
	 */
	public void setAcceleration(AccelerometerAxis axis, double value){
		if(values.containsKey(axis)){
			values.put(axis, value);
			model.fireUpdateEvent();
		}
		throw new RobotInitializationException("The virtual accelerometer does not support the "+axis+" axis");
	}

}
