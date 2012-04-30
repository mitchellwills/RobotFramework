package robot.util;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author Mitchell
 * 
 * Some utility methods for manipulating xml
 *
 */
public class XMLUtil {
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
		return node.getAttributes().getNamedItem(name).getNodeValue();
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
