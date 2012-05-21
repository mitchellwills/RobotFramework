package robot.imperium.hardware;

import robot.error.RobotInitializationException;

/**
 * Indicates that an object location is unknown
 * 
 * @author Mitchell
 *
 */
public class UnknownImperiumObjectException extends RobotInitializationException {
	/**
	 * Create a new exception
	 * @param label 
	 */
	public UnknownImperiumObjectException(String label) {
		super("Unknown object in "+label+" in hardware");
	}

}
