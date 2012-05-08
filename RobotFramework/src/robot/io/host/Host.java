package robot.io.host;

import robot.io.Input;
import robot.io.Output;
import robot.io.battery.Battery;

/**
 * @author Mitchell
 * 
 * Represents the host machine that the robot program is running on
 *
 */
public interface Host extends Input, Output {
	/**
	 * @return the battery that the host is running off of
	 */
	public Battery getBattery();
}
