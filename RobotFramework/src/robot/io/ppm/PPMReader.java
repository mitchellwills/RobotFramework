package robot.io.ppm;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * An input that reads a voltage
 *
 */
public interface PPMReader extends Input, UpdatableObject{
	/**
	 * the value returned by getChannel if there is no valid data to be returned
	 */
	public static final int INVALID_VALUE = -1;
	
	/**
	 * @param channel a ppm channel (0 indexed)
	 * @return the value of the given channel in us
	 */
	public long getChannel(int channel);
	/**
	 * @return the number of ppm channels
	 */
	public int getChannelCount();
}
