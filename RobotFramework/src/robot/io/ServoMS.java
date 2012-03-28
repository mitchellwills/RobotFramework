package robot.io;

import robot.util.RobotUtil;

/**
 * @author Mitchell
 * 
 * a servo that outputs a millisecond value representing the angle<br>
 * the mapping from angle to output is linear
 *
 */
public class ServoMS implements Servo {
	public static final double DISABLE_OUTPUT = Double.NaN;
	
	
	private final MSPWMOutput output;
	private final double initialAngle;
	private final double minAngle;
	private final double maxAngle;
	private final int minMS;
	private final int maxMs;
	
	

	/**
	 * @param output the output the object uses
	 * @param initialAngle the initial angle the 
	 * @param minAngle
	 * @param maxAngle
	 * @param minMS
	 * @param maxMs
	 */
	public ServoMS(MSPWMOutput output, double initialAngle, double minAngle,
			double maxAngle, int minMS, int maxMs) {
		this.output = output;
		this.initialAngle = initialAngle;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
		this.minMS = minMS;
		this.maxMs = maxMs;
	}

	@Override
	public void setAngle(double angle) {
		if(angle==DISABLE_OUTPUT){
			output.set(MSPWMOutput.DISABLED);
		}
		else{
			angle = RobotUtil.limit(angle, minAngle, maxAngle);
			// TODO Auto-generated method stub
		}
	}

	@Override
	public double getAngle() {
		output.get();
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMinAngle() {
		return minAngle;
	}

	@Override
	public double getMaxAngle() {
		return maxAngle;
	}

}
