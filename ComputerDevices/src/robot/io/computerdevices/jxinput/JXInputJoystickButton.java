package robot.io.computerdevices.jxinput;

import robot.io.ForwardingRobotObjectModel;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryInput;
import robot.io.computerdevices.joystick.ComputerJoystick;
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

	private final ForwardingRobotObjectModel<JXInputJoystickButton, ComputerJoystick> model = new ForwardingRobotObjectModel<JXInputJoystickButton, ComputerJoystick>(this);
	@Override
	public void addUpdateListener(RobotObjectListener<BinaryInput> listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(
			RobotObjectListener<BinaryInput> listener) {
		model.removeUpdateListener(listener);
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
