package robot.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Mitchell
 * 
 * Represents a serial bus that you can read and write to
 *
 */
public interface SerialInterface extends Input, Output{
	/**
	 * @return a stream that allows reading from the serial port
	 */
	public InputStream getInputStream();
	/**
	 * @return a stream that allows writing to the serial port
	 */
	public OutputStream getOutputStream();
}
