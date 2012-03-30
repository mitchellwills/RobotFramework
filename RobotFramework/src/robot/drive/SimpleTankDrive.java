package robot.drive;

import robot.io.speedcontroller.SpeedController;

/**
 * @author Mitchell
 * 
 *         A simple tank drive system that has 2 or 4 wheels
 * 
 */
public class SimpleTankDrive implements TankDrive {
	private SpeedController frontLeftDrive;
	private SpeedController backLeftDrive;
	private SpeedController frontRightDrive;
	private SpeedController backRightDrive;
	private boolean invertLeft;
	private boolean invertRight;

	/**
	 * @param leftDrive
	 *            the left drive motor
	 * @param rightDrive
	 *            the right drive motor
	 * @param invertLeft If the left motor should be inverted
	 * @param invertRight If the right morot should be inverted
	 */
	public SimpleTankDrive(SpeedController leftDrive,
			SpeedController rightDrive, boolean invertLeft, boolean invertRight) {
		this(null, leftDrive, null, rightDrive, invertLeft, invertRight);
	}

	/**
	 * @param frontLeftDrive
	 * @param backLeftDrive
	 * @param frontRightDrive
	 * @param backRightDrive
	 * @param invertLeft 
	 * @param invertRight 
	 */
	public SimpleTankDrive(SpeedController frontLeftDrive,
			SpeedController backLeftDrive, SpeedController frontRightDrive,
			SpeedController backRightDrive, boolean invertLeft, boolean invertRight) {
		this.frontLeftDrive = frontLeftDrive;
		this.backLeftDrive = backLeftDrive;
		this.frontRightDrive = frontRightDrive;
		this.backRightDrive = backRightDrive;
		this.invertLeft = invertLeft;
		this.invertRight = invertRight;
	}

	@Override
	public void tankDrive(double left, double right) {
		double invertedLeft;
		double invertedRight;
		if(invertLeft)
			invertedLeft = -left;
		else
			invertedLeft = left;
		if(invertRight)
			invertedRight = -right;
		else
			invertedRight = right;
		
		
		if (frontLeftDrive != null)
			frontLeftDrive.set(invertedLeft);
		if (backLeftDrive != null)
			backLeftDrive.set(invertedLeft);

		if (frontRightDrive != null)
			frontRightDrive.set(invertedRight);
		if (backRightDrive != null)
			backRightDrive.set(invertedRight);
	}

}
