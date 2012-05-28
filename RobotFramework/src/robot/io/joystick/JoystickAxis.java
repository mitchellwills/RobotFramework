package robot.io.joystick;

import robot.io.*;
import robot.io.value.*;

/**
 * @author Mitchell
 * 
 * An axis on a joystick
 *
 */
public interface JoystickAxis extends InputValue, UpdatableObject{
	String PARAM_ID = "id";

	/**
	 * @return the current value of a joystick
	 */
	@Override
	public double getValue();
}
