package robot.io.parallax;

import robot.io.Input;
import robot.io.binary.BinaryOutput;
import robot.io.frequency.FrequencyInput;

/**
 * @author Mitchell
 * 
 * Represents the TSL230R
 *
 */
public class TSL230R implements Input {

	/**
	 * @author Mitchell
	 * 
	 * The sensitivity of the device
	 *
	 */
	public static enum Sensitivity{
		/**
		 * turn the device off
		 */
		Off(0, false, false),
		/**
		 * 1x sensitivity
		 */
		x1(1.0, false, true),
		/**
		 * 10x sensitivity
		 */
		x10(10, true, false),
		/**
		 * 100x sensitivity
		 */
		x100(100, true, true);
		private final double factor;
		private final boolean s1;
		private final boolean s0;
		private Sensitivity(double factor, boolean s1, boolean s0){
			this.factor = factor;
			this.s1 = s1;
			this.s0 = s0;
		}
		/**
		 * Set this mode to the device
		 * @param s1Output
		 * @param s0Output
		 */
		public void apply(BinaryOutput s1Output, BinaryOutput s0Output){
			s1Output.set(s1);
			s0Output.set(s0);
		}
		/**
		 * @param raw
		 * @return scale the raw output of the device (after scaling) to the actual value accounting for the sensitivity
		 */
		public double scale(double raw){
			return raw/factor;
		}
	}
	/**
	 * @author Mitchell
	 * 
	 * The scaling of the pulse output of the device
	 *
	 */
	public static enum Scaling{
		/**
		 * no scaling
		 */
		_1(1.0, false, false),
		/**
		 * 1/2 scale factor
		 */
		_2(0.5, false, true),
		/**
		 * 1/10 scale factor
		 */
		_10(0.1, true, false),
		/**
		 * 1/100 scale factor
		 */
		_100(0.01, true, true);
		
		private final double factor;
		private final boolean s3;
		private final boolean s2;
		private Scaling(double factor, boolean s3, boolean s2){
			this.factor = factor;
			this.s3 = s3;
			this.s2 = s2;
		}
		/**
		 * Set this mode to the device
		 * @param s3Output
		 * @param s2Output
		 */
		public void apply(BinaryOutput s3Output, BinaryOutput s2Output){
			s3Output.set(s3);
			s2Output.set(s2);
		}
		/**
		 * @param raw
		 * @return scale the raw output of the device to the actual unscaled value accounting for the scaling
		 */
		public double scale(long raw){
			return raw/factor;
		}
	}

	private final FrequencyInput out;
	private final BinaryOutput s0;
	private final BinaryOutput s1;
	private final BinaryOutput s2;
	private final BinaryOutput s3;
	private Scaling scaling;
	private Sensitivity sensitivity;

	/**
	 * Create a TSL230R where the settings are hardwired
	 * @param out
	 * @param sensitivity 
	 * @param scaling 
	 */
	public TSL230R(FrequencyInput out, Sensitivity sensitivity, Scaling scaling){
		this(out, null, null, null, null, sensitivity, scaling);
	}

	/**
	 * Create a TSL230R where the settings can be configured dynamically
	 * with default sensitivity of 1x and scaling of 1/2
	 * @param out
	 * @param s0
	 * @param s1
	 * @param s2
	 * @param s3
	 */
	public TSL230R(FrequencyInput out, BinaryOutput s0, BinaryOutput s1, BinaryOutput s2, BinaryOutput s3){
		this(out, s0, s1, s2, s3, Sensitivity.x1, Scaling._2);
	}
	
	/**
	 * Create a TSL230R where the settings can be configured dynamically
	 * @param out
	 * @param s0
	 * @param s1
	 * @param s2
	 * @param s3
	 * @param defaultSensitivity 
	 * @param defaultScaling 
	 */
	public TSL230R(FrequencyInput out, BinaryOutput s0, BinaryOutput s1, BinaryOutput s2, BinaryOutput s3, Sensitivity defaultSensitivity, Scaling defaultScaling){
		this.out = out;
		this.s0 = s0;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		setSensitivity(defaultSensitivity);
		setScaling(defaultScaling);
	}
	
	private void setScaling(Scaling scaling) {
		this.scaling = scaling;
		if(s3!=null && s2!=null)
			scaling.apply(s3, s2);
	}

	private void setSensitivity(Sensitivity sensitivity){
		this.sensitivity = sensitivity;
		if(s1!=null && s0!=null)
			sensitivity.apply(s1, s0);
	}
	
	/**
	 * @return the current scaling for the device
	 */
	public Scaling getScaling(){
		return scaling;
	}
	
	/**
	 * @return the current sensitivity for the device
	 */
	public Sensitivity getSensitivity(){
		return sensitivity;
	}
	
	/**
	 * @return the raw frequency the device is outputting
	 */
	public long getRaw(){
		return out.getFrequency();
	}
	
	/**
	 * @return the output frequency if the scale was 1
	 */
	public double get(){
		return sensitivity.scale(scaling.scale(getRaw()));
	}
	
}
