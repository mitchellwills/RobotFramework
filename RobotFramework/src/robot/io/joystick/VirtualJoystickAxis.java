package robot.io.joystick;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;


/**
 * @author Mitchell
 * 
 * A virtual joystick axis implementation
 *
 */
public class VirtualJoystickAxis implements JoystickAxis{
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private final String name;
	private double value;
	
	/**
	 * Create a new virtual joystick axis
	 * @param id the id of the axis relative to the joystick
	 */
	public VirtualJoystickAxis(int id){
		this.name = "Virtual Axis "+id;
		value = 0.0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getValue() {
		return value;
	}

	/**
	 * Set the value the axis returns
	 * @param value
	 */
	public void set(double value){
		this.value = value;
		model.fireUpdateEvent();
	}
}
