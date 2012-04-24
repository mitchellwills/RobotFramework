package robot.io.vex;

import robot.Robot;
import robot.io.pwmms.MSPWMOutput;
import robot.io.speedcontroller.SpeedControllerMS;

/**
 * @author Mitchell
 * 
 * Represents a standard vex motor
 *
 */
public class VexMotor extends SpeedControllerMS{

	/**
	 * @param output the ms pwm output connected to the motor
	 */
	public VexMotor(final MSPWMOutput output) {
		super(output, 1000, 1460, 1500, 1600, 2000);
	}
	
	/**
	 * Create a new Vex Motor getting the PWM output from the robot by name
	 * @param robot
	 * @param location
	 */
	public VexMotor(final Robot robot, final String location) {
		this(robot.getFactory().getMSPWM(location));
	}

}
