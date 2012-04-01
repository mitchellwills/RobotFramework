package robot.io.joystick;

import robot.io.Input;

public interface Joystick extends Input{
	public String getName();
	
	
	public JoystickButton getButton(int id);
	public int getButtonCount();
	
	
	public JoystickAxis getAxis(int id);
	public int getAxisCount();

	public JoystickDirectional getDirectional(int id);
	public int getDirectionalCount();
}
