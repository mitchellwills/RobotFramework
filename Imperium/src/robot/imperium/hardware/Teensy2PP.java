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
import static robot.imperium.hardware.PinCapability.SerialRx;
import static robot.imperium.hardware.PinCapability.SerialTx;

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
		super("Teensy++ 2.0", 490, 5.0);
		
		addAVRPin(0, "0", PWM_Output, Interrupt, I2C_SCL).addLabels("PD0", "INT0", "SCL");
		addAVRPin(1, "1", PWM_Output, Interrupt, I2C_SDA).addLabels("PD1", "INT1", "SDA");
		addAVRPin(2, "2", Interrupt, SerialRx).addLabels("PD2", "INT2", "RX");
		addAVRPin(3, "3", Interrupt, SerialTx).addLabels("PD3", "INT3", "TX");
		addAVRPin(4, "4").addLabels("PD4");
		addAVRPin(5, "5").addLabels("PD5");
		addAVRPin(6, "6").addLabels("PD6", HardwareConfiguration.ONBOARD_LED);
		addAVRPin(7, "7").addLabels("PD7");
		addAVRPin(8, "8").addLabels("PE0");
		addAVRPin(9, "9").addLabels("PE1");
		addAVRPin(10, "10").addLabels("PC0");
		addAVRPin(11, "11").addLabels("PC1");
		addAVRPin(12, "12").addLabels("PC2");
		addAVRPin(13, "13").addLabels("PC3");
		addAVRPin(14, "14", PWM_Output).addLabels("PC4");
		addAVRPin(15, "15", PWM_Output).addLabels("PC5");
		addAVRPin(16, "16", PWM_Output).addLabels("PC6");
		addAVRPin(17, "17").addLabels("PC7");
		addAVRPin(18, "18", Interrupt).addLabels("PE6", "INT6");
		addAVRPin(19, "19", Interrupt).addLabels("PE7", "INT7");
		addAVRPin(20, "20", SPI_SS).addLabels("PB0", "SS");
		addAVRPin(21, "21", SPI_SCK).addLabels("PB1", "SCK");
		addAVRPin(22, "22", SPI_MOSI).addLabels("PB2", "MOSI");
		addAVRPin(23, "23", SPI_MISO).addLabels("PB3", "MISO");
		addAVRPin(24, "24", PWM_Output).addLabels("PB4");
		addAVRPin(25, "25", PWM_Output).addLabels("PB5");
		addAVRPin(26, "26", PWM_Output).addLabels("PB6");
		addAVRPin(27, "27", PWM_Output).addLabels("PB7");
		addAVRPin(28, "28").addLabels("PA0");
		addAVRPin(29, "29").addLabels("PA1");
		addAVRPin(30, "30").addLabels("PA2");
		addAVRPin(31, "31").addLabels("PA3");
		addAVRPin(32, "32").addLabels("PA4");
		addAVRPin(33, "33").addLabels("PA5");
		addAVRPin(34, "34").addLabels("PA6");
		addAVRPin(35, "35").addLabels("PA7");
		addAVRPin(36, "36", Interrupt).addLabels("PE4", "INT4");
		addAVRPin(37, "37", Interrupt).addLabels("PE5", "INT5");
		addAVRPin(38, "38", AnalogInput).addLabels("PF0", "A0");
		addAVRPin(39, "39", AnalogInput).addLabels("PF1", "A1");
		addAVRPin(40, "40", AnalogInput).addLabels("PF2", "A2");
		addAVRPin(41, "41", AnalogInput).addLabels("PF3", "A3");
		addAVRPin(42, "42", AnalogInput).addLabels("PF4", "A4");
		addAVRPin(43, "43", AnalogInput).addLabels("PF5", "A5");
		addAVRPin(44, "44", AnalogInput).addLabels("PF6", "A6");
		addAVRPin(45, "45", AnalogInput).addLabels("PF7", "A7");
	}
	

}
