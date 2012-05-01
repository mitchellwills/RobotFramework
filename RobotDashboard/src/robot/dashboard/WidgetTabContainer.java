package robot.dashboard;

import java.io.File;

import javax.swing.JTabbedPane;

import org.w3c.dom.NodeList;

import robot.Robot;

/**
 * @author Mitchell
 * 
 * A Tab panel that contains sub panels which are RobotDashboard panels
 *
 */
public class WidgetTabContainer extends JTabbedPane{
	private final Robot robot;
	/**
	 * Create a new Tab Panel
	 * @param robot
	 */
	public WidgetTabContainer(final Robot robot){
		this.robot = robot;
	}
	/**
	 * Add a new tab
	 * @param label what the tab should be labeled
	 * @param config
	 */
	public void addTab(final String label, final File config){
		RobotDashboardPanel panel = new RobotDashboardPanel(robot, config);
		addTab(label, panel);
	}
	/**
	 * Add a tab containing a list of widgets
	 * @param label the tab label
	 * @param widgetNodes the nodes containing the widgets to be displayed on the panel
	 * @param rootFile the root file that all file access will be relative to
	 */
	public void addTab(String label, NodeList widgetNodes, File rootFile) {
		RobotDashboardPanel panel = new RobotDashboardPanel(robot, null);
		panel.load(widgetNodes, rootFile);
		addTab(label, panel);
	}
}
