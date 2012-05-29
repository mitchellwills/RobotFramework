package robot.io.virtual;

import robot.boostrap.partial.*;
import robot.io.accelerometer.*;
import robot.io.analog.*;
import robot.io.binary.*;
import robot.io.compass.*;
import robot.io.counter.*;
import robot.io.dutycycle.*;
import robot.io.encoder.*;
import robot.io.frequency.*;
import robot.io.gyro.*;
import robot.io.joystick.*;
import robot.io.ppm.*;
import robot.io.pwm.*;
import robot.io.pwmms.*;
import robot.io.serial.*;
import robot.io.servo.*;
import robot.io.speedcontroller.*;

public class VirtualObjectPartialModule extends AbstractPartialModule {

	@Override
	protected void configure() {
		bind(Accelerometer.class, VirtualAccelerometer.class);
		bind(AnalogVoltageInput.class, VirtualAnalogVoltageInput.class);
		bind(BinaryInput.class, VirtualBinaryInput.class);
		bind(BinaryOutput.class, VirtualBinaryOutput.class);
		bind(Compass.class, VirtualCompass.class);
		bind(Counter.class, VirtualCounter.class);
		bind(DutyCycleInput.class, VirtualDutyCycleInput.class);
		bind(Encoder.class, VirtualEncoder.class);
		bind(FrequencyInput.class, VirtualFrequencyInput.class);
		bind(Gyroscope.class, VirtualGyro.class);
		bind(JoystickAxis.class, VirtualJoystickAxis.class);
		bind(JoystickButton.class, VirtualJoystickButton.class);
		bind(JoystickDirectional.class, VirtualJoystickDirectional.class);
		bind(Joystick.class, VirtualJoystick.class);
		bind(MSPWMOutput.class, VirtualMSPWMOutput.class);
		bind(PPMReader.class, VirtualPPMReader.class);
		bind(PWMOutput.class, VirtualPWMOutput.class);
		bind(SerialInterface.class, VirtualSerialInterface.class);
		bind(Servo.class, VirtualServo.class);
		bind(SpeedController.class, VirtualSpeedController.class);
	}

}
