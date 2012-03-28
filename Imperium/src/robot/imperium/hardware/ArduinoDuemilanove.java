package robot.imperium.hardware;

import robot.imperium.PinCapability;

/**
 * @author Mitchell
 * 
 * Represents the Arduino Duemilanove
 *
 */
public class ArduinoDuemilanove extends DefaultHardwareConfiguration {
	
	private static final ArduinoDuemilanove INSTANCE = new ArduinoDuemilanove();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static ArduinoDuemilanove get(){
		return INSTANCE;
	}

	private ArduinoDuemilanove() {
		super("Arduino Duemilanove", 20, 490);

		addCapability(0, "0");//Disable for serial , PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(1, "1");//Disable for serial , PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(2, "2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output);
		addCapability(3, "3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.PWM_Output);
		addCapability(4, "4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output);
		addCapability(5, "5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.PWM_Output);
		addCapability(6, "6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.PWM_Output);
		addCapability(7, "7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output);
		addCapability(8, "8", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output);
		addCapability(9, "9", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.PWM_Output);
		addCapability(10, "10", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.PWM_Output, PinCapability.SPI_SS);
		addCapability(11, "11", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.PWM_Output, PinCapability.SPI_MOSI);
		addCapability(12, "12", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.SPI_MISO);
		addCapability(13, "13", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.SPI_SCK);

		addCapability(14, "A0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.AnalogInput);
		addCapability(15, "A1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.AnalogInput);
		addCapability(16, "A2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.AnalogInput);
		addCapability(17, "A3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.AnalogInput);
		addCapability(18, "A4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.AnalogInput, PinCapability.I2C_SDA);
		addCapability(19, "A5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.MSPWM_Output, PinCapability.AnalogInput, PinCapability.I2C_SCL);
	}
	
	/**
	 * @return the pin of the LED that is on the Arduino Duemilanove board
	 */
	public int getOnboardLEDPin(){
		return getPin("13");
	}

}
