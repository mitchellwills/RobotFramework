package robot.io.computerdevices.jxinput;

import robot.io.joystick.JoystickAxis;
import de.hardcode.jxinput.Axis;

/**
 * @author Mitchell
 * 
 * An axis joystick on a joystick connected to a computer
 *
 */
public class JXInputJoystickAxis implements JoystickAxis {

	private final Axis nativeAxis;
	JXInputJoystickAxis(Axis nativeAxis) {
		this.nativeAxis = nativeAxis;
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
