package robot.dashboard;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;

import robot.dashboard.widget.JoystickWidget;
import robot.io.RobotObject;
import robot.io.joystick.Joystick;

public class RobotDashboardWindow extends JFrame {
	private Map<Class<? extends RobotObject>, Class<? extends Widget>> widgets = new HashMap<>();
	public RobotDashboardWindow(){
		widgets.put(Joystick.class, JoystickWidget.class);
	}
	
	public void put(RobotObject device){
		if(device==null)
			return;
		
		for(Entry<Class<? extends RobotObject>, Class<? extends Widget>> e:widgets.entrySet()){
			if(e.getKey().isAssignableFrom(device.getClass())){
				Class<? extends Widget> widgetClass = e.getValue();
				try {
					putWidget(widgetClass.getConstructor(e.getKey()).newInstance(device));
					return;
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		System.err.println("Could not find widget for "+device);
	}
	public void putWidget(Widget widget){
		add(widget);
		pack();
	}
}
