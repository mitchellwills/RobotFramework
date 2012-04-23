package robot.simple;

import robot.Robot;
import robot.imperium.ImperiumDevice;
import robot.imperium.hardware.Teensy2PP;
import robot.io.computerdevices.Computer;


public abstract class SimpleRobot extends Robot {

	private final ImperiumDevice device;
	public SimpleRobot(String serialPort) {
		super(null);
		putObject("computer", Computer.get());
		putObject("device", device = new ImperiumDevice(this, "computer/"+serialPort, Teensy2PP.get(), 500));
		setFactory(device.getFactory());
	}

}
