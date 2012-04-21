package robot.dashboard;

import javax.swing.JComponent;

import robot.io.RobotObject;

/**
 * @author Mitchell
 * 
 * Represents a widget that can be added to a RobotDashboardWindow
 * @param <T> the type of the objects this widget displays
 *
 */
public abstract class Widget<T extends RobotObject> extends JComponent{

	/**
	 * Set the object the widget should display
	 * @param o the object to be displayed
	 */
	public abstract void setObject(T o);
}
