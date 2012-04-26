package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.DEBUG_TYPE_ID;
import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.io.InputValue;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;


/**
 * @author Mitchell
 * 
 * A digital output
 *
 */
public class ImperiumDebug extends ImperiumDeviceObject implements InputValue, UpdatableObject {

	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private int value;
	/**
	 * Create a new ImperiumDebugOutput
	 * @param device
	 * @param pin
	 */
	public ImperiumDebug(ImperiumDevice device) {
		super(DEBUG_TYPE_ID, device);
		value = 0;
	}


	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
		if(model!=null)
			model.fireUpdateEvent();
	}

	@Override
	public void message(long[] values) {
		//
	}


}
