package robot.imperium.hardware;

import robot.imperium.PinCapability;

/**
 * @author Mitchell
 * 
 * Represents the Teensy++ 2.0
 *
 */
public class Teensy2PP extends DefaultHardwareConfiguration {
	
	private static final Teensy2PP INSTANCE = new Teensy2PP();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static Teensy2PP get(){
		return INSTANCE;
	}

	private Teensy2PP() {
		super("Teensy++ 2.0", 48);

		addCapability(0, "PA0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(1, "PA1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(2, "PA2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(3, "PA3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(4, "PA4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(5, "PA5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(6, "PA6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(7, "PA7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);

		addCapability(8, "PB0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(9, "PB1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(10, "PB2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(11, "PB3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(12, "PB4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(13, "PB5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(14, "PB6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(15, "PB7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);

		addCapability(16, "PC0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(17, "PC1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(18, "PC2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(19, "PC3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(20, "PC4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(21, "PC5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(22, "PC6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(23, "PC7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);

		addCapability(24, "PD0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(25, "PD1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(26, "PD2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(27, "PD3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(28, "PD4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(29, "PD5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(30, "PD6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(31, "PD7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);

		addCapability(32, "PE0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(33, "PE1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(34, "PE2");//not actually exposed on board
		addCapability(35, "PE3");//not actually exposed on board
		addCapability(36, "PE4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(37, "PE5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(38, "PE6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(39, "PE7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);

		addCapability(40, "PF0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(41, "PF1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(42, "PF2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(43, "PF3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(44, "PF4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(45, "PF5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(46, "PF6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(47, "PF7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
	}

}
