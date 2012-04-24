package robot.io.analog;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;

/**
 * @author Mitchell
 * 
 * An Analog Voltage Input whose value can be set
 *
 */
public class VirtualAnalogVoltageInput implements AnalogVoltageInput {

	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private final double maxVoltage;
	private double voltage;
	/**
	 * Create a new Virtual Voltage input
	 * @param initialVoltage the initial voltage the input will read
	 * @param maxVoltage the maximum voltage that the input provides
	 */
	public VirtualAnalogVoltageInput(final double initialVoltage, final double maxVoltage){
		this.maxVoltage = maxVoltage;
		this.voltage = initialVoltage;
	}

	@Override
	public double getMaxVoltage() {
		return maxVoltage;
	}

	@Override
	public double getVoltage() {
		return voltage;
	}

	
	@Override
	public double getValue() {
		return getVoltage();
	}
	
	/**
	 * Set the value of voltage that the input will read
	 * @param voltage the new voltage value
	 */
	public void setVoltage(final double voltage){
		this.voltage = voltage;
		model.fireUpdateEvent();
	}

}
