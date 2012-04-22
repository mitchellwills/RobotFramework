package robot;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.runner.Computer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import robot.error.RobotInitializationException;
import robot.io.NameRobotObjectFactory;
import robot.io.RobotObject;
import robot.io.RobotObjectFactory;
import robot.util.XMLUtil;

/**
 * @author Mitchell
 * 
 * Represents a robot
 *
 */
public abstract class Robot {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	
	private String configName;
	private RobotObjectFactory factory;
	/**
	 * Construct a new robot
	 * @param configFile the configuration to load for the robot
	 * @param factory the factory that can be used to create objects
	 */
	public Robot(File configFile){
		factory = new NameRobotObjectFactory(this);
		load(configFile);
		System.out.println("Loaded configuration: "+configName);
	}
	
	/**
	 * run the robot
	 */
	public void go() {
		initializeIO();
		run();
	}
	/**
	 * Initialization of all robot communication with other devices should occur here
	 */
	public abstract void initializeIO();
	/**
	 * perform the main actions of the robot
	 */
	public abstract void run();
	
	
	
	
	
	
	
	
	
	private void load(File configFile){
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(configFile);

			Node panelNode = dom.getElementsByTagName("robotConfig").item(0);

			configName = XMLUtil.getAttribute(panelNode, "name");

			NodeList nodes = panelNode.getChildNodes();

			for (int i = 0; i < nodes.getLength(); ++i) {
				Node node = nodes.item(i);
				if(node.getNodeName().equals("definitions")){
					NodeList definitionNodes = node.getChildNodes();
					for (int j = 0; j < definitionNodes.getLength(); ++j) {
						Node definitionNode = definitionNodes.item(j);
						if (definitionNode.getNodeName().equals("define")) {
							String name = XMLUtil.getAttribute(definitionNode, "name");
							String value = XMLUtil.getAttribute(definitionNode, "value");
							putDefinition(name, value);
						}
						else if(definitionNode.getNodeName().equals("#text")){
							//
						}
						else
							System.err.println("Unknown tag: "+definitionNode.getNodeName());
					}
				}
				else if(node.getNodeName().equals("#text")){
					//
				}
				else
					System.err.println("Unknown tag: "+node.getNodeName());
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return the factory that can be used to create objects
	 */
	public RobotObjectFactory getFactory(){
		return factory;
	}
	
	

	private final Map<String, String> definitions = new HashMap<String, String>();
	protected void putDefinition(String name, String value){
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
	
	protected void putObject(String name, RobotObject object){
		if(objects.containsKey(name))
			throw new RobotInitializationException("An object already exists with the name: "+name);
		objects.put(name, object);
		fireNewObjectEvent(name, object);
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
