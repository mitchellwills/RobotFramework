package robot.io.joystick;

/**
 * @author Mitchell
 * 
 * A joystick directional that is formed by two axes from a joystick
 *
 */
public class JoystickAxisDirectional implements JoystickDirectional {
	private final JoystickAxis xAxis;
	private final JoystickAxis yAxis;
	

	/**
	 * Create a joystick from two axes
	 * @param xAxis
	 * @param yAxis
	 */
	public JoystickAxisDirectional(JoystickAxis xAxis, JoystickAxis yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	@Override
	public String getName() {
		return "Axis Directional [x="+xAxis.getName()+", y="+yAxis.getName()+"]";
	}

	@Override
	public double getAngle() {
		return Math.toDegrees(Math.atan2(yAxis.getValue(), xAxis.getValue()) + Math.PI/2);//TODO ensure value is between 0 and 360
	}

	@Override
	public double getMagnatude() {
		return Math.sqrt(xAxis.getValue() * xAxis.getValue() + yAxis.getValue() * yAxis.getValue());
	}

	@Override
	public boolean isCentered() {
		return getMagnatude()==0;
	}

}
