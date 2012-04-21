package robot.imperium.hardware;

import static robot.imperium.hardware.PinCapability.AnalogInput;
import static robot.imperium.hardware.PinCapability.I2C_SCL;
import static robot.imperium.hardware.PinCapability.I2C_SDA;
import static robot.imperium.hardware.PinCapability.Interrupt;
import static robot.imperium.hardware.PinCapability.PWM_Output;
import static robot.imperium.hardware.PinCapability.SPI_MISO;
import static robot.imperium.hardware.PinCapability.SPI_MOSI;
import static robot.imperium.hardware.PinCapability.SPI_SCK;
import static robot.imperium.hardware.PinCapability.SPI_SS;

/**
 * @author Mitchell
 * 
 * Represents the Arduino Duemilanove
 *
 */
public class ArduinoDuemilanove extends ImperiumHardwareConfiguration {
	
	private static final ArduinoDuemilanove INSTANCE = new ArduinoDuemilanove();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static ArduinoDuemilanove get(){
		return INSTANCE;
	}

	private ArduinoDuemilanove() {
		super("Arduino Duemilanove", 490, 5.0);

		addPin(0, "0").addLabels("PD0", "RX");
		addPin(1, "1").addLabels("PD1", "TX");

		addAVRPin(2, "2", Interrupt).addLabels("PD2", "INT0");
		addAVRPin(3, "3", Interrupt, PWM_Output).addLabels("PD3", "INT1");
		addAVRPin(4, "4").addLabels("PD4");
		addAVRPin(5, "5", PWM_Output).addLabels("PD5");
		addAVRPin(6, "6", PWM_Output).addLabels("PD6");
		addAVRPin(7, "7").addLabels("PD7");
		addAVRPin(8, "8").addLabels("PB0");
		addAVRPin(9, "9", PWM_Output).addLabels("PB1");
		addAVRPin(10, "10", PWM_Output, SPI_SS).addLabels("PD2");
		addAVRPin(11, "11", PWM_Output, SPI_MOSI).addLabels("PD3");
		addAVRPin(12, "12", SPI_MISO).addLabels("PD4");
		addAVRPin(13, "13", SPI_SCK).addLabels("PD5");
		
		
		addAVRPin(14, "A0", AnalogInput).addLabels("14", "PC0");
		addAVRPin(15, "A1", AnalogInput).addLabels("15", "PC1");
		addAVRPin(16, "A2", AnalogInput).addLabels("16", "PC2");
		addAVRPin(17, "A3", AnalogInput).addLabels("17", "PC3");
		addAVRPin(18, "A4", AnalogInput, I2C_SDA).addLabels("18", "PC4");
		addAVRPin(19, "A5", AnalogInput, I2C_SCL).addLabels("19", "PC5");
		
		
	}

}
