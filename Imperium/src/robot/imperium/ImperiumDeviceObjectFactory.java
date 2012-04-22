package robot.imperium;

import robot.imperium.objects.ImperiumAnalogVoltageInput;
import robot.imperium.objects.ImperiumDigitalInput;
import robot.imperium.objects.ImperiumDigitalOutput;
import robot.imperium.objects.ImperiumDutyCycle;
import robot.imperium.objects.ImperiumFrequency;
import robot.imperium.objects.ImperiumMSPWMOutput;
import robot.imperium.objects.ImperiumPPMReader;
import robot.imperium.objects.ImperiumPulseCounter;
import robot.imperium.objects.ImperiumQuadEncoder;
import robot.imperium.objects.ImperiumSerialPort;
import robot.io.RobotObjectFactory;
import robot.io.analog.AnalogVoltageInput;
import robot.io.binary.BinaryInput;
import robot.io.binary.BinaryOutput;
import robot.io.counter.Counter;
import robot.io.dutycycle.DutyCycleInput;
import robot.io.encoder.Encoder;
import robot.io.frequency.FrequencyInput;
import robot.io.ppm.PPMReader;
import robot.io.pwmms.MSPWMOutput;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 * 
 * A RobotObjectFactory that returns Objects from a specific imperium device
 *
 */
public class ImperiumDeviceObjectFactory extends RobotObjectFactory {
	private final ImperiumDevice device;
	/**
	 * Create a new factory
	 * @param device the device the objects will be from
	 */
	public ImperiumDeviceObjectFactory(ImperiumDevice device){
		this.device = device;
	}
	
	@Override
	public AnalogVoltageInput getAnalogVoltageInput(String location){
		return new ImperiumAnalogVoltageInput(device, location);
	}

	@Override
	public BinaryInput getBinaryInput(String location){
		return new ImperiumDigitalInput(device, location);
	}

	@Override
	public BinaryOutput getBinaryOutput(String location){
		return new ImperiumDigitalOutput(device, location);
	}

	@Override
	public Counter getCounter(String location){
		return new ImperiumPulseCounter(device, location);
	}

	@Override
	public DutyCycleInput getDutyCycle(String location){
		return new ImperiumDutyCycle(device, location);
	}

	@Override
	public Encoder getEncoder(String aLocation, String bLocation){
		return new ImperiumQuadEncoder(device, aLocation, bLocation);
	}

	@Override
	public FrequencyInput getFrequencyInput(String location){
		return new ImperiumFrequency(device, location);
	}

	@Override
	public PPMReader getPPMReader(String location, int numChannels){
		return new ImperiumPPMReader(device, location, numChannels);
	}

	@Override
	public MSPWMOutput getMSPWM(String location){
		return new ImperiumMSPWMOutput(device, location);
	}

	@Override
	public SerialInterface getSerialInterface(String location, int baud){
		return new ImperiumSerialPort(device, location, baud);
	}
}
