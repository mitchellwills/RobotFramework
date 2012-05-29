package robot.io.computerdevices.joystick;

import robot.*;
import robot.error.*;
import robot.io.*;
import robot.io.computerdevices.*;
import robot.io.joystick.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;
import com.sun.jna.*;

/**
 * @author Mitchell
 * 
 * A joystick connected to the computer
 *
 */
public class ComputerJoystick implements Joystick, Nameable{
	
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
	@Inject public ComputerJoystick(@Assisted(PARAM_LOCATION) int id){
		if(id<0 || id>=getJoystickCount())
			throw new RobotInitializationException("Illegal computer joystick id: "+id+". There are  "+getJoystickCount()+" joysticks on the system");
		
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
		if(id<0 || id>=getButtonCount())
			throw new RobotInitializationException("Illegal button id: "+id+". There are  "+getButtonCount()+" buttons on the joystick: "+getName());
		return buttons[id];
	}

	@Override
	public int getButtonCount() {
		return buttons.length;
	}

	@Override
	public JoystickAxis getAxis(int id) {
		if(id<0 || id>=getAxisCount())
			throw new RobotInitializationException("Illegal axis id: "+id+". There are  "+getAxisCount()+" axes on the joystick: "+getName());
		return axes[id];
	}

	@Override
	public int getAxisCount() {
		return axes.length;
	}

	@Override
	public JoystickDirectional getDirectional(int id) {
		if(id<0 || id>=getDirectionalCount())
			throw new RobotInitializationException("Illegal directional id: "+id+". There are  "+getDirectionalCount()+" directionals on the joystick: "+getName());
		return directionals[id];
	}

	@Override
	public int getDirectionalCount() {
		return directionals.length;
	}
	
	/**
	 * poll the device to update
	 */
	public void poll(){
		DIJoystick.INSTANCE.poll(nativeJoystickPointer);
		model.fireUpdateEvent();
	}

	Pointer getNativePointer() {
		return nativeJoystickPointer;
	}

}
