package robot.io.encoder;

import robot.control.ControlLoopInput;
import robot.io.Input;
import robot.io.UpdatableObject;


/**
 * An encoder
 * 
 * @author Mitchell
 *
 */
public interface Encoder extends Input, UpdatableObject<Encoder>, ControlLoopInput {
	/**
	 * @return the position
	 */
	public int getPosition();
}
