package robot.thread;

import robot.util.*;

/**
 * A robot thread that periodically calls the {@link #periodic()} method
 * 
 * @author Mitchell
 *
 */
public abstract class PeriodicRobotThread extends RobotThread {
	private final AsyncTimer timer = new AsyncTimer();
	private final long updateDelay;
	private boolean run;
	
	/**
	 * Create a new periodic robot thread 
	 * @param threadManager 
	 * @param name the name of the thread
	 * @param updateDelay the delay (in ms) between calls to the {@link #periodic()} method
	 */
	public PeriodicRobotThread(RobotThreadFactory threadManager, String name, long updateDelay) {
		super(threadManager, name);
		this.updateDelay = updateDelay;
		run = true;
	}

	@Override
	public void run() {
		while(run){
			if(timer.waitComplete()){
				timer.startWaitFromPrevious(updateDelay);
				if(run)
					periodic();
			}
		}
	}
	
	public void stop(){
		run = false;
	}
	
	/**
	 * the method that is periodically called by the robot thread
	 */
	public abstract void periodic();

}
