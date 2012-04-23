package robot.io.computerdevices.rxtx;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import robot.error.RobotInitializationException;
import robot.io.serial.SerialInterface;

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
	public RxTxComputerSerialPort(String name, int baud){
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
}
