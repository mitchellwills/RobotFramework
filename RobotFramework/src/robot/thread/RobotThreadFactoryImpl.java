package robot.thread;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author Mitchell
 * 
 * The manager of all threads running on the robot
 *
 */
public class RobotThreadFactoryImpl implements UncaughtExceptionHandler, RobotThreadFactory {
	private final ThreadGroup robotThreadGroup = new ThreadGroup("Robot Thread Group");
	
	@Override
	public Thread newThread(Runnable target, String name){
		Thread thread = new Thread(robotThreadGroup, target, name);
		thread.setDaemon(false);
		thread.setUncaughtExceptionHandler(this);
		return thread;
	}


	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(e+" throw in thread: "+t);
	}
	
	
}
