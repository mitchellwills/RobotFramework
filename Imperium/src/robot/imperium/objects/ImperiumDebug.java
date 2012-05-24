package robot.imperium.objects;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.packet.ImperiumPacket;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;

/**
 * A debug object on an imperium device
 * 
 * @author Mitchell
 *
 */
public class ImperiumDebug extends ImperiumDeviceObject implements UpdatableObject{
	private final RobotObjectModel model = new RobotObjectModel(this);

	@Override
	public void addUpdateListener(RobotObjectListener listener) {
		model.addUpdateListener(listener);
	}

	@Override
	public void removeUpdateListener(RobotObjectListener listener) {
		model.removeUpdateListener(listener);
	}

	private int numUpdatePerSec = 0;
	/**
	 * Create a new Imperium Debug Object
	 * 
	 * @param device
	 */
	public ImperiumDebug(ImperiumDevice device) {
		super(ObjectTypeIds.DEBUG, device, 2, 0);
		init();
	}

	@Override
	public String getName() {
		return "Imperium Debug";
	}

	@Override
	protected void appendConfiguration(ImperiumPacket packet) {
		//none
	}

	@Override
	protected void appendSetValue(ImperiumPacket packet) {
		//none
	}

	@Override
	protected void readValue(ImperiumPacket packet) {
		numUpdatePerSec = packet.readUInteger(2);
	}
	public int getUpdateRate(){
		return numUpdatePerSec;
	}

}
