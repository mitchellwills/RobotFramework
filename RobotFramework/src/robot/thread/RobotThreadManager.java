package robot.thread;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author Mitchell
 * 
 * The manager of all threads running on the robot
 *
 */
public class RobotThreadManager implements UncaughtExceptionHandler {
	private final ThreadGroup robotThreadGroup = new ThreadGroup("Robot Thread Group");
	/**
	 * Create a new thread for the given runnable target
	 * @param target target runnable
	 * @param name thread name
	 * @return the new thread
	 */
	public Thread newThread(Runnable target, String name){
		Thread thread = new Thread(robotThreadGroup, target, name);
		thread.setDaemon(false);
		thread.setUncaughtExceptionHandler(this);
		return thread;
	}
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(t+" threw the exception "+e.getClass()+": "+e.getMessage());
		e.printStackTrace();
	}
}
