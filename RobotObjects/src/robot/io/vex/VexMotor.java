package robot.io.vex;

import robot.io.RobotObject;
import robot.io.factory.FactoryConstructable;
import robot.io.factory.FactoryParameter;
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
	@FactoryConstructable
	public VexMotor(@FactoryParameter(RobotObject.PARAM_LOCATION) final MSPWMOutput output) {
		super(output, 1000, 1460, 1500, 1600, 2000);
	}

}
