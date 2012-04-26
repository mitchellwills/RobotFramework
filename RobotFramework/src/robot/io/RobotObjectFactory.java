package robot.io;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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
 * An object that can be used to get RobotObjects from a String location
 *
 */
public abstract class RobotObjectFactory {
	/**
	 * @param location
	 * @return a new AnalogVoltageInput
	 */
	public AnalogVoltageInput getAnalogVoltageInput(String location){
		throw new RobotInitializationException("Analog Voltage Input not supported");
	}

	/**
	 * @param location
	 * @return a new BinaryInput
	 */
	public BinaryInput getBinaryInput(String location){
		throw new RobotInitializationException("Binary Input not supported");
	}

	/**
	 * @param location
	 * @return a new BinaryOutput
	 */
	public BinaryOutput getBinaryOutput(String location){
		throw new RobotInitializationException("Binary Output not supported");
	}

	/**
	 * @param location
	 * @return a new Counter
	 */
	public Counter getCounter(String location){
		throw new RobotInitializationException("Counter not supported");
	}

	/**
	 * @param location
	 * @return a new DutyCycleInput
	 */
	public DutyCycleInput getDutyCycle(String location){
		throw new RobotInitializationException("Duty Cycle not supported");
	}

	/**
	 * @param aLocation 
	 * @param bLocation 
	 * @return a new Encoder
	 */
	public Encoder getEncoder(String aLocation, String bLocation){
		throw new RobotInitializationException("Encoder not supported");
	}

	/**
	 * @param location
	 * @return a new FrequencyInput
	 */
	public FrequencyInput getFrequencyInput(String location){
		throw new RobotInitializationException("Frequency not supported");
	}

	/**
	 * @param location
	 * @param numChannels the number of channels the PPM reader will read
	 * @return a new PPMReader
	 */
	public PPMReader getPPMReader(String location, int numChannels){
		throw new RobotInitializationException("PPMReader not supported");
	}

	/**
	 * @param location
	 * @return a new PWMOutput
	 */
	public PWMOutput getPWMOutput(String location){
		throw new RobotInitializationException("PWM Output not supported");
	}

	/**
	 * @param location
	 * @return a new MSPWMOutput
	 */
	public MSPWMOutput getMSPWM(String location){
		throw new RobotInitializationException("MS PWM not supported");
	}

	/**
	 * @param location
	 * @param baud the baud rate of the serial interface
	 * @return a new SerialInterface
	 */
	public SerialInterface getSerialInterface(String location, int baud){
		throw new RobotInitializationException("Serial Interface not supported");
	}

	/**
	 * @param location
	 * @return a new Joystick
	 */
	public Joystick getJoystick(String location){
		throw new RobotInitializationException("Joystick not supported");
	}

	/**
	 * @param location
	 * @return a new Accelerometer
	 */
	public Accelerometer getAccelerometer(String location){
		throw new RobotInitializationException("Accelerometer not supported");
	}

	/**
	 * @param location
	 * @return a new SpeedController
	 */
	public SpeedController getSpeedController(String location){
		throw new RobotInitializationException("Speed Controller not supported");
	}
	


	/**
	 * @param type the full type name of the object to be loaded
	 * @param params parameters to pass to the class
	 * @return a new RobotObject
	 */
	public RobotObject getObject(String type, Map<String, String> params){
		try {
			return getObject((Class<? extends RobotObject>)Class.forName(type), params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param type the type of the object to be loaded
	 * @param params parameters to pass to the class
	 * @return a new RobotObject
	 */
	public RobotObject getObject(Class<? extends RobotObject> type, Map<String, String> params){

		try{
			Constructor<? extends RobotObject> constructor = type.getConstructor(Robot.class, Map.class);
			return constructor.newInstance(this, params);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
