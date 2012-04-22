package robot.io.computerdevices;

import robot.error.RobotInitializationException;
import robot.io.RobotObjectFactory;
import robot.io.computerdevices.joystick.ComputerJoystick;
import robot.io.joystick.Joystick;
import robot.io.serial.RxTxComputerSerialPort;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 * 
 * A RobotObjectFactory that creates objects connected directly to the computer
 *
 */
public class ComputerDeviceFactory extends RobotObjectFactory {

	@Override
	public SerialInterface getSerialInterface(String location, int baud) {
		return new RxTxComputerSerialPort(location, baud);
	}

	@Override
	public Joystick getJoystick(String location) {
		try {
			int id = Integer.parseInt(location);
			return ComputerJoystick.getJoystick(id);
		} catch (NumberFormatException e) {
			throw new RobotInitializationException(
					"Joystick location must be a number");
		}
	}
}
