package robot.io.computerdevices.serial;

import java.io.IOException;
import java.io.OutputStream;

import robot.io.computerdevices.ComputerPorts;

import com.sun.jna.Memory;

/**
 * @author Mitchell
 * 
 */
class ComputerSerialPortOutputStream extends OutputStream {
	private final int fd;
	private final Memory memory = new Memory(8192);

	ComputerSerialPortOutputStream(int fd) {
		this.fd = fd;
	}

	@Override
	public void write(int b) throws IOException {
		memory.setByte(0, (byte) b);
		ComputerPorts.INSTANCE.writeBytes(fd, memory, 1);
	}
	
	@Override
	public void write(byte[] buffer, int off, int len){
		memory.write(0, buffer, off, len);
		ComputerPorts.INSTANCE.writeBytes(fd, memory, len);
	}

}
