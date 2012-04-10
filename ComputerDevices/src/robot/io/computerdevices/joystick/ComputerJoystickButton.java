package robot.io.computerdevices.joystick;

import robot.io.ForwardingRobotObjectModel;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryInput;
import robot.io.computerdevices.DIJoystick;
import robot.io.joystick.JoystickButton;

/**
 * @author Mitchell
 * 
 * represents a button on a joystick connected to a computer
 *
 */
public class ComputerJoystickButton implements JoystickButton{
	
	private final ComputerJoystick joystick;
	private final int id;
	ComputerJoystickButton(ComputerJoystick joystick, int id){
		this.joystick = joystick;
		this.id = id;
	}
	

	private final ForwardingRobotObjectModel<ComputerJoystickButton, ComputerJoystick> model = new ForwardingRobotObjectModel<ComputerJoystickButton, ComputerJoystick>(this);
	@Override
	public void addUpdateListener(RobotObjectListener<BinaryInput> listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(
			RobotObjectListener<BinaryInput> listener) {
		model.removeUpdateListener(listener);
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
