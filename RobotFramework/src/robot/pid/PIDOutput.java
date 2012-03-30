package robot.pid;

/**
 * @author Mitchell
 * 
 * the output for a PID loop
 *
 */
public interface PIDOutput {
	/**
	 * @param value the output value from a PID loop
	 */
	public void setPIDOutput(double value);
}
