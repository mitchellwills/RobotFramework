package robot.thread;

import robot.util.AsyncTimer;

/**
 * A robot thread that periodically calls the {@link #periodic()} method
 * 
 * @author Mitchell
 *
 */
public abstract class PeriodicRobotThread extends RobotThread {
	private final AsyncTimer timer = new AsyncTimer();
	private final long updateDelay;
	
	/**
	 * Create a new periodic robot thread 
	 * @param robot the robot the thread is for
	 * @param name the name of the thread
	 * @param updateDelay the delay (in ms) between calls to the {@link #periodic()} method
	 */
	public PeriodicRobotThread(String name, long updateDelay) {
		super(name);
		this.updateDelay = updateDelay;
	}

	@Override
	public void run() {
		while(true){
			if(timer.waitComplete()){
				timer.startWaitFromPrevious(updateDelay);
				periodic();
			}
		}
	}
	
	/**
	 * the method that is periodically called by the robot thread
	 */
	public abstract void periodic();

}
