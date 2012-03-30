package robot.imperium.hardware;

import static robot.imperium.PinCapability.*;

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

		addCapability(0, "0");
		addCapability(1, "1");
		addCapability(2, "2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(3, "3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(4, "4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(5, "5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(6, "6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(7, "7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(8, "8", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(9, "9", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(10, "10", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SPI_SS);
		addCapability(11, "11", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SPI_MOSI);
		addCapability(12, "12", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_MISO);
		addCapability(13, "13", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_SCK);

		addCapability(14, "A0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(15, "A1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(16, "A2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(17, "A3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput);
		addCapability(18, "A4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput, I2C_SDA);
		addCapability(19, "A5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput, I2C_SCL);
	}
	
	/**
	 * @return the pin of the LED that is on the Arduino Duemilanove board
	 */
	public int getOnboardLEDPin(){
		return getPin("13");
	}

}
