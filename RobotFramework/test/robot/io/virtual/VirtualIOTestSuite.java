package robot.io.virtual;

import org.junit.runner.*;
import org.junit.runners.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	VirtualAccelerometerTest.class,
	VirtualAnalogTest.class,
	VirtualBinaryInputTest.class,
	VirtualBinaryOutputTest.class,
	VirtualCompassTest.class,
	VirtualCounterTest.class,
	VirtualDutyCycleTest.class,
	VirtualEncoderTest.class,
	VirtualFrequencyTest.class,
	VirtualGyroTest.class,
	VirtualJoystickAxisTest.class,
	VirtualJoystickButtonTest.class,
	VirtualJoystickDirectionalTest.class,
	VirtualJoystickTest.class,
	VirtualMSPWMOutputTest.class,
	VirtualPPMReaderTest.class,
	VirtualPWMTest.class,
	VirtualSerialInterfaceTest.class,
	VirtualServoTest.class,
	VirtualSpeedControllerTest.class
})
public class VirtualIOTestSuite {
	//
}
