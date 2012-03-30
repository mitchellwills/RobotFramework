package robot.pid;

/**
 * @author Mitchell
 * 
 * the input for a PID loop
 *
 */
public interface PIDInput {
	/**
	 * @return the input value for a PID loop
	 */
	public double getPIDInput();
}
