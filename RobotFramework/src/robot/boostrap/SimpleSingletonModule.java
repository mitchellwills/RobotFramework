package robot.boostrap;

import com.google.inject.*;

public class SimpleSingletonModule<T> extends AbstractModule {
	private final Class<T> type;
	private final Class<? extends T> objectClass;
	public SimpleSingletonModule(Class<T> type, Class<? extends T> objectClass){
		this.type = type;
		this.objectClass = objectClass;
	}
	@Override
	protected void configure() {
		bind(type).to(objectClass).asEagerSingleton();
	}
}