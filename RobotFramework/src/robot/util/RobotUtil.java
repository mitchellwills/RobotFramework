package robot.util;

import java.util.*;

/**
 * @author Mitchell
 * 
 * Utility methods for robots
 *
 */
public final class RobotUtil {
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
	 * @return if the value is contained within the specified bounds exclusive
	 */
	public static boolean within(double value, double min, double max) {
		return min<value && value<max;
	}
	
	/**
	 * Take the input value and return it or the default value if the input value is between the min and max values
	 * @param value the input value
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param defaultValue the value returned if the input is between the min and max values
	 * @return the thresholded value
	 */
	public static double threshold(double value, double min, double max, double defaultValue){
		if(within(value, min, max))
			return defaultValue;
		return value;
	}
	
	/**
	 * Threshold the input value around 0
	 * @param value the input value
	 * @param threshold the amount to threshold around 0 (bounds are -threshold to threshold)
	 * @return the thresholded value
	 */
	public static double threshold(double value, double threshold){
		return threshold(value, -threshold, threshold, 0);
	}

	public static <T> T[] concat(T[] first, T... second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

}
