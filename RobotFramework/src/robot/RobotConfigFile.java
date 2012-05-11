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

import robot.io.RobotObject;
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
						
						if(objectNode.getNodeType()==Node.TEXT_NODE || objectNode.getNodeType()==Node.COMMENT_NODE){
							//
						}
						else{
							Map<String, String> params = XMLUtil.getAttributes(objectNode);
							String name = XMLUtil.getAttribute(objectNode, "name");
							String type = objectNode.getNodeName();
							RobotObject object = robot.getFactory().getObject(type, params);
							if(object==null)
								robot.putObject(name, object);
							else
								System.err.println("Could not process tag: "+objectNode.getNodeName());
						}
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
