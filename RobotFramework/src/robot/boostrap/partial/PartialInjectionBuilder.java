package robot.boostrap.partial;

import java.util.*;

import robot.*;
import robot.io.*;

public class PartialInjectionBuilder<T extends RobotObject> {
	
	private final PartialBinding<T> binding;

	private final Map<String, Object> params = new HashMap<String, Object>();
	private final RobotObjectStore objectStore;

	public PartialInjectionBuilder(RobotObjectStore objectStore, PartialBinding<T> binding) {
		this.objectStore = objectStore;
		this.binding = binding;
	}

	public PartialInjectionBuilder<T> with(String name, Object value) {
		params.put(name, value);
		return this;
	}

	public PartialInjectionBuilder<T> with(String name, int value) {
		return with(name, (Object)value);
	}

	public PartialInjectionBuilder<T> with(String name, String value) {
		return with(name, (Object)value);
	}
	
	public PartialInjectionBuilder<T> with(Map<String, Object> paramMap) {
		if(paramMap==null)
			return this;
		for(Map.Entry<String, Object> entry:paramMap.entrySet())
			with(entry.getKey(), entry.getValue());
		return this;
	}

	public T create(String name) {
		T value = create();
		objectStore.putObject(name, value);
		return value;
	}
	
	public T create() {
		return binding.get(objectStore, params);
	}
}
