package robot.io.computerdevices.jxinput;

import de.hardcode.jxinput.Directional;
import robot.io.RobotObjectListener;
import robot.io.joystick.JoystickDirectional;

public class JXInputJoystickDirectional implements JoystickDirectional {

	private final Directional nativeDirectional;
	JXInputJoystickDirectional(Directional nativeDirectional) {
		this.nativeDirectional = nativeDirectional;
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
	public double getMagnatude() {
		return nativeDirectional.getValue();
	}
	
	public boolean isCentered(){
		return nativeDirectional.isCentered();
	}

}
