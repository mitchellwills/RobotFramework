package robot.drive;

import java.awt.*;

import robot.io.speedcontroller.*;
import robot.math.*;

/**
 * A simple mecanum drive system that has 4 wheels
 * 
 * 
 * @author Mitchell
 * @author Brendan
 * 
 * 
 */
public class VTCMecanumDrive implements TankDrive, ArcadeDrive, MecanumDrive {
	private static final LinearVector[] wheelVectors = new LinearVector[4];// the unit vector of what each wheel is capable of doing
	static{
		wheelVectors[0] = new LinearVector(1, 1).getUnitVector(); // left front
		wheelVectors[1] = new LinearVector(-1, 1).getUnitVector(); // right front
		wheelVectors[2] = new LinearVector(-1, 1).getUnitVector();// back left
		wheelVectors[3] = new LinearVector(1, 1).getUnitVector();// back right
	}
	
	
	private SpeedController frontLeftDrive;
	private SpeedController backLeftDrive;
	private SpeedController frontRightDrive;
	private SpeedController backRightDrive;
	private boolean invertLeft;
	private boolean invertRight;

	private Point frontRightPoint;
	private Point frontLeftPoint;
	private Point backLeftPoint;
	private Point backRightPoint;

	private Point VTC;

	private LinearVector[] rotationVectors;
	private LinearVector directionVector;

	/**
	 * @param frontLeftDrive
	 * @param backLeftDrive
	 * @param frontRightDrive
	 * @param backRightDrive
	 * @param invertLeft
	 * @param invertRight
	 * @param wheelXDistance 
	 * @param wheelYDistance 
	 */
	public VTCMecanumDrive(SpeedController frontLeftDrive, SpeedController backLeftDrive, SpeedController frontRightDrive, SpeedController backRightDrive,
			boolean invertLeft, boolean invertRight,
			int wheelXDistance, int wheelYDistance) {
		this.frontLeftDrive = frontLeftDrive;
		this.backLeftDrive = backLeftDrive;
		this.frontRightDrive = frontRightDrive;
		this.backRightDrive = backRightDrive;
		this.invertLeft = invertLeft;
		this.invertRight = invertRight;


		VTC = new Point(0, 0);
		rotationVectors = new LinearVector[4];
		for (int i = 0; i < 4; i++) {
			rotationVectors[i] = new LinearVector();
		}
		
		directionVector = new LinearVector();

		frontLeftPoint = new Point(-wheelXDistance, wheelYDistance);
		frontRightPoint = new Point(wheelXDistance, wheelYDistance);
		backLeftPoint = new Point(-wheelXDistance, -wheelYDistance);
		backRightPoint = new Point(wheelXDistance, -wheelYDistance);
	}

	/**
	 * Drive the 4 wheels with the specified speeds
	 * 
	 * @param frontLeft
	 * @param backLeft
	 * @param frontRight
	 * @param backRight
	 */
	public void drive(double frontLeft, double backLeft, double frontRight,
			double backRight) {
		if (invertLeft) {
			frontLeftDrive.set(-frontLeft);
			backLeftDrive.set(-backLeft);
		} else {
			frontLeftDrive.set(frontLeft);
			backLeftDrive.set(backLeft);
		}

		if (invertRight) {
			frontRightDrive.set(-frontRight);
			backRightDrive.set(-backRight);
		} else {
			frontRightDrive.set(frontRight);
			backRightDrive.set(backRight);
		}
	}

	@Override
	public void tankDrive(double left, double right) {
		drive(left, left, right, right);
	}
	
	@Override
	public void arcadeDrive(double speed, double spin) {
		DriveUtil.arcadeDrive(this, speed, spin);
	}

	@Override
	public void driveMecanum(double x, double y, double spin) {
		directionVector = new LinearVector(x, y);

		double wheelSpeeds[] = getDriveSpeeds(directionVector);
		double wheelRotateSpeeds[] = getWheelSpeedsForTurning(spin);

		for (int i = 0; i < 4; i++) {
			wheelSpeeds[i] += wheelRotateSpeeds[i];
		}

		wheelSpeeds = scaleSpeeds(wheelSpeeds,
				Math.max(directionVector.getMagnitude(), Math.abs(spin)));

		drive(wheelSpeeds[0], wheelSpeeds[2], wheelSpeeds[1], wheelSpeeds[3]);
	}
	
	/**
	 * Set the Virtual Turning Center
	 * @param VTC
	 */
	public void setVTC(Point VTC) {
		this.VTC = VTC;
	}
	
	private double[] getWheelSpeedsForTurning(double rotation) {
		double wheelSpeeds[] = new double[4];
		if (rotation == 0)
			return wheelSpeeds;
		LinearVector[] holdVectors = new LinearVector[4];
		holdVectors[0] = new LinearVector(VTC, frontLeftPoint);
		holdVectors[1] = new LinearVector(VTC, frontRightPoint);
		holdVectors[2] = new LinearVector(VTC, backLeftPoint);
		holdVectors[3] = new LinearVector(VTC, backRightPoint);
		double maxLength = 0;
		for (int i = 0; i < 4; i++) {
			if (holdVectors[i].getMagnitude() > maxLength)
				maxLength = holdVectors[i].getMagnitude();
		}
		for (int i = 0; i < 4; i++) {
			holdVectors[i] = holdVectors[i]
					.getOrthogonalVector(rotation)
					.getUnitVector()
					.getScaler( (holdVectors[i].getMagnitude() / maxLength) * Math.abs(rotation) );
			rotationVectors[i] = holdVectors[i];
			wheelSpeeds[i] = LinearVector.scalarProjection(holdVectors[i],
					wheelVectors[i]);

		}

		return wheelSpeeds;
	}
	

	private double[] getDriveSpeeds(LinearVector direction) {
		double wheelSpeeds[] = new double[4];
		for (int i = 0; i < 4; i++) {
			wheelSpeeds[i] = LinearVector.scalarProjection(direction,
					wheelVectors[i]);
		}
		return wheelSpeeds;
	}

	private double[] scaleSpeeds(double[] wheelSpeeds, double resultantSpeed) {
		double maxSpeed = 0;
		for (int i = 0; i < 4; i++) {
			if (Math.abs(wheelSpeeds[i]) > maxSpeed) {
				maxSpeed = Math.abs(wheelSpeeds[i]);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (maxSpeed == 0) {
				wheelSpeeds[i] = 0;
				continue;

			}
			wheelSpeeds[i] = (wheelSpeeds[i] / maxSpeed)
					* Math.abs(resultantSpeed);
		}
		return wheelSpeeds;
	}

}
