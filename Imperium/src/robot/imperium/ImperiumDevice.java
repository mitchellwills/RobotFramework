package robot.imperium;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import robot.Robot;
import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.imperium.hardware.ImperiumHardwareConfiguration;
import robot.imperium.packet.ImperiumPacket;
import robot.imperium.packet.PacketIds;
import robot.io.FactoryObject;
import robot.io.RobotObject;
import robot.io.RobotObjectFactory;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;
import robot.io.serial.SerialInterface;
import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 *         A serial device that that allows for fast GPIO over a serial port
 * 
 */
public class ImperiumDevice extends RobotObjectFactory implements RobotObject, FactoryObject, UpdatableObject {

	private final RobotObjectModel model = new RobotObjectModel(this);
	private final ImperiumDeviceObjectFactory factory = new ImperiumDeviceObjectFactory(this);
	@Override
	public RobotObjectFactory getFactory(){
		return factory;
	}
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private InputStream is;
	private OutputStream os;
	private ImperiumHardwareConfiguration hardwareConfiguration;
	

	/**
	 * @param robot 
	 * @param params 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public ImperiumDevice(Robot robot, Map<String, String> params) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SerialInterface serial;
		if(params.containsKey("serialPortName"))
			serial = (SerialInterface) robot.getObject(params.get("serialPortName"));
		else
			serial = robot.getFactory().getSerialInterface(params.get("serialPort"), 115200);
		int maxUpdateRate = Integer.parseInt(params.get("maxUpdateRate"));

		Class<?> hardwareConfigurationClass = Class.forName(params.get("hardwareConfiguration"));
		Method method = hardwareConfigurationClass.getMethod("get");
		ImperiumHardwareConfiguration hardwareConfiguration = (ImperiumHardwareConfiguration) method.invoke(null);
		init(serial, hardwareConfiguration, maxUpdateRate);
	}

	/**
	 * @param robot 
	 * @param location 
	 * @param serialPort
	 *            the port over which the computer will interact with the device
	 * @param hardwareConfiguration
	 * @param maxUpdateRate the maximum number of input updates per second the device will send
	 */
	public ImperiumDevice(Robot robot, String location, ImperiumHardwareConfiguration hardwareConfiguration, int maxUpdateRate) {
		init(robot.getFactory().getSerialInterface(location, 115200), hardwareConfiguration, maxUpdateRate);
	}

	/**
	 * @param serialPort
	 *            the port over which the computer will interact with the device
	 * @param hardwareConfiguration
	 * @param maxUpdateRate the maximum number of input updates per second the device will send
	 */
	public ImperiumDevice(SerialInterface serialPort, ImperiumHardwareConfiguration hardwareConfiguration, int maxUpdateRate) {
		init(serialPort, hardwareConfiguration, maxUpdateRate);
	}


	/**
	 * @param is
	 * @param os
	 * @param hardwareConfiguration
	 * @param maxUpdateRate the maximum number of input updates per second the device will send
	 */
	public ImperiumDevice(InputStream is, OutputStream os, ImperiumHardwareConfiguration hardwareConfiguration, int maxUpdateRate) {
		init(is, os, hardwareConfiguration, maxUpdateRate);
	}

	private void init(SerialInterface serialPort, ImperiumHardwareConfiguration hardwareConfiguration, int maxUpdateRate) {
		init(serialPort.getInputStream(), serialPort.getOutputStream(), hardwareConfiguration, maxUpdateRate);
	}
	/**
	 * @param is
	 * @param os
	 * @param hardwareConfiguration
	 * @param maxUpdateRate the maximum number of input updates per second the device will send
	 */
	private void init(InputStream is, OutputStream os, ImperiumHardwareConfiguration hardwareConfiguration, int maxUpdateRate) {
		if (is == null)
			throw new RobotInitializationException(
					"Imperium Input Stream was null");
		if (os == null)
			throw new RobotInitializationException(
					"Imperium Output Stream was null");
		if (hardwareConfiguration == null)
			throw new RobotInitializationException(
					"Imperium Hardware configuration was null");
		this.is = is;
		this.os = os;
		this.hardwareConfiguration = hardwareConfiguration;
		this.maxUpdateRate = maxUpdateRate;
		state = ImperiumDeviceState.DISCONNECTED;
		new Thread(new ImperiumEventThread()).start();//TODO run this in a robot thread
		configure();
	}

