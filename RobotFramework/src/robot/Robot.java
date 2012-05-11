package robot;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import robot.error.RobotInitializationException;
import robot.io.RobotObject;
import robot.io.factory.NameRobotObjectFactory;
import robot.io.factory.RobotObjectFactory;
import robot.io.factory.VirtualRobotObjectFactory;
import robot.io.host.Host;
import robot.thread.RobotThreadManager;

/**
 * @author Mitchell
 * 
 * Represents a robot
 *
 */
public abstract class Robot {
	private RobotObjectFactory factory;
	private RobotThreadManager threadManager;
	private static Robot INSTANCE = null;
	private static boolean initialized = false;
	
	
	/**
	 * Start the robot
	 * @param robot
	 */
	public static void go(Robot robot){
		initialized = true;

		INSTANCE.initializeIO();
		INSTANCE.run();//TODO run in thread
	}
	/**
	 * @return the singleton robot instance
	 */
	public static Robot getInstance(){
		if(INSTANCE==null || !initialized)
			throw new IllegalStateException("You must call Robot.go");
		return INSTANCE;
	}
	
	
	
	/**
	 * Construct a new robot with no configuration file
	 */
	public Robot(){
		this(null);
	}
	/**
	 * Construct a new robot with no configuration file
	 * @param host the host object for this system
	 */
	public Robot(Host host){
		this(null, host);
	}
	/**
	 * Construct a new robot
	 * @param configFile the configuration to load for the robot
	 * @param host the host object for this system
	 */
	public Robot(File configFile, Host host){
		this(configFile, null, host);
	}
	/**
	 * Construct a new robot
	 * @param configFile the configuration to load for the robot
	 * @param factory the factory that can be used to create objects
	 * @param host the host object for this system
	 */
	public Robot(File configFile, RobotObjectFactory factory, Host host){
		if(INSTANCE!=null)
			throw new IllegalStateException("Only one robot is allowed per VM");
		INSTANCE = this;
		
		if(host!=null)
			putObject("host", host);
		threadManager = new RobotThreadManager();
		putObject("virtual", new VirtualRobotObjectFactory());
		setFactory(factory);
		if(configFile!=null)
			RobotConfigFile.load(this, configFile);
	}
	/**
	 * Initialization of all robot communication with other devices should occur here
	 */
	protected abstract void initializeIO();
	/**
	 * perform the main actions of the robot
	 */
	protected abstract void run();
	

	/**
	 * @return the factory that can be used to create objects
	 */
	public RobotObjectFactory getFactory(){
		return factory;
	}
	
	/**
	 * @return the thread manager for the robot
	 */
	public RobotThreadManager getThreadManager(){
		return threadManager;
	}
	
	/**
	 * Set the factory used to construct robot objects by default
	 * @param factory
	 */
	public void setFactory(RobotObjectFactory factory){
		if(factory==null)
			this.factory = new NameRobotObjectFactory();
		else
			this.factory = factory;
	}
	
	

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
	
	
	
	private final Map<String, RobotObject> objects = new HashMap<String, RobotObject>();
	
	protected <T extends RobotObject> T putObject(String name, T object){
		if(objects.containsKey(name))
			throw new RobotInitializationException("An object already exists with the name: "+name);
		objects.put(name, object);
		fireNewObjectEvent(name, object);
		return object;
	}
	
	/**
	 * @param name name of the object
	 * @return the robot object associated with the given name
	 */
	public RobotObject getObject(String name){
		return objects.get(name);
	}
	
	
	private final Set<RobotListener> listeners = new HashSet<RobotListener>();
	/**
	 * Add an update listener
	 * @param listener
	 */
	public void addListener(RobotListener listener){
		listeners.add(listener);
	}
	/**
	 * Remove an update listener
	 * @param listener
	 */
	public void removeListener(RobotListener listener){
		listeners.remove(listener);
	}
	
	/**
	 * @param name the name given to the object
	 * @param object the new object
	 * 
	 */
	public void fireNewObjectEvent(String name, RobotObject object){
		for(RobotListener listener:listeners)
			listener.onNewObject(name, object);
	}
}
