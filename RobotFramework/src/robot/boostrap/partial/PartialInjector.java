package robot.boostrap.partial;

import java.util.*;

import robot.*;
import robot.io.*;

import com.google.common.collect.*;
import com.google.inject.*;


public class PartialInjector {
	private HashMultimap<Class<?>, PartialBinding<?>> bindings = HashMultimap.create();
	private final RobotObjectStore objectStore;
	
	public PartialInjector(Injector injector, RobotObjectStore objectStore, Iterable<PartialModule> modules) {
		this.objectStore = objectStore;
		for (PartialModule module : modules)
			module.configure(injector, bindings);
	}

	public <T extends RobotObject> PartialInjectionBuilder<T> builderFor(Class<T> type){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set<PartialBinding<T>> typeBindings = (Set) bindings.get(type);
		return new PartialInjectionBuilder<T>(objectStore, typeBindings);
	}

}
