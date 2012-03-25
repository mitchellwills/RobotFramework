package robot;

/**
 * @author Mitchell
 * 
 * Represents a robot
 *
 */
public abstract class Robot {
	/**
	 * Initialization of all robot commuincation with other devices should occur here
	 */
	public abstract void initializeIO();
	/**
	 * perform the main actions of the robot
	 */
	public abstract void run();
}
