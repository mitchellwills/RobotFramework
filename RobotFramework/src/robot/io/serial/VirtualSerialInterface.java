package robot.io.serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import robot.error.RobotInitializationException;

/**
 * @author Mitchell
 * 
 * A virtual serial interface whose input and output can be faked
 *
 */
public class VirtualSerialInterface implements SerialInterface {
	
	private final int baud;
	private final PipedInputStream inputStream;
	private final PipedOutputStream inputOutputStream;
	
	private final PipedOutputStream outputStream;
	private final PipedInputStream outputInputStream;
	/**
	 * Create a virtual serial interface that has a given baud rate (this baud rate is ignored but it is what is reported through {@link #getBaudRate()}
	 * @param baud
	 */
	public VirtualSerialInterface(int baud){
		this.baud = baud;
		try {
			inputStream = new PipedInputStream(inputOutputStream = new PipedOutputStream());
			outputInputStream = new PipedInputStream(outputStream = new PipedOutputStream());
		} catch (IOException e) {
			throw new RobotInitializationException("Error creating streams for fake serial interface", e);
		}
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public int getBaudRate() {
		return baud;
	}
	
	/**
	 * @return the output stream that will pipe all data written to it to the interface's input stream
	 */
	public OutputStream getInputOutputStream(){
		return inputOutputStream;
	}
	
	/**
	 * @return the input stream that all data written to the interface's output stream will appear in
	 */
	public InputStream getOutputInputStream(){
		return outputInputStream;
	}

}
