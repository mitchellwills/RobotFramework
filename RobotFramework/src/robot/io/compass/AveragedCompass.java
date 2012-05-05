package robot.io.compass;

import robot.Robot;
import robot.io.value.AverageInputValue;

/**
 * A compass that averages the value returned by a compass
 * 
 * @author Mitchell
 *
 */
public class AveragedCompass extends AverageInputValue implements Compass {
	
	/**
	 * Create a new AveragedCompass
	 * @param robot the robot the object is part of
	 * @param compass the source compass
	 * @param bufferSize the size of the buffer to store values in
	 * @param updateDelay the delay between adding values to the buffer
	 */
	public AveragedCompass(final Robot robot, final Compass compass, final int bufferSize, final long updateDelay){
		super(robot, compass, bufferSize, updateDelay);
	}

	@Override
	public double getHeading() {
		return getValue();
	}
}
