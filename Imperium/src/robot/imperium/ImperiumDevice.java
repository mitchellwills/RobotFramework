package robot.imperium;

import java.io.*;
import java.util.*;

import robot.boostrap.partial.*;
import robot.error.*;
import robot.imperium.packet.*;
import robot.imperium.resources.*;
import robot.io.*;
import robot.io.serial.*;
import robot.thread.*;
import robot.util.*;

import com.google.inject.*;

/**
 * 
 * A serial device that that allows for fast GPIO over a serial port
 * 
 * @author Mitchell
 * 
 */
public abstract class ImperiumDevice implements RobotObject, UpdatableObject, BuilderContext {
	public static final String PARAM_SERIAL_INTERFACE = "serialInterface";
	public static final String PARAM_MAX_UPDATE_RATE = "maxUpdateRate";

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
	 * @param threadFactory 
	 * @param serialPort
	 *            the port over which the computer will interact with the device
	 * @param maxUpdateRate
	 *            the maximum number of input updates per second the device will
	 *            send
	 */
	public ImperiumDevice(RobotThreadFactory threadFactory, SerialInterface serialPort, int maxUpdateRate) {
		if (serialPort == null)
			throw new RobotInitializationException("Serial Port was null");
		this.serialPort = serialPort;
		this.maxUpdateRate = maxUpdateRate;
		state = ImperiumDeviceState.DISCONNECTED;
		new ImperiumEventThread(threadFactory).start();
		new ImperiumOutputThread(threadFactory).start();
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
		public ImperiumEventThread(RobotThreadFactory threadFactory) {
			super(threadFactory, "Imperium Event Thread");
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
		public ImperiumOutputThread(RobotThreadFactory threadFactory) {
			super(threadFactory, "Imperium Output Thread", 1000/maxUpdateRate);
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
	
	
	
	
	
	
	
	private final Map<String, DeviceResource> resources = new HashMap<String, DeviceResource>();
	private final Map<String, String> extraResourceNames = new HashMap<String, String>();
	protected DeviceResource addResource(DeviceResource resource){
		if(resources.containsKey(resource.getName()) || extraResourceNames.containsKey(resource.getName()))
			throw new RobotInitializationException("Device already contains resource "+resource.getName());
		resources.put(resource.getName(), resource);
		return resource;
	}
	protected void addExtraResourceName(String newName, String targetName){
		if(!resources.containsKey(targetName) && !extraResourceNames.containsKey(targetName))
			throw new RobotInitializationException("Device does not contain target resource "+targetName);
		if(resources.containsKey(newName) || extraResourceNames.containsKey(newName))
			throw new RobotInitializationException("Device already contains resource "+newName);
		extraResourceNames.put(newName, targetName);
	}
	protected DeviceResource getResource(String name){
		String evaluatedName = name;
		while(extraResourceNames.containsKey(evaluatedName))
			evaluatedName = extraResourceNames.get(evaluatedName);
		return resources.get(evaluatedName);
	}
	public DeviceResource acquireResource(String name, ImperiumDeviceObject newOwner, DeviceResourceState requiredState){
		DeviceResource resource = getResource(name);
		resource.acquire(newOwner, requiredState);
		return resource;
	}

	
	
	
	
	
	@Override
	public Iterable<? extends Module> getInjectionModules() {
		return Collections.singleton(new ImperiumModule(this));
	}
	@Override
	public Iterable<? extends PartialModule> getPartialInjectionModules() {
		return Collections.singleton(new ImperiumPartialModule());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> getPartialParameters() {
		return (Map)Collections.singletonMap(ImperiumDeviceObject.PARAM_DEVICE, this);
	}
}
