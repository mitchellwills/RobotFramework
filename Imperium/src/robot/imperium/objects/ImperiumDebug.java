package robot.imperium.objects;

import static robot.imperium.objects.ObjectTypeIds.DEBUG_TYPE_ID;
import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;


/**
 * @author Mitchell
 * 
 * A digital output
 *
 */
public class ImperiumDebug extends ImperiumDeviceObject {

	/**
	 * Create a new ImperiumDebugOutput
	 * @param device
	 * @param pin
	 */
	public ImperiumDebug(ImperiumDevice device) {
		super(DEBUG_TYPE_ID, device);
	}
	
	@Override
	public void initialize(){
		//nothing to initialize
	}

	@Override
	public void setValue(int value) {
		System.out.println("Debug Value: "+value);
	}

	@Override
	public void message(long[] values) {
		//
	}


}
