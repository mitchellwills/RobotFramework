package robot.util;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.w3c.dom.*;
import org.xml.sax.*;

@RunWith(JUnit4.class)
public class XMLUtilTest {
	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		new XMLUtil();
	}
	
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static Node toDOM(String xml){
		try{
			InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(xml));
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			return doc.getFirstChild();
		} catch(Exception e){
			throw new RuntimeException("Error parsing test xml", e);
		}
	}

	private static final String testXML1 = "<hi hello='hi'><test/></hi>";
	private static final String testXML2 = "<hi><thisisatest attr1='val1' attr2='val2'/><anotherNode myattr='myVal'></anotherNode></hi>";
	private static final String testXML3 = "<hi mybool='true' mybool2='false' myint1='5' myint2='-10'/>";

	
	
	
	@Test
	public void testGetFirstChild() {
		Node root1 = toDOM(testXML1);
		Node child1 = XMLUtil.getFirstChild(root1, "test");
		assertEquals("test", child1.getNodeName());
		Node nonChild = XMLUtil.getFirstChild(root1, "someNode");
		assertEquals(null, nonChild);
		
		Node root2 = toDOM(testXML2);
		Node child21 = XMLUtil.getFirstChild(root2, "thisisatest");
		Node child22 = XMLUtil.getFirstChild(root2, "anotherNode");
		assertEquals("thisisatest", child21.getNodeName());
		assertEquals("anotherNode", child22.getNodeName());
	}
	
	
	@Test
	public void testHasAttribute() {
		Node root1 = toDOM(testXML1);
		assertEquals(true, XMLUtil.hasAttribute(root1, "hello"));
		assertEquals(false, XMLUtil.hasAttribute(root1, "hi"));
		Node child1 = XMLUtil.getFirstChild(root1, "test");
		assertEquals(false, XMLUtil.hasAttribute(child1, "hello"));
		
		Node root2 = toDOM(testXML2);
		assertEquals(false, XMLUtil.hasAttribute(root2, "hi"));
		Node child21 = XMLUtil.getFirstChild(root2, "thisisatest");
		Node child22 = XMLUtil.getFirstChild(root2, "anotherNode");
		assertEquals(false, XMLUtil.hasAttribute(child21, "hello"));
		assertEquals(false, XMLUtil.hasAttribute(child22, "hello"));
		assertEquals(true, XMLUtil.hasAttribute(child21, "attr1"));
		assertEquals(true, XMLUtil.hasAttribute(child21, "attr2"));
		assertEquals(true, XMLUtil.hasAttribute(child22, "myattr"));
	}

	@Test
	public void testGetAttribute() {
		Node root1 = toDOM(testXML1);
		assertEquals("hi", XMLUtil.getAttribute(root1, "hello"));
		assertEquals(null, XMLUtil.getAttribute(root1, "hi"));
		Node child1 = XMLUtil.getFirstChild(root1, "test");
		assertEquals(null, XMLUtil.getAttribute(child1, "hello"));
		
		Node root2 = toDOM(testXML2);
		assertEquals(null, XMLUtil.getAttribute(root2, "hi"));
		Node child21 = XMLUtil.getFirstChild(root2, "thisisatest");
		Node child22 = XMLUtil.getFirstChild(root2, "anotherNode");
		assertEquals(null, XMLUtil.getAttribute(child21, "hello"));
		assertEquals(null, XMLUtil.getAttribute(child22, "hello"));
		assertEquals("val1", XMLUtil.getAttribute(child21, "attr1"));
		assertEquals("val2", XMLUtil.getAttribute(child21, "attr2"));
		assertEquals("myVal", XMLUtil.getAttribute(child22, "myattr"));
	}

	@Test
	public void testGetIntAttribute() {
		Node root = toDOM(testXML3);
		assertEquals(5, XMLUtil.getIntAttribute(root, "myint1"));
		assertEquals(-10, XMLUtil.getIntAttribute(root, "myint2"));
		try{
			XMLUtil.getIntAttribute(root, "mybool");
			fail();
		} catch(NumberFormatException e){
			//success
		}
	}
	
	@Test
	public void testGetBooleanAttribute() {
		Node root = toDOM(testXML3);
		assertEquals(true, XMLUtil.getBooleanAttribute(root, "mybool"));
		assertEquals(false, XMLUtil.getBooleanAttribute(root, "mybool2"));
		assertEquals(false, XMLUtil.getBooleanAttribute(root, "myint1"));
	}
	@Test
	public void testGetAttributes() {
		Node root1 = toDOM(testXML1);
		Map<String, String> values1 = XMLUtil.getAttributes(root1);
		assertEquals("hi", values1.get("hello"));
		
		Node root2 = toDOM(testXML2);
		Map<String, String> values2 = XMLUtil.getAttributes(root2);
		assertEquals(true, values2.isEmpty());

		Node root3 = toDOM(testXML3);
		Map<String, String> values3 = XMLUtil.getAttributes(root3);
		assertEquals("true", values3.get("mybool"));
		assertEquals("false", values3.get("mybool2"));
		assertEquals("5", values3.get("myint1"));
		assertEquals("-10", values3.get("myint2"));
	}
}
