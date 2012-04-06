package robot.io;

/**
 * @author Mitchell
 * 
 * an class that listens for updates from a specific RobotObject type
 *
 * @param <T>
 */
public interface RobotObjectListener<T extends RobotObject> {
	/**
	 * Called when a robot object was updated
	 * @param object the object that was updated
	 */
	public void objectUpdated(T object);
}
