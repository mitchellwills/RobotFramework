package robot.control;

import robot.Robot;
import robot.io.value.InputValue;
import robot.io.value.OutputValue;
import robot.thread.PeriodicRobotThread;
import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 * A simple implementation of a PID loop
 * based on http://en.wikipedia.org/wiki/PID_loop
 *
 */
public class PIDController implements ControlLoop{
	
	private double Kp;
	private double Ki;
	private double Kd;

	private double threshold;
	
	private final InputValue input;
	private final OutputValue output;
	private final double minOutput;
	private final double maxOutput;
	
	private double setpoint;
	private double integral;
	private double previousError;
	private double lastUpdate;
	
	private final PIDControllerThread updateThread;
	

	/**
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param robot 
	 * @param input
	 * @param output
	 * @param minOutput 
	 * @param maxOutput 
	 */
	public PIDController(double Kp, double Ki, double Kd, InputValue input,
			OutputValue output, double minOutput, double maxOutput) {
		this(Kp, Ki, Kd, input, output, minOutput, maxOutput, 20);
	}
	/**
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param robot 
	 * @param input
	 * @param output
	 * @param minOutput 
	 * @param maxOutput 
	 * @param updateDelay the delay between updates of the PID loop
	 */
	public PIDController(double Kp, double Ki, double Kd, InputValue input,
			OutputValue output, double minOutput, double maxOutput, long updateDelay) {
		this(Kp, Ki, Kd, input, output, minOutput, maxOutput, 0, updateDelay);
	}
	

	/**
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param robot
	 * @param inputLocation
	 * @param outputLocation
	 * @param minOutput
	 * @param maxOutput
	 * @param threshold
	 * @param updateDelay
	 */
	public PIDController(double Kp, double Ki, double Kd, String inputLocation,
			String outputLocation, double minOutput, double maxOutput, double threshold, long updateDelay) {
		this(Kp, Ki, Kd, (InputValue)Robot.getInstance().getObject(inputLocation), (OutputValue)Robot.getInstance().getObject(outputLocation), minOutput, maxOutput, threshold, updateDelay);
	}
	/**
	 * @param Kp
	 * @param Ki
	 * @param Kd
	 * @param robot 
	 * @param input
	 * @param output
	 * @param minOutput 
	 * @param maxOutput 
	 * @param threshold 
	 * @param updateDelay the delay between updates of the PID loop
	 */
	public PIDController(double Kp, double Ki, double Kd, InputValue input,
			OutputValue output, double minOutput, double maxOutput, double threshold, long updateDelay) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.input = input;
		this.output = output;
		this.minOutput = minOutput;
		this.maxOutput = maxOutput;
		this.threshold = threshold;
		
		setpoint = getPosition();
		lastUpdate = System.currentTimeMillis();
		integral = 0;
		previousError = 0;
		
		updateThread = new PIDControllerThread(updateDelay);
		updateThread.start();
	}
	
	/**
	 * perform an update on the pid loop
	 */
	public synchronized void update(){
		double position = getPosition();
		double time = System.currentTimeMillis();
		double dt = time - lastUpdate;
		
		double error = position - setpoint;
		integral += dt * error;
		double derivative = (error - previousError)/dt;
		
		double value = Kp * error + Ki * integral + Kd * derivative;
		
		value = RobotUtil.limit(value, minOutput, maxOutput);
		if(Math.abs(value)<threshold)
			value = 0;
		output.setValue(value);
		
		previousError = error;
		lastUpdate = time;
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
		return input.getValue();
	}
	

	
	private class PIDControllerThread extends PeriodicRobotThread {
		public PIDControllerThread(long updateDelay) {
			super("PID Controller ("+PIDController.this.toString()+")", updateDelay);
		}

		@Override
		public void periodic(){
			update();
		}
	}


}
