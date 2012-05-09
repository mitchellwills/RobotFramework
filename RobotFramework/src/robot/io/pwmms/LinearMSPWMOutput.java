package robot.io.pwmms;

import robot.io.Output;
import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 *         An output that linearly scales an input for a MS PWM output
 * 
 */
public final class LinearMSPWMOutput implements Output {
	/**
	 * the value that represents the disabling PWM output
	 */
	public static final double DISABLE_OUTPUT = Double.NaN;

	private final MSPWMOutput output;

	private final double minValue;
	private final double maxValue;
	private final double valueRange;

	private final int minMS;
	@SuppressWarnings("unused")
	private final int maxMS;
	private final int msRange;

	/**
	 * @param output
	 *            the output this output writes to
	 * @param minValue
	 *            the minimum input value
	 * @param maxValue
	 *            the maximum output value
	 * @param minMS
	 *            the pwm ms value that should be set when the minimum value is
	 *            set
	 * @param maxMS
	 *            the pwm ms value that should be set when the maximum value is
	 *            set
	 */
	public LinearMSPWMOutput(MSPWMOutput output, double minValue,
			double maxValue, int minMS, int maxMS) {
		if (output == null)
			throw new NullPointerException("The output cannot be null");
		this.output = output;
		this.minValue = minValue;
		this.maxValue = maxValue;
		valueRange = maxValue - minValue;
		this.minMS = minMS;
		this.maxMS = maxMS;
		msRange = maxMS - minMS;
	}

	/**
	 * Set the value of the output
	 * 
	 * @param value
	 *            the input value that the output should be set to
	 */
	public void setValue(double value) {
		if (value == DISABLE_OUTPUT) {
			output.set(MSPWMOutput.DISABLED);
		} else {
			output.set(valueToMS(RobotUtil.limit(value, minValue, maxValue)));
		}
	}

	/**
	 * @return the current output value
	 */
	public double getValue() {
		return msToValue(output.get());
	}
	
	/**
	 * @return the minimum input value
	 */
	public double getMinimumValue(){
		return minValue;
	}
	
	/**
	 * @return the maximum input value
	 */
	public double getMaximumValue(){
		return maxValue;
	}

	private int valueToMS(double value) {
		if(value==DISABLE_OUTPUT)
			return MSPWMOutput.DISABLED;
		double scaledValue = (value - minValue) / valueRange;
		return (int) (scaledValue * msRange + minMS);
	}

	private double msToValue(int ms) {
		if(ms==MSPWMOutput.DISABLED)
			return DISABLE_OUTPUT;
		double scaledMS = ((double) (ms - minMS)) / msRange;
		return scaledMS * valueRange + minValue;
	}

}
