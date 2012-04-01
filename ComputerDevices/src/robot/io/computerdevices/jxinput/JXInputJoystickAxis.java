package robot.io.computerdevices.jxinput;

import de.hardcode.jxinput.Axis;
import robot.io.joystick.JoystickAxis;

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
	public AxisType getType() {
		if(nativeAxis==null)
			return null;
		switch(nativeAxis.getType()){
		case Axis.ROTATION:
			return AxisType.ROTATION;
		case Axis.SLIDER:
			return AxisType.SLIDER;
		case Axis.TRANSLATION:
			return AxisType.TRANSLATION;
		}
		return AxisType.UNKNOWN;
	}
	@Override
	public double getValue() {
		return nativeAxis.getValue();
	}

}
