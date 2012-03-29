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
	
	int buffer = -1;
	@Override
	public synchronized int read() throws IOException {
		if(buffer==-1)
			return readNative();
		int x =  buffer;
		buffer = -1;
		return x;
	}
	
	@Override
	public synchronized int available(){
		if(buffer<0)
			buffer = readNative();
		if(buffer<0)
			return 0;
		return 1;
	}
	long total = 0;
	long first = 0;
	public int readNative(){
		if(first == 0)
			first = System.currentTimeMillis();
		long start = System.currentTimeMillis();
		int value = ComputerPorts.INSTANCE.readFileByte(fileHandle);
		total += (System.currentTimeMillis()-start);
		System.out.println(total + " - "+ (  total*1.0/(System.currentTimeMillis()-first) ) );
		return value;
	}
	
}
