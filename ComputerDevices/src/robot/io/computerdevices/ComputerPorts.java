package robot.io.computerdevices;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * @author Mitchell
 * 
 * Interface used to access the native computer port library
 *
 */
public interface ComputerPorts extends Library {
	/**
	 * instance of the port library
	 */
	ComputerPorts INSTANCE = (ComputerPorts) Native.loadLibrary(
			"libComputerPortsNative", ComputerPorts.class);

	/**
	 * open a serial port
	 * @param name name of the file to open
	 * @param baud
	 * @return the native file descriptor
	 */
	int openSerialPort(String name, int baud);

	/**
	 * @param fd the native file descriptor
	 * @param byffer
	 * @param length
	 * @return the number of bytes read
	 */
	int readBytes(int fd, Pointer byffer, int length);
	/**
	 * @param fd the native file descriptor
	 * @param buffer
	 * @param length
	 * @return the number of bytes written
	 */
	int writeBytes(int fd, Pointer buffer, int length);
	
	/**
	 * @param fd the native file descriptor
	 * @return the number of bytes available to read
	 */
	int available(int fd);
	
	/**
	 * Close the native file
	 * @param fd the native file descriptor
	 * @return true if successful
	 */
	boolean closeSerialPort(int fd);
}
