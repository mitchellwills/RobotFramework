package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.LED_DISPLAY_TYPE_ID;

import java.util.EnumSet;
import java.util.Set;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;


/**
 * @author Mitchell
 * 
 * A led display driver
 *
 */
public class ImperiumLEDDisplay extends ImperiumDeviceObject implements UpdatableObject{

	private long currentState;

	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	/**
	 * Create a new Imperium LED Display
	 * @param device
	 * @param outputs the pins connected to the outputs
	 * @param positions the pins connected to the control pins for each display
	 */
	public ImperiumLEDDisplay(ImperiumDevice device, String[] outputs, String[] positions) {
		super(LED_DISPLAY_TYPE_ID, device, toPinArray(device, outputs, positions));
	}

	private static int[] toPinArray(ImperiumDevice device, String[] outputs, String[] positions) {
		int[] pins = new int[outputs.length+positions.length+2];
		pins[0] = outputs.length;
		pins[1] = positions.length;
		for(int i = 0; i<outputs.length; ++i)
			pins[2+i] = device.getHardwareConfiguration().getPinId(outputs[i]);
		for(int i = 0; i<positions.length; ++i)
			pins[2+outputs.length+i] = device.getHardwareConfiguration().getPinId(positions[i]);
		return pins;
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		if(pinId==0 || pinId==1)
			return EnumSet.noneOf(PinCapability.class);
		return EnumSet.of(PinCapability.DigitalOutput);
	}

	public void set(long value) {
		currentState = value;
		getDevice().sendMessagePacket(this, new byte[]{(byte) ((value>>24)&0xFF), (byte) ((value>>16)&0xFF), (byte) ((value>>8)&0xFF), (byte) ((value)&0xFF)}, 0, 4);
	}

	public long get() {
		return currentState;
	}

	@Override
	public void setValue(int value) {
		//don't do anything or could get out of sync
	}

	@Override
	public void message(long[] values) {
		//
	}

}
