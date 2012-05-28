package robot.io.value;

import robot.io.*;
import robot.thread.*;
import robot.util.*;

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
	 * @param threadFactory 
	 * @param inputValue the source input value
	 * @param bufferSize the size of the buffer to store values in
	 * @param updateDelay the delay between adding values to the buffer
	 */
	public AverageInputValue(RobotThreadFactory threadFactory, final InputValue inputValue, final int bufferSize, final long updateDelay){
		this.inputValue = inputValue;
		buffer = new RingBuffer(bufferSize);
		averageThread = new AverageThread(threadFactory, updateDelay);
		averageThread.start();
	}
	

	@Override
	public double getValue() {
		return buffer.average();
	}
	

	private class AverageThread extends PeriodicRobotThread {
		public AverageThread(RobotThreadFactory threadManager, long updateDelay) {
			super(threadManager, "Input Average Thread ("+AverageInputValue.this.toString()+")", updateDelay);
		}

		@Override
		public void periodic(){
			buffer.append(inputValue.getValue());
			model.fireUpdateEvent();
		}
	}
}
