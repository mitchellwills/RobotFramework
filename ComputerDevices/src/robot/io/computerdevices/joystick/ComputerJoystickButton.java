package robot.io.computerdevices.joystick;

import robot.io.computerdevices.DIJoystick;
import robot.io.joystick.JoystickButton;

public class ComputerJoystickButton implements JoystickButton{
	
	private final ComputerJoystick joystick;
	private final int id;
	ComputerJoystickButton(ComputerJoystick joystick, int id){
		this.joystick = joystick;
		this.id = id;
	}

	@Override
	public String getName() {
		return DIJoystick.INSTANCE.getButtonName(joystick.getNativePointer(), id);
	}
	@Override
	public boolean get() {
		return DIJoystick.INSTANCE.getButton(joystick.getNativePointer(), id);
	}
	

}
