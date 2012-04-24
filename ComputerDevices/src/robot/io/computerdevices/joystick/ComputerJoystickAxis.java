package robot.io.computerdevices.joystick;

import robot.io.ForwardingRobotObjectModel;
import robot.io.RobotObjectListener;
import robot.io.computerdevices.DIJoystick;
import robot.io.joystick.JoystickAxis;

/**
 * @author Mitchell
 * 
 * Represents an axis from a joystick connected to a computer
 *
 */
public class ComputerJoystickAxis implements JoystickAxis{
	private final ForwardingRobotObjectModel model = new ForwardingRobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(
			RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private final ComputerJoystick joystick;
	private final int id;
	ComputerJoystickAxis(ComputerJoystick joystick, int id){
		this.joystick = joystick;
		joystick.addUpdateListener(model);
		this.id = id;
	}

	@Override
	public String getName() {
		return DIJoystick.INSTANCE.getAxisName(joystick.getNativePointer(), id);
	}


	@Override
	public double getValue() {
		int raw = DIJoystick.INSTANCE.getAxis(joystick.getNativePointer(), id);
		return (raw-32767)/32768.0;
	}

}
