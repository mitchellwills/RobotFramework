package robot.io.joystick;

import robot.io.UpdatableObject;
import robot.io.binary.BinaryInput;

/**
 * @author Mitchell
 * 
 * represents a button on the joystick
 *
 */
public interface JoystickButton extends BinaryInput, UpdatableObject{
	/**
	 * @return the name of the button
	 */
	public String getName();
	/**
	 * @return true if the button is pressed
	 */
	@Override
	public boolean get();
}
