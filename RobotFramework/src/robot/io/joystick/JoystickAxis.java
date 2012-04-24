package robot.io.joystick;

import robot.io.InputValue;

/**
 * @author Mitchell
 * 
 * An axis on a joystick
 *
 */
public interface JoystickAxis extends InputValue{
	/**
	 * @return the name of the axis
	 */
	public String getName();
	/**
	 * @return the current value of a joystick
	 */
	@Override
	public double getValue();
}
