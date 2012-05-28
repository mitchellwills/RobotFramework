package robot.io.compass;

import robot.io.value.*;
import robot.thread.*;

/**
 * A compass that averages the value returned by a compass
 * 
 * @author Mitchell
 *
 */
public class AveragedCompass extends AverageInputValue implements Compass {
	
	/**
	 * Create a new AveragedCompass
	 * @param threadFactory 
	 * @param compass the source compass
	 * @param bufferSize the size of the buffer to store values in
	 * @param updateDelay the delay between adding values to the buffer
	 */
	public AveragedCompass(RobotThreadFactory threadFactory, final Compass compass, final int bufferSize, final long updateDelay){
		super(threadFactory, compass, bufferSize, updateDelay);
	}

	@Override
	public double getHeading() {
		return getValue();
	}
}
