package robot.simple;

import robot.Robot;
import robot.imperium.ImperiumDevice;
import robot.imperium.hardware.Teensy2PP;
import robot.io.computerdevices.Computer;
import robot.io.serial.SerialInterface;


/**
 * @author Mitchell
 * 
 * A simple robot that connects to an Imperium Device to use for all its IO
 *
 */
public abstract class SimpleRobot extends Robot {

	private final ImperiumDevice device;
	/**
	 * @param serialPort the serial port to connect using
	 */
	public SimpleRobot(SerialInterface serialPort) {
		super(null);
		putObject("host", Computer.get());
		putObject("device", device = new ImperiumDevice(serialPort, Teensy2PP.get(), 500));
		setFactory(device.getFactory());
	}

}
