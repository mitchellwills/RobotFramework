package robot.io.computerdevices;

import robot.io.computerdevices.joystick.*;
import robot.io.computerdevices.rxtx.*;
import robot.io.joystick.*;
import robot.io.serial.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

public class ComputerDevicesModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder()
		.implement(Joystick.class, ComputerJoystick.class)
		.implement(SerialInterface.class, RxTxComputerSerialPort.class)
		.build(ComputerDevicesFactory.class));
	}

}
