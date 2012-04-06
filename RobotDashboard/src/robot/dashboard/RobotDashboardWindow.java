package robot.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import robot.io.RobotObject;

/**
 * @author Mitchell
 * 
 * A window used to display widgets
 *
 */
public class RobotDashboardWindow extends JFrame {
	int i = 0;
	int j = 0;
	/**
	 * Construct a new widget window
	 */
	public RobotDashboardWindow(){
		setLayout(new GridBagLayout());
	}
	
	/**
	 * put a new object in the window using the registered widget (if any) to display it
	 * @param device
	 */
	public void put(RobotObject device){
		if(device==null)
			return;

		try {
			putWidget(RobotWidgets.getObjectWidgetConstructor(device.getClass()).newInstance(device));
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			System.err.println("Could not find widget for "+device);
		}
	}

	/**
	 * Put a new widget object in the window
	 * @param widget
	 */
	public void putWidget(Widget widget){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = j;
		c.gridy = i;
		c.fill = GridBagConstraints.BOTH;
		add(widget, c);
		++j;
		if(j>=3){
			++i;
			j = 0;
		}
		pack();
	}
}
