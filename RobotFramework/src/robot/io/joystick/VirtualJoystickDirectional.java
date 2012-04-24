package robot.io.joystick;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * A virtual joystick directional implementation
 *
 */
public class VirtualJoystickDirectional implements JoystickDirectional{
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private final String name;
	private double angle;
	private double magnitude;
	/**
	 * Create a new virtual joystick directional
	 * @param id the id of the directional relative to the joystick
	 */
	public VirtualJoystickDirectional(int id){
		this.name = "Virtual Directional "+id;
		angle = 0;
		magnitude = 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public double getMagnitude() {
		return magnitude;
	}

	@Override
	public boolean isCentered() {
		return magnitude==0;
	}
	
	/**
	 * Set the angle returned by this directional
	 * @param angle
	 */
	public void setAngle(final double angle){
		this.angle = angle;
	}
	
	/**
	 * Set the magnitude returned by the directional
	 * @param magnitude
	 */
	public void setMagnitude(final double magnitude){
		this.magnitude = magnitude;
	}

}
