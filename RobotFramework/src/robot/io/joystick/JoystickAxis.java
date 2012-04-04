package robot.io.joystick;

import robot.io.Input;

public interface JoystickAxis extends Input{
	public String getName();
	public double getValue();
}
