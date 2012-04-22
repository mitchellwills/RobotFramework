package robot.io;

import robot.error.RobotInitializationException;
import robot.io.accelerometer.Accelerometer;
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
import robot.io.speedcontroller.SpeedController;

/**
 * @author Mitchell
 * 
 * An object that can be used to get RobotObjects from a String location
 *
 */
public abstract class RobotObjectFactory {
	public AnalogVoltageInput getAnalogVoltageInput(String location){
		throw new RobotInitializationException("Analog Voltage Input not supported");
	}
	
	public BinaryInput getBinaryInput(String location){
		throw new RobotInitializationException("Binary Input not supported");
	}
	
	public BinaryOutput getBinaryOutput(String location){
		throw new RobotInitializationException("Binary Output not supported");
	}
	
	public Counter getCounter(String location){
		throw new RobotInitializationException("Counter not supported");
	}
	
	public DutyCycleInput getDutyCycle(String location){
		throw new RobotInitializationException("Duty Cycle not supported");
	}

	public Encoder getEncoder(String aLocation, String bLocation){
		throw new RobotInitializationException("Encoder not supported");
	}

	public FrequencyInput getFrequencyInput(String location){
		throw new RobotInitializationException("Frequency not supported");
	}

	public PPMReader getPPMReader(String location, int numChannels){
		throw new RobotInitializationException("PPMReader not supported");
	}

	public PWMOutput getPWMOutput(String location){
		throw new RobotInitializationException("PWM Output not supported");
	}

	public MSPWMOutput getMSPWM(String location){
		throw new RobotInitializationException("MS PWM not supported");
	}

	public SerialInterface getSerialInterface(String location, int baud){
		throw new RobotInitializationException("Serial Interface not supported");
	}

	public Joystick getJoystick(String location){
		throw new RobotInitializationException("Joystick not supported");
	}

	public Accelerometer getAccelerometer(String location){
		throw new RobotInitializationException("Accelerometer not supported");
	}

	public SpeedController getSpeedController(String location){
		throw new RobotInitializationException("Speed Controller not supported");
	}
}
