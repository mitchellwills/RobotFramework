package robot.io.computerdevices.serial;

import java.io.IOException;
import java.io.InputStream;

import robot.io.computerdevices.ComputerPorts;

import com.sun.jna.Memory;

/**
 * @author Mitchell
 *
 */
class ComputerSerialPortInputStream extends InputStream{
	private final int fd;
	private final Memory memory = new Memory(1024);
	
	ComputerSerialPortInputStream(int fd){
		this.fd = fd;
	}


	@Override
	public int read() throws IOException {
		int bytesRead = ComputerPorts.INSTANCE.readBytes(fd, memory, 1);
		if(bytesRead<0)
			throw new IOException("Error reading from serial port");
		return memory.getByte(0);
	}
	
	@Override
	public synchronized int read(byte[] buffer, int off, int len) throws IOException {
		int bytesRead = ComputerPorts.INSTANCE.readBytes(fd, memory, len);
		if(bytesRead<0)
			throw new IOException("Error reading from serial port: "+bytesRead);
		memory.read(0, buffer, off, bytesRead);
		return bytesRead;
	}
	
	@Override
	public synchronized int available(){
		int available = ComputerPorts.INSTANCE.available(fd);
		if(available<0)//error
			return 0;
		return available;
	}
	
}
