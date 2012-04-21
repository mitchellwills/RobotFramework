package robot.util;

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
}
