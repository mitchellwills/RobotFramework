package robot.io.computerdevices.joystick;

import robot.io.computerdevices.DIJoystick;
import robot.io.joystick.JoystickAxis;

public class ComputerJoystickAxis implements JoystickAxis{
	
	private final ComputerJoystick joystick;
	private final int id;
	ComputerJoystickAxis(ComputerJoystick joystick, int id){
		this.joystick = joystick;
		this.id = id;
	}

	@Override
	public String getName() {
		return DIJoystick.INSTANCE.getAxisName(joystick.getNativePointer(), id);
	}

	@Override
	public AxisType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getValue() {
		int raw = DIJoystick.INSTANCE.getAxis(joystick.getNativePointer(), id);
		return (raw-32767)/32768.0;
	}

}
