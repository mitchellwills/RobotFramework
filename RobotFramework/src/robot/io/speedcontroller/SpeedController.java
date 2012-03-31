package robot.io.speedcontroller;

import robot.io.Output;
import robot.pid.PIDOutput;


/**
 * @author Mitchell
 * 
 * A speed controller
 *
 */
public interface SpeedController extends Output, PIDOutput {
	/**
	 * set the output of the speed controller
	 * @param value the new speed (between -1.0 and 1.0)
	 */
	public void set(double value);
	/**
	 * @return the output speed (between -1.0 and 1.0)
	 */
	public double get();
}
