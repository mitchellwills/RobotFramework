package robot.io;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Mitchell
 * 
 * A model that can be used to store data related to an implementation of an object
 * @param <T> the type this object represents
 *
 */
public class RobotObjectModel<T extends RobotObject> {
	private final Set<RobotObjectListener<? super T>> listeners = new HashSet<RobotObjectListener<? super T>>();

	private final T object;
	/**
	 * @param object the object that this model stores data for
	 */
	public RobotObjectModel(T object) {
		this.object = object;
	}
	
	/**
	 * Add an update listener
	 * @param listener
	 */
	public void addUpdateListener(RobotObjectListener<? super T> listener){
		listeners.add(listener);
	}
	/**
	 * Remove an update listener
	 * @param listener
	 */
	public void removeUpdateListener(RobotObjectListener<? super T> listener){
		listeners.remove(listener);
	}
	
	/**
	 * 
	 */
	public void fireUpdateEvent(){
		for(RobotObjectListener<? super T> listener:listeners)
			listener.objectUpdated(object);
	}
}
