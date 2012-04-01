package robot.io.computerdevices;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * @author Mitchell
 * 
 * Interface used to access the native computer port library
 *
 */
public interface DIJoystick extends Library {
	/**
	 * instance of the port library
	 */
	DIJoystick INSTANCE = (DIJoystick) Native.loadLibrary(
			"libComputerDevicesNative", DIJoystick.class);

	int initDI();
	int enumerateDevices();
	
	Pointer getJoystickReference(int id);
	int getJoystickCount();
	
	
	//DIJoystick class
	String getName(Pointer joystick);
	
	int getNumButtons(Pointer joystick);
	int getNumAxes(Pointer joystick);
	int getNumPOVs(Pointer joystick);

	int getAxis(Pointer joystick, int id);
	boolean getButton(Pointer joystick, int id);
	int getPOV(Pointer joystick, int id);

	String getAxisName(Pointer joystick, int id);
	String getButtonName(Pointer joystick, int id);
	String getPOVName(Pointer joystick, int id);
	
	void cleanup(Pointer joystick);
	void poll(Pointer joystick);
}
