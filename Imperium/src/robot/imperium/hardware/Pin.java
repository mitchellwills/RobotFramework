package robot.imperium.hardware;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Mitchell
 * 
 * represents a pin in a hardware configuration
 *
 */
class Pin {
	private final int pinId;
	private final Set<PinCapability> pinCapabilities;
	private final Set<PinCapability> unmodifiablePinCapabilities;
	private final Set<String> pinLabels;
	private final String boardLabel;
	/**
	 * create a new pin
	 * @param pinId the pin that this exists on
	 * @param boardLabel the label of the pin on the board
	 */
	public Pin(int pinId, String boardLabel){
		this.pinId = pinId;
		pinCapabilities = EnumSet.noneOf(PinCapability.class);
		pinLabels = new HashSet<String>();
		unmodifiablePinCapabilities = Collections.unmodifiableSet(pinCapabilities);
		this.boardLabel = boardLabel;
	}
	
	
	/**
	 * @return the pinId of this pin
	 */
	public int getPinId(){
		return pinId;
	}


	/**
	 * @return the label of this pin on the board
	 */
	public String getBoardLabel() {
		return boardLabel;
	}


	/**
	 * @param label
	 * @return true if this pin has the given label
	 */
	public boolean hasLabel(String label) {
		if(label==null)
			return false;
		return pinLabels.contains(label) || label.equals(boardLabel);
	}


	public void addCapability(PinCapability capability) {
		pinCapabilities.add(capability);
	}
	
	public Set<PinCapability> getCapabilities(){
		return unmodifiablePinCapabilities;
	}


	public void addLabels(String... labels) {
		for(String label:labels)
			pinLabels.add(label);
	}
}
