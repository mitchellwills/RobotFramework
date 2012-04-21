package robot.imperium;

import java.util.Set;

import robot.imperium.hardware.ImperiumHardwareConfiguration;
import robot.imperium.hardware.PinCapability;
import robot.io.RobotObject;


/**
 * @author Mitchell
 *
 */
public abstract class ImperiumDeviceObject implements RobotObject{
	private final ImperiumDevice device;
	private final int typeId;
	private final int[] pins;
	private final int objectId;
	

	/**
	 * create a new Imperium device object and register it with the Imperium device
	 * 
	 * @param device the device the object exists on
	 * @param typeId the id that identifies the object type
	 */
	public ImperiumDeviceObject(int typeId, ImperiumDevice device){
		this(typeId, device, new int[]{});
	}
	
	/**
	 * create a new Imperium device object and register it with the Imperium device
	 * 
	 * @param device the device the object exists on
	 * @param typeId the id that identifies the object type
	 * @param pinLabels the labels of  that the object uses
	 */
	public ImperiumDeviceObject(int typeId, ImperiumDevice device, String... pinLabels){
		this(typeId, device, toPins(device.getHardwareConfiguration(), pinLabels));
	}
	
	private static int[] toPins(ImperiumHardwareConfiguration config, String... pinLabels){
		int[] pins = new int[pinLabels.length];
		for(int i = 0; i<pins.length; ++i)
			pins[i] = config.getPinId(pinLabels[i]);
		return pins;
	}
	
	/**
	 * create a new Imperium device object and register it with the Imperium device
	 * 
	 * @param device the device the object exists on
	 * @param typeId the id that identifies the object type
	 * @param pins the pins that the object uses
	 */
	public ImperiumDeviceObject(int typeId, ImperiumDevice device, int... pins){
		this.device = device;
		this.typeId = typeId;
		this.pins = pins;
		objectId = device.register(this);
	}
	
	/**
	 * @return the device this device exists on
	 */
	public ImperiumDevice getDevice(){
		return device;
	}
	
	/**
	 * @return the object id of the device
	 */
	public int getObjectId(){
		return objectId;
	}
	
	/**
	 * @return the id representing the type of the object
	 */
	public int getTypeId() {
		return typeId;
	}
	
	/**
	 * @param pinId
	 * @return the pin that is at the given pinId
	 */
	public int getPin(int pinId){
		return pins[pinId];
	}
	
	/**
	 * @return the number of pins used by this object
	 */
	public int getPinCount(){
		return pins.length;
	}
	
	/**
	 * @param pinId
	 * @return the required capabilities for a given pinId
	 */
	public abstract Set<PinCapability> getRequiredCapabilities(int pinId);
	
	/**
	 * initialize the device
	 * will be called after the device is globally configured
	 */
	public abstract void initialize();
	
	/**
	 * #!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!#<br>
	 * NOTE: this is not intended to be called by a user<br>
	 * #!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!#
	 * @param value new value
	 * 
	 */
	public abstract void setValue(int value);

	/**
	 * #!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!#<br>
	 * NOTE: this is not intended to be called by a user<br>
	 * #!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!#
	 * @param values the values in the message
	 * 
	 */
	public abstract void message(long[] values);

	
}
