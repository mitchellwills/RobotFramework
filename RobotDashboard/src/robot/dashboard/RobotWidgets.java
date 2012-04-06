package robot.dashboard;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import robot.dashboard.widget.AnalogVoltageInputWidget;
import robot.dashboard.widget.BinaryOutputWidget;
import robot.dashboard.widget.JoystickWidget;
import robot.io.RobotObject;
import robot.io.analog.AnalogVoltageInput;
import robot.io.binary.BinaryOutput;
import robot.io.joystick.Joystick;

public final class RobotWidgets {
	private RobotWidgets(){}//prevent instanciation
	
	private static Map<Class<? extends RobotObject>, Class<? extends Widget>> widgets = new HashMap<>();//TODO change to multimap
	static{
		registerWidget(Joystick.class, JoystickWidget.class);
		registerWidget(BinaryOutput.class, BinaryOutputWidget.class);
		registerWidget(AnalogVoltageInput.class, AnalogVoltageInputWidget.class);
	}
	
	public static void registerWidget(Class<? extends RobotObject> objectType, Class<? extends Widget> widgetType){
		widgets.put(objectType, widgetType);
	}
	
	public static Constructor<? extends Widget> getObjectWidgetConstructor(Class<? extends RobotObject> objectType){
		for(Entry<Class<? extends RobotObject>, Class<? extends Widget>> e:widgets.entrySet()){
			if(e.getKey().isAssignableFrom(objectType)){
				Class<? extends Widget> widgetClass = e.getValue();
				try {
					return widgetClass.getConstructor(e.getKey());
				} catch (NoSuchMethodException | SecurityException e1) {
					return null;
				}
			}
		}
		return null;
	}
}
