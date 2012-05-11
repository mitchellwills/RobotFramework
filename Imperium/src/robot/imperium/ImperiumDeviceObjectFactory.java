package robot.imperium;

import java.util.Map;

import robot.error.RobotInitializationException;
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
import robot.io.RobotObject;
import robot.io.analog.AnalogVoltageInput;
import robot.io.binary.BinaryInput;
import robot.io.binary.BinaryOutput;
import robot.io.counter.Counter;
import robot.io.dutycycle.DutyCycleInput;
import robot.io.encoder.Encoder;
import robot.io.factory.RobotObjectFactory;
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
	

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends RobotObject> T _getObject(Class<T> type, Map<String, String> params){
		if(type==null)
			throw new RobotInitializationException("Cannot create an object of type null");
		if(type.isAssignableFrom(AnalogVoltageInput.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			return (T) new ImperiumAnalogVoltageInput(device, location);
		}
		else if(type.isAssignableFrom(BinaryInput.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			return (T) new ImperiumDigitalInput(device, location);
		}
		else if(type.isAssignableFrom(BinaryOutput.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			return (T) new ImperiumDigitalOutput(device, location);
		}
		else if(type.isAssignableFrom(Counter.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			return (T) new ImperiumPulseCounter(device, location);
		}
		else if(type.isAssignableFrom(DutyCycleInput.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			return (T) new ImperiumDutyCycle(device, location);
		}
		else if(type.isAssignableFrom(Encoder.class)){
			String locationA = getParam(params, ImperiumQuadEncoder.PARAM_LOCATION_A);
			String locationB = getParam(params, ImperiumQuadEncoder.PARAM_LOCATION_B);
			return (T) new ImperiumQuadEncoder(device, locationA, locationB);
		}
		else if(type.isAssignableFrom(FrequencyInput.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			return (T) new ImperiumFrequency(device, location);
		}
		else if(type.isAssignableFrom(PPMReader.class)){
			try{
				String location = getParam(params, RobotObject.PARAM_LOCATION);
				int numChannels = getIntParam(params, PPMReader.PARAM_NUM_CHANNELS);
				return (T) new ImperiumPPMReader(device, location, numChannels);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the number of channels in a Virtual PPM reader");
			}
		}
		else if(type.isAssignableFrom(MSPWMOutput.class)){
			String location = getParam(params, RobotObject.PARAM_LOCATION);
			return (T) new ImperiumMSPWMOutput(device, location);
		}
		else if(SerialInterface.class.equals(type)){
			try{
				String location = getParam(params, RobotObject.PARAM_LOCATION);
				int baud = getIntParam(params, SerialInterface.PARAM_BAUD);
				return (T) new ImperiumSerialPort(device, location, baud);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the baud rate of a VirtualSerialInterface");
			}
		}
		
		return null;
	}
}
