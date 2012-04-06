package robot.imperium.dashboard;

import robot.dashboard.RobotWidgets;
import robot.imperium.ImperiumDevice;

/**
 * @author Mitchell
 * 
 * Class that can register Imperium Robot Object Dashboard widgets
 *
 */
public final class ImperiumDashboard {
	private ImperiumDashboard(){}
	
	/**
	 * register all Imperium Robot Dashboard Widgets
	 */
	public static void registerWidgets(){
		RobotWidgets.registerWidget(ImperiumDevice.class, ImperiumDeviceWidget.class);
	}
	
}
