package robot.imperium;

import robot.imperium.packet.ImperiumPacket;
import robot.io.RobotObject;

/**
 * @author Mitchell
 * 
 */
public abstract class ImperiumDeviceObject implements RobotObject {
	private final ImperiumDevice device;
	private final int typeId;
	private int objectId  = -1;
	private final int inputSize;
	private final int outputSize;

	/**
	 * create a new Imperium device object and register it with the Imperium
	 * device
	 * 
	 * @param device
	 *            the device the object exists on
	 * @param typeId
	 *            the id that identifies the object type
	 * @param inputSize the size in bytes of the input value
	 * @param outputSize the size in bytes of the output value
	 */
	public ImperiumDeviceObject(int typeId, ImperiumDevice device, int inputSize, int outputSize) {
		this.device = device;
		this.typeId = typeId;
		this.inputSize = inputSize;
		this.outputSize = outputSize;
	}

	protected void init() {
		objectId = device.configure(this);
	}

	/**
	 * @return the device this device exists on
	 */
	public ImperiumDevice getDevice() {
		return device;
	}

	/**
	 * @return the object id of the device
	 */
	public int getObjectId() {
		return objectId;
	}

	/**
	 * @return the id representing the type of the object
	 */
	public int getTypeId() {
		return typeId;
	}
	
	
	
	public int getInputSize() {
		return inputSize;
	}

	public int getOutputSize() {
		return outputSize;
	}

	protected abstract void appendConfiguration(ImperiumPacket packet);
	protected abstract void appendSetValue(ImperiumPacket packet);
	protected abstract void readValue(ImperiumPacket packet);
}