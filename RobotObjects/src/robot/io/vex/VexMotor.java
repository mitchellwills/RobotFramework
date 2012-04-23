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
	public VexMotor(MSPWMOutput output) {
		super(output, 1000, 1460, 1500, 1600, 2000);
	}
	
	public VexMotor(Robot robot, String location) {
		this(robot.getFactory().getMSPWM(location));
	}

}
