package robot.io.joystick;

import robot.io.Input;

/**
 * @author Mitchell
 * 
 * An axis on a joystick
 *
 */
public interface JoystickAxis extends Input{
	/**
	 * @return the name of the axis
	 */
	public String getName();
	/**
	 * @return the current value of a joystick
	 */
	public double getValue();
}
