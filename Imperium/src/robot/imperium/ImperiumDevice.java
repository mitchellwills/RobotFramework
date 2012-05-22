package robot.imperium;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.imperium.packet.ImperiumPacket;
import robot.imperium.packet.PacketIds;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;
import robot.io.factory.FactoryObject;
import robot.io.factory.RobotObjectFactory;
import robot.io.serial.SerialInterface;
import robot.thread.PeriodicRobotThread;
import robot.thread.RobotThread;
import robot.util.RobotUtil;

/**
 * 
 * A serial device that that allows for fast GPIO over a serial port
 * 
 * @author Mitchell
 * 
 */
public abstract class ImperiumDevice implements RobotObject, FactoryObject,
		UpdatableObject {

	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private final SerialInterface serialPort;

	/**
	 * @param serialPort
	 *            the port over which the computer will interact with the device
	 * @param maxUpdateRate
	 *            the maximum number of input updates per second the device will
	 *            send
	 */
	public ImperiumDevice(SerialInterface serialPort, int maxUpdateRate) {
		if (serialPort == null)
			throw new RobotInitializationException("Serial Port was null");
		this.serialPort = serialPort;
		this.maxUpdateRate = maxUpdateRate;
		state = ImperiumDeviceState.DISCONNECTED;
		new ImperiumEventThread().start();
		new ImperiumOutputThread().start();
		configure();
	}

	private final ImperiumDeviceObjectFactory factory = new ImperiumDeviceObjectFactory(this);

	@Override
	public RobotObjectFactory getFactory() {
		return factory;
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

	private final ImperiumTaskExecutionLock<ImperiumPacket, RobotException> pingLock = new ImperiumTaskExecutionLock<ImperiumPacket, RobotException>();

	/**
	 * @return the time it took the device to respond will return -1 if the
	 *         device failed to respond
	 */
	public int ping() {
		synchronized (pingLock) {
			ImperiumPacket packet = new ImperiumPacket();
			packet.setId(PacketIds.PING_REQUEST);
			packet.setDataLength(0);
			try {
				long time = System.currentTimeMillis();
				sendPacket(packet);
				if (pingLock.waitOn(500))
					return (int) (System.currentTimeMillis() - time);
				return -1;

			} catch (IOException e) {
				throw new RobotException("Error sending ping", e);
			}
		}
	}

	private final ImperiumTaskExecutionLock<ImperiumPacket, RobotInitializationException> configureLock = new ImperiumTaskExecutionLock<ImperiumPacket, RobotInitializationException>();

	/**
	 * configure the device based on currently registered
	 * 
	 * @throws IOException
	 */
	private void configure() {
		RobotUtil.sleep(1500);// TODO: ping device until initialized

		synchronized (configureLock) {
			setState(ImperiumDeviceState.CONFIGURING);
			try {// catch any exception that occurs so that state can be restored to disconnected
				ImperiumPacket configurePacket = new ImperiumPacket();
				configurePacket.setId(PacketIds.GLOBAL_CONFIGURE_REQUEST);
				configurePacket.appendInteger(maxUpdateRate, 2);

				sendPacket(configurePacket);
				if (configureLock.waitOn(1000)) {
					if (configureLock.isError())
						throw configureLock.getException();

					ImperiumPacket packet = configureLock.getReturnValue();
					packet.resetReadPosition();

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

	private final List<ImperiumDeviceObject> objects = new ArrayList<ImperiumDeviceObject>();

	int configure(ImperiumDeviceObject object) {
		int objectId;
		synchronized (configureLock) {
			try {
				objectId = objects.size();
				ImperiumPacket configurePacket = new ImperiumPacket();
				configurePacket.setId(PacketIds.OBJECT_CONFIGURE_REQUEST);
				configurePacket.setDataLength(0);

				configurePacket.appendInteger(objectId, 1);
				configurePacket.appendInteger(object.getTypeId(), 1);
				object.appendConfiguration(configurePacket);

				sendPacket(configurePacket);
			} catch (Exception e) {
				setState(ImperiumDeviceState.DISCONNECTED);
				throw new RobotException(e);
			}

			if (configureLock.waitOn(1000)) {
				if (configureLock.isError())
					throw configureLock.getException();

				ImperiumPacket packet = configureLock.getReturnValue();
				packet.resetReadPosition();
				if (packet.getDataLength() != 4)
					throw new RobotInitializationException("Error configuring Imperium Device Object. Response configure packet was the wrong size");

				if (packet.readInteger(1) != objectId)
					throw new RobotInitializationException("Error configuring Imperium Device Object. Received incorrect object id");

				if (packet.readInteger(1) != object.getTypeId())
					throw new RobotInitializationException("Error configuring Imperium Device Object. Received incorrect object type id");

				if (packet.readInteger(1) != object.getInputSize())
					throw new RobotInitializationException("Error configuring Imperium Device Object. Received incorrect object input size");

				if (packet.readInteger(1) != object.getOutputSize())
					throw new RobotInitializationException("Error configuring Imperium Device Object. Received incorrect object output size");

			} else
				throw new RobotInitializationException("Error configuring Imperium Device. The device did not respond within 1 second");

			objects.add(object);
			model.fireUpdateEvent();
		}

		return objectId;
	}

	private void error(ImperiumPacket packet) {
		packet.resetReadPosition();
		int errorCode = packet.readInteger(1);
		throw new RobotException("Error occured on device: " + errorCode + " - "+Arrays.toString(Arrays.copyOfRange(packet.getData(), 1, packet.getDataLength())));
	}

	private class ImperiumEventThread extends RobotThread {
		public ImperiumEventThread() {
			super("Imperium Event Thread");
		}

		private ImperiumPacket packet = new ImperiumPacket();

		@Override
		public void run() {
			InputStream is = serialPort.getInputStream();
			while (true) {
				try {
					if (is.available() > 0) {
						packet.read(is);
						//System.out.println("Received: "+packet);
						processInputPacket(packet);
					} else
						RobotUtil.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	private class ImperiumOutputThread extends PeriodicRobotThread {
		private ImperiumPacket packet = new ImperiumPacket();
		public ImperiumOutputThread() {
			super("Imperium Output Thread", 1000/maxUpdateRate);
			packet.setId(PacketIds.BULK_OUTPUT_VALUE);
		}

		@Override
		public void periodic() {
			if(objects.size()==0)
				return;
			try {
				packet.setDataLength(0);
				for(ImperiumDeviceObject object:objects)
					object.appendSetValue(packet);
				sendPacket(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * process a packet from the device
	 * 
	 * @param packet
	 */
	private void processInputPacket(ImperiumPacket packet) {
		packet.resetReadPosition();
		switch (packet.getId()) {
		case PacketIds.DEVICE_BOOT:
			System.out.println("Imperium Device Booted");
			break;
		case PacketIds.PING_RESPONSE:
			pingLock.finish(packet.clone());
			break;
		case PacketIds.GLOBAL_CONFIGURE_RESPONSE:
			configureLock.finish(packet.clone());
			break;
		case PacketIds.OBJECT_CONFIGURE_RESPONSE:
			configureLock.finish(packet.clone());
			break;
		case PacketIds.BULK_INPUT_VALUE:
			for(ImperiumDeviceObject object:objects)
				object.readValue(packet);
			break;
		case PacketIds.ERROR_MESSAGE:
			error(packet);
			break;
		default:
			throw new RobotException("Received unknown packet: " + packet
					+ " at " + System.currentTimeMillis());
		}
	}

	/**
	 * send a packet to the device
	 * 
	 * @param packet
	 * @throws IOException
	 */
	public synchronized void sendPacket(ImperiumPacket packet)
			throws IOException {
		if (packet != null) {
			packet.write(serialPort.getOutputStream());
			//System.out.println("Sent: "+packet);
		}
	}
	
	
	
	
	
	
	
	private final Map<String, DeviceFeature> features = new HashMap<String, DeviceFeature>();
	private final Map<String, String> extraFeatureNames = new HashMap<String, String>();
	protected void addFeature(DeviceFeature feature){
		if(features.containsKey(feature.getName()) || extraFeatureNames.containsKey(feature.getName()))
			throw new RobotInitializationException("Device already contains feature "+feature.getName());
		features.put(feature.getName(), feature);
	}
	protected void addExtraFeatureName(String newName, String targetName){
		if(!features.containsKey(targetName) && !extraFeatureNames.containsKey(targetName))
			throw new RobotInitializationException("Device does not contain target feature "+targetName);
		if(features.containsKey(newName) || extraFeatureNames.containsKey(newName))
			throw new RobotInitializationException("Device already contains feature "+newName);
		extraFeatureNames.put(newName, targetName);
	}
	protected DeviceFeature getFeature(String name){
		String evaluatedName = name;
		while(extraFeatureNames.containsKey(evaluatedName))
			evaluatedName = extraFeatureNames.get(evaluatedName);
		return features.get(evaluatedName);
	}
	public DeviceFeature acquireFeature(String name, ImperiumDeviceObject newOwner, DeviceFeatureCapability... requiredCapabilities){
		DeviceFeature feature = getFeature(name);
		feature.acquire(newOwner, requiredCapabilities);
		return feature;
	}

}
