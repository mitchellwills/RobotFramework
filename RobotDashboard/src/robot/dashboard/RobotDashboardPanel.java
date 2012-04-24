package robot.dashboard;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
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

/**
 * @author Mitchell
 * 
 * A dashboard panel that loads the widget layout from a xml config file
 *
 */
public class RobotDashboardPanel extends JPanel implements RobotListener {
	private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	private final Robot robot;
	private final Map<String, Widget<?>> widgets = new HashMap<String, Widget<?>>();

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
		load(config);
	}

	/**
	 * Load a new Configuration file
	 * @param configFile
	 */
	public synchronized void load(final File configFile) {
		removeAll();
		Iterator<Entry<String, Widget<?>>> widgetIterator = widgets.entrySet().iterator();
		while(widgetIterator.hasNext()){
			Entry<String, Widget<?>> entry = widgetIterator.next();
			entry.getValue().setObject(null);
			widgetIterator.remove();
		}
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(configFile);

			Node panelNode = dom.getElementsByTagName("panel").item(0);

			int panelWidth = XMLUtil.getIntAttribute(panelNode, "width");
			int panelHeight = XMLUtil.getIntAttribute(panelNode, "height");

			NodeList nodes = panelNode.getChildNodes();

			for (int i = 0; i < nodes.getLength(); ++i) {
				Node node = nodes.item(i);
				try {
					if (node.getNodeName().equals("widget")) {
						String name = XMLUtil.getAttribute(node, "name");
						String label = XMLUtil.getAttribute(node, "label");
						String typeName = XMLUtil.getAttribute(node, "type");
						int x = XMLUtil.getIntAttribute(node, "x");
						int y = XMLUtil.getIntAttribute(node, "y");
						int width = XMLUtil.getIntAttribute(node, "width");
						int height = XMLUtil.getIntAttribute(node, "height");
						Class<?> type = Class.forName(typeName);
						
						Widget<RobotObject> widget = (Widget<RobotObject>)type.newInstance();
						widgets.put(name, widget);
						widget.setObject(robot.getObject(name));
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
								String tabConfigFileName = XMLUtil.getAttribute(tabNode, "config");
								File tabConfigFile = new File(configFile.getParentFile(), tabConfigFileName);
								tabContainer.addTab(label, tabConfigFile);
							}
							else if(tabNode.getNodeName().equals("#text")){
								//
							}
							else
								System.err.println("Unknown tag: "+tabNode.getNodeName());
						}
						tabContainer.setBounds(x, y, width, height);
						add(tabContainer);
					}
					else if(node.getNodeName().equals("#text")){
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
				}
			}
			
			setPreferredSize(new Dimension(panelWidth, panelHeight));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onNewObject(String name, RobotObject object) {
		Widget<RobotObject> widget = (Widget<RobotObject>) widgets.get(name);
		if (widget != null)
			widget.setObject(object);
	}
}
