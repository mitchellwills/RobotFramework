package robot.boostrap.partial;

import robot.io.*;

import com.google.inject.*;

public class ContextBuilder {
	private final BuilderContext context;
	private final Injector injector;
	private final PartialInjector partialInjector;

	public ContextBuilder(BuilderContext context, Injector injector, PartialInjector partialInjector){
		this.context = context;
		this.injector = injector.createChildInjector(context.getInjectionModules());
		this.partialInjector = partialInjector.createChildInjector(this.injector, context.getPartialInjectionModules());
	}
	
	public <T extends RobotObject> PartialInjectionBuilder<T> getBuilder(Class<T> type){
		return partialInjector.builderFor(type).with(context.getPartialParameters());
	}
	
	public <T> T get(Class<T> type){
		return injector.getInstance(type);
	}
}
