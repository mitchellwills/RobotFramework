package robot.io.encoder;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual encoder whose value can be set
 *
 */
public class VirtualEncoder implements Encoder{

	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private int position = 0;

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public double getValue() {
		return getPosition();
	}
	
	/**
	 * Set the exact value of the encoder
	 * @param position 
	 */
	public void setPosition(int position){
		this.position = position;
		model.fireUpdateEvent();
	}
	
	/**
	 * increment the encoder position value by 1
	 */
	public void increment(){
		position++;
		model.fireUpdateEvent();
	}
	
	/**
	 * decrement the encoder position value by 1
	 */
	public void decrement(){
		position--;
		model.fireUpdateEvent();
	}

}
