package robot.boostrap.partial;

import com.google.common.collect.*;
import com.google.inject.*;

public interface PartialModule {
	public void configure(Injector injector, Multimap<Class<?>, PartialBinding<?>> bindings);
}
