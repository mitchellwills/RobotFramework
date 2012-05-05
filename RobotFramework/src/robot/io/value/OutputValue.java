package robot.io.value;


/**
 * @author Mitchell
 * 
 * An output that has a single value associated with it
 *
 */
public interface OutputValue extends InputValue{
	/**
	 * Set the output's value
	 * @param value the new value
	 */
	public void setValue(double value);
}
