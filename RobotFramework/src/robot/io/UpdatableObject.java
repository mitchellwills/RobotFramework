package robot.io;

public interface UpdatableObject {

	public void addUpdateListener(RobotObjectListener listener);
	public void removeUpdateListener(RobotObjectListener listener);
}
