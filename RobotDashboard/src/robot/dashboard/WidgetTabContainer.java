package robot.dashboard;

import java.io.File;

import javax.swing.JTabbedPane;

import robot.Robot;

public class WidgetTabContainer extends JTabbedPane{
	private final Robot robot;
	public WidgetTabContainer(Robot robot){
		this.robot = robot;
	}
	public void addTab(String label, File config){
		RobotDashboardPanel panel = new RobotDashboardPanel(robot, config);
		addTab(label, panel);
	}
}
