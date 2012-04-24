package robot.io.servo;

import robot.io.OutputValue;

/**
 * @author Mitchell
 * 
 * Represents a servo that can hold an angle that 
 *
 */
public interface Servo extends OutputValue {
	
	/**
	 * Set the angle the servo is at
	 * @param angle
	 */
	public void setAngle(double angle);
	
	/**
	 * @return the current angle the servo is at
	 */
	public double getAngle();
	
	/**
	 * @return the minimum angle the servo can be at
	 */
	public double getMinAngle();
	
	/**
	 * @return the maximum angle the servo is at
	 */
	public double getMaxAngle();

}
