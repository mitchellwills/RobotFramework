
package robot;

import com.google.inject.*;

public class RobotInstanceModule extends AbstractModule {

	private final Robot robot;
	
	public RobotInstanceModule(Robot robot) {
		this.robot = robot;
	}

	@Override
	protected void configure() {
		bind(Robot.class).toInstance(robot);
	}

}
