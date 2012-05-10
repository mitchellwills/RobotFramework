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
	
	private RobotObjectFactory getFactory(String name){
		if(name==null)
			throw new RobotInitializationException("Object Location must have a seperator");
		RobotObject object = Robot.getInstance().getObject(name);
		if(object instanceof FactoryObject)
			return ((FactoryObject)object).getFactory();
		throw new RobotInitializationException(name+" is not a robot factory");
	}
	private String getFactoryName(String location){
		int index = location.indexOf(SEPARATION_CHAR);
		if(index==-1)
			return null;
		return location.substring(0, index);
	}
	private String getSubLocation(String location){
		int index = location.indexOf(SEPARATION_CHAR);
		if(index==-1)
			throw new RobotInitializationException("Object Location must have a seperator");
		return location.substring(index+1);
	}

	@Override
	public AnalogVoltageInput getAnalogVoltageInput(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof AnalogVoltageInput))
				throw new RobotInitializationException(location+" is not an AnalogVoltageInput");
			return (AnalogVoltageInput) object;
		}
		return getFactory(factoryName).getAnalogVoltageInput(getSubLocation(location));
	}

	@Override
	public BinaryInput getBinaryInput(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof BinaryInput))
				throw new RobotInitializationException(location+" is not a BinaryInput");
			return (BinaryInput) object;
		}
		return getFactory(factoryName).getBinaryInput(getSubLocation(location));
	}

	@Override
	public BinaryOutput getBinaryOutput(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof BinaryOutput))
				throw new RobotInitializationException(location+" is not a BinaryOutput");
			return (BinaryOutput) object;
		}
		return getFactory(factoryName).getBinaryOutput(getSubLocation(location));
	}

	@Override
	public Counter getCounter(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof Counter))
				throw new RobotInitializationException(location+" is not a Counter");
			return (Counter) object;
		}
		return getFactory(factoryName).getCounter(getSubLocation(location));
	}

	@Override
	public DutyCycleInput getDutyCycle(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof DutyCycleInput))
				throw new RobotInitializationException(location+" is not a DutyCycleInput");
			return (DutyCycleInput) object;
		}
		return getFactory(factoryName).getDutyCycle(getSubLocation(location));
	}

	@Override
	public Encoder getEncoder(String aLocation, String bLocation){
		if(!getFactoryName(aLocation).equals(getFactoryName(bLocation)))
			throw new RobotInitializationException("A Location and B Location must share the same factory");
		
		String factoryName = getFactoryName(aLocation);
		if(factoryName==null)
			throw new RobotInitializationException("Cannot get an encoder from the location of two inputs");
		
		return getFactory(factoryName).getEncoder(getSubLocation(aLocation), getSubLocation(bLocation));
	}

	@Override
	public FrequencyInput getFrequencyInput(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof FrequencyInput))
				throw new RobotInitializationException(location+" is not a FrequencyInput");
			return (FrequencyInput) object;
		}
		return getFactory(factoryName).getFrequencyInput(getSubLocation(location));
	}

	@Override
	public PPMReader getPPMReader(String location, int numChannels){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof PPMReader))
				throw new RobotInitializationException(location+" is not a PPMReader");
			if(((PPMReader)object).getChannelCount()!=numChannels)
				throw new RobotInitializationException("Channel count of PPMReader '"+location+"' does not match "+numChannels);
			return (PPMReader) object;
		}
		return getFactory(factoryName).getPPMReader(getSubLocation(location), numChannels);
	}

	@Override
	public PWMOutput getPWMOutput(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof PWMOutput))
				throw new RobotInitializationException(location+" is not a PWMOutput");
			return (PWMOutput) object;
		}
		return getFactory(factoryName).getPWMOutput(getSubLocation(location));
	}

	@Override
	public MSPWMOutput getMSPWM(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof MSPWMOutput))
				throw new RobotInitializationException(location+" is not a MSPWMOutput");
			return (MSPWMOutput) object;
		}
		return getFactory(factoryName).getMSPWM(getSubLocation(location));
	}

	@Override
	public SerialInterface getSerialInterface(String location, int baud){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof SerialInterface))
				throw new RobotInitializationException(location+" is not a SerialInterface");
			if(((SerialInterface)object).getBaudRate()!=baud)
				throw new RobotInitializationException("Baud rate of SerialInterface '"+location+"' does not match "+baud);
			return (SerialInterface) object;
		}
		return getFactory(factoryName).getSerialInterface(getSubLocation(location), baud);
	}

	@Override
	public Joystick getJoystick(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof Joystick))
				throw new RobotInitializationException(location+" is not a Joystick");
			return (Joystick) object;
		}
		return getFactory(factoryName).getJoystick(getSubLocation(location));
	}

	@Override
	public Accelerometer getAccelerometer(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof Accelerometer))
				throw new RobotInitializationException(location+" is not an Accelerometer");
			return (Accelerometer) object;
		}
		return getFactory(factoryName).getAccelerometer(getSubLocation(location));
	}

	@Override
	public SpeedController getSpeedController(String location){
		String factoryName = getFactoryName(location);
		if(factoryName==null){
			RobotObject object = Robot.getInstance().getObject(location);
			if(!(object instanceof SpeedController))
				throw new RobotInitializationException(location+" is not a SpeedController");
			return (SpeedController) object;
		}
		return getFactory(factoryName).getSpeedController(getSubLocation(location));
	}
}
