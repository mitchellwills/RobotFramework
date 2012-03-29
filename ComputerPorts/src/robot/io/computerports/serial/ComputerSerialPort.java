package robot.io.computerports.serial;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import robot.error.RobotInitializationException;
import robot.io.computerports.ComputerPorts;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 * 
 * A serial port that exists on the computer
 *
 */
public class ComputerSerialPort implements SerialInterface{
	
	
	private final int fd;
	
	private final InputStream is;
	private final OutputStream os;
	
	/**
	 * create the serial port on the given port with the given configuration
	 * 
	 * @param name the name of the port
	 * @param baud the baud rate of the port
	 */
	public ComputerSerialPort(String name, int baud){
		fd = ComputerPorts.INSTANCE.openSerialPort("\\\\.\\"+name, baud);
		if(fd<0)
			throw new RobotInitializationException("Error initalizing serial port on "+name);
		
		is = new ComputerSerialPortInputStream(fd);
		os = new BufferedOutputStream(new ComputerSerialPortOutputStream(fd));
		
	}
	
	/**
	 * close the connection to the serial port
	 * NOTE: the port cannot be reopened throught this object
	 */
	public void close() {
		if(fd>=0)
			ComputerPorts.INSTANCE.closeSerialPort(fd);
	}

	@Override
	public InputStream getInputStream() {
		return is;
	}

	@Override
	public OutputStream getOutputStream() {
		return os;
	}
	

	/**
	 * 
	 */
	public void destroy(){
		close();
	}

}
