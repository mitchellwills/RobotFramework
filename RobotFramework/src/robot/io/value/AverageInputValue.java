package robot.io.value;

import robot.Robot;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;
import robot.thread.PeriodicRobotThread;
import robot.util.RingBuffer;

/**
 * An object that averages the value returned by {@link InputValue#getValue()} using a ring buffer
 * 
 * @author Mitchell
 *
 */
public class AverageInputValue implements InputValue, UpdatableObject {
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private final InputValue inputValue;
	private final RingBuffer buffer;
	private final AverageThread averageThread;
	
	/**
	 * Create a new AverageInputValue
	 * @param robot the robot the object is part of
	 * @param inputValue the source input value
	 * @param bufferSize the size of the buffer to store values in
	 * @param updateDelay the delay between adding values to the buffer
	 */
	public AverageInputValue(Robot robot, final InputValue inputValue, final int bufferSize, final long updateDelay){
		this.inputValue = inputValue;
		buffer = new RingBuffer(bufferSize);
		averageThread = new AverageThread(robot, updateDelay);
		averageThread.start();
	}
	

	@Override
	public double getValue() {
		return buffer.average();
	}
	

	private class AverageThread extends PeriodicRobotThread {
		public AverageThread(Robot robot, long updateDelay) {
			super(robot, "Input Average Thread ("+AverageInputValue.this.toString()+")", updateDelay);
		}

		@Override
		public void periodic(){
			buffer.append(inputValue.getValue());
		}
	}
}
