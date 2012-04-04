package robot.io.joystick;

import robot.io.Input;
import robot.io.UpdatableObject;

public interface Joystick extends Input, UpdatableObject{
	public String getName();
	
	
	public JoystickButton getButton(int id);
	public int getButtonCount();
	
	
	public JoystickAxis getAxis(int id);
	public int getAxisCount();

	public JoystickDirectional getDirectional(int id);
	public int getDirectionalCount();

}
