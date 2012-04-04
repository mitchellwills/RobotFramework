package robot.io.computerdevices.joystick;

import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.computerdevices.DIJoystick;
import robot.io.joystick.Joystick;
import robot.io.joystick.JoystickAxis;
import robot.io.joystick.JoystickButton;
import robot.io.joystick.JoystickDirectional;

import com.sun.jna.Pointer;

/**
 * @author Mitchell
 * 
 * A joystick connected to the computer
 *
 */
public class ComputerJoystick implements Joystick{
	static{
		DIJoystick.INSTANCE.initDI();
		DIJoystick.INSTANCE.enumerateDevices();
	}
	
	/**
	 * @return the number of joysticks attacked to the computer
	 */
	public static int getJoystickCount(){
		return DIJoystick.INSTANCE.getJoystickCount();
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
	
	
	
	private Pointer nativeJoystickPointer;
	private ComputerJoystickAxis[] axes;
	private ComputerJoystickButton[] buttons;
	private ComputerJoystickDirectional[] directionals;
	/**
	 * @param id the id of the joystick
	 */
	public ComputerJoystick(int id){
		nativeJoystickPointer = DIJoystick.INSTANCE.getJoystickReference(id);
		
		int axisCount = DIJoystick.INSTANCE.getNumAxes(nativeJoystickPointer);
		axes = new ComputerJoystickAxis[axisCount];
		for(int i = 0; i<axisCount; ++i)
			axes[i] = new ComputerJoystickAxis(this, i);
		
		int buttonCount = DIJoystick.INSTANCE.getNumButtons(nativeJoystickPointer);
		buttons = new ComputerJoystickButton[buttonCount];
		for(int i = 0; i<buttonCount; ++i)
			buttons[i] = new ComputerJoystickButton(this, i);
		
		int directionalCount = DIJoystick.INSTANCE.getNumPOVs(nativeJoystickPointer);
		directionals = new ComputerJoystickDirectional[directionalCount];
		for(int i = 0; i<directionalCount; ++i)
			directionals[i] = new ComputerJoystickDirectional(this, i);
	}

	@Override
	public String getName() {
		return DIJoystick.INSTANCE.getName(nativeJoystickPointer);
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
	
	public void poll(){
		DIJoystick.INSTANCE.poll(nativeJoystickPointer);
		model.fireUpdateEvent();
	}

	Pointer getNativePointer() {
		return nativeJoystickPointer;
	}

}
