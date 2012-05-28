package robot.io.virtual;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;
import org.junit.runner.*;

import test.*;
import test.RobotTestRunner.ParamTest;
import test.RobotTestRunner.TestParameter;

@RunWith(RobotTestRunner.class)
public class VirtualSerialInterfaceTest {
	
	@TestParameter
	public static final Object[] test1 = {9600, new byte[]{'H', 'e', 'l', 'l', 'o'}, new byte[]{'H', 'i'}};
	@TestParameter
	public static final Object[] test2 = {115200, new byte[]{'T', 'e', 's', 't'}, new byte[]{'i'}};
	@TestParameter
	public static final Object[] test3 = {1000, new byte[]{'f'}, new byte[]{}};

	private final int baud;
	private final byte[] readData;
	private final byte[] writeData;

	
	public VirtualSerialInterfaceTest(int baud, byte[] readData, byte[] writeData) {
		this.baud = baud;
		this.readData = readData;
		this.writeData = writeData;
	}
	
	private VirtualSerialInterface serialPort;
	@Before public void setup(){
		serialPort = new VirtualSerialInterface(baud);
	}
	
	@ParamTest public void testBaudRate() {
		assertEquals(baud, serialPort.getBaudRate(), 0);
	}
	
	@ParamTest public void testRead() {
		try {
			serialPort.getInputOutputStream().write(readData);
			byte[] dst = new byte[readData.length];
			serialPort.getInputStream().read(dst);
			assertArrayEquals(readData, dst);
		} catch (IOException e) {
			fail();
		}
	}
	
	@ParamTest public void testWrite() {
		try {
			serialPort.getOutputStream().write(writeData);
			byte[] dst = new byte[writeData.length];
			serialPort.getOutputInputStream().read(dst);
			assertArrayEquals(writeData, dst);
		} catch (IOException e) {
			fail();
		}
	}

}
