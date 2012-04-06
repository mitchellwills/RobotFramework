package robot.imperium;

/**
 * @author Mitchell
 * 
 *         Represents the state of the connection to the device
 * 
 */
public enum ImperiumDeviceState {
	/**
	 * the connection to the device has been closed or the device has not been
	 * configured
	 */
	DISCONNECTED,
	/**
	 * the configuration has been sent to the robot and waiting for a success
	 * packet
	 */
	CONFIGURING,
	/**
	 * the device has been successfully configured
	 */
	CONNECTED;
}
