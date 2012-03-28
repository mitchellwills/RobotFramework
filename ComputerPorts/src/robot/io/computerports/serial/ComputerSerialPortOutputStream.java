package robot.io.computerports.serial;

import java.io.IOException;
import java.io.OutputStream;

import robot.io.computerports.ComputerPorts;
import robot.io.computerports.MemoryBuffer;

import com.sun.jna.platform.win32.WinNT;

/**
 * @author Mitchell
 * 
 */
class ComputerSerialPortOutputStream extends OutputStream {
	public static final int BUFFER_SIZE = 2048;

	private final WinNT.HANDLE fileHandle;

	private final MemoryBuffer buffer;

	ComputerSerialPortOutputStream(WinNT.HANDLE fileHandle) {
		this.fileHandle = fileHandle;
		buffer = new MemoryBuffer(BUFFER_SIZE);
	}

	@Override
	public synchronized void write(int b) throws IOException {
		buffer.append((byte) b);
	}

	@Override
	public synchronized void write(byte b[]) throws IOException {
		buffer.append(b);
	}

	@Override
	public synchronized void write(byte b[], int off, int len) throws IOException {
		buffer.append(b, off, b.length);
	}

	@Override
	public synchronized void flush() throws IOException {
		ComputerPorts.INSTANCE.writeFile(fileHandle, buffer.getMemory(), buffer.getWrittenData());
		buffer.clearWrittenData();
		
	}

}
