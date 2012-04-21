package robot.io.encoder;

import robot.control.ControlLoopInput;
import robot.io.RobotObjectListener;

/**
 * @author Mitchell
 * 
 * An input for a control loop that returns the position of the encoder
 *
 */
public class EncoderPositionControlLoopInput implements Encoder, ControlLoopInput {

	private final Encoder input;
	/**
	 * Create a new control loop input based on an encoder's position
	 * 
	 * @param input
	 */
	public EncoderPositionControlLoopInput(Encoder input){
		this.input = input;
	}
	@Override
	public void addUpdateListener(RobotObjectListener<Encoder> listener) {
		input.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener<Encoder> listener) {
		input.addUpdateListener(listener);
	}

	@Override
	public double get() {
		return getPosition();
	}

	@Override
	public int getPosition() {
		return input.getPosition();
	}

}
