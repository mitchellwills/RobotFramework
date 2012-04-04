package robot.io.pwmms;

import robot.io.Output;
import robot.io.RobotObjectListener;
import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 *         An output that linearly scales an input (-1.0 to 1.0) for a MS PWM output with a deadband at 0.0
 * 
 */
public final class DeadbandedLinearMSPWMOutput implements Output {
	/**
	 * the value that represents the disabling PWM output
	 */
	public static final double DISABLE_OUTPUT = Double.NaN;
	
	private static final double MIN_VALUE = -1.0;
	private static final double CENTER_VALUE = 0.0;
	private static final double MAX_VALUE = 1.0;

	private final MSPWMOutput output;

	private final int minMS;
	private final int minDeadbandMS;
	private final int deadbandCenterMS;
	private final int maxDeadbandMS;
	private final int maxMS;
	

	/**
	 * @param output
	 *            the output this output writes to
	 * @param minMS the pulse length in ms that corresponds to full backward
	 * @param minDeadbandMS 
	 * @param deadbandCenterMS 
	 * @param maxDeadbandMS 
	 * @param maxMS the pulse length in ms that corresponds to full forward
	 */
	public DeadbandedLinearMSPWMOutput(MSPWMOutput output, int minMS,
			int minDeadbandMS, int deadbandCenterMS, int maxDeadbandMS,
			int maxMS) {
		if (output == null)
			throw new NullPointerException("The output cannot be null");
		this.output = output;
		this.minMS = minMS;
		this.minDeadbandMS = minDeadbandMS;
		this.deadbandCenterMS = deadbandCenterMS;
		this.maxDeadbandMS = maxDeadbandMS;
		this.maxMS = maxMS;
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
			output.set(valueToMS(RobotUtil.limit(value, MIN_VALUE, MAX_VALUE)));
		}
	}

	/**
	 * @return the current output value
	 */
	public double getValue() {
		return msToValue(output.get());
	}

	private int valueToMS(double value) {
		if(value==DISABLE_OUTPUT)
			return MSPWMOutput.DISABLED;
		if(value==CENTER_VALUE)
			return deadbandCenterMS;
		if(value<CENTER_VALUE)
			return (int) (value*(minDeadbandMS-minMS) + minDeadbandMS);
		if(value>CENTER_VALUE)
			return (int) (value*(maxMS-maxDeadbandMS) + maxDeadbandMS);
		return MSPWMOutput.DISABLED;
	}

	private double msToValue(int ms) {
		if(ms==MSPWMOutput.DISABLED)
			return DISABLE_OUTPUT;
		if(RobotUtil.within(ms, minDeadbandMS, maxDeadbandMS))
			return CENTER_VALUE;
		if(ms<minDeadbandMS)
			return ((double)(ms-minDeadbandMS))/(minDeadbandMS-minMS);
		if(ms<maxDeadbandMS)
			return ((double)(ms-maxDeadbandMS))/(maxMS-maxDeadbandMS);
		return CENTER_VALUE;
	}
}
