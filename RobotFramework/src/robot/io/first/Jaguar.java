package robot.io.first;


import robot.io.pwmms.MSPWMOutput;
import robot.io.speedcontroller.SpeedControllerMS;

/**
 * @author Mitchell
 * 
 * Represents a Jaguar Motor Controller
 *
 */
public class Jaguar extends SpeedControllerMS{

	/**
	 * @param output the ms pwm output connected to the motor
	 */
	public Jaguar(MSPWMOutput output) {
		super(output, 1000, 1425, 1500, 1575, 2000);
	}

}
