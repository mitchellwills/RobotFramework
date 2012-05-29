package robot.io.computerdevices;

import robot.io.*;
import robot.io.joystick.*;
import robot.io.serial.*;

import com.google.inject.assistedinject.*;

public interface ComputerDevicesFactory {
	public Joystick createJoystick(@Assisted(RobotObject.PARAM_LOCATION) int location);
	public SerialInterface createSerialInterface(@Assisted(RobotObject.PARAM_LOCATION) String location, @Assisted(SerialInterface.PARAM_BAUD) int baud);
}