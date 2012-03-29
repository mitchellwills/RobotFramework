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

	/**
	 * 
	 * limit a value so that it is between a min and a max
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return the limited value
	 */
	public static double limit(double value, double min, double max) {
		if(value<min)
			return min;
		if(value>max)
			return max;
		return value;
		
	}

	/**
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return if the value is contained withing the specified bounds exclusive
	 */
	public static boolean within(double value, double min, double max) {
		return min<value && value<max;
		
	}
}
