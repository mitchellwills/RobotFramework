package robot.io.computerports.serial;

import java.io.IOException;
import java.io.InputStream;

import robot.io.computerports.ComputerPorts;

import com.sun.jna.platform.win32.WinNT;

/**
 * @author Mitchell
 *
 */
class ComputerSerialPortInputStream extends InputStream{
	public static final int BUFFER_SIZE = 2048;
	
	private final WinNT.HANDLE fileHandle;
	
	ComputerSerialPortInputStream(WinNT.HANDLE fileHandle){
		this.fileHandle = fileHandle;
	}
	
	@Override
	public int read() throws IOException {
		return ComputerPorts.INSTANCE.readFileByte(fileHandle);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = ComputerPorts.INSTANCE.readFile(fileHandle, b, off, len);
		if(bytesRead<=0)
			throw new IOException("Error reading byte from file stream");
		return bytesRead;
	}
	
}
