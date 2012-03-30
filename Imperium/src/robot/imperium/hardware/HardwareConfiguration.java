package robot.imperium.hardware;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;




/**
 * @author Mitchell
 * 
 * Represents the hardware on a device
 * The device has a list of pins which have a name (what the hardware calls them), a label (what they are labeled on the board), and a list of capabilities
 *
 */
public abstract class HardwareConfiguration {
	/**
	 * label of a pin that has an onboard led
	 */
	public static final String ONBOARD_LED = "ONBOARD_LED";
	
	private final String name;
	private final Map<Integer, Pin> pins;
	private final double pwmFrequency;
	
	protected HardwareConfiguration(String name, double pwmFrequency){
		this.name = name;
		pins = new HashMap<Integer, Pin>();
		this.pwmFrequency = pwmFrequency;
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
	 * @param pinId the pin to check compatibility on
	 * @param capabilities capabilities to check for
	 * @return true if the pin can support all the capabilities
	 */
	public boolean supports(int pinId, EnumSet<PinCapability> capabilities){
		return getCapabilities(pinId).containsAll(capabilities);
	}

	protected Pin addPin(int pinId, String boardLabel, PinCapability... capabilities){
		Pin pin = new Pin(pinId, boardLabel);
		for(PinCapability capability:capabilities)
			pin.addCapability(capability);
		pins.put(pinId, pin);
		return pin;
	}


	/**
	 * @param pinId the pin (0 - (maxPins-1))
	 * @return the capabilities for a given pin
	 */
	public Set<PinCapability> getCapabilities(int pinId) {
		Pin p = pins.get(pinId);
		if(p==null)
			return Collections.<PinCapability>emptySet();
		return p.getCapabilities();
	}
	

	/**
	 * @param pinId
	 * @return the label of the pin (what it is labeled on the board)
	 */
	public String getLabel(int pinId){
		return pins.get(pinId).getBoardLabel();
	}
	
	/**
	 * @param label the name or label of a pin
	 * @return the pin with the given label or name, null if no pin was found
	 */
	private Pin getPin(String label){
		for(Pin pin:pins.values()){
			if(pin.hasLabel(label))
				return pin;
		}
		return null;
	}
	/**
	 * @param label
	 * @return the pinId of the pin
	 */
	public int getPinId(String label){
		Pin p = getPin(label);
		if(p==null)
			return -1;
		return p.getPinId();
	}

	/**
	 * @return the frequency of the hardware's PWM output in Hz
	 */
	public double getPWMFrequency() {
		return pwmFrequency;
	}

	/**
	 * @return the name of the configuration
	 */
	public String getName() {
		return name;
	}
}
