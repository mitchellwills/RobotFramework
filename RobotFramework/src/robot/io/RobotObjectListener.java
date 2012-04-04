package robot.io;

public interface RobotObjectListener<T extends RobotObject> {
	public void objectUpdated(T object);
}
