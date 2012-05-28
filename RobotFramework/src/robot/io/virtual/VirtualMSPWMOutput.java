package robot.io.virtual;

import robot.io.*;
import robot.io.pwmms.*;

/**
 * @author Mitchell
 * 
 * A MSPWMOutput whose value can be set but will not effect any real world change
 *
 */
public class VirtualMSPWMOutput implements MSPWMOutput{
	private final RobotObjectModel model = new RobotObjectModel(this);
	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}
	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}
	

	private int ms = MSPWMOutput.DISABLED;
	@Override
	public void set(int ms) {
		this.ms = ms;
		model.fireUpdateEvent();
	}
	@Override
	public int get() {
		return ms;
	}
	
	
	@Override
	public void setValue(double value) {
		set((int)value);
	}
	@Override
	public double getValue() {
		return get();
	}


}
