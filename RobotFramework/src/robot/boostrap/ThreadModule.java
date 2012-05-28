package robot.boostrap;

import robot.thread.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;

public class ThreadModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new FactoryModuleBuilder()
		.implement(RobotThread.class, RobotThread.class)
		.implement(PeriodicRobotThread.class, PeriodicRobotThread.class)
     .build(RobotThreadFactory.class));
	}
}