package robot.io.computerdevices.jxinput;

import de.hardcode.jxinput.Button;
import robot.io.joystick.JoystickButton;

/**
 * @author Mitchell
 * 
 * A joystick button that exists on a joystick connected to a computers
 *
 */
public class JXInputJoystickButton implements JoystickButton {
	
	private final Button nativeButton;
	JXInputJoystickButton(Button nativeButton){
		this.nativeButton = nativeButton;
	}

	@Override
	public String getName() {
		return nativeButton.getName();
	}

	@Override
	public ButtonType getType() {
		switch(nativeButton.getType()){
		case Button.PUSHBUTTON:
			return ButtonType.PUSHBUTTON;
		case Button.TOGGLEBUTTON:
			return ButtonType.TOGGLEBUTTON;
		}
		return ButtonType.UNKNOWN;
	}

	@Override
	public boolean get() {
		return nativeButton.getState();
	}

}
