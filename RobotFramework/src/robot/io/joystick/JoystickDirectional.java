package robot.io.joystick;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * A directional on a joystick
 *
 */
public interface JoystickDirectional extends Input, UpdatableObject{
	/**
	 * @return the name of the directional
	 */
	public String getName();
	/**
	 * @return the angle the directional is at
	 */
	public double getAngle();
	/**
	 * @return the magnitude of the directional
	 */
	public double getMagnitude();
	/**
	 * @return true if the directional is centered
	 */
	public boolean isCentered();
}
