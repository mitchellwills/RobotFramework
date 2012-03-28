package robot.io.computerports.serial;

import java.io.IOException;
import java.io.OutputStream;

import robot.io.computerports.ComputerPorts;

import com.sun.jna.platform.win32.WinNT;

/**
 * @author Mitchell
 * 
 */
class ComputerSerialPortOutputStream extends OutputStream {
	private final WinNT.HANDLE fileHandle;

	ComputerSerialPortOutputStream(WinNT.HANDLE fileHandle) {
		this.fileHandle = fileHandle;
	}

	@Override
	public void write(int b) throws IOException {
		ComputerPorts.INSTANCE.writeFileByte(fileHandle, b);
	}

}
