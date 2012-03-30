package robot.imperium.hardware;

import static robot.imperium.PinCapability.AnalogInput;
import static robot.imperium.PinCapability.DigitalInput;
import static robot.imperium.PinCapability.DigitalOutput;
import static robot.imperium.PinCapability.I2C_SCL;
import static robot.imperium.PinCapability.I2C_SDA;
import static robot.imperium.PinCapability.MSPWM_Output;
import static robot.imperium.PinCapability.PWM_Output;
import static robot.imperium.PinCapability.SPI_MISO;
import static robot.imperium.PinCapability.SPI_MOSI;
import static robot.imperium.PinCapability.SPI_SCK;
import static robot.imperium.PinCapability.SPI_SS;
import static robot.imperium.PinCapability.SelectablePullUp;
import static robot.imperium.hardware.AtmegaPins.PB0;
import static robot.imperium.hardware.AtmegaPins.PB1;
import static robot.imperium.hardware.AtmegaPins.PB2;
import static robot.imperium.hardware.AtmegaPins.PB3;
import static robot.imperium.hardware.AtmegaPins.PB4;
import static robot.imperium.hardware.AtmegaPins.PB5;
import static robot.imperium.hardware.AtmegaPins.PC0;
import static robot.imperium.hardware.AtmegaPins.PC1;
import static robot.imperium.hardware.AtmegaPins.PC2;
import static robot.imperium.hardware.AtmegaPins.PC3;
import static robot.imperium.hardware.AtmegaPins.PC4;
import static robot.imperium.hardware.AtmegaPins.PC5;
import static robot.imperium.hardware.AtmegaPins.PD0;
import static robot.imperium.hardware.AtmegaPins.PD1;
import static robot.imperium.hardware.AtmegaPins.PD2;
import static robot.imperium.hardware.AtmegaPins.PD3;
import static robot.imperium.hardware.AtmegaPins.PD4;
import static robot.imperium.hardware.AtmegaPins.PD5;
import static robot.imperium.hardware.AtmegaPins.PD6;
import static robot.imperium.hardware.AtmegaPins.PD7;

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

		addPin(PD0, "0").addLabels("PD0", "TX");
		
		addPin(PD1, "1").addLabels("PD1", "RX");
		
		addPin(PD2, "2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output).addLabels("PD2", "INT0");
		
		addPin(PD3, "3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PD3", "INT1");
		
		addPin(PD4, "4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output).addLabels("PD4", "T0");
		
		addPin(PD5, "5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PD5", "T1");
		
		addPin(PD6, "6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PD6", "AIN0");
		
		addPin(PD7, "7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output).addLabels("PD7", "AIN1");
		
		addPin(PB0, "8", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output).addLabels("PB0", "ICP");
		
		addPin(PB1, "9", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB1", "OC1");
		
		addPin(PB2, "10", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SPI_SS).addLabels("PB2", "SS");
		
		addPin(PB3, "11", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SPI_MOSI).addLabels("PB3", "MOSI");
		
		addPin(PB4, "12", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_MISO).addLabels("PB4", "MISO");
		
		addPin(PB5, "13", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, SPI_SCK).addLabels("PB5", "SCK", HardwareConfiguration.ONBOARD_LED);
		
		

		addPin(PC0, "A0", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC0", "ADC0");
		
		addPin(PC1, "A1", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC1", "ADC1");
		
		addPin(PC2, "A2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC2", "ADC2");
		
		addPin(PC3, "A3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput).addLabels("PC3", "ADC3");
		
		addPin(PC4, "A4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput, I2C_SDA).addLabels("PC4", "ADC4", "SDA");
		
		addPin(PC5, "A5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, AnalogInput, I2C_SCL).addLabels("PC5", "ADC5", "SCL");
	}

}
