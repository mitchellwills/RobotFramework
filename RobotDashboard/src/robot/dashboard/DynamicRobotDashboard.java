package robot.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import robot.Robot;
import robot.error.RobotInitializationException;
import robot.io.RobotObject;

/**
 * @author Mitchell
 * 
 * A window that robot objects can be added to dynamically
 *
 */
public class DynamicRobotDashboard extends JFrame {
	private int i = 0;
	private int j = 0;
	private final int gridWidth;
	private final Robot robot;
	/**
	 * Construct a new widget panel
	 * @param title the title of the window
	 * @param robot the robot this panel displays for
	 * @param gridWidth the number of elements to display per row
	 */
	public DynamicRobotDashboard(String title, Robot robot, int gridWidth){
		super(title);
		setLayout(new GridBagLayout());
		this.robot = robot;
		this.gridWidth = gridWidth;
	}

	/**
	 * put a new object in the window using the registered widget (if any) to display it
	 * @param name the name of the object
	 * @param label the label that the widget should have
	 */
	public void put(String name, String label){
		put(robot.getObject(name), label);
	}
	/**
	 * put a new object in the window using the registered widget (if any) to display it
	 * @param device the device to be represented
	 * @param label the label that the widget should have
	 */
	public void put(RobotObject device, String label){
		if(device==null)
			return;

		try {
			Class<? extends Widget<?>> c = RobotWidgets.getObjectWidgetClass(device.getClass());
			if(c==null)
				throw new RobotInitializationException("Could not find widget for "+device);
			Widget<RobotObject> widget = (Widget<RobotObject>) c.newInstance();
			widget.setObject(device);
			putWidget(widget, label);
		} catch (InstantiationException e) {
			throw new RobotInitializationException("Could not find widget for "+device);
		} catch (IllegalAccessException e) {
			throw new RobotInitializationException("Could not create widget for "+device);
		} catch (IllegalArgumentException e) {
			throw new RobotInitializationException("Could not create widget for "+device);
		}
	}

	/**
	 * Put a new widget object in the window
	 * @param widget the widget to be added
	 * @param label the label to surround the widget
	 */
	public void putWidget(Widget<?> widget, String label){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = j;
		c.gridy = i;
		c.fill = GridBagConstraints.BOTH;
		
		
		WidgetContainer o = new WidgetContainer(label, widget);
		add(o, c);
		
		++j;
		if(j>=gridWidth){
			++i;
			j = 0;
		}
		pack();
	}
}
