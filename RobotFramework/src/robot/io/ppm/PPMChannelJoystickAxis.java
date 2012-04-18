package robot.io.ppm;

import robot.io.joystick.JoystickAxis;

/**
 * A Joystick Axis that maps a channel from a ppm reader to an axis value
 * 
 * @author Mitchell
 *
 */
public class PPMChannelJoystickAxis implements JoystickAxis{

	private final PPMReader reader;
	private final int channel;
	private final long center;
	private final long range;
	/**
	 * Create a new PPM channel joystick axis
	 * @param reader the reader the axis reads from
	 * @param channel the channel on the ppm reader
	 * @param min the minimum pulse length that maps to -1.0
	 * @param max the maximum pulse length that maps to 1.0
	 */
	public PPMChannelJoystickAxis(PPMReader reader, int channel, long min, long max){
		this.reader = reader;
		this.channel = channel;
		this.center = (min+max)/2;
		this.range = (max-min)/2;
	}
	
	@Override
	public String getName() {
		return "PPM channel "+channel;
	}

	@Override
	public double getValue() {
		long value = reader.getChannel(channel);
		if(value == PPMReader.INVALID_VALUE)
			return 0;
		return (value-center)/((double)range);
	}

}
