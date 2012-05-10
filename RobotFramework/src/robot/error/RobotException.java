package robot.error;

/**
 * @author Mitchell
 * 
 * An exception that can occur on the robot
 *
 */
public class RobotException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7938108954188667668L;

	/**
	 * create a new robot exception
	 */
	public RobotException() {
		super();
	}

	/**
	 * create a robot exception with a message and a cause
	 * @param message
	 * @param cause
	 */
	public RobotException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * create a robot exception with a message
	 * @param message
	 */
	public RobotException(String message) {
		super(message);
	}

	/**
	 * create a robot exception with a cause
	 * @param cause
	 */
	public RobotException(Throwable cause) {
		super(cause);
	}
	
}
