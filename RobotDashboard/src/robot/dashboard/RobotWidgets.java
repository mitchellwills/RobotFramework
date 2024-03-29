package robot.dashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import robot.dashboard.widget.AnalogVoltageInputWidget;
import robot.dashboard.widget.BinaryOutputWidget;
import robot.dashboard.widget.JoystickWidget;
import robot.dashboard.widget.SpeedControllerWidget;
import robot.io.RobotObject;
import robot.io.analog.AnalogVoltageInput;
import robot.io.binary.BinaryOutput;
import robot.io.joystick.Joystick;
import robot.io.speedcontroller.SpeedController;

/**
 * @author Mitchell
 * 
 * The class that maps robot objects to widgets that can be added to a RobotDashboardWindow
 *
 */
public final class RobotWidgets {
	private RobotWidgets(){}//prevent instanciation
	
	private static Map<Class<? extends RobotObject>, Class<? extends Widget<?>>> widgets = new HashMap<Class<? extends RobotObject>, Class<? extends Widget<?>>>();//TODO change to multimap
	static{
		registerWidget(Joystick.class, JoystickWidget.class);
		registerWidget(BinaryOutput.class, BinaryOutputWidget.class);
		registerWidget(AnalogVoltageInput.class, AnalogVoltageInputWidget.class);
		registerWidget(SpeedController.class, SpeedControllerWidget.class);
	}
	
	/**
	 * Register that a given widget can support a given object type
	 * @param objectType
	 * @param widgetType
	 */
	public static void registerWidget(Class<? extends RobotObject> objectType, Class<? extends Widget<?>> widgetType){
		widgets.put(objectType, widgetType);
	}
	
	/**
	 * @param objectType
	 * @return the constructor that can be used to construct a widget for a given object type
	 */
	public static Class<? extends Widget<?>> getObjectWidgetClass(Class<? extends RobotObject> objectType){
		for(Entry<Class<? extends RobotObject>, Class<? extends Widget<?>>> e:widgets.entrySet()){
			if(e.getKey().isAssignableFrom(objectType)){
				return e.getValue();
			}
		}
		return null;
	}
}
