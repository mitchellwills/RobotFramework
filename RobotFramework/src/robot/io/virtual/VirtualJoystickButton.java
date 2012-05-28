package robot.io.virtual;

import robot.io.*;
import robot.io.joystick.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

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
	
	private boolean value;
	private final int id;
	
	public VirtualJoystickButton(){
		this(0);
	}
	
	/**
	 * Create a new virtual joystick button
	 * @param id the id of the button relative to the joystick
	 */
	@Inject public VirtualJoystickButton(@Assisted(JoystickButton.PARAM_ID) int id){
		this.id = id;
		value = false;
	}
	
	public int getId(){
		return id;
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
