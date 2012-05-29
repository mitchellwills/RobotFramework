package robot.io.computerdevices.jxinput;

import robot.*;
import robot.io.*;
import robot.io.joystick.*;
import de.hardcode.jxinput.*;

/**
 * @author Mitchell
 * 
 * Represents a joystick on the computer using the JXInput library
 *
 */
public class JXInputJoystick implements Joystick, Nameable{
	
	/**
	 * @param id
	 * @return a joystick that is attached to the computer
	 */
	public static JXInputJoystick getJoystick(int id){
		return new JXInputJoystick(JXInputManager.getJXInputDevice(id));
	}
	/**
	 * @return the number of joysticks attached to the computer
	 */
	public static int getJoystickCount(){
		return JXInputManager.getNumberOfDevices();
	}
	
	private final JXInputDevice nativeDevice;
	private final JoystickButton[] buttons;
	private final JoystickAxis[] axes;
	private final JoystickDirectional[] directionals;
	private JXInputJoystick(JXInputDevice nativeDevice){
		this.nativeDevice = nativeDevice;
		
		buttons = new JoystickButton[nativeDevice.getNumberOfButtons()];
		for(int i = 0; i<buttons.length; ++i)
			buttons[i] = new JXInputJoystickButton(this, nativeDevice.getButton(i));
		
		axes = new JoystickAxis[nativeDevice.getNumberOfAxes()];
		for(int i = 0; i<axes.length; ++i)
			axes[i] = new JXInputJoystickAxis(this, nativeDevice.getAxis(i));
		
		directionals = new JoystickDirectional[nativeDevice.getNumberOfDirectionals()];
		for(int i = 0; i<directionals.length; ++i)
			directionals[i] = new JXInputJoystickDirectional(this, nativeDevice.getDirectional(i));
	}
	
	
	@Override
	public String getName() {
		return nativeDevice.getName();
	}

	@Override
	public JoystickButton getButton(int id) {
		return buttons[id];
	}

	@Override
	public int getButtonCount() {
		return buttons.length;
	}

	@Override
	public JoystickAxis getAxis(int id) {
		return axes[id];
	}

	@Override
	public int getAxisCount() {
		return axes.length;
	}

	@Override
	public JoystickDirectional getDirectional(int id) {
		return directionals[id];
	}

	@Override
	public int getDirectionalCount() {
		return directionals.length;
	}
	
	/**
	 * This does not actually poll the device but instead just fires the update listeners
	 */
	public void poll(){
		model.fireUpdateEvent();
	}
	
	

	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

}
