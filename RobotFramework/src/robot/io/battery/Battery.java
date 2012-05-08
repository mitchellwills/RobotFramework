package robot.io.battery;


/**
 * @author Mitchell
 * 
 * Represents a battery
 *
 */
public interface Battery {
	/**
	 * @return the percent of capacity the battery is currently at (0.0-1.0)
	 */
	public double getBatteryLeftPercent();
}
