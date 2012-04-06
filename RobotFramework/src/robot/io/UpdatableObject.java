package robot.io;

/**
 * @author Mitchell
 * 
 * An object that can updated and will notify a RobotObject Listener
 * @param <T> the type of the object
 *
 */
public interface UpdatableObject<T extends RobotObject> extends RobotObject{

	/**
	 * Add a listener to the object
	 * @param listener the listener to be added
	 */
	public void addUpdateListener(RobotObjectListener<T> listener);
	/**
	 * remove a listener from the object
	 * @param listener the listener to be removed
	 */
	public void removeUpdateListener(RobotObjectListener<T> listener);
}
