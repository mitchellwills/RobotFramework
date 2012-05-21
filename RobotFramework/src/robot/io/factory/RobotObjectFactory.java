package robot.io.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import robot.error.RobotInitializationException;
import robot.io.RobotObject;
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
 * An object that can be used to get RobotObjects from a String location
 *
 * @author Mitchell
 * 
 */
public abstract class RobotObjectFactory {

	/**
	 * @param params
	 * @param key
	 * @return the value of a parameter as a string
	 */
	public static String getParam(Map<String, String> params, String key){
		return params.get(key);
	}
	
	/**
	 * @param params
	 * @param key
	 * @return the value of a parameter as an integer
	 */
	public static int getIntParam(Map<String, String> params, String key){
		return Integer.parseInt(getParam(params, key));
	}
	
	/**
	 * @param params
	 * @param key
	 * @return the value of a parameter as a double floating point number
	 */
	public static double getDoubleParam(Map<String, String> params, String key){
		return Double.parseDouble(getParam(params, key));
	}

	/**  
	 * @param type the full type name of the object to be loaded
	 * @param params parameters to pass to the class
	 * @return a new RobotObject
	 */
	@SuppressWarnings("unchecked")
	public RobotObject getObject(String type, Map<String, String> params){
		if(type==null)
			throw new RobotInitializationException("Cannot create an object of type null");
		if(type.equals("AnalogVoltageInput"))
			return getObject(AnalogVoltageInput.class, params);
		else if(type.equals("BinaryInput"))
			return getObject(BinaryInput.class, params);
		else if(type.equals("BinaryOutput"))
			return getObject(BinaryOutput.class, params);
		else if(type.equals("Counter"))
			return getObject(Counter.class, params);
		else if(type.equals("DutyCycleInput"))
			return getObject(DutyCycleInput.class, params);
		else if(type.equals("Encoder"))
			return getObject(Encoder.class, params);
		else if(type.equals("FrequencyInput"))
			return getObject(FrequencyInput.class, params);
		else if(type.equals("PPMReader"))
			return getObject(PPMReader.class, params);
		else if(type.equals("PWMOutput"))
			return getObject(PWMOutput.class, params);
		else if(type.equals("MSPWMOutput"))
			return getObject(MSPWMOutput.class, params);
		else if(type.equals("SerialInterface"))
			return getObject(SerialInterface.class, params);
		else if(type.equals("Joystick"))
			return getObject(Joystick.class, params);
		else if(type.equals("Accelerometer"))
			return getObject(Accelerometer.class, params);
		else if(type.equals("SpeedController"))
			return getObject(SpeedController.class, params);
		
		try {
			Class<?> clazz = Class.forName(type);
			return getObject((Class<? extends RobotObject>)clazz, params);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param type the type of the object to be loaded
	 * @param location the value corresponding to an objects location
	 * @return a new RobotObject
	 */
	public <T extends RobotObject> T getObject(Class<T> type, String location){
		Map<String, String> params = new HashMap<String, String>();
		params.put(RobotObject.PARAM_LOCATION, location);
		return getObject(type, params);
	}
	

	protected abstract <T extends RobotObject> T _getObject(Class<T> type, Map<String, String> params);
	
	/**
	 * @param type the type of the object to be loaded
	 * @param params parameters to pass to the class
	 * @return a new RobotObject
	 */
	public final <T extends RobotObject> T getObject(Class<T> type, Map<String, String> params){
		if(type==null)
			throw new RobotInitializationException("Cannot create an object of type null");
		T object = _getObject(type, params);
		if(object!=null)
			return object;
		try{
			//TODO expand this beyond Map constructor, Respect FactoryConstrucable and FactoryParameter annotations
			Constructor<T> constructor = type.getConstructor(Map.class);
			return constructor.newInstance(params);
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
