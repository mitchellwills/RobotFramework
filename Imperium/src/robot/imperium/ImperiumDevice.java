package robot.imperium;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import robot.error.RobotInitializationException;
import robot.imperium.hardware.HardwareConfiguration;
import robot.imperium.packet.ImperiumPacket;
import robot.imperium.packet.PacketIds;
import robot.io.SerialInterface;
import robot.util.ByteUtil;
import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 * A serial device that that allows for fast GPIO
 *
 */
public class ImperiumDevice {
	private final InputStream is;
	private final OutputStream os;
	private final HardwareConfiguration hardwareConfiguration;
	
	

	/**
	 * @param serialPort the port over which the computer will interact with the device
	 * @param hardwareConfiguration
	 */
	public ImperiumDevice(SerialInterface serialPort, HardwareConfiguration hardwareConfiguration){
		this(serialPort.getInputStream(), serialPort.getOutputStream(), hardwareConfiguration);
	}
	/**
	 * @param is
	 * @param os
	 * @param hardwareConfiguration 
	 */
	public ImperiumDevice(InputStream is, OutputStream os, HardwareConfiguration hardwareConfiguration){
		if(is==null)
			throw new RobotInitializationException("Imperium Input Stream was null");
		if(os==null)
			throw new RobotInitializationException("Imperium Output Stream was null");
		if(hardwareConfiguration==null)
			throw new RobotInitializationException("Imperium Hardware configuration was null");
		this.is = is;
		this.os = os;
		this.hardwareConfiguration = hardwareConfiguration;
		new Thread(new ImperiumEventThread()).start();
	}
	

	private ImperiumDeviceState state = ImperiumDeviceState.DISCONNECTED;
	
	/**
	 * @return the state of the connection to the device
	 */
	public ImperiumDeviceState getState(){
		return state;
	}
	
	
	
	private int maxUpdateRate = 0;
	/**
	 * @return the maximum rate the device will send input updates (updates per second)
	 * 0 means no limit
	 */
	public int getMaxUpdateRate(){
		return maxUpdateRate;
	}
	/**
	 * Set the maximum number of input updates per second the device will send
	 * @param rate in updates per second
	 */
	public void setMaxUpdateRate(int rate){
		this.maxUpdateRate = rate;
	}
	
	
	private final List<ImperiumDeviceObject> objects = new ArrayList<ImperiumDeviceObject>();
	/**
	 * @param object the object to be added to this device
	 * @return the object id of the new object
	 */
	int register(ImperiumDeviceObject object){
		for(int pinId = 0; pinId<object.getPinCount(); ++pinId){
			if(!hardwareConfiguration.supports(object.getPin(pinId), object.getRequiredCapabilities(pinId)))
				throw new RobotInitializationException("The "+hardwareConfiguration.getName()+" does not support "+object.getRequiredCapabilities(pinId)+" on pin "+object.getPin(pinId));
		}
		objects.add(object);
		return objects.size()-1;
	}
	
	
	
	
	
	private final Object configureLock = new Object();
	/**
	 * configure the device based on currently registered 
	 * @throws IOException 
	 */
	public void configure() {
		RobotUtil.sleep(2000);//wait for device to initialize
		
		try {
			synchronized(configureLock){
				state = ImperiumDeviceState.CONFIGURING;
				ImperiumPacket configurePacket = new ImperiumPacket();
				configurePacket.setId(PacketIds.GLOBAL_CONFIGURE);
				configurePacket.setDataLength(0);
				configurePacket.appendInteger(maxUpdateRate, 1);
				
				configurePacket.appendInteger(objects.size(), 1);
				
				for(ImperiumDeviceObject object:objects){
					configurePacket.appendInteger(object.getObjectId(), 1);
					configurePacket.appendInteger(object.getTypeId(), 1);
					configurePacket.appendInteger(object.getPinCount(), 1);
					for(int i = 0; i<object.getPinCount(); ++i)
						configurePacket.appendInteger(object.getPin(i), 1);
				}
				sendPacket(configurePacket);
				try {
					configureLock.wait(1000);
				} catch (InterruptedException e) {
					//continue if interrupted
				}
				if(state==ImperiumDeviceState.CONFIGURE_ERROR)
					throw new RobotInitializationException("Error configuring Imperium Device. The device failed to configure properly");
				if(state==ImperiumDeviceState.CONFIGURING)
					throw new RobotInitializationException("Error configuring Imperium Device. The device did not respond within 1 second");
				
				for(ImperiumDeviceObject object:objects)
					object.initialize();
			}
		} catch (IOException e) {
			throw new RobotInitializationException("Error configuring Imperium Device", e);
		}
		

	}

	
	private void confirmConfig(ImperiumPacket packet) {
		synchronized(configureLock){
			if(packet==null){//TODO check if configured successfully
				state = ImperiumDeviceState.CONFIGURE_ERROR;
			}
			else{
				state = ImperiumDeviceState.CONNECTED;
			}
			configureLock.notifyAll();
		}
	}

	private void inputValue(ImperiumPacket packet) {
		int objectId = ByteUtil.getUnsigned(packet.getData(), 0, 1);
		int value = ByteUtil.getUnsigned(packet.getData(), 1, 4);
		ImperiumDeviceObject object = objects.get(objectId);
		object.setValue(value);
	}
	
	
	
	
	
	
	
	
	
	
	
	private class ImperiumEventThread implements Runnable{
		private ImperiumPacket packet = new ImperiumPacket();
		@Override
		public void run() {
			while(true){
				try{
					if(is.available()>0){
						packet.read(is);
						System.out.println("Received: "+packet);
						processInputPacket(packet);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * process a packet from the device
	 * @param packet
	 */
	private void processInputPacket(ImperiumPacket packet){
		switch(packet.getId()){
		case PacketIds.CONFIGURE_CONFIRM:
			confirmConfig(packet);
			break;
		case PacketIds.INPUT_VALUE:
			inputValue(packet);
			break;
		}
	}
	/**
	 * send a packet to the device
	 * @param packet
	 * @throws IOException 
	 */
	public void sendPacket(ImperiumPacket packet) throws IOException {
		System.out.println("Sent: "+packet);
		if(packet!=null)
			packet.write(os);
	}
}
