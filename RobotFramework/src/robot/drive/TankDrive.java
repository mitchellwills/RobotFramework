package robot.drive;

/**
 * @author Mitchell
 * 
 * A drive system that can drive using tank drive
 *
 */
public interface TankDrive extends RobotDrive{
	/**
	 * @param left speed of the left motors
	 * @param right speed of the right motors
	 */
	public void tankDrive(double left, double right);
}
