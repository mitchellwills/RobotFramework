package robot.control;

import robot.io.*;

/**
 * @author Mitchell
 * 
 * A control loop that will bring a system to a given setpoint
 *
 */
public interface ControlLoop extends RobotObject{
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
