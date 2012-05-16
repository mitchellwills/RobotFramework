package robot.imperium;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import robot.Robot;
import robot.error.RobotException;
import robot.error.RobotInitializationException;
import robot.imperium.hardware.ImperiumHardwareConfiguration;
import robot.imperium.packet.ImperiumPacket;
import robot.imperium.packet.PacketIds;
import robot.io.RobotObject;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;
import robot.io.factory.FactoryObject;
import robot.io.factory.RobotObjectFactory;
import robot.io.serial.SerialInterface;
import robot.io.serial.SerialUtils;
import robot.thread.RobotThread;
import robot.util.RobotUtil;

/**
 * 
 * A serial device that that allows for fast GPIO over a serial port
 * 
 * @author Mitchell
 * 
 */
public class ImperiumDevice extends RobotObjectFactory implements RobotObject,
		FactoryObject, UpdatableObject {

	/**
	 * name of the parameter in factories param map corresponding to the imperium devices serial port
	 */
	public static final String PARAM_SERIAL_PORT = "serialPort";

	/**
	 * name of the parameter in factories param map corresponding to the maximum number of updates per second
	 */
	public static final String PARAM_MAX_UPDATE_RATE = "maxUpdateRate";

	/**
	 * name of the parameter in factories param map corresponding to the hardware configuration of the device
	 */
	public static final String PARAM_HARDWARE_CONFIGURATION = "hardwareConfiguration";

	private final RobotObjectModel model = new RobotObjectModel(this);
	private final ImperiumDeviceObjectFactory factory = new ImperiumDeviceObjectFactory(
			this);

	@Override
	public RobotObjectFactory getFactory() {
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
	 * @param params
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public ImperiumDevice(Map<String, String> params)
			throws NoSuchMethodException, SecurityException,
			ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		SerialInterface serial = SerialUtils.getSerialInterface(Robot.getInstance().getFactory(), getParam(params, PARAM_SERIAL_PORT), 115200);

		int maxUpdateRate = RobotObjectFactory.getIntParam(params,
				PARAM_MAX_UPDATE_RATE);

		Class<?> hardwareConfigurationClass = Class.forName(getParam(params,
				PARAM_HARDWARE_CONFIGURATION));
		Method method = hardwareConfigurationClass.getMethod("get");
		ImperiumHardwareConfiguration hardwareConfiguration = (ImperiumHardwareConfiguration) method
				.invoke(null);
		init(serial, hardwareConfiguration, maxUpdateRate);
	}

	/**
	 * @param serialPort
	 *            the port over which the computer will interact with the device
	 * @param hardwareConfiguration
	 * @param maxUpdateRate
	 *            the maximum number of input updates per second the device will
	 *            send
	 */
	public ImperiumDevice(SerialInterface serialPort,
			ImperiumHardwareConfiguration hardwareConfiguration,
			int maxUpdateRate) {
		init(serialPort, hardwareConfiguration, maxUpdateRate);
	}

	/**
	 * @param is
	 * @param os
	 * @param hardwareConfiguration
	 * @param maxUpdateRate
	 *            the maximum number of input updates per second the device will
	 *            send
	 */
	public ImperiumDevice(InputStream is, OutputStream os,
			ImperiumHardwareConfiguration hardwareConfiguration,
			int maxUpdateRate) {
		init(is, os, hardwareConfiguration, maxUpdateRate);
	}

	private void init(SerialInterface serialPort,
			ImperiumHardwareConfiguration hardwareConfiguration,
			int maxUpdateRate) {
		init(serialPort.getInputStream(), serialPort.getOutputStream(),
				hardwareConfiguration, maxUpdateRate);
	}

	/**
	 * @param is
	 * @param os
	 * @param hardwareConfiguration
	 * @param maxUpdateRate
	 *            the maximum number of input updates per second the device will
	 *            send
	 */
	private void init(InputStream is, OutputStream os,
			ImperiumHardwareConfiguration hardwareConfiguration,
			int maxUpdateRate) {
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
		new ImperiumEventThread().start();
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

	private void error(ImperiumPacket packet) {
		packet.resetReadPosition();
		int errorCode = packet.readInteger(1);
		throw new RobotException("Error occured on device: " + errorCode+" - "+Arrays.toString(packet.getData()));
	}


	private class ImperiumEventThread extends RobotThread {
		public ImperiumEventThread() {
			super("Imperium Event Thread");
		}

		private ImperiumPacket packet = new ImperiumPacket();

		@Override
		public void run() {
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

	/**
	 * process a packet from the device
	 * 
	 * @param packet
	 */
	private void processInputPacket(ImperiumPacket packet) {
		switch (packet.getId()) {
		case PacketIds.DEVICE_BOOT:
			System.out.println("Imperium Device Booted");
			break;
		case PacketIds.PING_RESPONSE:
			pingLock.finish(packet.clone());// must clone for other thread to process
			break;
		case PacketIds.ERROR_MESSAGE:
			error(packet);
			break;
		default:
			throw new RobotException("Received unknown packet: " + packet + " at "
					+ System.currentTimeMillis());
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
			packet.write(os);
			//System.out.println("Sent: "+packet);
		}
	}

}
