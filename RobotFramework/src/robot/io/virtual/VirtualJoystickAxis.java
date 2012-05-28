package robot.io.virtual;

import robot.io.*;
import robot.io.joystick.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;


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

	private double value;
	private final int id;
	
	public VirtualJoystickAxis(){
		this(0);
	}
	
	/**
	 * Create a new virtual joystick axis
	 * @param id the id of the axis relative to the joystick
	 */
	@Inject public VirtualJoystickAxis(@Assisted(JoystickAxis.PARAM_ID) int id){
		this.id = id;
		value = 0.0;
	}
	
	public int getId(){
		return id;
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
