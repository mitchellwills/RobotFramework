package robot.imperium.objects;

import java.io.IOException;
import java.util.EnumSet;

import robot.error.RobotException;
import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.PinCapability;
import robot.imperium.packet.ImperiumPacket;
import robot.imperium.packet.Packets;
import robot.io.BinaryOutput;


/**
 * @author Mitchell
 * 
 * A digital output
 *
 */
public class ImperiumDigitalOutput extends ImperiumDeviceObject implements BinaryOutput{

	private boolean currentState;
	/**
	 * Create a new USBIODigitalOutput
	 * @param device
	 * @param pin
	 */
	public ImperiumDigitalOutput(ImperiumDevice device, int pin) {
		super(device, pin);
	}

	@Override
	public EnumSet<PinCapability> getRequiredCapabilities(int pinId) {
		return EnumSet.of(PinCapability.DigitalOutput);
	}
	
	@Override
	public void initialize(){
		set(false);
	}

	@Override
	public void set(boolean value) {
		ImperiumPacket packet = new ImperiumPacket();
		Packets.makeSetValue(packet, getObjectId(), value?1:0);
		try {
			getDevice().sendPacket(packet);
			
		} catch (IOException e) {
			throw new RobotException("Error setting digital output", e);
		}
	}

	@Override
	public boolean get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		currentState = (value!=0);
	}

}
