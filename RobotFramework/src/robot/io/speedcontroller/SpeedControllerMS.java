package robot.io.speedcontroller;

import robot.io.ForwardingRobotObjectModel;
import robot.io.RobotObjectListener;
import robot.io.pwmms.DeadbandedLinearMSPWMOutput;
import robot.io.pwmms.LinearMSPWMOutput;
import robot.io.pwmms.MSPWMOutput;

/**
 * @author Mitchell
 * 
 * A speed controller that outputs to a MS PWM output
 *
 */
public class SpeedControllerMS implements SpeedController {

	/**
	 * the output value that will disable the output
	 */
	public static final double DISABLE_OUTPUT = LinearMSPWMOutput.DISABLE_OUTPUT;
	
	private final DeadbandedLinearMSPWMOutput output;
	private final ForwardingRobotObjectModel<SpeedController, MSPWMOutput> model = new ForwardingRobotObjectModel<SpeedController, MSPWMOutput>(this);
	@Override
	public void addUpdateListener(RobotObjectListener<SpeedController> listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(
			RobotObjectListener<SpeedController> listener) {
		model.removeUpdateListener(listener);
	}
	

	/**
	 * Creates a speed controller that outputs outputs 1000 ms for full backwards and 2000 ms for full forward respectively
	 * @param output the ms pwm output
	 */
	public SpeedControllerMS(MSPWMOutput output) {
		this(output, 1000, 2000);
	}

	/**
	 * Creates a speed controller that outputs outputs given values for forward and backward and the average for stopped
	 * @param output the ms pwm output
	 * @param fullBackward 
	 * @param fullForward 
	 */
	public SpeedControllerMS(MSPWMOutput output, int fullBackward, int fullForward) {
		this(output, fullBackward, (fullForward+fullBackward)/2, fullForward);
	}

	/**
	 * Creates a speed controller that outputs outputs given values for forward, backward and stopped
	 * @param output the ms pwm output
	 * @param fullBackward 
	 * @param stopped 
	 * @param fullForward 
	 */
	public SpeedControllerMS(MSPWMOutput output, int fullBackward, int stopped, int fullForward) {
		this(output, fullBackward, stopped, stopped, stopped, fullForward);
	}

	/**
	 * Creates a speed controller that outputs outputs given values for forward, backward and stopped
	 * @param output the ms pwm output
	 * @param fullBackward 
	 * @param backwardDeadband the end of the deadband zone on the backward side
	 * @param stopped 
	 * @param forwardDeadband the end of the deadband zone on the forward side
	 * @param fullForward 
	 */
	public SpeedControllerMS(MSPWMOutput output, int fullBackward, int backwardDeadband, int stopped, int forwardDeadband, int fullForward) {
		this.output = new DeadbandedLinearMSPWMOutput(output, fullBackward, backwardDeadband, stopped, forwardDeadband, fullForward);
		output.addUpdateListener(model);
	}
	

	@Override
	public void set(double value) {
		output.setValue(value);
	}

	@Override
	public double get() {
		return output.getValue();
	}

}
