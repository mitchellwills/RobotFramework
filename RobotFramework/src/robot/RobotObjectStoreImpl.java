package robot;

import java.util.*;

import robot.error.*;
import robot.io.*;

public class RobotObjectStoreImpl implements RobotObjectStore{
	
	private final Map<String, RobotObject> objects = new HashMap<String, RobotObject>();
	private final Set<RobotListener> listeners = new HashSet<RobotListener>();
	
	
	@Override
	public <T extends RobotObject> T putObject(String name, T object){
		if(objects.containsKey(name))
			throw new RobotInitializationException("An object already exists with the name: "+name);
		objects.put(name, object);
		fireNewObjectEvent(name, object);
		return object;
	}
	
	/**
	 * @param name name of the object
	 * @return the robot object associated with the given name
	 */
	@Override
	public RobotObject getObject(String name){
		return objects.get(name);
	}
	
	
	
	
	
	
	
	
	/**
	 * Add an update listener
	 * @param listener
	 */
	@Override
	public void addListener(RobotListener listener){
		listeners.add(listener);
	}
	/**
	 * Remove an update listener
	 * @param listener
	 */
	@Override
	public void removeListener(RobotListener listener){
		listeners.remove(listener);
	}
	
	/**
	 * @param name the name given to the object
	 * @param object the new object
	 * 
	 */
	void fireNewObjectEvent(String name, RobotObject object){
		for(RobotListener listener:listeners)
			listener.onNewObject(name, object);
	}
}
