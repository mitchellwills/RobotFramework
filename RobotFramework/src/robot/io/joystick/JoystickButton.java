package robot.io.joystick;

import robot.io.*;
import robot.io.binary.*;

/**
 * @author Mitchell
 * 
 * represents a button on the joystick
 *
 */
public interface JoystickButton extends BinaryInput, UpdatableObject{
	
	String PARAM_ID = "id";
	
	/**
	 * @return true if the button is pressed
	 */
	@Override
	public boolean get();
}
