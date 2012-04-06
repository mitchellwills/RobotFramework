package robot.io.computerdevices.joystick;

import robot.io.computerdevices.DIJoystick;
import robot.io.joystick.JoystickDirectional;

/**
 * @author Mitchell
 * 
 * Represents a directional on a joystick connected to a computer
 *
 */
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
	
	private double getRaw(){
		return DIJoystick.INSTANCE.getPOV(joystick.getNativePointer(), id);
	}

	@Override
	public double getAngle() {
		double raw = getRaw();
		if(raw == -1)
			return 0;
		return getRaw()/100;
	}

	@Override
	public double getMagnatude() {
		if(getRaw()==-1)
			return 0;
		return 1;
	}

	@Override
	public boolean isCentered() {
		return getMagnatude()==0;
	}

}
