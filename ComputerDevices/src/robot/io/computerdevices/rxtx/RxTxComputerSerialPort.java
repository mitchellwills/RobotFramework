package robot.io.computerdevices.rxtx;

import gnu.io.*;

import java.io.*;
import java.lang.reflect.*;

import robot.error.*;
import robot.io.*;
import robot.io.serial.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

/**
 * @author Mitchell
 * 
 * A serial port that exists on the computer
 *
 */
public final class RxTxComputerSerialPort implements SerialInterface{
	private final CommPortIdentifier identifier;
	private final SerialPort port;
	private final InputStream is;
	private final OutputStream os;
	
	/**
	 * create the serial port on the given port with the given baud rate
	 * there are 8 data bits, 1 stop bit, and no parity
	 * 
	 * @param name the name of the port
	 * @param baud the baud rate of the port
	 */
	@Inject public RxTxComputerSerialPort(
			@Assisted(RobotObject.PARAM_LOCATION) String name,
			@Assisted(SerialInterface.PARAM_BAUD) int baud){
		this(name, baud, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
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
	public RxTxComputerSerialPort(String name, int baud, int dataBits, int stopBits, int parity){
		try {
			identifier = CommPortIdentifier.getPortIdentifier(name);
			port = (SerialPort)identifier.open(getClass().getName(), 2000);
			port.setSerialPortParams(baud, dataBits, stopBits, parity);
			is = port.getInputStream();
			os = port.getOutputStream();
		} catch (NoSuchPortException e) {
			throw new RobotInitializationException("Could not initialize Serial Port ("+name+"): The port does not exist", e);
		} catch (PortInUseException e) {
			throw new RobotInitializationException("Could not initialize Serial Port ("+name+"): The port is in use", e);
		} catch (ClassCastException e) {
			throw new RobotInitializationException("Could not initialize Serial Port ("+name+"): It is not a serial port", e);
		} catch (UnsupportedCommOperationException e) {
			throw new RobotInitializationException("Could not initialize Serial Port ("+name+"): The configuration is not supported", e);
		} catch (IOException e) {
			throw new RobotInitializationException("Could not initialize Serial Port ("+name+"): IO error", e);
		}
	}
	
	@Override
	public int getBaudRate(){
		return port.getBaudRate();
	}

	@Override
	public InputStream getInputStream() {
		return is;
	}

	@Override
	public OutputStream getOutputStream() {
		return os;
	}
	
	public void close(){//https://forums.oracle.com/forums/thread.jspa?threadID=1292323
		try{
			Field ioLockedField = RXTXPort.class.getDeclaredField("IOLocked");
			ioLockedField.setAccessible(true);
			ioLockedField.setInt(port, 0);
		} catch(Exception e){
			e.printStackTrace();
		}
		port.close();
	}
}
