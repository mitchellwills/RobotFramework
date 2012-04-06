package robot.io.first;


import robot.io.pwmms.MSPWMOutput;
import robot.io.speedcontroller.SpeedControllerMS;

/**
 * @author Mitchell
 * 
 * Represents a Victor Motor Controller
 *
 */
public class Victor extends SpeedControllerMS{

	/**
	 * @param output the ms pwm output connected to the motor
	 */
	public Victor(MSPWMOutput output) {
		super(output, 1350, 1445, 1500, 1550, 1650);
	}

}
