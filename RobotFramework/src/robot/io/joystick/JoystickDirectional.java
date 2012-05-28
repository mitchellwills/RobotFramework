package robot.io.joystick;

import robot.io.*;

/**
 * @author Mitchell
 * 
 * A directional on a joystick
 *
 */
public interface JoystickDirectional extends Input, UpdatableObject{
	
	String PARAM_ID = "id";
	
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
