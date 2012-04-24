package robot.io.vex;

import robot.Robot;
import robot.io.pwmms.MSPWMOutput;
import robot.io.servo.ServoMS;

/**
 * @author Mitchell
 * 
 * A Vex Servo
 *
 */
public class VexServo extends ServoMS{

	/**
	 * Create a new Vex Servo
	 * @param output
	 */
	public VexServo(final MSPWMOutput output) {
		super(output, 0, 180, 1000, 2000);
	}
	
	/**
	 * Create a new vex servo getting the PWM output from the robot by name
	 * @param robot
	 * @param location
	 */
	public VexServo(final Robot robot, final String location) {
		this(robot.getFactory().getMSPWM(location));
	}

}
