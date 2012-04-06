package robot.imperium.dashboard;

import robot.dashboard.RobotWidgets;
import robot.imperium.ImperiumDevice;

public final class ImperiumDashboard {
	private ImperiumDashboard(){}
	
	public static void registerWidgets(){
		RobotWidgets.registerWidget(ImperiumDevice.class, ImperiumDeviceWidget.class);
	}
	
}
