package robot.io.vex;

import robot.Robot;
import robot.io.pwmms.MSPWMOutput;
import robot.io.servo.ServoMS;

public class VexServo extends ServoMS{

	public VexServo(MSPWMOutput output) {
		super(output, 0, 180, 1000, 2000);
	}
	
	public VexServo(Robot robot, String location) {
		this(robot.getFactory().getMSPWM(location));
	}

}
