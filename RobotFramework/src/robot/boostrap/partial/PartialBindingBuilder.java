package robot.boostrap.partial;

import java.lang.reflect.*;
import java.util.*;

import com.google.inject.*;

public class PartialBindingBuilder<T> {

	private final Class<T> type;
	private Class<? extends T> targetType;

	public PartialBindingBuilder(Class<T> type, Class<? extends T> targetType) {
		this.type = type;
		this.targetType = targetType;
	}

	public Class<?> getType() {
		return type;
	}

	public Set<PartialBinding<T>> build(Injector injector) {
		Set<PartialBinding<T>> bindings = new HashSet<PartialBinding<T>>();
		Constructor<T>[] constructors = (Constructor<T>[]) targetType.getConstructors();
		for (Constructor<T> constructor : constructors) {
			if (constructor.getAnnotation(Inject.class) != null) {
				bindings.add(new PartialConstructorBinding<T>(injector, type, constructor));
			}
		}
		return bindings;
	}

}
