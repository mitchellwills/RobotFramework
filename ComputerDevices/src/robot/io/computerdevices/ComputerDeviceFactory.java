package robot.io.computerdevices;

import java.util.Map;

import robot.error.RobotInitializationException;
import robot.io.RobotObject;
import robot.io.computerdevices.joystick.ComputerJoystick;
import robot.io.computerdevices.rxtx.RxTxComputerSerialPort;
import robot.io.factory.RobotObjectFactory;
import robot.io.joystick.Joystick;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 * 
 * A RobotObjectFactory that creates objects connected directly to the computer
 *
 */
public class ComputerDeviceFactory extends RobotObjectFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T extends RobotObject> T getObject(Class<T> type, Map<String, String> params){
		if(type==null)
			throw new RobotInitializationException("Cannot create an object of type null");
		if(type.isAssignableFrom(SerialInterface.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			int baud = getIntParam(params, SerialInterface.PARAM_BAUD);
			return (T) new RxTxComputerSerialPort(location, baud);
		}
		else if(type.isAssignableFrom(Joystick.class)){
			try {
				int location = getIntParam(params, RobotObject.PARAM_LOCATION);
				return (T) ComputerJoystick.getJoystick(location);
			} catch (NumberFormatException e) {
				throw new RobotInitializationException(
						"Joystick location must be a number");
			}
		}
		
		return null;
	}

}
