package robot.boostrap.partial;

import java.util.*;

import robot.error.*;

import com.google.inject.*;


public abstract class AbstractPartialModule implements PartialModule{
	
	private Set<PartialBindingBuilder<?>> localBindings;
	protected abstract void configure();
	
	@Override
	public void configure(Injector injector, Map<Class<?>, PartialBinding<?>> bindings) {
		localBindings = new HashSet<PartialBindingBuilder<?>>();
		configure();
		for(PartialBindingBuilder<?> builder:localBindings){
			if(bindings.get(builder.getType())!=null)
				throw new RobotInitializationException("Duplicate binding for "+builder.getType());
			bindings.put(builder.getType(), builder.build(injector));
		}
	}
	
	public <T> PartialBindingBuilder<T> bind(Class<T> type, Class<? extends T> targetType){
		PartialBindingBuilder<T> bindingBuilder =  new PartialBindingBuilder<T>(type, targetType);
		localBindings.add(bindingBuilder);
		return bindingBuilder;
	}

}
