package robot.boostrap.partial;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import robot.*;
import robot.boostrap.*;
import robot.error.*;

import com.google.inject.*;
import com.google.inject.assistedinject.*;
import com.google.inject.util.*;

public class PartialConstructorBinding<T> implements PartialBinding<T> {
	private final Set<ParameterKey> keys;
	private final Constructor<T> constructor;
	private final Class<T> type;
	private final Injector injector;
	

	public PartialConstructorBinding(Injector injector, Class<T> type, Constructor<T> constructor) {
		this.injector = injector;
		this.type = type;
		this.constructor = constructor;
		Set<ParameterKey> keys = new HashSet<ParameterKey>();
		for (int i = 0; i < constructor.getParameterTypes().length; ++i) {
			Class<?> paramType = ClassUtil.primitiveToWraper(constructor.getParameterTypes()[i]);
			Annotation[] paramAnnotations = constructor.getParameterAnnotations()[i];
			for (Annotation annotation : paramAnnotations) {
				if (annotation instanceof Assisted) {
					keys.add(new ParameterKey(((Assisted) annotation), paramType));
				}
			}
		}
		this.keys = Collections.unmodifiableSet(keys);
	}

	@Override
	public boolean matches(Map<String, Object> params) {
		Set<String> paramKeys = new HashSet<String>();paramKeys.addAll(params.keySet());
		for(ParameterKey key:keys){
			if(!paramKeys.remove(key.getName()))
				return false;
		}
		return paramKeys.isEmpty();
	}

	@Override
	public T get(final RobotObjectStore objectStore, final Map<String, Object> params) {
		if (!matches(params))
			throw new RobotInitializationException("Parameters do not match");

		Module assistedModule = new AbstractModule() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			protected void configure() {
				Binder binder = binder().withSource(PartialConstructorBinding.this);
				for(ParameterKey key:keys){
					Object value = params.get(key.getName());
					if(value instanceof String){
						Object storedValue = objectStore.getObject((String)value);
						if(storedValue != null && key.getType().isAssignableFrom(storedValue.getClass()))
							value = storedValue;
					}
					if(value==null)
						throw new RobotInitializationException("Cannot inject null");
					if(!key.getType().isAssignableFrom(value.getClass()))
						throw new RobotInitializationException("Parameter "+key.getName()+" of type "+value.getClass().getName()+" '"+value+"' does not match "+key.getType());
						
					binder.bind(key.asKey()).toProvider((Provider) Providers.of(value));
				}
				binder.bind(type).toConstructor(constructor);
			}
		};

		Injector forCreate = injector.createChildInjector(assistedModule);
		Binding<T> binding = forCreate.getBinding(type);
		return binding.getProvider().get();
	}



	
	
	
	
	private static class ParameterKey{
		private Assisted annotation;
		private Class<?> type;
		public ParameterKey(Assisted annotation, Class<?> type){
			this.annotation = annotation;
			this.type = type;
		}
		public Key<?> asKey() {
			return Key.get(type, annotation);
		}
		public String getName() {
			return annotation.value();
		}
		public Class<?> getType() {
			return type;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((annotation == null) ? 0 : annotation.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ParameterKey other = (ParameterKey) obj;
			if (annotation == null) {
				if (other.annotation != null)
					return false;
			} else if (!annotation.equals(other.annotation))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
		
	}
}
