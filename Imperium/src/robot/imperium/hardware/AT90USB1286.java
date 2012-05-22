package robot.imperium.hardware;

import robot.imperium.DeviceFeatureCapability;
import robot.io.serial.SerialInterface;


public class AT90USB1286 extends AVRImperiumDevice {

	public AT90USB1286(SerialInterface serialPort, int maxUpdateRate) {
		super(serialPort, maxUpdateRate);
		
		
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
		addAVRPin(IOPort.PORTD, IOPortBit.Bit3, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT3", IOPort.PORTD, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit2, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT2", IOPort.PORTD, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit1, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT1", IOPort.PORTD, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTD, IOPortBit.Bit0, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT0", IOPort.PORTD, IOPortBit.Bit0);
		
		addAVRPin(IOPort.PORTE, IOPortBit.Bit7, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT7", IOPort.PORTE, IOPortBit.Bit7);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit6, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT6", IOPort.PORTE, IOPortBit.Bit6);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit5, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT5", IOPort.PORTE, IOPortBit.Bit5);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit4, DeviceFeatureCapability.ExternalInterrupt);
		addExtraFeatureName("INT4", IOPort.PORTE, IOPortBit.Bit4);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit3);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit2);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit1);
		addAVRPin(IOPort.PORTE, IOPortBit.Bit0);
		
		addAVRPin(IOPort.PORTF, IOPortBit.Bit7, DeviceFeatureCapability.AnalogVoltageInput);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit6, DeviceFeatureCapability.AnalogVoltageInput);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit5, DeviceFeatureCapability.AnalogVoltageInput);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit4, DeviceFeatureCapability.AnalogVoltageInput);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit3, DeviceFeatureCapability.AnalogVoltageInput);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit2, DeviceFeatureCapability.AnalogVoltageInput);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit1, DeviceFeatureCapability.AnalogVoltageInput);
		addAVRPin(IOPort.PORTF, IOPortBit.Bit0, DeviceFeatureCapability.AnalogVoltageInput);
		
		
	}
 
	
}