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
	
	private final long updateDelay;
	
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
		this(Kp, Ki, Kd, input, output, minOutput, maxOutput, 20);
	}
	/**
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param input
	 * @param output
	 * @param minOutput 
	 * @param maxOutput 
	 * @param updateDelay the delay between updates of the PID loop
	 */
	public PIDController(double Kp, double Ki, double Kd, PIDInput input,
			PIDOutput output, double minOutput, double maxOutput, long updateDelay) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.input = input;
		this.output = output;
		this.minOutput = minOutput;
		this.maxOutput = maxOutput;
		this.updateDelay = updateDelay;
		
		setpoint = getPosition();
		
		new PIDControllerThread().start();
	}
	
	/**
	 * perform an update on the pid loop
	 */
	public void update(){
		double error = getPosition() - setpoint;
		
		double value = Kp * error;
		
		//TODO do integral and derivative terms
		
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
	

	
	private class PIDControllerThread extends Thread {
		@Override
		public void run(){
			while(true){
				update();
				RobotUtil.sleep(updateDelay);//TODO do calculation for loop to actully run every updateDelay
			}
		}
	}


}
