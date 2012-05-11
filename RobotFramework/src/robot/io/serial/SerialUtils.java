package robot.io.serial;

import java.util.HashMap;
import java.util.Map;

import robot.Robot;
import robot.io.RobotObject;
import robot.io.factory.RobotObjectFactory;

/**
 * Utilities for dealing with {@link SerialInterface}s
 * 
 * @author Mitchell
 *
 */
public class SerialUtils {
	/**
	 * Get a serial interface from a factory based on location and baud rate
	 * @param factory
	 * @param location
	 * @param baud
	 * @return a serial interface
	 */
	public static SerialInterface getSerialInterface(RobotObjectFactory factory, String location, int baud){
		Map<String, String> serialPortParams = new HashMap<String, String>();
		serialPortParams.put(RobotObject.PARAM_LOCATION, location);
		serialPortParams.put(SerialInterface.PARAM_BAUD, Integer.toString(baud));
		return Robot.getInstance().getFactory().getObject(SerialInterface.class, serialPortParams);
	}
}
