package robot.io.computerdevices.jxinput;

import robot.*;
import robot.io.*;
import robot.io.joystick.*;
import de.hardcode.jxinput.*;

/**
 * @author Mitchell
 * 
 * A directional on a joystick connected to a computer
 *
 */
public class JXInputJoystickDirectional implements JoystickDirectional, Nameable {

	private final Directional nativeDirectional;
	JXInputJoystickDirectional(JXInputJoystick joystick, Directional nativeDirectional) {
		this.nativeDirectional = nativeDirectional;
		joystick.addUpdateListener(model);
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
		return nativeDirectional.getName();
	}

	@Override
	public double getAngle() {
		return nativeDirectional.getDirection()/100.0;
	}

	@Override
	public double getMagnitude() {
		return nativeDirectional.getValue();
	}
	
	@Override
	public boolean isCentered(){
		return nativeDirectional.isCentered();
	}

}
