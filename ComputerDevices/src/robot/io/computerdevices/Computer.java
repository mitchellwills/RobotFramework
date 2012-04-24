package robot.io.computerdevices;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;

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
	
	/**
	 * @return the currently connected serial ports
	 */
	public String[] getSerialPorts(){
		Enumeration<?> portIdentifiers = CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> ports = new ArrayList<String>();
		while(portIdentifiers.hasMoreElements()){
			CommPortIdentifier portIdentifier = (CommPortIdentifier) portIdentifiers.nextElement();
			if(portIdentifier.getPortType()==CommPortIdentifier.PORT_SERIAL)
				ports.add(portIdentifier.getName());
		}
		String[] r = new String[ports.size()];
		for(int i = 0; i<ports.size(); ++i)
			r[i] = ports.get(i);
		return r;
	}

}
