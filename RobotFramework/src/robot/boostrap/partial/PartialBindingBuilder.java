package robot.boostrap.partial;

import java.lang.reflect.*;

import robot.error.*;

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

	public PartialBinding<T> build(Injector injector) {
		PartialBinding<T> binding = null;
		Constructor<T>[] constructors = (Constructor<T>[]) targetType.getConstructors();
		Constructor<T> defaultConstructor = null;
		for (Constructor<T> constructor : constructors) {
			if (constructor.getAnnotation(Inject.class) != null){
				if(binding!=null)
					throw new RobotInitializationException("Cannot have more than one possible injection point in "+targetType.getName());
				binding = new PartialConstructorBinding<T>(injector, type, constructor);
			}
			if(constructor.getParameterTypes().length==0)
				defaultConstructor = constructor;
		}
		if(binding==null && defaultConstructor!=null)
				binding = new PartialConstructorBinding<T>(injector, type, defaultConstructor);
		if(binding==null)
			throw new RobotInitializationException("No possible injection points found for "+targetType.getName());
		return binding;
	}

}
