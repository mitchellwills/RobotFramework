package robot.pid;

import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 * A simple implementation of a PID loop
 *
 */
public class PIDController implements PIDLoop{
	
	private double Kp;
	private double Ki;
	private double Kd;
	
	private final PIDInput input;
	private final PIDOutput output;
	private final double minOutput;
	private final double maxOutput;
	
	private double setpoint;
	

	/**
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param input
	 * @param output
	 * @param minOutput 
	 * @param maxOutput 
	 */
	public PIDController(double Kp, double Ki, double Kd, PIDInput input,
			PIDOutput output, double minOutput, double maxOutput) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.input = input;
		this.output = output;
		this.minOutput = minOutput;
		this.maxOutput = maxOutput;
		
		setpoint = getPosition();
	}
	
	public void update(){
		double error = getPosition() - setpoint;
		double value = Kp * error;
		
		value = RobotUtil.limit(value, minOutput, maxOutput);
		output.setPIDOutput(value);
	}

	@Override
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	@Override
	public double getSetpoint() {
		return setpoint;
	}

	@Override
	public double getPosition() {
		return input.getPIDInput();
	}

}
