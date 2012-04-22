package robot.io.computerdevices;

import robot.io.FactoryObject;
import robot.io.RobotObjectFactory;

/**
 * @author Mitchell
 * 
 * Object that represents a computer
 *
 */
public class Computer implements FactoryObject{
	private static final Computer INSTANCE = new Computer();
	/**
	 * @return the singleton computer instance
	 */
	public static Computer get() {
		return INSTANCE;
	}
	private Computer(){
	}
	
	private ComputerDeviceFactory factory = new ComputerDeviceFactory();
	@Override
	public RobotObjectFactory getFactory() {
		return factory;
	}

}
