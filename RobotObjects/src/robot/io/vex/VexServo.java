package robot.io.vex;

import robot.io.RobotObject;
import robot.io.factory.old.*;
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
	@FactoryConstructable
	public VexServo(@FactoryParameter(RobotObject.PARAM_LOCATION) final MSPWMOutput output) {
		super(output, 0, 180, 1000, 2000);
	}

}
