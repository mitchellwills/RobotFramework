package robot.control;

/**
 * @author Mitchell
 * 
 * the output for a control loop
 *
 */
public interface ControlLoopOutput {
	/**
	 * @param value the output value from a control loop
	 */
	public void set(double value);
}
