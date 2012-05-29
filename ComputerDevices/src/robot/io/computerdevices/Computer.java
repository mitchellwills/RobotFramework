package robot.io.computerdevices;

import gnu.io.*;

import java.util.*;

import robot.boostrap.partial.*;
import robot.io.host.*;

import com.google.inject.*;

/**
 * @author Mitchell
 * 
 * Object that represents a computer
 *
 */
public class Computer implements BuilderContext, Host{

	private final ComputerBattery battery = new ComputerBattery();//TODO handle if there is not battery
	/**
	 * @return the battery installed on the computer
	 */
	@Override
	public ComputerBattery getBattery(){
		return battery;
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
	
	
	@Override
	public Iterable<? extends Module> getInjectionModules() {
		return Collections.singleton(new ComputerDevicesModule());
	}
	@Override
	public Iterable<? extends PartialModule> getPartialInjectionModules() {
		return Collections.singleton(new ComputerDevicesPartialModule());
	}
	@Override
	public Map<String, Object> getPartialParameters() {
		return Collections.EMPTY_MAP;
	}

}
