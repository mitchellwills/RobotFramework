package robot.util;

/**
 * @author Mitchell
 * 
 * Utility methods for robots
 *
 */
public class RobotUtil {
	/**
	 * sleep a given number of milliseconds
	 * @param timeMS
	 */
	public static void sleep(long timeMS){
		try {
			Thread.sleep(timeMS);
		} catch (InterruptedException e) {
			//just continue
		}
	}
}
