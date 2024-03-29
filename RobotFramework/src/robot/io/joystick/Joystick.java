package robot.io.joystick;

import robot.io.*;

/**
 * @author Mitchell
 * 
 * Abstract representation of a joystick
 *
 */
public interface Joystick extends Input, UpdatableObject{
	
	String PARAM_NUM_DIRECTIONALS = "numDirectionals";
	String PARAM_NUM_AXES = "numAxes";
	String PARAM_NUM_BUTTONS = "numButtons";
	
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
