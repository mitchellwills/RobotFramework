package robot.io.host;


/**
 * @author Mitchell
 * 
 * Represents that battery of a host device
 *
 */
public interface HostBattery {
	/**
	 * @return the percent of capacity the battery is currently at (0.0-1.0)
	 */
	public double getBatteryLeftPercent();
	/**
	 * @return the time in seconds remaining of the battery's life
	 * -1 if unknown
	 */
	public int getTimeRemaining();
	/**
	 * @return true if the device is plugged into a power source
	 */
	public boolean isPluggedIn();
}
