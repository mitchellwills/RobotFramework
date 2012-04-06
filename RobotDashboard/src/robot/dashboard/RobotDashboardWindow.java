package robot.dashboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;

import robot.io.RobotObject;

public class RobotDashboardWindow extends JFrame {
	int i = 0;
	int j = 0;
	public RobotDashboardWindow(){
		setLayout(new GridBagLayout());
	}
	
	public void put(RobotObject device){
		if(device==null)
			return;

		try {
			putWidget(RobotWidgets.getObjectWidgetConstructor(device.getClass()).newInstance(device));
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Could not find widget for "+device);
		}
	}

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
