package robot.io;


/**
 * @author Mitchell
 * 
 * A RobotObject Model that listens for events from one robot object and forwards it to ones for the given object
 *
 * @param <T> the type of the current object that events are being forwarded to
 * @param <S> the type of the object that is the actual source of the event
 */
public class ForwardingRobotObjectModel<T extends RobotObject, S extends RobotObject> extends RobotObjectModel<T> implements RobotObjectListener<S> {

	/**
	 * @param object
	 */
	public ForwardingRobotObjectModel(T object) {
		super(object);
	}

	@Override
	public void objectUpdated(S source) {
		fireUpdateEvent();
	}

}
