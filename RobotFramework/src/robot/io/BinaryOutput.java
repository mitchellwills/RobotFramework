package robot.io;


/**
 * @author Mitchell
 * 
 * an output that can be either active or inactive
 *
 */
public interface BinaryOutput extends Output {
	/**
	 * Set the state of the output
	 * @param value true if the output should be active
	 */
	public void set(boolean value);
	/**
	 * @return the current state of the output
	 */
	public boolean get();
}
