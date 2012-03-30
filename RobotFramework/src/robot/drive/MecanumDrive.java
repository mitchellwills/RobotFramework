package robot.drive;

/**
 * @author Mitchell
 *
 */
public interface MecanumDrive extends RobotDrive, TankDrive{
	/**
	 * @param x speed moving in the x direction
	 * @param y speed moving in the y direction
	 * @param spin speed to spin at
	 */
	public void driveMecanum(double x, double y, double spin);
}
