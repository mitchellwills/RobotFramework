package robot.io.joystick;

public class JoystickAxisDirectional implements JoystickDirectional {
	private final JoystickAxis xAxis;
	private final JoystickAxis yAxis;
	

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
