package robot.io.joystick;

import robot.io.*;

/**
 * @author Mitchell
 * 
 * A joystick directional that is formed by two axes from a joystick
 *
 */
public class JoystickAxisDirectional implements JoystickDirectional {

	private final ForwardingRobotObjectModel model = new ForwardingRobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	
	private final JoystickAxis xAxis;
	private final JoystickAxis yAxis;

	/**
	 * Create a joystick from two axes
	 * @param xAxis
	 * @param yAxis
	 */
	public JoystickAxisDirectional(JoystickAxis xAxis, JoystickAxis yAxis) {
		this.xAxis = xAxis;
		xAxis.addUpdateListener(model);
		this.yAxis = yAxis;
		yAxis.addUpdateListener(model);
	}

	@Override
	public double getAngle() {
		return (Math.toDegrees(Math.atan2(yAxis.getValue(), xAxis.getValue()) + Math.PI/2)+360)%360;
	}

	@Override
	public double getMagnitude() {
		return Math.sqrt(xAxis.getValue() * xAxis.getValue() + yAxis.getValue() * yAxis.getValue());
	}

	@Override
	public boolean isCentered() {
		return getMagnitude()==0;
	}

}
