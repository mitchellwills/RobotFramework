package robot.io;

import robot.io.speedcontroller.SpeedController;
import robot.io.speedcontroller.VirtualSpeedController;

/**
 * @author Mitchell
 * 
 * A Robot Object Factory that returns virtual objects
 *
 */
public class VirtualRobotObjectFactory extends RobotObjectFactory{
	@Override
	public SpeedController getSpeedController(String location){
		return new VirtualSpeedController();
	}
}
