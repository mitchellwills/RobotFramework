package robot.io.computerdevices.jxinput;

import robot.io.joystick.JoystickButton;
import de.hardcode.jxinput.Button;

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
	public boolean get() {
		return nativeButton.getState();
	}

}
