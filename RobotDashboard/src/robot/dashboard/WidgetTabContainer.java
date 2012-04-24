package robot.dashboard;

import java.io.File;

import javax.swing.JTabbedPane;

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
}
