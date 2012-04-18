package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.PPM_READER_TYPE_ID;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import robot.error.RobotInitializationException;
import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.ppm.PPMReader;


/**
 * @author Mitchell
 * 
 * PPM reader on an ImperiumDevice
 *
 */
public class ImperiumPPMReader extends ImperiumDeviceObject implements PPMReader{
	private final RobotObjectModel<ImperiumPPMReader> model = new RobotObjectModel<ImperiumPPMReader>(this);

	@Override
	public void addUpdateListener(RobotObjectListener<PPMReader> listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener<PPMReader> listener) {
		model.removeUpdateListener(listener);
	}

	
	private long[][] channels;
	private int channelPosition;

	/**
	 * Create a new Imperium PPM Reader the uses two buffers
	 * @param device
	 * @param pin the pin to be read from
	 * @param channelCount the number of channels that are read
	 */
	public ImperiumPPMReader(ImperiumDevice device, String pin, int channelCount) {
		this(device, pin, channelCount, 2);
	}
	/**
	 * Create a new Imperium PPM Reader
	 * @param device
	 * @param pin the pin to be read from
	 * @param channelCount the number of channels that are read
	 * @param bufferCount the number of buffers to average to get the current value
	 */
	public ImperiumPPMReader(ImperiumDevice device, String pin, int channelCount, int bufferCount) {
		super(PPM_READER_TYPE_ID, device, device.getHardwareConfiguration().getPinId(pin), channelCount);
		if(bufferCount<1)
			throw new RobotInitializationException("buffer count must be >= 1");
		
		channelPosition = 0;
		channels = new long[bufferCount][channelCount];
		for(int b = 0; b<getBufferCount(); ++b)
			for(int i = 0; i<getChannelCount(); ++i)
				channels[b][i] = INVALID_VALUE;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		if(pinId==0)
			return EnumSet.of(PinCapability.Interrupt);
		return Collections.<PinCapability>emptySet();
	}
	
	@Override
	public void initialize(){
		for(int b = 0; b<getBufferCount(); ++b)
			for(int i = 0; i<getChannelCount(); ++i)
				channels[b][i] = INVALID_VALUE;
	}

	@Override
	public synchronized void message(long[] values) {
		for(int i = 0; i<getChannelCount(); ++i)
			channels[channelPosition][i] = values[i];
		channelPosition++;
		if(channelPosition>=channels.length)
			channelPosition = 0;
		model.fireUpdateEvent();
	}
	
	@Override
	public void setValue(int value) {
		//nothing sent using this
	}

	@Override
	public long getChannel(int channel) {
		long value = 0;
		int valueCount = 0;
		for(int i = 0; i<getBufferCount(); ++i){
			if(channels[i][channel]!=INVALID_VALUE){
				value += channels[i][channel];
				++valueCount;
			}
		}
		if(valueCount==0)
			return INVALID_VALUE;
		value/=valueCount;
		return value;
	}

	/**
	 * @return the number of buffers averaged to get the current value
	 */
	public int getBufferCount() {
		return channels.length;
	}

	@Override
	public int getChannelCount() {
		return channels[0].length;
	}

}
