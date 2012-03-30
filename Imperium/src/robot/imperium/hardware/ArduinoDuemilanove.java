package robot.imperium.hardware;

import static robot.imperium.hardware.PinCapability.Interrupt;
import static robot.imperium.hardware.PinCapability.AnalogInput;
import static robot.imperium.hardware.PinCapability.DigitalInput;
import static robot.imperium.hardware.PinCapability.DigitalOutput;
import static robot.imperium.hardware.PinCapability.I2C_SCL;
import static robot.imperium.hardware.PinCapability.I2C_SDA;
import static robot.imperium.hardware.PinCapability.MSPWM_Output;
import static robot.imperium.hardware.PinCapability.PWM_Output;
import static robot.imperium.hardware.PinCapability.SPI_MISO;
import static robot.imperium.hardware.PinCapability.SPI_MOSI;
import static robot.imperium.hardware.PinCapability.SPI_SCK;
import static robot.imperium.hardware.PinCapability.SPI_SS;
import static robot.imperium.hardware.PinCapability.SelectablePullUp;

/**
 * @author Mitchell
 * 
 * Represents the Arduino Duemilanove
 *
 */
public class ArduinoDuemilanove extends HardwareConfiguration {
	
	private static final ArduinoDuemilanove INSTANCE = new ArduinoDuemilanove();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static ArduinoDuemilanove get(){
		return INSTANCE;
	}

	private ArduinoDuemilanove() {
		super("Arduino Duemilanove", 490);

		addPin(0, "0").addLabels("PD0", "TX");
		
		addPin(1, "1").addLabels("PD1", "RX");
		
		addPin(2, "2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, Interrupt).addLabels("PD2", "INT0");
		
		addPin(3, "3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, Interrupt).addLabels("PD3", "INT1");
		
		addPin(4, "4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output).addLabels("PD4", "T0");
		
		addPin(5, "5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PD5", "T1");
		
		addPin(6, "6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PD6", "AIN0");
		
		addPin(7, "7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output).addLabels("PD7", "AIN1");
		
		addPin(8, "8", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output).addLabels("PB0", "ICP");
		
		addPin(9, "9", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB1", "OC1");
		
		addPin(10, "10", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SPI_SS).addLabels("PB2", "SS");
		
		addPin(11, "11", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SPI_MOSI).addLabels("PB3", "MOSI");
		
		addPin(12, "12", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_MISO).addLabels("PB4", "MISO");
		
		addPin(13, "13", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_SCK).addLabels("PB5", "SCK", HardwareConfiguration.ONBOARD_LED);
		
		

		addPin(14, "A0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC0", "ADC0");
		
		addPin(15, "A1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC1", "ADC1");
		
		addPin(16, "A2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC2", "ADC2");
		
		addPin(17, "A3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC3", "ADC3");
		
		addPin(18, "A4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput, I2C_SDA).addLabels("PC4", "ADC4", "SDA");
		
		addPin(19, "A5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput, I2C_SCL).addLabels("PC5", "ADC5", "SCL");
	}

}
