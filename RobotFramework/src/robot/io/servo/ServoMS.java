package robot.io.servo;

import robot.io.*;
import robot.io.pwmms.*;

/**
 * @author Mitchell
 * 
 * a servo that outputs a millisecond value representing the angle<br>
 * the mapping from angle to output is linear
 *
 */
public class ServoMS implements Servo {
	/**
	 * the output value that will disable the output
	 */
	public static final double DISABLE_OUTPUT = LinearMSPWMOutput.DISABLE_OUTPUT;

	private final ForwardingRobotObjectModel model = new ForwardingRobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private final LinearMSPWMOutput output;
	/**
	 * Creates a servo that rotates between 0 and 180 degrees and outputs 1000 and 2000 ms respectively
	 * @param output the ms pwm output
	 */
	public ServoMS(MSPWMOutput output) {
		this(output, 0, 180, 1000, 2000);
	}

	/**
	 * Creates a servo that rotates between two given angle and outputs 1000 ms for the minimum angle and 2000 ms for the maximum angle respectively
	 * @param output the ms pwm output
	 * @param minAngle the minimum angle
	 * @param maxAngle the maximum angle
	 */
	public ServoMS(MSPWMOutput output, double minAngle,
			double maxAngle) {
		this(output, minAngle, maxAngle, 1000, 2000);
	}

	/**
	 * @param output the ms pwm output
	 * @param minAngle the minimum angle
	 * @param maxAngle the maximum angle
	 * @param minMS the minimum angle ms value
	 * @param maxMS the maximum angle ms value
	 */
	public ServoMS(MSPWMOutput output, double minAngle,
			double maxAngle, int minMS, int maxMS) {
		this.output = new LinearMSPWMOutput(output, minAngle, maxAngle, minMS, maxMS);
		output.addUpdateListener(model);
	}

	@Override
	public void setAngle(double angle) {
		output.setValue(angle);
	}

	@Override
	public double getAngle() {
		return output.getValue();
	}

	@Override
	public double getMinAngle() {
		return output.getMinimumValue();
	}

	@Override
	public double getMaxAngle() {
		return output.getMaximumValue();
	}

	@Override
	public void setValue(double value) {
		setAngle(value);
	}

	@Override
	public double getValue() {
		return getAngle();
	}
}
