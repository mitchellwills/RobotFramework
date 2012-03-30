package robot.imperium.hardware;


/**
 * @author Mitchell
 * 
 * Represents the Teensy++ 2.0
 *
 */
public class Teensy2PP extends HardwareConfiguration {
	
	private static final Teensy2PP INSTANCE = new Teensy2PP();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static Teensy2PP get(){
		return INSTANCE;
	}

	private Teensy2PP() {
		super("Teensy++ 2.0", 490);

		/*addCapability(28, "A0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(29, "A1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(30, "A2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(31, "A3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(32, "A4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(33, "A5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(34, "A6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(35, "A7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);

		addCapability(20, "B0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_SS);
		addCapability(21, "B1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_SCK);
		addCapability(22, "B2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_MOSI);
		addCapability(23, "B3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_MISO);
		addCapability(24, "B4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(25, "B5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(26, "B6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(27, "B7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);

		addCapability(10, "C0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(11, "C1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(12, "C2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(13, "C3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(14, "C4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(15, "C5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(16, "C6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(17, "C7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);

		addCapability(0, "D0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, I2C_SCL, PWM_Output, Interrupt);
		addCapability(1, "D1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, I2C_SDA, PWM_Output, Interrupt);
		addCapability(2, "D2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SerialRx, Interrupt);
		addCapability(3, "D3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SerialTx, Interrupt);
		addCapability(4, "D4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(5, "D5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(6, "D6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(7, "D7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);

		addCapability(8, "E0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(9, "E1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(36, "E4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, Interrupt);
		addCapability(37, "E5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, Interrupt);
		addCapability(18, "E6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, Interrupt);
		addCapability(19, "E7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, Interrupt);

		addCapability(38, "F0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(39, "F1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(40, "F2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(41, "F3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(42, "F4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(43, "F5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(44, "F6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(45, "F7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);*/
	}
	

}
