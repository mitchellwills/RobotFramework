package robot.io.computerdevices.joystick;

import robot.error.RobotInitializationException;
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
	private static ComputerJoystick[] joysticks;
	static{
		DIJoystick.INSTANCE.initDI();
		DIJoystick.INSTANCE.enumerateDevices();
		joysticks = new ComputerJoystick[DIJoystick.INSTANCE.getJoystickCount()];
	}
	
	/**
	 * @return the number of joysticks attacked to the computer
	 */
	public static int getJoystickCount(){
		return joysticks.length;
	}
	/**
	 * @param id the id of the joystick on the system
	 * @return a joystick with the given id
	 */
	public static ComputerJoystick getJoystick(int id){
		if(id<0 || id>=getJoystickCount())
			throw new RobotInitializationException("Illegal computer joystick id: "+id+". There are  "+getJoystickCount()+" joysticks on the system");
		ComputerJoystick joystick = joysticks[id];
		if(joystick != null)
			return joystick;
		return joysticks[id] = new ComputerJoystick(id);
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
	ComputerJoystick(int id){
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
	
	public void poll(){
		DIJoystick.INSTANCE.poll(nativeJoystickPointer);
		model.fireUpdateEvent();
	}

	Pointer getNativePointer() {
		return nativeJoystickPointer;
	}

}
