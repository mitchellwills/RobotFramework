package robot.io.ppm;

import robot.io.Input;
import robot.io.UpdatableObject;

/**
 * @author Mitchell
 * 
 * An input that reads a voltage
 *
 */
public interface PPMReader extends Input, UpdatableObject<PPMReader>{
	/**
	 * @param channel a ppm channel
	 * @return the value of the given channel in us
	 */
	public long getChannel(int channel);
	/**
	 * @return the number of ppm channels
	 */
	public int getChannelCount();
}
