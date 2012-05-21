package robot.imperium.hardware;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import robot.imperium.ImperiumDevice;
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
	@SuppressWarnings("javadoc")
	public enum IOPort{
		PORTA(0, "A"),
		PORTB(1, "B"),
		PORTC(2, "C"),
		PORTD(3, "D"),
		PORTE(4, "E"),
		PORTF(5, "F");
		
		private int num;
		private String label;
		private IOPort(int num, String label){
			this.num = num;
			this.label = label;
		}
		public int getNum(){
			return num;
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
			throw new UnknownImperiumObjectException("Port "+portName);
		}
	}
	
	
	/**
	 * Represents a bit in an IO port on an AVR chip
	 * 
	 * @author Mitchell
	 *
	 */
	@SuppressWarnings("javadoc")	
	public enum IOPortBit{
		Bit0(0),
		Bit1(1),
		Bit2(2),
		Bit3(3),
		Bit4(4),
		Bit5(5),
		Bit6(6),
		Bit7(7);
		
		private int num;
		private IOPortBit(int num){
			this.num = num;
		}
		public int getNum(){
			return num;
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
			throw new UnknownImperiumObjectException("Port bit "+portBitName);
		}
	}


	public AVRImperiumDevice(SerialInterface serialPort, int maxUpdateRate) {
		super(serialPort, maxUpdateRate);
	}


	private static final Pattern simpleAVRPinPattern = Pattern.compile("P([A-F])([0-7])");
	@Override
	public byte getPin(String location) {
		Matcher matcher = simpleAVRPinPattern.matcher(location);
		if(matcher.find()){
			return getPin(IOPort.toPort(matcher.group(1)), IOPortBit.toPortBit(matcher.group(2)));
		}
		throw new UnknownImperiumObjectException(location);
	}
	
	public byte getPin(IOPort port, IOPortBit portBit) {
		return (byte) (port.getNum()*8 + portBit.getNum());
	}
	
	


}
