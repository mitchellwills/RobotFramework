package robot.io.computerdevices.joystick;

import robot.io.computerdevices.DIJoystick;
import robot.io.joystick.JoystickDirectional;

public class ComputerJoystickDirectional implements JoystickDirectional{
	
	private final ComputerJoystick joystick;
	private final int id;
	ComputerJoystickDirectional(ComputerJoystick joystick, int id){
		this.joystick = joystick;
		this.id = id;
	}

	@Override
	public String getName() {
		return DIJoystick.INSTANCE.getButtonName(joystick.getNativePointer(), id);
	}

	@Override
	public double getAngle() {
		return DIJoystick.INSTANCE.getPOV(joystick.getNativePointer(), id);
	}

	@Override
	public double getMagnatude() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCentered() {
		// TODO Auto-generated method stub
		return false;
	}

}
