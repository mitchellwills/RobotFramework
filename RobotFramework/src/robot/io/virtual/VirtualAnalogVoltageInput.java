package robot.io.virtual;

import robot.error.*;
import robot.io.*;
import robot.io.analog.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

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
	 * @param maxVoltage the maximum voltage that the input provides
	 */
	@Inject public VirtualAnalogVoltageInput(@Assisted(AnalogVoltageInput.PARAM_MAX_VOLTAGE) final double maxVoltage){
		if(maxVoltage<=0)
			throw new RobotInitializationException("The maximum voltage cannot be 0 or negative");
		this.maxVoltage = maxVoltage;
		voltage = 0;
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
