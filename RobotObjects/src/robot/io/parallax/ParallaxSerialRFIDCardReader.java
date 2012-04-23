package robot.io.parallax;

import java.io.IOException;
import java.io.InputStream;

import robot.io.Input;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;
import robot.io.binary.BinaryOutput;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 * 
 * Represents the parallax RFID Card Reader
 *
 */
public class ParallaxSerialRFIDCardReader implements Input, UpdatableObject{
	private static final int START_BYTE = 0x0A;
	private static final int END_BYTE = 0x0D;
	
	private final BinaryOutput enOutput;
	
	private String lastTag = null;
	

	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	
	/**
	 * Create a card reader without control of the enable line
	 * @param serialPort the port used to to receive data from the device
	 */
	public ParallaxSerialRFIDCardReader(SerialInterface serialPort){
		this(serialPort, null);
	}
	
	/**
	 * Create a card reader
	 * @param serialPort the port used to to receive data from the device
	 * @param enOutput the line connected to the devices enable port
	 */
	public ParallaxSerialRFIDCardReader(SerialInterface serialPort, BinaryOutput enOutput){
		this.enOutput = enOutput;
		new SerialHandler(serialPort.getInputStream()).start();
	}
	
	/**
	 * @return the last tag scanned or null if no tags have been scanned
	 */
	public String getLastTag(){
		return lastTag;
	}
	
	/**
	 * enable the card reader
	 */
	public void enable(){
		if(enOutput!=null)
			enOutput.set(false);
	}
	
	/**
	 * disable the card reader
	 */
	public void disable(){
		if(enOutput!=null)
			enOutput.set(true);
	}

	private class SerialHandler extends Thread{
		private final InputStream is;
		public SerialHandler(InputStream is) {
			this.is = is;
		}

		@Override
		public void run(){
			while(true){
				try{
					while(is.read()!=START_BYTE)
						Thread.yield();
					byte[] data = new byte[10];
					is.read(data);
					while(is.available()==0)
						Thread.yield();
					if(is.read()!=END_BYTE)
						continue;
					
					lastTag = new String(data);
					model.fireUpdateEvent();
					
				} catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
