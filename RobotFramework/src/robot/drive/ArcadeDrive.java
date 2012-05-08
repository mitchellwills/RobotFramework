package robot.drive;

/**
 * @author Mitchell
 * 
 * A drive system that can drive using arcade drive
 *
 */
public interface ArcadeDrive extends RobotDrive{
	/**
	 * @param speed 
	 * @param spin 
	 */
	public void arcadeDrive(double speed, double spin);
}
