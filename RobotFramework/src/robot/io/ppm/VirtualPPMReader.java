package robot.io.ppm;

import robot.error.RobotException;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;


/**
 * @author Mitchell
 * 
 * A PPM reader whose channel values can be set
 *
 */
public class VirtualPPMReader implements PPMReader{
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private long[] values;
	/**
	 * Create a new Virtual PPM reader with a given number of channels
	 * @param numChannels
	 */
	public VirtualPPMReader(int numChannels){
		values = new long[numChannels];
		for(int i = 0; i<values.length; ++i)
			values[i] = PPMReader.INVALID_VALUE;
	}
	
	@Override
	public long getChannel(int channel) {
		if(channel<0||channel>=getChannelCount())
			throw new RobotException("The Virtual PPM reader does not have channel "+channel);
		return values[channel];
	}

	@Override
	public int getChannelCount() {
		return values.length;
	}
	
	/**
	 * Set the value of a single channel
	 * 
	 * @param channel
	 * @param channelValue
	 */
	public void setChannelValue(final int channel, final long channelValue){
		if(channel<0||channel>=getChannelCount())
			throw new RobotException("The Virtual PPM reader does not have channel "+channel);
		values[channel] = channelValue;
		model.fireUpdateEvent();
	}
	
	/**
	 * Set the value of all channels at once
	 * @param channelValues
	 */
	public void setChannelValues(final long... channelValues){
		if(values.length!=channelValues.length)
			throw new RobotException("The number of channels being set on a virtual PPM reader must match the number it has");
		for(int i = 0; i<values.length; ++i)
			values[i] = channelValues[i];
		model.fireUpdateEvent();
	}

}
