package robot;

import robot.io.RobotObject;

/**
 * @author Mitchell
 * 
 * A class that listeners for updates to a robot
 *
 */
public interface RobotListener {
	/**
	 * Called when a new object is added to the robot
	 * @param name
	 * @param object
	 */
	public void onNewObject(String name, RobotObject object);
}
