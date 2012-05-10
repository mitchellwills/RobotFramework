package robot;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import robot.util.XMLUtil;

class RobotConfigFile {
	private RobotConfigFile(){}
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	static void load(Robot robot, File configFile){
		if(robot==null)
			throw new NullPointerException("The robot cannot be null");
		if(configFile==null)
			throw new NullPointerException("The robot configuration file cannot be null");
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(configFile);

			Node panelNode = dom.getElementsByTagName("robotConfig").item(0);

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
							robot.putDefinition(name, value);
						}
						else if(definitionNode.getNodeType()==Node.TEXT_NODE || definitionNode.getNodeType()==Node.COMMENT_NODE){
							//
						}
						else
							System.err.println("Unknown tag: "+definitionNode.getNodeName());
					}
				}
				else if(node.getNodeName().equals("objects")){
					NodeList objectNodes = node.getChildNodes();
					for (int j = 0; j < objectNodes.getLength(); ++j) {
						Node objectNode = objectNodes.item(j);
						if (objectNode.getNodeName().equals("AnalogVoltageInput")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getAnalogVoltageInput(location));
						}
						else if (objectNode.getNodeName().equals("BinaryInput")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getBinaryInput(location));
						}
						else if (objectNode.getNodeName().equals("BinaryOutput")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getBinaryOutput(location));
						}
						else if (objectNode.getNodeName().equals("Counter")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getCounter(location));
						}
						else if (objectNode.getNodeName().equals("DutyCycle")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getDutyCycle(location));
						}
						else if (objectNode.getNodeName().equals("Encoder")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String locationA = XMLUtil.getAttribute(objectNode, "locationA");
							String locationB = XMLUtil.getAttribute(objectNode, "locationB");
							robot.putObject(name, robot.getFactory().getEncoder(locationA, locationB));
						}
						else if (objectNode.getNodeName().equals("FrequencyInput")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getFrequencyInput(location));
						}
						else if (objectNode.getNodeName().equals("PPMReader")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							int numChannels = XMLUtil.getIntAttribute(objectNode, "numChannels");
							robot.putObject(name, robot.getFactory().getPPMReader(location, numChannels));
						}
						else if (objectNode.getNodeName().equals("PWMOutput")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getPWMOutput(location));
						}
						else if (objectNode.getNodeName().equals("MSPWMOutput")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getMSPWM(location));
						}
						else if (objectNode.getNodeName().equals("SerialInterface")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							int baud = XMLUtil.getIntAttribute(objectNode, "baud");
							robot.putObject(name, robot.getFactory().getSerialInterface(location, baud));
						}
						else if (objectNode.getNodeName().equals("Joystick")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getJoystick(location));
						}
						else if (objectNode.getNodeName().equals("Accelerometer")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getAccelerometer(location));
						}
						else if (objectNode.getNodeName().equals("Joystick")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getJoystick(location));
						}
						else if (objectNode.getNodeName().equals("SpeedController")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String location = XMLUtil.getAttribute(objectNode, "location");
							robot.putObject(name, robot.getFactory().getSpeedController(location));
						}
						
						
						
						
						
						else if (objectNode.getNodeName().equals("object")) {
							String name = XMLUtil.getAttribute(objectNode, "name");
							String type = XMLUtil.getAttribute(objectNode, "type");
							
							Map<String, String> params = XMLUtil.getAttributes(objectNode);
							robot.putObject(name, robot.getFactory().getObject(type, params));
						}
						
						
						else if(objectNode.getNodeType()==Node.TEXT_NODE || objectNode.getNodeType()==Node.COMMENT_NODE){
							//
						}
						else
							System.err.println("Unknown tag: "+objectNode.getNodeName());
					}
				}
				else if(node.getNodeType()==Node.TEXT_NODE || node.getNodeType()==Node.COMMENT_NODE){
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
}
