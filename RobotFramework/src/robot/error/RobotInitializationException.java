package robot.error;

/**
 * @author Mitchell
 * 
 * An exception that can occur on the robot during initialization
 *
 */
public class RobotInitializationException extends RobotException {

	/**
	 * create a new robot exception
	 */
	public RobotInitializationException() {
		super();
	}

	/**
	 * create a robot exception with a message and a cause
	 * @param message
	 * @param cause
	 */
	public RobotInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * create a robot exception with a message
	 * @param message
	 */
	public RobotInitializationException(String message) {
		super(message);
	}

	/**
	 * create a robot exception with a cause
	 * @param cause
	 */
	public RobotInitializationException(Throwable cause) {
		super(cause);
	}
	
}
