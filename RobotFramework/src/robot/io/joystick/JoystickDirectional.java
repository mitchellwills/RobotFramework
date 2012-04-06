package robot.io.joystick;

import robot.io.Input;

/**
 * @author Mitchell
 * 
 * A directional on a joystick
 *
 */
public interface JoystickDirectional extends Input{
	/**
	 * @return the name of the directional
	 */
	public String getName();
	/**
	 * @return the angle the directional is at
	 */
	public double getAngle();
	/**
	 * @return the magnatude of the directional
	 */
	public double getMagnatude();
	/**
	 * @return true if the directional is centered
	 */
	public boolean isCentered();
}
