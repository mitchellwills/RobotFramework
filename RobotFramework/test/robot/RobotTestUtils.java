package robot;

import java.lang.reflect.Field;

@SuppressWarnings("javadoc")
public class RobotTestUtils {
	public static void initFakeRobot(){
		Robot.go(new FakeRobot());
	}
	public static void cleanupFakeRobot() {
		try{
			Field instanceField = Robot.class.getDeclaredField("INSTANCE");
			instanceField.setAccessible(true);
			Field initializedField = Robot.class.getDeclaredField("initialized");
			initializedField.setAccessible(true);
			instanceField.set(null, null);
			initializedField.set(null, false);
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
