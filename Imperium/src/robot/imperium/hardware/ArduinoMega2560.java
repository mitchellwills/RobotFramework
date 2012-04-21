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
 * Represents the Arduino Mega 2560 board
 *
 */
public class ArduinoMega2560 extends ImperiumHardwareConfiguration {
	
	private static final ArduinoMega2560 INSTANCE = new ArduinoMega2560();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static ArduinoMega2560 get(){
		return INSTANCE;
	}

	private ArduinoMega2560() {
		super("Arduino Mega 2560", 490, 5.0);

		addPin(0, "0").addLabels("PE0", "RX0", "RX");
		addPin(1, "1").addLabels("PE1", "TX0", "TX");

		addAVRPin(2, "2", PWM_Output, Interrupt).addLabels("PE4", "INT0");
		addAVRPin(3, "3", PWM_Output, Interrupt).addLabels("PE5", "INT1");
		addAVRPin(4, "4", PWM_Output).addLabels("PG5");
		addAVRPin(5, "5", PWM_Output).addLabels("PE3");
		
		addAVRPin(6, "6", PWM_Output).addLabels("PH3");
		addAVRPin(7, "7", PWM_Output).addLabels("PH4");
		addAVRPin(8, "8", PWM_Output).addLabels("PH5");
		addAVRPin(9, "9", PWM_Output).addLabels("PH6");
		addAVRPin(10, "10", PWM_Output).addLabels("PB4");
		addAVRPin(11, "11", PWM_Output).addLabels("PB5");
		addAVRPin(12, "12", PWM_Output).addLabels("PB6");
		addAVRPin(13, "13", PWM_Output).addLabels("PB7", ONBOARD_LED);
		addAVRPin(14, "14", SerialTx).addLabels("PJ1", "TX3");
		addAVRPin(15, "15", SerialRx).addLabels("PJ0", "RX3");
		addAVRPin(16, "16", PWM_Output, SerialTx).addLabels("PH1", "TX2");
		addAVRPin(17, "17", PWM_Output, SerialRx).addLabels("PH0", "RX2");
		addAVRPin(18, "18", SerialTx, Interrupt).addLabels("PD3", "TX1", "INT5");
		addAVRPin(19, "19", SerialRx, Interrupt).addLabels("PD2", "RX1", "INT4");
		addAVRPin(20, "20", Interrupt, I2C_SDA).addLabels("PD1", "INT3", "SDA");
		addAVRPin(21, "21", Interrupt, I2C_SCL).addLabels("PD0", "INT2", "SCL");
		addAVRPin(22, "22").addLabels("PA0");
		addAVRPin(23, "23").addLabels("PA1");
		addAVRPin(24, "24").addLabels("PA2");
		addAVRPin(25, "25").addLabels("PA3");
		addAVRPin(26, "26").addLabels("PA4");
		addAVRPin(27, "27").addLabels("PA5");
		addAVRPin(28, "28").addLabels("PA6");
		addAVRPin(29, "29").addLabels("PA7");
		addAVRPin(30, "30").addLabels("PC7");
		addAVRPin(31, "31").addLabels("PC6");
		addAVRPin(32, "32").addLabels("PC5");
		addAVRPin(33, "33").addLabels("PC4");
		addAVRPin(34, "34").addLabels("PC3");
		addAVRPin(35, "35").addLabels("PC2");
		addAVRPin(36, "36").addLabels("PC1");
		addAVRPin(37, "37").addLabels("PC0");
		addAVRPin(38, "38").addLabels("PD7");
		addAVRPin(39, "39").addLabels("PG2");
		addAVRPin(40, "40").addLabels("PG1");
		addAVRPin(41, "41").addLabels("PG0");
		addAVRPin(42, "42").addLabels("PL7");
		addAVRPin(43, "43").addLabels("PL6");
		addAVRPin(44, "44", PWM_Output).addLabels("PL5");
		addAVRPin(45, "45", PWM_Output).addLabels("PL4");
		addAVRPin(46, "46", PWM_Output).addLabels("PL3");
		addAVRPin(47, "47").addLabels("PL2");
		addAVRPin(48, "48").addLabels("PL1");
		addAVRPin(49, "49").addLabels("PL0");
		addAVRPin(50, "50", SPI_MISO).addLabels("PB3", "MISO");
		addAVRPin(51, "51", SPI_MOSI).addLabels("PB2", "MOSI");
		addAVRPin(52, "52", SPI_SCK).addLabels("PB1", "SCK");
		addAVRPin(53, "53", SPI_SS).addLabels("PB0", "SS");

		addAVRPin(54, "A0", AnalogInput).addLabels("54", "PF0");
		addAVRPin(55, "A1", AnalogInput).addLabels("55", "PF1");
		addAVRPin(56, "A2", AnalogInput).addLabels("56", "PF2");
		addAVRPin(57, "A3", AnalogInput).addLabels("57", "PF3");
		addAVRPin(58, "A4", AnalogInput).addLabels("58", "PF4");
		addAVRPin(59, "A5", AnalogInput).addLabels("59", "PF5");
		addAVRPin(60, "A6", AnalogInput).addLabels("60", "PF6");
		addAVRPin(61, "A7", AnalogInput).addLabels("61", "PF7");
		addAVRPin(62, "A8", AnalogInput).addLabels("62", "PK0");
		addAVRPin(63, "A9", AnalogInput).addLabels("63", "PK1");
		addAVRPin(64, "A10", AnalogInput).addLabels("64", "PK2");
		addAVRPin(65, "A11", AnalogInput).addLabels("65", "PK3");
		addAVRPin(66, "A12", AnalogInput).addLabels("66", "PK4");
		addAVRPin(67, "A13", AnalogInput).addLabels("67", "PK5");
		addAVRPin(68, "A14", AnalogInput).addLabels("68", "PK6");
		addAVRPin(69, "A15", AnalogInput).addLabels("69", "PK7");
	}

}
