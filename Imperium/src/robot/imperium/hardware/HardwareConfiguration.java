package robot.imperium.hardware;

import java.util.EnumSet;

import robot.imperium.PinCapability;



/**
 * @author Mitchell
 * 
 * Represents the hardware on a device
 *
 */
public abstract class HardwareConfiguration {
	private final String name;
	protected HardwareConfiguration(String name){
		this.name = name;
	}
	
	/**
	 * @param pin the pin to check compatibility on
	 * @param capability capability to check for
	 * @return true if the pin can support the capability
	 */
	public boolean supports(int pin, PinCapability capability){
		return getCapabilities(pin).contains(capability);
	}
	
	/**
	 * @param pin the pin to check compatibility on
	 * @param capabilities capabilities to check for
	 * @return true if the pin can support all the capabilities
	 */
	public boolean supports(int pin, EnumSet<PinCapability> capabilities){
		return getCapabilities(pin).containsAll(capabilities);
	}
	
	/**
	 * @param pin the pin (0 - (maxPins-1))
	 * @return the capabilities for a given pin
	 */
	public abstract EnumSet<PinCapability> getCapabilities(int pin);
	
	/**
	 * @return the number of pins on the device
	 */
	public abstract int getPinCount();

	/**
	 * @return the name of the configuration
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the frequency of the hardware's PWM output in Hz
	 */
	public abstract double getPWMFrequency();
}
