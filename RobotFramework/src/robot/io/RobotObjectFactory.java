package robot.io;

import robot.error.RobotInitializationException;
import robot.io.analog.AnalogVoltageInput;
import robot.io.binary.BinaryInput;
import robot.io.binary.BinaryOutput;
import robot.io.counter.Counter;
import robot.io.dutycycle.DutyCycleInput;
import robot.io.encoder.Encoder;
import robot.io.frequency.FrequencyInput;
import robot.io.joystick.Joystick;
import robot.io.ppm.PPMReader;
import robot.io.pwm.PWMOutput;
import robot.io.pwmms.MSPWMOutput;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 * 
 * An object that can be used to get RobotObjects from a String location
 *
 */
public abstract class RobotObjectFactory {
	public AnalogVoltageInput newAnalogVoltageInput(String location){
		throw new RobotInitializationException("Analog Voltage Input not supported");
	}
	
	public BinaryInput newBinaryInput(String location){
		throw new RobotInitializationException("Binary Input not supported");
	}
	
	public BinaryOutput newBinaryOutput(String location){
		throw new RobotInitializationException("Binary Output not supported");
	}
	
	public Counter newCounter(String location){
		throw new RobotInitializationException("Counter not supported");
	}
	
	public DutyCycleInput newDutyCycle(String location){
		throw new RobotInitializationException("Duty Cycle not supported");
	}

	public Encoder newEncoder(String aLocation, String bLocation){
		throw new RobotInitializationException("Encoder not supported");
	}

	public FrequencyInput newFrequencyInput(String location){
		throw new RobotInitializationException("Frequency not supported");
	}

	public PPMReader newPPMReader(String location, int numChannels){
		throw new RobotInitializationException("PPMReader not supported");
	}

	public PWMOutput newPWMOutput(String location){
		throw new RobotInitializationException("PWM Output not supported");
	}

	public MSPWMOutput newMSPWM(String location){
		throw new RobotInitializationException("MS PWM not supported");
	}

	public SerialInterface newSerialInterface(String location, int baud){
		throw new RobotInitializationException("Serial Interface not supported");
	}

	public Joystick newJoystick(String location){
		throw new RobotInitializationException("Joystick not supported");
	}

	public Joystick newAccelerometer(String location){
		throw new RobotInitializationException("Accelerometer not supported");
	}
}
