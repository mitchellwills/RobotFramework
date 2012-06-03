package robot.io.virtual;

import java.util.*;

import robot.boostrap.partial.*;
import robot.io.battery.*;
import robot.io.host.*;

import com.google.inject.*;

/**
 * @author Mitchell
 * 
 * Object that represents a computer
 *
 */
public class VirtualHost implements BuilderContext, Host{
	
	@Override
	public Battery getBattery() {
		return null;
	}
	
	@Override
	public Iterable<? extends Module> getInjectionModules() {
		return Collections.singleton(new VirtualObjectModule());
	}
	@Override
	public Iterable<? extends PartialModule> getPartialInjectionModules() {
		return Collections.singleton(new VirtualObjectPartialModule());
	}
	@Override
	public Map<String, Object> getPartialParameters() {
		return Collections.EMPTY_MAP;
	}

}
