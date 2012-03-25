package robot.imperium.hardware;

import java.lang.reflect.Array;
import java.util.EnumSet;

import robot.imperium.PinCapability;

/**
 * @author Mitchell
 * 
 * A hardware configuration with built in support for storing the data
 *
 */
public class DefaultHardwareConfiguration extends HardwareConfiguration {
	private final EnumSet<PinCapability>[] pinCapabilities;
	private final String[] pinLabels;
	
	/**
	 * Create a configureation with a given number of pins
	 * @param name the name of the configuration
	 * @param numPins the number of pins on the hardware
	 */
	public DefaultHardwareConfiguration(String name, int numPins){
		super(name);
		pinCapabilities = (EnumSet<PinCapability>[]) Array.newInstance(EnumSet.class, numPins);
		for(int i = 0; i<numPins; ++i)
			pinCapabilities[i] = EnumSet.noneOf(PinCapability.class);
		pinLabels = new String[numPins];
	}

	protected void addCapability(int pin, PinCapability capability){
		if(pin<0 || pin >= getPinCount())
			throw new RuntimeException("Invalid pin: "+pin+" - Pin Count: "+getPinCount());
		pinCapabilities[pin].add(capability);
	}
	
	protected void addCapability(int pin, String label, PinCapability... capabilities){
		pinLabels[pin] = label;
		for(PinCapability capability:capabilities)
			addCapability(pin, capability);
	}
	
	protected void addCapability(int pin, PinCapability... capabilities){
		for(PinCapability capability:capabilities)
			addCapability(pin, capability);
	}
	
	protected void addAllCapability(PinCapability capability){
		for(int pin = 0; pin<getPinCount(); ++pin)
			addCapability(pin, capability);
	}


	@Override
	public EnumSet<PinCapability> getCapabilities(int pin) {
		if(pin<0 || pin >= getPinCount())
			throw new RuntimeException("Invalid pin: "+pin+" - Pin Count: "+getPinCount());
		return pinCapabilities[pin];
	}
	
	@Override
	public int getPinCount(){
		return pinCapabilities.length;
	}
	
	/**
	 * @param pin
	 * @return the label of the pin
	 */
	public String getLabel(int pin){
		return pinLabels[pin];
	}
	
	/**
	 * @param label
	 * @return the pin with the given label, -1 if no pin was found
	 */
	public int getPin(String label){
		for(int pin = 0; pin<getPinCount(); ++pin){
			if(pinLabels[pin].equals(label))
				return pin;
		}
		return -1;
	}

}
