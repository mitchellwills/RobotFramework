package robot.dashboard;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import robot.Robot;
import robot.RobotListener;
import robot.io.RobotObject;
import robot.util.XMLUtil;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * @author Mitchell
 * 
 * A dashboard panel that loads the widget layout from a xml config file
 *
 */
public class RobotDashboardPanel extends JPanel implements RobotListener {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private final Robot robot;
	private final Multimap<String, Widget<?>> widgets = HashMultimap.create();

	/**
	 * Create a new Dashboard Panel
	 * 
	 * @param robot
	 * @param config the initial configuration file to load
	 */
	public RobotDashboardPanel(final Robot robot, final File config) {
		setLayout(null);
		this.robot = robot;
		robot.addListener(this);
		if(config!=null)
			load(config);
	}

	/**
	 * Load a new Configuration file
	 * @param configFile
	 */
	public synchronized void load(final File configFile) {
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(configFile);
	
			Node panelNode = dom.getElementsByTagName("panel").item(0);
	
			int panelWidth = XMLUtil.getIntAttribute(panelNode, "width");
			int panelHeight = XMLUtil.getIntAttribute(panelNode, "height");
	
			NodeList nodes = panelNode.getChildNodes();
			load(nodes, configFile);
			setPreferredSize(new Dimension(panelWidth, panelHeight));
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load a configuration from a set of nodes
	 * @param nodes the nodes containing the widgets to be displayed on the panel
	 * @param rootFile the root file that all file access will be relative to
	 */
	public synchronized void load(final NodeList nodes, File rootFile) {
		removeAll();
		Iterator<Entry<String, Widget<?>>> widgetIterator = widgets.entries().iterator();
		while(widgetIterator.hasNext()){
			Entry<String, Widget<?>> entry = widgetIterator.next();
			entry.getValue().setObject(null);
			widgetIterator.remove();
		}
		
		for (int i = 0; i < nodes.getLength(); ++i) {
			Node node = nodes.item(i);
			try {
				if (node.getNodeName().equals("widget")) {
					String label = XMLUtil.getAttribute(node, "label");
					int x = XMLUtil.getIntAttribute(node, "x");
					int y = XMLUtil.getIntAttribute(node, "y");
					int width = XMLUtil.getIntAttribute(node, "width");
					int height = XMLUtil.getIntAttribute(node, "height");
					Widget<?> widget = processWidgetNode(node);
					JComponent o = new WidgetContainer(label, widget);
					o.setBounds(x, y, width, height);
					add(o);
				}
				else if(node.getNodeName().equals("tabs")){
					int x = XMLUtil.getIntAttribute(node, "x");
					int y = XMLUtil.getIntAttribute(node, "y");
					int width = XMLUtil.getIntAttribute(node, "width");
					int height = XMLUtil.getIntAttribute(node, "height");
					WidgetTabContainer tabContainer = new WidgetTabContainer(robot);
					
					NodeList tabNodes = node.getChildNodes();
					for (int j = 0; j < tabNodes.getLength(); ++j) {
						Node tabNode = tabNodes.item(j);
						if (tabNode.getNodeName().equals("tab")) {
							String label = XMLUtil.getAttribute(tabNode, "label");
							if(XMLUtil.hasAttribute(tabNode, "config")){
								String tabConfigFileName = XMLUtil.getAttribute(tabNode, "config");
								File tabConfigFile = new File(rootFile.getParentFile(), tabConfigFileName);
								tabContainer.addTab(label, tabConfigFile);
							}
							else{
								tabContainer.addTab(label, tabNode.getChildNodes(), rootFile);
							}
						}
						else if (tabNode.getNodeName().equals("tabWidget")) {
							String label = XMLUtil.getAttribute(tabNode, "label");
							Widget<?> widget = processWidgetNode(tabNode);
							tabContainer.addTab(label, widget);
						}
						else if(tabNode.getNodeType()==Node.TEXT_NODE || tabNode.getNodeType()==Node.COMMENT_NODE){
							//
						}
						else
							System.err.println("Unknown tag: "+tabNode.getNodeName());
					}
					tabContainer.setBounds(x, y, width, height);
					add(tabContainer);
				}
				else if(node.getNodeType()==Node.TEXT_NODE || node.getNodeType()==Node.COMMENT_NODE){
					//
				}
				else
					System.err.println("Unknown tag: "+node.getNodeName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	private Widget<?> processWidgetNode(Node node) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String name = XMLUtil.getAttribute(node, "name");
		String typeName = XMLUtil.getAttribute(node, "type");
		Class<?> type = Class.forName(typeName);
		
		Widget<RobotObject> widget;
		try{
			Constructor<?> constructor = type.getConstructor(Map.class);
			Map<String, String> params = new HashMap<String, String>();
			NodeList paramNodes = node.getChildNodes();
			for(int k = 0; k<paramNodes.getLength(); ++k){
				Node param = paramNodes.item(k);
				if(param.getNodeType()==Node.TEXT_NODE || param.getNodeType()==Node.COMMENT_NODE)
					continue;
				params.put(param.getNodeName(), param.getTextContent());
			}
			widget = (Widget<RobotObject>)constructor.newInstance(params);
		} catch(NoSuchMethodException e){
			widget = (Widget<RobotObject>)type.newInstance();
		}
		
		widgets.put(name, widget);
		widget.setObject(robot.getObject(name));
		return widget;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onNewObject(String name, RobotObject object) {
		Collection<Widget<?>> w = widgets.get(name);
		for(Widget<?> widget:w)
			((Widget<RobotObject>)widget).setObject(object);
	}
}