	private ImperiumDeviceState state;

	/**
	 * @return the state of the connection to the device
	 */
	public ImperiumDeviceState getState() {
		return state;
	}

	protected void setState(ImperiumDeviceState state) {
		this.state = state;
		model.fireUpdateEvent();
	}

	private int maxUpdateRate = 0;

	/**
	 * @return the maximum rate the device will send input updates (updates per
	 *         second) 0 means no limit
	 */
	public int getMaxUpdateRate() {
		return maxUpdateRate;
	}

	/**
	 * @return the configuration of the hardware device that this object
	 *         communicates with
	 */
	public ImperiumHardwareConfiguration getHardwareConfiguration() {
		return hardwareConfiguration;
	}

	private final List<ImperiumDeviceObject> objects = new ArrayList<ImperiumDeviceObject>();

	/**
	 * @param object
	 *            the object to be added to this device
	 * @return the object id of the new object
	 */
	int register(ImperiumDeviceObject object) {
		for (int pinId = 0; pinId < object.getPinCount(); ++pinId) {
			object.validPin(pinId, hardwareConfiguration, hardwareConfiguration.getCapabilities(object.getPin(pinId)));
		}
		int objectId =  objects.size();
		

		synchronized (configureLock) {
			try {// catch any exception that occurs so that state can be
					// restored to disconnected
				ImperiumPacket configurePacket = new ImperiumPacket();
				configurePacket.setId(PacketIds.CONFIGURE_OBJECT);
				configurePacket.setDataLength(0);

				configurePacket.appendInteger(objectId, 1);
				configurePacket.appendInteger(object.getTypeId(), 1);
				configurePacket.appendInteger(object.getPinCount(), 1);
				for (int pin = 0; pin < object.getPinCount(); ++pin)
					configurePacket.appendInteger(object.getPin(pin), 1);
					
				sendPacket(configurePacket);
				
				if (configureLock.waitOn(1000)) {
					if (configureLock.isError())
						throw configureLock.getException();

					ImperiumPacket packet = configureLock.getReturnValue();
					packet.resetReadPosition();
					if (packet.getDataLength() != 2)
						throw new RobotInitializationException("Error configuring Imperium Device Object. Response configure packet was the wrong size");

					int typeId = packet.readInteger(1);
					int errorCode = packet.readInteger(1);
					if (typeId != object.getTypeId() || errorCode != 0)
						throw new RobotInitializationException(
								"Error configuring Imperium Device. Object "
										+ objectId
										+ " of type "
										+ object.getTypeId()
										+ " failed to configure");
				} else
					throw new RobotInitializationException(
							"Error configuring Imperium Device. The device did not respond within 1 second");
			} catch (Exception e) {
				setState(ImperiumDeviceState.DISCONNECTED);
				throw new RobotException(e);
			}
		}

		objects.add(object);
		model.fireUpdateEvent();
		return objectId;
	}

	private final ImperiumTaskExecutionLock<ImperiumPacket, RobotInitializationException> configureLock = new ImperiumTaskExecutionLock<ImperiumPacket, RobotInitializationException>();

	/**
	 * configure the device based on currently registered
	 * 
	 * @throws IOException
	 */
	private void configure() {
		RobotUtil.sleep(1500);//TODO: ping device until initialized

		synchronized (configureLock) {
			setState(ImperiumDeviceState.CONFIGURING);
			try {// catch any exception that occurs so that state can be
					// restored to disconnected
				ImperiumPacket configurePacket = new ImperiumPacket();
				configurePacket.setId(PacketIds.GLOBAL_CONFIGURE);
				configurePacket.setDataLength(0);
				configurePacket.appendInteger(maxUpdateRate, 2);

				configurePacket.appendInteger(objects.size(), 1);

				for (int i = 0; i < objects.size(); ++i) {
					ImperiumDeviceObject object = objects.get(i);
					configurePacket.appendInteger(object.getObjectId(), 1);
					configurePacket.appendInteger(object.getTypeId(), 1);
					configurePacket.appendInteger(object.getPinCount(), 1);
					for (int pin = 0; pin < object.getPinCount(); ++pin)
						configurePacket
								.appendInteger(object.getPin(pin), 1);
				}
				sendPacket(configurePacket);
				if (configureLock.waitOn(1000)) {
					if (configureLock.isError())
						throw configureLock.getException();

					ImperiumPacket packet = configureLock.getReturnValue();
					packet.resetReadPosition();
					if (packet.getDataLength() != objects.size() * 2)
						throw new RobotInitializationException(
								"Error configuring Imperium Device. Response configure packet was the wrong size");

					for (int i = 0; i < objects.size(); ++i) {
						ImperiumDeviceObject object = objects.get(i);
						int typeId = packet.readInteger(1);
						int errorCode = packet.readInteger(1);
						if (typeId != object.getTypeId() || errorCode != 0)
							throw new RobotInitializationException(
									"Error configuring Imperium Device. Object "
											+ object.getObjectId()
											+ " of type "
											+ object.getTypeId()
											+ " failed to configure");
					}

					setState(ImperiumDeviceState.CONNECTED);
				} else
					throw new RobotInitializationException(
							"Error configuring Imperium Device. The device did not respond within 1 second");
			} catch (Exception e) {
				setState(ImperiumDeviceState.DISCONNECTED);
				throw new RobotException(e);
			}
		}
	}
	
	
	

