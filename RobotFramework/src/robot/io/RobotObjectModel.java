package robot.io;

import java.util.HashSet;
import java.util.Set;


public class RobotObjectModel {
	private final Set<RobotObjectListener> listeners = new HashSet<>();

	private final RobotObject object;
	public RobotObjectModel(RobotObject object) {
		this.object = object;
	}
	
	public void addUpdateListener(RobotObjectListener listener){
		listeners.add(listener);
	}
	public void removeUpdateListener(RobotObjectListener listener){
		listeners.remove(listener);
	}
	
	public void fireUpdateEvent(){
		for(RobotObjectListener listener:listeners)
			listener.objectUpdated(object);
	}
}
