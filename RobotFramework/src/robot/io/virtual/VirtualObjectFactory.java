package robot.io.virtual;

import robot.io.accelerometer.*;
import robot.io.analog.*;
import robot.io.gyro.*;
import robot.io.joystick.*;
import robot.io.ppm.*;
import robot.io.pwm.*;
import robot.io.serial.*;
import robot.io.servo.*;

import com.google.inject.assistedinject.*;

public interface VirtualObjectFactory {
	public VirtualAccelerometer createAccelerometer(@Assisted(Accelerometer.PARAM_NUM_AXES) int numAxes);
	public VirtualAnalogVoltageInput createAnalogVoltageInput(@Assisted(AnalogVoltageInput.PARAM_MAX_VOLTAGE) final double maxVoltage);
	public VirtualBinaryInput createBinaryInput();
	public VirtualBinaryOutput createBinaryOutput();
	public VirtualCompass createCompass();
	public VirtualCounter createCounter();
	public VirtualDutyCycleInput createDutyCycleInput();
	public VirtualEncoder createEncoder();
	public VirtualFrequencyInput createFrequencyInput();
	public VirtualGyro createGyro(@Assisted(Gyroscope.PARAM_NUM_AXES) int numAxes);
	public VirtualJoystick createJoystick(
			@Assisted(Joystick.PARAM_NUM_BUTTONS) int numButtons,
			@Assisted(Joystick.PARAM_NUM_AXES) int numAxes,
			@Assisted(Joystick.PARAM_NUM_DIRECTIONALS) int numDirectionals);
	public VirtualJoystickAxis createJoystickAxis(@Assisted(JoystickAxis.PARAM_ID) int id);
	public VirtualJoystickButton createJoystickButton(@Assisted(JoystickButton.PARAM_ID) int id);
	public VirtualJoystickDirectional createJoystickDirectional(@Assisted(JoystickDirectional.PARAM_ID) int id);
	public VirtualMSPWMOutput createMSPWMOutput();
	public VirtualPPMReader createPPMReader(@Assisted(PPMReader.PARAM_NUM_CHANNELS) int numChannels);
	public VirtualPWMOutput createPWMOutput(@Assisted(PWMOutput.PARAM_FREQUENCY) double frequency);
	public VirtualSerialInterface createSerialInterface(@Assisted(SerialInterface.PARAM_BAUD) int baud);
	public VirtualServo createServo(@Assisted(Servo.PARAM_MIN_ANGLE) double minAngle, @Assisted(Servo.PARAM_MAX_ANGLE) double maxAngle);
	public VirtualSpeedController createSpeedController();
}