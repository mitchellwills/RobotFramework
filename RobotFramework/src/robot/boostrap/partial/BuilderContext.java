package robot.boostrap.partial;

import java.util.*;

import com.google.inject.*;

public interface BuilderContext {
	
	public Iterable<? extends Module> getInjectionModules();
	public Iterable<? extends PartialModule> getPartialInjectionModules();
	public Map<String, Object> getPartialParameters();
}
