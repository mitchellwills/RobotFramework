package robot;

import java.util.*;
import java.util.regex.*;

import robot.boostrap.partial.*;
import robot.error.*;
import robot.io.*;

import com.google.inject.*;

/**
 * @author Mitchell
 * 
 * Represents a robot
 *
 */
public abstract class Robot {
	/**
	 * Initialization of all robot communication with other devices should occur here
	 */
	protected abstract void initializeIO();
	/**
	 * perform the main actions of the robot
	 */
	protected abstract void run();


	private final Map<String, String> definitions = new HashMap<String, String>();
	void putDefinition(String name, String value){
		if(definitions.containsKey(name))
			throw new RobotInitializationException("An object already exists with the name: "+name);
		definitions.put(name, value);
	}
	/**
	 * @param name the name of the definition
	 * @return the value associated with the given name
	 */
	public String getDefinition(String name){
		return definitions.get(name);
	}
	/**
	 * replace ${name} with the value
	 * @param input string
	 * @return the original string with ${name} replaced with their equivalent values
	 */
	public String e(String input){
		return evaluateDefinitions(input);
	}
	/**
	 * replace ${name} with the value
	 * @param input string
	 * @return the original string with ${name} replaced with their equivalent values
	 */
	public String evaluateDefinitions(String input){
		Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");
	    Matcher m = p.matcher(input);
	    StringBuffer s = new StringBuffer();
	    while (m.find()){
	    	String value = getDefinition(m.group(1));
	    	if(value==null)
	    		throw new RobotInitializationException("Invalid definition in '"+input+"'. "+m.group(1)+" does not exist");
	        m.appendReplacement(s, value);
	    }
	    return s.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private Injector injector;
	private PartialInjector partialInjector;
	private RobotObjectStore objectStore;
	synchronized void inject(Injector injector, PartialInjector partialInjector, RobotObjectStore objectStore){
		if(injector==null||partialInjector==null||objectStore==null)
			throw new RobotInitializationException("The injected values for the robot cannot be null");
		if(this.injector!=null||this.partialInjector!=null||this.objectStore!=null)
			throw new RobotInitializationException("The injected values cannot be set more than once");
		this.injector = injector;
		this.partialInjector = partialInjector;
		this.objectStore = objectStore;
	}
	
	
	
	
	
	public <T extends RobotObject> PartialInjectionBuilder<T> getBuilder(Class<T> type){
		return partialInjector.builderFor(type);
	}
	public <T> T get(Class<T> type){
		return injector.getInstance(type);
	}
	public ContextBuilder in(String contextName){
		RobotObject object = objectStore.getObject(contextName);
		if(object instanceof BuilderContext)
			return in((BuilderContext)object);
		throw new RobotInitializationException(contextName+" is not a builder context");
	}
	public ContextBuilder in(BuilderContext context){
		return new ContextBuilder(context, injector, partialInjector);
	}
	public RobotObject getObject(String name){
		return objectStore.getObject(name);
	}
	public <T extends RobotObject> T putObject(String name, T object){
		return objectStore.putObject(name, object);
	}
	
	
	
	
	/**
	 * Add an update listener
	 * @param listener
	 */
	public void addListener(RobotListener listener){
		objectStore.addListener(listener);
	}
	/**
	 * Remove an update listener
	 * @param listener
	 */
	public void removeListener(RobotListener listener){
		objectStore.removeListener(listener);
	}
	
	
	
}
