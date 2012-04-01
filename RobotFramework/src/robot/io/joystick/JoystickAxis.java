package robot.io.joystick;

import robot.io.Input;

public interface JoystickAxis extends Input{
	public enum AxisType{
		ROTATION, SLIDER, TRANSLATION, UNKNOWN;
		
	}
	public String getName();
	public AxisType getType();
	public double getValue();
}
