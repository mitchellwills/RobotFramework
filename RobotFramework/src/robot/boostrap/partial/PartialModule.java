package robot.boostrap.partial;

import java.util.*;

import com.google.inject.*;

public interface PartialModule {
	public void configure(Injector injector, Map<Class<?>, PartialBinding<?>> bindings);
}
