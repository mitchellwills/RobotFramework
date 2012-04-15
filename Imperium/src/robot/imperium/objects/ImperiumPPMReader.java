package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.PPM_READER_TYPE_ID;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

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

	
	private long[] channels;
	/**
	 * Create a new Imperium PPM Reader
	 * @param device
	 * @param pin the pin to be read from
	 * @param channelCount the number of channels that are read
	 */
	public ImperiumPPMReader(ImperiumDevice device, String pin, int channelCount) {
		super(PPM_READER_TYPE_ID, device, device.getHardwareConfiguration().getPinId(pin), channelCount);
		channels = new long[channelCount];
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		if(pinId==0)
			return EnumSet.of(PinCapability.Interrupt);
		return Collections.<PinCapability>emptySet();
	}
	
	@Override
	public void initialize(){
		for(int i = 0; i<getChannelCount(); ++i)
			channels[i] = 0;
	}

	@Override
	public void message(long[] values) {
		for(int i = 0; i<getChannelCount(); ++i)
			channels[i] = values[i];
		model.fireUpdateEvent();
	}
	
	@Override
	public void setValue(int value) {
		//nothing sent using this
	}

	@Override
	public long getChannel(int channel) {
		return channels[channel];
	}

	@Override
	public int getChannelCount() {
		return channels.length;
	}

}
