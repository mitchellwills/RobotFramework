package robot.drive;

import robot.util.RobotUtil;

/**
 * A collection of utility functions to assist with driving
 * 
 * @author Mitchell
 *
 */
public class DriveUtil {
	/**
	 * Cause a tank drive to drive using arcade drive
	 * @param tankDrive
	 * @param speed (-1.0 - 1.0)
	 * @param spin (-1.0 - 1.0)
	 */
	public static void arcadeDrive(TankDrive tankDrive, double speed, double spin){
		double left = speed + spin;
		double right = speed - spin;
		
		tankDrive.tankDrive(RobotUtil.limit(left, -1.0, 1.0), RobotUtil.limit(right, -1.0, 1.0));
	}
}
