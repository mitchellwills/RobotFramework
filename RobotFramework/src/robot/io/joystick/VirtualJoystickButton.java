package robot.io.joystick;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual joystick button implementation
 *
 */
public class VirtualJoystickButton implements JoystickButton{
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
	private boolean value;
	
	/**
	 * Create a new virtual joystick button
	 * @param id the id of the button relative to the joystick
	 */
	public VirtualJoystickButton(int id){
		this.name = "Virtual Button "+id;
		value = false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean get() {
		return value;
	}
	
	@Override
	public double getValue() {
		return get()?1:0;
	}
	
	/**
	 * Set the value of the button
	 * @param value
	 */
	public void set(boolean value){
		this.value = value;
		model.fireUpdateEvent();
	}

}
