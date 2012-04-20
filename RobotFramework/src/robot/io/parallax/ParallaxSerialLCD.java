package robot.io.parallax;

import java.io.IOException;

import robot.error.RobotException;
import robot.io.Output;
import robot.io.serial.SerialInterface;

/**
 * @author Mitchell
 *
 */
public class ParallaxSerialLCD implements Output {
	/**
	 * @author Mitchell
	 * 
	 * represents the different states of the display
	 *
	 */
	public enum DisplayMode{
		/**
		 * display off
		 */
		OFF,
		/**
		 * display on, with cursor off and no blink
		 */
		ON_NCURSOR_NBLINK,
		/**
		 * display on, with cursor off and character blink
		 */
		ON_NCURSOR_BLINK,
		/**
		 * display on, with cursor on and no blink
		 */
		ON_CURSOR_NBLINK,
		/**
		 * display on, with cursor on and character blink
		 */
		ON_CURSOR_BLINK;
	}
	
	
	private final SerialInterface serialPort;
	/**
	 * Create a new LCD device
	 * @param serialPort the serial port used to communicate with the lcd
	 */
	public ParallaxSerialLCD(SerialInterface serialPort){
		this.serialPort = serialPort;
	}

	private void writeByte(int b){
		try {
			serialPort.getOutputStream().write(b);
			serialPort.getOutputStream().flush();
		} catch (IOException e) {
			throw new RobotException("Error writing to serial port output stream", e);
		}
	}
	private void writeBytes(byte[] bytes){
		try {
			serialPort.getOutputStream().write(bytes);
			serialPort.getOutputStream().flush();
		} catch (IOException e) {
			throw new RobotException("Error writing to serial port output stream", e);
		}
	}
	
	/**
	 * move the cursor left one position
	 */
	public void left(){
		writeByte(0x08);
	}
	
	/**
	 * move the cursor right one position
	 */
	public void right(){
		writeByte(0x09);
	}
	
	/**
	 * move the cursor to the next line (horizontal position stays the same)
	 */
	public void lineFeed(){
		writeByte(0x0A);
	}
	
	/**
	 * clear the screen
	 * the cursor will be returned to 
	 */
	public void clear(){
		writeByte(0x0C);
	}
	
	/**
	 * move the cursor to the next line (horizontal position moves to 0)
	 */
	public void carriageReturn(){
		writeByte(0x0D);
	}
	
	/**
	 * Turn the display's backlight on or off
	 * @param enabled true if the backlight should be on
	 */
	public void setBacklight(boolean enabled){
		if(enabled)
			writeByte(0x11);
		else
			writeByte(0x12);
	}
	
	/**
	 * Set the state of the display
	 * @param mode display mode
	 */
	public void setDisplayMode(DisplayMode mode){
		switch(mode){
		case OFF:
			writeByte(0x15);
			break;
		case ON_CURSOR_BLINK:
			writeByte(0x19);
			break;
		case ON_CURSOR_NBLINK:
			writeByte(0x18);
			break;
		case ON_NCURSOR_BLINK:
			writeByte(0x17);
			break;
		case ON_NCURSOR_NBLINK:
			writeByte(0x16);
			break;
		}
	}
	
	/**
	 * Set the position of the cursor on the display
	 * @param line the line (vertical position)
	 * @param position the horizontal position of the cursor
	 */
	public void setCursorPosition(int line, int position){
		writeByte( 0x80+ line*20 + position );
	}
	
	
	/**
	 * Append a character at the current position of the cursor
	 * @param c the character to be written (must be ascii 0x20-0x7F)
	 */
	public void append(char c){
		writeByte(c);
	}
	
	/**
	 * Append a string at the current position of the cursor
	 * @param str the string to be displayed
	 */
	public void append(String str){
		writeBytes(str.getBytes());
	}
}
