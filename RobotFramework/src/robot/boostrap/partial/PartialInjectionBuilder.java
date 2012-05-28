package robot.boostrap.partial;

import java.util.*;

import robot.*;
import robot.error.*;
import robot.io.*;

public class PartialInjectionBuilder<T extends RobotObject> {
	
	private final Set<PartialBinding<T>> bindings;

	private final Map<String, Object> params = new HashMap<String, Object>();
	private final RobotObjectStore objectStore;

	public PartialInjectionBuilder(RobotObjectStore objectStore, Set<PartialBinding<T>> bindings) {
		this.objectStore = objectStore;
		this.bindings = bindings;
	}

	public PartialInjectionBuilder<T> with(String name, String objectName) {
		params.put(name, objectStore.getObject(objectName));
		return this;
	}

	public PartialInjectionBuilder<T> withValue(String name, Object value) {
		params.put(name, value);
		return this;
	}

	public T create(String name) {
		T value = create();
		objectStore.putObject(name, value);
		return value;
	}
	public T create() {
		PartialBinding<T> match = null;
		for(PartialBinding<T> binding:bindings){
			if(binding.matches(params)){
				if(match!=null)
					throw new RobotInitializationException("Multiple matches found");
				match = binding;
			}
		}
		if(match==null)
			throw new RobotInitializationException("No matching constructor found");
		return match.get(params);
	}
}
