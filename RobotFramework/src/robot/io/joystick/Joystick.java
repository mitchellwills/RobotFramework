package robot.io.joystick;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * Abstract representation of a joystick
 *
 */
public interface Joystick extends Input, UpdatableObject{
	/**
	 * @return the name of the joystick
	 */
	public String getName();
	
	
	/**
	 * @param id
	 * @return a button on the joystick
	 */
	public JoystickButton getButton(int id);
	/**
	 * @return the number of buttons on the joystick
	 */
	public int getButtonCount();
	
	
	/**
	 * @param id
	 * @return an axis on the joystick
	 */
	public JoystickAxis getAxis(int id);
	/**
	 * @return the number of axes on the joystick
	 */
	public int getAxisCount();

	/**
	 * @param id
	 * @return a directional on the joystick
	 */
	public JoystickDirectional getDirectional(int id);
	/**
	 * @return the number of directionals that exist on the joystick
	 */
	public int getDirectionalCount();

}
