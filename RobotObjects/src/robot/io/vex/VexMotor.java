package robot.io.vex;

import robot.io.pwmms.*;
import robot.io.speedcontroller.*;

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

}
