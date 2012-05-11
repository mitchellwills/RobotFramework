package robot.io.serial;

import java.io.InputStream;
import java.io.OutputStream;

import robot.io.Input;
import robot.io.Output;

/**
 * @author Mitchell
 * 
 * Represents a serial bus that you can read and write to
 *
 */
public interface SerialInterface extends Input, Output{
	/**
	 * name of the parameter in factories param map corresponding to the serial port's baud rate
	 */
	String PARAM_BAUD = null;

	/**
	 * @return a stream that allows reading from the serial port
	 */
	public InputStream getInputStream();
	/**
	 * @return a stream that allows writing to the serial port
	 */
	public OutputStream getOutputStream();
	
	/**
	 * @return the baud rate of the serial interface
	 */
	public int getBaudRate();
}
