package robot.io.virtual;

import robot.io.*;
import robot.io.joystick.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

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

	private final int id;
	private double angle;
	private double magnitude;
	
	public VirtualJoystickDirectional(){
		this(0);
	}
	
	/**
	 * Create a new virtual joystick directional
	 * @param id the id of the directional relative to the joystick
	 */
	@Inject public VirtualJoystickDirectional(@Assisted(JoystickDirectional.PARAM_ID) int id){
		this.id = id;
		angle = 0;
		magnitude = 0;
	}
	
	public int getId(){
		return id;
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
