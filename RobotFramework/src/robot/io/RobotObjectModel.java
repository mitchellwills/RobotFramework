package robot.io;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Mitchell
 * 
 * A model that can be used to store data related to an implementation of an object
 *
 */
public class RobotObjectModel {
	private final Set<RobotObjectListener> listeners = new HashSet<RobotObjectListener>();

	private final RobotObject object;
	/**
	 * @param object the object that this model stores data for
	 */
	public RobotObjectModel(RobotObject object) {
		this.object = object;
	}
	
	/**
	 * Add an update listener
	 * @param listener
	 */
	public void addUpdateListener(RobotObjectListener listener){
		listeners.add(listener);
	}
	/**
	 * Remove an update listener
	 * @param listener
	 */
	public void removeUpdateListener(RobotObjectListener listener){
		listeners.remove(listener);
	}
	
	/**
	 * 
	 */
	public void fireUpdateEvent(){
		for(RobotObjectListener listener:listeners)
			listener.objectUpdated(object);
	}
}
