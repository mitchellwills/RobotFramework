package robot.imperium.hardware;

import robot.imperium.*;
import robot.io.serial.*;
import robot.thread.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;


public class AT90USB1286 extends AVRImperiumDevice {

	@Inject public AT90USB1286(RobotThreadFactory threadFactory,
			@Assisted(ImperiumDevice.PARAM_SERIAL_INTERFACE) SerialInterface serialPort,
			@Assisted(ImperiumDevice.PARAM_MAX_UPDATE_RATE) int maxUpdateRate) {
		super(threadFactory, serialPort, maxUpdateRate);

		
		addAVRPin(IOPort.PORTA, IOPortBit.Bit7);
		addAVRPin(IOPort.PORTA, IOPortBit.Bit6);
		addAVRPin(IOPort.PORTA, IOPortBit.Bit5);
		addAVRPin(IOPort.PORTA, IOPortBit.Bit4);
		addAVRPin(IOPort.PORTA, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTA, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTA, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTA, IOPortBit.Bit0);
		
		addAVRPin(IOPort.PORTB, IOPortBit.Bit7);
		addAVRPin(IOPort.PORTB, IOPortBit.Bit6);
		addAVRPin(IOPort.PORTB, IOPortBit.Bit5);
		addAVRPin(IOPort.PORTB, IOPortBit.Bit4);
		addAVRPin(IOPort.PORTB, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTB, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTB, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTB, IOPortBit.Bit0);
		
		addAVRPin(IOPort.PORTC, IOPortBit.Bit7);
		addAVRPin(IOPort.PORTC, IOPortBit.Bit6);
		addAVRPin(IOPort.PORTC, IOPortBit.Bit5);
		addAVRPin(IOPort.PORTC, IOPortBit.Bit4);
		addAVRPin(IOPort.PORTC, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTC, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTC, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTC, IOPortBit.Bit0);
		
		addAVRPin(IOPort.PORTD, IOPortBit.Bit7);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit6);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit5);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit4);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit0);
		
		addAVRPin(IOPort.PORTE, IOPortBit.Bit7);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit6);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit5);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit4);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit0);
		
		addAVRPin(IOPort.PORTF, IOPortBit.Bit7);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit6);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit5);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit4);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit0);
		
		
		addAVRExternalInterrupt(7, IOPort.PORTE, IOPortBit.Bit7);
		addAVRExternalInterrupt(6, IOPort.PORTE, IOPortBit.Bit6);
		addAVRExternalInterrupt(5, IOPort.PORTE, IOPortBit.Bit5);
		addAVRExternalInterrupt(4, IOPort.PORTE, IOPortBit.Bit4);
		addAVRExternalInterrupt(3, IOPort.PORTD, IOPortBit.Bit3);
		addAVRExternalInterrupt(2, IOPort.PORTD, IOPortBit.Bit2);
		addAVRExternalInterrupt(1, IOPort.PORTD, IOPortBit.Bit1);
		addAVRExternalInterrupt(0, IOPort.PORTD, IOPortBit.Bit0);
		addAVRPinChangeInterrupt(0);
		
		addAVRAnalogInput(7, IOPort.PORTF, IOPortBit.Bit7);
		addAVRAnalogInput(6, IOPort.PORTF, IOPortBit.Bit6);
		addAVRAnalogInput(5, IOPort.PORTF, IOPortBit.Bit5);
		addAVRAnalogInput(4, IOPort.PORTF, IOPortBit.Bit4);
		addAVRAnalogInput(3, IOPort.PORTF, IOPortBit.Bit3);
		addAVRAnalogInput(2, IOPort.PORTF, IOPortBit.Bit2);
		addAVRAnalogInput(1, IOPort.PORTF, IOPortBit.Bit1);
		addAVRAnalogInput(0, IOPort.PORTF, IOPortBit.Bit0);
		
		addAVRSerialPort(0, IOPort.PORTD, IOPortBit.Bit3, IOPort.PORTD, IOPortBit.Bit2);
	}
 
	
}