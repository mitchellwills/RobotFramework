package robot.io.vex;

import robot.error.RobotInitializationException;
import robot.io.ForwardingRobotObjectModel;
import robot.io.RobotObjectListener;
import robot.io.binary.BinaryInput;
import robot.io.joystick.Joystick;
import robot.io.joystick.JoystickAxis;
import robot.io.joystick.JoystickButton;
import robot.io.joystick.JoystickDirectional;
import robot.io.ppm.PPMChannelJoystickAxis;
import robot.io.ppm.PPMReader;

/**
 * 
 * A standard vex transmitter
 * 
 * 
 * @author Mitchell
 *
 */
public class VexTransmitter implements Joystick{
	private static final long AXIS_MIN = 500;
	private static final long AXIS_MAX = 1500;
	private static final long BUTTON_CENTER = 1000;
	private static final long BUTTON_THRESHOLD = 200;
	
	private final ForwardingRobotObjectModel<VexTransmitter, PPMReader> model = new ForwardingRobotObjectModel<VexTransmitter, PPMReader>(this);
	@Override
	public void addUpdateListener(RobotObjectListener<Joystick> listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener<Joystick> listener) {
		model.removeUpdateListener(listener);
	}
	

	private final PPMReader reader;
	private final PPMChannelJoystickAxis[] axes;
	private final VexTransmitterButton[] buttons;
	/**
	 * @param reader the reader that reads the input from the transmitter
	 */
	public VexTransmitter(PPMReader reader) {
		if(reader.getChannelCount()!=6)
			throw new RobotInitializationException("A Vex Transmitter must take a PPM reader with 6 channels");
		this.reader = reader;
		axes = new PPMChannelJoystickAxis[4];
		axes[0] = new PPMChannelJoystickAxis(reader, 0, AXIS_MIN, AXIS_MAX);
		axes[1] = new PPMChannelJoystickAxis(reader, 1, AXIS_MIN, AXIS_MAX);
		axes[2] = new PPMChannelJoystickAxis(reader, 2, AXIS_MIN, AXIS_MAX);
		axes[3] = new PPMChannelJoystickAxis(reader, 3, AXIS_MIN, AXIS_MAX);
		buttons = new VexTransmitterButton[4];
		buttons[0] = new VexTransmitterButton(reader, 4, false);
		buttons[1] = new VexTransmitterButton(reader, 4, true);
		buttons[2] = new VexTransmitterButton(reader, 5, false);
		buttons[3] = new VexTransmitterButton(reader, 5, true);
		reader.addUpdateListener(model);
	}
	@Override
	public String getName() {
		return "Vex Transmitter ["+reader+"]";
	}
	
	
	@Override
	public JoystickButton getButton(int id) {
		return buttons[id];
	}
	@Override
	public int getButtonCount() {
		return 4;
	}
	
	private static class VexTransmitterButton implements JoystickButton{

		private final ForwardingRobotObjectModel<VexTransmitterButton, VexTransmitter> model = new ForwardingRobotObjectModel<VexTransmitterButton, VexTransmitter>(this);
		@Override
		public void addUpdateListener(RobotObjectListener<BinaryInput> listener) {
			model.addUpdateListener(listener);
		}
		@Override
		public void removeUpdateListener(
				RobotObjectListener<BinaryInput> listener) {
			model.removeUpdateListener(listener);
		}
		
		private final PPMReader reader;
		private final int channel;
		private final boolean up;
		public VexTransmitterButton(PPMReader reader, int channel, boolean up){
			this.reader = reader;
			this.channel = channel;
			this.up = up;
		}

		@Override
		public String getName() {
			return "Vex "+(up?"up":"down")+" on "+channel;
		}

		@Override
		public boolean get() {
			if(up)
				return reader.getChannel(channel)<BUTTON_CENTER-BUTTON_THRESHOLD;
			return reader.getChannel(channel)>BUTTON_CENTER+BUTTON_THRESHOLD;
		}
		
	}
	
	
	
	@Override
	public JoystickAxis getAxis(int id) {
		return axes[id];
	}
	@Override
	public int getAxisCount() {
		return 4;
	}
	
	
	@Override
	public JoystickDirectional getDirectional(int id) {
		return null;
	}
	@Override
	public int getDirectionalCount() {
		return 0;
	}
	

}
