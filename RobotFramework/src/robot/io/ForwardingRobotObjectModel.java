package robot.io;


/**
 * @author Mitchell
 * 
 * A RobotObject Model that listens for events from one robot object and forwards it to ones for the given object
 */
public class ForwardingRobotObjectModel extends RobotObjectModel implements RobotObjectListener {

	/**
	 * @param object
	 */
	public ForwardingRobotObjectModel(RobotObject object) {
		super(object);
	}

	@Override
	public void objectUpdated(RobotObject source) {
		fireUpdateEvent();
	}

}
