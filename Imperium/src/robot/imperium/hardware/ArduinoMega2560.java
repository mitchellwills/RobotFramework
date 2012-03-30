package robot.imperium.hardware;

import static robot.imperium.PinCapability.*;

/**
 * @author Mitchell
 * 
 * Represents the Arduino Mega 2560 board
 *
 */
public class ArduinoMega2560 extends DefaultHardwareConfiguration {
	
	private static final ArduinoMega2560 INSTANCE = new ArduinoMega2560();
	/**
	 * @return the singleton instance of the configuration
	 */
	public static ArduinoMega2560 get(){
		return INSTANCE;
	}

	private ArduinoMega2560() {
		super("Arduino Duemilanove", 100, 490);

		addCapability(0, "0");
		addCapability(1, "1");

		addCapability(2, "2", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(3, "3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(4, "4", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(5, "5", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		

		addCapability(16, "16", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(17, "17", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);

		addCapability(89, "TX3", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SerialTx);//Tx3
		addCapability(6, "6", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SerialRx);//Rx3
		
		addCapability(7, "7", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SerialTx);//Tx2
		addCapability(8, "8", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SerialRx);//Rx2
		
		addCapability(9, "9", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SerialTx);//Tx1, Rx1 is pin 53

		addCapability(10, "10", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(11, "11", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(12, "12", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(13, "13", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);



		addCapability(14, "14", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(15, "15", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);


		addCapability(18, "18", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(19, "19", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		
		addCapability(20, "20", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, I2C_SDA);
		addCapability(21, "21", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, I2C_SCL);

		addCapability(22, "22", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(23, "23", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(24, "24", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(25, "25", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(26, "26", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(27, "27", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(28, "28", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(29, "29", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		

		addCapability(30, "30", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(31, "31", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(32, "32", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(33, "33", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(34, "34", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(35, "35", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(36, "36", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(37, "37", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		

		addCapability(38, "38", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(39, "39", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		

		addCapability(40, "40", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(41, "41", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(42, "42", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(43, "43", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);

		addCapability(44, "44", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(45, "45", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		addCapability(46, "46", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output);
		
		addCapability(47, "47", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(48, "48", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		addCapability(49, "49", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);

		addCapability(50, "50", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output);
		
		addCapability(51, "51", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, I2C_SCL);
		addCapability(52, "52", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, I2C_SDA);
		addCapability(53, "53", DigitalInput, DigitalOutput, SelectablePullUp, MSPWM_Output, PWM_Output, SerialRx);//Rx1
		
		
		

	}
	
	/**
	 * @return the pin of the LED that is on the Arduino Mega 2560 board
	 */
	public int getOnboardLEDPin(){
		return getPin("13");
	}

}
