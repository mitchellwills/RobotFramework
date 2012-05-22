package robot.imperium.objects;

import robot.imperium.ImperiumDevice;
import robot.imperium.ImperiumDeviceObject;
import robot.imperium.packet.ImperiumPacket;
import robot.io.RobotObjectListener;
import robot.io.RobotObjectModel;
import robot.io.UpdatableObject;

/**
 * A digital input on an imperium device
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

	/**
	 * Create a new Digital Output
	 * 
	 * @param device
	 * @param location 
	 */
	public ImperiumDebug(ImperiumDevice device) {
		super(ObjectTypeIds.DEBUG, device, 2, 0);
		init();
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
		System.out.println(packet.readInteger(2));
	}

}
