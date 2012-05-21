package robot.imperium.hardware;

import robot.io.serial.SerialInterface;


public class AT90USB1286 extends AVRImperiumDevice {

	public AT90USB1286(SerialInterface serialPort, int maxUpdateRate) {
		super(serialPort, maxUpdateRate);
	}
 
	
}