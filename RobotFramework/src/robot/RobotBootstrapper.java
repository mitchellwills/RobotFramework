package robot;

import java.util.*;

import robot.boostrap.*;
import robot.boostrap.partial.*;
import robot.error.*;
import robot.io.host.*;
import robot.thread.*;

import com.google.inject.*;

public class RobotBootstrapper {
	private Stage stage;
	private Class<? extends Robot> robotClass;
	private Collection<Module> modules = new HashSet<Module>();
	private Collection<PartialModule> partialModules = new HashSet<PartialModule>();

	public RobotBootstrapper(Class<? extends Robot> robotClass, Class<? extends Host> hostClass, String[] args) {
		this.robotClass = robotClass;
		addModule(new SimpleSingletonModule<RobotThreadFactory>(RobotThreadFactory.class, RobotThreadFactoryImpl.class));
		addModule(new SimpleSingletonModule<RobotObjectStore>(RobotObjectStore.class, RobotObjectStoreImpl.class));
		addModule(new SimpleSingletonModule<Host>(Host.class, hostClass));
		addModule(new BoostrapperModule(args));
		stage = Stage.PRODUCTION;
	}
	
	private static final class BoostrapperModule extends AbstractModule{
		private final String[] commandLineArgs;
		BoostrapperModule(String[] commandLineArgs){
			this.commandLineArgs = commandLineArgs;
		}
		@Override
		protected void configure() {
			bind(String[].class).annotatedWith(CommandLineArgs.class).toInstance(commandLineArgs);
		}
	}
	
	public void addModule(Module module){
		modules.add(module);
	}
	public void addModule(PartialModule module){
		partialModules.add(module);
	}
	
	public void setHost(Class<? extends Host> hostClass){
		addModule(new SimpleSingletonModule<Host>(Host.class, hostClass));
	}

	private Robot robot = null;
	public Robot start() {
		if(robot!=null)
			throw new RobotInitializationException("Cannot start the robot bootstrapper more than once");
		try {
			robot = robotClass.newInstance();
		} catch (Exception e) {
			throw new RobotInitializationException("The robot class must have a public default contructor", e);
		}
		modules.add(new RobotInstanceModule(robot));
		Injector injector = Guice.createInjector(stage, modules);
		RobotObjectStore objectStore = injector.getInstance(RobotObjectStore.class);
		objectStore.putObject("host", injector.getInstance(Host.class));
		robot.inject(injector, new PartialInjector(injector, objectStore, partialModules), objectStore);

		robot.initializeIO();
		robot.run();
		return robot;
	}
}
