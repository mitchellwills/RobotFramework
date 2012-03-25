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
		super("Arduino Duemilanove", 20);

		addCapability(0, "0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.SerialRx);
		addCapability(1, "1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.SerialTx);
		addCapability(2, "2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(3, "3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(4, "4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(5, "5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(6, "6", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(7, "7", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(8, "8", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(9, "9", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS);
		addCapability(10, "10", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.SPI_SS);
		addCapability(11, "11", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.SPI_MOSI);
		addCapability(12, "12", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.SPI_MISO);
		addCapability(13, "13", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.SPI_SCK);

		addCapability(14, "A0", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(15, "A1", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(16, "A2", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(17, "A3", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput);
		addCapability(18, "A4", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput, PinCapability.I2C_SDA);
		addCapability(19, "A5", PinCapability.DigitalInput, PinCapability.DigitalOutput, PinCapability.SelectablePullUp, PinCapability.PWM_Output_MS, PinCapability.AnalogInput, PinCapability.I2C_SCL);

	}

}
