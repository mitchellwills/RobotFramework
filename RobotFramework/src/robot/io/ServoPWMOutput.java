package robot.io;


/**
 * @author Mitchell
 * 
 * An output that takes a value in milliseconds that is the length of the pulses
 *
 */
public interface ServoPWMOutput extends Output {
	/**
	 * @param ms the length of the pulses in milliseconds
	 * -1 will disable the pulse output
	 */
	public void set(int ms);
	/**
	 * @return the current pulse length in milliseconds
	 * -1 means the pulse output is disabled
	 */
	public int get();
}
