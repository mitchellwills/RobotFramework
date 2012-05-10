package robot.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import robot.error.RobotInitializationException;
import robot.io.accelerometer.Accelerometer;
import robot.io.accelerometer.VirtualAccelerometer;
import robot.io.analog.AnalogVoltageInput;
import robot.io.analog.VirtualAnalogVoltageInput;
import robot.io.binary.BinaryInput;
import robot.io.binary.BinaryOutput;
import robot.io.binary.VirtualBinaryInput;
import robot.io.binary.VirtualBinaryOutput;
import robot.io.counter.Counter;
import robot.io.counter.VirtualCounter;
import robot.io.dutycycle.DutyCycleInput;
import robot.io.dutycycle.VirtualDutyCycleInput;
import robot.io.encoder.Encoder;
import robot.io.encoder.VirtualEncoder;
import robot.io.frequency.FrequencyInput;
import robot.io.frequency.VirtualFrequencyInput;
import robot.io.joystick.Joystick;
import robot.io.joystick.VirtualJoystick;
import robot.io.ppm.PPMReader;
import robot.io.ppm.VirtualPPMReader;
import robot.io.pwm.PWMOutput;
import robot.io.pwm.VirtualPWMOutput;
import robot.io.pwmms.MSPWMOutput;
import robot.io.pwmms.VirtualMSPWMOutput;
import robot.io.serial.SerialInterface;
import robot.io.serial.VirtualSerialInterface;
import robot.io.speedcontroller.SpeedController;
import robot.io.speedcontroller.VirtualSpeedController;

/**
 * @author Mitchell
 * 
 * A Robot Object Factory that returns virtual objects
 *
 */
public class VirtualRobotObjectFactory extends RobotObjectFactory implements FactoryObject{

	@Override
	public AnalogVoltageInput getAnalogVoltageInput(String location){
		try{
			double maxVoltage = Double.parseDouble(location);
			return new VirtualAnalogVoltageInput(maxVoltage);
		} catch(NumberFormatException e){
			throw new RobotInitializationException("The location of a vitual analog voltage input must be a number representing its maximum voltage");
		}
	}

	@Override
	public BinaryInput getBinaryInput(String location){
		return new VirtualBinaryInput();
	}

	@Override
	public BinaryOutput getBinaryOutput(String location){
		return new VirtualBinaryOutput();
	}

	@Override
	public Counter getCounter(String location){
		return new VirtualCounter();
	}

	@Override
	public DutyCycleInput getDutyCycle(String location){
		return new VirtualDutyCycleInput();
	}

	@Override
	public Encoder getEncoder(String aLocation, String bLocation){
		return new VirtualEncoder();
	}

	@Override
	public FrequencyInput getFrequencyInput(String location){
		return new VirtualFrequencyInput();
	}

	@Override
	public PPMReader getPPMReader(String location, int numChannels){
		return new VirtualPPMReader(numChannels);
	}

	@Override
	public PWMOutput getPWMOutput(String location){
		try{
			double maxVoltage = Double.parseDouble(location);
			return new VirtualPWMOutput(maxVoltage);
		} catch(NumberFormatException e){
			throw new RobotInitializationException("The location of a vitual PWM output must be a number representing the frequency of the output");
		}
	}

	@Override
	public MSPWMOutput getMSPWM(String location){
		return new VirtualMSPWMOutput();
	}

	@Override
	public SerialInterface getSerialInterface(String location, int baud){
		return new VirtualSerialInterface(baud);
	}

	@Override
	public Joystick getJoystick(String location){
		Matcher matcher = Pattern.compile("(.+),(.+),(.+),(.+)").matcher(location);
		if(matcher.find()){
			String name = matcher.group(1);
			int numButtons = Integer.parseInt(matcher.group(2));
			int numAxes = Integer.parseInt(matcher.group(3));
			int numDirectionals = Integer.parseInt(matcher.group(4));
			return new VirtualJoystick(name, numButtons, numAxes, numDirectionals);
		}
		throw new RobotInitializationException("The location of a vitual Joystick must be \"name,numButtons,numAxes,numDirectionals\"");
	}

	@Override
	public Accelerometer getAccelerometer(String location){
		int numAxes = Integer.parseInt(location);
		return new VirtualAccelerometer(numAxes);
	}

	@Override
	public SpeedController getSpeedController(String location){
		return new VirtualSpeedController();
	}

	@Override
	public RobotObjectFactory getFactory() {
		return this;
	}
}
