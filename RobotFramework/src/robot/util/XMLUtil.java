package robot.util;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Mitchell
 * 
 * Some utility methods for manipulating xml
 *
 */
public class XMLUtil {
	
	/**
	 * @param root the node whose children will be searched
	 * @param childName the name of the child node
	 * @return the first child node of the root with the given name
	 */
	public static Node getFirstChild(Node root, String childName){
		NodeList children = root.getChildNodes();
		for(int i = 0; i<children.getLength(); ++i){
			Node child = children.item(i);
			if(child.getNodeName().equals(childName))
				return child;
		}
		return null;
	}
	
	/**
	 * @param node the node to get the attribute from
	 * @param name the name of the attribute
	 * @return if the given node has the given attribute
	 */
	public static boolean hasAttribute(Node node, String name){
		Node attributeNode = node.getAttributes().getNamedItem(name);
		return attributeNode!=null;
	}
	
	/**
	 * @param node the node to get the attribute from
	 * @param name the name of the attribute
	 * @return the value of the attribute
	 */
	public static String getAttribute(Node node, String name){
		Node attrNode = node.getAttributes().getNamedItem(name);
		if(attrNode==null)
			return null;
		return attrNode.getNodeValue();
	}

	/**
	 * @param node the node to get the attribute from
	 * @param name the name of the attribute
	 * @return the value of the attribute as an integer
	 */
	public static int getIntAttribute(Node node, String name){
		return Integer.parseInt(getAttribute(node, name));
	}

	/**
	 * @param node the node to get the attribute from
	 * @param name the name of the attribute
	 * @return the value of the attribute as a boolean
	 */
	public static boolean getBooleanAttribute(Node node, String name){
		return Boolean.parseBoolean(getAttribute(node, name));
	}
	
	/**
	 * @param node
	 * @return a map containing all attributes of a node
	 */
	public static Map<String, String> getAttributes(Node node){
		Map<String, String> r = new HashMap<String, String>();
		NamedNodeMap attributes = node.getAttributes();
		for(int k = 0; k<attributes.getLength(); ++k){
			Node attribute = attributes.item(k);
			r.put(attribute.getNodeName(), attribute.getNodeValue());
		}
		return r;
	}
}
