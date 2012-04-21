package robot.io;

/**
 * @author Mitchell
 * 
 * an class that listens for updates from a specific RobotObject type
 *
 */
public interface RobotObjectListener {
	/**
	 * Called when a robot object was updated
	 * @param object the object that was updated
	 */
	public void objectUpdated(RobotObject object);
}
