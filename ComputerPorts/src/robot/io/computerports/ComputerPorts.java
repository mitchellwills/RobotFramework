package robot.io.computerports;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;

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

	WinNT.HANDLE createSerialPort(String name);
	boolean openSerialPort(WinNT.HANDLE handle, int baud, int dataBits, int stopBits, int parity);

	int readFileByte(WinNT.HANDLE handle);
	int writeFileByte(WinNT.HANDLE handle, int b);
	
	boolean closeSerialPort(WinNT.HANDLE handle);
}
