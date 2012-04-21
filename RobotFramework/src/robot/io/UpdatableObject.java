package robot.io;

/**
 * @author Mitchell
 * 
 * An object that can updated and will notify a RobotObject Listener
 *
 */
public interface UpdatableObject extends RobotObject{

	/**
	 * Add a listener to the object
	 * @param listener the listener to be added
	 */
	public void addUpdateListener(RobotObjectListener listener);
	/**
	 * remove a listener from the object
	 * @param listener the listener to be removed
	 */
	public void removeUpdateListener(RobotObjectListener listener);
}
