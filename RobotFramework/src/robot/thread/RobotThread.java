package robot.thread;



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
	 * @param threadFactory 
	 * @param name the name of the thread
	 */
	public RobotThread(RobotThreadFactory threadFactory, String name){
		thread = threadFactory.newThread(this, name);
	}
	/**
	 * start the thread
	 */
	public void start(){
		thread.start();
	}
	public void stop(){
		thread.stop();
	}
	/**
	 * @return the state of the thread
	 */
	public Thread.State getState(){
		return thread.getState();
	}
}
