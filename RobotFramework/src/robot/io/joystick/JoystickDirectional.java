package robot.io.joystick;

import robot.io.Input;

public interface JoystickDirectional extends Input{
	public String getName();
	public double getAngle();
	public double getMagnatude();
	public boolean isCentered();
}
