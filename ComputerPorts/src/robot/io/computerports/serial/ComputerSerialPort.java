package robot.io.computerports.serial;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import robot.error.RobotInitializationException;
import robot.io.computerports.ComputerPorts;
import robot.io.serial.SerialInterface;

import com.sun.jna.platform.win32.WinNT;

/**
 * @author Mitchell
 * 
 * A serial port that exists on the computer
 *
 */
public class ComputerSerialPort implements SerialInterface{
	/**No parity*/
	public static final int NOPARITY = 0;
	/**Odd parity*/
	public static final int ODDPARITY = 1;
	/**Even parity*/
	public static final int EVENPARITY = 2;
	/**Mark parity*/
	public static final int MARKPARITY = 3;
	/**Space parity*/
	public static final int SPACEPARITY = 4;

	/**1 stop bit.*/
	public static final int ONESTOPBIT = 0;
	/**1.5 stop bits.*/
	public static final int ONE5STOPBITS = 1;
	/**2 stop bits.*/
	public static final int TWOSTOPBITS = 2;
	
	
	private final WinNT.HANDLE fileHandle;
	
	private final InputStream is;
	private final OutputStream os;
	
	/**
	 * create the serial port on the given port with the given baud rate
	 * there are 8 data bits, 1 stop bit, and no parity
	 * 
	 * @param name the name of the port
	 * @param baud the baud rate of the port
	 */
	public ComputerSerialPort(String name, int baud){
		this(name, baud, 8, ComputerSerialPort.ONESTOPBIT, ComputerSerialPort.NOPARITY);
	}
	/**
	 * create the serial port on the given port with the given configuration
	 * 
	 * @param name the name of the port
	 * @param baud the baud rate of the port
	 * @param dataBits
	 * @param stopBits
	 * @param parity
	 */
	public ComputerSerialPort(String name, int baud, int dataBits, int stopBits, int parity){
		fileHandle = ComputerPorts.INSTANCE.createSerialPort("\\\\.\\"+name);
		if(fileHandle==null)
			throw new RobotInitializationException("Error initalizing serial port on "+name);
		if(ComputerPorts.INSTANCE.openSerialPort(fileHandle, baud, dataBits, stopBits, parity))
			throw new RobotInitializationException("Error opening serial port on "+name);
		is = new BufferedInputStream(new ComputerSerialPortInputStream(fileHandle));
		os = new ComputerSerialPortOutputStream(fileHandle);
		
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
		if(fileHandle!=null)
			ComputerPorts.INSTANCE.closeSerialPort(fileHandle);
	}

}
