package robot.io.computerdevices;

import robot.boostrap.partial.*;
import robot.io.computerdevices.joystick.*;
import robot.io.computerdevices.rxtx.*;
import robot.io.joystick.*;
import robot.io.serial.*;

public class ComputerDevicesPartialModule extends AbstractPartialModule {

	@Override
	protected void configure() {
		bind(Joystick.class, ComputerJoystick.class);
		bind(SerialInterface.class, RxTxComputerSerialPort.class);
	}

}
