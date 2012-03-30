package robot.imperium.hardware;

import static robot.imperium.PinCapability.DigitalInput;
import static robot.imperium.PinCapability.DigitalOutput;
import static robot.imperium.PinCapability.MSPWM_Output;
import static robot.imperium.PinCapability.PWM_Output;
import static robot.imperium.PinCapability.SelectablePullUp;
import static robot.imperium.hardware.AtmegaPins.PB4;
import static robot.imperium.hardware.AtmegaPins.PB5;
import static robot.imperium.hardware.AtmegaPins.PB6;
import static robot.imperium.hardware.AtmegaPins.PB7;
import static robot.imperium.hardware.AtmegaPins.PE0;
import static robot.imperium.hardware.AtmegaPins.PE1;
import static robot.imperium.hardware.AtmegaPins.PE3;
import static robot.imperium.hardware.AtmegaPins.PE4;
import static robot.imperium.hardware.AtmegaPins.PE5;
import static robot.imperium.hardware.AtmegaPins.PG5;
import static robot.imperium.hardware.AtmegaPins.PH3;
import static robot.imperium.hardware.AtmegaPins.PH4;
import static robot.imperium.hardware.AtmegaPins.PH5;
import static robot.imperium.hardware.AtmegaPins.PH6;

/**
 * @author Mitchell
 * 
 * Represents the Arduino Mega 2560 board
 *
 */
public class ArduinoMega2560 extends HardwareConfiguration {
	
	private static final ArduinoMega2560 INSTANCE = new ArduinoMega2560();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static ArduinoMega2560 get(){
		return INSTANCE;
	}

	private ArduinoMega2560() {
		super("Arduino Duemilanove", 490);

		addPin(PE0, "0").addLabels("PE0", "RX0");
		addPin(PE1, "1").addLabels("PE1", "TX0");

		addPin(PE4, "2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PE4");
		addPin(PE5, "3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PE5");
		
		addPin(PG5, "4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PG5");

		addPin(PE3, "5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PE3");

		addPin(PH3, "6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH3");
		addPin(PH4, "7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH4");
		addPin(PH5, "8", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH5");
		addPin(PH6, "9", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH6");
		
		addPin(PB4, "10", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB4");
		addPin(PB5, "11", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB5");
		addPin(PB6, "12", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB6");
		addPin(PB7, "13", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB7", ONBOARD_LED);
		
		

	}

}
