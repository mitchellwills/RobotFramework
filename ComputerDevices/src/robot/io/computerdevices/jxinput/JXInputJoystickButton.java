package robot.io.computerdevices.jxinput;

import robot.*;
import robot.io.*;
import robot.io.joystick.*;
import de.hardcode.jxinput.*;

/**
 * @author Mitchell
 * 
 * A joystick button that exists on a joystick connected to a computers
 *
 */
public class JXInputJoystickButton implements JoystickButton, Nameable {
	
	private final Button nativeButton;
	JXInputJoystickButton(JXInputJoystick joystick, Button nativeButton){
		joystick.addUpdateListener(model);
		this.nativeButton = nativeButton;
	}

	private final ForwardingRobotObjectModel model = new ForwardingRobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
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
	@Override
	public double getValue() {
		return get()?1:0;
	}

}
