package robot.pid;

/**
 * @author Mitchell
 * 
 * An object that takes a PIDInput and PIDOutput to execute a PID loop
 *
 */
public interface PIDLoop {
	/**
	 * Set the setpoint of of the loop
	 * @param setpoint
	 */
	public void setSetpoint(double setpoint);
	/**
	 * @return the setpoint of the loop
	 */
	public double getSetpoint();
	/**
	 * @return the actual position of the input
	 */
	public double getPosition();
}
