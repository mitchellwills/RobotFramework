package robot.imperium;

/**
 * @author Mitchell
 * 
 * Represents the capabilities of a pin
 *
 */
public enum PinCapability {
	/**
	 * Read an analog voltage
	 */
	AnalogInput,
	/**
	 * Write an analog voltage
	 */
	AnalogOutput,
	
	
	/**
	 * Write a digital value
	 */
	DigitalOutput,
	/**
	 * Read a digital value
	 */
	DigitalInput,

	/**
	 * An optional pull up
	 */
	SelectablePullUp,
	/**
	 * An optional pull down
	 */
	SelectablePullDown,

	/**
	 * Write a PWM signal using milliseconds as the value
	 */
	MSPWM_Output,

	/**
	 * Write a PWM using a duty cycle
	 */
	PWM_Output,
	
	/**
	 * Serial transmit
	 */
	SerialTx,
	/**
	 * Serial receive
	 */
	SerialRx,
	
	
	/**
	 * SPI Master in Slave out pin
	 */
	SPI_MISO,
	/**
	 * SPI Master out Slave in pin
	 */
	SPI_MOSI,
	/**
	 * SPI clock
	 */
	SPI_SCK,
	/**
	 * SPI slave select
	 */
	SPI_SS,
	
	
	/**
	 * I2C Clock
	 */
	I2C_SCL,
	/**
	 * I2C data
	 */
	I2C_SDA;
	
}
