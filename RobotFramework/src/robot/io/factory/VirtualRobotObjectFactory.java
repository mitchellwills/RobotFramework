package robot.io.factory;

import java.util.Map;

import robot.error.RobotInitializationException;
import robot.io.RobotObject;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends RobotObject> T getObject(Class<T> type, Map<String, String> params){
		if(type==null)
			throw new RobotInitializationException("Cannot create an object of type null");
		if(type.isAssignableFrom(AnalogVoltageInput.class)){
			try{
				double maxVoltage = getDoubleParam(params, AnalogVoltageInput.PARAM_MAX_VOLTAGE);
				return (T) new VirtualAnalogVoltageInput(maxVoltage);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the max voltage of a Virtual Analog Voltage Input");
			}
		}
		else if(type.isAssignableFrom(BinaryInput.class)){
			return (T) new VirtualBinaryInput();
		}
		else if(type.isAssignableFrom(BinaryOutput.class)){
			return (T) new VirtualBinaryOutput();
		}
		else if(type.isAssignableFrom(Counter.class)){
			return (T) new VirtualCounter();
		}
		else if(type.isAssignableFrom(DutyCycleInput.class)){
			return (T) new VirtualDutyCycleInput();
		}
		else if(type.isAssignableFrom(Encoder.class)){
			return (T) new VirtualEncoder();
		}
		else if(type.isAssignableFrom(FrequencyInput.class)){
			return (T) new VirtualFrequencyInput();
		}
		else if(type.isAssignableFrom(PPMReader.class)){
			try{
				int numChannels = getIntParam(params, PPMReader.PARAM_NUM_CHANNELS);
				return (T) new VirtualPPMReader(numChannels);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the number of channels in a Virtual PPM reader");
			}
		}
		else if(type.isAssignableFrom(PWMOutput.class)){
			try{
				double frequency = getDoubleParam(params, PWMOutput.PARAM_FREQUENCY);
				return (T) new VirtualPWMOutput(frequency);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the frequency of a Virtual PWM output");
			}
		}
		else if(type.isAssignableFrom(MSPWMOutput.class)){
			return (T) new VirtualMSPWMOutput();
		}
		else if(SerialInterface.class.equals(type)){
			try{
				int baud = getIntParam(params, SerialInterface.PARAM_BAUD);
				return (T) new VirtualSerialInterface(baud);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the baud rate of a VirtualSerialInterface");
			}
		}
		else if(type.isAssignableFrom(Joystick.class)){
			try{
				String name = getParam(params, PPMReader.PARAM_NUM_CHANNELS);
				int numButtons = getIntParam(params, PPMReader.PARAM_NUM_CHANNELS);
				int numAxes = getIntParam(params, PPMReader.PARAM_NUM_CHANNELS);
				int numDirectionals = getIntParam(params, PPMReader.PARAM_NUM_CHANNELS);
				return (T) new VirtualJoystick(name, numButtons, numAxes, numDirectionals);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the name and number of buttons, axes, and directionals in a Virtual Joysitck");
			}
		}
		else if(type.isAssignableFrom(Accelerometer.class)){
			try{
				int numAxes = getIntParam(params, Accelerometer.PARAM_NUM_AXES);
				return (T) new VirtualAccelerometer(numAxes);
			} catch(NumberFormatException e){
				throw new RobotInitializationException("You must specify the baud rate of a VirtualSerialInterface");
			}
		}
		else if(type.isAssignableFrom(SpeedController.class)){
			return (T) new VirtualSpeedController();
		}

		
		return null;
	}

	@Override
	public VirtualRobotObjectFactory getFactory() {
		return this;
	}
}
