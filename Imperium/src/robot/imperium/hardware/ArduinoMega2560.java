package robot.imperium.hardware;

import static robot.imperium.PinCapability.DigitalInput;
import static robot.imperium.PinCapability.DigitalOutput;
import static robot.imperium.PinCapability.MSPWM_Output;
import static robot.imperium.PinCapability.PWM_Output;
import static robot.imperium.PinCapability.SelectablePullUp;

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

		addPin(0, "0").addLabels("PE0", "RX0");
		addPin(1, "1").addLabels("PE1", "TX0");

		addPin(2, "2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PE4");
		addPin(3, "3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PE5");
		
		addPin(4, "4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PG5");

		addPin(5, "5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PE3");

		addPin(6, "6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH3");
		addPin(7, "7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH4");
		addPin(8, "8", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH5");
		addPin(9, "9", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PH6");
		
		addPin(10, "10", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB4");
		addPin(11, "11", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB5");
		addPin(12, "12", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB6");
		addPin(13, "13", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output).addLabels("PB7", ONBOARD_LED);
		
		

	}

}