	private final ImperiumTaskExecutionLock<ImperiumPacket, RobotException> pingLock = new ImperiumTaskExecutionLock<ImperiumPacket, RobotException>();
	/**
	 * @return the time it took the device to respond
	 * will return -1 if the device failed to respond
	 */
	public int ping() {
		synchronized (pingLock) {
			ImperiumPacket packet = new ImperiumPacket();
			packet.setId(PacketIds.PING_REQUEST);
			packet.setDataLength(0);
			try {
				long time = System.currentTimeMillis();
				sendPacket(packet);
				if(pingLock.waitOn(500))
					return (int)(System.currentTimeMillis() - time);
				return -1;

			} catch (IOException e) {
				throw new RobotException("Error sending ping", e);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	

	private void inputValue(ImperiumPacket packet) {
		if(getState() != ImperiumDeviceState.CONNECTED)
			return;
		packet.resetReadPosition();
		int objectId = packet.readInteger(1);
		int value = packet.readInteger(4);
		objects.get(objectId).setValue(value);
	}
	
	private long lastBulkUpdate = 0;
	private int bulkUpdateCount = 0;
	private double bulkUpdateRate = 0;
	/**
	 * @return the rate at which the device is returning bulk updates (updated once a second)
	 */
	public double getBulkUpdateRate(){
		return bulkUpdateRate;
	}
	private void bulkValues(ImperiumPacket packet) {
		if(getState() != ImperiumDeviceState.CONNECTED)
			return;
		
		long time = System.currentTimeMillis();
		long diff = time-lastBulkUpdate;
		if(diff>=1000){
			bulkUpdateRate = bulkUpdateCount*1000d/diff;
			bulkUpdateCount = 0;
			lastBulkUpdate = time;
			model.fireUpdateEvent();
		}
		++bulkUpdateCount;
		
		packet.resetReadPosition();
		int objectCount = packet.readInteger(1);
		for(int i = 0; i<objectCount&&i<objects.size(); ++i){
			int value = packet.readInteger(4);
			objects.get(i).setValue(value);
		}
	}
	
	

	private void message(ImperiumPacket packet) {
		if(getState() != ImperiumDeviceState.CONNECTED)
			return;
		
		packet.resetReadPosition();
		int objectId = packet.readInteger(1);
		int numberSize = packet.readInteger(1);
		int numberCount = (packet.getDataLength()-2)/numberSize;
		long[] values = new long[numberCount];
		for(int i = 0; i<numberCount; ++i){
			values[i] = packet.readInteger(numberSize);
		}
		objects.get(objectId).message(values);
	}
	

	private void error(ImperiumPacket packet) {
		packet.resetReadPosition();
		int errorCode = packet.readInteger(1);
		throw new RobotException("Error occured on device: " + errorCode);
	}

	private long lastReceivedPacketUpdate = 0;
	private int packetReceivedCountTmp = 0;
	private int packetReceivedSizeTmp = 0;
	private int packetReceivedCount = 0;
	private int packetReceivedSize = 0;
	/**
	 * @return the number of packets received in the previous second
	 */
	public int getPacketReceivedCount() {
		return packetReceivedCount;
	}

	/**
	 * @return the number of bytes of packets received in the previous second
	 */
	public int getPacketReceivedSize() {
		return packetReceivedSize;
	}

	private class ImperiumEventThread implements Runnable {
		private ImperiumPacket packet = new ImperiumPacket();

		@Override
		public void run() {
			while (true) {
				try {
					if (is.available() > 0) {
						packet.read(is);
						//System.out.println("Received: "+packet);
						processInputPacket(packet);

						long time = System.currentTimeMillis();
						long diff = time-lastReceivedPacketUpdate;
						if(diff>=1000){
							packetReceivedSize = packetReceivedSizeTmp;
							packetReceivedCount = packetReceivedCountTmp;
							packetReceivedSizeTmp = 0;
							packetReceivedCountTmp = 0;
							lastReceivedPacketUpdate = time;
							model.fireUpdateEvent();
						}
						packetReceivedSizeTmp+=packet.size();
						++packetReceivedCountTmp;
					} else
						RobotUtil.sleep(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * process a packet from the device
	 * 
	 * @param packet
	 */
	private void processInputPacket(ImperiumPacket packet) {
		switch (packet.getId()) {
		case PacketIds.CONFIGURE_CONFIRM:
			configureLock.finish(packet.clone());//must clone for other thread to process
			break;
		case PacketIds.PING_RESPONSE:
			pingLock.finish(packet.clone());//must clone for other thread to process
			break;
		case PacketIds.ERROR_MESSAGE:
			error(packet);
			break;
		case PacketIds.INPUT_VALUE:
			inputValue(packet);
			break;
		case PacketIds.BULK_INPUT:
			bulkValues(packet);
			break;
		case PacketIds.MESSAGE:
			message(packet);
			break;
		default:
			System.out.println("Received unknown packet: " + packet + " at "
					+ System.currentTimeMillis());
			break;
		}
	}

	private long lastSentPacketUpdate = 0;
	private int packetSentCountTmp = 0;
	private int packetSentSizeTmp = 0;
	private int packetSentCount = 0;
	private int packetSentSize = 0;
	/**
	 * @return the number of packets sent in the previous second
	 */
	public int getPacketSentCount() {
		return packetSentCount;
	}

	/**
	 * @return the number of bytes of packets sent in the previous second
	 */
	public int getPacketSentSize() {
		return packetSentSize;
	}

	/**
	 * send a packet to the device
	 * 
	 * @param packet
	 * @throws IOException
	 */
	public synchronized void sendPacket(ImperiumPacket packet)
			throws IOException {
		if (packet != null){
			packet.write(os);

			long time = System.currentTimeMillis();
			long diff = time-lastSentPacketUpdate;
			if(diff>=1000){
				packetSentSize = packetSentSizeTmp;
				packetSentCount = packetSentCountTmp;
				packetSentSizeTmp = 0;
				packetSentCountTmp = 0;
				lastSentPacketUpdate = time;
				model.fireUpdateEvent();
			}
			packetSentSizeTmp+=packet.size();
			++packetSentCountTmp;
		}
		//System.out.println("Sent: "+packet);
	}

	/**
	 * @param object
	 *            the object whose value is being set
	 * @param value
	 *            the new value that will be sent to the device
	 */
	public void sendSetPacket(ImperiumDeviceObject object, int value) {
		ImperiumPacket packet = new ImperiumPacket();
		packet.setId(PacketIds.SET_VALUE);
		packet.setDataLength(0);
		packet.appendInteger(object.getObjectId(), 1);
		packet.appendInteger(value, 2);
		try {
			sendPacket(packet);
		} catch (IOException e) {
			throw new RobotException("Error setting output of " + object, e);
		}
	}

	/**
	 * Send a message to an object on the device
	 * @param object the object this message is for
	 * @param data the message data
	 * @param off offset from the start
	 * @param length number of bytes to write
	 */
	public void sendMessagePacket(ImperiumDeviceObject object, byte[] data, int off, int length) {
		ImperiumPacket packet = new ImperiumPacket();
		packet.setId(PacketIds.MESSAGE);
		packet.setDataLength(0);
		packet.appendInteger(object.getObjectId(), 1);
		packet.appendInteger(length, 1);
		for(int i = 0; i<length; ++i)
			packet.appendInteger(data[off+i], 1);
		try {
			sendPacket(packet);
		} catch (IOException e) {
			throw new RobotException("Error setting output of " + object, e);
		}
	}

}
