package robot.io.joystick;

import robot.io.binary.BinaryInput;

public interface JoystickButton extends BinaryInput{
	public enum ButtonType{
		TOGGLEBUTTON, PUSHBUTTON, UNKNOWN;
		
	}
	public String getName();
	public ButtonType getType();
}
