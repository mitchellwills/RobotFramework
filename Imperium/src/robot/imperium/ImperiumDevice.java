package robot.imperium;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.imperium.hardware.HardwareConfiguration;
import robot.imperium.packet.ImperiumPacket;
import robot.imperium.packet.PacketIds;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;
import robot.io.serial.SerialInterface;
import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 *         A serial device that that allows for fast GPIO
 * 
 */
public class ImperiumDevice implements RobotObject, UpdatableObject<ImperiumDevice> {

	private final RobotObjectModel<ImperiumDevice> model = new RobotObjectModel<ImperiumDevice>(this);

	@Override
	public void addUpdateListener(RobotObjectListener<ImperiumDevice> listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener<ImperiumDevice> listener) {
		model.removeUpdateListener(listener);
	}

	private final InputStream is;
	private final OutputStream os;
	private final HardwareConfiguration hardwareConfiguration;

	/**
	 * @param serialPort
	 *            the port over which the computer will interact with the device
	 * @param hardwareConfiguration
	 */
	public ImperiumDevice(SerialInterface serialPort,
			HardwareConfiguration hardwareConfiguration) {
		this(serialPort.getInputStream(), serialPort.getOutputStream(),
				hardwareConfiguration);
	}

	/**
	 * @param is
	 * @param os
	 * @param hardwareConfiguration
	 */
	public ImperiumDevice(InputStream is, OutputStream os,
			HardwareConfiguration hardwareConfiguration) {
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
		state = ImperiumDeviceState.DISCONNECTED;
		new Thread(new ImperiumEventThread()).start();
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
	 * Set the maximum number of input updates per second the device will send
	 * 
	 * @param rate
	 *            in updates per second
	 */
	public void setMaxUpdateRate(int rate) {
		if (state != ImperiumDeviceState.DISCONNECTED)
			throw new RobotInitializationException(
					"Cannot set update rate while not disconnected from device");
		this.maxUpdateRate = rate;
		model.fireUpdateEvent();
	}

	/**
	 * @return the configuration of the hardware device that this object
	 *         communicates with
	 */
	public HardwareConfiguration getHardwareConfiguration() {
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
			if (!hardwareConfiguration.supports(object.getPin(pinId),
					object.getRequiredCapabilities(pinId)))
				throw new RobotInitializationException("The "
						+ hardwareConfiguration.getName()
						+ " does not support "
						+ object.getRequiredCapabilities(pinId) + " on pin "
						+ object.getPin(pinId));
		}
		objects.add(object);
		model.fireUpdateEvent();
		return objects.size() - 1;
	}

	private final ImperiumTaskExecutionLock<ImperiumPacket, RobotInitializationException> configureLock = new ImperiumTaskExecutionLock<>();

	/**
	 * configure the device based on currently registered
	 * 
	 * @throws IOException
	 */
	public void configure() {
		RobotUtil.sleep(1500);// TODO: wait for device to initialize

		try {
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

						for (ImperiumDeviceObject object : objects)
							object.initialize();

						setState(ImperiumDeviceState.CONNECTED);
						ping();
					} else
						throw new RobotInitializationException(
								"Error configuring Imperium Device. The device did not respond within 1 second");
				} catch (Exception e) {
					setState(ImperiumDeviceState.DISCONNECTED);
					throw e;
				}
			}
		} catch (IOException e) {
			throw new RobotInitializationException(
					"Error configuring Imperium Device", e);
		}
	}
	
	
	

	private final ImperiumTaskExecutionLock<ImperiumPacket, RobotException> pingLock = new ImperiumTaskExecutionLock<>();
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
		packet.resetReadPosition();
		int objectId = packet.readInteger(1);
		int value = packet.readInteger(4);
		ImperiumDeviceObject object = objects.get(objectId);
		object.setValue(value);
	}

	private void error(ImperiumPacket packet) {
		packet.resetReadPosition();
		int errorCode = packet.readInteger(1);
		throw new RobotException("Error occured on device: " + errorCode);
	}

	private class ImperiumEventThread implements Runnable {
		private ImperiumPacket packet = new ImperiumPacket();

		@Override
		public void run() {
			while (true) {
				try {
					if (is.available() > 0) {
						packet.read(is);
						processInputPacket(packet);
						//System.out.println("Received: "+packet);
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
			configureLock.finish(packet);
			break;
		case PacketIds.PING_RESPONSE:
			pingLock.finish(packet);
			break;
		case PacketIds.ERROR_MESSAGE:
			error(packet);
			break;
		case PacketIds.INPUT_VALUE:
			inputValue(packet);
			break;
		default:
			System.out.println("Received unknown packet: " + packet + " at "
					+ System.currentTimeMillis());
			break;
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
		if (packet != null)
			packet.write(os);
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

}
