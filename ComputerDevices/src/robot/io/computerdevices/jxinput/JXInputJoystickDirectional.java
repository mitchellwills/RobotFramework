package robot.io.computerdevices.jxinput;

import robot.io.joystick.JoystickDirectional;
import de.hardcode.jxinput.Directional;

/**
 * @author Mitchell
 * 
 * A directional on a joystick connected to a computer
 *
 */
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
	
	@Override
	public boolean isCentered(){
		return nativeDirectional.isCentered();
	}

}
