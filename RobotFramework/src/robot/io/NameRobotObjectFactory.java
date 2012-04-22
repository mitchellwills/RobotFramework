package robot.io;

import robot.Robot;
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
 * A Robot Object Factory that gets factories for generating Robot Objects from their 
 *
 */
public class NameRobotObjectFactory extends RobotObjectFactory{
	/**
	 * the character used to separate the name of an object from the factory name
	 */
	public static final char SEPARATION_CHAR = '/';
	
	private final Robot robot;
	public NameRobotObjectFactory(Robot robot){
		this.robot = robot;
	}

	private RobotObjectFactory getFactory(String name){
		RobotObject object = robot.getObject(name);
		if(object instanceof FactoryObject)
			return ((FactoryObject)object).getFactory();
		throw new RobotInitializationException(name+" is not a robot factory");
	}
	private RobotObjectFactory getFactoryFromLocation(String location){
		return getFactory(getFactoryName(location));
	}
	private String getFactoryName(String location){
		int index = location.indexOf(SEPARATION_CHAR);
		if(index==-1)
			throw new RobotInitializationException("Object Location must have a seperator");
		return location.substring(0, index);
	}
	private String getSubLocation(String location){
		int index = location.indexOf(SEPARATION_CHAR);
		if(index==-1)
			throw new RobotInitializationException("Object Location must have a seperator");
		return location.substring(index+1);
	}

	@Override
	public AnalogVoltageInput newAnalogVoltageInput(String location){
		return getFactoryFromLocation(location).newAnalogVoltageInput(getSubLocation(location));
	}

	@Override
	public BinaryInput newBinaryInput(String location){
		return getFactoryFromLocation(location).newBinaryInput(getSubLocation(location));
	}

	@Override
	public BinaryOutput newBinaryOutput(String location){
		return getFactoryFromLocation(location).newBinaryOutput(getSubLocation(location));
	}

	@Override
	public Counter newCounter(String location){
		return getFactoryFromLocation(location).newCounter(getSubLocation(location));
	}

	@Override
	public DutyCycleInput newDutyCycle(String location){
		return getFactoryFromLocation(location).newDutyCycle(getSubLocation(location));
	}

	@Override
	public Encoder newEncoder(String aLocation, String bLocation){
		if(!getFactoryName(aLocation).equals(getFactoryName(bLocation)))
			throw new RobotInitializationException("A Location and B Location must share the same factory");
		return getFactoryFromLocation(aLocation).newEncoder(getSubLocation(aLocation), getSubLocation(bLocation));
	}

	@Override
	public FrequencyInput newFrequencyInput(String location){
		return getFactoryFromLocation(location).newFrequencyInput(getSubLocation(location));
	}

	@Override
	public PPMReader newPPMReader(String location, int numChannels){
		return getFactoryFromLocation(location).newPPMReader(getSubLocation(location), numChannels);
	}

	@Override
	public PWMOutput newPWMOutput(String location){
		return getFactoryFromLocation(location).newPWMOutput(getSubLocation(location));
	}

	@Override
	public MSPWMOutput newMSPWM(String location){
		return getFactoryFromLocation(location).newMSPWM(getSubLocation(location));
	}

	@Override
	public SerialInterface newSerialInterface(String location, int baud){
		return getFactoryFromLocation(location).newSerialInterface(getSubLocation(location), baud);
	}

	@Override
	public Joystick newJoystick(String location){
		return getFactoryFromLocation(location).newJoystick(getSubLocation(location));
	}

	@Override
	public Accelerometer newAccelerometer(String location){
		return getFactoryFromLocation(location).newAccelerometer(getSubLocation(location));
	}

	@Override
	public SpeedController newSpeedController(String location){
		return getFactoryFromLocation(location).newSpeedController(getSubLocation(location));
	}
}
