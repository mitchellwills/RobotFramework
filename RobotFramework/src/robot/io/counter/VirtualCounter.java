package robot.io.counter;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual counter whose value can be set
 *
 */
public class VirtualCounter implements Counter{

	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private int count = 0;

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public double getValue() {
		return getCount();
	}
	
	/**
	 * Set the exact value of the counter
	 * @param count
	 */
	public void setCount(int count){
		this.count = count;
		model.fireUpdateEvent();
	}
	
	/**
	 * increment the counter value by 1
	 */
	public void increment(){
		count++;
		model.fireUpdateEvent();
	}
	
	/**
	 * decrement the counter by 1
	 */
	public void decrement(){
		count--;
		model.fireUpdateEvent();
	}

}
