package robot.imperium;

import java.util.EnumSet;

import robot.io.RobotDevice;


/**
 * @author Mitchell
 *
 */
public abstract class ImperiumDeviceObject implements RobotDevice{
	private final ImperiumDevice device;
	private final int[] pins;
	private final int objectId;
	
	/**
	 * create a new Imperium device object and register it with the Imperium device
	 * 
	 * @param device the device the object exists on
	 * @param pins the pins that the object uses
	 */
	public ImperiumDeviceObject(ImperiumDevice device, int... pins){
		this.device = device;
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
	 * @param pinId
	 * @return the pin that is at the given pinId
	 */
	public int getPin(int pinId){
		return pins[pinId];
	}
	
	/**
	 * @return the number of pins used by this object
	 */
	public int getUsedPinCount(){
		return pins.length;
	}
	
	/**
	 * @param pinId
	 * @return the required capabilities for a given pinId
	 */
	public abstract EnumSet<PinCapability> getRequiredCapabilities(int pinId);
	
	/**
	 * initialize the device
	 * will be called after the device is globally configured
	 */
	public abstract void initialize();
	
	/**
	 * @param value new value
	 * NOTE: this is only intended to be called by a user
	 */
	public abstract void setValue(int value);
	
}
