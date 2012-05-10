package robot.thread;

import robot.Robot;


/**
 * A Thread that is run 
 * 
 * @author Mitchell
 *
 */
public abstract class RobotThread implements Runnable{
	private Thread thread;
	/**
	 * Create a new thread for a given robot
	 * @param robot
	 * @param name the name of the thread
	 */
	public RobotThread(String name){
		thread = Robot.getInstance().getThreadManager().newThread(this, name);
	}
	/**
	 * start the thread
	 */
	public void start(){
		thread.start();
	}
	/**
	 * @return the state of the thread
	 */
	public Thread.State getState(){
		return thread.getState();
	}
}
