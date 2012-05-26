package robot.imperium.hardware;

import robot.error.RobotInitializationException;
import robot.imperium.ImperiumDevice;
import robot.imperium.resources.DeviceResource;
import robot.imperium.resources.ResourceFactory;
import robot.imperium.resources.ResourceState;
import robot.io.serial.SerialInterface;


/**
 * An Imperium hardware configuration that specific to an AVR processor
 * 
 * @author Mitchell
 *
 */
public class AVRImperiumDevice extends ImperiumDevice {
	/**
	 * Represents an IO port on an AVR chip
	 * 
	 * @author Mitchell
	 *
	 */
	public enum IOPort{
		PORTA(0, "A"),
		PORTB(1, "B"),
		PORTC(2, "C"),
		PORTD(3, "D"),
		PORTE(4, "E"),
		PORTF(5, "F");
		
		private final int num;
		private final String label;
		private IOPort(int num, String label){
			this.num = num;
			this.label = label;
		}
		public static IOPort toPort(String portName) {
			if("A".equals(portName))
				return PORTA;
			else if("B".equals(portName))
				return PORTB;
			else if("C".equals(portName))
				return PORTC;
			else if("D".equals(portName))
				return PORTD;
			else if("E".equals(portName))
				return PORTE;
			else if("F".equals(portName))
				return PORTF;
			throw new RobotInitializationException("Unknown port "+portName);
		}
	}
	
	
	/**
	 * Represents a bit in an IO port on an AVR chip
	 * 
	 * @author Mitchell
	 *
	 */
	public enum IOPortBit{
		Bit0(0),
		Bit1(1),
		Bit2(2),
		Bit3(3),
		Bit4(4),
		Bit5(5),
		Bit6(6),
		Bit7(7);
		
		private final int num;
		private IOPortBit(int num){
			this.num = num;
		}
		public static IOPortBit toPortBit(String portBitName) {
			if("0".equals(portBitName))
				return Bit0;
			else if("1".equals(portBitName))
				return Bit1;
			else if("2".equals(portBitName))
				return Bit2;
			else if("3".equals(portBitName))
				return Bit3;
			else if("4".equals(portBitName))
				return Bit4;
			else if("5".equals(portBitName))
				return Bit5;
			else if("6".equals(portBitName))
				return Bit6;
			else if("7".equals(portBitName))
				return Bit7;
			throw new RobotInitializationException("Unknown port bit "+portBitName);
		}
	}


	public AVRImperiumDevice(SerialInterface serialPort, int maxUpdateRate) {
		super(serialPort, maxUpdateRate);
	}
	
	protected void addAVRPin(IOPort port, IOPortBit bit){
		addResource(ResourceFactory.single(pinName(port, bit), pinLocation(port, bit))
				.states(ResourceState.DigitalInput, ResourceState.DigitalOutput, ResourceState.DigitalInputPullup, ResourceState.Disconnected)
				.build());
	}
	protected void addAVRExternalInterrupt(int id, IOPort port, IOPortBit bit){
		addResource(ResourceFactory.single("INT"+id, id).states(ResourceState.Interrupt)
				.dependancy(getResource(pinName(port, bit)), ResourceState.DigitalInput)
				.build());
	}
	protected void addAVRAnalogInput(int id, IOPort port, IOPortBit bit){
		addResource(ResourceFactory.single("ADC"+id, id)
				.states(ResourceState.AnalogInput)
				.dependancy(getResource(pinName(port, bit)), ResourceState.DigitalInput)
				.dependancy(getADCResource(), ResourceState.Enabled)
				.build());
	}
	protected DeviceResource getADCResource(){
		DeviceResource resource = getResource("ADC");
		if(resource==null)
			return addResource(ResourceFactory.multi("ADC", 0)
					.states(ResourceState.Enabled, ResourceState.Disabled)
					.build());
		return resource;
	}
	protected void addAVRSerialPort(int id, IOPort txPort, IOPortBit txBit, IOPort rxPort, IOPortBit rxBit){
		addResource(ResourceFactory.single("Serial"+id, id).states(ResourceState.SerialPort)
				.dependancy(getResource(pinName(txPort, txBit)), ResourceState.Disconnected)
				.dependancy(getResource(pinName(rxPort, rxBit)), ResourceState.Disconnected)
				.build());
	}
	
	
	private static int pinLocation(IOPort port, IOPortBit bit){
		return port.num*8 + bit.num;
	}
	public static String pinName(IOPort port, IOPortBit bit){
		return "P"+port.label+bit.num;
	}


}
