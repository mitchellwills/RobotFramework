package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.SERIAL_PORT_TYPE_ID;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.hardware.PinCapability;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 * 
 *         Serial Port on an ImperiumDevice
 * 
 */
public class ImperiumSerialPort extends ImperiumDeviceObject implements
		SerialInterface {

	private BufferedOutputStream outputStream;
	private PipedInputStream inputStream;
	private PipedOutputStream inputOutputStream;

	/**
	 * Create a new Imperium Serial Port
	 * The pins should both be on the same serial port on the device
	 * 
	 * @param device
	 * @param rxPin the pin used to receive serial data
	 * @param txPin the pin used to transmit serial data
	 * @param baud the baud rate 
	 * TODO use actual baud rates
	 */
	public ImperiumSerialPort(final ImperiumDevice device, String rxPin, String txPin, int baud) {
		super(SERIAL_PORT_TYPE_ID, device, device.getHardwareConfiguration().getPinId(rxPin), device.getHardwareConfiguration().getPinId(txPin), baud);
		outputStream = new BufferedOutputStream(new OutputStream() {
			
			@Override
			public void write(byte b[], int off, int len) throws IOException {
				//System.out.println("Sent "+Arrays.toString(b));
				device.sendMessagePacket(ImperiumSerialPort.this, b, off, len);
		    }

			@Override
			public void write(int b) throws IOException {
				if(b==-1)
					return;
				write(new byte[]{(byte) b}, 0, 1);
			}
		}, 50);
		try {
			inputStream = new PipedInputStream(inputOutputStream = new PipedOutputStream());
		} catch (IOException e) {
			throw new RobotInitializationException("Error creating input stream for serial port", e);
		}
	}

	@Override
	public Set<PinCapability> getRequiredCapabilities(int pinId) {
		if (pinId == 0)
			return EnumSet.of(PinCapability.SerialRx);
		if (pinId == 1)
			return EnumSet.of(PinCapability.SerialTx);
		return Collections.<PinCapability> emptySet();
	}

	@Override
	public void initialize() {
		//
	}

	@Override
	public void setValue(int value) {
		//
	}

	@Override
	public void message(long[] values) {
		for(int i = 0; i<values.length; ++i){
			try {
				inputOutputStream.write((int)values[i]);
			} catch (IOException e) {
				throw new RobotException("Error writing to input stream", e);
			}
		}
		//System.out.println("Received "+Arrays.toString(values));
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}

}
