package robot.io.vex;

import robot.io.pwmms.*;
import robot.io.servo.*;

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

}
