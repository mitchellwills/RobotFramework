package robot.boostrap.partial;

import java.util.*;

import com.google.common.collect.*;
import com.google.inject.*;


public abstract class AbstractPartialModule implements PartialModule{
	
	private Set<PartialBindingBuilder<?>> localBindings;
	protected abstract void configure();
	
	@Override
	public void configure(Injector injector, Multimap<Class<?>, PartialBinding<?>> bindings) {
		localBindings = new HashSet<PartialBindingBuilder<?>>();
		configure();
		for(PartialBindingBuilder<?> builder:localBindings)
			bindings.putAll(builder.getType(), builder.build(injector));
	}
	
	public <T> PartialBindingBuilder<T> bind(Class<T> type, Class<? extends T> targetType){
		PartialBindingBuilder<T> bindingBuilder =  new PartialBindingBuilder<T>(type, targetType);
		localBindings.add(bindingBuilder);
		return bindingBuilder;
	}

}
