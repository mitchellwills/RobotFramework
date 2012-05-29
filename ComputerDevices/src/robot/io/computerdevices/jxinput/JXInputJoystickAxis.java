package robot.io.computerdevices.jxinput;

import robot.*;
import robot.io.*;
import robot.io.joystick.*;
import de.hardcode.jxinput.*;

/**
 * @author Mitchell
 * 
 * An axis joystick on a joystick connected to a computer
 *
 */
public class JXInputJoystickAxis implements JoystickAxis, Nameable {

	private final Axis nativeAxis;
	JXInputJoystickAxis(JXInputJoystick joystick, Axis nativeAxis) {
		joystick.addUpdateListener(model);
		this.nativeAxis = nativeAxis;
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
		if(nativeAxis==null)
			return null;
		return nativeAxis.getName();
	}
	@Override
	public double getValue() {
		return nativeAxis.getValue();
	}

}
