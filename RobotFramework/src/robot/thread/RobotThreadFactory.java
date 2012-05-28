package robot.thread;

public interface RobotThreadFactory {
	public Thread newThread(Runnable target, String name);
}
