package robot;

import robot.io.*;

public interface RobotObjectStore {
	public <T extends RobotObject> T putObject(String name, T object);
	public RobotObject getObject(String name);
	public void addListener(RobotListener listener);
	public void removeListener(RobotListener listener);
}
