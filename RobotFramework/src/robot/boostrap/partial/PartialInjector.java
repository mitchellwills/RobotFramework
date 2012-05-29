package robot.boostrap.partial;

import java.util.*;

import robot.*;
import robot.io.*;

import com.google.inject.*;


public class PartialInjector {
	private final Map<Class<?>, PartialBinding<?>> bindings;
	private final RobotObjectStore objectStore;
	private final Injector injector;
	
	public PartialInjector(Injector injector, RobotObjectStore objectStore, Iterable<? extends PartialModule> modules) {
		this(injector, objectStore, new HashMap<Class<?>, PartialBinding<?>>(), modules);
	}

	private PartialInjector(Injector injector, RobotObjectStore objectStore, Map<Class<?>, PartialBinding<?>> existingBindings, Iterable<? extends PartialModule> modules) {
		this.injector = injector;
		this.objectStore = objectStore;
		bindings = existingBindings;
		if(modules!=null){
			for (PartialModule module : modules)
				module.configure(injector, bindings);
		}
	}
	public PartialInjector createChildInjector(Injector newInjector, Iterable<? extends PartialModule> partialInjectionModules) {
		Injector injector;
		if(newInjector==null)
			injector = this.injector;
		else
			injector = newInjector;
		Map<Class<?>, PartialBinding<?>> bindings = new HashMap<Class<?>, PartialBinding<?>>();
		bindings.putAll(this.bindings);
		return new PartialInjector(injector, objectStore, bindings, partialInjectionModules);
	}

	public <T extends RobotObject> PartialInjectionBuilder<T> builderFor(Class<T> type){
		PartialBinding<T> typeBinding = (PartialBinding<T>) bindings.get(type);
		if(typeBinding==null){
			typeBinding = new PartialBindingBuilder<T>(type, type).build(injector);
			bindings.put(type, typeBinding);//cache the binding for next time
		}
		return new PartialInjectionBuilder<T>(objectStore, typeBinding);
	}

}
