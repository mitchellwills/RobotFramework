package robot.io.joystick;

import robot.io.ForwardingRobotObjectModel;
import robot.io.RobotObjectListener;

/**
 * @author Mitchell
 * 
 * A virtual joystick whose inputs can be set
 *
 */
public class VirtualJoystick implements Joystick{
	private final ForwardingRobotObjectModel model = new ForwardingRobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	
	private final String name;
	private final VirtualJoystickButton[] buttons;
	private final VirtualJoystickAxis[] axes;
	private final VirtualJoystickDirectional[] directionals;
	/**
	 * Create a virtual joystick
	 * @param name the name of the joystick
	 * @param numButtons
	 * @param numAxes
	 * @param numDirectionals
	 */
	public VirtualJoystick(String name, int numButtons, int numAxes, int numDirectionals){
		this.name = name;
		
		buttons = new VirtualJoystickButton[numButtons];
		for(int i = 0; i<buttons.length; ++i){
			buttons[i] = new VirtualJoystickButton(i);
			buttons[i].addUpdateListener(model);
		}

		axes = new VirtualJoystickAxis[numAxes];
		for(int i = 0; i<axes.length; ++i){
			axes[i] = new VirtualJoystickAxis(i);
			axes[i].addUpdateListener(model);
		}

		directionals = new VirtualJoystickDirectional[numDirectionals];
		for(int i = 0; i<directionals.length; ++i){
			directionals[i] = new VirtualJoystickDirectional(i);
			directionals[i].addUpdateListener(model);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public VirtualJoystickButton getButton(int id) {
		return buttons[id];
	}

	@Override
	public int getButtonCount() {
		return buttons.length;
	}
	
	/**
	 * Set the state of the joystick's button
	 * @param id
	 * @param value
	 */
	public void setButtonState(int id, boolean value){
		getButton(id).set(value);
	}
	/**
	 * Press (and hold) a button on the joystick
	 * @param id
	 */
	public void pressButton(final int id){
		setButtonState(id, true);
	}
	/**
	 * Release a button on the joystick
	 * @param id
	 */
	public void releaseButon(final int id){
		setButtonState(id, false);
	}
	

	@Override
	public VirtualJoystickAxis getAxis(int id) {
		return axes[id];
	}

	@Override
	public int getAxisCount() {
		return axes.length;
	}
	
	/**
	 * Set the value of an axis on the joystick
	 * @param id
	 * @param value
	 */
	public void setAxisValue(int id, double value){
		getAxis(id).set(value);
	}
	

	@Override
	public VirtualJoystickDirectional getDirectional(int id) {
		return directionals[id];
	}

	@Override
	public int getDirectionalCount() {
		return directionals.length;
	}

	
	/**
	 * Set the angle of a directional on the joystick
	 * @param id
	 * @param angle
	 */
	public void setDirectionalAngle(int id, double angle){
		getDirectional(id).setAngle(angle);
	}
	
	/**
	 * Set the magnitude of a directional on the joystick
	 * @param id
	 * @param magnitude
	 */
	public void setDirectionalMagnitude(int id, double magnitude){
		getDirectional(id).setMagnitude(magnitude);
	}

}
